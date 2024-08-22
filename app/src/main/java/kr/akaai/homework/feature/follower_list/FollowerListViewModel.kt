package kr.akaai.homework.feature.follower_list

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.usecase.favorite_user.InsertFavoriteUserUseCase
import kr.akaai.homework.usecase.github_api.GetFollowerListUseCase
import javax.inject.Inject

@HiltViewModel
class FollowerListViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getFollowerListUseCase: GetFollowerListUseCase,
    private val insertFavoriteUserUseCase: InsertFavoriteUserUseCase
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<FollowerListUiState> = MutableStateFlow(FollowerListUiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    private val _followerList: SnapshotStateList<FollowerInfo> = SnapshotStateList()
    val followerList get() = _followerList

    private var currentPage = 0

    fun loadData(login: String) {
        _followerList.clear()
        loadFollowerList(login)
    }

    fun loadFollowerList(login: String) {
        viewModelScope.launch(ioDispatcher) {
            getFollowerListUseCase(login, currentPage)
                .onSuccess {
                    _followerList.addAll(it)
                    currentPage++
                    if (uiState.value == FollowerListUiState.Loading) _uiState.set(FollowerListUiState.View)
                }
                .onFailure {
                }
        }
    }

    fun addFavoriteUser(userInfo: UserInfo) {
        showLoading()
        viewModelScope.launch(ioDispatcher) {
            insertFavoriteUserUseCase(FavoriteUserData(userInfo.login, userInfo.avatarUrl))
                .onSuccess {
                    dismissLoading()
                    showPopupMessage("Success")
                }
                .onFailure {
                    dismissLoading()
                    showPopupMessage(getErrorMessage(it))
                }
        }
    }

    sealed interface FollowerListUiState {
        data object Loading: FollowerListUiState
        data object View: FollowerListUiState
    }
}