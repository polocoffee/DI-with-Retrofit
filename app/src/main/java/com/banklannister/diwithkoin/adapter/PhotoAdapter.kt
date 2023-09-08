package com.banklannister.diwithkoin.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.banklannister.diwithkoin.R
import com.banklannister.diwithkoin.databinding.PhotoRowBinding
import com.banklannister.diwithkoin.response.PhotoResponse

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private lateinit var binding: PhotoRowBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = PhotoRowBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoResponse.Hit) {
            binding.apply {
                tvUserName.text = item.user
                tvCommentCount.text = item.comments.toString()
                tvLikeCount.text = item.likes.toString()

                imgArt.load(item.previewURL) {
                    crossfade(true)
                    scale(Scale.FILL)
                    placeholder(R.drawable.ic_placeholder)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<PhotoResponse.Hit>() {
        override fun areItemsTheSame(
            oldItem: PhotoResponse.Hit,
            newItem: PhotoResponse.Hit
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoResponse.Hit,
            newItem: PhotoResponse.Hit
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}