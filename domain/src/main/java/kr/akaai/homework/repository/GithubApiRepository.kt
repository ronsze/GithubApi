package kr.akaai.homework.repository

import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo

interface GithubApiRepository {
    suspend fun getFollowerList(userId: String, page: Int): ArrayList<FollowerInfo>
    suspend fun getUserInfo(userId: String): UserInfo
}