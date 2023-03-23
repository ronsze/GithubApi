package kr.akaai.homework.github

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FollowerInfo (
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,
): Serializable