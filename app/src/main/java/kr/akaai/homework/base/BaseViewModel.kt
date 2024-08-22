package kr.akaai.homework.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.akaai.homework.core.util.Errors.PRIMARY_KEY_DUPLICATE
import kr.akaai.homework.model.PopupMessageState

abstract class BaseViewModel: ViewModel() {
    private val _popupMessage: MutableStateFlow<PopupMessageState> = MutableStateFlow(PopupMessageState())
    val popupMessage get() = _popupMessage.asStateFlow()

    private val _loadingBarVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingBarVisible get() = _loadingBarVisible.asStateFlow()

    protected fun showPopupMessage(message: String) = viewModelScope.launch {
        _popupMessage.emit(PopupMessageState(true, message))
        withContext(Dispatchers.Default) { delay(2000) }
        _popupMessage.emit(PopupMessageState())
    }

    protected fun showLoading() = _loadingBarVisible.set(true)
    protected fun dismissLoading() = _loadingBarVisible.set(false)

    protected fun getErrorMessage(t: Throwable): String {
        return when {
            t.message?.contains(PRIMARY_KEY_DUPLICATE) == true -> "Already added"
            else -> "Unknown Error"
        }
    }

    protected fun<T> MutableStateFlow<T>.set(value: T) = viewModelScope.launch { emit(value) }
}