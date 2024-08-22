package kr.akaai.homework.feature.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.akaai.homework.base.BaseViewModel
import kr.akaai.homework.model.github.UserInfo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel() {
    lateinit var currentUserInfo: UserInfo
}