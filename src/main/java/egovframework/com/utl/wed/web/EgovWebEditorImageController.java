package egovframework.com.utl.wed.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.utl.wed.util.CkImageSaver;
import egovframework.com.utl.wed.util.DefaultFileSaveManager;
import egovframework.com.utl.wed.util.DirectoryPathManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cryptography.impl.EgovEnvCryptoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.utl.fcc.service.EgovFormBasedFileUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

/**
 * 웹에디터 이미지 upload 처리 Controller
 * @author 공통컴포넌트개발팀 한성곤
 * @since 2009.08.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일                수정자          수정내용
 *  -----------   --------  ---------------------------
 *   2009.08.26   한성곤          최초 생성
 *   2017.08.31   장동한          path, physical 파라미터 노출 암호화 처리
 *   2017.12.12   장동한          출력 모듈 경로 변경 취약점 조치
 *   2018.03.07   신용호          URLEncode 처리
 *   2018.08.17   신용호          URL 암호화 보안 추가 조치
 *   2020.08.05   신용호          imageUploadCk Parameter 수정
 *   2022.07.12   이석곤          주석 파라미터 type명과 변수명 수정
 *
 * </pre>
 */
@RestController
public class EgovWebEditorImageController {

	/** 로그설정 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovWebEditorImageController.class);

	private static final String IMAGE_BASE_DIR_KEY = "ck.image.dir";
	private static final String IMAGE_BASE_URL_KEY = "ck.image.url";
	private static final String IMAGE_ALLOW_TYPE_KEY = "ck.image.type.allow";
	private static final String IMAGE_SAVE_CLASS_KEY = "ck.image.save.class";

	/** 첨부파일 위치 지정 */
	private final String uploadDir;
	/** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png */
	private final String extWhiteList;
	/** 첨부 최대 파일 크기 지정 */
	private final long maxFileSize = 1024L * 1024L * 100L;   //업로드 최대 사이즈 설정 (100M)

	/** 암호화서비스 */
//	private final EgovEnvCryptoServiceImpl cryptoService;
	private EgovEnvCryptoServiceImpl cryptoService;

	/** EgovMessageSource */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	private final Environment env;
	private CkImageSaver ckImageSaver;

	private final ServletContext servletContext;

	public EgovWebEditorImageController(EgovEnvCryptoServiceImpl cryptoService, @Autowired Environment env, ServletContext servletContext) {
        this.cryptoService = cryptoService;
        this.env = env;
		this.uploadDir = env.getProperty("Globals.fileStorePath");
		this.extWhiteList = env.getProperty("Globals.fileDownload.Extensions");
        this.servletContext = servletContext;
    }

	@PostConstruct
	public void init() {
		String imageBaseDir = env.getProperty(IMAGE_BASE_DIR_KEY);
		String imageDomain = env.getProperty(IMAGE_BASE_URL_KEY);
		String saveManagerClass = env.getProperty(IMAGE_SAVE_CLASS_KEY);
		String allowFileType = env.getProperty(IMAGE_ALLOW_TYPE_KEY);

		ckImageSaver = new CkImageSaver(
				imageBaseDir,
				imageDomain,
				StringUtils.isNotBlank(allowFileType) ? StringUtils.split(allowFileType, ",") : new String[] {""},
				saveManagerClass,//2022.01. Method call passes null for non-null parameter 처리
				cryptoService);
	}

	/**
	 * 이미지 Upload(CK에디터)를 처리한다.
	 *
	 * @param
	 * @return
	 * @throws
	 */
	@PostMapping(value="/utl/wed/insertImageCk")
	public void imageUploadCk(@RequestParam("upload") MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" 파일 >> " + upload.getOriginalFilename());

		// 파일이 비어 있는지 확인
		if (upload.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File upload is empty.");
			return;
		}

		try {
			ckImageSaver.saveAndReturnUrlToClient(request, response, upload);
		} catch (Exception e) {
			LOGGER.error("Error saving image", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Image upload failed.");
		}
	}

	/**
	 * 이미지 view를 제공한다.
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping(value="/utl/wed/imageSrc")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//2017.12.12 - 출력 모듈 경로 변경 취약점 조치
		//KISA 보안약점 조치 (2018-10-29, 윤창원)
		String subPath = this.decrypt(EgovStringUtil.isNullToString(request.getParameter("path")));
		String physical = this.decrypt(EgovStringUtil.isNullToString(request.getParameter("physical")));
		String mimeType = this.decrypt(EgovStringUtil.isNullToString(request.getParameter("contentType")));

		if (subPath.indexOf("..") >= 0 ) throw new Exception("Security Exception - illegal url called.");
		if (physical.indexOf("..") >= 0 ) throw new Exception("Security Exception - illegal url called.");

		String ext = "";
		if ( physical.lastIndexOf(".") > 0 )
			ext = physical.substring(physical.lastIndexOf(".") + 1,physical.length()).toLowerCase();
		if ( ext == null ) throw new FileNotFoundException();

		if ( extWhiteList.indexOf(ext) >= 0 )
			EgovFormBasedFileUtil.viewFile(response, uploadDir, subPath, physical, mimeType);
		else
			System.out.println("@@@@@ 타나3");
			throw new FileNotFoundException();
	}

	/**
	 * 복호화
	 *
	 * @param decrypt
	 * @return
	 */
	private String decrypt(String decrypt){

		try {
			return cryptoService.decrypt(decrypt); // Handles URLDecoding.
//			return cryptoService.decryptNone(decrypt); // Does not handle URLDecoding.
		} catch(IllegalArgumentException e) {
			LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Running : "+ e.getMessage());
		}
		return decrypt;
	}

	@ResponseBody
	@PostMapping("/upload/images")
	public Map<String,Object> imageUpload(@RequestParam Map<String, Object> paramMap, MultipartRequest request) throws IOException {

		MultipartFile uploadFile = request.getFile("upload");
		if (uploadFile == null || uploadFile.isEmpty()) {
			paramMap.put("error", "File is empty or not provided.");
			return paramMap;
		}
		String uploadDir = "C:/upload/images";
		String uploadId = UUID.randomUUID() + "." + uploadFile.getOriginalFilename();

		File folder = new File(uploadDir);
		if(!folder.exists()){
			folder.mkdirs();
		}

		uploadFile.transferTo(new File(uploadDir,uploadId));

		paramMap.put("url", "/upload/ckFile/"+uploadId);

		return paramMap;
	}
}