package egovframework.com.cop.bbs.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BBSMasterListDTO {
    private String bbsId;
    private String bbsTyCode;
    private String codeNm;
    private String bbsNm;
    private String tmplatId;
    private String useAt;
    private String frstRegistPnttm;
    private String frstRegisterNm;

    public BBSMasterListDTO(String bbsId, String bbsTyCode, String codeNm, String bbsNm, String tmplatId, String useAt, LocalDateTime frstRegistPnttm, String frstRegisterNm) {
        this.bbsId = bbsId;
        this.bbsTyCode = bbsTyCode;
        this.codeNm = codeNm;
        this.bbsNm = bbsNm;
        this.tmplatId = tmplatId;
        this.useAt = useAt;
        this.setFrstRegistPnttm(frstRegistPnttm);
        this.frstRegisterNm = frstRegisterNm;
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getBbsTyCode() {
        return bbsTyCode;
    }

    public void setBbsTyCode(String bbsTyCode) {
        this.bbsTyCode = bbsTyCode;
    }

    public String getCodeNm() {
        return codeNm;
    }

    public void setCodeNm(String codeNm) {
        this.codeNm = codeNm;
    }

    public String getBbsNm() {
        return bbsNm;
    }

    public void setBbsNm(String bbsNm) {
        this.bbsNm = bbsNm;
    }

    public String getTmplatId() {
        return tmplatId;
    }

    public void setTmplatId(String tmplatId) {
        this.tmplatId = tmplatId;
    }

    public String getUseAt() {
        return useAt;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
    }

    public String getFrstRegistPnttm() {
        return frstRegistPnttm;
    }

    public void setFrstRegistPnttm(LocalDateTime frstRegistPnttm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        this.frstRegistPnttm = frstRegistPnttm.format(formatter);
    }

    public String getFrstRegisterNm() {
        return frstRegisterNm;
    }

    public void setFrstRegisterNm(String frstRegisterNm) {
        this.frstRegisterNm = frstRegisterNm;
    }
}
