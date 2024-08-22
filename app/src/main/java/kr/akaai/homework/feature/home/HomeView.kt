package kr.akaai.homework.feature.home

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kr.akaai.homework.R
import kr.akaai.homework.feature.favorite_user.FavoriteUserView
import kr.akaai.homework.feature.follower_list.FollowerListView
import kr.akaai.homework.model.github.UserInfo
import kr.akaai.homework.ui.theme.Purple500

@Composable
fun HomeView(
    userInfo: UserInfo,
    navigateToDetail: (String) -> Unit,
    popupBackStack: () -> Unit
) {
    BackHandler {
        popupBackStack()
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navItems = listOf(
        HomeDestination.FollowerList,
        HomeDestination.FavoriteUser
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItems.forEach {
                    val isSelected = currentRoute == it.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { navController.navigate(it.route) },
                        icon = {
                            val colorTint = if (isSelected) Purple500 else Color.LightGray
                            Image(
                                painter = painterResource(id = it.icon),
                                colorFilter = ColorFilter.tint(color = colorTint),
                                contentDescription = "",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    )
                }
            }
        }
    ) {
        HomeNavigation(
            userInfo = userInfo,
            navController = navController,
            modifier = Modifier.padding(it),
            navigateToDetail = navigateToDetail,
            popupBackStack = popupBackStack
        )
    }
}

@Composable
fun HomeNavigation(
    userInfo: UserInfo,
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    navigateToDetail: (String) -> Unit,
    popupBackStack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.FollowerList.route,
        modifier = modifier
    ) {
        composable(HomeDestination.FollowerList.route) {
            FollowerListView(
                userInfo = userInfo,
                navigateToDetail = navigateToDetail,
                popupBackStack = popupBackStack
            )
        }

        composable(HomeDestination.FavoriteUser.route) {
            FavoriteUserView(
                popupBackStack = popupBackStack
            )
        }
    }
}

sealed class HomeDestination(val route: String, @DrawableRes val icon: Int) {
    data object FollowerList: HomeDestination("follower_list", R.drawable.ic_followers)
    data object FavoriteUser: HomeDestination("favorite_user", R.drawable.ic_favorite)
}