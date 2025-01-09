package egovframework.com.cop.bbs.bbs.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import egovframework.com.cop.bbs.bbs.entity.Comtnbbs;
import egovframework.com.cop.bbs.bbs.entity.ComtnbbsId;
import egovframework.com.cop.bbs.bbs.entity.Comtncomment;
import egovframework.com.cop.bbs.bbs.event.BoardEvent;
import egovframework.com.cop.bbs.bbs.event.BoardEventType;
import egovframework.com.cop.bbs.bbs.repository.ComtnbbsRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtnbbsmasteroptnRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtncommentRepository;
import egovframework.com.cop.bbs.bbs.service.BBSDTO;
import egovframework.com.cop.bbs.bbs.service.BBSListDTO;
import egovframework.com.cop.bbs.bbs.service.Board;
import egovframework.com.cop.bbs.bbs.service.BoardEventPublisher;
import egovframework.com.cop.bbs.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.bbs.service.BoardVO;
import egovframework.com.cop.bbs.bbs.service.EgovArticleService;
import egovframework.com.utl.AppUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EgovArticleServiceImpl implements EgovArticleService {

    private final ComtnbbsRepository comtnbbsRepository;
    private final ComtncommentRepository comtncommentRepository;
    private final ComtnbbsmasteroptnRepository comtnbbsmasteroptnRepository;
    private final BoardEventPublisher boardEventPublisher;

    @Qualifier("egovArticleIdGnrService")
    private final EgovIdGnrService idgenServiceArticle;

    public EgovArticleServiceImpl(ComtnbbsRepository comtnbbsRepository, ComtncommentRepository comtncommentRepository, ComtnbbsmasteroptnRepository comtnbbsmasteroptnRepository, 
    		BoardEventPublisher boardEventPublisher,
    		@Qualifier("egovArticleIdGnrService") EgovIdGnrService idgenServiceArticle
    		) {
        this.comtnbbsRepository = comtnbbsRepository;
        this.comtncommentRepository = comtncommentRepository;
        this.comtnbbsmasteroptnRepository = comtnbbsmasteroptnRepository;
		this.boardEventPublisher = boardEventPublisher;
        this.idgenServiceArticle = idgenServiceArticle;
    }

    @Override
    public List<BBSListDTO> seletArticleNotice(BoardMasterVO boardMasterVO){
        List<BBSListDTO> noticeList = comtnbbsRepository.selectArticleNotice(boardMasterVO.getBbsId(),boardMasterVO.getSearchCnd(),boardMasterVO.getSearchWrd());
        return noticeList;
    }

    @Override
    public Map<String, Object> selectArticleList(BoardMasterVO boardMasterVO) {
        Pageable pageable = PageRequest.of(boardMasterVO.getFirstIndex() > 0 ? boardMasterVO.getFirstIndex() : 0, boardMasterVO.getPageUnit());

        Page<BBSListDTO> bbsListDTOPage  = comtnbbsRepository.selectArticleList(boardMasterVO.getBbsId(), boardMasterVO.getSearchCnd(), boardMasterVO.getSearchWrd(), pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", bbsListDTOPage.getContent());
        response.put("number", bbsListDTOPage.getNumber());
        response.put("size", bbsListDTOPage.getSize());
        response.put("totalElements", bbsListDTOPage.getTotalElements());
        response.put("totalPages", bbsListDTOPage.getTotalPages());
        response.put("hasPrevious", bbsListDTOPage.hasPrevious());
        response.put("hasNext", bbsListDTOPage.hasNext());

        return response;
    }

    @Override
    @Transactional
    public BBSDTO selectArticleDetail(Board board) {
        int iniqireCo = comtnbbsRepository.selectMaxInqireCo(board.getBbsId(), board.getNttId());
        board.setInqireCo(iniqireCo);
        LocalDateTime lastUpdtPnttm = LocalDateTime.now();
        int result = comtnbbsRepository.updateInqireCo(iniqireCo, board.getLastUpdusrId(), lastUpdtPnttm, board.getBbsId(), board.getNttId());
        if (result <= 0) {
            throw new RuntimeException("Failed to update inquiry count for article. No records were updated.");
        }

        // 게시글 옵션
        String answerAt = comtnbbsmasteroptnRepository.findById(board.getBbsId()).get().getAnswerAt();
        String stsfdgAt = comtnbbsmasteroptnRepository.findById(board.getBbsId()).get().getStsfdgAt();
        BBSDTO bbsdto = comtnbbsRepository.selectArticleDetail(board.getBbsId(), board.getNttId());

        return bbsdto;
    }

    @Override
    public void insertArticle(BoardVO boardVO) throws Exception{
    	
    	long oldNttId = boardVO.getNttId();
    	long newNttId = idgenServiceArticle.getNextLongId();
    	
    	try {
    		if ("Y".equals(boardVO.getReplyAt())) {
                BBSDTO bbsdto = comtnbbsRepository.selectArticleDetail(boardVO.getBbsId(), Long.valueOf(boardVO.getParnts()));
                // 답글인 경우 1. Parnts를 세팅, 2.Parnts의 sortOrdr을 현재글의 sortOrdr로 가져오도록, 3.nttNo는 현재 게시판의 순서대로
                // replyLc는 부모글의 ReplyLc + 1
                if(boardVO.getNttId() == 0){
                    boardVO.setSortOrdr(bbsdto.getSortOrdr());
                    boardVO.setReplyLc(String.valueOf(bbsdto.getAnswerLc() + 1));
                    boardVO.setNttNo(comtnbbsRepository.selectNttNo(boardVO.getBbsId(), boardVO.getSortOrdr()));

                    System.out.println("계산 후 LC 번호 >> " + boardVO.getReplyLc());

                    // 답글에 대한 nttId 생성 후 nttId에 set +++
                    //long nttId = idgenServiceArticle.getNextLongId();
                    boardVO.setNttId(newNttId);
                    boardVO.setFrstRegistPnttm(LocalDateTime.now());

                    //더미데이터
                    boardVO.setNtcrNm("테스트1");
                    boardVO.setNtcrId("USRCNFRM_00000000000");

                }

            } else {
                // 답글이 아닌경우 Parnts = 0, replyLc는 = 0, sortOrdr = nttNo(Query에서 처리)
                boardVO.setParnts("0");
                boardVO.setReplyLc("0");
                boardVO.setReplyAt("N");

                if(boardVO.getNttId() == 0){
                    //long nttId = idgenServiceArticle.getNextLongId();
                    boardVO.setNttId(newNttId);
                    boardVO.setFrstRegistPnttm(LocalDateTime.now());

                    //더미데이터
                    boardVO.setNtcrNm("테스트1");
                    boardVO.setNtcrId("USRCNFRM_00000000000");

                }

                boardVO.setSortOrdr(boardVO.getNttId());
            }

            if(oldNttId == newNttId){
                System.out.println("게시글 수정");
                boardVO.setNttId(oldNttId);
                boardVO.setLastUpdusrId("USRCNFRM_00000000000");
                boardVO.setLastUpdtPnttm(LocalDateTime.now());
            }

            /* 익명글 처리 */
            if(boardVO.getAnonymousAt().equals("Y")){
                boardVO.setNtcrId("annoymous");
                boardVO.setNtcrNm("익명");
            }

            //더미데이터
            boardVO.setNtcrNm("테스트1");
            boardVO.setNtcrId("USRCNFRM_00000000000");

            comtnbbsRepository.save(AppUtils.bbsVOToEntity(boardVO));
            
            // 게시글 저장 후 이벤트 발행
            BoardEvent event = BoardEvent.builder()
                    .eventType(BoardEventType.CREATE)
                    .nttId(boardVO.getNttId())
                    .bbsId(boardVO.getBbsId())
                    .nttSj(boardVO.getNttSj())
                    .nttCn(boardVO.getNttCn())
                    .eventDateTime(new Date())
                    .build();
            
            boardEventPublisher.publishBoardEvent(event);
            
            // 이력관리 mySql 테이블에 코드 "N"으로 저장
            /*
            if(boardVO.getNttId() == newNttId){	// 신규 게시글 작성의 경우
                insertToBbsManageInfo(newNttId, boardVO.getBbsId(), "N");
            } else { // 기존 게시글 수정의 경우
                insertToBbsManageInfo(oldNttId, boardVO.getBbsId(), "N");
            }
            */
            
    	} catch (Exception e) {
    		log.error("Error occurred during insertion or indexing:", e);
    		throw e;
    	}
    }

    @Override
    public void updateArticle(BoardMaster boardMaster) {

    }

    @Override
    public void deleteArticle(BoardVO boardVO) {

        ComtnbbsId comtnbbsId = new ComtnbbsId();
        comtnbbsId.setBbsId(boardVO.getBbsId());
        comtnbbsId.setNttId(boardVO.getNttId());

        // 메인(최상위)게시글인 경우
        if(boardVO.getReplyAt() == "N"){
            List<Comtnbbs> comtnbbsList = comtnbbsRepository.findAllByComtnbbsIdAndSortOrdr(comtnbbsId,boardVO.getSortOrdr());
            List<Comtncomment> commentList = comtncommentRepository.findAllByComtncommentId_BbsIdAndComtncommentId_NttId(boardVO.getBbsId(), boardVO.getNttId());
            if(comtnbbsList != null){
                for(int i=0; i<comtnbbsList.size(); i++){
                    comtnbbsList.get(i).setUseAt("N");
                    comtnbbsRepository.save(comtnbbsList.get(i));
                }
            }

            if(commentList != null){
                System.out.println("댓글 있음");
                for(Comtncomment comment : commentList) {
                    comment.setUseAt("N");
                    comtncommentRepository.save(comment);
                }
            }

        }else{  // 답글인 경우
            Comtnbbs bbs = comtnbbsRepository.findById(comtnbbsId).get();
            List<Comtncomment> commentList = comtncommentRepository.findAllByComtncommentId_BbsIdAndComtncommentId_NttId(comtnbbsId.getBbsId(), comtnbbsId.getNttId());
            bbs.setUseAt("N");
            for(Comtncomment comment : commentList){
                comment.setUseAt("N");
                comtncommentRepository.save(comment);
            }
            comtnbbsRepository.save(bbs);
            parntsItem(bbs.getComtnbbsId().getBbsId(),bbs.getComtnbbsId().getNttId());
        }
    }
    
    public void parntsItem(String bbsId, long nttId){
        List<Comtnbbs> rList = comtnbbsRepository.findAllByParntscttNo((int)nttId);
        List<Comtncomment> commentList = comtncommentRepository.findAllByComtncommentId_BbsIdAndComtncommentId_NttId(bbsId,nttId);
        if(rList.isEmpty()){
            System.out.println("더이상 답글 없음");;
        }else{
            for(int i=0; i<rList.size(); i++){
                rList.get(i).setUseAt("N");
                comtnbbsRepository.save(rList.get(i));
                parntsItem(rList.get(i).getComtnbbsId().getBbsId(),rList.get(i).getComtnbbsId().getNttId());
            }
            for(Comtncomment comment : commentList){
                comment.setUseAt("N");
                comtncommentRepository.save(comment);
            }
        }
    }
}
