package kr.akaai.homework.feature.favorite_user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.akaai.homework.databinding.ItemFavoriteUserBinding
import java.io.File

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserViewHolder>() {
    private val favoriteUserList: ArrayList<FavoriteUserData> = ArrayList()
    private lateinit var imageDir: File

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        imageDir = parent.context.filesDir
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val item = favoriteUserList[position]
        holder.binding.name.text = item.userId
        holder.binding.image.setImageBitmap(item.image)
    }

    override fun getItemCount(): Int = favoriteUserList.count()

    fun setFavoriteUserList(list: ArrayList<FavoriteUserData>) {
        favoriteUserList.clear()
        favoriteUserList.addAll(list)
    }
}