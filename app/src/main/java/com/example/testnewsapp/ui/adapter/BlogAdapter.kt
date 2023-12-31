package com.example.testnewsapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testnewsapp.R
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.model.Category
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BlogAdapter: RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    var onItemClickListenerBlog: OnBlogItemClickListener? = null

//    //var onFavoriteIconClickListener: OnFavoriteIconClickListener? = null
//
//    var blogDetails: BlogPost? = null
//    fun setBlogPosts(blogPosts: List<BlogPost>) {
//        differ.submitList(blogPosts)
//
//    }

    private val callback = object : DiffUtil.ItemCallback<BlogPost>(){
        override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem == newItem
        }
    }

    val differ= AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val blog = differ.currentList[position]

        holder.bind(blog)

    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imNews)
        private val textName: TextView = itemView.findViewById(R.id.tvTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.tvDesc)
        private val textDate: TextView = itemView.findViewById(R.id.tvData)


        fun bind(blogPost: BlogPost){
            Glide.with(itemView.context)
                 .load(blogPost.image)
                 .into(imageView)
            imageView.clipToOutline = true

            textName.text = blogPost.category.name
            textDescription.text = blogPost.content
            textDate.text = getCurrentDate() // Установка текущей даты

            itemView.setOnClickListener {
                onItemClickListenerBlog?.onBlogItemClick(blogPost)

                notifyDataSetChanged()
                Log.d("MyLog","Мои данные - $blogPost")
            }
        }
    }
}
    interface OnBlogItemClickListener {
        fun onBlogItemClick(blogPost: BlogPost)
}

