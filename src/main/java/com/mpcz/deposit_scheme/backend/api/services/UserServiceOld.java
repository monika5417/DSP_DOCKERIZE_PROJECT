package com.mpcz.deposit_scheme.backend.api.services;

import javax.validation.Valid;

import com.mpcz.deposit_scheme.backend.api.domain.User;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.exception.UserException;
import com.mpcz.deposit_scheme.backend.api.request.UserSignUpForm;

public interface UserServiceOld {

	User saveUser(@Valid UserSignUpForm userSingUpForm)throws Exception ;

}
