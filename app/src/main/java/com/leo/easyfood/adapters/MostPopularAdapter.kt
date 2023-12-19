package com.leo.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leo.easyfood.databinding.MostPopularCardBinding
import com.leo.easyfood.pojo.CategoryMeals

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick: ((CategoryMeals)->Unit)
    private var mealsList = ArrayList<CategoryMeals>()
    fun setMeals(mealsList: ArrayList<CategoryMeals>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            MostPopularCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
    }

    class PopularMealViewHolder(val binding: MostPopularCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}
