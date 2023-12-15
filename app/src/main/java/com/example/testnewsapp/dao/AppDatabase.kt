package com.example.testnewsapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testnewsapp.model.BlogPost

@Database(entities = [FavoriteBlogPost::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteBlogPostDao(): FavoriteBlogPostDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "favorite_database"
            ).build()
        }

        fun toFavoriteBlogPost(blogPost: BlogPost): FavoriteBlogPost {
            return FavoriteBlogPost(
                id = blogPost.id,
                title = blogPost.title,
                content = blogPost.content,
                image = blogPost.image,
                category = blogPost.category.name
            )
        }
    }
}
