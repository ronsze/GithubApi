package kr.akaai.homework.feature.search_user

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kr.akaai.homework.base.viewmodel.BaseViewModel
import kr.akaai.homework.base.SingleLiveEvent
import kr.akaai.homework.base.mvvm.EventLiveData
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.usecase.GetFollowerListUseCase
import kr.akaai.homework.usecase.GetUserInfoUseCase
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {
    companion object {
        const val CURRENT_USER_INFO = "current_user_info"
        const val FOLLOWER_LIST = "follower_list"
    }

    private val _event: EventLiveData<SearchUserEvent> = EventLiveData()
    val event: LiveData<SearchUserEvent> get() = _event

    lateinit var userInfo: UserInfo
        private set

    private fun getUserInfo(userId: String) {
        viewModelScope.launch(ioDispatcher) {
            getUserInfoUseCase(userId)
                .onSuccess {
                    userInfo = it
                    _event.postValue(SearchUserEvent.NavToMain)
                }
                .onFailure {
                    _event.postValue(SearchUserEvent.SearchFollowerFailed(it.message.toString()))
                }
        }
    }

    sealed class SearchUserEvent {
        object NavToMain: SearchUserEvent()
        data class SearchFollowerFailed(val msg: String): SearchUserEvent()
    }
}