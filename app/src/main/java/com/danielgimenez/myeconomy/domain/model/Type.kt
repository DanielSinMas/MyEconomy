package com.danielgimenez.myeconomy.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.danielgimenez.myeconomy.data.entity.TypeEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(val id: Int, var name: String?, val localId: Int): Parcelable{

    fun toEntity(): TypeEntity{
        val entity = TypeEntity(name!!, localId)
        return entity
    }

    fun toMap(user: String) =
        hashMapOf(
                "id" to id,
                "name" to name,
                "localId" to localId,
                "user" to user
        )

    override fun describeContents() = 0

    companion object {
        val localIdField = "localId"
        val nameField = "name"
    }
}