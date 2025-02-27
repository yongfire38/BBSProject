package egovframework.com.cop.bbs.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egovframework.com.cop.bbs.bbs.entity.Comtnbbssynclog;

@Repository
public interface ComtnbbssynclogRepository extends JpaRepository<Comtnbbssynclog, String> {}
