package egovframework.com.cop.bbs.bbs.web;

import egovframework.com.cop.bbs.bbs.service.BoardVO;
import egovframework.com.cop.bbs.bbs.service.EgovFileMngService;
import egovframework.com.cop.bbs.bbs.service.FileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class EgovFileMngController {

    public final EgovFileMngService fileMngService;

    @PostMapping("/cmm/fms/selectFileInfs")
    public ResponseEntity<List<FileVO>> selectFileInfs(@RequestParam("atchFileId") String atchFileId) throws IOException {
        List<FileVO> response = fileMngService.selectFileInfs(atchFileId);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/cmm/fms/deleteFileInfs")
    public void  deleteFileInfs(FileVO fileVO){
        String id = "";
        for(String num : fileVO.getDeleteFileSn()){
            fileVO.setFileSn(num);
            fileMngService.deleteFileInfs(fileVO);
        }
    }

}
