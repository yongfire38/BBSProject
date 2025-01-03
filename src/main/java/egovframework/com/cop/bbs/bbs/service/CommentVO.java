package egovframework.com.cop.bbs.bbs.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO extends ComDefaultVO implements Serializable {

    private Long nttId;

    private String bbsId;

    private Long answerNo;

    private String wrterId;

    private String wrterNm;

    private String answer;

    private String useAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime frstRegistPnttm;

    private String frstRegisterId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdtPnttm;

    private String lastUpdusrId;

    private String password;

}
