package kr.akaai.homework.core.usecase

import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.usecase.GetFollowerListUseCase

class GetFollowerListUseCaseImpl(
    private val repository: GithubApiRepository
): GetFollowerListUseCase {
    override suspend fun invoke(userId: String): Result<UserInfo> =
        runCatching { repository.getUserInfo(userId) }
}