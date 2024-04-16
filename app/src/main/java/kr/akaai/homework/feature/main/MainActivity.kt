package kr.akaai.homework.feature.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.R
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivityMainBinding
import kr.akaai.homework.feature.favorite_user.FavoriteUserFragment
import kr.akaai.homework.feature.follower_list.FollowerListFragment

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()

    private val followerListFragment = FollowerListFragment.newInstance()
    private val favoriteUserFragment = FavoriteUserFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        super.onCreate(savedInstanceState)
        binding.bottomNavigation.selectedItemId = R.id.menu_followers
        changePageToFollowers()
        cacheDir.deleteRecursively()
    }

    override fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initView()
    }

    private fun initView() {
        binding.bottomNavigation.setOnItemSelectedListener(viewModel.onNavigationItemClickListener)
    }

    override fun observeViewModel() {
        viewModel.changePageToFollowersEvent.observe(this) {
            changePageToFollowers()
        }

        viewModel.changePageToFavoriteUserEvent.observe(this) {
            changePageToFavoriteUser()
        }
    }

    private fun changePageToFollowers() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, followerListFragment)
            .commit()
    }

    private fun changePageToFavoriteUser() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, favoriteUserFragment)
            .commit()
    }

    override fun finish() {
        backSlideFinish()
    }
}