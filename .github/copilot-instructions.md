# Copilot / AI agent instructions for Dependable-task-api

Repository summary
- **What:** Java Spring Boot service (README states Java + Spring Boot).
- **Where to start:** See [README.md](README.md#L1-L3) for the minimal project description.

Quick discovery steps (always run first)
- **Detect build system:** Look for `pom.xml` or `build.gradle` in repo root. Example:
  - `ls pom.xml build.gradle gradlew gradlew.bat`
  - If `pom.xml` exists: use `mvn -DskipTests package` or `mvn test`.
  - If `gradle` wrapper exists: use `./gradlew build` or `./gradlew test`.
- **Find Spring Boot entry:** Search for classes named `*Application` or annotated with `@SpringBootApplication`:
  - `grep -R "@SpringBootApplication" -n src || grep -R "class .*Application" -n src`

Project layout & conventions to expect
- **Sources:** standard Maven/Gradle layout `src/main/java` and `src/test/java`.
- **Common components:** if present, expect packages like `controller`, `service`, `repository` and configuration under `config`.
- **Properties:** check `src/main/resources/application.properties` or `application.yml` for runtime configuration and external endpoints.

Testing and run workflow
- **Run tests locally:** use the detected build tool (`mvn test` or `./gradlew test`).
- **Run app locally:** with Maven `mvn spring-boot:run` or with Gradle `./gradlew bootRun` when available.
- **Debugging:** launch the `*Application` main class with remote debug flags or use IDE run/debug configurations.

Patterns to follow when editing or adding code
- **Respect package structure:** keep classes in existing packages; mirror existing naming conventions.
- **Controller vs Service:** Keep HTTP handling in classes annotated `@RestController` and business logic in `@Service` beans.
- **Configuration:** Prefer configuration values in `application.properties`/`application.yml`, avoid hard-coding values.

Integration and external dependencies
- **Discover integrations:** Inspect `pom.xml` or `build.gradle` for external dependencies (databases, messaging, cloud SDKs).
- **Secrets & env:** Look for `application-*.yml` or environment variable usage; do not hard-code secrets in code.

Search patterns and examples (useful commands)
- Find controllers: `grep -R "@RestController\|@Controller" -n src || true`
- Find services: `grep -R "@Service" -n src || true`
- Find repository interfaces: `grep -R "extends JpaRepository\|@Repository" -n src || true`

If `.github/copilot-instructions.md` already exists
- Merge: preserve any existing bespoke guidance. Add/update short sections above only when they reflect repository facts.

Notes for reviewers
- This file is intentionally concise. If you want deeper, example-driven rules (code style, PR checks, commit message format), provide existing files or preferences and I will extend this guidance.

End of file.
