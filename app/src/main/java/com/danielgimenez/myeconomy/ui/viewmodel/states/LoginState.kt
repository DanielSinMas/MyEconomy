package com.danielgimenez.myeconomy.ui.viewmodel.states

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse

sealed class LoginState{
    abstract val response: Response<GetDataForUserResponse>?
}

data class SuccessLogintState(override val response: Response<GetDataForUserResponse>): LoginState()
data class LoadingLogintState(override val response: Response<GetDataForUserResponse>?): LoginState()
data class ErrorLoginState(override val response: Response<GetDataForUserResponse>): LoginState()