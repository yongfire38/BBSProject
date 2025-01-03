package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ComtnbbsuseId implements Serializable {

    @Column(name = "BBS_ID")
    private String bbsId;

    @Column(name = "TRGET_ID")
    private String trgetId;
}
