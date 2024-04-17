package kr.akaai.homework.core.repository.github_api

import kr.akaai.homework.repository.GithubApiRepository
import javax.inject.Inject

class GithubApiRepositoryImpl @Inject constructor(
    private val githubApiDataSource: GithubApiDataSource
) : GithubApiRepository {
    override suspend fun getFollowerList(userId: String, page: Int) =
        githubApiDataSource.getFollowerList(userId, page)

    override suspend fun getUserInfo(userId: String) =
        githubApiDataSource.getUserInfo(userId)
}