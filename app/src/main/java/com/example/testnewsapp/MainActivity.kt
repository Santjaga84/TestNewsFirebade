package com.example.testnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.testnewsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.testnewsapp.ui.splash.SplashViewModel


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private  val mBinding get() = binding!!

    private val blogViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        CoroutineScope(Dispatchers.Main).launch {

            delay(3000)
            binding = ActivityMainBinding.inflate(layoutInflater)

            //Запускаеться Splash экран и потом появляеться основной экран
            setContentView(mBinding.root)

        }
    }
}