package kr.akaai.homework.feature.search_user

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.base.CommonComponent
import kr.akaai.homework.base.SingleLiveEvent
import kr.akaai.homework.core.api.github_api.GithubApiRepository
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    commonComponent: CommonComponent,
    private val githubApiRepository: GithubApiRepository
): BaseViewModel(commonComponent) {
    companion object {
        const val CURRENT_USER_INFO = "current_user_info"
        const val FOLLOWER_LIST = "follower_list"
    }

    private val _goToMainEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val goToMainEvent: LiveData<Void>
        get() = _goToMainEvent

    private val _searchFollowerFailEvent: MutableLiveData<String> = MutableLiveData()
    val searchFollowerFailEvent: LiveData<String>
        get() = _searchFollowerFailEvent

    private val  _followerList = ArrayList<FollowerInfo>()
    val followerList: ArrayList<FollowerInfo>
        get() = _followerList

    var userInfo: UserInfo? = null
    val userIdObservableField: ObservableField<String> = ObservableField("")

    private fun getUserInfo() {
        compositeDisposable.add(githubApiRepository.getUserInfo(userIdObservableField.get().toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                userInfo = res
                fetchFollowerList()
            }, { err ->
                _searchFollowerFailEvent.postValue(err.message)
            })
        )
    }

    private fun fetchFollowerList() {
        compositeDisposable.add(githubApiRepository.getFollowerList(userIdObservableField.get().toString(), 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                if (res.isEmpty()) {
                    _searchFollowerFailEvent.postValue("Follower list is Empty")
                } else {
                    _followerList.clear()
                    _followerList.addAll(res)
                    _goToMainEvent.call()
                }
            }, { err ->
                _searchFollowerFailEvent.postValue(err.message)
            })
        )
    }

    fun onClickSearch() {
        getUserInfo()
    }
}