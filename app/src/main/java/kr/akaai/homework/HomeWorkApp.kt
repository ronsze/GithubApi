package kr.akaai.homework

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.google.gson.Gson
import kr.akaai.homework.Constants.GITHUB_BASE_URL
import kr.akaai.homework.feature.github_webview.GithubWebView
import kr.akaai.homework.feature.home.HomeView
import kr.akaai.homework.feature.search_user.SearchUserView
import kr.akaai.homework.feature.user_detail.UserDetailView
import kr.akaai.homework.model.github.UserInfo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeWorkApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MainDestination.SearchUser.route
    ) {
        composable(MainDestination.SearchUser.route) {
            SearchUserView(
                navigateToMain = { userInfo ->
                    navigateToFollowerList(userInfo, navController)
                }
            )
        }

        composable(
            route = "${MainDestination.Main.route}/{$USER_INFO}",
            arguments = listOf(navArgument(USER_INFO) { type = NavType.StringType })
        ) {
            val userInfo = Gson().fromJson(it.arguments?.getString(USER_INFO), UserInfo::class.java)
            HomeView(
                userInfo = userInfo,
                navigateToDetail = { userId -> navController.navigate("${MainDestination.UserDetail.route}/$userId") },
                popupBackStack = navController::popBackStack
            )
        }

        composable(
            route = "${MainDestination.UserDetail.route}/{$USER_ID}",
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) {
            val userId = it.arguments?.getString(USER_ID) ?: ""
            UserDetailView(
                userId = userId,
                navigateToFollowerList = { userInfo ->
                    navigateToFollowerList(userInfo, navController)
                },
                navigateToGithubProfile = {
                    navController.navigate("${MainDestination.GithubProfile.route}/$USER_ID")
                },
                popupBackStack = navController::popBackStack
            )
        }

        composable(
            route = "${MainDestination.GithubProfile.route}/{$USER_ID}",
            arguments = listOf(navArgument(USER_ID) { type = NavType.StringType })
        ) {
            val userId = it.arguments?.getString(USER_ID) ?: ""
            GithubWebView(githubUrl = "$GITHUB_BASE_URL/$userId")
        }
    }
}

private fun navigateToFollowerList(
    userInfo: UserInfo,
    navController: NavHostController
) {
    val data = Gson().toJson(userInfo)
    navController.navigate("${MainDestination.Main.route}/${URLEncoder.encode(data, StandardCharsets.UTF_8.toString())}") {
        popUpTo(MainDestination.SearchUser.route) {
            inclusive = false
        }
    }
}

private const val USER_INFO = "userInfo"
private const val USER_ID = "userId"

private sealed class MainDestination(val route: String) {
    data object SearchUser: MainDestination("search_user")
    data object Main: MainDestination("main")
    data object UserDetail: MainDestination("user_detail")
    data object GithubProfile: MainDestination("github_profile")
}