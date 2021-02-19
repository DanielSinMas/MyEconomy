package com.danielgimenez.myeconomy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeUseCase
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(val typeRepository: TypeRepository,
                                        private val coroutineContext: CoroutineContext): ViewModel() {

    private val job = Job()

    var insertTypeLiveData = MutableLiveData<InsertTypeState>()

    fun insertType(type: Type) = launchSilent(coroutineContext, exceptionHandler, job){
        val request = InsertTypeRequest(type)
        val result = typeRepository.insertType(request)
    }

    fun insertTypes(list: List<Type>) = launchSilent(coroutineContext, exceptionHandler, job){
        val result = list.map {
            async {
                val result = typeRepository.insertType(InsertTypeRequest(it))
                Log.e("Result", result.toString())
            }
        }.awaitAll()
        var typesList = typeRepository.getTypes()!!
        insertTypeLiveData.postValue(SuccessInsertTypetState(Response.Success(typesList)))
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}