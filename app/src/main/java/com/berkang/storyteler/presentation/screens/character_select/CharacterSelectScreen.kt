package com.berkang.storyteler.presentation.screens.character_select

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSelectScreen(
    onNavigateBack: () -> Unit,
    onNavigateToStoryPlayer: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Karakter Seçimi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎭",
                fontSize = 64.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Karakter Seçim Ekranı",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Burada masal karakteri seçilecek",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onNavigateToStoryPlayer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Masalı Başlat")
            }
        }
    }
}