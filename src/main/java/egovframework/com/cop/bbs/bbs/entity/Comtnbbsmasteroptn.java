package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Comtnbbsmasteroptn {

    @Id
    @Column(name = "BBS_ID")
    private String bbsId;

    @Column(name = "ANSWER_AT")
    private String answerAt;

    @Column(name = "STSFDG_AT")
    private String stsfdgAt;

    @Column(name = "FRST_REGIST_PNTTM")
    private LocalDateTime frstRegistPnttm;

    @Column(name = "LAST_UPDT_PNTTM")
    private LocalDateTime lastUpdtPnttm;

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;
}
