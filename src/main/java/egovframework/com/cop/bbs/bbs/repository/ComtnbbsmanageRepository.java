package egovframework.com.cop.bbs.bbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egovframework.com.cop.bbs.bbs.entity.Comtnbbsmanage;

@Repository
public interface ComtnbbsmanageRepository extends JpaRepository<Comtnbbsmanage, String> {
	// SYNC_STTUS_CODE가 'N'또는 'E'인 데이터를 10건 가져오기 (등록 순서대로)
	List<Comtnbbsmanage> findTop10BySyncSttusCodeInOrderByRegistPnttmAsc(List<String> syncSttusCodes);
}
