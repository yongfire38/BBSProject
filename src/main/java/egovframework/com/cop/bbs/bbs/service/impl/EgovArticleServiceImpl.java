package egovframework.com.cop.bbs.bbs.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import egovframework.com.cop.bbs.bbs.entity.*;
import egovframework.com.cop.bbs.bbs.event.BoardEvent;
import egovframework.com.cop.bbs.bbs.event.BoardEventType;
import egovframework.com.cop.bbs.bbs.service.*;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import egovframework.com.cop.bbs.bbs.repository.ComtnbbsRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtnbbsmasteroptnRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtnbbssynclogRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtncommentRepository;
import egovframework.com.utl.AppUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EgovArticleServiceImpl implements EgovArticleService {

    private final ComtnbbsRepository comtnbbsRepository;
    private final ComtncommentRepository comtncommentRepository;
    private final ComtnbbsmasteroptnRepository comtnbbsmasteroptnRepository;
    private final  ComtnbbssynclogRepository syncLogRepository;
    private final StreamBridge streamBridge;

    @Qualifier("egovArticleIdGnrService")
    private final EgovIdGnrService idgenServiceArticle;
    
    @Qualifier("egovSyncIdGnrService")
    private final EgovIdGnrService idgenServiceSync;
    
    public EgovArticleServiceImpl(ComtnbbsRepository comtnbbsRepository, ComtncommentRepository comtncommentRepository, ComtnbbsmasteroptnRepository comtnbbsmasteroptnRepository, ComtnbbssynclogRepository syncLogRepository, StreamBridge streamBridge,
    		@Qualifier("egovArticleIdGnrService") EgovIdGnrService idgenServiceArticle, @Qualifier("egovSyncIdGnrService") EgovIdGnrService idgenServiceSync
    		) {
        this.comtnbbsRepository = comtnbbsRepository;
        this.comtncommentRepository = comtncommentRepository;
        this.comtnbbsmasteroptnRepository = comtnbbsmasteroptnRepository;
        this.syncLogRepository = syncLogRepository;
        this.streamBridge = streamBridge;
        this.idgenServiceArticle = idgenServiceArticle;
        this.idgenServiceSync = idgenServiceSync;
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
//        Comtnbbsmasteroptn comtnbbsmasteroptn = comtnbbsmasteroptnRepository.findById(board.getBbsId()).get();
        BBSDTO bbsdto = comtnbbsRepository.selectArticleDetail(board.getBbsId(), board.getNttId());

        BoardVO boardVO = new BoardVO();
        BeanUtils.copyProperties(bbsdto,boardVO);

        return bbsdto;
    }

    @Override
    public void insertArticle(BoardVO boardVO) throws Exception{
    	
    	long oldNttId = boardVO.getNttId();
    	long newNttId = idgenServiceArticle.getNextLongId();
    	
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
	        
	        // COMTNBBSSYNCLOG에 Pending으로 저장
	        Comtnbbssynclog syncLog = new Comtnbbssynclog();
	        syncLog.setSyncId(idgenServiceSync.getNextStringId());
	        syncLog.setNttId(boardVO.getNttId());
	        syncLog.setBbsId(boardVO.getBbsId());
	        syncLog.setSyncSttusCode("P");  // Pending
	        syncLog.setRegistPnttm(new Date());
	        syncLogRepository.save(syncLog);
        
        try {    
            // 게시글 저장 후 이벤트 발행
            BoardEvent event = BoardEvent.builder()
                    .eventType(BoardEventType.CREATE)
                    .nttId(boardVO.getNttId())
                    .bbsId(boardVO.getBbsId())
                    .nttSj(boardVO.getNttSj())
                    .nttCn(boardVO.getNttCn())
                    .eventDateTime(new Date())
                    .build();
            
            streamBridge.send("searchProducer-out-0", event);
    	}
        catch (Exception e) {
        	log.warn("Failed to send event to RabbitMQ. Event will be processed later via COMTNBBSSYNCLOG: {}", e.getMessage());
        	
        	Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("success", false);
            resultMap.put("message", "게시글은 저장되었으나 검색 서비스 연동이 지연될 수 있습니다.");
    	}
    }

    @Override
    public void updateArticle(BoardMaster boardMaster) {

    }

    @Override
    public void deleteArticle(BoardVO boardVO) throws Exception{

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
        
        // COMTNBBSSYNCLOG에 Pending으로 저장
        Comtnbbssynclog syncLog = new Comtnbbssynclog();
        syncLog.setSyncId(idgenServiceSync.getNextStringId());
        syncLog.setNttId(boardVO.getNttId());
        syncLog.setBbsId(boardVO.getBbsId());
        syncLog.setSyncSttusCode("P");  // Pending
        syncLog.setRegistPnttm(new Date());
        syncLogRepository.save(syncLog);
        
        try {
        	// 게시글 삭제 후 이벤트 발행
        	BoardEvent event = BoardEvent.builder()
        			.eventType(BoardEventType.DELETE)
        			.nttId(boardVO.getNttId())
        			.bbsId(boardVO.getBbsId())
        			.nttSj(boardVO.getNttSj())
        			.nttCn(boardVO.getNttCn())
        			.eventDateTime(new Date())
        			.build();
        
        	streamBridge.send("searchProducer-out-0", event);
        } catch (Exception e) {
        	log.warn("Failed to send event to RabbitMQ. Event will be processed later via COMTNBBSSYNCLOG: {}", e.getMessage());
        	
        	Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("success", false);
            resultMap.put("message", "게시글은 저장되었으나 검색 서비스 연동이 지연될 수 있습니다.");
    	}
    }

    @Override
    public BoardMasterOptnVO selectBBSMasterOptn(String bbsId) {
        Comtnbbsmasteroptn comtnbbsmasteroptn = comtnbbsmasteroptnRepository.findById(bbsId).get();
        return AppUtils.bbsmasteroptnEntityToVO(comtnbbsmasteroptn);
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
