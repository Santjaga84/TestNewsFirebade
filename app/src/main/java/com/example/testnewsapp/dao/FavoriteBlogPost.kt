package com.example.testnewsapp.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "favorite_database")
data class FavoriteBlogPost(
    @PrimaryKey
    val id: Int = Random.nextInt(),
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo (name = "content")
    val content: String,
    @ColumnInfo (name = "image")
    val image: String,
    @ColumnInfo (name = "category")
    val category: String
)