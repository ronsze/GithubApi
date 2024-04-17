package kr.akaai.homework.feature.search_user

import android.content.Intent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.Constants.USER_INFO
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivitySearchUserBinding
import kr.akaai.homework.feature.main.MainActivity
import kr.akaai.homework.model.github.UserInfo

@AndroidEntryPoint
class SearchUserActivity : BaseActivity<ActivitySearchUserBinding, SearchUserViewModel>(ActivitySearchUserBinding::inflate) {
    override val viewModel: SearchUserViewModel by viewModels()

    override fun afterBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun observeViewModel() {
        viewModel.event.observe(this) {
            handleEvent(it)
        }
    }

    private fun handleEvent(event: SearchUserViewModel.SearchUserEvent) {
        when (event) {
            SearchUserViewModel.SearchUserEvent.OnClickSearch -> viewModel.searchUser(binding.userIdField.text.toString())
            is SearchUserViewModel.SearchUserEvent.NavToMain -> navToMain(event.userInfo)
        }
    }

    private fun navToMain(userInfo: UserInfo) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(USER_INFO, userInfo)
        startActivitySlide(intent)
    }
}