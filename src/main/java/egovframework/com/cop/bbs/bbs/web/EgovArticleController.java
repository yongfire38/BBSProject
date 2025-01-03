package egovframework.com.cop.bbs.bbs.web;

import egovframework.com.cop.bbs.bbs.service.Board;
import egovframework.com.cop.bbs.bbs.service.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EgovArticleController {

    @GetMapping("/cop/bbs/bbs/selectArticleListView")
    public String selectArticleList(@RequestParam("bbsId") String bbsId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, Model model) {
        model.addAttribute("bbsId", bbsId);
        model.addAttribute("pageNum", pageNum);
        return "egovframework/com/cop/bbs/bbs/EgovArticleList";
    }

    @GetMapping("/cop/bbs/bbs/selectArticleDetailView")
    public String selectArticleDetail(@RequestParam("bbsId") String bbsId, @RequestParam("nttId") String nttId, Model model) {
        model.addAttribute("bbsId", bbsId);
        model.addAttribute("nttId", nttId);
        return "egovframework/com/cop/bbs/bbs/EgovArticleDetail";
    }

    @GetMapping("/cop/bbs/bbs/insertArticleView")
    public String insertArticle(BoardVO boardVO, Model model) {
        model.addAttribute("bbsId", boardVO.getBbsId());
        model.addAttribute("pageNum", boardVO.getPageIndex());

        // 수정
        if(boardVO.getNttId() != 0){
            model.addAttribute("nttId", boardVO.getNttId());
        }else{
            model.addAttribute("nttId", 0);
        }

        // 답글
        if(boardVO.getReplyAt().equals("Y")){
            System.out.println("답글 >>> " + boardVO.getReplyAt());
            model.addAttribute("answerAt",boardVO.getReplyAt());
            model.addAttribute("parnts",boardVO.getParnts());
        }
        return "egovframework/com/cop/bbs/bbs/EgovArticleRegist";
    }
}
