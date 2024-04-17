package kr.akaai.homework.feature.favorite_user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.akaai.homework.databinding.ItemFavoriteUserBinding
import kr.akaai.homework.model.favorite.FavoriteUserData

class FavoriteUserAdapter: ListAdapter<FavoriteUserData, FavoriteUserAdapter.FavoriteUserViewHolder>(
    object : DiffUtil.ItemCallback<FavoriteUserData>() {
        override fun areItemsTheSame(oldItem: FavoriteUserData, newItem: FavoriteUserData): Boolean = oldItem.userId == newItem.userId
        override fun areContentsTheSame(oldItem: FavoriteUserData, newItem: FavoriteUserData): Boolean = oldItem == newItem
    }
) {
    inner class FavoriteUserViewHolder(val binding: ItemFavoriteUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val item = currentList[position]
        holder.binding.run {
            this.item = item
        }
    }

    override fun getItemCount(): Int = currentList.count()
}