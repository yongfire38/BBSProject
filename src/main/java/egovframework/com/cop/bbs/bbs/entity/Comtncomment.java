package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "COMTNCOMMENT")
public class Comtncomment {

    // NTT_ID, BBS_ID, ANSWER_NO
    @EmbeddedId
    private ComtncommentId comtncommentId;

    @Column(name = "WRTER_ID")
    private String wrterId;

    @Column(name = "WRTER_NM")
    private String wrterNm;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "USE_AT")
    private String useAt;

    @Column(name = "FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name = "LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Column(name = "PASSWORD")
    private String password;
}