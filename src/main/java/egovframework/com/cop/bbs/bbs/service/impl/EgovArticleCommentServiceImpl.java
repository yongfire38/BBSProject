package egovframework.com.cop.bbs.bbs.service.impl;

import egovframework.com.cop.bbs.bbs.entity.Comtncomment;
import egovframework.com.cop.bbs.bbs.entity.ComtncommentId;
import egovframework.com.cop.bbs.bbs.repository.ComtncommentRepository;
import egovframework.com.cop.bbs.bbs.service.CommentVO;
import egovframework.com.cop.bbs.bbs.service.EgovArticleCommentService;
import egovframework.com.utl.AppUtils;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EgovArticleCommentServiceImpl implements EgovArticleCommentService {

    private final ComtncommentRepository commentRepository;

    @Qualifier("egovAnswerNoGnrService")
    private final EgovIdGnrService idgenServiceComment;

    public EgovArticleCommentServiceImpl(ComtncommentRepository commentRepository, EgovIdGnrService egovAnswerNoGnrService){
        this.commentRepository = commentRepository;
        this.idgenServiceComment = egovAnswerNoGnrService;
    }

    @Override
    public Page<CommentVO> selectArticleCommentList(CommentVO commentVO) {
        Sort sort = Sort.by(Sort.Direction.DESC,"frstRegistPnttm");
        Pageable pageable = PageRequest.of(commentVO.getFirstIndex(),commentVO.getRecordCountPerPage(),sort);

        return commentRepository.findAllByComtncommentId_BbsIdAndComtncommentId_NttIdAndUseAt(commentVO.getBbsId(), commentVO.getNttId(),"Y" ,pageable).map(AppUtils::commentEntityToVO);
    }

    @Override
    public void insertArticleComment(CommentVO commentVO) throws FdlException {

        // 유저 더미 데이터
        String userId = "USRCNFRM_00000000001";
        String userNm = "테스트1";

        if(commentVO.getAnswerNo() == null){
            Long id = idgenServiceComment.getNextLongId();
            commentVO.setAnswerNo(id);
        }

        if(commentVO.getFrstRegistPnttm() == null){
            commentVO.setFrstRegistPnttm(LocalDateTime.now());
        }else{
            commentVO.setLastUpdtPnttm(LocalDateTime.now());
        }

        commentVO.setUseAt("Y");

        // 더미데이터 추가
        commentVO.setWrterId(userId);
        commentVO.setWrterNm(userNm);
        commentVO.setFrstRegisterId(userId);

        commentRepository.save(AppUtils.commentVOToEntity(commentVO));
    }

    @Override
    public void deleteArticleComment(CommentVO commentVO) {
        ComtncommentId comtncommentId = new ComtncommentId();
        comtncommentId.setBbsId(commentVO.getBbsId());
        comtncommentId.setNttId(commentVO.getNttId());
        comtncommentId.setAnswerNo(commentVO.getAnswerNo());

        Comtncomment comtncomment = commentRepository.findById(comtncommentId).orElse(null);

        if(comtncomment != null){
            // 로그인정보용 더미데이터
            comtncomment.setLastUpdusrId(comtncomment.getFrstRegisterId());

            comtncomment.setLastUpdtPnttm(LocalDateTime.now());
            comtncomment.setUseAt("N");

            commentRepository.save(comtncomment);
        }
    }
}
