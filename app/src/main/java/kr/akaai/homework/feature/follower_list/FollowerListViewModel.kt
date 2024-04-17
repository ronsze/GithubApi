package kr.akaai.homework.feature.follower_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kr.akaai.homework.base.mvvm.EventLiveData
import kr.akaai.homework.base.viewmodel.BaseViewModel
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
    private val _event: EventLiveData<FollowerListEvent> = EventLiveData()
    val event: LiveData<FollowerListEvent> get() = _event

    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    private val _followerList = ArrayList<FollowerInfo>()
    val followerList: ArrayList<FollowerInfo>
        get() = _followerList

    fun setCurrentUser(userInfo: UserInfo) = _userInfo.postValue(userInfo)

    fun loadData() {
        loadFollowerList()
    }

    private fun loadFollowerList() {
        userInfo.value?.run {
            viewModelScope.launch(ioDispatcher) {
                getFollowerListUseCase(login)
                    .onSuccess {

                    }
                    .onFailure {
                        showToast(it.message.toString())
                    }
            }
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

    fun onClickFollowerItem(userId: String) = _event.postValue(FollowerListEvent.NavToUserDetail(userId))
    fun onClickAddFavorite() = addFavoriteUser()
    fun onClickFinish() = _event.postValue(FollowerListEvent.Finish)

    sealed class FollowerListEvent {
        data class NavToUserDetail(val userId: String): FollowerListEvent()
        data object Finish: FollowerListEvent()
    }
}