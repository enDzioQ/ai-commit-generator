# AI Commit Generator

An IntelliJ Platform plugin that generates commit message suggestions from staged Git changes in the current repository. In practice, the plugin reads `git diff --cached`, sends it to a local Ollama instance, and returns a ready-to-use commit message suggestion.

## How it works

1. Stage the files you want to commit with `git add`.
2. Open the action from the IntelliJ Tools menu: `Generate AI Commit Message`.
3. The plugin reads only the staged diff.
4. The diff is sent to the local Ollama API at `http://127.0.0.1:11434/api/generate`.
5. The generated commit message is shown in a dialog.

If there are no staged changes, the plugin shows a message saying there is nothing to generate.

## Requirements

- IntelliJ IDEA with IntelliJ Platform Plugin support.
- Git available on the system `PATH`.
- Ollama running locally.
- A local model installed: `llama3.2`.

The project was tested with Ollama client `0.6.1` and the `llama3.2` model.

## Local setup

1. Clone the repository and open it in IntelliJ IDEA.
2. Make sure Ollama is running locally and the model is installed:

```powershell
ollama pull llama3.2
ollama serve
```

3. From the project root, launch the plugin sandbox IDE:

```powershell
.\gradlew.bat runIde
```

4. In the sandbox IDE, stage a few changes with `git add`.
5. Open the Tools menu and select `Generate AI Commit Message`.

## Build

If you only want to verify that the project compiles, run:

```powershell
.\gradlew.bat build
```

## Ollama configuration

The plugin uses the local Ollama endpoint:

```text
http://127.0.0.1:11434/api/generate
```

By default, the plugin sends requests to `llama3.2`. If you want to use a different model, change the model name in the code or move it into plugin configuration.

## Project structure

- `src/main/kotlin` - plugin logic.
- `src/main/resources/META-INF/plugin.xml` - action registration and plugin metadata.
- `build.gradle.kts` - build and dependency configuration.
- `settings.gradle.kts` - Gradle project name.

## Troubleshooting

- If the plugin does not generate anything, make sure you have staged changes.
- If you get a connection error, make sure Ollama is running locally and listening on port `11434`.
- If Ollama reports a missing model, pull `llama3.2` or update the model name in `OllamaApi.kt`.
- If `git diff` takes too long, check that the repository is opened correctly and that Git works in your terminal.
