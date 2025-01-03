package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtnbbs;
import egovframework.com.cop.bbs.bbs.entity.Comtnbbsmaster;
import egovframework.com.cop.bbs.bbs.service.BBSMasterDTO;
import egovframework.com.cop.bbs.bbs.service.BBSMasterListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComtnbbsmasterRepository extends JpaRepository<Comtnbbsmaster, String> {

    @Query( "SELECT new egovframework.com.cop.bbs.bbs.service.BBSMasterListDTO(a.bbsId, a.bbsTyCode, b.codeNm, a.bbsNm, a.tmplatId, a.useAt, a.frstRegistPnttm, c.userNm AS frstRegisterNm) " +
            "FROM Comtnbbsmaster a " +
            "LEFT JOIN Comtccmmndetailcode b ON a.bbsTyCode = b.comtccmmndetailcodeId.code AND b.comtccmmndetailcodeId.codeId = 'COM101' AND b.useAt = 'Y' " +
            "LEFT JOIN Comvnusermaster c ON a.frstRegisterId = c.esntlId " +
            "WHERE (:cmmntyId IS NULL OR :cmmntyId = '' OR a.cmmntyId = :cmmntyId) " +
            "AND ((:searchCnd = '0' AND a.bbsNm LIKE %:searchWrd%) " +
            "OR (:searchCnd = '1' AND a.bbsIntrcn LIKE %:searchWrd%)) " +
            "ORDER BY a.frstRegistPnttm DESC" )
    Page<BBSMasterListDTO> selectBBSMasterInfs(@Param("cmmntyId") String cmmntyId, @Param("searchCnd") String searchCnd, @Param("searchWrd") String searchWrd, Pageable pageable);

    @Query( "SELECT new egovframework.com.cop.bbs.bbs.service.BBSMasterDTO(" +
                "a.bbsId, a.bbsTyCode, b.codeNm AS bbsTyCodeNm, a.bbsIntrcn, a.bbsNm, a.tmplatId, d.tmplatNm, d.tmplatCours, a.fileAtchPosblAt, a.atchPosblFileNumber, a.atchPosblFileSize, " +
                "a.replyPosblAt, a.frstRegisterId, e.userNm AS frstRegisterNm, a.cmmntyId, a.useAt, a.frstRegistPnttm, a.blogId, " +
                "(SELECT COALESCE(c.useAt, 'N') FROM Comtnbbsuse c WHERE c.comtnbbsmaster.bbsId = :bbsId AND c.comtnbbsuseId.trgetId IN (:uniqId, 'SYSTEM_DEFAULT_BOARD')) AS authFlag) " +
            "FROM Comtnbbsmaster a " +
            "LEFT JOIN Comtccmmndetailcode b ON a.bbsTyCode = b.comtccmmndetailcodeId.code AND b.comtccmmndetailcodeId.codeId = 'COM101' AND b.useAt = 'Y' " +
            "LEFT JOIN Comtntmplatinfo d ON a.tmplatId = d.tmplatId " +
            "LEFT JOIN Comvnusermaster e ON a.frstRegisterId = e.esntlId " +
            "WHERE a.bbsId = :bbsId" )
    BBSMasterDTO selectBBSMasterDetail(@Param("bbsId") String bbsId, @Param("uniqId") String uniqId);

}
