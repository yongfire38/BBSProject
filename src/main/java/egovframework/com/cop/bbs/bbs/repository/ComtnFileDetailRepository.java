package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtnfiledetail;
import egovframework.com.cop.bbs.bbs.entity.ComtnfiledetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComtnFileDetailRepository extends JpaRepository<Comtnfiledetail, ComtnfiledetailId> {

    public List<Comtnfiledetail> findAllByComtnfiledetailId_AtchFileId(String atchFileId);
}
