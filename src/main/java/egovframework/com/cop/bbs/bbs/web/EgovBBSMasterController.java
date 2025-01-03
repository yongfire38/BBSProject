package egovframework.com.cop.bbs.bbs.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EgovBBSMasterController {

    @GetMapping("/cop/bbs/bbs/selectBBSMasterInfsView")
    public String selectBBSMasterList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, Model model) {
        model.addAttribute("pageNum", pageNum);
        return "egovframework/com/cop/bbs/bbs/EgovBBSMasterList";
    }

    @GetMapping("/cop/bbs/bbs/selectBBSMasterDetailView")
    public String selectBBSMasterDetail(@RequestParam("bbsId") String bbsId, Model model) {
        model.addAttribute("bbsId", bbsId);
        return "egovframework/com/cop/bbs/bbs/EgovBBSMasterDetail";
    }

    @GetMapping("/cop/bbs/bbs/insertBBSMasterView")
    public String insertBBSMaster() {
        return "egovframework/com/cop/bbs/bbs/EgovBBSMasterRegist";
    }

    @GetMapping("/cop/bbs/bbs/updateBBSMasterView")
    public String updateBBSMaster(@RequestParam("bbsId") String bbsId, Model model) {
        model.addAttribute("bbsId", bbsId);
        return "egovframework/com/cop/bbs/bbs/EgovBBSMasterUpdt";
    }
}
