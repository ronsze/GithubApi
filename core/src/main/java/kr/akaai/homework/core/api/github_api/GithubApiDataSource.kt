package kr.akaai.homework.core.api.github_api

import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.akaai.homework.core.annotation.ForGithubApi
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class GithubApiDataSource @Inject constructor(
    @ForGithubApi retrofit: Retrofit
) : GithubApiRepository {
    private val githubApiService = retrofit.create(GithubApiService::class.java)

    override fun getFollowerList(userId: String, page: Int): Single<ArrayList<FollowerInfo>> =
        githubApiService.getFollowerList(userId, page = page)


    override fun getUserInfo(userId: String): Single<UserInfo> =
        githubApiService.getUserInfo(userId)
}