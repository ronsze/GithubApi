package kr.akaai.homework.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.akaai.homework.base.mvvm.EventLiveData

abstract class BaseViewModel: ViewModel() {
    private val _baseEvent: EventLiveData<BaseEvent> = EventLiveData()
    val baseEvent: LiveData<BaseEvent> get() = _baseEvent

    protected fun showToast(msg: String) = _baseEvent.postValue(BaseEvent.ShowToast(msg))
    protected fun<T> MutableStateFlow<T>.set(value: T) = viewModelScope.launch { emit(value) }

    sealed class BaseEvent {
        data class ShowToast(val msg: String): BaseEvent()
    }
}