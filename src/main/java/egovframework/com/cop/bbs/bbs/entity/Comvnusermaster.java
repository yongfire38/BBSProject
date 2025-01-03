package egovframework.com.cop.bbs.bbs.entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Immutable
@Table(name = "COMVNUSERMASTER")
public class Comvnusermaster {

    @Id
    @Column(name = "ESNTL_ID")
    private String esntlId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_NM")
    private String userNm;

    @Column(name = "USER_ZIP")
    private String userZip;

    @Column(name = "USER_ADRES")
    private String userAdres;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "GROUP_ID")
    private String groupId;

    @Column(name = "USER_SE")
    private String userSe;

    @Column(name = "ORGNZT_ID")
    private String orgnztId;

    @OneToMany(mappedBy = "userList")
    private List<Comtnbbs> comtnQustnrIems;
}
