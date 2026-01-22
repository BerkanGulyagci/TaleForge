package com.berkang.storyteler.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.berkang.storyteler.presentation.screens.character_select.CharacterSelectScreen
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
                onNavigateToStorySetup = {
                    navController.navigate(Screen.StorySetup.route)
                }
            )
        }
        
        composable(Screen.StorySetup.route) {
            StorySetupScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCharacterSelect = {
                    navController.navigate(Screen.CharacterSelect.route)
                }
            )
        }
        
        composable(Screen.CharacterSelect.route) {
            CharacterSelectScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToStoryPlayer = {
                    navController.navigate(Screen.StoryPlayer.route)
                }
            )
        }
        
        composable(Screen.StoryPlayer.route) {
            StoryPlayerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}