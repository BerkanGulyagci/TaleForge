package com.berkang.storyteler.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object StorySetup : Screen("story_setup")
    object CharacterSelect : Screen("character_select")
    object StoryPlayer : Screen("story_player")
}