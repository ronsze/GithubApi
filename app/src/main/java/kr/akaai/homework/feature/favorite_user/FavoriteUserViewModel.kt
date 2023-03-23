package kr.akaai.homework.feature.favorite_user

import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.base.CommonComponent
import kr.akaai.homework.base.SingleLiveEvent
import kr.akaai.homework.core.util.FavoriteUserModule
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel @Inject constructor(
    commonComponent: CommonComponent,
    private val favoriteUserModule: FavoriteUserModule
): BaseViewModel(commonComponent) {
    private val _loadFavoriteUserListEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val loadFavoriteUserListEvent: LiveData<Void>
        get() = _loadFavoriteUserListEvent

    val favoriteUserList = ArrayList<FavoriteUserData>()

    fun loadData() {
        favoriteUserList.clear()
        val list = favoriteUserModule.getFavoriteUserList()

        list.forEach {
            val image = favoriteUserModule.getFavoriteUserImage(it)
            favoriteUserList.add(FavoriteUserData(it, image))
        }

        _loadFavoriteUserListEvent.call()
    }
}