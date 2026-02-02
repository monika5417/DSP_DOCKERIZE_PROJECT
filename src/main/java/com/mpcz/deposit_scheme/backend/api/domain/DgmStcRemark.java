package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DGM_STC_REMARK")  
public class DgmStcRemark extends Auditable<Long> {
	private static final long serialVersionUID = 1L;
	
	
    @Id
    @SequenceGenerator(name = "DGM_STC_REMARK_S", sequenceName = "DGM_STC_REMARK_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DGM_STC_REMARK_S")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONSUMER_APP_NO")
    private String consumerAppNo;

    @Column(name = "REMARK")
    private String Remark;

    @Column(name = "CREATED_DATE")
    private String createdDate;

    @Column(name = "STC_NAME")
    private String stcName;
    
    @Column(name = "STC_ID")
    private long stcId;

    @Column(name = "REMARK_COUNT")
    private long remarkCount;

    @Column(name = "USER_ROLE")
    private String userRole;
    
   
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public long getStcId() {
		return stcId;
	}

	public void setStcId(long stcId) {
		this.stcId = stcId;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerAppNo() {
		return consumerAppNo;
	}

	public void setConsumerAppNo(String consumerAppNo) {
		this.consumerAppNo = consumerAppNo;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}


	public String getStcName() {
		return stcName;
	}

	public void setStcName(String stcName) {
		this.stcName = stcName;
	}

	public long getRemarkCount() {
		return remarkCount;
	}

	public void setRemarkCount(long remarkCount) {
		this.remarkCount = remarkCount;
	}

    
    
}
