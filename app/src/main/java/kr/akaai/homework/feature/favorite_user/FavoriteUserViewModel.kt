package kr.akaai.homework.feature.favorite_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.akaai.homework.base.viewmodel.BaseViewModel
import kr.akaai.homework.core.provider.IODispatcher
import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.usecase.favorite_user.GetFavoriteUsersUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteUserViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase
): BaseViewModel() {
    private val _favoriteUserList: MutableStateFlow<List<FavoriteUserData>> = MutableStateFlow(listOf())
    val favoriteUserList: LiveData<List<FavoriteUserData>> get() = _favoriteUserList.asLiveData()

    fun loadData() {
        viewModelScope.launch(ioDispatcher) {
            getFavoriteUsersUseCase()
                .onSuccess {
                    _favoriteUserList.set(it)
                }
                .onFailure {  }
        }
    }
}