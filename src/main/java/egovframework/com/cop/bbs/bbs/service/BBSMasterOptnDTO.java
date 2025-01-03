package egovframework.com.cop.bbs.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BBSMasterOptnDTO {
    private String bbsId;
    private String answerAt;
    private String stsfdgAt;
    private String frstRegisterId;
    private String frstRegisterNm;
    private String frstRegistPnttm;

    public BBSMasterOptnDTO(String bbsId, String answerAt, String stsfdgAt, String frstRegisterId, String frstRegisterNm, LocalDateTime frstRegistPnttm) {
        this.bbsId = bbsId;
        this.answerAt = answerAt;
        this.stsfdgAt = stsfdgAt;
        this.frstRegisterId = frstRegisterId;
        this.frstRegisterNm = frstRegisterNm;
        this.setFrstRegistPnttm(frstRegistPnttm);
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getAnswerAt() {
        return answerAt;
    }

    public void setAnswerAt(String answerAt) {
        this.answerAt = answerAt;
    }

    public String getStsfdgAt() {
        return stsfdgAt;
    }

    public void setStsfdgAt(String stsfdgAt) {
        this.stsfdgAt = stsfdgAt;
    }

    public String getFrstRegisterId() {
        return frstRegisterId;
    }

    public void setFrstRegisterId(String frstRegisterId) {
        this.frstRegisterId = frstRegisterId;
    }

    public String getFrstRegisterNm() {
        return frstRegisterNm;
    }

    public void setFrstRegisterNm(String frstRegisterNm) {
        this.frstRegisterNm = frstRegisterNm;
    }

    public String getFrstRegistPnttm() {
        return frstRegistPnttm;
    }

    public void setFrstRegistPnttm(LocalDateTime frstRegistPnttm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        this.frstRegistPnttm = frstRegistPnttm.format(formatter);
    }
}
