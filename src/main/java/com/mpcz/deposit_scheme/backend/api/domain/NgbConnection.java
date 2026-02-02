package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="NGB_CONNECTION")
public class NgbConnection {

	@Id
	@SequenceGenerator(name = "NGB_CONNECTION_SEQ", sequenceName = "NGB_CONNECTION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NGB_CONNECTION_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
    
    @Column(name = "CONSUMER_NO")
    private String consumerNo;

    @Column(name = "METER_IDENTIFIER")
    private String meterIdentifier;

    @Column(name = "HAS_CTR")
    private boolean hasCtr;

    @Column(name = "METER_INSTALLATION_DATE")
    private String meterInstallationDate;

    @Column(name = "HAS_MODEM")
    private boolean hasModem;

    @Column(name = "METER_SERIAL_NO")
    private String meterSerialNo;

    @Column(name = "MAPPING_STATUS")
    private String mappingStatus;

    @Column(name = "CTR_IDENTIFIER")
    private String ctrIdentifier;

    @Column(name = "OVERALL_MF")
    private String overallMf;

    @Column(name = "SERIAL_NO")
    private String serialNo;

    @Column(name = "CT_MAKE")
    private String ctMake;

    @Column(name = "CT_RATIO")
    private String ctRatio;

    @Column(name = "MTR_MAKE")
    private String mtrMake;

    @Column(name = "METER_OWNER")
    private String meterOwner;

    @Column(name = "CAPACITY")
    private String capacity;

    @Column(name = "MF")
    private String mf;

    @Column(name = "CODE")
    private String code;

    @Column(name = "PHASE")
    private String phase;

    @Column(name = "BILL_DATE")
    private String billDate;

    @Column(name = "CURRENT_READ")
    private String currentRead;

    @Column(name = "PREVIOUS_READ")
    private String previousRead;

    @Column(name = "BILL_MONTH")
    private String billMonth;

    @Column(name = "READING_DATE")
    private String readingDate;

    @Column(name = "READING_TYPE")
    private String readingType;

    @Column(name = "METER_STATUS")
    private String meterStatus;

    @Column(name = "REPLACEMENT_FLAG")
    private String replacementFlag;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "READING")
    private String reading;

    @Column(name = "DIFFERENCE")
    private String difference;

    @Column(name = "READMF")
    private String readMf;

    @Column(name = "CONSUMPTION")
    private String consumption;

    @Column(name = "ASSESSMENT")
    private String assessment;

    @Column(name = "PROPAGATED_ASSESSMENT")
    private String propagatedAssessment;

    @Column(name = "TOTAL_CONSUMPTION")
    private String totalConsumption;

    @Column(name = "USED_ON_BILL")
    private String usedOnBill;

    @Column(name = "READ_MASTER_KW")
    private String readMasterKW;

    @Column(name = "READ_MASTER_PF")
    private String readMasterPF;

    @Column(name = "LOCATION_CODE")
    private String locationCode;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CONSUMER_NAME")
    private String consumerName;

    @Column(name = "IS_BPL")
    private boolean isBpl;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "IS_EMPLOYEE")
    private boolean isEmployee;

    @Column(name = "GROUP_NO")
    private String groupNo;

    @Column(name = "READING_DIARY_NO")
    private String readingDiaryNo;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ADDRESS3")
    private String address3;

    @Column(name = "PRIMARY_MOBILE_NO")
    private String primaryMobileNo;

    @Column(name = "AADHAAR_NO")
    private String aadhaarNo;

    @Column(name = "TARIFF_CATEGORY")
    private String tariffCategory;

    @Column(name = "CONNECTION_TYPE")
    private String connectionType;

    @Column(name = "METERING_STATUS")
    private String meteringStatus;

    @Column(name = "PREMISE_TYPE")
    private String premiseType;

    @Column(name = "SANCTIONED_LOAD")
    private String sanctionedLoad;

    @Column(name = "SANCTIONED_LOAD_UNIT")
    private String sanctionedLoadUnit;

    @Column(name = "CONTRACT_DEMAND")
    private String contractDemand;

    @Column(name = "CONTRACT_DEMAND_UNIT")
    private String contractDemandUnit;

    @Column(name = "IS_SEASONAL")
    private boolean isSeasonal;

    @Column(name = "PURPOSE_OF_INSTALLATION_ID")
    private String purposeOfInstallationId;

    @Column(name = "PURPOSE_OF_INSTALLATION")
    private String purposeOfInstallation;

    @Column(name = "TARIFF_CODE")
    private String tariffCode;

    @Column(name = "SUB_CATEGORY_CODE")
    private String subCategoryCode;

    @Column(name = "CONS_PHASE")
    private String consPhase;

    @Column(name = "IS_GOVERNMENT")
    private boolean isGovernment;

    @Column(name = "IS_DEMANDSIDE")
    private boolean isDemandside;

    @Column(name = "FEEDE_RNAME")
    private String feederName;

    @Column(name = "DTRNAME")
    private String dtrName;

    // Getter and Setter methods for each variable

}
