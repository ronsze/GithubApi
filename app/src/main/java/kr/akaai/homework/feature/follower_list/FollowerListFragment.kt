package kr.akaai.homework.feature.follower_list

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.Constants.USER_ID
import kr.akaai.homework.base.context_view.BaseFragment
import kr.akaai.homework.databinding.FragmentFollowerListBinding
import kr.akaai.homework.feature.follower_list.adapter.FollowerListDecoration
import kr.akaai.homework.feature.follower_list.adapter.FollowersAdapter
import kr.akaai.homework.feature.main.MainViewModel
import kr.akaai.homework.feature.user_detail.UserDetailActivity

@AndroidEntryPoint
class FollowerListFragment : BaseFragment<FragmentFollowerListBinding, FollowerListViewModel>(FragmentFollowerListBinding::inflate) {
    override val viewModel: FollowerListViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    private val followersAdapter: FollowersAdapter = FollowersAdapter(viewModel::onClickFollowerItem)

    override fun afterBinding() {
        binding.viewModel = viewModel
        initView()
        viewModel.setCurrentUser(activityViewModel.currentUserInfo)
        viewModel.loadData()
    }

    override fun observeViewModel() {
        viewModel.event.observe(viewLifecycleOwner) {
            handleEvent(it)
        }
    }

    private fun initView() {
        followersAdapter.fetchFollowerList(viewModel.followerList)

        binding.followersRecycler.adapter = followersAdapter
        binding.followersRecycler.addItemDecoration(
            FollowerListDecoration(requireContext())
        )
    }

    private fun handleEvent(event: FollowerListViewModel.FollowerListEvent) {
        when (event) {
            is FollowerListViewModel.FollowerListEvent.NavToUserDetail -> navToUserDetail(event.userId)
            FollowerListViewModel.FollowerListEvent.Finish -> requireActivity().finish()
        }
    }

    private fun navToUserDetail(userId: String) {
        val intent = Intent(requireActivity(), UserDetailActivity::class.java)
        intent.putExtra(USER_ID, userId)
        startActivitySlide(intent)
    }
}