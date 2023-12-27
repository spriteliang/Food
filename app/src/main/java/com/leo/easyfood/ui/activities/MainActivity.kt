package com.leo.easyfood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.leo.easyfood.R
import com.leo.easyfood.databinding.ActivityMainBinding
import com.leo.easyfood.db.MealDatabase
import com.leo.easyfood.viewmodel.HomeViewModel
import com.leo.easyfood.viewmodel.HomeViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeViewModelProvider=HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProvider)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.frag_host)

        bottomNavigationView.setupWithNavController(navController)
    }
}