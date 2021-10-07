package StepDefinition;

import Steps.LoginSteps;
import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.WebDriver;


public class LoginStepDefinition {

    @Managed
    WebDriver driver;

    @Steps
    LoginSteps loginSteps;

    @Given("user launch the google page application")
    public void user_launch_the_google_page_application() {
        driver.manage().window().maximize();
        driver.get("https://www.google.com/");
    }
}
