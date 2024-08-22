package kr.akaai.homework.feature.search_user

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.usecase.github_api.GetUserInfoUseCase
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {
    private val _uiState: MutableStateFlow<SearchUserUiState> = MutableStateFlow(SearchUserUiState.View)
    val uiState get() = _uiState

    fun searchUser(userId: String) {
        if (userId.isEmpty()) return
        showLoading()
        viewModelScope.launch(ioDispatcher) {
            getUserInfoUseCase(userId)
                .onSuccess {
                    dismissLoading()
                    _uiState.set(SearchUserUiState.UserSearched(it))
                }
                .onFailure {
                    dismissLoading()
                    showPopupMessage(getErrorMessage(it))
                }
        }
    }

    fun resetUiState() = _uiState.set(SearchUserUiState.View)

    sealed interface SearchUserUiState {
        data object View: SearchUserUiState
        data class UserSearched(val userInfo: UserInfo): SearchUserUiState
    }
}