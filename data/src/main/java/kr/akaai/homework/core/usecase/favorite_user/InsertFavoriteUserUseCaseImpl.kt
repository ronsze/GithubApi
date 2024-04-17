package kr.akaai.homework.core.usecase.favorite_user

import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.repository.FavoriteUserRepository
import kr.akaai.homework.usecase.favorite_user.InsertFavoriteUserUseCase

class InsertFavoriteUserUseCaseImpl(
    private val repository: FavoriteUserRepository
): InsertFavoriteUserUseCase {
    override suspend fun invoke(favoriteUser: FavoriteUserData): Result<Unit> = runCatching {
        repository.insertFavoriteUser(favoriteUser)
    }
}