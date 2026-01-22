package com.berkang.storyteler.presentation.screens.create_story

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berkang.storyteler.domain.model.StoryGenre
import com.berkang.storyteler.domain.model.StoryLength
import com.berkang.storyteler.domain.model.StoryRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStoryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCharacterSelection: (StoryRequest) -> Unit,
    viewModel: CreateStoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yeni Masal Oluştur") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Masal konusu
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Masal Konusu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.topic,
                        onValueChange = viewModel::updateTopic,
                        placeholder = { Text("Örn: Uzayda macera, sihirli orman...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            }
            
            // Tür seçimi
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Masal Türü",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Column(modifier = Modifier.selectableGroup()) {
                        StoryGenre.values().forEach { genre ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (uiState.selectedGenre == genre),
                                        onClick = { viewModel.updateGenre(genre) },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (uiState.selectedGenre == genre),
                                    onClick = null
                                )
                                Text(
                                    text = genre.displayName,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // Uzunluk seçimi
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Masal Uzunluğu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Column(modifier = Modifier.selectableGroup()) {
                        StoryLength.values().forEach { length ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (uiState.selectedLength == length),
                                        onClick = { viewModel.updateLength(length) },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (uiState.selectedLength == length),
                                    onClick = null
                                )
                                Column(modifier = Modifier.padding(start = 8.dp)) {
                                    Text(text = length.displayName)
                                    Text(
                                        text = "~${length.estimatedMinutes} dakika",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Yaş seçimi
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Hedef Yaş: ${uiState.targetAge}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Slider(
                        value = uiState.targetAge.toFloat(),
                        onValueChange = { viewModel.updateAge(it.toInt()) },
                        valueRange = 3f..12f,
                        steps = 8
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("3 yaş", fontSize = 12.sp)
                        Text("12 yaş", fontSize = 12.sp)
                    }
                }
            }
            
            // Ek notlar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Ek Notlar (İsteğe bağlı)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.additionalNotes,
                        onValueChange = viewModel::updateNotes,
                        placeholder = { Text("Özel istekleriniz...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            }
            
            // Devam et butonu
            Button(
                onClick = {
                    viewModel.createStoryRequest()?.let { request ->
                        onNavigateToCharacterSelection(request)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = viewModel.isFormValid(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Karakter Seç ve Devam Et",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}