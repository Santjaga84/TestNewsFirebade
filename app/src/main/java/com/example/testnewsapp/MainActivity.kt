package com.example.testnewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testnewsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private  val mBinding get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)


        CoroutineScope(Dispatchers.Main).launch {

            delay(2000)
            binding = ActivityMainBinding.inflate(layoutInflater)

            //Запускаеться Splash экран и потом появляеться основной экран
            setContentView(mBinding.root)

        }
    }
}