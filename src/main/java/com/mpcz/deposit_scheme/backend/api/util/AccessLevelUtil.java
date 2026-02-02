package com.mpcz.deposit_scheme.backend.api.util;

import java.util.ArrayList;
import java.util.List;

import com.mpcz.deposit_scheme.backend.api.dto.AccessLevelDTO;
import com.mpcz.deposit_scheme.backend.api.enums.UserAccessLevelEnum;

public final class AccessLevelUtil {

	private static List<AccessLevelDTO> accessLevelList;

	static {
		List<AccessLevelDTO> tempAccessLevelDtoList = new ArrayList<>();
		tempAccessLevelDtoList
				.add(new AccessLevelDTO(UserAccessLevelEnum.DISCOM.getId(), UserAccessLevelEnum.DISCOM.getValue()));
		tempAccessLevelDtoList
				.add(new AccessLevelDTO(UserAccessLevelEnum.REGION.getId(), UserAccessLevelEnum.REGION.getValue()));
		tempAccessLevelDtoList
				.add(new AccessLevelDTO(UserAccessLevelEnum.CIRCLE.getId(), UserAccessLevelEnum.CIRCLE.getValue()));
		tempAccessLevelDtoList
				.add(new AccessLevelDTO(UserAccessLevelEnum.DIVISION.getId(), UserAccessLevelEnum.DIVISION.getValue()));
		tempAccessLevelDtoList.add(new AccessLevelDTO(UserAccessLevelEnum.SUBDIVISION.getId(),
				UserAccessLevelEnum.SUBDIVISION.getValue()));
		tempAccessLevelDtoList
				.add(new AccessLevelDTO(UserAccessLevelEnum.DC.getId(), UserAccessLevelEnum.DC.getValue()));
		accessLevelList = tempAccessLevelDtoList;

	}

	public static List<AccessLevelDTO> getAccessLevelList() {
		return accessLevelList;
	}

}
