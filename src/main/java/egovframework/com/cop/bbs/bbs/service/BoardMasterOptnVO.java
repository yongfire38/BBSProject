package egovframework.com.cop.bbs.bbs.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardMasterOptnVO extends ComDefaultVO implements Serializable {
    private String bbsId;
    private String answerAt;
    private String stsfdgAt;
    private LocalDateTime frstRegistPnttm;
    private LocalDateTime lastUpdtPnttm;
    private String frstRegisterId;
    private String lastUpdusrId;
}
