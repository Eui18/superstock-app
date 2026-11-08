package com.superstock.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.auth.screen.LoginScreen
import com.superstock.app.presentation.auth.screen.RegisterScreen
import com.superstock.app.presentation.home.screen.HomeScreen
import com.superstock.app.presentation.products.screen.ProductDetailScreen
import com.superstock.app.presentation.session.state.SessionState
import com.superstock.app.presentation.session.viewmodel.SessionViewModel

@Composable
fun SuperStockApp(
    sessionViewModel: SessionViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.sessionViewModelFactory
    )
) {
    val sessionState by sessionViewModel.sessionState.collectAsStateWithLifecycle()

    when (sessionState) {
        SessionState.Loading -> Unit
        SessionState.LoggedOut -> AuthNavHost()
        SessionState.LoggedIn -> MainNavHost()
    }
}

@Composable
private fun AuthNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.LOGIN
    ) {
        composable(AppRoute.LOGIN) {
            LoginScreen(
                onRegisterClick = { navController.navigate(AppRoute.REGISTER) }
            )
        }
        composable(AppRoute.REGISTER) {
            RegisterScreen(
                onLoginClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.HOME
    ) {
        composable(AppRoute.HOME) {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(AppRoute.productDetail(productId))
                }
            )
        }
        composable(
            route = AppRoute.PRODUCT_DETAIL,
            arguments = listOf(
                navArgument(AppRoute.PRODUCT_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt(AppRoute.PRODUCT_ID)
                ?: return@composable

            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}