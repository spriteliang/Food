package com.leo.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.leo.easyfood.R
import com.leo.easyfood.adapters.MostPopularAdapter
import com.leo.easyfood.databinding.FragmentHomeBinding
import com.leo.easyfood.pojo.CategoryMeals
import com.leo.easyfood.pojo.Meal
import com.leo.easyfood.pojo.MealList
import com.leo.easyfood.retrofit.RetrofitInstance
import com.leo.easyfood.ui.activities.MealActivity
import com.leo.easyfood.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var homeMvvm: HomeViewModel
    private val TAG: String? = HomeFragment::class.java.simpleName
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter

    companion object {
        const val MEAL_ID = "com.leo.easyfood.ui.fragments.idMeal"
        const val MEAL_NAME = "com.leo.easyfood.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.leo.easyfood.ui.fragments.thumbMeal"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItem()
        observePopularItemsLiveData()
        onPopularItemClick()

    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick={meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }

    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemLivedata().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal

        }
    }
}