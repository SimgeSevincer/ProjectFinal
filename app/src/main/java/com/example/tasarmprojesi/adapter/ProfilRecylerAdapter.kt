package com.example.tasarmprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasarmprojesi.databinding.ProfilrecyclerrowBinding
import com.example.tasarmprojesi.model.Post
import com.squareup.picasso.Picasso

class ProfilRecylerAdapter (private  val profilpostList : ArrayList<Post>) : RecyclerView.Adapter<ProfilRecylerAdapter.ProfilpostHolder>(){
    class ProfilpostHolder(val binding: ProfilrecyclerrowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilpostHolder {
        val binding = ProfilrecyclerrowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProfilpostHolder(binding)
    }

    override fun getItemCount(): Int {
        println(profilpostList.size)
        return profilpostList.size
    }

    override fun onBindViewHolder(holder: ProfilpostHolder, position: Int) {
        holder.binding.textView25.text=profilpostList.get(position).userEmail
        holder.binding.textView28.text=profilpostList.get(position).comment
        Picasso.get().load(profilpostList.get(position).downloadUrl).into(holder.binding.imageView25)
    }
}