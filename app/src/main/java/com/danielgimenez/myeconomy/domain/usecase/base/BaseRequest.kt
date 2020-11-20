package com.danielgimenez.myeconomy.domain.usecase.base

interface BaseRequest {
    fun validate(): Boolean
}