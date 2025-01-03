package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "COMTNFILE")
public class ComtnFile {
    @Id
    @Column(name="ATCH_FILE_ID", length = 20)
    private String atchFileId;

    @Column(name="CREAT_DT")
    private LocalDateTime creatDt;
    @Column(name="USE_AT", length = 1)
    private String useAt;
}
