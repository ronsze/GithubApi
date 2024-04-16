package kr.akaai.homework.core.repository.github_api

import kr.akaai.homework.repository.GithubApiRepository
import retrofit2.Retrofit
import javax.inject.Inject

class GithubApiDataSource @Inject constructor(
    retrofit: Retrofit
) : GithubApiRepository {
    private val githubApiService = retrofit.create(GithubApiService::class.java)

    override fun getFollowerList(userId: String, page: Int) =
        githubApiService.getFollowerList(userId, page = page)

    override fun getUserInfo(userId: String) =
        githubApiService.getUserInfo(userId)
}