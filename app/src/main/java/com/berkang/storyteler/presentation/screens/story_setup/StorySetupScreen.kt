package com.berkang.storyteler.presentation.screens.story_setup

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorySetupScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCharacterSelect: () -> Unit,
    viewModel: StorySetupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val genres = listOf("Adventure", "Fairy Tale", "Educational", "Funny", "Friendship")
    val lengths = listOf("Short", "Medium", "Long")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Story Setup") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Topic Input
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Story Topic",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.topic,
                        onValueChange = viewModel::updateTopic,
                        placeholder = { Text("e.g., Space adventure, Magic forest...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            }
            
            // Genre Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Story Genre",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Column(modifier = Modifier.selectableGroup()) {
                        genres.forEach { genre ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (uiState.genre == genre),
                                        onClick = { viewModel.updateGenre(genre) },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (uiState.genre == genre),
                                    onClick = null
                                )
                                Text(
                                    text = genre,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // Length Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Story Length",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Column(modifier = Modifier.selectableGroup()) {
                        lengths.forEach { length ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (uiState.length == length),
                                        onClick = { viewModel.updateLength(length) },
                                        role = Role.RadioButton
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (uiState.length == length),
                                    onClick = null
                                )
                                Column(modifier = Modifier.padding(start = 8.dp)) {
                                    Text(text = length)
                                    Text(
                                        text = when(length) {
                                            "Short" -> "~3 minutes"
                                            "Medium" -> "~7 minutes"
                                            "Long" -> "~15 minutes"
                                            else -> ""
                                        },
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Target Age Slider
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Target Age: ${uiState.targetAge}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Slider(
                        value = uiState.targetAge.toFloat(),
                        onValueChange = { viewModel.updateTargetAge(it.toInt()) },
                        valueRange = 3f..12f,
                        steps = 8
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("3 years", fontSize = 12.sp)
                        Text("12 years", fontSize = 12.sp)
                    }
                }
            }
            
            // Additional Notes
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Additional Notes (Optional)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.notes,
                        onValueChange = viewModel::updateNotes,
                        placeholder = { Text("Any special requests...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            }
            
            // Continue Button
            Button(
                onClick = onNavigateToCharacterSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.isValid,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Continue to Character Selection",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}