package com.example.testnewsapp.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_database")
data class FavoriteBlogPost(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo (name = "content")
    val content: String,
    @ColumnInfo (name = "image")
    val image: String,
    @ColumnInfo (name = "category")
    val category: String
)