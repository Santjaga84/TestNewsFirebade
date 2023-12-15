package com.example.testnewsapp.repositoriy

import androidx.room.TypeConverter
import com.example.testnewsapp.dao.FavoriteBlogPost
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.model.Category

object BlogPostConverter {
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