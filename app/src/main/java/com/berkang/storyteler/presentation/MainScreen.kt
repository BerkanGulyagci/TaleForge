package com.berkang.storyteler.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.berkang.storyteler.presentation.navigation.Screen
import com.berkang.storyteler.presentation.navigation.StoryTelerNavigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Yüzen Navbar, Box ile en alta sabitlenir
    Box(modifier = Modifier.fillMaxSize()) {
        // Ana İçerik
        StoryTelerNavigation(navController = navController)
        
        // Custom Floating Navbar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            FloatingBottomBar(
                currentRoute = currentDestination?.route,
                onNavigate = { route ->
                    if (route == "add_story") {
                        // Ortadaki butona basılınca direkt story setup veya input açtırabiliriz
                        // Şimdilik StorySetup ekranına gitsin
                        navController.navigate(Screen.StorySetup.route)
                    } else {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun FloatingBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    // Navbar Container
    Surface(
        modifier = Modifier
            .width(320.dp)
            .height(72.dp)
            .shadow(elevation = 20.dp, shape = RoundedCornerShape(50.dp), spotColor = Color(0x20000000)),
        shape = RoundedCornerShape(50.dp),
        color = Color.White.copy(alpha = 0.95f) // Blur efekti için alpha (backdrop blur modifier gerekir ama standart compose'da zor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            NavBarIcon(
                icon = Icons.Default.Home,
                isSelected = currentRoute == Screen.Home.route,
                onClick = { onNavigate(Screen.Home.route) }
            )
            
            // Library
            NavBarIcon(
                icon = Icons.Default.MenuBook, // Book iconu
                isSelected = currentRoute == Screen.Library.route,
                onClick = { onNavigate(Screen.Library.route) }
            )
            
            // ADD BUTTON (Floating Action Button style inside nav)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = (-20).dp) // Yukarı taşıma efekti
                    .shadow(10.dp, CircleShape, spotColor = Color(0xFF135BEC))
                    .clip(CircleShape)
                    .background(Color(0xFF135BEC)) // Primary Blue
                    .clickable { onNavigate("add_story") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            // Explore
            NavBarIcon(
                icon = Icons.Default.Explore,
                isSelected = false, // Sayfası yok henüz
                onClick = { /* TODO */ }
            )
            
            // Settings
            NavBarIcon(
                icon = Icons.Default.Settings,
                isSelected = false, // Sayfası yok henüz
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun NavBarIcon(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) Color(0xFF135BEC) else Color(0xFF94A3B8), // Selected: Primary, Unselected: Slate 400
            modifier = Modifier.size(28.dp)
        )
    }
}
