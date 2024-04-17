package kr.akaai.homework.usecase.favorite_user

import kr.akaai.homework.model.favorite.FavoriteUserData

interface InsertFavoriteUserUseCase {
    suspend operator fun invoke(
        favoriteUser: FavoriteUserData
    ): Result<Unit>
}