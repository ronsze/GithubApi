package kr.akaai.homework.feature.favorite_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.base.context_view.BaseFragment
import kr.akaai.homework.databinding.FragmentFavoriteUserBinding
import kr.akaai.homework.feature.favorite_user.adapter.FavoriteUserAdapter

@AndroidEntryPoint
class FavoriteUserFragment : BaseFragment<FragmentFavoriteUserBinding, FavoriteUserViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() = FavoriteUserFragment()
    }

    override val viewModel: FavoriteUserViewModel by viewModels()

    private val favoriteUserAdapter = FavoriteUserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel

        initView()
        viewModel.loadData()
        return binding.root
    }

    override fun observeViewModel() {
        viewModel.loadFavoriteUserListEvent.observe(this) {
            favoriteUserAdapter.setFavoriteUserList(viewModel.favoriteUserList)
            favoriteUserAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        binding.favoriteRecycler.adapter = favoriteUserAdapter
    }
}