package com.example.testnewsapp.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.testnewsapp.databinding.FragmentSplashBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController


class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private val mBinding get() = binding!!
    private val splashViewModel: SplashViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirestoreDataLoading()


    }

    private fun initFirestoreDataLoading() {
        // Инициируем загрузку данных из Firestore и сохранение их в BlogViewModel
        splashViewModel.loadDataFromFirestore(requireContext())

        splashViewModel.isDataLoaded.observe(viewLifecycleOwner, Observer { isDataLoaded ->
            if (isDataLoaded) {
                // Данные уже прочитаны, выполните необходимые действия
                mBinding.progressBar.visibility = View.GONE
                if(mBinding.progressBar.visibility == View.GONE){
                    navigateToNextFragment()
                }


            } else {
                // Продолжайте отображать прогрессбар, так как данные еще не загружены
                mBinding.progressBar.visibility = View.VISIBLE
                // Показать тост с сообщением об ошибке


            }
        })

        splashViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Показать тост с сообщением об ошибке
            showToast(errorMessage)
        }

        splashViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (!error.isNullOrBlank()) {
                // Ошибка при загрузке данных, отобразите сообщение об ошибке
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToNextFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
        findNavController().navigate(action)
    }
}