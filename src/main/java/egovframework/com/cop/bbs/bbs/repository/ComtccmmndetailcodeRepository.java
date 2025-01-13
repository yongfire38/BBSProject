package egovframework.com.cop.bbs.bbs.repository;

import egovframework.com.cop.bbs.bbs.entity.Comtccmmndetailcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComtccmmndetailcodeRepository extends JpaRepository<Comtccmmndetailcode, String> {

    List<Comtccmmndetailcode> findByUseAtAndComtccmmndetailcodeId_CodeId(String useAt, String codeId);
}
