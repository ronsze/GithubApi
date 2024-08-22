package kr.akaai.homework.core.usecase.github_api

import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.usecase.github_api.GetFollowerListUseCase

class GetFollowerListUseCaseImpl(
    private val repository: GithubApiRepository
): GetFollowerListUseCase {
    override suspend fun invoke(
        userId: String,
        page: Int,
    ): Result<List<FollowerInfo>> =
        runCatching { repository.getFollowerList(userId, page) }
}