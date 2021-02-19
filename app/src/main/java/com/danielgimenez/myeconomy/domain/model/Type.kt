package com.danielgimenez.myeconomy.domain.model

import com.danielgimenez.myeconomy.data.entity.TypeEntity

data class Type(val id: Int, val name: String){

    fun toEntity(): TypeEntity{
        var entity = TypeEntity(name)
        return entity
    }

    fun toMap(user: String) =
        hashMapOf(
                "id" to id,
                "name" to name,
                "user" to user
        )
}