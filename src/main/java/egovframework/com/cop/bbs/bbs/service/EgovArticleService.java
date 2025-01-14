package egovframework.com.cop.bbs.bbs.service;

import java.util.List;
import java.util.Map;

public interface EgovArticleService {

    Map<String, Object> selectArticleList(BoardMasterVO boardMasterVO);
    List<BBSListDTO> seletArticleNotice(BoardMasterVO boardMasterVO);
    BBSDTO selectArticleDetail(Board board);
    void insertArticle(BoardVO boardVO) throws Exception;
    void updateArticle(BoardMaster boardMaster);
    void deleteArticle(BoardVO boardVO);
    BoardMasterOptnVO selectBBSMasterOptn(String bbsId);
}
