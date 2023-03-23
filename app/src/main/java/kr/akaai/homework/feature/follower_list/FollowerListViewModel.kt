package kr.akaai.homework.feature.follower_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.base.CommonComponent
import kr.akaai.homework.base.SingleLiveEvent
import kr.akaai.homework.core.api.github_api.GithubApiRepository
import kr.akaai.homework.core.util.FavoriteUserModule
import kr.akaai.homework.core.util.Functions.toArrayList
import kr.akaai.homework.feature.favorite_user.FavoriteUserViewModel
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class FollowerListViewModel @Inject constructor(
    commonComponent: CommonComponent,
    private val githubApiRepository: GithubApiRepository,
    private val favoriteUserModule: FavoriteUserModule
) : BaseViewModel(commonComponent) {

    private val _fetchFollowerListEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val fetchFollowerListEvent: LiveData<Void>
        get() = _fetchFollowerListEvent

    private val _currentUserIdLiveData: MutableLiveData<String> = MutableLiveData("")
    val currentUserIdLiveData: LiveData<String>
        get() = _currentUserIdLiveData

    private val _goToUserDetailEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val goToUserDetailEvent: LiveData<String>
        get() = _goToUserDetailEvent

    private val _searchFollowerFailEvent: MutableLiveData<Throwable> = MutableLiveData()
    val searchFollowerFailEvent: LiveData<Throwable>
        get() = _searchFollowerFailEvent

    private val _showToastEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val showToastEvent: LiveData<String>
        get() = _showToastEvent

    private val _finishEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val finishEvent: LiveData<Void>
        get() = _finishEvent

    private val _followerList = ArrayList<FollowerInfo>()
    val followerList: ArrayList<FollowerInfo>
        get() = _followerList

    private var currentPage: Int = 1
    private var isLastPage: Boolean = false
    var currentUserInfo: UserInfo? = null
        set(value) {
            _currentUserIdLiveData.postValue(value?.login.toString())
            field = value
        }

    fun setFollowerList(list: ArrayList<FollowerInfo>) {
        _followerList.addAll(list)
        _fetchFollowerListEvent.call()
    }

    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                if (visibleItemCount == layoutManager.itemCount) {
                    if (!isLastPage) {
                        currentPage++
                        fetchFollowerList()
                    }
                }
            }
        }
    }

    private fun fetchFollowerList() {
        compositeDisposable.add(
            githubApiRepository.getFollowerList(
                _currentUserIdLiveData.value.toString(), currentPage
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    if (res.isEmpty()) isLastPage = true
                    else setFollowerList(res)
                }, { err ->
                    _searchFollowerFailEvent.postValue(err)
                })
        )
    }

    private fun addFavoriteUser() {
        CoroutineScope(Dispatchers.Main).launch {
            val task = withContext(Dispatchers.Default) { favoriteUserModule.addUser(
                currentUserInfo?.login.toString(),
                currentUserInfo?.avatar_url.toString())
            }

            if (task == null) _showToastEvent.postValue("Success")
            else _showToastEvent.postValue(task.message)
        }
    }

    fun onClickFollowerItem(userId: String) {
        _goToUserDetailEvent.postValue(userId)
    }

    fun onClickAddFavorite() {
        addFavoriteUser()
    }

    fun onClickFinish() {
        _finishEvent.call()
    }
}