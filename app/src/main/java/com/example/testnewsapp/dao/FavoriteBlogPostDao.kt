//package com.example.testnewsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.testnewsapp.dao.FavoriteBlogPost
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.repositoriy.BlogPostConverter
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(BlogPostConverter::class)
interface FavoriteBlogPostDao {
    @Query("SELECT * FROM favorite_database WHERE id = :blogId")
    suspend fun isFavoriteBlogPost(blogId: String): FavoriteBlogPost?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogPost: FavoriteBlogPost)

    @Delete
    suspend fun delete(blogPost: FavoriteBlogPost)

    @Query("SELECT * FROM favorite_database")
    fun getAllFavorite(): Flow<List<FavoriteBlogPost>>
}