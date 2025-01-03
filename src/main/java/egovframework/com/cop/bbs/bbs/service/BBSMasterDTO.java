package egovframework.com.cop.bbs.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BBSMasterDTO {

    private String bbsId;
    private String bbsTyCode;
    private String bbsTyCodeNm;
    private String bbsIntrcn;
    private String bbsNm;
    private String tmplatId;
    private String tmplatNm;
    private String tmplatCours;
    private String fileAtchPosblAt;
    private Integer atchPosblFileNumber;
    private Long atchPosblFileSize;
    private String replyPosblAt;
    private String frstRegisterId;
    private String frstRegisterNm;
    private String cmmntyId;
    private String useAt;
    private String frstRegistPnttm;
    private String blogId;
    private String authFlag;
    private String option;

    public BBSMasterDTO(String bbsId, String bbsTyCode, String bbsTyCodeNm, String bbsIntrcn, String bbsNm, String tmplatId, String tmplatNm, String tmplatCours, String fileAtchPosblAt, Integer atchPosblFileNumber, Long atchPosblFileSize, String replyPosblAt, String frstRegisterId, String frstRegisterNm, String cmmntyId, String useAt, LocalDateTime frstRegistPnttm, String blogId, String authFlag) {
        this.bbsId = bbsId;
        this.bbsTyCode = bbsTyCode;
        this.bbsTyCodeNm = bbsTyCodeNm;
        this.bbsIntrcn = bbsIntrcn;
        this.bbsNm = bbsNm;
        this.tmplatId = tmplatId;
        this.tmplatNm = tmplatNm;
        this.tmplatCours = tmplatCours;
        this.fileAtchPosblAt = fileAtchPosblAt;
        this.atchPosblFileNumber = atchPosblFileNumber;
        this.atchPosblFileSize = atchPosblFileSize;
        this.replyPosblAt = replyPosblAt;
        this.frstRegisterId = frstRegisterId;
        this.frstRegisterNm = frstRegisterNm;
        this.cmmntyId = cmmntyId;
        this.useAt = useAt;
        this.setFrstRegistPnttm(frstRegistPnttm);
        this.blogId = blogId;
        this.authFlag = authFlag;
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

    public String getBbsTyCodeNm() {
        return bbsTyCodeNm;
    }

    public void setBbsTyCodeNm(String bbsTyCodeNm) {
        this.bbsTyCodeNm = bbsTyCodeNm;
    }

    public String getBbsIntrcn() {
        return bbsIntrcn;
    }

    public void setBbsIntrcn(String bbsIntrcn) {
        this.bbsIntrcn = bbsIntrcn;
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

    public String getTmplatNm() {
        return tmplatNm;
    }

    public void setTmplatNm(String tmplatNm) {
        this.tmplatNm = tmplatNm;
    }

    public String getTmplatCours() {
        return tmplatCours;
    }

    public void setTmplatCours(String tmplatCours) {
        this.tmplatCours = tmplatCours;
    }

    public String getFileAtchPosblAt() {
        return fileAtchPosblAt;
    }

    public void setFileAtchPosblAt(String fileAtchPosblAt) {
        this.fileAtchPosblAt = fileAtchPosblAt;
    }

    public Integer getAtchPosblFileNumber() {
        return atchPosblFileNumber;
    }

    public void setAtchPosblFileNumber(Integer atchPosblFileNumber) {
        this.atchPosblFileNumber = atchPosblFileNumber;
    }

    public Long getAtchPosblFileSize() {
        return atchPosblFileSize;
    }

    public void setAtchPosblFileSize(Long atchPosblFileSize) {
        this.atchPosblFileSize = atchPosblFileSize;
    }

    public String getReplyPosblAt() {
        return replyPosblAt;
    }

    public void setReplyPosblAt(String replyPosblAt) {
        this.replyPosblAt = replyPosblAt;
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

    public String getCmmntyId() {
        return cmmntyId;
    }

    public void setCmmntyId(String cmmntyId) {
        this.cmmntyId = cmmntyId;
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

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
