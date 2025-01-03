package egovframework.com.cop.bbs.bbs.service;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class ComDefaultVO {
    /** 검색조건 */
    private String searchCondition = "";

    /** 검색Keyword */
    private String searchKeyword = "";

    /** 검색사용여부 */
    private String searchUseYn = "";

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지개수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;

    /** 검색KeywordFrom */
    private String searchKeywordFrom = "";

    /** 검색KeywordTo */
    private String searchKeywordTo = "";
}
