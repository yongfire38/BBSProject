package egovframework.com.cop.bbs.bbs.service.impl;

import egovframework.com.cop.bbs.bbs.entity.ComtnFile;
import egovframework.com.cop.bbs.bbs.entity.Comtnfiledetail;
import egovframework.com.cop.bbs.bbs.entity.ComtnfiledetailId;
import egovframework.com.cop.bbs.bbs.repository.ComtnFileDetailRepository;
import egovframework.com.cop.bbs.bbs.repository.ComtnFileRepository;
import egovframework.com.cop.bbs.bbs.service.EgovFileMngService;
import egovframework.com.cop.bbs.bbs.service.FileVO;
import egovframework.com.utl.AppUtils;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EgovFileMngServiceImpl implements EgovFileMngService {

    private final ComtnFileRepository fileRepository;
    private final ComtnFileDetailRepository fileDetailRepository;

    private final EgovIdGnrService egovFileIdGnrService;

    @Override
    public String insertFileInf(FileVO fvo) throws Exception {

        int fileCount = fileDetailRepository.findAllByComtnfiledetailId_AtchFileId(fvo.getAtchFileId()).size();
        if(fileCount > 0){
            fvo.setFileSn(String.valueOf(fileCount));
        }

        System.out.println("등록할 파일 아이디 >> " + fvo.getAtchFileId());
        System.out.println("추가 등록한 시리얼 >> " + fvo.getFileSn());

        fvo.setCreatDt(LocalDateTime.now().toString());

        ComtnFile comtnFile = new ComtnFile();
        Comtnfiledetail comtnfiledetail = new Comtnfiledetail();
        BeanUtils.copyProperties(fvo,comtnFile);
        BeanUtils.copyProperties(fvo,comtnfiledetail);

        comtnFile.setUseAt("Y");

        ComtnfiledetailId comtnfiledetailId =  new ComtnfiledetailId();
        comtnfiledetailId.setAtchFileId(fvo.getAtchFileId());
        if(fvo.fileSn.isEmpty()){
            comtnfiledetailId.setFileSn("0");
        }else{
            comtnfiledetailId.setFileSn(fvo.getFileSn());
        }
        comtnfiledetail.setComtnfiledetailId(comtnfiledetailId);

        comtnFile.setCreatDt(LocalDateTime.now());

        fileRepository.save(comtnFile);
        fileDetailRepository.save(comtnfiledetail);

        return fvo.getAtchFileId();
    }

    @Override
    public String insertFileInfs(List<FileVO> fvoList) throws Exception {
        String id ="";
        for(int i =0; i<fvoList.size(); i++){
            if(fvoList.get(i).getAtchFileId() != null){
                id=fvoList.get(i).getAtchFileId();
                fvoList.get(i).setAtchFileId(id);
                fvoList.get(i).setFileSn(String.valueOf(i));
            }
            id = insertFileInf(fvoList.get(i));
        }
        return id;
    }

    @Override
    public List<FileVO> selectFileInfs(String atchFileId) {
        return fileDetailRepository.findAllByComtnfiledetailId_AtchFileId(atchFileId).stream().map(AppUtils::fileDetailEntityToVO).collect(Collectors.toList());
    }

    @Override
    public void deleteFileInfs(FileVO fileVO) {
        ComtnfiledetailId comtnfiledetailId = new ComtnfiledetailId();
        comtnfiledetailId.setAtchFileId(fileVO.getAtchFileId());
        comtnfiledetailId.setFileSn(fileVO.getFileSn());
        fileDetailRepository.deleteById(comtnfiledetailId);

        int size = fileDetailRepository.findAllByComtnfiledetailId_AtchFileId(fileVO.getAtchFileId()).size();

        if(size == 0){
            ComtnFile comtnFile = fileRepository.findById(fileVO.getAtchFileId()).get();
            comtnFile.setUseAt("N");
            fileRepository.save(comtnFile);
        }
    }
}
