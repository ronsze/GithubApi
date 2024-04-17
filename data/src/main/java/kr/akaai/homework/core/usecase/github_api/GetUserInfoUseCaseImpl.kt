package kr.akaai.homework.core.usecase.github_api

import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.usecase.github_api.GetUserInfoUseCase

class GetUserInfoUseCaseImpl(
    private val repository: GithubApiRepository
): GetUserInfoUseCase {
    override suspend fun invoke(userId: String): Result<UserInfo> =
        runCatching { repository.getUserInfo(userId) }
}