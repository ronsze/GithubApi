package kr.akaai.homework.repository

import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo

interface GithubApiRepository {
    fun getFollowerList(userId: String, page: Int): ArrayList<FollowerInfo>
    fun getUserInfo(userId: String): UserInfo
}