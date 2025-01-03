package egovframework.com.cop.bbs.bbs.web;

import egovframework.com.cop.bbs.bbs.pagination.EgovPaginationFormat;
import egovframework.com.cop.bbs.bbs.service.CommentVO;
import egovframework.com.cop.bbs.bbs.service.SatisfactionVO;
import egovframework.com.cop.bbs.bbs.service.impl.EgovBBSSatisfactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class EgovBBSSatisfactionController {

    @Value("${page.size}")
    private int pageSize;
    @Value("${page.unit}")
    private int pageUnit;

    private final EgovBBSSatisfactionServiceImpl bbsSatisfactionService;

    @PostMapping("/cop/stf/selectSatisfactionList")
    public ResponseEntity<?> selectSatisfactionList(SatisfactionVO satisfactionVO){
        pageUnit = 3;
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(satisfactionVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);

        satisfactionVO.setFirstIndex(paginationInfo.getCurrentPageNo()-1);
        satisfactionVO.setLastIndex(paginationInfo.getLastRecordIndex());
        satisfactionVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        Map<String,Object> map = bbsSatisfactionService.selectSatisfactionList(satisfactionVO);
        Page<SatisfactionVO> response = (Page<SatisfactionVO>) map.get("sList");
        paginationInfo.setTotalRecordCount((int)response.getTotalElements());
        EgovPaginationFormat egovPaginationFormat = new EgovPaginationFormat();
        egovPaginationFormat.paginationFormat(paginationInfo, "linkPage");

        Map<String,Object> result = new HashMap<>();
        result.put("response",response);
        result.put("satisAvr",map.get("satisfactionAverage"));

        EgovPaginationFormat paginationFormat = new EgovPaginationFormat();
        String paginationHtml = paginationFormat.paginationFormat(paginationInfo, "linkPage");

        result.put("pagination", paginationHtml);
        result.put("lineNumber", (satisfactionVO.getPageIndex()-1)*satisfactionVO.getPageSize());


        return ResponseEntity.ok(result);
    }

    @PostMapping("/cop/stf/insertSatisfaction")
    public ResponseEntity<?> insertSatisfaction(@Valid SatisfactionVO satisfactionVO, BindingResult bindingResult) throws FdlException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else{
            if(satisfactionVO.getStsfdg() >= 0){
                bbsSatisfactionService.insertSatisfaction(satisfactionVO);
            }
        }

        return ResponseEntity.ok().body("등록이 완료되었습니다.");
    }

    @Transactional
    @PostMapping ("/cop/stf/deleteSatisfaction")
    public ResponseEntity<?> deleteSatisfaction(SatisfactionVO satisfactionVO,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else{
            if(!satisfactionVO.getStsfdgNo().isEmpty()){
                bbsSatisfactionService.deleteSatisfaction(satisfactionVO.getStsfdgNo());
            }
        }
        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
