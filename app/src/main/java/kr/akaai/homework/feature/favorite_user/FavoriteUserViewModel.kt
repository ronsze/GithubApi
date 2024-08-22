package kr.akaai.homework.feature.favorite_user

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.usecase.favorite_user.GetFavoriteUsersUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase
): BaseViewModel() {
    private val _favoriteUserList: MutableStateFlow<List<FavoriteUserData>> = MutableStateFlow(listOf())
    val favoriteUserList get() = _favoriteUserList.asStateFlow()

    fun loadData() {
        showLoading()
        viewModelScope.launch(ioDispatcher) {
            getFavoriteUsersUseCase()
                .onSuccess {
                    dismissLoading()
                    _favoriteUserList.set(it)
                }
                .onFailure {
                    dismissLoading()
                }
        }
    }
}