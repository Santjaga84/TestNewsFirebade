package com.example.testnewsapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.model.Category

class CategoryAdapter:RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClickListenerCategory: OnCategoryItemClickListener? = null
    var selectedCategory: Category? = null

    private val callback = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName = differ.currentList[position]

        holder.bind(categoryName)
      }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            categoryName.text = category.name

            // Устанавливаем цвет текста кнопки в зависимости от выбранной категории
            val backgroundRes = if (category == selectedCategory) {
                // Ресурс для выбранной категории
                R.color.selected_category_background
            } else {
                // Ресурс для не выбранной категории
                R.color.default_category_background
            }
            categoryName.setBackgroundResource(backgroundRes)

            itemView.setOnClickListener {
                // Обрабатываем клик на элементе

                onItemClickListenerCategory?.onCategoryItemClick(category)
                selectedCategory = category
                notifyDataSetChanged()
            }
        }
      }
    }
    interface OnCategoryItemClickListener {
       fun onCategoryItemClick(category: Category)
    }