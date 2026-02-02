package com.mpcz.deposit_scheme.backend.api.enums;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  charitra 
 */
public enum ChargesSlabEnum {
	
    Rf("Registration fees");
	
    private final String value;
    private static final Map<String, ChargesSlabEnum> ENUM_MAP  = new HashMap<>();
    
    
    public String getValue() {
        return value;
    }

    private ChargesSlabEnum(String value) {
        this.value = value;
    }

    static {
        for (ChargesSlabEnum instance : ChargesSlabEnum.values()) {
            ENUM_MAP.put(instance.getValue(), instance);
        }
    }

    public static ChargesSlabEnum getByValue(Integer value) {
        return ENUM_MAP.get(value);
    }
    
}
