package com.example.desafiofirebase.entities

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class Repository{
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val cr:CollectionReference = db.collection("Games")
    val storageReference = FirebaseStorage.getInstance().getReference(cr.document().id)


}

