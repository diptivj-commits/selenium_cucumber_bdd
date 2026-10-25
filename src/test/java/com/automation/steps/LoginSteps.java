package com.automation.steps;
import com.automation.driver.DriverFactory;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;
public class LoginSteps {
    private LoginPage page;
    @Given("I am on the login page")
    public void openLogin() { page = new LoginPage(DriverFactory.get()); page.open(); }
    @When("I log in with username {string} and password {string}")
    public void login(String username, String password) { page.login(username, password); }
    @Then("I should see the login message {string}")
    public void verifyMessage(String expected) { assertTrue(page.message(expected).contains(expected)); }
    @Then("the logout button should be visible")
    public void verifyLogoutButton() { assertTrue(page.logoutVisible()); }
    @Then("I should be on the {string} page")
    public void verifyPage(String path) { assertEquals("/" + path, page.waitForPath(path)); }
}
