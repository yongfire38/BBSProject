package egovframework.com.cop.bbs.bbs.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.data.domain.Page;

public interface EgovArticleCommentService {
    public Page<CommentVO> selectArticleCommentList(CommentVO commentVO);
    public void insertArticleComment(CommentVO commentVO) throws FdlException;
    public void deleteArticleComment(CommentVO commentVO);
}
