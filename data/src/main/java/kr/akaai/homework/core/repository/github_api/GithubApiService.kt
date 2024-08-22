package kr.akaai.homework.core.repository.github_api

import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("users/{userId}/followers")
    suspend fun getFollowerList(
        @Path ("userId") userId: String,
        @Query("per_page") perPage: Int = 15,
        @Query("page") page: Int
    ): List<FollowerInfo>

    @GET("users/{userId}")
    suspend fun getUserInfo(
        @Path ("userId") userId: String
    ): UserInfo
}