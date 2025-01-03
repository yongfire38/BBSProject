package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ComtncommentId implements Serializable {

    @Column(name = "NTT_ID")
    private Long nttId;

    @Column(name = "BBS_ID")
    private String bbsId;

    @Column(name = "ANSWER_NO")
    private Long answerNo;
}