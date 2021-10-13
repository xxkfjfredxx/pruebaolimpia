package com.fredrueda.pruebaandroid.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fredrueda.pruebaandroid.databinding.ItemPostBinding
import com.fredrueda.pruebaandroid.models.DataBook
import com.fredrueda.pruebaandroid.utils.GetImageThumbnail

class PostAdapter(
    private val context: Context
):RecyclerView.Adapter<PostAdapter.PostHolder>() {

    private var mData = mutableListOf<DataBook>()

    internal fun setData(data:List<DataBook>){
        this.mData = data as MutableList<DataBook>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostHolder {
        ItemPostBinding.inflate(LayoutInflater.from(context), parent, false).also { binding->
            return  PostHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: PostAdapter.PostHolder, position: Int) {
        val post = this.mData[position];
        holder.bind(post, position)
    }

    override fun getItemCount() = this.mData.size

    inner class PostHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(leagues: DataBook, position: Int) {
            val getImageThumbnail = GetImageThumbnail()
            this.binding.tvName.text = leagues.nombre.toString()
            this.binding.tvDireccion.text = leagues.direccion.toString()
            //Picasso.get().load(leagues.strTeamBadge.toString()).into(this.binding.imgBadge)
        }

    }
}