package egovframework.com.cop.bbs.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BBSListDTO {
    private Long nttId;
    private String nttSj;
    private String nttCn;
    private String frstRegisterId;
    private String frstRegisterNm;
    private String frstRegistPnttm;
    private Integer rdcnt;
    private Integer parntscttNo;
    private String answerAt;
    private Integer answerLc;
    private String useAt;
    private String atchFileId;
    private String bbsId;
    private String ntceBgnde;
    private String ntceEndde;
    private String sjBoldAt;
    private String noticeAt;
    private String secretAt;
    private Long commentCo;
    private String ntcrNm;

    public BBSListDTO(Long nttId, String nttSj, String nttCn, String frstRegisterId, String frstRegisterNm, LocalDateTime frstRegistPnttm, Integer rdcnt, Integer parntscttNo, String answerAt, Integer answerLc, String useAt, String atchFileId, String bbsId, String ntceBgnde, String ntceEndde, String sjBoldAt, String noticeAt, String secretAt, Long commentCo,String ntcrNm) {
        this.nttId = nttId;
        this.nttSj = nttSj;
        this.nttCn = nttCn;
        this.frstRegisterId = frstRegisterId;
        this.frstRegisterNm = frstRegisterNm;
        this.setFrstRegistPnttm(frstRegistPnttm);
        this.rdcnt = rdcnt;
        this.parntscttNo = parntscttNo;
        this.answerAt = answerAt;
        this.answerLc = answerLc;
        this.useAt = useAt;
        this.atchFileId = atchFileId;
        this.bbsId = bbsId;
        this.ntceBgnde = ntceBgnde;
        this.ntceEndde = ntceEndde;
        this.sjBoldAt = sjBoldAt;
        this.noticeAt = noticeAt;
        this.secretAt = secretAt;
        this.commentCo = commentCo;
        this.ntcrNm = ntcrNm;
    }

    public Long getNttId() {
        return nttId;
    }

    public void setNttId(Long nttId) {
        this.nttId = nttId;
    }

    public String getNttSj() {
        return nttSj;
    }

    public void setNttSj(String nttSj) {
        this.nttSj = nttSj;
    }

    public String getNttCn() {
        return nttCn;
    }

    public void setNttCn(String nttCn) {
        this.nttCn = nttCn;
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

    public Integer getRdcnt() {
        return rdcnt;
    }

    public void setRdcnt(Integer rdcnt) {
        this.rdcnt = rdcnt;
    }

    public Integer getParntscttNo() {
        return parntscttNo;
    }

    public void setParntscttNo(Integer parntscttNo) {
        this.parntscttNo = parntscttNo;
    }

    public String getAnswerAt() {
        return answerAt;
    }

    public void setAnswerAt(String answerAt) {
        this.answerAt = answerAt;
    }

    public Integer getAnswerLc() {
        return answerLc;
    }

    public void setAnswerLc(Integer answerLc) {
        this.answerLc = answerLc;
    }

    public String getUseAt() {
        return useAt;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
    }

    public String getAtchFileId() {
        return atchFileId;
    }

    public void setAtchFileId(String atchFileId) {
        this.atchFileId = atchFileId;
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getNtceBgnde() {
        return ntceBgnde;
    }

    public void setNtceBgnde(String ntceBgnde) {
        this.ntceBgnde = ntceBgnde;
    }

    public String getNtceEndde() {
        return ntceEndde;
    }

    public void setNtceEndde(String ntceEndde) {
        this.ntceEndde = ntceEndde;
    }

    public String getSjBoldAt() {
        return sjBoldAt;
    }

    public void setSjBoldAt(String sjBoldAt) {
        this.sjBoldAt = sjBoldAt;
    }

    public String getNoticeAt() {
        return noticeAt;
    }

    public void setNoticeAt(String noticeAt) {
        this.noticeAt = noticeAt;
    }

    public String getSecretAt() {
        return secretAt;
    }

    public void setSecretAt(String secretAt) {
        this.secretAt = secretAt;
    }

    public Long getCommentCo() {
        return commentCo;
    }

    public void setCommentCo(Long commentCo) {
        this.commentCo = commentCo;
    }

    public String getNtcrNm(){return ntcrNm;}

    public void setNtcrNm(String ntcrNm){this.ntcrNm = ntcrNm ;}
}
