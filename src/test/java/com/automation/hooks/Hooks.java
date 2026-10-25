package com.automation.hooks;
import com.automation.driver.DriverFactory;
import io.cucumber.java.*;
import org.openqa.selenium.*;
public class Hooks {
    @Before
    public void startBrowser() { DriverFactory.start(); }
    @After
    public void finishScenario(Scenario scenario) {
        try {
            if (scenario.isFailed() && DriverFactory.isStarted()) {
                try {
                    byte[] image = ((TakesScreenshot) DriverFactory.get()).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(image, "image/png", "Failure screenshot");
                } catch (RuntimeException error) { scenario.log("Screenshot unavailable: " + error.getMessage()); }
            }
        } finally { DriverFactory.stop(); }
    }
}
