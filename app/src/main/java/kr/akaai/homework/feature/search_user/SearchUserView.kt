package kr.akaai.homework.feature.search_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.akaai.homework.R
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.ui.composables.LoadingBar
import kr.akaai.homework.ui.composables.PopupMessage

@Composable
fun SearchUserView(
    viewModel: SearchUserViewModel = hiltViewModel(),
    navigateToMain: (UserInfo) -> Unit
) {
    View(
        viewModel = viewModel
    )

    Box {
        val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is SearchUserViewModel.SearchUserUiState.UserSearched -> {
                    navigateToMain(uiState.userInfo)
                    viewModel.resetUiState()
                }
                else -> Unit
            }
        }

        PopupMessage(state = viewModel.popupMessage.collectAsStateWithLifecycle().value)
        if (viewModel.loadingBarVisible.collectAsStateWithLifecycle().value) LoadingBar()
    }
}

@Composable
private fun View(
    viewModel: SearchUserViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_github),
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))

        val keyboardController = LocalSoftwareKeyboardController.current
        var userId by remember { mutableStateOf("") }
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            textStyle = TextStyle.Default.copy(
                fontSize = 25.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions {
                viewModel.searchUser(userId)
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp)
        )
        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { viewModel.searchUser(userId) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 60.dp)
        ) {
            Text(
                text = "Search",
                fontSize = 20.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun Preview() {
    SearchUserView {}
}