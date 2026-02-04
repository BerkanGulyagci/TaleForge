package com.berkang.storyteler.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.berkang.storyteler.presentation.screens.home.HomeScreen
import com.berkang.storyteler.presentation.screens.story_player.StoryPlayerScreen
import com.berkang.storyteler.presentation.screens.story_setup.StorySetupScreen

@Composable
fun StoryTelerNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToStoryPlayer = { prompt ->
                    navController.navigate("${Screen.StoryPlayer.route}/$prompt")
                },
                onNavigateToLibrary = {
                    navController.navigate(Screen.Library.route)
                }
            )
        }
        
        composable(Screen.StorySetup.route) {
            StorySetupScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToStoryPlayer = { prompt ->
                    navController.navigate("${Screen.StoryPlayer.route}/$prompt")
                }
            )
        }
        
        /* CharacterSelect removed as per user request to simplify flow */
        
        composable(
            route = "${Screen.StoryPlayer.route}/{prompt}",
            arguments = listOf(
                androidx.navigation.navArgument("prompt") { type = androidx.navigation.NavType.StringType }
            )
        ) {
            StoryPlayerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Library.route) {
            com.berkang.storyteler.presentation.screens.library.LibraryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onStoryClick = { story ->
                    // Şimdilik sadece StoryPlayer'a gidiyoruz. 
                    // Navigasyon: Saved story ID'sini gönder
                    navController.navigate("${Screen.StoryPlayer.route}/history:${story.id}")
                }
            )
        }
    }
}