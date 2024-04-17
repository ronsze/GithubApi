package kr.akaai.homework.feature.follower_list.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kr.akaai.homework.R
import kr.akaai.homework.databinding.ItemFollowersBinding
import kr.akaai.homework.model.github.FollowerInfo
import java.io.File

class FollowersAdapter(
    private val onClickItemEvent: (userId: String) -> Unit
): RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    inner class FollowersViewHolder(val binding: ItemFollowersBinding): RecyclerView.ViewHolder(binding.root)

    private var followerList: ArrayList<FollowerInfo> = ArrayList()
    private lateinit var cacheDir: File
    private var dummyProfileImage: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        cacheDir = parent.context.cacheDir
        dummyProfileImage = ContextCompat.getDrawable(parent.context, R.drawable.ic_followers)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val item = followerList[position]
        holder.binding.run {
            this.item = item

            parentLayout.setOnClickListener {
                onClickItemEvent(item.login)
            }
        }
    }

    override fun getItemCount(): Int = followerList.count()

    fun fetchFollowerList(list: ArrayList<FollowerInfo>) {
        followerList.clear()
        followerList.addAll(list)
    }
}