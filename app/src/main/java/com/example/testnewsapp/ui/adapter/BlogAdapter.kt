package com.example.testnewsapp.ui.adapter

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BlogAdapter(): RecyclerView.Adapter<BlogAdapter.ViewHolder>() {



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
//        val model = modelList[position]
//        holder.textName.text = model.name
//        holder.textDescription.text = model.description
//
//
//        // Загрузка изображения с использованием библиотеки Glide или Picasso
//         Glide.with(holder.imageView.context).load(model.imageUrl).into(holder.imageView)

        //val blog = modelList[position]
        val blog = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(blog.image).into(holder.imageView)
            holder.imageView.clipToOutline = true
            holder.textName.text = blog.category.name
            holder.textDescription.text = blog.content
            holder.textDate.text = getCurrentDate() // Установка текущей даты

            setOnClickListener {
                onItemClickListener?.let { it(blog)}
            }
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imNews)
        val textName: TextView = itemView.findViewById(R.id.tvTitle)
        val textDescription: TextView = itemView.findViewById(R.id.tvDesc)
        val textDate: TextView = itemView.findViewById(R.id.tvData)
    }

    private var onItemClickListener: ((BlogPost) -> Unit)? = null

    fun setOnItemClickListener(listener: (BlogPost) -> Unit){
        onItemClickListener = listener
    }

}