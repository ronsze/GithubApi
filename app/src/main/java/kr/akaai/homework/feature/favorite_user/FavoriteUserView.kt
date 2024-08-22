package kr.akaai.homework.feature.favorite_user

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.ui.composables.BaseToolbar
import kr.akaai.homework.ui.composables.BaseToolbarDefaults
import kr.akaai.homework.ui.composables.LoadingBar

@Composable
fun FavoriteUserView(
    viewModel: FavoriteUserViewModel = hiltViewModel(),
    popupBackStack: () -> Unit
) {
    BackHandler {
        popupBackStack()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> { viewModel.loadData() }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Toolbar()

            val favoriteUserList by viewModel.favoriteUserList.collectAsStateWithLifecycle()
            FavoriteUserList(
                data = favoriteUserList
            )
        }

        if (viewModel.loadingBarVisible.collectAsStateWithLifecycle().value) LoadingBar()
    }
}

@Composable
private fun Toolbar() {
    BaseToolbar(
        titleComposable = BaseToolbarDefaults.defaultTitle(
            title = "Favorite"
        ),
    )
}

@Composable
private fun FavoriteUserList(
    data: List<FavoriteUserData>
) {
    LazyColumn {
        items(data) {
            HorizontalDivider(color = Color.LightGray)
            FavoriteUserItem(
                item = it
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun FavoriteUserItem(
    item: FavoriteUserData
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Spacer(modifier = Modifier.width(20.dp))

        GlideImage(
            model = item.profileImageUrl,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = item.userId,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(20.dp))
    }
}