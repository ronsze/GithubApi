package kr.akaai.homework.core.usecase

import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.usecase.GetUserInfoUseCase

class GetUserInfoUseCaseImpl(
    private val repository: GithubApiRepository
): GetUserInfoUseCase {
    override suspend fun invoke(userId: String): Result<UserInfo> =
        runCatching { repository.getUserInfo(userId) }
}