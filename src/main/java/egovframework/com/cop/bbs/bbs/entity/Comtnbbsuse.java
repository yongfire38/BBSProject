package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "COMTNBBSUSE")
public class Comtnbbsuse {

    // BBS_ID, TRGET_ID
    @EmbeddedId
    private ComtnbbsuseId comtnbbsuseId;

    @ManyToOne
    @MapsId("bbsId")
    @JoinColumn(name = "BBS_ID", referencedColumnName = "BBS_ID")
    private Comtnbbsmaster comtnbbsmaster;

    @Column(name = "USE_AT")
    private String useAt;  // 사용 여부

    @Column(name = "REGIST_SE_CODE")
    private String registSeCode;  // 등록 구분 코드

    @Column(name = "FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;  // 최초 등록 시간

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;  // 최초 등록자 ID

    @Column(name = "LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;  // 최종 수정 시간

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;  // 최종 수정자 ID
}
