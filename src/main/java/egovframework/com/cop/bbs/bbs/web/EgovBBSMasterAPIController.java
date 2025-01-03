package egovframework.com.cop.bbs.bbs.web;

import egovframework.com.cop.bbs.bbs.service.*;
import egovframework.com.cop.bbs.bbs.pagination.EgovPaginationFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EgovBBSMasterAPIController {

    private final EgovBBSMasterService egovBBSMasterService;

    @Value("${page.size}")
    private int pageSize;
    @Value("${page.unit}")
    private int pageUnit;

    @GetMapping("/cop/bbs/bbs/selectBBSMasterInfs")
    public ResponseEntity<?> selectBBSMasterList(
            @RequestParam(value = "searchCnd") String searchCnd,
            @RequestParam(value = "searchWrd") String searchWrd,
            @RequestParam(value = "pageIndex") int pageIndex) {

        BoardMasterVO boardMasterVO = new BoardMasterVO();
        boardMasterVO.setSearchCnd(searchCnd);
        boardMasterVO.setSearchWrd(searchWrd);
        boardMasterVO.setPageIndex(pageIndex);
        boardMasterVO.setFirstIndex(pageIndex - 1);
        boardMasterVO.setPageUnit(pageUnit);

        Map<String, Object> response = egovBBSMasterService.selectBBSMasterInfs(boardMasterVO);

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(pageIndex);
        paginationInfo.setRecordCountPerPage(pageUnit);
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(((Long) response.get("totalElements")).intValue());

        EgovPaginationFormat paginationFormat = new EgovPaginationFormat();
        String paginationHtml = paginationFormat.paginationFormat(paginationInfo, "fn_egov_search_bbssj");

        response.put("paginationHtml", paginationHtml);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cop/bbs/bbs/selectBBSMasterDetail")
    public ResponseEntity<?> selectBBSMasterDetail(@RequestParam(value = "bbsId") String bbsId) {
        BoardMasterVO boardMasterVO = new BoardMasterVO();
        boardMasterVO.setBbsId(bbsId);
        BBSMasterDTO bbsMasterDTO = egovBBSMasterService.selectBBSMasterInf(boardMasterVO);
        return ResponseEntity.ok(bbsMasterDTO);
    }

    @GetMapping("/cop/bbs/bbs/boardSetupInfo")
    public ResponseEntity<?> getRegistrationInfo() {
        ComDefaultCodeVO comDefaultCodeVO = new ComDefaultCodeVO();
        comDefaultCodeVO.setCodeId("COM101");
        return ResponseEntity.ok(egovBBSMasterService.getRegistrationInfo(comDefaultCodeVO));
    }

    @PostMapping("/cop/bbs/bbs/insertBBSMaster")
    public ResponseEntity<?> insertBBSMaster(@RequestBody @Valid BoardMaster boardMaster, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if (true) {                         // 사용자 인증 로직 추가 필요
            egovBBSMasterService.insertBBSMasterInf(boardMaster);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("게시판이 성공적으로 등록되었습니다.");
    }

    @PostMapping("/cop/bbs/bbs/updateBBSMaster")
    public ResponseEntity<?> updateBBSMaster(@RequestBody @Valid BoardMaster boardMaster, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if (true) {                         // 사용자 인증 로직 추가 필요
            egovBBSMasterService.updateBBSMasterInf(boardMaster);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("게시판이 성공적으로 수정되었습니다.");
    }
}
