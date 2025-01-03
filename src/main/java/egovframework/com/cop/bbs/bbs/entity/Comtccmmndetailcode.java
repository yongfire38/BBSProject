package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "COMTCCMMNDETAILCODE")
public class Comtccmmndetailcode {

    // CODE_ID, CODE
    @EmbeddedId
    private ComtccmmndetailcodeId comtccmmndetailcodeId;

    @Column(name = "CODE_NM")
    private String codeNm;

    @Column(name = "CODE_DC")
    private String codeDc;

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
}
