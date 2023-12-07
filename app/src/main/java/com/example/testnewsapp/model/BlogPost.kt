package com.example.testnewsapp.model

import com.google.firebase.firestore.PropertyName

data class BlogPost(
    @PropertyName("id")
    val id: Int = 0,

    @PropertyName("category")
    val category: Category = Category(),

    @PropertyName("title")
    val title: String = "",

    @PropertyName("content")
    val content: String = "",

    @PropertyName("image")
    val image: String = "",

    @PropertyName("date")
    val date: String = ""
)
