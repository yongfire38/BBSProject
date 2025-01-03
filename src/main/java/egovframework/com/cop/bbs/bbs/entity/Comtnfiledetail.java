package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "COMTNFILEDETAIL")
public class Comtnfiledetail {

    @EmbeddedId
    ComtnfiledetailId comtnfiledetailId;

    @Column(name="FILE_STRE_COURS")
    private String fileStreCours;
    @Column(name="STRE_FILE_NM")
    private String streFileNm;
    @Column(name="ORIGNL_FILE_NM")
    private String orignlFileNm;
    @Column(name="FILE_EXTSN")
    private String fileExtsn;
    @Column(name="FILE_CN")
    private String fileCn;
    @Column(name="FILE_SIZE")
    private Long fileSize;

}
