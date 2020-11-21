package com.danielgimenez.myeconomy.domain.usecase.types

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase

open class GetTypesUseCase(var repository: TypeRepository): BaseUseCase<Nothing, List<Type>>(){
    override suspend fun run(): Response<List<Type>> {
        return Response.Success(repository.getTypes()!!)
    }
}