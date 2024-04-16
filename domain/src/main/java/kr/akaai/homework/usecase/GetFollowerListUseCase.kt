package kr.akaai.homework.usecase

import kr.akaai.homework.model.github.UserInfo

interface GetFollowerListUseCase {
    suspend operator fun invoke(
        userId: String
    ): Result<UserInfo>
}