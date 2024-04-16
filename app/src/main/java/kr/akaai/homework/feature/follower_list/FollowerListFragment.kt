package kr.akaai.homework.feature.follower_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.base.context_view.BaseFragment
import kr.akaai.homework.databinding.FragmentFollowerListBinding
import kr.akaai.homework.feature.search_user.SearchUserViewModel
import kr.akaai.homework.feature.user_detail.UserDetailActivity
import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo

@AndroidEntryPoint
class FollowerListFragment : BaseFragment<FragmentFollowerListBinding, FollowerListViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() = FollowerListFragment()
    }

    override val viewModel: FollowerListViewModel by viewModels()

    private lateinit var followersAdapter: FollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel

        initView()

        requireActivity().intent.apply {
            viewModel.currentUserInfo = getSerializableExtra(SearchUserViewModel.CURRENT_USER_INFO) as UserInfo
            if (viewModel.followerList.isEmpty()) {
                viewModel.setFollowerList(
                    getSerializableExtra(SearchUserViewModel.FOLLOWER_LIST) as ArrayList<FollowerInfo>
                )
            }
        }

        return binding.root
    }

    override fun observeViewModel() {
        viewModel.fetchFollowerListEvent.observe(this) {
            val lastIndex = followersAdapter.itemCount
            followersAdapter.fetchFollowerList(viewModel.followerList)
            followersAdapter.notifyItemRangeInserted(lastIndex, 15)
        }

        viewModel.showToastEvent.observe(this) {
            showToast(it)
        }

        viewModel.goToUserDetailEvent.observe(this) {
            goToUserDetail(it)
        }

        viewModel.searchFollowerFailEvent.observe(this) {
            showToast(it.message.toString())
            requireActivity().finish()
        }

        viewModel.finishEvent.observe(this) {
            requireActivity().finish()
        }
    }

    private fun initView() {
        followersAdapter = FollowersAdapter(viewModel::onClickFollowerItem)
        followersAdapter.fetchFollowerList(viewModel.followerList)

        binding.followersRecycler.adapter = followersAdapter
        binding.followersRecycler.addItemDecoration(
            FollowerListDecoration(requireContext())
        )
        binding.followersRecycler.addOnScrollListener(viewModel.onScrollListener)
    }

    private fun goToUserDetail(userId: String) {
        val intent = Intent(requireActivity(), UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.USER_ID, userId)
        startActivitySlide(intent)
    }
}