# Selenium Cucumber Java BDD

A standalone Maven BDD project using Java, Selenium 4.50.0, Cucumber 7.20.1, and the Cucumber JUnit 4 runner (JUnit 4.13.2). This project deliberately uses JUnit 4's @RunWith integration, not the JUnit 5 launcher from the earlier Swing project. Do not mix their runner imports.

## Requirements
- Full JDK 21 (sources target Java 17+).
- Maven 3.9.x. Verify `java -version`, `javac -version`, `mvn -version`.
- Chrome, or Firefox if selected in configuration.
- Internet for Maven dependencies, Selenium Manager driver download, and the public practice website.

## Eclipse
File > Import > Maven > Existing Maven Projects; select the folder containing pom.xml. Then Maven > Update Project. Right-click RunCucumberTest.java > Run As > JUnit Test. Choose JUnit 4 if asked. No TestNG plugin is needed. An optional Cucumber editor plugin provides feature syntax highlighting, but is not required to run the JUnit runner.

## Command Prompt
Run commands in the folder containing pom.xml:
```bat
mvn clean test
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@login"
mvn test -Dcucumber.filter.tags="@frames"
mvn test -Dcucumber.filter.tags="@regression and not @frames"
mvn test -Dheadless=true
mvn test -Dbrowser=firefox
mvn test -Dcucumber.execution.dry-run=true
```
In PowerShell, quote complete -D arguments, for example:
```powershell
mvn test '-Dcucumber.filter.tags=@smoke'
mvn test '-Dcucumber.execution.dry-run=true'
```
A dry run validates Gherkin-to-Java bindings without opening browsers or executing assertions. It does not prove website behavior. Tests run sequentially by default, with a new browser for every scenario/example row. No parallel execution is configured.

## Reports
After execution open target/cucumber-report.html. Failed-scenario screenshots are embedded in that report (when the browser is available). JSON/XML output is under target. Maven Surefire results are in target/surefire-reports. `mvn clean` removes previous output. Reports contain the public demo credentials present in feature steps; never use real secrets in committed feature files.

## BDD structure
- features/login.feature: Given/When/Then, Background, Scenario Outline, Examples, tags.
- features/frames.feature: nested browser frames and defaultContent switching.
- steps/: Java implementations of the Gherkin steps and assertions.
- pages/LoginPage.java: page actions, locators, explicit waits.
- hooks/Hooks.java: browser start, failure screenshot, cleanup.
- driver/DriverFactory.java: Chrome/Firefox lifecycle, Selenium Manager, per-thread browser storage.
- config/ConfigReader.java: classpath configuration with -D overrides.
- runner/RunCucumberTest.java: feature/glue discovery and report plugins.

There are six executable scenarios: one valid login, three invalid login examples, and two frame scenarios. Sample credentials belong to https://the-internet.herokuapp.com. External website availability/locator changes can cause browser failures.

## Adding a scenario
1. Write a Scenario or Scenario Outline in a .feature file under src/test/resources/features.
2. Reuse existing steps where wording and meaning match. Implement new expressions in com.automation.steps when needed.
3. Put locators/actions in a page object and business assertions in step definitions.
4. Add a tag if desired and run the runner or Maven command.

## Troubleshooting
- mvn not recognised: install Maven and add its bin folder to PATH, then open a new terminal; alternatively use Eclipse Run As > Maven test.
- Undefined steps: check step wording, expression parameters, and runner glue packages.
- Driver startup error: allow Selenium Manager downloads and remove stale webdriver.chrome.driver settings.
- Missing config: confirm src/test/resources is a test resource folder.
- The runner uses JUnit 4 annotations; select JUnit 4 in Eclipse.
