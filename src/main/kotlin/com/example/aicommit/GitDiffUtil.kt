package com.example.aicommit

import java.io.File
import java.util.concurrent.TimeUnit

object GitDiffUtil {

    fun getDiff(projectPath: String): String {

        val process = ProcessBuilder(
            "git", "--no-pager", "diff", "--cached"
        )
            .directory(File(projectPath))
            .redirectErrorStream(true)
            .start()

        val finished = process.waitFor(10, TimeUnit.SECONDS)

        if (!finished) {
            process.destroyForcibly()
            throw IllegalStateException("git diff timed out")
        }

        return process.inputStream.bufferedReader().readText()
    }
}