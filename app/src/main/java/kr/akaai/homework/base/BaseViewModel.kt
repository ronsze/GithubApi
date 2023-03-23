package kr.akaai.homework.base

import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel constructor(
    protected val commonComponent: CommonComponent
): AndroidViewModel(commonComponent.application) {
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}