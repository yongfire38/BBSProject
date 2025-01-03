package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ComtccmmndetailcodeId implements Serializable {

    @Column(name = "CODE_ID")
    private String codeId;

    @Column(name = "CODE")
    private String code;
}
