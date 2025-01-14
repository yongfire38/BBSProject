package egovframework.com.cmm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.cop.bbs.bbs.service.FileVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.util.EgovResourceCloseHelper;

/**
 * @author 공통 서비스 개발팀 이삼섭
 * @version 1.0
 * @Class Name  : EgovFileMngUtil.java
 * @Description : 메시지 처리 관련 유틸리티
 * @Modification Information
 *
 *   수정일               수정자            수정내용
 *   ----------   --------   ---------------------------
 *   2009.02.13   이삼섭            최초 생성
 *   2011.08.09   서준식            utl.fcc패키지와 Dependency제거를 위해 getTimeStamp()메서드 추가
 *   2017.03.03   조성원            시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
 *   2020.10.26   신용호            parseFileInf(List<MultipartFile> files ...) 추가
 *   2022.11.11   김혜준            시큐어코딩 처리
 *
 * @see
 * @since 2009. 02. 13
 *
 */
@Component("EgovFileMngUtil")
public class EgovFileMngUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovFileMngUtil.class);

	@Value("${Globals.fileStorePath}")
	private static String FILE_STORE_PATH;

	public static final int BUFF_SIZE = 2048;

	@Resource(name = "egovFileIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 *
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(List<MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = "";
		String atchFileIdString = "";

		if (storePath == null || "".equals(storePath)) {
			storePathString = FILE_STORE_PATH;
		} else {
			storePathString = FILE_STORE_PATH;
		}

		if (atchFileId == null || "".equals(atchFileId)) {
			atchFileIdString = idgenService.getNextStringId();
		} else {
			atchFileIdString = atchFileId;
		}

		File saveFolder = new File(storePath);

		if (!saveFolder.exists() || saveFolder.isFile()) {
			// 2017.03.03 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			if (saveFolder.mkdirs()) {
				LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
			} else {
				LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}

		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;

		for (MultipartFile file : files) {

			String orginFileName = file.getOriginalFilename();
			if (StringUtils.isEmpty(orginFileName)) {
				continue;
			}

			// 2022.11.11 시큐어코딩 처리
			String fileExt = FilenameUtils.getExtension(orginFileName).toUpperCase();
			String newName = KeyStr + getTimeStamp() + fileKey;
			long size = file.getSize();
//			String filePath = storePathString + File.separator + newName;
			file.transferTo(new File(storePath,orginFileName));

			fvo = new FileVO();
			fvo.setFileExtsn(fileExt);
			fvo.setFileStreCours(storePath);
			fvo.setFileSize(size);
			fvo.setOrignlFileNm(orginFileName);
			fvo.setStreFileNm(newName);
			fvo.setAtchFileId(atchFileIdString);
			fvo.setFileSn(String.valueOf(fileKey));

			result.add(fvo);

			fileKey++;
		}

		return result;
	}

	/**
	 * 공통 컴포넌트 utl.fcc 패키지와 Dependency 제거를 위해 내부 메서드로 추가 정의함
	 * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서 17자리의 TIMESTAMP값을 구하는 기능
	 *
	 * @param
	 * @return Timestamp 값
	 * @see
	 */
	private static String getTimeStamp() {

		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(연도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
	}
}
