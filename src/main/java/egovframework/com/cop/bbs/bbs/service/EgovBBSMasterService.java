package egovframework.com.cop.bbs.bbs.service;

import java.util.Map;

public interface EgovBBSMasterService {

    Map<String, Object> selectBBSMasterInfs(BoardMasterVO boardMasterVO);
    BBSMasterDTO selectBBSMasterInf(BoardMasterVO boardMasterVO);
    Map<String, Object> getRegistrationInfo(ComDefaultCodeVO comDefaultCodeVO);
    void insertBBSMasterInf(BoardMaster boardMaster) throws Exception;
    void updateBBSMasterInf(BoardMaster boardMaster);
}
