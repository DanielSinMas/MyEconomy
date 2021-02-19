package com.danielgimenez.myeconomy.domain.usecase.types

import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.base.BaseRequest

class InsertTypeRequest(var type: Type): BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}