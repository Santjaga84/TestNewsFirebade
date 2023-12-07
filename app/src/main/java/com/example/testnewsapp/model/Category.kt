package com.example.testnewsapp.model

import com.google.firebase.firestore.PropertyName

data class Category(

    @PropertyName("id")
    val id: Int = 0,

    @PropertyName("name")
    val name: String = "",

    @PropertyName("image")
    val image: String = ""

)
