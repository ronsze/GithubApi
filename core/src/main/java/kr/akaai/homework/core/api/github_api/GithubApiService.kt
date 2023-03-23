package kr.akaai.homework.core.api.github_api

import io.reactivex.Single
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("users/{userId}/followers")
    fun getFollowerList(
        @Path ("userId") userId: String,
        @Query("per_page") perPage: Int = 15,
        @Query("page") page: Int
    ): Single<ArrayList<FollowerInfo>>

    @GET("users/{userId}")
    fun getUserInfo(
        @Path ("userId") userId: String
    ): Single<UserInfo>
}