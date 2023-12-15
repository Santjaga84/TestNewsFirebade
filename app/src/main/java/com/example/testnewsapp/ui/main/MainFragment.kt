package com.example.testnewsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testnewsapp.R
import com.example.testnewsapp.databinding.FragmentMainBinding
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.model.Category
import com.example.testnewsapp.ui.adapter.BlogAdapter
import com.example.testnewsapp.ui.adapter.CategoryAdapter
import com.example.testnewsapp.ui.adapter.OnBlogItemClickListener
import com.example.testnewsapp.ui.adapter.OnCategoryItemClickListener
import com.example.testnewsapp.ui.details.DetailsViewModel
import com.example.testnewsapp.ui.splash.SplashViewModel

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val mBinding get() = binding!!

    private lateinit var recyclerViewBlog: RecyclerView
    private lateinit var modelAdapterBlog: BlogAdapter

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    private val viewModel by viewModels<SplashViewModel>()

    private val detailsViewModel by viewModels<DetailsViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().runOnUiThread {
            recyclerViewBlog = mBinding.rcView
            recyclerViewBlog.layoutManager = LinearLayoutManager(context)

            // Инициализация адаптера и установка его для RecyclerView
            modelAdapterBlog = BlogAdapter()
            recyclerViewBlog.adapter = modelAdapterBlog

            // Проверка наличия интернета перед запросом данных
            viewModel.checkInternetConnection(requireContext())

            recyclerViewCategory = mBinding.rcViewCategory
            recyclerViewCategory.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            categoryAdapter = CategoryAdapter()
            recyclerViewCategory.adapter = categoryAdapter


            viewModel.isInternetAvailable.observe(viewLifecycleOwner) { isInternetAvailable ->
                if (isInternetAvailable) {
                    // Загрузка данных из Firestore только при наличии интернета
                    viewModel.loadDataFromFirestore(requireContext())
                    // Наблюдение за изменениями в списке блогов и обновление адаптера
                    viewModel.blogPosts.observe(viewLifecycleOwner) { blogPosts ->
                        modelAdapterBlog.differ.submitList(blogPosts)

                    }

                    viewModel.categoryLD.observe(viewLifecycleOwner) { category ->
                        categoryAdapter.differ.submitList(category)

                    }

                    categoryAdapter.onItemClickListenerCategory = object : OnCategoryItemClickListener {
                        override fun onCategoryItemClick(category: Category) {
                            // Обработка клика на категории
                            if (category.name == "All") {
                                // Если выбрана категория "All", отображение всех карточек
                                modelAdapterBlog.differ.submitList(viewModel.blogPosts.value)
                            } else {
                                // Вызовите логику для фильтрации по категории и обновления списка BlogAdapter
                                val filteredPosts =
                                    viewModel.blogPosts.value?.filter { it.category.id == category.id }
                                modelAdapterBlog.differ.submitList(filteredPosts)
                            }
                        }
                    }

                    modelAdapterBlog.onItemClickListenerBlog = object : OnBlogItemClickListener{
                        override fun onBlogItemClick(blogPost: BlogPost) {
                            // Вызовите метод навигации к DetailsFragment и передайте данные о выбранной карточке

                            detailsViewModel.setSelectedBlogPost(blogPost)
                            Log.d("MyLog","кнопка нажата")
                            findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundleOf("blog" to blogPost))

                        }
                    }

//                    modelAdapterBlog.onFavoriteIconClickListener = object : OnFavoriteIconClickListener {
//                        override fun onFavoriteIconClick(blogPost: BlogPost) {
//                            // Обработка нажатия на избранное
//                            blogPost.isFavorite = !blogPost.isFavorite
//                            // Ваша логика для обновления состояния блога в удаленном источнике
//
//                            // Если вам нужно получить обновленный список избранных блогов, то можете вызвать здесь метод
//                            // detailsViewModel.loadFavoriteBlogPosts() и обновить адаптер в обработчике колбека
//                            //detailsViewModel.loadFavoriteBlogPosts()
//                        }
//                    }
//                    modelAdapterBlog.onItemClickListenerBlog = object : OnBlogItemClickListener {
//                        override fun onFavoriteIconClick(blogPost: BlogPost) {
//                            // Обработка нажатия на избранное
//                            blogPost.isFavorite = !blogPost.isFavorite
//                            // Ваша логика для обновления состояния блога в удаленном источнике
//                            // Можете вызвать здесь метод Firestore или другого удаленного источника для обновления информации о блоге
//
//                            // Если вам нужно получить обновленный список избранных блогов, то можете вызвать метод
//                            // detailsViewModel.loadFavoriteBlogPosts() и обновить адаптер в обработчике колбека
//                            detailsViewModel.set()
//                        }
//                    }
                }
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                // Обработка нажатия кнопки "Назад"
                // закрытие фрагмента или приложения
                requireActivity().finish()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}