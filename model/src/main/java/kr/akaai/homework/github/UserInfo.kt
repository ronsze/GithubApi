package kr.akaai.homework.github

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfo (
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("public_gists")
    val publicGits: Int,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int
): Serializable