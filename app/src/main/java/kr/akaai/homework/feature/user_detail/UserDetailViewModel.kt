package kr.akaai.homework.feature.user_detail

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
import kr.akaai.homework.core.util.Functions
import kr.akaai.homework.core.util.Functions.toArrayList
import kr.akaai.homework.github.FollowerInfo
import kr.akaai.homework.github.UserInfo
import org.json.JSONArray
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    commonComponent: CommonComponent,
    private val githubApiRepository: GithubApiRepository,
    private val favoriteUserModule: FavoriteUserModule
): BaseViewModel(commonComponent) {
    companion object {
        private const val FILE_PREFIX = "profile-img"
    }

    private val _finishEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val finishEvent: LiveData<Void>
        get() = _finishEvent

    private val _goToGithubEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val goToGithubEvent: LiveData<Void>
        get() = _goToGithubEvent

    private val _goToMainEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val goToMainEvent: LiveData<Void>
        get() = _goToMainEvent

    private val _searchUserFailEvent: SingleLiveEvent<Throwable?> = SingleLiveEvent()
    val searchUserFailEvent: LiveData<Throwable?>
        get() = _searchUserFailEvent

    private val _searchFollowerFailEvent: MutableLiveData<Throwable> = MutableLiveData()
    val searchFollowerFailEvent: LiveData<Throwable>
        get() = _searchFollowerFailEvent

    private val _showToastEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val showToastEvent: LiveData<String>
        get() = _showToastEvent

    private val _followerList: ArrayList<FollowerInfo> = ArrayList()
    val followerList: ArrayList<FollowerInfo>
        get() = _followerList

    private val _userProfileImageLiveData: MutableLiveData<Drawable?> = MutableLiveData()
    val userProfileImageLiveData: LiveData<Drawable?>
        get() = _userProfileImageLiveData

    val userInfo: ObservableField<UserInfo> = ObservableField()
    val followingField: ObservableField<String> = ObservableField()
    val followersField: ObservableField<String> = ObservableField()
    val publicGitsField: ObservableField<String> = ObservableField()
    val publicReposField: ObservableField<String> = ObservableField()

    fun fetchUserInfo(userId: String) {
        compositeDisposable.add(
            githubApiRepository.getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    userInfo.set(res)
                    setUserInfo()
                    setUserProfileImage()
                }, { err ->
                    _searchUserFailEvent.postValue(err)
                })
        )
    }

    private fun searchFollowers() {
        compositeDisposable.add(
            githubApiRepository.getFollowerList(userInfo.get()?.login.toString(), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    _followerList.clear()
                    _followerList.addAll(res)
                    _goToMainEvent.call()
                }, { err ->
                    _searchFollowerFailEvent.postValue(err)
                })
        )
    }

    private fun setUserInfo() {
        userInfo.get()?.run {
            followingField.set("Following\n   $following")
            followersField.set("Followers\n   $followers")
            publicGitsField.set("Public Gits\n   $publicGits")
            publicReposField.set("Public Repos\n   $publicRepos")
        }
    }

    private fun setUserProfileImage() {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO) {
                val fileName = "${FILE_PREFIX}-${userInfo.get()?.login}.jpg"
                val file = File(commonComponent.application.cacheDir, fileName)
                val source = userInfo.get()?.avatar_url.toString()

                Functions.getCacheImage(file, source)
            }

            _userProfileImageLiveData.postValue(BitmapDrawable(commonComponent.application.resources, bitmap))
        }
    }

    private fun addFavoriteUser() {
        CoroutineScope(Dispatchers.Main).launch {
            val task = withContext(Dispatchers.Default) { favoriteUserModule.addUser(
                userInfo.get()?.login.toString(),
                userInfo.get()?.avatar_url.toString())
            }

            if (task == null) _showToastEvent.postValue("Success")
            else _showToastEvent.postValue(task.message)
        }
    }

    fun onClickAdd() {
        addFavoriteUser()
    }

    fun onClickGetFollowers() {
        searchFollowers()
    }

    fun onClickGithubProfile() {
        _goToGithubEvent.call()
    }

    fun onClickFinish() {
        _finishEvent.call()
    }
}