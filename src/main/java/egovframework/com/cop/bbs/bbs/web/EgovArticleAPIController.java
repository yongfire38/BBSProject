package egovframework.com.cop.bbs.bbs.web;

import egovframework.com.cmm.util.EgovFileMngUtil;
import egovframework.com.cop.bbs.bbs.pagination.EgovPaginationFormat;
import egovframework.com.cop.bbs.bbs.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.faces.annotation.RequestMap;
import javax.faces.annotation.RequestParameterMap;
import javax.validation.Valid;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EgovArticleAPIController {

    private final EgovArticleService egovArticleService;
    private final EgovFileMngService fileMngService;
    private final EgovFileMngUtil fileMngUtil;

    @Value("${page.size}")
    private int pageSize;
    @Value("${page.unit}")
    private int pageUnit;

    @GetMapping("/cop/bbs/bbs/selectArticleList")
    public ResponseEntity<?> selectArticleList(
            @RequestParam(value = "bbsId") String bbsId,
            @RequestParam(value = "searchCnd") String searchCnd,
            @RequestParam(value = "searchWrd") String searchWrd,
            @RequestParam(value = "pageIndex") int pageIndex) {

        BoardMasterVO boardMasterVO = new BoardMasterVO();
        boardMasterVO.setBbsId(bbsId);
        boardMasterVO.setSearchCnd(searchCnd);
        boardMasterVO.setSearchWrd(searchWrd);
        boardMasterVO.setPageIndex(pageIndex);
        boardMasterVO.setFirstIndex(pageIndex - 1);
        boardMasterVO.setPageUnit(pageUnit);

        List<BBSListDTO> noticeList = egovArticleService.seletArticleNotice(boardMasterVO);
        int noticeCnt = noticeList.size();
        if(noticeCnt != 0 ){
            boardMasterVO.setPageUnit(pageUnit - noticeCnt);
        }


        Map<String, Object> response = egovArticleService.selectArticleList(boardMasterVO);

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(pageIndex);
        paginationInfo.setRecordCountPerPage(pageUnit - noticeCnt);
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(((Long) response.get("totalElements")).intValue());

        EgovPaginationFormat paginationFormat = new EgovPaginationFormat();
        String paginationHtml = paginationFormat.paginationFormat(paginationInfo, "linkPage");

        response.put("paginationHtml", paginationHtml);
        response.put("noticeList", noticeList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cop/bbs/bbs/selectArticleDetail")
    public ResponseEntity<?> selectArticleDetail(@RequestParam(value = "bbsId") String bbsId, @RequestParam(value = "nttId") int nttId) {
        Board board = new Board();
        board.setBbsId(bbsId);
        board.setNttId(nttId);
        return ResponseEntity.ok(egovArticleService.selectArticleDetail(board));
    }

    @ResponseBody
    @PostMapping("/cop/bbs/bbs/insertArticle")
    public ResponseEntity<?> insertArticle(@Valid @ModelAttribute BoardVO boardVO, MultipartHttpServletRequest multiRequest, BindingResult bindingResult) throws Exception {
        String fileAtchId = "";
        List<FileVO> result = null;
        String uploadDir = "C:/upload/files";
        List<MultipartFile> files = multiRequest.getFiles("fileList");

        if(!files.isEmpty()) {
            result = fileMngUtil.parseFileInf(files, "BBS_",0,boardVO.getAtchFileId(),uploadDir);
            fileAtchId = fileMngService.insertFileInfs(result);
            boardVO.setAtchFileId(fileAtchId);
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if (true) {                         // 사용자 인증 로직 추가 필요
            boardVO.setFrstRegisterId("USRCNFRM_00000000000");
            egovArticleService.insertArticle(boardVO);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("게시판이 성공적으로 등록되었습니다.");
    }

    @PostMapping("/cop/bbs/bbs/deleteArticleDetail")
    public ResponseEntity<?> deleteArticle(@ModelAttribute BoardVO boardVO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if(true){
            egovArticleService.deleteArticle(boardVO);
        }

        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }

    @PostMapping("cop/bbs/bbs/selectBBSMasterOptn")
    public ResponseEntity<?> selectBBSMasterOptn(String bbsId){
        BoardMasterOptnVO boardMasterOptnVO = egovArticleService.selectBBSMasterOptn(bbsId);
        return ResponseEntity.ok().body(boardMasterOptnVO);
    }
}