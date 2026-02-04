package com.berkang.storyteler.data.prompt

import javax.inject.Inject

class PromptBuilder @Inject constructor() {

    fun build(userPrompt: String): String {
        return """
            You are a creative storyteller AI.
            
            TASK:
            Create a unique, engaging, and complete story based on the following input.
            
            USER INPUT:
            "$userPrompt"
            
            INSTRUCTIONS:
            - Write ONLY the story.
            - Do not include any meta-text, introductions, or explanations.
            - Ensure the content is safe and suitable for all audiences.
            - If the input is empty or meaningless, write a fun random fairy tale.
        """.trimIndent()
    }
}
