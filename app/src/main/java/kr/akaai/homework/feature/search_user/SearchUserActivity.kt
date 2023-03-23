package kr.akaai.homework.feature.search_user

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.R
import kr.akaai.homework.base.BaseActivity
import kr.akaai.homework.databinding.ActivitySearchUserBinding
import kr.akaai.homework.feature.follower_list.FollowerListFragment
import kr.akaai.homework.feature.main.MainActivity

@AndroidEntryPoint
class SearchUserActivity : BaseActivity<ActivitySearchUserBinding, SearchUserViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)
        super.onCreate(savedInstanceState)
    }

    override val viewModel: SearchUserViewModel by viewModels()

    override fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun observeViewModel() {
        viewModel.searchFollowerFailEvent.observe(this) {
            showToast(it)
        }

        viewModel.goToMainEvent.observe(this) {
            goToMain()
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SearchUserViewModel.FOLLOWER_LIST, viewModel.followerList)
        intent.putExtra(SearchUserViewModel.CURRENT_USER_INFO, viewModel.userInfo)
        startActivitySlide(intent)
    }
}