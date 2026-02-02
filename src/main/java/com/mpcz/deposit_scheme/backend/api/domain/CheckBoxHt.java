package com.mpcz.deposit_scheme.backend.api.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Check_Box_Ht")
@Entity(name = "CheckBoxHt")
@NoArgsConstructor
@AllArgsConstructor
public @Data class CheckBoxHt {
	
	@Id
	@SequenceGenerator(name = "Check_Box_Ht_SEQ", sequenceName = "Check_Box_Ht_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Check_Box_Ht_SEQ")
	@Column(name = "ID", columnDefinition = "serial",updatable = false)
	private long id;
	
	@Column(name="consumer_Application_Id")
	private long consumerApplicationId;
	
    @Column(name = "check_box_1")
    private String checkBox1;

    @Column(name = "check_box_2")
    private String checkBox2;

    @Column(name = "check_box_3")
    private String checkBox3;

    @Column(name = "check_box_4")
    private String checkBox4;

    @Column(name = "check_box_5")
    private String checkBox5;

    @Column(name = "check_box_6")
    private String checkBox6;

    @Column(name = "check_box_7")
    private String checkBox7;

    @Column(name = "check_box_8")
    private String checkBox8;

    @Column(name = "check_box_9")
    private String checkBox9;

    @Column(name = "check_box_10")
    private String checkBox10;

    @Column(name = "check_box_11")
    private String checkBox11;

    @Column(name = "check_box_12")
    private String checkBox12;

    @Column(name = "check_box_13")
    private String checkBox13;

    @Column(name = "check_box_14")
    private String checkBox14;

    @Column(name = "check_box_15")
    private String checkBox15;

    @Column(name = "check_box_16")
    private String checkBox16;

    @Column(name = "check_box_17")
    private String checkBox17;

    @Column(name = "check_box_18")
    private String checkBox18;

    @Column(name = "check_box_19")
    private String checkBox19;

    @Column(name = "check_box_20")
    private String checkBox20;

    @Column(name = "check_box_21")
    private String checkBox21;

    @Column(name = "check_box_22")
    private String checkBox22;

    @Column(name = "check_box_23")
    private String checkBox23;

   

}