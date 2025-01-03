package egovframework.com.cop.bbs.bbs.service;


import org.egovframe.rte.fdl.cmmn.exception.FdlException;

import java.util.Map;

public interface EgovBBSSatisfactionService {
    public Map<String,Object> selectSatisfactionList(SatisfactionVO satisfactionVO);
    public int insertSatisfaction(SatisfactionVO satisfactionVO) throws FdlException;
    public int deleteSatisfaction(String satisfactionNo) ;
}
