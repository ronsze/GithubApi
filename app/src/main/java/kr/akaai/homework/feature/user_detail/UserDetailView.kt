package kr.akaai.homework.feature.user_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
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
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.ui.composables.BaseToolbar
import kr.akaai.homework.ui.composables.LoadingBar
import kr.akaai.homework.ui.composables.PopupMessage

@Composable
fun UserDetailView(
    userId: String,
    viewModel: UserDetailViewModel = hiltViewModel(),
    navigateToFollowerList: (UserInfo) -> Unit,
    navigateToGithubProfile: (String) -> Unit,
    popupBackStack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> viewModel.fetchUserInfo(userId)
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    when (uiState) {
        UserDetailViewModel.UserDetailUiState.Loading -> LoadingBar()
        UserDetailViewModel.UserDetailUiState.Failed -> Unit
        is UserDetailViewModel.UserDetailUiState.View -> View(
            userInfo = uiState.userInfo,
            addFavorite = viewModel::addFavoriteUser,
            navigateToFollowerList = navigateToFollowerList,
            navigateToGithubProfile = navigateToGithubProfile,
            popupBackStack = popupBackStack
        )
    }

    PopupMessage(state = viewModel.popupMessage.collectAsStateWithLifecycle().value)
}

@Composable
private fun View(
    userInfo: UserInfo,
    addFavorite: (UserInfo) -> Unit,
    navigateToFollowerList: (UserInfo) -> Unit,
    navigateToGithubProfile: (String) -> Unit,
    popupBackStack: () -> Unit
) {
    Box {
        Column {
            Toolbar(
                popupBackStack = popupBackStack,
                addFavorite = { addFavorite(userInfo) }
            )
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                UserProfileLayer(
                    avatarUrl = userInfo.avatarUrl,
                    login = userInfo.login,
                    name = userInfo.name,
                    location = userInfo.location
                )
                Spacer(modifier = Modifier.height(30.dp))

                BoxLayer(
                    leftPlaceHolder = "Following",
                    leftText = userInfo.following.toString(),
                    rightPlaceHolder = "Followers",
                    rightText = userInfo.followers.toString(),
                    buttonText = "Get Followers",
                    onClickButton = { navigateToFollowerList(userInfo) }
                )
                Spacer(modifier = Modifier.height(30.dp))

                BoxLayer(
                    leftPlaceHolder = "Gits",
                    leftText = userInfo.publicGits.toString(),
                    rightPlaceHolder = "Repos",
                    rightText = userInfo.publicRepos.toString(),
                    buttonText = "Github Profile",
                    onClickButton = { navigateToGithubProfile(userInfo.login) }
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    popupBackStack: () -> Unit,
    addFavorite: () -> Unit
) {
    BaseToolbar(
        frontComposable = {
            Text(
                text = "Done",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { popupBackStack() }
            )
        },
        rearComposable = {
            Text(
                text = "Add",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { addFavorite() }
            )
        }
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun UserProfileLayer(
    avatarUrl: String,
    login: String,
    name: String,
    location: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            model = avatarUrl,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = login,
                fontSize = 20.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = name,
                fontSize = 20.sp,
                color = Color.LightGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = location,
                fontSize = 20.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun BoxLayer(
    leftPlaceHolder: String,
    leftText: String,
    rightPlaceHolder: String,
    rightText: String,
    buttonText: String,
    onClickButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Row {
            Text(
                text = leftPlaceHolder,
                fontSize = 15.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = rightPlaceHolder,
                fontSize = 15.sp,
                color = Color.Black,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            Text(
                text = leftText,
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = rightText,
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(45.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue)
            ),
            onClick = onClickButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Text(
                text = buttonText,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}