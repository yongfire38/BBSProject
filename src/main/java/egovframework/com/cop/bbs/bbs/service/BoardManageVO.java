package egovframework.com.cop.bbs.bbs.service;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardManageVO implements Serializable {
	
	private static final long serialVersionUID = 1718228303523128069L;
	
	private String syncId;
	private int nttId;
	private String bbsId;
	private String syncSttusCode;
	private Date registPnttm;
	private Date syncPnttm;
	private Date errorPnttm;

}
