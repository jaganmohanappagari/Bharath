package Steps;

import Pages.LoginPage;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Step;
import org.fluentlenium.core.annotation.Page;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    @Page
    LoginPage loginPage;

    @Managed
    WebDriver driver;

    @Step
    public void LoginToPacteraEdgeApp(String sUserName, String sPassword) {
        loginPage.txt_UserName.sendKeys(sUserName);
        loginPage.txt_Password.sendKeys(sPassword);
        loginPage.btn_Submit.click();
    }

    @Step
    public void verifyLoginPage() {
        Assert.assertTrue(loginPage.txt_UserName.isDisplayed());
    }
}
