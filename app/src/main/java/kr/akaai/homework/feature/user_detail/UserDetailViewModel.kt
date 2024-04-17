package kr.akaai.homework.feature.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.akaai.homework.base.mvvm.EventLiveData
import kr.akaai.homework.base.viewmodel.BaseViewModel
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
    private val _event: EventLiveData<UserDetailEvent> = EventLiveData()
    val event: LiveData<UserDetailEvent> get() = _event

    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    private val _following: MutableStateFlow<String> = MutableStateFlow("")
    val following: StateFlow<String> get() = _following

    private val _followers: MutableStateFlow<String> = MutableStateFlow("")
    val followers: StateFlow<String> get() = _followers

    private val _publicGits: MutableStateFlow<String> = MutableStateFlow("")
    val publicGits: StateFlow<String> get() = _publicGits

    private val _publicRepos: MutableStateFlow<String> = MutableStateFlow("")
    val publicRepos: StateFlow<String> get() = _publicRepos

    fun fetchUserInfo(userId: String) {
        viewModelScope.launch(ioDispatcher) {
            getUserInfoUseCase(userId)
                .onSuccess {
                    _userInfo.postValue(it)
                    setUserInfo(it)
                }
                .onFailure {}
        }
    }

    private fun setUserInfo(userInfo: UserInfo) {
        with(userInfo) {
            _following.set("Following\n   $following")
            _followers.set("Followers\n   $followers")
            _publicGits.set("Public Gits\n   $publicGits")
            _publicRepos.set("Public Repos\n   $publicRepos")
        }
    }

    private fun addFavoriteUser() {
        userInfo.value?.run {
            viewModelScope.launch(ioDispatcher) {
                insertFavoriteUserUseCase(FavoriteUserData(login, avatarUrl))
                    .onSuccess {
                        showToast("Success")
                    }
                    .onFailure {
                        showToast(it.message.toString())
                    }
            }
        }
    }

    fun onClickAdd() = addFavoriteUser()
    fun onClickGetFollowers() = _event.postValue(UserDetailEvent.NavToMain)
    fun onClickGithubProfile() = _event.postValue(UserDetailEvent.NavToGithub)
    fun onClickFinish() = _event.postValue(UserDetailEvent.Finish)

    sealed class UserDetailEvent {
        data object NavToGithub: UserDetailEvent()
        data object NavToMain: UserDetailEvent()
        data object Finish: UserDetailEvent()
    }
}