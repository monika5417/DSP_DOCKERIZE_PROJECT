package com.mpcz.deposit_scheme.backend.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "CheckBoxPtrDTO Form")
public class CheckBoxPtrDTO {
	
	@ApiModelProperty(notes="consumerApplicationId not null",required =true)
	private long consumerApplicationId;
	
	@ApiModelProperty(notes = "checkBox 1 not null", required = true)
    private String checkBox1;
	
	@ApiModelProperty(notes = "checkBox 2 not null", required = true)
    private String checkBox2;
	
	@ApiModelProperty(notes = "checkBox 3 not null", required = true)
    private String checkBox3;
	
	@ApiModelProperty(notes = "checkBox 4 not null",required = true)
    private String checkBox4;
	
	@ApiModelProperty(notes = "checkBox 5 not null", required=true)
    private String checkBox5;
	
	@ApiModelProperty(notes = "checkBox 6 not null", required = true)
    private String checkBox6;
	
	@ApiModelProperty(notes = "checkBox 7 not null",required =true)
    private String checkBox7;
	
	@ApiModelProperty(notes = "checkBox 8 not null", required =true)
    private String checkBox8;
	
	@ApiModelProperty(notes = "checkBox 9 not null", required = true)
    private String checkBox9;
	
	@ApiModelProperty(notes="checkBox 10 not null")
    private String checkBox10;
	
	@ApiModelProperty(notes="checkBox 11 not null",required =true)
    private String checkBox11;
	
	@ApiModelProperty(notes="check box 12 not null",required =true)
    private String checkBox12;
	
	@ApiModelProperty(notes="check box 13 not null", required =true)
    private String checkBox13;
	
	@ApiModelProperty(notes="check box 14 not null",required =true)
    private String checkBox14;
	
	@ApiModelProperty(notes="check box 15 not null",required=true)
    private String checkBox15;
	
	@ApiModelProperty(notes="check box 16 not null",  required =true)
    private String checkBox16;
	
	@ApiModelProperty(notes="check box 17 not null", required =true)
    private String checkBox17;
	
	@ApiModelProperty(notes="check box 18 not null", required =true)
    private String checkBox18;
	
	@ApiModelProperty(notes="check box 19 not null",required =true)
    private String checkBox19;
	
	@ApiModelProperty(notes="check box 20 not null", required =true)
    private String checkBox20;
	
	@ApiModelProperty(notes="check Box 21 not null", required =true)
    private String checkBox21;
	
	@ApiModelProperty(notes="check box 22 not null",required =true)
    private String checkBox22;
	
	@ApiModelProperty(notes="check box 23 not null",required =true)
    private String checkBox23;
	
	@ApiModelProperty(notes="check box 24 not null",required =true)
    private String checkBox24;
	
	@ApiModelProperty(notes="check box 25 not null",required =true)
    private String checkBox25;
	
	@ApiModelProperty(notes="check box 26 not null",required =true)
    private String checkBox26;
	
	@ApiModelProperty(notes="check box 27 not null",required =true)
    private String checkBox27;
	
	@ApiModelProperty(notes="check box 28 not null",required =true)
    private String checkBox28;
	
	@ApiModelProperty(notes="check box 229 not null",required =true)
    private String checkBox29;
	
	@ApiModelProperty(notes="check box 30 not null",required =true)
    private String checkBox30;
    
}