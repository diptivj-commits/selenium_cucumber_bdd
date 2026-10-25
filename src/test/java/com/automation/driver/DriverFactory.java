package com.automation.driver;
import com.automation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import java.time.Duration;
import java.util.Locale;
public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private DriverFactory() {}
    public static void start() {
        if (DRIVER.get() != null) throw new IllegalStateException("A browser is already active");
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        WebDriver driver;
        switch (ConfigReader.get("browser").toLowerCase(Locale.ROOT)) {
            case "chrome":
                ChromeOptions chrome = new ChromeOptions();
                if (headless) chrome.addArguments("--headless=new");
                chrome.addArguments("--window-size=1440,900");
                driver = new ChromeDriver(chrome); break;
            case "firefox":
                FirefoxOptions firefox = new FirefoxOptions();
                if (headless) firefox.addArguments("-headless");
                driver = new FirefoxDriver(firefox); break;
            default: throw new IllegalArgumentException("Supported browsers: chrome, firefox");
        }
        DRIVER.set(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.positiveInt("page.load.timeout.seconds")));
    }
    public static WebDriver get() {
        WebDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("Browser not started; check Cucumber hooks");
        return driver;
    }
    public static boolean isStarted() { return DRIVER.get() != null; }
    public static void stop() {
        WebDriver driver = DRIVER.get();
        try { if (driver != null) driver.quit(); } finally { DRIVER.remove(); }
    }
}
