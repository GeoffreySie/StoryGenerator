package com.example.storygen


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class StoryGenerator {
    private val apiKey = "substitute-with-api-key"
    private val apiUrl = "https://api-inference.huggingface.co/models/google/gemma-7b"

    suspend fun generateStory(prompt: String): String {
        val requestBody = JSONObject()
        requestBody.put("inputs", prompt)

        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val client = OkHttpClient()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && responseBody != null) {
                try {
                    val jsonResponse = JSONArray(responseBody)
                    jsonResponse.getJSONObject(0).getString("generated_text")
                } catch (e: Exception) {
                    "Error: Invalid JSON response"
                }
            } else {
                "Error: ${response.code} - ${response.message}"
            }
        }
    }
}
