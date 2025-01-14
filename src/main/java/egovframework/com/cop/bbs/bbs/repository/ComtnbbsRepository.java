package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtnbbs;
import egovframework.com.cop.bbs.bbs.entity.ComtnbbsId;
import egovframework.com.cop.bbs.bbs.service.BBSDTO;
import egovframework.com.cop.bbs.bbs.service.BBSListDTO;
import egovframework.com.cop.bbs.bbs.service.Board;
import egovframework.com.cop.bbs.bbs.service.BoardVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComtnbbsRepository extends JpaRepository<Comtnbbs, ComtnbbsId> {

    @Query( "SELECT new egovframework.com.cop.bbs.bbs.service.BBSListDTO(b.comtnbbsId.nttId, b.nttSj, b.nttCn, b.frstRegisterId, COALESCE(u.userNm, b.ntcrNm) AS frstRegisterNm, b.frstRegistPnttm, b.rdcnt, b.parntscttNo, b.answerAt, b.answerLc, " +
            "b.useAt, b.atchFileId, b.comtnbbsId.bbsId, b.ntceBgnde, b.ntceEndde, b.sjBoldAt, b.noticeAt, b.secretAt, COUNT(c) AS commentCo, b.ntcrNm) " +
            "FROM Comtnbbs b " +
            "LEFT JOIN Comvnusermaster u ON b.frstRegisterId = u.esntlId " +
            "LEFT JOIN Comtncomment c ON b.comtnbbsId.nttId = c.comtncommentId.nttId AND b.comtnbbsId.bbsId = c.comtncommentId.bbsId AND c.useAt = 'Y' " +
            "WHERE b.comtnbbsId.bbsId = :bbsId AND (b.noticeAt='N' OR b.noticeAt=null) " +
            "AND b.useAt = 'Y' " +
            "AND ((:searchCnd = '0' AND b.nttSj LIKE CONCAT('%', :searchWrd, '%')) " +
            "OR (:searchCnd = '1' AND b.nttCn LIKE CONCAT('%', :searchWrd, '%')) " +
            "OR (:searchCnd = '2' AND u.userNm LIKE CONCAT('%', :searchWrd, '%'))) " +
            "GROUP BY b.comtnbbsId.nttId, b.nttSj, u.userNm, b.ntcrNm, b.frstRegistPnttm, b.rdcnt, b.parntscttNo, b.answerAt, b.answerLc, b.useAt, b.atchFileId, b.comtnbbsId.bbsId, b.ntceBgnde, b.ntceEndde, b.sjBoldAt, b.noticeAt, b.secretAt " +
            "ORDER BY b.sortOrdr DESC,b.parntscttNo ASC,b.answerLc ASC, b.nttNo ASC" )
    Page<BBSListDTO> selectArticleList(@Param("bbsId") String bbsId, @Param("searchCnd") String searchCnd, @Param("searchWrd") String searchWrd, Pageable pageable);

    @Query( "SELECT new egovframework.com.cop.bbs.bbs.service.BBSListDTO(b.comtnbbsId.nttId, b.nttSj, b.nttCn, b.frstRegisterId, COALESCE(u.userNm, b.ntcrNm) AS frstRegisterNm, b.frstRegistPnttm, b.rdcnt, b.parntscttNo, b.answerAt, b.answerLc, " +
            "b.useAt, b.atchFileId, b.comtnbbsId.bbsId, b.ntceBgnde, b.ntceEndde, b.sjBoldAt, b.noticeAt, b.secretAt, COUNT(c) AS commentCo,b.ntcrNm) " +
            "FROM Comtnbbs b " +
            "LEFT JOIN Comvnusermaster u ON b.frstRegisterId = u.esntlId " +
            "LEFT JOIN Comtncomment c ON b.comtnbbsId.nttId = c.comtncommentId.nttId AND b.comtnbbsId.bbsId = c.comtncommentId.bbsId AND c.useAt = 'Y' " +
            "WHERE b.comtnbbsId.bbsId = :bbsId AND b.noticeAt='Y' " +
            "AND b.useAt = 'Y' " +
            "AND ((:searchCnd = '0' AND b.nttSj LIKE CONCAT('%', :searchWrd, '%')) " +
            "OR (:searchCnd = '1' AND b.nttCn LIKE CONCAT('%', :searchWrd, '%')) " +
            "OR (:searchCnd = '2' AND u.userNm LIKE CONCAT('%', :searchWrd, '%'))) " +
            "GROUP BY b.comtnbbsId.nttId, b.nttSj, u.userNm, b.ntcrNm, b.frstRegistPnttm, b.rdcnt, b.parntscttNo, b.answerAt, b.answerLc, b.useAt, b.atchFileId, b.comtnbbsId.bbsId, b.ntceBgnde, b.ntceEndde, b.sjBoldAt, b.noticeAt, b.secretAt " +
            "ORDER BY b.sortOrdr DESC, b.nttNo ASC" )
    List<BBSListDTO> selectArticleNotice(@Param("bbsId") String bbsId, @Param("searchCnd") String searchCnd, @Param("searchWrd") String searchWrd);

    @Query( "SELECT COALESCE(MAX(c.rdcnt), 0) + 1 FROM Comtnbbs c WHERE c.comtnbbsId.bbsId = :bbsId AND c.comtnbbsId.nttId = :nttId" )
    int selectMaxInqireCo(@Param("bbsId") String bbsId, @Param("nttId") Long nttId);

    @Modifying
    @Query("UPDATE Comtnbbs c SET c.rdcnt = :inqireCo, c.lastUpdusrId = :lastUpdusrId, c.lastUpdtPnttm = :lastUpdtPnttm WHERE c.comtnbbsId.bbsId = :bbsId AND c.comtnbbsId.nttId = :nttId")
    int updateInqireCo(@Param("inqireCo") int inqireCo, @Param("lastUpdusrId") String lastUpdusrId, @Param("lastUpdtPnttm") LocalDateTime lastUpdtPnttm, @Param("bbsId") String bbsId, @Param("nttId") Long nttId);

    @Query( "SELECT new egovframework.com.cop.bbs.bbs.service.BBSDTO(a.nttSj, a.ntcrId, a.ntcrNm, a.nttNo, a.nttCn, a.password, a.frstRegisterId, COALESCE(b.userNm, a.ntcrNm), a.frstRegistPnttm, a.ntceBgnde, a.ntceEndde, a.rdcnt, " +
            "a.useAt, a.atchFileId, a.comtnbbsId.bbsId, a.comtnbbsId.nttId, a.sjBoldAt, a.noticeAt, a.secretAt, a.parntscttNo, a.answerAt, a.answerLc, a.sortOrdr, c.bbsTyCode, c.replyPosblAt, c.fileAtchPosblAt, c.atchPosblFileNumber, c.bbsNm) " +
            "FROM Comtnbbs a " +
            "LEFT JOIN Comvnusermaster b ON a.frstRegisterId = b.esntlId " +
            "LEFT JOIN a.comtnbbsmaster c " +
            "WHERE a.comtnbbsId.bbsId = :bbsId " +
            "AND a.comtnbbsId.nttId = :nttId " +
            "AND a.useAt = 'Y'" )
    BBSDTO selectArticleDetail(@Param("bbsId") String bbsId, @Param("nttId") Long nttId);


    @Query("SELECT IFNULL(MAX(c.sortOrdr),0)+1 AS nttNo FROM Comtnbbs c WHERE c.comtnbbsId.bbsId = :bbsId")
    int selectSortOrder(@Param("bbsId") String bbsId);

    @Query("SELECT IFNULL(MAX(b.nttNo),0)+1 AS NTT_NO  FROM Comtnbbs b WHERE b.comtnbbsId.bbsId = :bbsId AND b.sortOrdr = :sortOrdr")
    long selectNttNo(@Param("bbsId") String bbsId, @Param("sortOrdr") long sortOrdr);

    List<Comtnbbs> findAllByComtnbbsIdAndSortOrdr(ComtnbbsId comtnbbsId,long SortOrdr);
    List<Comtnbbs> findAllByParntscttNo(int parntscttNo);
}