package com.mkd.topnewsindia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mkd.topnewsindia.data.model.Article
import com.mkd.topnewsindia.presentation.details.DetailsScreen
import com.mkd.topnewsindia.presentation.home.screens.HomeScreen
import com.mkd.topnewsindia.presentation.home.viewModel.HomeViewModel
import com.mkd.topnewsindia.presentation.navigation.Route
import com.mkd.topnewsindia.presentation.webPage.WebPage
import com.mkd.topnewsindia.ui.theme.TopNewsIndiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash Screen
        installSplashScreen()

        //Set Content View
        setContent {
            TopNewsIndiaTheme {

                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.HomeScreen.route,
                ) {
                    composable(route = Route.HomeScreen.route) {
                        HomeScreen(articles = articles, navigateToDetails = { article ->
                            navigateToDetails(
                                navController = navController, article = article
                            )
                        })
                    }

                    composable(route = Route.DetailsScreen.route) {
                        navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                            ?.let { article ->
                                DetailsScreen(article = article,
                                    navigateUp = { navController.navigateUp() },
                                    navigateToWebView = { _ ->
                                        navigateToWebView(
                                            navController = navController, article = article
                                        )
                                    })
                            }
                    }

                    composable(route = Route.WebPage.route) {
                        navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                            ?.let { article ->
                                WebPage(
                                    article = article
                                )
                            }
                    }
                }
            }
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}

private fun navigateToWebView(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.WebPage.route
    )
}