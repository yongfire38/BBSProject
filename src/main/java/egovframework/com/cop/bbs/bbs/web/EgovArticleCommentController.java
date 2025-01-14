package egovframework.com.cop.bbs.bbs.web;

import egovframework.com.cop.bbs.bbs.pagination.EgovPaginationFormat;
import egovframework.com.cop.bbs.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.bbs.service.CommentVO;
import egovframework.com.cop.bbs.bbs.service.impl.EgovArticleCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class EgovArticleCommentController {

    private final EgovArticleCommentServiceImpl articleCommentService;
    @Value("${page.size}")
    private int pageSize;
    @Value("${page.unit}")
    private int pageUnit;

    @PostMapping("/cop/cmt/selectArticleCommentList")
    public ResponseEntity<?> selectArticleCommentList(CommentVO commentVO){
        pageUnit = 3;
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(commentVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        commentVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        commentVO.setLastIndex(paginationInfo.getLastRecordIndex());
        commentVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Page<CommentVO> response = articleCommentService.selectArticleCommentList(commentVO);
        paginationInfo.setTotalRecordCount((int)response.getTotalElements());
        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String,Object> result = new HashMap<>();
        result.put("response",response);

        EgovPaginationFormat paginationFormat = new EgovPaginationFormat();
        String paginationHtml = paginationFormat.paginationFormat(paginationInfo, "Comment_linkPage");

        result.put("pagination", paginationHtml);
        result.put("lineNumber", (commentVO.getPageIndex()-1)*commentVO.getPageSize());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/cop/cmt/insertArticleComment")
    public ResponseEntity<?> insertArticleComment(@Valid CommentVO commentVO, BindingResult bindingResult) throws FdlException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if(!commentVO.getAnswer().isEmpty()){
            articleCommentService.insertArticleComment(commentVO);
        }

        return ResponseEntity.ok().body("댓글이 등록되었습니다.");
    }

    @PostMapping("/cop/cmt/deleteArticleComment")
    public ResponseEntity<?> deleteArticleComment(@Valid CommentVO commentVO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        articleCommentService.deleteArticleComment(commentVO);

        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }
}
