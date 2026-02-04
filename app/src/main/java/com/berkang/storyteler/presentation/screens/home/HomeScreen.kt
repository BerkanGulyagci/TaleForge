package com.berkang.storyteler.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berkang.storyteler.domain.model.StoryHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Tema Renkleri
val PrimaryColor = Color(0xFF135BEC)
val BackgroundLight = Color(0xFFF8FAFC)
val BackgroundDark = Color(0xFF0F172A)
val Sage100 = Color(0xFFDEEDE6)
val Sage500 = Color(0xFF4D876F)
val Misty100 = Color(0xFFDAE9F0)
val Misty400 = Color(0xFF5297B8)
val Misty500 = Color(0xFF397C9C)
val Slate100 = Color(0xFFF1F5F9)
val Slate800 = Color(0xFF1E293B)

@Composable
fun HomeScreen(
    onNavigateToStoryPlayer: (String) -> Unit,
    onNavigateToLibrary: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp) // Navbar için boşluk
    ) {
        // --- Header ---
        HeaderSection()

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // --- Welcome Section ---
            Text(
                text = buildAnnotatedString {
                    append("Günaydın, bugün hangi ")
                    withStyle(style = SpanStyle(color = Misty400)) {
                        append("masalı")
                    }
                    append(" yazalım?")
                },
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                lineHeight = 36.sp
            )

            // --- AI Search Prompt Bar ---
            PromptSearchBar(
                text = uiState.prompt,
                onTextChange = { viewModel.updatePrompt(it) },
                onSubmit = {
                    if (uiState.prompt.isNotBlank()) {
                        onNavigateToStoryPlayer(uiState.prompt)
                    }
                }
            )

            // --- Featured Story ---
            FeaturedStorySection(
                onStartReading = { 
                    val prompt = viewModel.getFeaturedStoryPrompt()
                    onNavigateToStoryPlayer(prompt)
                }
            )

            // --- Categories ---
            CategoriesSection(
                onCategoryClick = { category ->
                    val prompt = viewModel.getPromptForCategory(category)
                    onNavigateToStoryPlayer(prompt)
                }
            )

            // --- Recent Tales ---
            RecentTalesSection(
                stories = uiState.recentStories,
                onStoryClick = { id ->
                    // ID formatını koruyarak (history:ID) gönderiyoruz
                    onNavigateToStoryPlayer("history:$id")
                },
                onSeeAll = onNavigateToLibrary
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar (Gradyan ile daha şık)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Sage100, Misty100)
                        )
                    )
                    .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = Sage500,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = "Hoş geldin,",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B)
                )
                Text(
                    text = "Gezgin", // Sabit isim yerine
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }
        }

        // Notification Button
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF475569)
                )
            }
        }
    }
}

@Composable
fun PromptSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Slate100, RoundedCornerShape(20.dp))
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = "AI",
            tint = Misty400,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color(0xFF334155)
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = { onSubmit() }),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "Bir ejderha ve küçük bir bulut hakkında...",
                        style = LocalTextStyle.current.copy(
                            fontSize = 14.sp,
                            color = Color(0xFF94A3B8)
                        )
                    )
                }
                innerTextField()
            }
        )
        
        Button(
            onClick = onSubmit,
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go",
                tint = Color.White
            )
        }
    }
}

@Composable
fun FeaturedStorySection(onStartReading: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Günün Masalı",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            TextButton(onClick = { /* TODO */ }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Hepsini Gör",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Misty500
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Misty500,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Featured Card - Görsel yerine canlı gradient ve ikon
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column {
                // "Görsel" Alanı
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF2C3E50), Color(0xFF4CA1AF)) // Mistik orman gradienti
                            )
                        )
                ) {
                    // Arka plan deseni veya ikonlar
                    Icon(
                        imageVector = Icons.Default.Terrain, 
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).size(80.dp),
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                    
                    // Köşeye küçük detay
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(24.dp),
                        tint = Color(0xFFFFD700).copy(alpha = 0.8f) 
                    )
                }
                
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = Sage100,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "MACERA",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF475569),
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = "• 5 dk okuma",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Gümüş Orman'ın Gizemi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Sisli tepelerin ardında saklı, fısıldayan ağaçların arasında geçen bir dostluk hikayesi.",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B),
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Button(
                        onClick = onStartReading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                    ) {
                        Text(
                            text = "Okumaya Başla",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriesSection(onCategoryClick: (String) -> Unit) {
    Column {
        Text(
            text = "Kategoriler",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(icon = Icons.Default.MenuBook, label = "Klasik", color = Misty100, iconColor = Misty500, onClick = onCategoryClick)
            CategoryItem(icon = Icons.Default.Terrain, label = "Doğa", color = Sage100, iconColor = Sage500, onClick = onCategoryClick)
            CategoryItem(icon = Icons.Default.Star, label = "Uzay", color = Color(0xFFEFF6FF), iconColor = Color(0xFF3B82F6), onClick = onCategoryClick) 
            CategoryItem(icon = Icons.Default.Home, label = "Saray", color = Color(0xFFFFF7ED), iconColor = Color(0xFFF59E0B), onClick = onCategoryClick)
            CategoryItem(icon = Icons.Default.Pets, label = "Hayvanlar", color = Color(0xFFFFF1F2), iconColor = Color(0xFFF43F5E), onClick = onCategoryClick)
        }
    }
}

@Composable
fun CategoryItem(
    icon: ImageVector,
    label: String,
    color: Color,
    iconColor: Color,
    onClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable { onClick(label) }
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(20.dp),
            color = color,
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconColor,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF334155)
        )
    }
}

@Composable
fun RecentTalesSection(
    stories: List<StoryHistory>,
    onStoryClick: (String) -> Unit,
    onSeeAll: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Son Yazdıkların",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            // Eğer liste boş değilse buton aktif olsun
            if (stories.isNotEmpty()) {
                TextButton(onClick = onSeeAll) {
                    Text(text = "Tümü", color = Misty500, fontSize = 12.sp)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (stories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Henüz kaydedilmiş masal yok.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stories.forEach { story ->
                     RecentTaleCard(
                        modifier = Modifier.weight(1f),
                        story = story,
                        onClick = { onStoryClick(story.id) }
                    )
                }
                // Eğer tek hikaye varsa düzen bozulmasın diye boşluk bırakabiliriz
                if (stories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun RecentTaleCard(
    modifier: Modifier = Modifier,
    story: StoryHistory,
    onClick: () -> Unit
) {
    // Tarihi formatla
    val dateString = try {
        val sdf = SimpleDateFormat("EEE", Locale("tr"))
        sdf.format(Date(story.createdAt)).uppercase()
    } catch (e: Exception) {
        "GÜN"
    }

    val icon = when {
        story.title.contains("Ejderha", ignoreCase = true) -> Icons.Default.Thunderstorm
        story.title.contains("Uzay", ignoreCase = true) -> Icons.Default.Star
        story.title.contains("Orman", ignoreCase = true) -> Icons.Default.Terrain
        else -> Icons.Default.Cloud
    }
    
    val (iconBg, iconColor) = if (story.title.length % 2 == 0) {
        Sage100 to Sage500
    } else {
        Misty100 to Misty500
    }

    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = story.title.ifBlank { "İsimsiz Masal" },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                maxLines = 1
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "$dateString • KAYITLI",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8)
            )
        }
    }
}
