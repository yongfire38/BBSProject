package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtccmmndetailcode;
import egovframework.com.cop.bbs.bbs.entity.ComtccmmndetailcodeId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComtccmmndetailcodeRepository extends JpaRepository<Comtccmmndetailcode, ComtccmmndetailcodeId> {

    List<Comtccmmndetailcode> findByUseAtAndComtccmmndetailcodeId_CodeId(String useAt, String codeId);
}
