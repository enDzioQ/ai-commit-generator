package com.example.aicommit

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class GenerateCommitAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return

        val diff = GitDiffUtil.getDiff(project.basePath ?: "")

        if (diff.isBlank()) {
            Messages.showInfoMessage("No staged changes", "AI Commit Generator")
            return
        }

        val result = OllamaApi.generate(diff)

        Messages.showMessageDialog(
            result,
            "AI Commit Message",
            Messages.getInformationIcon()
        )
    }
}