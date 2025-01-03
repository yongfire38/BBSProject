package egovframework.com.cop.bbs.bbs.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatisfactionVO extends ComDefaultVO implements Serializable {
    /** 만족도 번호 */
    private String stsfdgNo = "";

    /** 게시판 ID */
    private String bbsId = "";

    /** 게시물 번호 */
    private long nttId = 0L;

    /** 작성자 ID */
    private String wrterId = "";

    /** 작성자명 */
    private String wrterNm = "";

    /** 패스워드 */
    private String password = "";

    /** 만족도 내용 */
    private String stsfdgCn = "";

    /** 만족도 */
    private int stsfdg;

    /** 사용 여부 */
    private String useAt = "";

    /** 최초등록자 아이디 */
    private String frstRegisterId = "";

    /** 최초 등록자명 */
    private String frstRegisterNm = "";

    /** 최초등록시점 */
    private LocalDateTime frstRegistPnttm;

    /** 최종수정자 아이디 */
    private String lastUpdtId = "";

    /** 최종수정시점 */
    private LocalDateTime lastUpdusrPnttm;

    /** 확인 패스워드 */
    private String confirmPassword = "";
}
