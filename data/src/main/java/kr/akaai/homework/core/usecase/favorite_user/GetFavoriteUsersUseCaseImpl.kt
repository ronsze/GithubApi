package kr.akaai.homework.core.usecase.favorite_user

import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.repository.FavoriteUserRepository
import kr.akaai.homework.usecase.favorite_user.GetFavoriteUsersUseCase

class GetFavoriteUsersUseCaseImpl(
    private val repository: FavoriteUserRepository
): GetFavoriteUsersUseCase {
    override suspend fun invoke(): Result<List<FavoriteUserData>> = runCatching {
        repository.getFavoriteUsers()
    }
}