package com.danielgimenez.myeconomy.domain.usecase.types

import com.danielgimenez.myeconomy.domain.model.Type

data class InsertTypeRemoteResponse(var types: List<Type>) {
}