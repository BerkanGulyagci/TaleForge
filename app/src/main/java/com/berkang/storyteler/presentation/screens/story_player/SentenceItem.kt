package com.berkang.storyteler.presentation.screens.story_player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SentenceItem(
    sentence: String,
    isReading: Boolean,
    isRead: Boolean
) {
    val backgroundColor = when {
        isReading -> MaterialTheme.colorScheme.primaryContainer
        isRead -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surface
    }
    
    val textColor = when {
        isReading -> MaterialTheme.colorScheme.onPrimaryContainer
        isRead -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    // Scale animation for reading sentence
    val scale by animateFloatAsState(
        targetValue = if (isReading) 1.02f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "sentenceScale"
    )
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        border = if (isReading) {
            androidx.compose.foundation.BorderStroke(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else null,
        tonalElevation = if (isReading) 6.dp else if (isRead) 1.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Reading indicator - animated pulse
            if (isReading) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            } else if (isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            } else {
                Spacer(modifier = Modifier.width(12.dp))
            }
            
            Text(
                text = sentence,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 26.sp,
                color = textColor,
                fontWeight = when {
                    isReading -> FontWeight.Bold
                    isRead -> FontWeight.Normal
                    else -> FontWeight.Normal
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
