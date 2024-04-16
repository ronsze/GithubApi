package kr.akaai.homework.feature.follower_list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kr.akaai.homework.R
import kr.akaai.homework.core.util.Functions
import kr.akaai.homework.databinding.ItemFollowersBinding
import kr.akaai.homework.model.github.FollowerInfo
import java.io.File

class FollowersAdapter(
    private val onClickItemEvent: (userId: String) -> Unit
): RecyclerView.Adapter<FollowersViewHolder>() {
    companion object {
        private const val FILE_PREFIX = "profile-img"
    }

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
        holder.binding.item = item
        holder.binding.parentLayout.setOnClickListener {
            onClickItemEvent(item.login)
        }

        setImageSource(holder, item)
    }

    override fun getItemCount(): Int = followerList.count()

    private fun setImageSource(holder: FollowersViewHolder, item: FollowerInfo) {
        CoroutineScope(Dispatchers.Main).launch {
            holder.binding.image.setImageDrawable(dummyProfileImage)

            val bitmap = withContext(Dispatchers.IO) {
                val fileName = "${FILE_PREFIX}-${item.login}.jpg"
                val file = File(cacheDir, fileName)
                Functions.getCacheImage(file, item.avatarUrl)
            }

            holder.binding.image.setImageBitmap(bitmap)
        }
    }

    fun fetchFollowerList(list: ArrayList<FollowerInfo>) {
        followerList.clear()
        followerList.addAll(list)
    }
}