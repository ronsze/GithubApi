package kr.akaai.homework.usecase.github_api

import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo

interface GetFollowerListUseCase {
    suspend operator fun invoke(
        userId: String,
        page: Int
    ): Result<List<FollowerInfo>>
}