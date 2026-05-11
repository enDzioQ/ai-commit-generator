# AI Commit Generator — IntelliJ Plugin

Overview
- This IntelliJ plugin generates suggested commit messages using AI.

Repository contents
- `src/main/kotlin` — plugin source code.
- `src/main/resources` — resources and `META-INF/plugin.xml`.
- Build files: `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, the `gradle/` folder, and Gradle wrappers (`gradlew`, `gradlew.bat`).
- `.github` and `.gitignore` — CI configuration and ignore rules.

Removed items
- Archived and temporary build/cache directories were removed (`build/`, `.gradle/`, `.kotlin/`, `.run/`, `.idea/`, `.intellijPlatform/`).
- Tests (`src/test`) and the `LICENSE` file were removed at the user's request.

Build and run (local)
1. From the repository root, run:

```powershell
.\gradlew.bat build
.\gradlew.bat runIde
```

2. In the IDE: install the plugin from the Run configuration (Run › Run Plugin).
