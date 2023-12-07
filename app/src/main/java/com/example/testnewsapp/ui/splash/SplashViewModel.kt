package com.example.testnewsapp.ui.splash

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testnewsapp.model.BlogPost
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashViewModel: ViewModel() {

    private val postsLiveData = MutableLiveData<List<BlogPost>>()
    val blogPosts: LiveData<List<BlogPost>> get() = postsLiveData

    private val isDataLoadedLiveData = MutableLiveData<Boolean>()
    val isDataLoaded: LiveData<Boolean> get() = isDataLoadedLiveData

    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String> get() = errorLiveData

    private val isInternetAvailableLiveData = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> get() = isInternetAvailableLiveData

    fun checkInternetConnection(context: Context) {
        isInternetAvailableLiveData.value = context.isInternetAvailable()
    }

    // Метод для загрузки данных из Firestore для всех документов коллекции
    fun loadDataFromFirestore(context: Context) {
        if (context.isInternetAvailable()) {

            // использование Firebase Firestore
            val db = Firebase.firestore
            val collectionRef = db.collection("News")

            collectionRef
                .get()
                .addOnSuccessListener { result ->
                    val blogPosts = mutableListOf<BlogPost>()

                    for (document in result) {
                        // Преобразование данных из документа в модель данных (BlogPost)
                        val blogPost = document.toObject(BlogPost::class.java)
                        blogPosts.add(blogPost)
                        Log.d("MyLog", "${document.id} => ${document.data}")
                    }
                    postsLiveData.value = blogPosts
                    isDataLoadedLiveData.value = true // Устанавливаем флаг, что данные загружены
                    Log.d("MyLog", "ok")
                }
                .addOnFailureListener { exception ->
                    isDataLoadedLiveData.value = false // Устанавливаем флаг, что данные не загружены
                    Log.d("MyLog", "get failed with ", exception)
                    //Ошибка при загрузке данных, отправляем ошибку в LiveData
                    errorLiveData.value =
                        "Ошибка при загрузке данных. Проверьте подключение к интернету."
                }
        } else {
            // Если нет интернет-соединения
            isDataLoadedLiveData.value = false // Устанавливаем флаг, что данные не загружены
            errorLiveData.value = "Отсутствует подключение к интернету"
            Log.d("MyLog", "No internet connection")
        }
      }

//    Co
    fun Context.isInternetAvailable(): Boolean {
        val connectivityManager =
            ContextCompat.getSystemService(this, ConnectivityManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(network)
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetworkInfo = connectivityManager?.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }


    companion object {
        private const val TAG = "SplashViewModel"
    }
}