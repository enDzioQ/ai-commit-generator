package com.example.aicommit

import java.io.File

object GitDiffUtil {

    fun getDiff(projectPath: String): String {

        val process = ProcessBuilder(
            "git", "diff", "--cached"
        )
            .directory(File(projectPath))
            .start()

        return process.inputStream.bufferedReader().readText()
    }
}