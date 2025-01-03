package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtnstsfdg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComtnstsfdgRepository extends JpaRepository<Comtnstsfdg,String> {
    Page<Comtnstsfdg> findAllByBbsIdAndNttIdAndUseAt(String bbsId, Long nttId, String useAt, Pageable pageable);
    List<Comtnstsfdg> findAllByBbsIdAndNttIdAndUseAt(String bbsId, Long nttId, String useAt);

}
