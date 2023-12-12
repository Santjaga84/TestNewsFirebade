package com.example.testnewsapp.repositoriy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.model.Category
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.testnewsapp.isInternetAvailable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository {

    val blogPostsLiveData = MutableLiveData<List<BlogPost>>()

    val isDataLoadedLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val isInternetAvailableLiveData = MutableLiveData<Boolean>()
    val categoriesLiveData = MutableLiveData<List<Category>>()



    fun loadDataFromFirestore(context: Context) {
        if (isInternetAvailable(context)) {
            isInternetAvailableLiveData.value = true
            loadBlogPostsFromFirestore()

        } else {
            isDataLoadedLiveData.value = false
            errorLiveData.value = "Отсутствует подключение к интернету"
            Log.d("MyLog", "No internet connection")
        }
    }

    private fun loadBlogPostsFromFirestore() {
        val db = Firebase.firestore
        val collectionRef = db.collection("News")

        collectionRef
            .get()
            .addOnSuccessListener { result ->
                val blogPosts = mutableListOf<BlogPost>()

                for (document in result) {
                    val blogPost = document.toObject(BlogPost::class.java)
                    blogPosts.add(blogPost)
                    Log.d("MyLog", "${document.id} => ${document.data}")
                }
                blogPostsLiveData.value = blogPosts
                isDataLoadedLiveData.value = true
                Log.d("MyLog", "Blog posts loaded successfully")
            }
            .addOnFailureListener { exception ->
                isDataLoadedLiveData.value = false
                Log.d("MyLog", "Failed to load blog posts", exception)
                errorLiveData.value = "Failed to load blog posts. Check your internet connection."
            }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            ContextCompat.getSystemService(context, ConnectivityManager::class.java)

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

    fun setBlogPostsLiveData(blogPosts: List<BlogPost>) {
        blogPostsLiveData.value = blogPosts
    }
}