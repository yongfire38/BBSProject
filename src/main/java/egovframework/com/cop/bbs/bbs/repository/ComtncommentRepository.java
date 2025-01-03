package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtncomment;
import egovframework.com.cop.bbs.bbs.entity.ComtncommentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComtncommentRepository extends JpaRepository<Comtncomment, ComtncommentId> {
    Page<Comtncomment> findAllByComtncommentId_BbsIdAndComtncommentId_NttIdAndUseAt(String bbsId, Long nttId, String useAt, Pageable pageable);
    List<Comtncomment> findAllByComtncommentId_BbsIdAndComtncommentId_NttId(String bbsId, Long nttId);
}
