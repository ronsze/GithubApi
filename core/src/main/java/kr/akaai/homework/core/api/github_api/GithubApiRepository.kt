package kr.akaai.homework.core.api.github_api

import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import retrofit2.Call

interface GithubApiRepository {
    fun getFollowerList(userId: String, page: Int): Single<ArrayList<FollowerInfo>>
    fun getUserInfo(userId: String): Single<UserInfo>
}