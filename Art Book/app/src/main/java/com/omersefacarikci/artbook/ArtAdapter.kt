package com.omersefacarikci.artbook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omersefacarikci.artbook.ArtAdapter.ArtHolder
import com.omersefacarikci.artbook.databinding.RecyclerRowBinding

class ArtAdapter(val artList: ArrayList<Artım>): RecyclerView.Adapter<ArtHolder>() {
    class ArtHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ArtHolder,
        position: Int
    ) {
        holder.binding.recyclerviewtextview.text = artList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArtActivity::class.java)
            intent.putExtra("bilgi","eski")
            intent.putExtra("id",artList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }


}