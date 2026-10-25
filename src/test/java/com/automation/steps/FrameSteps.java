package com.automation.steps;
import com.automation.config.ConfigReader;
import com.automation.driver.DriverFactory;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import static org.junit.Assert.*;
public class FrameSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    @Given("I am on the nested frames page")
    public void openFrames() {
        driver = DriverFactory.get();
        wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.positiveInt("explicit.wait.seconds")));
        driver.get(ConfigReader.get("base.url") + "/nested_frames");
    }
    @When("I switch to the top frame and then the middle frame")
    public void enterNestedFrame() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frame-top"));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frame-middle"));
    }
    @When("I return to the main document")
    public void returnToMain() { driver.switchTo().defaultContent(); }
    @When("I switch to the bottom frame")
    public void enterBottom() { wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frame-bottom")); }
    @Then("the current frame body should contain {string}")
    public void verifyFrameText(String expected) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), expected));
        assertEquals(expected, driver.findElement(By.tagName("body")).getText().trim());
    }
}
