package egovframework.com.cop.bbs.bbs.service.impl;

import egovframework.com.cop.bbs.bbs.repository.ComtnstsfdgRepository;
import egovframework.com.cop.bbs.bbs.service.EgovBBSSatisfactionService;
import egovframework.com.cop.bbs.bbs.service.SatisfactionVO;
import egovframework.com.utl.AppUtils;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EgovBBSSatisfactionServiceImpl implements EgovBBSSatisfactionService {

    private final ComtnstsfdgRepository comtnstsfdgRepository;

    @Qualifier("egovStsfdgNoGnrService")
    private final EgovIdGnrService idgenServiceStsfdgNo;

    public EgovBBSSatisfactionServiceImpl(ComtnstsfdgRepository comtnstsfdgRepository, @Qualifier("egovStsfdgNoGnrService") EgovIdGnrService idgenServiceStsfdgNo) {
        this.comtnstsfdgRepository = comtnstsfdgRepository;
        this.idgenServiceStsfdgNo = idgenServiceStsfdgNo;
    }


    @Override
    public Map<String,Object> selectSatisfactionList(SatisfactionVO satisfactionVO) {

        Sort sort = Sort.by(Sort.Direction.DESC,"frstRegistPnttm");
        Pageable pageable = PageRequest.of(satisfactionVO.getFirstIndex(),satisfactionVO.getRecordCountPerPage(),sort);
        Page<SatisfactionVO> sList = comtnstsfdgRepository.findAllByBbsIdAndNttIdAndUseAt(satisfactionVO.getBbsId(), satisfactionVO.getNttId(),"Y",pageable).map(AppUtils::satisfactionEntiyToVO);
        List<SatisfactionVO> cntList = comtnstsfdgRepository.findAllByBbsIdAndNttIdAndUseAt(satisfactionVO.getBbsId(), satisfactionVO.getNttId(),"Y").stream().map(AppUtils::satisfactionEntiyToVO).collect(Collectors.toList());

        // 만족도 평균값
        double cnts = 0;
        for (SatisfactionVO vo : cntList) {
            cnts += vo.getStsfdg();
        }
        double satisfactionAverage = (double) Math.round((cnts/cntList.size()) * 10) /10;

        Map<String,Object> response = new HashMap<>();
        response.put("sList",sList);
        response.put("satisfactionAverage",satisfactionAverage);

        return response;
    }

    @Override
    public int insertSatisfaction(SatisfactionVO satisfactionVO) throws FdlException {
        System.out.println("별점 >> " + satisfactionVO.getStsfdg());
        if(satisfactionVO.getStsfdgNo().isEmpty()){
            Long id = idgenServiceStsfdgNo.getNextLongId();
            satisfactionVO.setStsfdgNo(String.valueOf(id));
        }

        if(satisfactionVO.getFrstRegistPnttm() == null){
            satisfactionVO.setFrstRegistPnttm(LocalDateTime.now());
        }else{
            satisfactionVO.setLastUpdusrPnttm(LocalDateTime.now());
        }

        satisfactionVO.setUseAt("Y");

        // 유저 더미 데이터
        String userId = "USRCNFRM_00000000001";
        String userNm = "테스트1";
        satisfactionVO.setWrterId(userId);
        satisfactionVO.setWrterNm(userNm);
        satisfactionVO.setFrstRegisterId(userId);

        comtnstsfdgRepository.save(AppUtils.satisfationVOToEntity(satisfactionVO));

        return 0;
    }

    @Override
    public int deleteSatisfaction(String satisfactionNo) {

        System.out.println("삭제할 번호 >> "+ satisfactionNo);
        if(!satisfactionNo.isEmpty()){
            comtnstsfdgRepository.deleteById(satisfactionNo);
        }

        return 1;
    }
}
