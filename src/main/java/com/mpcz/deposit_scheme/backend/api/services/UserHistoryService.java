package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.UserHistory;

public interface UserHistoryService {

	public UserHistory saveUserHistory(final UserHistory consumerHistory);

}
