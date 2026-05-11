package com.example.aicommit

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object OllamaApi {

    private val client = OkHttpClient.Builder()
        .callTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    fun generate(diff: String): String {

        val prompt = """
            Write a short git commit message for this diff:
            
            $diff
        """.trimIndent()

        val json = JSONObject()
        json.put("model", "llama3.2:latest")
        json.put("prompt", prompt)
        json.put("stream", false)

        val body = json.toString()
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://127.0.0.1:11434/api/generate")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("Ollama request failed with HTTP ${response.code}")
            }

            val text = response.body?.string() ?: ""
            return JSONObject(text).optString("response").ifBlank {
                throw IllegalStateException("Ollama returned an empty response")
            }
        }
    }
}