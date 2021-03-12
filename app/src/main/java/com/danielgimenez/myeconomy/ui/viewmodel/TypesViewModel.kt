package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import com.danielgimenez.myeconomy.ui.viewmodel.states.GetTypesState
import com.danielgimenez.myeconomy.ui.viewmodel.states.SuccessGetTypesState
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.sql.Types
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TypesViewModel @Inject constructor(private val typeRepository: TypeRepository,
                     private val coroutineContext: CoroutineContext): ViewModel() {

    private val job = Job()
    val typesLiveData = MutableLiveData<GetTypesState>()

    fun getTypes() = launchSilent(coroutineContext, exceptionHandler , job){
        val result = typeRepository.getTypes()!!
        typesLiveData.postValue(SuccessGetTypesState(Response.Success(result)))
    }

    fun insertTypes(types: List<Type>) = launchSilent(coroutineContext, exceptionHandler, job){
        val result = types.map { type ->
            async{
                typeRepository.insertTypeRemote()
            }
        }.awaitAll()

    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}