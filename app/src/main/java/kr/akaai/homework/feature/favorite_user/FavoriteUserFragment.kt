package kr.akaai.homework.feature.favorite_user

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.base.context_view.BaseFragment
import kr.akaai.homework.databinding.FragmentFavoriteUserBinding
import kr.akaai.homework.feature.favorite_user.adapter.FavoriteUserAdapter

@AndroidEntryPoint
class FavoriteUserFragment : BaseFragment<FragmentFavoriteUserBinding, FavoriteUserViewModel>(FragmentFavoriteUserBinding::inflate) {
    override val viewModel: FavoriteUserViewModel by viewModels()
    private val favoriteUserAdapter = FavoriteUserAdapter()

    override fun afterBinding() {
        binding.viewModel = viewModel
        initView()
        viewModel.loadData()
    }

    override fun observeViewModel() {
        viewModel.favoriteUserList.observe(this) {
            favoriteUserAdapter.submitList(it)
        }
    }

    private fun initView() {
        binding.favoriteRecycler.adapter = favoriteUserAdapter
    }
}