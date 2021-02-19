package com.danielgimenez.myeconomy.domain.usecase.types

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase

open class InsertTypeUseCase(var repository: TypeRepository): BaseUseCase<InsertTypeRequest, Type>() {
    override suspend fun run(): Response<Type> {
        return repository.insertType(request!!)
    }
}