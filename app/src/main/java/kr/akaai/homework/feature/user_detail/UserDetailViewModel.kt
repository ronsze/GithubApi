package kr.akaai.homework.feature.user_detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.usecase.favorite_user.InsertFavoriteUserUseCase
import kr.akaai.homework.usecase.github_api.GetUserInfoUseCase
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val insertFavoriteUserUseCase: InsertFavoriteUserUseCase
): BaseViewModel() {
    private val _uiState: MutableStateFlow<UserDetailUiState> = MutableStateFlow(UserDetailUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun fetchUserInfo(userId: String) {
        viewModelScope.launch(ioDispatcher) {
            getUserInfoUseCase(userId)
                .onSuccess {
                    _uiState.set(UserDetailUiState.View(it))
                }
                .onFailure {
                    showPopupMessage(getErrorMessage(it))
                    _uiState.set(UserDetailUiState.Failed)
                }
        }
    }

    fun addFavoriteUser(userInfo: UserInfo) {
        viewModelScope.launch(ioDispatcher) {
            insertFavoriteUserUseCase(FavoriteUserData(userInfo.login, userInfo.avatarUrl))
                .onSuccess {
                    showPopupMessage("Success")
                }
                .onFailure {
                    showPopupMessage(getErrorMessage(it))
                }
        }
    }

    sealed interface UserDetailUiState {
        data object Loading: UserDetailUiState
        data object Failed: UserDetailUiState
        data class View(val userInfo: UserInfo): UserDetailUiState
    }
}