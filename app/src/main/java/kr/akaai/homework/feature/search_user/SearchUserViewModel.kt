package kr.akaai.homework.feature.search_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kr.akaai.homework.base.mvvm.EventLiveData
import kr.akaai.homework.base.viewmodel.BaseViewModel
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.usecase.github_api.GetUserInfoUseCase
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {
    private val _event: EventLiveData<SearchUserEvent> = EventLiveData()
    val event: LiveData<SearchUserEvent> get() = _event

    fun searchUser(userId: String) {
        viewModelScope.launch(ioDispatcher) {
            getUserInfoUseCase(userId)
                .onSuccess {
                    _event.postValue(SearchUserEvent.NavToMain(it))
                }
                .onFailure {
                    showToast(it.message.toString())
                }
        }
    }

    fun onClickSearch() = _event.postValue(SearchUserEvent.OnClickSearch)

    sealed class SearchUserEvent {
        data object OnClickSearch: SearchUserEvent()
        data class NavToMain(val userInfo: UserInfo): SearchUserEvent()
    }
}