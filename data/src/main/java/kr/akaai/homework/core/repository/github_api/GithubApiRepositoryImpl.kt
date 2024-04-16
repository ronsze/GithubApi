package kr.akaai.homework.core.repository.github_api

import kr.akaai.homework.repository.GithubApiRepository

class GithubApiRepositoryImpl(
    private val githubApiDataSource: GithubApiDataSource
) : GithubApiRepository {
    override fun getFollowerList(userId: String, page: Int) =
        githubApiDataSource.getFollowerList(userId, page)

    override fun getUserInfo(userId: String) =
        githubApiDataSource.getUserInfo(userId)
}