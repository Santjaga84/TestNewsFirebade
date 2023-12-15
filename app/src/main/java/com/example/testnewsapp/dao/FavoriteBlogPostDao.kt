package com.example.testnewsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteBlogPostDao {
    @Query("SELECT * FROM favorite_database WHERE id = :blogId")
    suspend fun isFavoriteBlogPost(blogId: String): FavoriteBlogPost?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogPost: FavoriteBlogPost)

    @Query("DELETE FROM favorite_database WHERE id = :blogId")
    suspend fun delete(blogId: String)

    @Query("SELECT * FROM favorite_database")
    suspend fun getAllFavorite(): List<FavoriteBlogPost>
}