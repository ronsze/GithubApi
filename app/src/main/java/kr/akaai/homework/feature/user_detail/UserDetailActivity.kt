package kr.akaai.homework.feature.user_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.R
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivityUserDetailBinding
import kr.akaai.homework.feature.github_webview.GithubWebViewActivity
import kr.akaai.homework.feature.main.MainActivity
import kr.akaai.homework.feature.search_user.SearchUserViewModel

@AndroidEntryPoint
class UserDetailActivity : BaseActivity<ActivityUserDetailBinding, UserDetailViewModel>() {
    companion object {
        const val USER_ID = "user_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        super.onCreate(savedInstanceState)
    }

    override val viewModel: UserDetailViewModel by viewModels()

    override fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.fetchUserInfo(intent.getStringExtra(USER_ID).toString())
    }

    override fun observeViewModel() {
        viewModel.finishEvent.observe(this) {
            backSlideFinish()
        }

        viewModel.showToastEvent.observe(this) {
            showToast(it)
        }

        viewModel.goToGithubEvent.observe(this) {
            goToGithub()
        }

        viewModel.goToMainEvent.observe(this) {
            goToMain()
        }

        viewModel.searchUserFailEvent.observe(this) {
            showToast(it.toString())
        }

        viewModel.searchFollowerFailEvent.observe(this) {
            showToast(it.message.toString())
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SearchUserViewModel.FOLLOWER_LIST, viewModel.followerList)
        intent.putExtra(SearchUserViewModel.CURRENT_USER_INFO, viewModel.userInfo.get())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivitySlide(intent)
    }

    private fun goToGithub() {
        val intent = Intent(this, GithubWebViewActivity::class.java)
        intent.putExtra(GithubWebViewActivity.GITHUB_URL, "https://github.com/${viewModel.userInfo.get()?.login.toString()}")
        startActivitySlide(intent)
    }

    override fun finish() {
        backSlideFinish()
    }
}