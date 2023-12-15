package com.example.testnewsapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testnewsapp.R
import com.example.testnewsapp.databinding.FragmentDetailsBinding
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.repositoriy.FavoriteBlogRepository
import com.example.testnewsapp.ui.main.DetailsViewModelFactory


class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var binding: FragmentDetailsBinding? = null
    private val mBinding get() = binding!!
    private val blog by lazy { arguments?.getSerializable("blog") as BlogPost }
    private var isFavorite = false
    var favoriteBlogPost = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory =
            DetailsViewModelFactory(
                FavoriteBlogRepository(requireContext())
            )
        detailsViewModel =
            ViewModelProvider(this, factory)[DetailsViewModel::class.java]

        Log.d("MyLog", "DetailsFragment onViewCreated")
        val ivFavoriteBackground = mBinding.ivFavorite
        ivFavoriteBackground.visibility = View.VISIBLE
        //ivFavoriteBackground.findViewById<ImageView>(R.id.ivFavorite)
        ivFavoriteBackground.setImageResource(R.drawable.baseline_favorite_border_24)

        setData(blog)

        setFavoriteImage()

    }

    private fun setData(blog: BlogPost?) {
        if (blog != null) {
            // Используйте selectedBlogPost здесь для отображения данных
            detailsViewModel.setSelectedBlogPost(blog)

            Log.d("MyLog", "Observed selectedBlogPost: $blog")
            Glide.with(this).load(blog.image).into(mBinding.ivDetailsImage)
            mBinding.tvDetailsTitle.text = blog.category.name
            mBinding.tvTitle.text = blog.title
            mBinding.tvDetailsContent.text = blog.content


        }
    }

    private fun setFavoriteImage() {
        mBinding.ivFavorite.setOnClickListener {
            isFavorite = !isFavorite
            val favoriteImageRes =
                if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            mBinding.ivFavorite.setImageResource(favoriteImageRes)

            // Сохранение/удаление блога в/из избранного
            // Получение объекта FavoriteBlogPost из BlogPost


            // Вставка в базу данных
            blog?.let { detailsViewModel.toggleFavoriteStatus(it) }

        }

    }
}