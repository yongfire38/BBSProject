package egovframework.com.cop.bbs.bbs.service.impl;

import egovframework.com.cop.bbs.bbs.entity.Comtccmmndetailcode;
import egovframework.com.cop.bbs.bbs.entity.Comtnbbsmaster;
import egovframework.com.cop.bbs.bbs.entity.Comtnbbsmasteroptn;
import egovframework.com.cop.bbs.bbs.repository.ComtccmmndetailcodeRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtnbbsmasterRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtnbbsmasteroptnRepository;
import egovframework.com.cop.bbs.bbs.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EgovBBSMasterServiceImpl implements EgovBBSMasterService {

    private final ComtnbbsmasterRepository comtnbbsmasterRepository;
    private final ComtccmmndetailcodeRepository comtccmmndetailcodeRepository;
    private final ComtnbbsmasteroptnRepository comtnbbsmasteroptnRepository;

    @Qualifier("egovBBSMstrIdGnrService")
    private final EgovIdGnrService idgenServiceBbs;

    public EgovBBSMasterServiceImpl(
            ComtnbbsmasterRepository comtnbbsmasterRepository, ComtccmmndetailcodeRepository comtccmmndetailcodeRepository,
            ComtnbbsmasteroptnRepository comtnbbsmasteroptnRepository, @Qualifier("egovBBSMstrIdGnrService") EgovIdGnrService idgenServiceBbs) {
        this.comtnbbsmasterRepository = comtnbbsmasterRepository;
        this.comtccmmndetailcodeRepository = comtccmmndetailcodeRepository;
        this.comtnbbsmasteroptnRepository = comtnbbsmasteroptnRepository;
        this.idgenServiceBbs = idgenServiceBbs;
    }

    @Override
    public Map<String, Object> selectBBSMasterInfs(BoardMasterVO boardMasterVO) {

        Pageable pageable = PageRequest.of(boardMasterVO.getFirstIndex() > 0 ? boardMasterVO.getFirstIndex() : 0, boardMasterVO.getPageUnit());

        Page<BBSMasterListDTO> bbsMasterListDTOPage  = comtnbbsmasterRepository.selectBBSMasterInfs(boardMasterVO.getCmmntyId(), boardMasterVO.getSearchCnd(), boardMasterVO.getSearchWrd(), pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", bbsMasterListDTOPage.getContent());
        response.put("number", bbsMasterListDTOPage.getNumber());
        response.put("size", bbsMasterListDTOPage.getSize());
        response.put("totalElements", bbsMasterListDTOPage.getTotalElements());
        response.put("totalPages", bbsMasterListDTOPage.getTotalPages());
        response.put("hasPrevious", bbsMasterListDTOPage.hasPrevious());
        response.put("hasNext", bbsMasterListDTOPage.hasNext());

        return response;
    }

    @Override
    public BBSMasterDTO selectBBSMasterInf(BoardMasterVO boardMasterVO) {

        BBSMasterDTO bbsMasterDTO = comtnbbsmasterRepository.selectBBSMasterDetail(boardMasterVO.getBbsId(), boardMasterVO.getUniqId());

//        if (EgovComponentChecker.hasComponent("EgovBBSCommentService") || EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")) {
        if (true) {                 // 임시 코드
            Optional<Comtnbbsmasteroptn> optionalComtnbbsmasteroptn = comtnbbsmasteroptnRepository.findById(bbsMasterDTO.getBbsId());

            if (optionalComtnbbsmasteroptn.isPresent()) {
                Comtnbbsmasteroptn comtnbbsmasteroptn = optionalComtnbbsmasteroptn.get();

                if (comtnbbsmasteroptn.getAnswerAt().equals("Y")) {
                    bbsMasterDTO.setOption("comment");
                }
                if (comtnbbsmasteroptn.getStsfdgAt().equals("Y")) {
                    bbsMasterDTO.setOption("stsfdg");
                }
            } else {
                bbsMasterDTO.setOption("na"); // 미지정 상태로 수정 가능 (이미 지정된 경우는 수정 불가로 처리)
            }
        }

        return bbsMasterDTO;
    }

    @Override
    public Map<String, Object> getRegistrationInfo(ComDefaultCodeVO comDefaultCodeVO) {
        Map<String, Object> response = new HashMap<>();

        // 게시판 유형 목록
        List<Comtccmmndetailcode> entities = comtccmmndetailcodeRepository.findByUseAtAndComtccmmndetailcodeId_CodeId("Y", comDefaultCodeVO.getCodeId());
        List<ComDefaultCodeVO> codeResult = entities.stream()
                .map(entity -> {
                    ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
                    codeVO.setCodeId(entity.getComtccmmndetailcodeId().getCodeId());
                    codeVO.setCode(entity.getComtccmmndetailcodeId().getCode());
                    codeVO.setCodeNm(entity.getCodeNm());
                    codeVO.setCodeDc(entity.getCodeDc());
                    return codeVO;
                })
                .collect(Collectors.toList());
        response.put("codeResult", codeResult);

        // 컴포넌트 존재 여부
        /*
        response.put("useComment", hasComponent("EgovArticleCommentService"));
        response.put("useSatisfaction", hasComponent("EgovBBSSatisfactionService"));
        */
        // 임시 코드
        response.put("useComment", true);
        response.put("useSatisfaction", true);

        return response;
    }

    @Override
    public void insertBBSMasterInf(BoardMaster boardMaster) throws Exception {

        String bbsId = idgenServiceBbs.getNextStringId() + RandomStringUtils.randomAlphanumeric(10);
        Comtnbbsmaster comtnbbsmaster = new Comtnbbsmaster();

        comtnbbsmaster.setBbsId(bbsId);
        comtnbbsmaster.setBbsTyCode(boardMaster.getBbsTyCode());
        comtnbbsmaster.setBbsNm(boardMaster.getBbsNm());
        comtnbbsmaster.setBbsIntrcn(boardMaster.getBbsIntrcn());
        comtnbbsmaster.setReplyPosblAt(boardMaster.getReplyPosblAt());
        comtnbbsmaster.setFileAtchPosblAt(boardMaster.getFileAtchPosblAt());
        comtnbbsmaster.setAtchPosblFileNumber(boardMaster.getAtchPosblFileNumber());
        comtnbbsmaster.setTmplatId(boardMaster.getTmplatId());
        comtnbbsmaster.setUseAt(boardMaster.getUseAt());
        comtnbbsmaster.setCmmntyId(boardMaster.getCmmntyId());
        comtnbbsmaster.setFrstRegisterId("USRCNFRM_00000000000");               // LoginVO 대체 임시 로직
        comtnbbsmaster.setFrstRegistPnttm(LocalDateTime.now());
        comtnbbsmaster.setBlogId(boardMaster.getBlogId());

        comtnbbsmasterRepository.save(comtnbbsmaster);

        if (boardMaster.getOption().equals("comment") || boardMaster.getOption().equals("stsfdg")) {
            Comtnbbsmasteroptn comtnbbsmasteroptn = new Comtnbbsmasteroptn();

            // 조건부 로직
            if (boardMaster.getOption() == null || boardMaster.getOption().isEmpty()) {
                comtnbbsmasteroptn.setAnswerAt("N");
                comtnbbsmasteroptn.setStsfdgAt("N");
            } else if ("comment".equals(boardMaster.getOption())) {
                comtnbbsmasteroptn.setAnswerAt("Y");
                comtnbbsmasteroptn.setStsfdgAt("N");
            } else if ("stsfdg".equals(boardMaster.getOption())) {
                comtnbbsmasteroptn.setAnswerAt("N");
                comtnbbsmasteroptn.setStsfdgAt("Y");
            }

            comtnbbsmasteroptn.setBbsId(bbsId);
            comtnbbsmasteroptn.setFrstRegisterId("USRCNFRM_00000000000");           // LoginVO 대체 임시 로직
            comtnbbsmasteroptn.setFrstRegistPnttm(comtnbbsmaster.getFrstRegistPnttm());

            comtnbbsmasteroptnRepository.save(comtnbbsmasteroptn);
        }
    }

    @Override
    public void updateBBSMasterInf(BoardMaster boardMaster) {
        Comtnbbsmaster comtnbbsmaster = comtnbbsmasterRepository.findById(boardMaster.getBbsId())
                .orElseThrow(() -> new EntityNotFoundException("Comtnbbsmaster not found with id " + boardMaster.getBbsId()));

        comtnbbsmaster.setBbsId(boardMaster.getBbsId());
        comtnbbsmaster.setBbsTyCode(boardMaster.getBbsTyCode());
        comtnbbsmaster.setBbsNm(boardMaster.getBbsNm());
        comtnbbsmaster.setBbsIntrcn(boardMaster.getBbsIntrcn());
        comtnbbsmaster.setReplyPosblAt(boardMaster.getReplyPosblAt());
        comtnbbsmaster.setFileAtchPosblAt(boardMaster.getFileAtchPosblAt());
        comtnbbsmaster.setAtchPosblFileNumber(boardMaster.getAtchPosblFileNumber());
        comtnbbsmaster.setTmplatId(boardMaster.getTmplatId());
        comtnbbsmaster.setUseAt(boardMaster.getUseAt());
        comtnbbsmaster.setCmmntyId(boardMaster.getCmmntyId());
        comtnbbsmaster.setLastUpdusrId("USRCNFRM_00000000000");               // LoginVO 대체 임시 로직
        comtnbbsmaster.setLastUpdtPnttm(LocalDateTime.now());
        comtnbbsmaster.setBlogId(boardMaster.getBlogId());

        comtnbbsmasterRepository.save(comtnbbsmaster);

        if ("comment".equals(boardMaster.getOption()) || "stsfdg".equals(boardMaster.getOption())) {
            Comtnbbsmasteroptn comtnbbsmasteroptn = comtnbbsmasteroptnRepository.findById(comtnbbsmaster.getBbsId())
                    .orElseGet(Comtnbbsmasteroptn::new);

            // 조건부 로직
            if (boardMaster.getOption() == null || boardMaster.getOption().isEmpty()) {
                comtnbbsmasteroptn.setAnswerAt("N");
                comtnbbsmasteroptn.setStsfdgAt("N");
            } else if ("comment".equals(boardMaster.getOption())) {
                comtnbbsmasteroptn.setAnswerAt("Y");
                comtnbbsmasteroptn.setStsfdgAt("N");
            } else if ("stsfdg".equals(boardMaster.getOption())) {
                comtnbbsmasteroptn.setAnswerAt("N");
                comtnbbsmasteroptn.setStsfdgAt("Y");
            }

            if (comtnbbsmasteroptn.getBbsId() == null) {            // 새로운 객체인 경우
                comtnbbsmasteroptn.setBbsId(comtnbbsmaster.getBbsId());
                comtnbbsmasteroptn.setFrstRegisterId("USRCNFRM_00000000000"); // LoginVO 대체 임시 로직
                comtnbbsmasteroptn.setFrstRegistPnttm(comtnbbsmaster.getFrstRegistPnttm());
            } else {                                                // 기존 객체인 경우
                comtnbbsmasteroptn.setLastUpdusrId("USRCNFRM_00000000000"); // LoginVO 대체 임시 로직
                comtnbbsmasteroptn.setLastUpdtPnttm(comtnbbsmaster.getLastUpdtPnttm());
            }

            comtnbbsmasteroptnRepository.save(comtnbbsmasteroptn);
        }
    }

    public static Map<String, String> formatLocalDateTimeFields(Object dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        Map<String, String> formattedMap = new HashMap<>();

        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // private 필드 접근 허용
            try {
                if (field.getType().equals(LocalDateTime.class)) {
                    LocalDateTime date = (LocalDateTime) field.get(dto);
                    if (date != null) {
                        formattedMap.put(field.getName(), date.format(formatter));
                    }
                } else {
                    Object value = field.get(dto);
                    if (value != null) {
                        formattedMap.put(field.getName(), value.toString());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return formattedMap;
    }
}
