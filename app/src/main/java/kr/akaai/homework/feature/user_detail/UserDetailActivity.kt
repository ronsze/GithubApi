package kr.akaai.homework.feature.user_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.Constants.GITHUB_BASE_URL
import kr.akaai.homework.Constants.GITHUB_URL
import kr.akaai.homework.Constants.USER_ID
import kr.akaai.homework.Constants.USER_INFO
import kr.akaai.homework.R
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivityUserDetailBinding
import kr.akaai.homework.feature.github_webview.GithubWebViewActivity
import kr.akaai.homework.feature.main.MainActivity
import kr.akaai.homework.feature.search_user.SearchUserViewModel
import kr.akaai.homework.model.github.UserInfo

@AndroidEntryPoint
class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>(ActivityUserDetailBinding::inflate) {
    override val viewModel: UserDetailViewModel by viewModels()

    override fun afterBinding() {
        binding.viewModel = viewModel
        viewModel.fetchUserInfo(intent.getStringExtra(USER_ID).toString())
    }

    override fun observeViewModel() {
        viewModel.event.observe(this) {
            handleEvent(it)
        }
    }

    private fun handleEvent(event: UserDetailViewModel.UserDetailEvent) {
        when (event) {
            UserDetailViewModel.UserDetailEvent.NavToMain -> viewModel.userInfo.value?.run { navToMain(this) }
            UserDetailViewModel.UserDetailEvent.NavToGithub -> viewModel.userInfo.value?.run { navToGithub(login) }
            UserDetailViewModel.UserDetailEvent.Finish -> finish()
        }
    }

    private fun navToMain(userInfo: UserInfo) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(USER_INFO, userInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivitySlide(intent)
    }

    private fun navToGithub(userId: String) {
        val intent = Intent(this, GithubWebViewActivity::class.java)
        intent.putExtra(GITHUB_URL, "$GITHUB_BASE_URL/$userId")
        startActivitySlide(intent)
    }

    override fun finish() = backSlideFinish()
}