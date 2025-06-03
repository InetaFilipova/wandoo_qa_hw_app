# Wandoo QA HW App

## Overview
This project is a Java-based end-to-end (E2E) test automation suite using Cucumber (BDD), JUnit, Rest Assured, and modular JSON test data. It is designed to test API endpoints and user flows for the Wandoo application.

## Features
- **Cucumber BDD**: Write human-readable feature files and step definitions.
- **JUnit**: Test runner integration.
- **Rest Assured**: API testing made simple.
- **Modular Test Data**: Test data organized by domain for maintainability.
- **Dependency Injection**: PicoContainer for state sharing between steps.
- **Environment & Endpoint Configuration**: All base URLs and endpoints are managed via Maven properties and profiles, and passed as system properties to tests.

## Project Structure
```
├── pom.xml
├── README.md
├── src
│   ├── main
│   └── test
│       ├── java
│       │   └── e2e
│       │       ├── steps         # Step definitions (Cucumber glue code)
│       │       └── utils         # Test utilities and shared context
│       └── resources
│           ├── features         # Gherkin feature files
│           └── testdata         # Modular JSON test data
```

## Getting Started

### Prerequisites
- Java 17 or higher (Java 23 set in pom.xml)
- Maven 3.6+

### Setup
1. Clone the repository:
   ```sh
   git clone <repo-url>
   cd wandoo_qa_hw_app
   ```
2. Install dependencies:
   ```sh
   mvn clean install
   ```

### Running Tests
To execute all Cucumber scenarios with default endpoints and local base URL:
```sh
mvn test -Plocal
```

- The `local` Maven profile sets `base.url` to `http://localhost:8080`.
- All endpoint paths (e.g. `/public/sign-up`) are defined in `pom.xml` under `<properties>`.
- The Maven Surefire plugin is configured to pass these as system properties to your tests.

#### Overriding Endpoints or Base URL
You can override any property at runtime:
```sh
mvn test -Plocal -Dendpoint.signup=/custom-signup -Dbase.url=https://api.staging.example.com
```

### Test Data
- Modular JSON files are in `src/test/resources/testdata/`.
- Edit or add files to manage data for specific domains (e.g., registration, personal data, account).

### Adding/Editing Tests
- Feature files: `src/test/resources/features/`
- Step definitions: `src/test/java/e2e/steps/`
- Utilities/context: `src/test/java/e2e/utils/`

### Dependency Injection
- PicoContainer is used for constructor injection in step definitions to share state between steps.

### Configuration Management
- All configuration is managed via Maven properties and profiles.
- No Java code contains hardcoded URLs or endpoints.
- To add a new environment, create a new Maven profile in `pom.xml` with the desired properties.

### Troubleshooting
- If you see errors like `MalformedURLException: ...null`, ensure all required endpoint properties are set (either in `pom.xml` or via `-D` flags).
- Properties in `<properties>` are only available to Java via `System.getProperty` if included in the `systemPropertyVariables` of the Surefire plugin.

## Best Practices
- Keep test data modular and environment-agnostic.
- Use Maven profiles for environment-specific configuration.
- Avoid hardcoding any URLs or endpoints in Java code.
- Use constructor injection for shared state.

## Future Perspectives
- Automatically download and validate against API specifications (OpenAPI/Swagger).
- Use API specs for schema validation and test/data generation.
- Add more advanced reporting and parallel execution support.
