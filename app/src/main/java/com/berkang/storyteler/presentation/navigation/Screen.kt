package com.berkang.storyteler.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object StorySetup : Screen("story_setup")
    object StoryPlayer : Screen("story_player")
    object Library : Screen("library")
}