package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementState;




public class SeleniumUtils extends PageObject {
	
	
	
	
	//#####################################################################################################################
	
	// NOTE : JAVA SCRIPT HELPER METHODS WILL BE SPECIFIC TO THE PROJECT
	
	//#####################################################################################################################

	//#######################  OBJECT SYNCHRONIZATION METHODS ###################################################################

	
	public static void  logMessage(String  message){
		System.out.println(message);
	}
	
	public boolean  waitUntillGivenWindowPresent(int Time, int NoofWindows) {
		boolean flag = false;
		Set<String> windows = getDriver().getWindowHandles();
		int windowCount = windows.size();
		for (int i = 0; i < Time; i++) {
			if (windowCount >= NoofWindows) {
				flag=true;
				System.out.println("No wait required as windowCount >= NoofWindows");
				break;
				
			} else {
				try {
					defaultWait(ProjectVariables.TImeout_2_Seconds);
					System.out.println("Waited for Sleep :" +ProjectVariables.TImeout_2_Seconds);
					flag=true;
				} catch (Exception e) {
					System.out.println("Exception Thrown"+e.toString());
					flag=false;
					
				}

			}
		}
		return flag;

	}
	
	public void resetImplicitWaitToDefault() {

		resetImplicitTimeout();
		System.out.println("Resetted Implicit Timeout");
	}
	
	public void waitForPresenceOfElement(WebElementFacade element, int TimeoutinSeconds) {
		element.withTimeoutOf(TimeoutinSeconds, TimeUnit.SECONDS).waitUntilPresent();
		System.out.println("Waited for Presence Of Element : "+ element.toString()+ "for seconds:"+ TimeoutinSeconds);

	}

	public void waitForVisibilityOfElement(WebElementFacade element, int TimeoutinSeconds) {
		element.withTimeoutOf(TimeoutinSeconds, TimeUnit.SECONDS).waitUntilVisible();
		System.out.println("Waited for Visibility Of Element :"+ element.toString()+ "for seconds:"+ TimeoutinSeconds);
	}

	public void waitForElementNoTVisible(WebElementFacade element, int TimeoutinSeconds) {
		element.withTimeoutOf(TimeoutinSeconds, TimeUnit.SECONDS).waitUntilNotVisible();
		System.out.println("Waited for Element Not Visible :"+ element.toString()+ "for seconds:"+ TimeoutinSeconds);
	}

	public void waitForElementToEnable(WebElementFacade element, int TimeoutinSeconds) {
		element.withTimeoutOf(TimeoutinSeconds, TimeUnit.SECONDS).waitUntilEnabled();
		System.out.println("Waited for Element To Enable :"+ element.toString()+ "for seconds:"+ TimeoutinSeconds);
	}

	public void waitForElementToBeClickable(WebElementFacade element, int TimeoutinSeconds) {
		element.withTimeoutOf(TimeoutinSeconds, TimeUnit.SECONDS).waitUntilClickable();
		System.out.println("waited For Element To Be Clickable :"+ element.toString()+ "for seconds:"+ TimeoutinSeconds);
	}

	public void waitForWebElements(List<WebElementFacade> elements, int TimeOutinseconds) {

		withTimeoutOf(TimeOutinseconds, TimeUnit.SECONDS).waitFor(elements);
		System.out.println("waited For WebElements in sec :" + TimeOutinseconds);
	}
	
	public static void defaultWait(long i) {
		try {
			
			Thread.sleep(TimeUnit.SECONDS.toMillis(i));
			//System.out.println("Waited for " + i + " seconds");
			
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void setGivenInmplicitWait(int TimeoutinSeconds) {

		//setImplicitTimeout(TimeoutinSeconds, TimeUnit.SECONDS);
		System.out.println("Implicit wait setted for " + TimeoutinSeconds + " seconds");
	}
	
	//#######################  OBJECT HIGHLIGHT METHODS ###################################################################
	
	public void highlightRemoveElement(WebElementFacade element) {

		for (int i = 0; i < ProjectVariables.MIN_TIME_OUT; i++) {

			evaluateJavascript("arguments[0].style.border='7px solid yellow'", element);
			System.out.println("Highlighted Element");

			evaluateJavascript("arguments[0].style.border=''", element);
			System.out.println(" Removed Highlight on Element");
		}
	}

	public void highlightElement(WebElementFacade element) {

		for (int i = 0; i < ProjectVariables.MIN_TIME_OUT; i++) {

			evaluateJavascript("arguments[0].style.border='7px solid yellow'", element);
			//System.out.println("Highlighted Element");
		}
	}
	
	public void highlightElement(String Xpath) {

		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < 2; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].style.border='7px solid black'", element);

		}
	}
	
	public void removehighlightElement(WebElementFacade element) {

		for (int i = 0; i < ProjectVariables.MIN_TIME_OUT; i++) {

			evaluateJavascript("arguments[0].style.border=''", element);
			//System.out.println(" Removed Highlight on Element");
		}
	}

	public void removehighlightElement(String Xpath) {
		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < ProjectVariables.MIN_TIME_OUT; i++) {

			evaluateJavascript("arguments[0].style.border=''", element);
			
		}
	}
		
	public void highlightElements(List<WebElementFacade> elements) {

		for (int i = 0; i < elements.size(); i++) {

			evaluateJavascript("arguments[0].style.border='7px solid yellow'", elements.get(i));
			evaluateJavascript("arguments[0].style.border=''", elements.get(i));
		}
		System.out.println("Highlighted Element");
	}

	public void removeHighlightElements(List<WebElementFacade> elements) {

		for (int i = 0; i < elements.size(); i++) {

			evaluateJavascript("arguments[0].style.border=''", elements.get(i));
		}
		System.out.println(" Removed Highlight on Element");
	}
	
	
	//#######################  OBJECT OPERATION METHODS ###################################################################

	public boolean  switchWindowUsingWindowCount(int waitTimer, int windowNum) {
		boolean flag=false;
		try{
		waitUntillGivenWindowPresent(waitTimer, windowNum);
		ArrayList<String> windowHandles = new ArrayList<String>(getDriver().getWindowHandles());
		System.out.println("Window Size :" + windowHandles.size());
		getDriver().switchTo().window(windowHandles.get(windowNum - 1).toString());
		flag= true;
		System.out.println("switched To WindowCount :"+windowNum);
		}
		catch (Exception e){
			System.out.println("Not able to Switch To window count:" + windowNum);
			flag= false;
		}
		return flag;
	}

	public boolean isFileDownloaded(String dirPath, String fileName, String ext) {
		boolean flag = false;
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			flag = false;
			System.out.println("No File downloaded");
		}

		for (int i = 0; i < files.length; i++) {

			if (files[i].getName().contains(ext) && files[i].getName().equals(fileName)) {
				flag = true;
				System.out.println("File Downloaded");
			}
		}
		return flag;
	}

	public File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	public int switchWindowCount(int waitTimer, int windowNum) 
	{
		
		waitUntillGivenWindowPresent(waitTimer, windowNum);
		ArrayList<String> windowHandles = new ArrayList<String>(getDriver().getWindowHandles());
		System.out.println("Window Size :" + windowHandles.size());
		
		return windowHandles.size();
	}



	public String[] getTextFromWebElements(List<WebElementFacade> elements) {

		String[] Text = new String[elements.size()];
		System.out.println("Elements size:"+elements.size());
		for (int i = 0; i < elements.size(); i++) {
			System.out.println(isElementDisplayed(elements.get(i)));
			Text[i] = elements.get(i).getText();
			System.out.println("Element text Added to list is :" + Text[i]);

		}
		return Text;
	}

	//#######################  OBJECT ACTIONS  METHODS ###################################################################
	
	public boolean clickGivenElement(WebElementFacade element) {
		boolean flag =false;
		try{
			
		element.click();
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Element :"+element.toString()+" Not Clicked" );
			flag =false;
		}
		return flag;
	}

	public boolean clickGivenXpath(String xpath) {
		boolean flag =false;
		try{
			
		$(xpath).withTimeoutOf(ProjectVariables.MID_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible().click();
		System.out.println("Element Clicked successfuly::"+xpath);	
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("object not found to click::"+xpath+",Exception::"+ignored);
			flag =false;
		}
		
		if(flag==false)
		{
			GenericUtils.Verify("object not found to click ==>"+xpath,false);
		}
		
		
		return flag;
	}
		
	public boolean enterTextToElement(WebElementFacade element, String text) {

		boolean flag =false;
		try{
			element.clear();
			element.sendKeys(text);
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Element :"+element.toString()+" Not Enterd text");
			flag =false;
		}
		return flag;
		
	}

	public boolean  moveToElementAndClick(WebElementFacade element) {

		

		boolean flag =false;
		try{
			withAction().moveToElement(element).click().perform();
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Element :"+element.toString()+" Not Clicked");
			flag =false;
		}
		return flag;
	}

	public boolean rightClickOnElement(WebElementFacade element) {
		
		
		boolean flag =false;
		try{
			withAction().contextClick(element).click().perform();
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Element :"+element.toString()+" Not Clicked");
			flag =false;
		}
		return flag;

		
	}

	public boolean clickAndHoldElement(WebElementFacade element) {

		
		boolean flag =false;
		try{
			withAction().clickAndHold(element).click().perform();
		
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Element :"+element.toString()+" Not able to Click and Hold");
			flag =false;
		}
		return flag;
	}

	public boolean dragAndDropByElement(WebElementFacade source, WebElementFacade target) {

	
		boolean flag =false;
		try{
			withAction().dragAndDrop(source, target).click().perform();
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Not able to dragAndDrop given  :"+source +target );
			flag =false;
		}
		return flag;
	}

	public boolean SelectDropDownByVisibleText(WebElementFacade element, String text) {

				boolean flag =false;
		try{
			element.selectByVisibleText(text);
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Not able to selectByVisibleText  :"+element +"::"+text );
			flag =false;
		}
		return flag;
	}

	public boolean SelectDropDownByValue(WebElementFacade element, String value) {

		boolean flag =false;
		try{
			element.selectByValue(value);
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Not able to SelectDropDownByValue  :"+element +"::"+value );
			flag =false;
		}
		return flag;
		
	}

	public boolean SelectDropDownByIndex(WebElementFacade element, int indexValue) {
		boolean flag =false;
		try{
			element.selectByIndex(indexValue);
			
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Not able to SelectDropDownByIndex  :"+element +"::"+indexValue );
			flag =false;
		}
		return flag;
		
	}

	public boolean UploadFile(WebElementFacade element, String filepath) {
		boolean flag =false;
		try{
			upload(filepath).to(element);
			withAction().clickAndHold(element).click().perform();
			
			
		flag =true;
		}
		catch(Exception ignored){
			System.out.println("Not able to UploadFile  :"+element +"::"+filepath );
			flag =false;
		}
		return flag;
		
	}

	

	//#######################  OBJECT VALIDATORS METHODS ###################################################################

	public boolean isElementDisplayed(WebElementFacade element) {
		
		//highlightElement(element);
		//removehighlightElement(element);
		return element.isDisplayed();
		
	}

	public boolean isElementCurrentlyVisible(WebElementFacade element) {
		
		return element.isCurrentlyVisible();
	}
	
	public boolean is_WebElement_Displayed(String Xpath) {
		try {
			boolean bstatus=false;
			
			//highlightElement(Xpath);
			//removehighlightElement(Xpath);
			bstatus=$(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).isDisplayed();
			
			if(!bstatus)
			{
				System.out.println("Object was not displayed ==>"+Xpath);
			}
			
			return bstatus;
			
		} catch (Exception e) {
			//System.out.println("Element was not displayed:"+Xpath);
			return false;
		}
	}

	public boolean isElementPresent(WebElementFacade element) {
		
		return element.isPresent();
	}

	public boolean isElementEnabled(WebElementFacade element) {
		
		return element.isEnabled();
	}

	public boolean isElementSelected(WebElementFacade element) {
		
		return element.isSelected();
	}

	public boolean isElementCurrentlyEnabled(WebElementFacade element) {
		
		return element.isCurrentlyEnabled();
	}

	public boolean isElementHasFocus(WebElementFacade element) {
		
		return element.hasFocus();
	}

	//#######################  OBJECT VALIDATION METHODS  ###################################################################
	

	public void elementShouldBeVisible(WebElementFacade element) {

		element.shouldBeVisible();
		System.out.println("elementShouldBeVisible  :"+element);
	}

	public void elementShouldBePresent(WebElementFacade element) {
		System.out.println("elementShouldBePresent  :"+element);
		element.shouldBePresent();
	}

	public void elementShouldBeEnabled(WebElementFacade element) {
		System.out.println("elementShouldBeEnabled  :"+element);
		element.shouldBeEnabled();
	}

	public void elementShouldNotBeVisible(WebElementFacade element) {
		System.out.println("elementShouldNotBeVisible  :"+element);
		element.shouldNotBeVisible();
	}

	public void elementShouldNotBePresent(WebElementFacade element) {
		System.out.println("elementShouldNotBePresent  :"+element);
		element.shouldNotBePresent();
	}

	public void elementShouldNotBeEnabled(WebElementFacade element) {
		System.out.println("elementShouldNotBeEnabled  :"+element);
		element.shouldNotBeEnabled();
	}

	//#########################################################################################################################

	public void moveToElement(String xpath) {
	
		WebElement element = getDriver().findElement(By.xpath(xpath));
		Actions builder = new Actions(getDriver());
		builder.moveToElement(element).build().perform();

	
	}	
	
	public boolean is_WebElement_Visible(String Xpath) {
		try {
			//highlightElement(Xpath);
			//removehighlightElement(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).isVisible();
		} catch (Exception e) {
			
			//System.out.println("Element not visible ===>"+Xpath+",Exception ===>"+e);
			
			
			return false;
		}
	}
	
	public String get_TextFrom_Locator(String Xpath) {
		try {
			//highlightElement(Xpath);
			//removehighlightElement(Xpath);
			String  stext=$(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.MILLISECONDS).waitUntilVisible()
					.getTextValue();
			System.out.println("Text retrieved successfully::"+stext);
			return 	stext;
		}
		catch (Exception e) {
			System.out.println("Element not found to get the text:"+Xpath);
			Assert.assertTrue("Element not found to get the text from the xpath=====>"+Xpath+",Exception ==>"+e, false);
			return null;
		}
	}
	
	public String get_TextFrom_Webelement(WebElement element) {
		try {

			return element.getText();	
		}
		catch (Exception e) {
			System.out.println("Element not found to get the text:"+element);
			Assert.assertTrue("Element not found to get the text from the xpath=====>"+element, false);
			return null;
		}
	}
	
	public void Remove_highlightElement(String Xpath) {

		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < 2; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].style.border=''", element);

		}

	}

	public boolean Click_Untill_Element_is_displayed_with_the_given_time(int waitcount,String scrollbar, String RequiredElement) 
	{
		
			int scount=0;
			if(is_WebElement_Displayed( scrollbar))
			{
				do
				{
					Doubleclick(scrollbar);
					Doubleclick(scrollbar);
					//System.out.println("scount:"+scount);
					scount=scount+1;
					//System.out.println(!(is_WebElement_Displayed( Locator2)||(scount==waitcount)));
				}while(!(is_WebElement_Displayed(RequiredElement)||(scount==waitcount)));
				
				
					
			}
			return is_WebElement_Displayed( RequiredElement);
			
			
		
	}
	
	public void Doubleclick(String xpath)
	{
		Actions action = new Actions(getDriver());
		action.moveToElement(getDriver().findElement(By.xpath(xpath))).doubleClick().build().perform();
	}
	
	public void ClickandHoldUntillTheElementisVisible(String clickableelement, String requiredelement) {

		int scount=0;
		WebElement element=getDriver().findElement(By.xpath(clickableelement));
		Actions action = new Actions(getDriver());
	   
		do
		{
			action.clickAndHold(element).build().perform();
			
			System.out.println("scount:"+scount);
			scount=scount+1;
			System.out.println(is_WebElement_Displayed(requiredelement));
		}
		
		while(!(is_WebElement_Displayed(requiredelement)||(scount==20)));
	   
	    
	    
		
	}
		
	public int get_Matching_WebElement_count(String xpath) {
		return getDriver().findElements(By.xpath(xpath)).size();
	}
	
	public void ClickHold(String xpath, int count)
	{
		
		WebElement element=getDriver().findElement(By.xpath(xpath));
		Actions action = new Actions(getDriver());
	    int j=0;
		do
		{
			action.clickAndHold(element).build().perform();
			
			j=j+1;
		}
		while(!(j==count));
		
	    
	    
	  
	}
		
	public void refreshBrowser() {
		getDriver().navigate().refresh();
	}
	
	public static void scrollingToGivenElement(WebDriver driver,String string) {
			
			WebElement element = driver.findElement(By.xpath(string));
			((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView(true);", element);
		}

	public String Get_Value_By_given_attribute(String attribute,String Xpath) {
		try {
			highlightElement(Xpath);
			removehighlightElement(Xpath);
			String value=$(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).getAttribute(attribute);
					
			return value;
		} catch (Exception e) {
			return null;
		}
	}
		
	@SuppressWarnings("deprecation")
	public boolean Enter_given_Text_Element(String xpath, String text) {
		
		try {
		$(xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).waitUntilVisible().clear();
		$(xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).waitUntilVisible().sendKeys(text);
		System.out.println("Text entered successfully::"+text);
		return true;
		}
		catch (Exception e) {
			System.out.println(text+" was unable to enter in the given Xpath:"+xpath);
			Assert.assertTrue("'"+text+"' text was unable to enter in the given Xpath:"+xpath,false);
			return false;
		}
	}
	
	public boolean Wait_Untill_Element_is_Not_displayed(int waitcount,String Locator) 
	{
		
			int scount=0;
			do
			{
				defaultWait(ProjectVariables.MIN_TIME_OUT);
				System.out.println("scount:"+scount);
				scount=scount+1;
				System.out.println((is_WebElement_Displayed(Locator)^(scount==waitcount)));
			}while((is_WebElement_Displayed(Locator)^(scount==waitcount)));
			
			/*if (is_WebElement_Displayed(Locator) == true) 
			{
				System.out.println(Locator+ " is still displayed in UI");
				
				Assert.assertTrue(Locator+ " is still displayed in UI,Expected is,it shoudnot display", false);
			}*/
			return is_WebElement_Displayed( Locator);
			
			
		
	}
	
	public boolean Wait_Untill_Element_is_displayed(int waitcount,String Locator) 
	{
		
			int scount=0;
			do
			{
				defaultWait(ProjectVariables.MIN_TIME_OUT);
				//System.out.println("scount:"+scount);
				scount=scount+1;
				//System.out.println(!(is_WebElement_Displayed( Locator)||(scount==waitcount)));
			}while(!(is_WebElement_Displayed( Locator)||(scount==waitcount)));
			
			if (is_WebElement_Displayed(Locator) == false) 
			{
				System.out.println(Locator+ " is not displayed in UI");
				
				//Assert.assertTrue(Locator+ " is not displayed in UI", false);
			}
			return is_WebElement_Displayed( Locator);
			
			
		
	}
	
	public boolean is_WebElement_Selected(String Xpath) {
		try {
			highlightElement_CheckBox(Xpath);
			Remove_highlightElement(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.MILLISECONDS).isSelected();
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean is_WebElement_Present(String Xpath) {
		try {
			highlightElement_CheckBox(Xpath);
			// Remove_highlightElement(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.MILLISECONDS).isPresent();
		} catch (Exception e) {
			System.out.println("Given element was not present:" + Xpath);
			return false;
		}
	}
	public void highlightElement_CheckBox(String Xpath) {

		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < 2; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='7px solid black'", element);

		}
	}
	
	public String[] get_All_Text_from_Locator(String Xpath) {
		List<WebElement> elements = getDriver().findElements(By.xpath(Xpath));
		String[] text = new String[elements.size()];
		for (int i = 0; i < elements.size(); i++) {

			text[i] = elements.get(i).getText();
			System.out.println(text[i]);
		}
		return text;
	}
	
	public void Open_Multiple_Browsers(String CPQ_QA_URL, String CPW_QA_URL) {
		getDriver().get(CPQ_QA_URL);
		getDriver().manage().window().maximize();
		((JavascriptExecutor) getDriver()).executeScript("window.open()");
		// ((JavascriptExecutor)getDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(tabs.get(1));
		getDriver().get(CPW_QA_URL);

	}
	
	public boolean is_WebElement_Enabled(String Xpath) {
		try {
			highlightElement_CheckBox(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.MILLISECONDS).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void Enter_given_Text_Element(WebElementFacade element, String text) {
		highlightElement(element);
		element.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.MILLISECONDS).waitUntilVisible().clear();
		element.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.MILLISECONDS).waitUntilVisible().sendKeys(text);
	}
	
	public String Tooltip_Text_Validation(String Xpath) {
		WebElement Element=getDriver().findElement(By.xpath(Xpath));
		Point coordinates121 = getDriver().findElement(By.xpath(Xpath)).getLocation();
		try {
			Robot robot = new Robot();
	        robot.mouseMove(coordinates121.getX(),coordinates121.getY()+120);
			Thread.sleep(5000);
			return Element.getAttribute("title");
		} catch (InterruptedException | AWTException e) {
			e.printStackTrace();
			return null;
		}  	
        
	}
	
	public boolean MouseHoverWith_Robot_keys(String Xpath){
		try
		{
			//Mouse Over
			Point coordinates = getDriver().findElement(By.xpath(Xpath)).getLocation();
			Robot robot;
		    robot = new Robot();
	        //robot.mouseMove(coordinates.getX(),coordinates.getY()+120);
		    robot.mouseMove(coordinates.getX(),coordinates.getY()+120);
		    defaultWait(ProjectVariables.MIN_TIME_OUT);
				return true;
	
	} catch (Exception e) {
		
		System.out.println("Throwed an exception:'"+e+"',while mouse hovering to the element:"+Xpath);
		e.printStackTrace();
		return false;
	}  	
     
        
        
	}
	
	public boolean Click_Untill_Element_is_not_displayed_with_the_given_time(int waitcount,String scrollbar, String RequiredElement) 
	{
		
			int scount=0;
			if(is_WebElement_Displayed( scrollbar))
			{
				do
				{
					Doubleclick(scrollbar);
					Doubleclick(scrollbar);
					System.out.println("scount:"+scount);
					scount=scount+1;
					
				}while((is_WebElement_Displayed(RequiredElement)^(scount==waitcount)));
				
				
					
			}
			return is_WebElement_Displayed( RequiredElement);
			
			
		
	}
	
	public boolean DynamicWaitfortheLoadingIconWithCount(String Locator,int count) {
		boolean bstatus=false;
		int j = 1;
		System.out.println("Loading is in progress");
		
		do {
			defaultWait(ProjectVariables.TImeout_2_Seconds);
			j = j + 1;
			
			if (j == count) {
				break;
			}
			
			bstatus=is_WebElement_Visible(Locator);
			
		} while (bstatus);
		
		
		

		return bstatus;
		
	}
	
	public boolean SelectDropDownByValue(String Xpath, String text) {
		Select element=new Select(getDriver().findElement(By.xpath(Xpath)));
		boolean flag =false;
		try
		{
			element.selectByValue(text);
			flag =true;
		}
		catch(Exception ignored)
		{
			System.out.println("Not able to selectByVisibleText  :"+element +"::"+text );
			flag =false;
		}
		return flag;
}
	
	public void clickDownArrow(){
		Actions actionObject = new Actions(getDriver());
		actionObject = actionObject.sendKeys(Keys.ARROW_DOWN); //ASSIGN the return or you lose this event.
		actionObject.perform();
	}

	public List<WebElement> getElementsList(String locatorType, String locatorValue)  {

		List<WebElement> elements = null;
		try {

			switch (locatorType) {
			case "NAME":

				elements = getDriver().findElements(By.name(locatorValue));

				break;
			case "XPATH":			
				elements = getDriver().findElements(By.xpath(locatorValue));

				break;
			case "ID":			

				elements = getDriver().findElements(By.id(locatorValue));
				break;
			case "CSS":	

				elements = getDriver().findElements(By.cssSelector(locatorValue));
				break;
			case "CLASS":
				elements = getDriver().findElements(By.className(locatorValue));
				break;
			case "LINK_TEXT":
				elements = getDriver().findElements(By.linkText(locatorValue));

				break;
			case "PARTIAL_LINK_TEXT":				

				elements = getDriver().findElements(By.partialLinkText(locatorValue));

				break;
			case "TAG_NAME":

				elements = getDriver().findElements(By.tagName(locatorValue));

				break;
			default:

				break;
			}	


		}catch (Exception e) {
			GenericUtils.logMessage(
					"Elements not present:  of Locator Type :" + locatorType + "   of Value :  " + locatorValue);
			
		}
		return  elements;

	}

	public void waitForContentLoad() throws NoSuchElementException {
		boolean bstatus=false;
		int j = 1;
		System.out.println("Loading is in progress");
		
		do {
			defaultWait(ProjectVariables.TImeout_2_Seconds);
			j = j + 1;
			
			if (j == 200) {
				break;
			}
			
			bstatus=is_WebElement_Displayed("//p[contains(text(),'Presentation Loading') or contains(text(),'Please wait. Content loading')]");
			
		} while (bstatus);
		
		if(bstatus)
		{
			Assert.assertTrue("Loading is taking more time....,more than '6' minutes", false);
		}
		

		
	}
	
	public boolean is_WebElement_enabled(String Xpath) {
		try {


			return $(Xpath).withTimeoutOf(ProjectVariables.TImeout_5_Seconds, ChronoUnit.SECONDS).isEnabled();
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("Given element not in enabled mode,Xpath==>"+Xpath+",Exception=>"+e);
			return false;
		}
	}
}
