package com.berkang.storyteler.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkang.storyteler.domain.model.StoryHistory
import com.berkang.storyteler.domain.usecase.GetStoryHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val recentStories: List<StoryHistory> = emptyList(),
    val prompt: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoryHistoryUseCase: GetStoryHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRecentStories()
    }

    private fun loadRecentStories() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val allStories = getStoryHistoryUseCase()
                // Son 2 hikayeyi al
                val recent = allStories.take(2)
                _uiState.value = _uiState.value.copy(
                    recentStories = recent,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun updatePrompt(text: String) {
        _uiState.value = _uiState.value.copy(prompt = text)
    }

    // Kategoriden prompt üretme yardımcısı
    fun getPromptForCategory(categoryName: String): String {
        return when (categoryName) {
            "Klasik" -> "Klasik bir masal anlat, prensesler, şatolar veya kuleler içersin. Geleneksel bir anlatımı olsun."
            "Doğa" -> "Doğa ile iç içe, orman, nehirler ve konuşan hayvanları konu alan huzurlu bir masal anlat."
            "Uzay" -> "Uzayda geçen, gezegenler, yıldızlar ve uzaylı dostlukları hakkında heyecanlı bir macera masalı anlat."
            "Saray" -> "Görkemli bir sarayda geçen, krallar, kraliçeler ve gizli geçitler hakkında bir masal."
            "Hayvanlar" -> "Sevimli hayvanların başrolde olduğu, dostluk ve yardımlaşma temalı eğlenceli bir masal."
            else -> "$categoryName hakkında güzel bir masal anlat."
        }
    }
    
    fun getFeaturedStoryPrompt(): String {
        return "Gizemli bir ormanda geçen, sisli tepeler ve fısıldayan ağaçlar hakkında, dostluk temalı sürükleyici bir macera masalı anlat ('Gümüş Orman'ın Gizemi')."
    }
}
