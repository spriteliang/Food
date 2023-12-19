package com.leo.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leo.easyfood.pojo.CategoryList
import com.leo.easyfood.pojo.CategoryMeals
import com.leo.easyfood.pojo.Meal
import com.leo.easyfood.pojo.MealList
import com.leo.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private val TAG: String? = HomeViewModel::class.java.simpleName
    private val popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, "HomeFragment ${t.message.toString()}")
            }
        })
    }

    fun getPopularItem() {
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun observeRandomMealLivedata(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemLivedata(): LiveData<List<CategoryMeals>> {
        return popularItemsLiveData
    }
}