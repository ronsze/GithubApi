package kr.akaai.homework.repository

import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo

interface GithubApiRepository {
    suspend fun getFollowerList(userId: String, page: Int): List<FollowerInfo>
    suspend fun getUserInfo(userId: String): UserInfo
}