package egovframework.com.utl;

import egovframework.com.cop.bbs.bbs.entity.*;
import egovframework.com.cop.bbs.bbs.service.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class AppUtils {

    public static BoardVO bbsEntityToVO(Comtnbbs bbs){
        BoardVO vo = new BoardVO();
        vo.setBbsId(bbs.getComtnbbsId().getBbsId());
        vo.setNttId(bbs.getComtnbbsId().getNttId());
        BeanUtils.copyProperties(bbs, vo);
        return vo;
    }

    public static Comtnbbs bbsVOToEntity(BoardVO vo){
        Comtnbbs bbs = new Comtnbbs();
        ComtnbbsId id = new ComtnbbsId();
        Comtnbbsmaster mst = new Comtnbbsmaster();
        BeanUtils.copyProperties(vo,bbs);

        id.setBbsId(vo.getBbsId());
        id.setNttId(vo.getNttId());

        mst.setBbsId(vo.getBbsId());

        bbs.setComtnbbsId(id);
        bbs.setComtnbbsmaster(mst);
        bbs.setRdcnt(vo.getInqireCo());
        bbs.setSortOrdr((int) vo.getSortOrdr());
        bbs.setParntscttNo(Integer.valueOf(vo.getParnts()));
        bbs.setAnswerLc(Integer.valueOf(vo.getReplyLc()));
        bbs.setAnswerAt(vo.getReplyAt());
        bbs.setNtcrId(vo.getFrstRegisterId());

        return bbs;
    }

    public static BoardMasterOptnVO bbsmasteroptnEntityToVO(Comtnbbsmasteroptn comtnbbsmasteroptn){
        BoardMasterOptnVO boardMasterOptnVO = new BoardMasterOptnVO();
        BeanUtils.copyProperties(comtnbbsmasteroptn,boardMasterOptnVO);
        return boardMasterOptnVO;
    }

    public static FileVO fileDetailEntityToVO(Comtnfiledetail fileDetail){
        FileVO fvo = new FileVO();
        BeanUtils.copyProperties(fileDetail,fvo);
        fvo.setAtchFileId(fileDetail.getComtnfiledetailId().getAtchFileId());
        fvo.setFileSn(fileDetail.getComtnfiledetailId().getFileSn());
        return fvo;
    }

    public static FileVO fileEntityToVO(ComtnFile comtnFile){
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(comtnFile,fileVO);
        return fileVO;
    }

    public static CommentVO commentEntityToVO(Comtncomment comtncomment){
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comtncomment, commentVO);

        commentVO.setBbsId(comtncomment.getComtncommentId().getBbsId());
        commentVO.setNttId(comtncomment.getComtncommentId().getNttId());
        commentVO.setAnswerNo(comtncomment.getComtncommentId().getAnswerNo());

        return commentVO;
    }

    public static Comtncomment commentVOToEntity(CommentVO commentVO){
        Comtncomment comtncomment = new Comtncomment();
        BeanUtils.copyProperties(commentVO,comtncomment);

        ComtncommentId comtncommentId = new ComtncommentId();
        comtncommentId.setBbsId(commentVO.getBbsId());
        comtncommentId.setNttId(commentVO.getNttId());
        comtncommentId.setAnswerNo(commentVO.getAnswerNo());

        comtncomment.setComtncommentId(comtncommentId);
        return comtncomment;
    }

    public static SatisfactionVO satisfactionEntiyToVO(Comtnstsfdg comtnstsfdg){
        SatisfactionVO satisfactionVO = new SatisfactionVO();
        BeanUtils.copyProperties(comtnstsfdg, satisfactionVO);
        return satisfactionVO;
    }

    public static Comtnstsfdg satisfationVOToEntity(SatisfactionVO satisfactionVO){
        Comtnstsfdg comtnstsfdg = new Comtnstsfdg();
        BeanUtils.copyProperties(satisfactionVO,comtnstsfdg);
        return comtnstsfdg;
    }
    
    public static BoardManageVO bbsManageEntityToVO(Comtnbbsmanage comtnbbsmanage){
    	BoardManageVO boardManageVO = new BoardManageVO();
    	BeanUtils.copyProperties(comtnbbsmanage, boardManageVO);
    	return boardManageVO;
    }
    
    public static Comtnbbsmanage bbsManageVOToEntity(BoardManageVO boardManageVO){
    	Comtnbbsmanage comtnbbsmanage = new Comtnbbsmanage();
    	BeanUtils.copyProperties(boardManageVO, comtnbbsmanage);
    	return comtnbbsmanage;
    } 
}
