package kr.akaai.homework.feature.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.akaai.homework.base.viewmodel.BaseViewModel
import kr.akaai.homework.model.github.UserInfo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel() {
    lateinit var currentUserInfo: UserInfo
}