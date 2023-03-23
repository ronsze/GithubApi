package kr.akaai.homework.core.api.github_api

import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import retrofit2.Call

class GithubApiRepositoryImpl(
    private val githubApiDataSource: GithubApiDataSource
) : GithubApiRepository {
    override fun getFollowerList(userId: String, page: Int): Single<ArrayList<FollowerInfo>> =
        githubApiDataSource.getFollowerList(userId, page)

    override fun getUserInfo(userId: String): Single<UserInfo> =
        githubApiDataSource.getUserInfo(userId)
}