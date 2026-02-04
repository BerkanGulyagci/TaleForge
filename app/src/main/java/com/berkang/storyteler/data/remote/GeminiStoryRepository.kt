package com.berkang.storyteler.data.remote

import android.net.Uri
import android.util.Log
import com.berkang.storyteler.data.prompt.PromptBuilder
import com.berkang.storyteler.domain.model.Story
import com.berkang.storyteler.domain.model.StoryGenre
import com.berkang.storyteler.domain.model.StoryLength
import com.berkang.storyteler.domain.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiStoryRepository @Inject constructor(
    private val promptBuilder: PromptBuilder
) : StoryRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val apiKey = "YOUR_API_KEY" // API Key removed for security
private val model = "gemini-flash-latest"
    private val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"

    override suspend fun generateStory(userPrompt: String): Story = withContext(Dispatchers.IO) {
        val finalPrompt = promptBuilder.build(userPrompt)
        
        var title = "Yeni Masal"
        var content = "Bilinmeyen Masal"

        try {
            val jsonBody = JSONObject().apply {
                put("contents", org.json.JSONArray().put(
                    JSONObject().put("parts", org.json.JSONArray().put(
                        JSONObject().put("text", finalPrompt)
                    ))
                ))
            }

            val request = Request.Builder()
                .url(apiUrl)
                .addHeader("Content-Type", "application/json")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string() ?: ""
                Log.d("GeminiRepo", "Raw Response: $responseBody")

                if (response.isSuccessful) {
                    val rawText = parseGeminiResponse(responseBody)
                    val cleanJson = rawText
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()
                    
                    try {
                        // Basit bir title/content ayırımı yapmayı dene, yoksa raw text kullan
                        // PromptBuilder JSON istemediği için (sadece text istiyor),
                        // gelen cevap muhtemelen düz metin olacaktır.
                        // Eğer JSON gelirse parse etmeyi deneriz.
                        if (cleanJson.startsWith("{")) {
                             val json = JSONObject(cleanJson)
                             title = json.optString("title", "Yeni Masal")
                             content = json.optString("content", rawText)
                        } else {
                            // Düz metin geldiyse, ilk satırı başlık yap
                            val lines = cleanJson.lines()
                            if (lines.isNotEmpty()) {
                                title = lines.first().take(50) // Çok uzun başlık olmasın
                                content = cleanJson.substringAfter("\n").trim()
                            } else {
                                content = cleanJson
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("GeminiRepo", "Parse hatası: $cleanJson", e)
                        content = rawText
                    }
                } else {
                    content = "Hata: API yanıt vermedi. (${response.code})"
                }
            }
        } catch (e: Exception) {
            content = "Hata oluştu: ${e.localizedMessage}"
            e.printStackTrace()
        }

        Story(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            genre = StoryGenre.ADVENTURE,
            length = StoryLength.MEDIUM,
            targetAge = 7,
            characterId = "gemini",
            isCompleted = true,
            createdAt = System.currentTimeMillis()
        )
    }
    
    private fun parseGeminiResponse(jsonString: String): String {
        try {
            val jsonObject = JSONObject(jsonString)
            val candidates = jsonObject.optJSONArray("candidates") ?: return ""
            val firstCandidate = candidates.optJSONObject(0) ?: return ""
            val content = firstCandidate.optJSONObject("content") ?: return ""
            val parts = content.optJSONArray("parts") ?: return ""
            return parts.optJSONObject(0)?.optString("text") ?: ""
        } catch (e: Exception) {
            return ""
        }
    }

    override suspend fun saveStory(story: Story): Result<Unit> { return Result.success(Unit) }
    override suspend fun getStories(): Flow<List<Story>> { return flowOf(emptyList()) }
    override suspend fun getStoryById(id: String): Story? { return null }
    override suspend fun deleteStory(id: String): Result<Unit> { return Result.success(Unit) }
}
