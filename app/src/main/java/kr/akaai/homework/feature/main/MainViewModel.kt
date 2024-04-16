package kr.akaai.homework.feature.main

import androidx.lifecycle.LiveData
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.akaai.homework.R
import kr.akaai.homework.base.viewmodel.BaseViewModel
import kr.akaai.homework.base.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    commonComponent: CommonComponent
): BaseViewModel(commonComponent) {
    private val _changePageToFollowersEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val changePageToFollowersEvent: LiveData<Void>
        get() = _changePageToFollowersEvent

    private val _changePageToFavoriteUserEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val changePageToFavoriteUserEvent: LiveData<Void>
        get() = _changePageToFavoriteUserEvent

    val onNavigationItemClickListener = NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_followers -> {
                _changePageToFollowersEvent.call()
                return@OnItemSelectedListener true
            }

            R.id.menu_favorite -> {
                _changePageToFavoriteUserEvent.call()
                return@OnItemSelectedListener true
            }

            else -> {
                return@OnItemSelectedListener false
            }
        }
    }
}