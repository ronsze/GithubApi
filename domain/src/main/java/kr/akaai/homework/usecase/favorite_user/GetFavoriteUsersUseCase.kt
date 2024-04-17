package kr.akaai.homework.usecase.favorite_user

import kr.akaai.homework.model.favorite.FavoriteUserData

interface GetFavoriteUsersUseCase {
    suspend operator fun invoke(): Result<List<FavoriteUserData>>
}