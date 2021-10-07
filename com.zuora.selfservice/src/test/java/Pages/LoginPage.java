package Pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class LoginPage extends PageObject {

    @FindBy(name="username")
    public WebElementFacade txt_UserName;

    @FindBy(name="password")
    public WebElementFacade txt_Password;

    @FindBy(name="submit")
    public WebElementFacade btn_Submit;

}
