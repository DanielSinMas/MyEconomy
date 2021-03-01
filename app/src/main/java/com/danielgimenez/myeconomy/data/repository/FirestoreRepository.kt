package com.danielgimenez.myeconomy.data.repository

import com.danielgimenez.myeconomy.domain.model.Type
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository(val listener : FirestoreRepositoryListener) {

    private val db = Firebase.firestore

    fun getTypes(user: String){
        val collection = db.collection("types")
        collection.whereEqualTo("user", user).get().addOnSuccessListener{ task ->
            listener.getTypes(task.documents.map { document ->
                document.toObject(Type::class.java)
            })
        }.addOnFailureListener{
            listener.error("Error getting types")
        }
    }

    interface FirestoreRepositoryListener{
        fun getTypes(list: List<Type?>)
        fun error(error: String)
    }
}