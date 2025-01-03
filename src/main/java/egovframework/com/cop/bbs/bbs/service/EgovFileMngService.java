package egovframework.com.cop.bbs.bbs.service;

import java.util.List;

public interface EgovFileMngService {

    // 파일 하나
    public String insertFileInf(FileVO fvo) throws Exception;

    // 파일 두개 이상
    public String insertFileInfs(List<FileVO> fvoList) throws Exception;

    public List<FileVO> selectFileInfs(String atchFileId);

    public void deleteFileInfs(FileVO fileVO);
}
