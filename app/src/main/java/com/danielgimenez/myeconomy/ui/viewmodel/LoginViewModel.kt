package com.danielgimenez.myeconomy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.LoginRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoadingLogintState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.SuccessLogintState
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(val loginRepository: LoginRepository,
                                        private val coroutineContext: CoroutineContext): ViewModel() {

    private val job = Job()

    val loginLiveData = MutableLiveData<LoginState>()

    fun getDataForUser(id_token: String) = launchSilent(coroutineContext, exceptionHandler, job){
        loginLiveData.postValue(LoadingLogintState(null))
        val data = loginRepository.getDataForUser(id_token)
        loginLiveData.postValue(SuccessLogintState(data))
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}