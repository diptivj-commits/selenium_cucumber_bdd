package com.automation.pages;
import com.automation.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By submit = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.positiveInt("explicit.wait.seconds")));
    }
    public void open() {
        driver.get(ConfigReader.get("base.url") + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(username));
    }
    public void login(String user, String pass) {
        WebElement input = driver.findElement(username); input.clear(); input.sendKeys(user);
        input = driver.findElement(password); input.clear(); input.sendKeys(pass);
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }
    public String message(String expected) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(flash, expected));
        return driver.findElement(flash).getText();
    }
    public boolean logoutVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/logout']"))).isDisplayed();
    }
    public String waitForPath(String path) {
        wait.until(d -> java.net.URI.create(d.getCurrentUrl()).getPath().equals("/" + path));
        return java.net.URI.create(driver.getCurrentUrl()).getPath();
    }
}
