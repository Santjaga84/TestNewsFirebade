package com.example.testnewsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
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
import com.example.testnewsapp.ui.splash.SplashViewModel

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val mBinding get() = binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var modelAdapter: BlogAdapter

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    private val viewModel by viewModels<SplashViewModel>()

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
            recyclerView = mBinding.rcView
            recyclerView.layoutManager = LinearLayoutManager(context)

            // Инициализация адаптера и установка его для RecyclerView
            modelAdapter = BlogAdapter()
            recyclerView.adapter = modelAdapter

            // Проверка наличия интернета перед запросом данных
            viewModel.checkInternetConnection(requireContext())

            // Вручную добавляем "All" как первую категорию
            val categories = mutableListOf(("All"))
           recyclerViewCategory = mBinding.rcViewCategory
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewCategory.adapter = CategoryAdapter(categories)



            viewModel.isInternetAvailable.observe(viewLifecycleOwner) { isInternetAvailable ->
                if (isInternetAvailable) {
                    // Загрузка данных из Firestore только при наличии интернета
                    viewModel.loadDataFromFirestore(requireContext())
                } else {
                    // Обработка случая отсутствия интернета
                    // Например, показать сообщение об ошибке или предложить повторить попытку
                    Log.d("MyLog", "No internet connection")
                }

                // Наблюдение за изменениями в списке блогов и обновление адаптера
                viewModel.blogPosts.observe(viewLifecycleOwner) { blogPosts ->
                    modelAdapter.differ.submitList(blogPosts)
                    Log.d("MyLog", "Number of blog posts in observer: ${blogPosts.size}")

                }
            }
            // Если нужно, можете установить слушатель кликов в адаптере
            modelAdapter.setOnItemClickListener { blogPost ->
                // Обработка клика на элементе списка
                // Можно вызвать метод для перехода к другому фрагменту или выполнения других действий
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Обработка нажатия кнопки "Назад"
            // Например, закрытие фрагмента или приложения
            requireActivity().finish()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}