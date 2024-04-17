package kr.akaai.homework.feature.main

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.Constants.USER_INFO
import kr.akaai.homework.R
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivityMainBinding
import kr.akaai.homework.feature.favorite_user.FavoriteUserFragment
import kr.akaai.homework.feature.follower_list.FollowerListFragment
import kr.akaai.homework.model.github.UserInfo

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {
    override val viewModel: MainViewModel by viewModels()
    private var currentFragmentTag: String? = null

    override fun afterBinding() {
        binding.viewModel = viewModel
        viewModel.currentUserInfo = intent.getSerializableExtra(USER_INFO) as UserInfo
        initBottomNavigation()
    }

    override fun observeViewModel() {}

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            changeFragment(it.itemId)
            true
        }
        binding.bottomNavigation.selectedItemId = R.id.menu_followers
    }

    private fun changeFragment(page: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(page.toString())
        if (fragment != null) {
            replaceFragment(fragment)
        } else {
            val newFragment = when (page) {
                R.id.menu_followers -> FollowerListFragment()
                R.id.menu_favorite -> FavoriteUserFragment()
                else -> FollowerListFragment()
            }
            initFragment(newFragment, page.toString())
        }
    }

    private fun initFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, tag).commit()
        currentFragmentTag = fragment.tag
        hideFragments()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().show(fragment).commit()
        currentFragmentTag = fragment.tag
        hideFragments()
    }

    private fun hideFragments() {
        supportFragmentManager.fragments.forEach {
            if (it.tag != currentFragmentTag && !it.isHidden) supportFragmentManager.beginTransaction().hide(it).commit()
        }
    }

    override fun finish() = backSlideFinish()
}