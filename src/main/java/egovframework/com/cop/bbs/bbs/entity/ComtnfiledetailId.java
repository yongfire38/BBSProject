package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class ComtnfiledetailId implements Serializable {

    @Column(name="ATCH_FILE_ID")
    private String atchFileId;

    @Column(name="FILE_SN")
    private String fileSn;
}
