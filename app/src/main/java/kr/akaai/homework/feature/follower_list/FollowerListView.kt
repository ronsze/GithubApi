package kr.akaai.homework.feature.follower_list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kr.akaai.homework.R
import kr.akaai.homework.model.github.FollowerInfo
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.ui.composables.BaseToolbar
import kr.akaai.homework.ui.composables.BaseToolbarDefaults
import kr.akaai.homework.ui.composables.LoadingBar
import kr.akaai.homework.ui.composables.PopupMessage

@Composable
fun FollowerListView(
    userInfo: UserInfo,
    viewModel: FollowerListViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    popupBackStack: () -> Unit
) {
    BackHandler {
        popupBackStack()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> viewModel.loadData(userInfo.login)
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        FollowerListViewModel.FollowerListUiState.Loading -> {
            LoadingBar()
        }
        FollowerListViewModel.FollowerListUiState.View -> {
            Column {
                Toolbar(
                    userName = userInfo.name,
                    onClickAdd = { viewModel.addFavoriteUser(userInfo) },
                    popupBackStack = popupBackStack
                )

                val followerList = viewModel.followerList
                FollowerList(
                    list = followerList,
                    navigateToDetail = navigateToDetail
                )
            }

            PopupMessage(state = viewModel.popupMessage.collectAsStateWithLifecycle().value)
        }
    }
}

@Composable
private fun Toolbar(
    userName: String,
    onClickAdd: () -> Unit,
    popupBackStack: () -> Unit
) {
    BaseToolbar(
        frontComposable = BaseToolbarDefaults.defaultToolbarImage(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            onClick = popupBackStack,
            modifier = Modifier.padding(vertical = 12.dp)
        ),
        titleComposable = BaseToolbarDefaults.defaultTitle(
            title = userName
        ),
        rearComposable = {
            Text(
                text = "Add",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { onClickAdd() }
            )
        }
    )
}

@Composable
private fun FollowerList(
    list: List<FollowerInfo>,
    navigateToDetail: (String) -> Unit
) {
    val scrollState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(top = 15.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(list) {
            FollowerListItem(
                item = it,
                onClick = navigateToDetail
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun FollowerListItem(
    item: FollowerInfo,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick(item.login) }
    ) {
        GlideImage(
            model = item.avatarUrl,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = item.login,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(100.dp)
        )
    }
}