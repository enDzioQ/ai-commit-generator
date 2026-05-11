package com.example.aicommit

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object OllamaApi {

    private val client = OkHttpClient()

    fun generate(diff: String): String {

        val prompt = """
            Write a short git commit message for this diff:
            
            $diff
        """.trimIndent()

        val json = JSONObject()
        json.put("model", "llama3")
        json.put("prompt", prompt)
        json.put("stream", false)

        val body = json.toString()
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://localhost:11434/api/generate")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            val text = response.body?.string() ?: ""
            return JSONObject(text).getString("response")
        }
    }
}