package com.example.aicommit

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.ui.Messages

class GenerateCommitAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return

        ProgressManager.getInstance().run(object : Task.Backgroundable(
            project,
            "Generating commit message",
            false
        ) {
            override fun run(indicator: ProgressIndicator) {
                try {
                    indicator.text = "Reading staged changes"
                    val diff = GitDiffUtil.getDiff(project.basePath ?: "")

                    if (diff.isBlank()) {
                        ApplicationManager.getApplication().invokeLater {
                            Messages.showInfoMessage("No staged changes", "AI Commit Generator")
                        }
                        return
                    }

                    indicator.text = "Calling local AI model"
                    val result = OllamaApi.generate(diff)

                    ApplicationManager.getApplication().invokeLater {
                        Messages.showMessageDialog(
                            result,
                            "AI Commit Message",
                            Messages.getInformationIcon()
                        )
                    }
                } catch (exception: Exception) {
                    ApplicationManager.getApplication().invokeLater {
                        Messages.showErrorDialog(
                            project,
                            exception.message ?: "Unknown error",
                            "AI Commit Generator"
                        )
                    }
                }
            }
        })
    }
}