package com.danielgimenez.myeconomy.domain.model

import com.danielgimenez.myeconomy.data.entity.TypeEntity

data class Type(val id: Int, var name: String, val localId: Int){

    fun toEntity(): TypeEntity{
        var entity = TypeEntity(name, localId)
        return entity
    }

    fun toMap(user: String) =
        hashMapOf(
                "id" to id,
                "name" to name,
                "localid" to localId,
                "user" to user
        )

    companion object{
        val localIdField = "localId"
        val nameField = "name"
    }
}