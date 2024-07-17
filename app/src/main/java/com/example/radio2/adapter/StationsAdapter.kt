package com.example.radio2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.radio2.R
import com.example.radio2.databinding.FragmentMainMenuBinding
import com.example.radio2.databinding.StationLayoutBinding
import com.example.radio2.model.StationsModel




class StationsAdapter(private val onItemClicked: (Int) -> Unit): RecyclerView.Adapter<StationsAdapter.StationsViewHolder>() {
    class StationsViewHolder(val binding: StationLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private var stationsList = emptyList<StationsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsViewHolder {
        val binding = StationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stationsList.size
    }

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: StationsViewHolder, position: Int) {
//        holder.binding.station_name.text = stationsList[position].stationName
//        val imageName = stationsList[position].imagePath
//
//        // Получаем идентификатор ресурса изображения
//        val context = holder.itemView.context
//        val imageResource = context.resources.getIdentifier(imageName, "drawable", context.packageName)
//
//        // Устанавливаем изображение в ImageView
//        holder.itemView.station_logo.setImageResource(imageResource)

        val station = stationsList[position]
        holder.binding.stationName.text = station.stationName

        val imageName = station.imagePath
        val context = holder.itemView.context
        val imageResource = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        holder.binding.stationLogo.setImageResource(imageResource)

        holder.itemView.setOnClickListener{
            onItemClicked(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list : List<StationsModel>) {
        stationsList = list
        notifyDataSetChanged()
    }

}