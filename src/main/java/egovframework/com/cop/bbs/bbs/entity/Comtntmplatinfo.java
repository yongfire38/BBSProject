package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "COMTNTMPLATINFO")
public class Comtntmplatinfo {

    @Id
    @Column(name = "TMPLAT_ID")
    private String tmplatId;            // 템플릿 ID

    @Column(name = "TMPLAT_NM")
    private String tmplatNm;          // 템플릿 이름

    @Column(name = "TMPLAT_COURS")
    private String tmplatCours;       // 템플릿 경로

    @Column(name = "USE_AT")
    private String useAt;             // 사용 여부

    @Column(name = "TMPLAT_SE_CODE")
    private String tmplatSeCode;      // 템플릿 구분 코드

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;    // 최초 등록자 ID

    @Column(name = "FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;  // 최초 등록 시간

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;      // 최종 수정자 ID

    @Column(name = "LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;    // 최종 수정 시간
}
