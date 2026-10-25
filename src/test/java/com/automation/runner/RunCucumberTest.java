package com.automation.runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = {"com.automation.steps", "com.automation.hooks"},
    plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber-report.json", "junit:target/cucumber-report.xml"},
    monochrome = true
)
public class RunCucumberTest {}
