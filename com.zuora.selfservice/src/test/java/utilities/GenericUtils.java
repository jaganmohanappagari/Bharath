package utilities;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;



public class GenericUtils extends PageObject {


	//################################################################################################################################################################
	public static void Verify(String StepDetails,String sStatus){

		if (sStatus.equalsIgnoreCase("PASSED")) {
			System.out.println(StepDetails);
			Serenity.recordReportData().withTitle(StepDetails).andContents("Passed");
			
			Assert.assertTrue(StepDetails, true);
		} else {
			System.out.println(StepDetails);
			Serenity.recordReportData().withTitle(StepDetails).andContents("Failed");
			
			Assert.assertTrue(StepDetails, false);

		}
	}
	
	//################################################################################################################################################################
	
	public static String decode(String value)  {
		byte[] decodedValue = null;
		try{
			decodedValue = Base64.getDecoder().decode(value);	     
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return new String(decodedValue, StandardCharsets.UTF_8);
	}	
	
	//################################################################################################################################################################
	
	public boolean setValue(By sObjectType,String sInputVal){
		boolean blnResult=false;
		int iTimer=0;

		try{
			do{
				List<WebElement> sTxtElement = getDriver().findElements(sObjectType);
				//if size greater than zero
				if(sTxtElement.size()>0){
					getDriver().findElement(sObjectType).clear();
					getDriver().findElement(sObjectType).sendKeys("");
					getDriver().findElement(sObjectType).sendKeys(sInputVal);
					Thread.sleep(1000);
					blnResult=true;
					Verify("Value enterted sucessfully:="+sInputVal,"PASSED");

				}

				iTimer=iTimer+1;
				Thread.sleep(1000);


			}while((blnResult!=true) && (iTimer!=10));


			if 	(blnResult!=true){
				System.out.println("Object not found:="+sObjectType);
				Verify("Object not found to enter the value:="+sObjectType,"FAILED");
				return false;
			}
		}catch(Exception e){
			System.out.println("Object not entered Successfully , Failed due to :="+e.getMessage());
			Verify("Object not entered Successfully , Failed due to :="+e.getMessage(),"FAILED");
			return false;
		}
		return blnResult;
	}
	
	//################################################################################################################################################################
	
	public boolean clickButton(By sObjectType){
		boolean blnFlag = false;
		int iTimer = 0; 
		try{  
			do {

				try{
					List<WebElement> sList = getDriver().findElements(sObjectType);

					if (sList.size() > 0){
						for (int i=0;i < sList.size();i++){
							if (sList.get(i)!= null && sList.get(i).isDisplayed() && sList.get(i).isEnabled()){   
								sList.get(i).click();
								blnFlag = true;
								break;
							}
						}
					}

					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(InvalidElementStateException Ie){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(StaleElementReferenceException  e){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(WebDriverException se){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				} 

			} while ((blnFlag != true) && (iTimer != 30));

			if ( blnFlag != true) {             
				Verify("Object not found to click :="+sObjectType,"FAILED");
				blnFlag = false ;
			}

		}   catch(Exception e){     
			System.err.println(e); 
			getDriver().quit();  
		} 

		return blnFlag;

	}
	
	//################################################################################################################################################################
	
	public boolean isElementExist(String sTagName,String sText){
		boolean blnFlag = false;
		int iTimer = 0;
		String strXpath = "//"+sTagName+"[text()='"+sText+"']";

		try{  
			do {
				try{
					List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

					if (sList.size() > 0){
						for (int i=0;i < sList.size();i++){
							if (sList.get(i)!= null && sList.get(i).isDisplayed() && sList.get(i).isEnabled()){      
								blnFlag = true;
								Verify("Link displayed sucessfully:="+sText,"PASSED");
								break;                                   
							}
						}
					}

					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(ElementNotVisibleException Ie){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(StaleElementReferenceException  e){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(NoSuchElementException se){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				} 


			} while ((blnFlag != true) && (iTimer != 10));

			if ( blnFlag != true) {
				Verify("Object not found:="+strXpath,"FAILED");
				blnFlag = false ;
			}

		}   catch(Exception e){
			System.err.println(e); 
			Verify("Object not clicked, Failed due to exception:="+e.getMessage(),"FAILED");
		} 

		return blnFlag;

	}	
	
	//################################################################################################################################################################
	
	public boolean clickOnElement(String sTagName,String sText){
		boolean blnFlag = false;
		int iTimer = 0;
		String strXpath = "//"+sTagName+"[text()= '"+sText+"']";

		try{  
			do {
				try{
					List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

					if (sList.size() > 0) {
						for (int i=0;i < sList.size();i++){
							if (sList.get(i).isDisplayed() && sList.get(i).isEnabled()){                                                
								sList.get(i).click();             
								blnFlag = true;  
								Verify("Link clicked sucessfully:="+sText,"PASSED");
								break;
							}
						}
					}

					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(InvalidElementStateException Ie){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(StaleElementReferenceException  e){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(WebDriverException se){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}  
			} while ((blnFlag != true) && (iTimer != 15));

			if ( blnFlag != true) {
				Verify("Object not found:="+strXpath,"FAILED");
				blnFlag = false ;
			}

		}    catch(Exception e){                      
			System.err.println(e); 
			Verify("Object not found :"+strXpath+",failed due to exception ==>"+e,"FAILED");  
		} 

		return blnFlag;

	}	
	
	//################################################################################################################################################################
	
	public boolean clickOnElement(String sXpath){
		boolean blnFlag = false;
		int iTimer = 0; 
		try{  
			do {

				try{
					List<WebElement> sList = getDriver().findElements(By.xpath(sXpath));

					if (sList.size() > 0){
						for (int i=0;i < sList.size();i++){
							if (sList.get(i)!= null && sList.get(i).isDisplayed() && sList.get(i).isEnabled()){   
								sList.get(i).click();
								Verify("Link clicked sucessfully:="+sXpath,"PASSED");
								blnFlag = true;
								break;
							}
						}
					}

					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(InvalidElementStateException Ie){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(StaleElementReferenceException  e){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(WebDriverException se){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				} 

			} while ((blnFlag != true) && (iTimer != 30));

			if ( blnFlag != true) {             
				Verify("Object not found:="+sXpath,"FAILED");
				blnFlag = false ;
			}

		}   catch(Exception e){     
			System.err.println(e); 
			Verify("Object not found failed due to Execption"+e.getMessage(),"FAILED"); 
		} 

		return blnFlag;

	}
	
	//################################################################################################################################################################
	
	public boolean isElementExist(String sXpath){
		boolean blnFlag = false;
		int iTimer = 0; 
		try{  
			do {
				try{

					List<WebElement> sList = getDriver().findElements(By.xpath(sXpath));

					if (sList.size() > 0){
						for (int i=0;i < sList.size();i++){
							if (sList.get(i)!= null && sList.get(i).isDisplayed() && sList.get(i).isEnabled()){                        
								blnFlag = true;
								Verify("Value displayed sucessfully:="+sXpath,"PASSED");
								break;
							}
						}
					}

					Thread.sleep(ProjectVariables.MIN_TIME_OUT);
					iTimer = iTimer+1; 

				}catch(StaleElementReferenceException  e){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 
					System.out.println("Object not found failed due to Execption"+e.getMessage());
				}catch(WebDriverException se){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 
					System.out.println("Object not found failed due to Execption"+se.getMessage());
				}
			   catch(Exception e){     
				   iTimer = iTimer+1; 
				   System.out.println("Object not found failed due to Execption"+e.getMessage());
			} 

			} while ((blnFlag != true) && (iTimer != 10));

			if ( blnFlag != true) {             
				
				Verify("Object not found:="+sXpath,"FAILED");
				blnFlag = false ;
				
			}

		}   catch(Exception e){     
			//System.err.println(e); 
			Verify("Object not found failed due to Execption"+e.getMessage(),"FAILED");
			System.out.println("Object not found failed due to Execption"+e.getMessage());
			blnFlag = false ;
		} 

		return blnFlag;

	}
	
	//################################################################################################################################################################
	
	public boolean isElementExist(String sXpath,int Time){
		boolean blnFlag = false;
		int iTimer = 0; 
		try{  
			do {
				try{

					List<WebElement> sList = getDriver().findElements(By.xpath(sXpath));

					if (sList.size() > 0){
						for (int i=0;i < sList.size();i++){
							if (sList.get(i)!= null && sList.get(i).isDisplayed() && sList.get(i).isEnabled()){                        
								blnFlag = true;
								Verify("Element displayed sucessfully:="+sXpath,"PASSED");
								break;
							}
						}
					}

					Thread.sleep(ProjectVariables.MIN_TIME_OUT);
					iTimer = iTimer+1; 

				}catch(StaleElementReferenceException  e){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 

				}catch(WebDriverException se){
					if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
					iTimer = iTimer+1; 
				}

			} while ((blnFlag != true) && (iTimer != Time));

			if ( blnFlag != true) {             
				//Verify("Object not found:="+sXpath,"FAILED");
				blnFlag = false ;
			}

		}   catch(Exception e){     
			System.err.println(e); 
			getDriver().quit();  
		} 

		return blnFlag;

	}	
	
	//################################################################################################################################################################
	
	public boolean selectDropdownValue(By sElement,String sValue){
		boolean blnResult=false;
		int iTimer=0;
		try{
			do{
				List<WebElement> sList =getDriver().findElements(sElement);

				if (sList.size()>0){
					Select slistitem=new Select(getDriver().findElement(sElement));
					slistitem.selectByVisibleText(sValue);
					Verify("Dropdown value selected sucessfully:="+sElement,"PASSED");
					Thread.sleep(1000);
					blnResult=true;
				}
				iTimer=iTimer+1;
			}while(blnResult!=true && iTimer!=60);
			//if flag false
			if (blnResult!=true){
				Verify("Object not found:="+sElement,"FAILED");
				getDriver().quit();
			}
		}catch(Exception e){
			Verify("Object not clicked Successfully , Failed due to :="+e.getMessage(),"FAILED");
		}
		return blnResult;
	}
	
	//################################################################################################################################################################
	/*public  boolean VALIDATE_PDF_DATA(String sPDF_path,String sInput){

			boolean blnResult=false;
			 try (PDDocument document = PDDocument.load(new File(sPDF_path)))
		        {

		            document.getClass();

		            if (!document.isEncrypted()) {

		                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		                stripper.setSortByPosition(true);

		                PDFTextStripper tStripper = new PDFTextStripper();

		                String pdfFileInText = tStripper.getText(document);
		                //System.out.println("Text:" + st);

						// split by whitespace
		                String lines[] = pdfFileInText.split("\\r?\\n");
		                for (String line : lines) {

		                    if(line.trim().toUpperCase().contains(sInput)){
		                    	Verify("PDF details matched sucessfully:=="+sInput,"PASSED");
		                    	blnResult=true;
		                    	break;
		                    }
		                }
		                //if flag returns false
		                if(blnResult==false){
		                	Verify("Data not found"+sInput,"FAILED");
		                }

		            }

		        }catch(Exception e){
		        	Verify("Object not clicked Successfully , Failed due to :="+e.getMessage(),"FAILED");
		        }
			 return blnResult;

		}*/
	//################################################################################################################################################################
	//function doing the magic in I.E browser but doesnt produce same result in chrome browser
	public void downloadFiles(String FilePath) throws Exception {
		//StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(FilePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Thread.sleep(2500);
		Robot robot = new Robot();

		// Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);

		// Release Enter
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Press CTRL+S
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);

		// Release CTRL+S
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_S);

		/*// Press CTRL+V
               robot.keyPress(KeyEvent.VK_CONTROL);
               robot.keyPress(KeyEvent.VK_V);

               // Release CTRL+V
               robot.keyRelease(KeyEvent.VK_CONTROL);
               robot.keyRelease(KeyEvent.VK_V);*/
		Thread.sleep(3000);

		//Press Enter 
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(3000);

		System.out.println("Vk_Enter" +" cliecked");
	}

	//################################################################################################################################################################

	public static String getDate_given_Format() {
		// String element = DA_PROJ_OR.LAST_SEARCH_TIME;
		String sExpectedTime = new SimpleDateFormat("MM/dd/yy").format(Calendar.getInstance().getTime());
		String[] words = sExpectedTime.split("\\s");
		System.out.println("System Date-->:- " + words[0]);

		String sExpectedDate = words[0];

		return sExpectedDate;
	}

	//################################################################################################################################################################
	
	public static int generate_Random_Number_for_Given_Range(int range) {
		Random rand = new Random();
		int value = rand.nextInt(range);
		return value;
	}
	
	//################################################################################################################################################################

	public static int GetRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	//################################################################################################################################################################

	public static boolean verify_List_contains_given_String(List<String> usersList, String arg1) {
		System.out.println(usersList);
		System.out.println(arg1);
		System.out.println(usersList.contains(arg1));

		return usersList.contains(arg1);

	}

	//################################################################################################################################################################
	
	public static long Convert_given_to_milliseonds(String arg1) {

		return TimeUnit.MINUTES.toMillis(Integer.valueOf(arg1));

	}

	//################################################################################################################################################################
	
	public static void logMessage(String  message){
		System.out.println("\n");
	}
	
	//################################################################################################################################################################
	
	public static void defaultWait(long i) {
		try {

			Thread.sleep(TimeUnit.SECONDS.toMillis(i));
			System.out.println("Waited for " + i + " seconds");

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	//################################################################################################################################################################
	
	public static boolean CompareTwoArrayLists(ArrayList<String>sExpected,ArrayList<String>sActual){

		int flag = 0;

		for (int i = 0;i < sExpected.size();i++) {
			flag = 0;
			for (int j = 0;j < sActual.size(); j++) {
				if(sActual.get(i).equals(sExpected.get(j))){
					System.out.println("Exist..:"+sExpected.get(j));
					flag = flag + 1;
				}
			}
		}
		if(flag == 0)
			return false;
		else
			return true;

	}
	
	//################################################################################################################################################################
	
	public static String readGivenFileAsString(String filpath) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filpath));
		String readerValue = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = reader.readLine();
			}
			readerValue = sb.toString();
		} finally {
			reader.close();
		}
		return  readerValue;


	}

	//################################################################################################################################################################
	
	public static void Verify(String sDescription, boolean blnStatus){
		if (blnStatus) {
			System.out.println(sDescription);
			Serenity.recordReportData().withTitle(sDescription).andContents("Passed");
			Assert.assertTrue("validation successful for "+sDescription, true);
		} else {
			System.out.println(sDescription);
			Serenity.recordReportData().withTitle(sDescription).andContents("Failed");
			Serenity.takeScreenshot();
			Assert.assertTrue("validation not successful for "+sDescription, false);

		}}

	//################################################################################################################################################################
	
	public static String RetrivetheExactMonthname(String reuquiredSubsequentRelease) {
		String month=reuquiredSubsequentRelease.substring(4,6);
		String monthname = null;

		switch(month)
		{
		case "01":
			monthname="JAN";
			break;
		case "02":
			monthname="FEB";
			break;
		case "03":
			monthname="MAR";
			break;
		case "04":
			monthname="APR";
			break;
		case "05":
			monthname="MAY";
			break;
		case "06":
			monthname="JUN";
			break;
		case "07":
			monthname="JUL";
			break;
		case "08":
			monthname="AUG";
			break;
		case "09":
			monthname="SEP";
			break;
		case "10":
			monthname="OCT";
			break;
		case "11":
			monthname="NOV";
			break;
		case "12":
			monthname="DEC";
			break;


		}


		return monthname;
	}

	//################################################################################################################################################################
	
	public static String RetrivetheExactMonth(String reuquiredSubsequentRelease) {
		String month=reuquiredSubsequentRelease.substring(0,3);
		String monthname = null;

		switch(month)
		{
		case "JAN":
			monthname="01";
			break;
		case "FEB":
			monthname="02";
			break;
		case "MAR":
			monthname="03";
			break;
		case "APR":
			monthname="04";
			break;
		case "MAY":
			monthname="05";
			break;
		case "JUN":
			monthname="06";
			break;
		case "JUL":
			monthname="07";
			break;
		case "AUG":
			monthname="08";
			break;
		case "SEP":
			monthname="09";
			break;
		case "OCT":
			monthname="10";
			break;
		case "NOV":
			monthname="11";
			break;
		case "DEC":
			monthname="12";
			break;


		}


		return monthname;
	}

	//################################################################################################################################################################
	
	public static String SystemDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);

	}

	//################################################################################################################################################################
	
	public static String SystemTime_in_the_given_format(String Timeformat){
		DateFormat dateFormat = new SimpleDateFormat(Timeformat);
		Date date = new Date();


		return dateFormat.format(date);

	}

	//################################################################################################################################################################
	
	public static void saveTheResults(String reultsPath) throws IOException
	{
		//User Directory
		String sDirectory = System.getProperty("user.dir");

		//Project Result Folder
		String sResultfolderPath = sDirectory + "\\target\\site\\serenity";


		File Sorcefile = new File(sResultfolderPath);
		File Destfile = new File(reultsPath+"/"+SystemTime_in_the_given_format("yyyy_MM_dd")+"/"+SystemTime_in_the_given_format("h-mm-s aa"));
		System.out.println("Results Destination Path ============>"+Destfile);

		//Copying Results 
		FileUtils.copyDirectory(Sorcefile, Destfile);
		System.out.println("Results were saved successfully in the path ============>"+Destfile);
	}

	//################################################################################################################################################################

	public static long Difference_in_the_given_two_dates(String Date1,String Date2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.ENGLISH);
		Date firstDate = sdf.parse(Date1);
		Date secondDate = sdf.parse(Date2);


		@SuppressWarnings("deprecation")
		long diffInMillies = Math.abs(firstDate.getMinutes() - secondDate.getMinutes());

		//long diffInSeconds = Math.abs(firstDate.getSeconds() - secondDate.getSeconds());


		logMessage("Difference in Dates is:"+diffInMillies+" Minutes");

		return diffInMillies;

	}

	//################################################################################################################################################################
	
	public boolean IsElementExistWithContains(String sTagName,String sText){
		boolean blnFlag = false;
		int iTimer = 0;
		String strXpath = "//"+sTagName+"[contains(text(),'"+sText+"')]";

		try{  
			do {

				List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

				if (sList.size() > 0){
					for (int i=0;i < sList.size();i++){
						if (sList.get(i).isDisplayed()){         
							blnFlag = true;
							Verify("Link displayed sucessfully:="+sText,"PASSED");
							break;                                   
						}
					}
				}

				if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
				iTimer = iTimer+1;  

			} while ((blnFlag != true) && (iTimer != 10));

			if ( blnFlag != true) {
				System.out.println(sText+" object not found");
				blnFlag = false ;
			}

			if(!blnFlag)
			{
				Assert.assertTrue("Object not found ==>"+strXpath,false);
			}


		}   catch(Exception e){
			System.err.println(e); 
			Verify("Object not found , Failed due to :="+e.getMessage(),"FAILED");
			getDriver().quit(); 

		} 

		return blnFlag;

	}	

	//################################################################################################################################################################

	public static String Convert_Epoch_time_to_given_format_for_the_given_date(String Timeformat,Long time){
		 Date date = new Date(time);
	        DateFormat format = new SimpleDateFormat(Timeformat);
	        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
	        String formatted = format.format(date);
	        System.out.println("Exact Date ===>"+formatted);
	        //"Etc/UTC"
	        //"MM-dd-yyyy"
		return formatted;

	}
	
	//==============================================GetElementText()=============================================================================>
    public  String GetElementText(String strXpath){
                    boolean blnFlag = false;
                    int iTimer = 0;
                    String sGetText=null;

                    try{  
                                    do {
                                                    try{
                                                                    List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

                                                                    if (sList.size() > 0){
                                                                                    for (int i=0;i < sList.size();i++){
                                                                                                    if (sList.get(i)!= null && sList.get(i).isDisplayed() && sList.get(i).isEnabled()){    
                                                                                                                    sGetText=sList.get(i).getText();
                                                                                                                    blnFlag = true;
                                                                                                                    Verify("Text Retrieved sucessfully:="+sGetText,"PASSED");
                                                                                                                    break;                                   
                                                                                                    }
                                                                                    }
                                                                    }

                                                                    if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
                                                                    iTimer = iTimer+1; 

                                                    }catch(ElementNotVisibleException Ie){
                                                                    if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
                                                                    iTimer = iTimer+1; 

                                                    }catch(StaleElementReferenceException  e){
                                                                    if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
                                                                    iTimer = iTimer+1; 

                                                    }catch(NoSuchElementException se){
                                                                    if (!blnFlag) {Thread.sleep(ProjectVariables.MIN_TIME_OUT);}
                                                                    iTimer = iTimer+1; 

                                                    } 


                                    } while ((blnFlag != true) && (iTimer != 5));

                                    if ( blnFlag != true) {
                                                    Verify("Object not found:="+strXpath,"FAILED");
                                                    blnFlag = false ;
                                    }

                    }   catch(Exception e){
                                    System.err.println(e); 
                                    Verify("Object not clicked, Failed due to exception:="+e.getMessage(),"FAILED");
                    } 

                    return sGetText;

    }
//==============================================CompareTwoValues===============================================================================>
    public void CompareTwoValues(String sFunctionality,String sInput1,Long sInput2){
                    String sInput3=null;
                    if (sInput2==0&&sInput1.equalsIgnoreCase("-"))
                    {
                                  sInput3="-";
                    }
                    else
                    {
                                    sInput3=String.valueOf(sInput2);
                    }
                    if(sInput1.equalsIgnoreCase(String.valueOf(sInput3)))
                    {
                                    GenericUtils.Verify("Both values matched:=="+sFunctionality+"::==UI Value "+sInput1+" & DB value==>"+sInput2,"PASSED");
                    }else{
                                    GenericUtils.Verify("Both values not matched:=="+sFunctionality+"::==UI Value "+sInput1+" & DB value==>"+sInput2,"FAILED");
                    }
                    
    }
	public void CompareTwoValues(String sFunctionality,String sInput1,String sInput2){
	                    
	                    if(sInput1.equalsIgnoreCase(sInput2)){
	                                    GenericUtils.Verify("Both values matched:=="+sFunctionality+"::==UI Value ==>"+sInput1+" & DB value==>"+sInput2,"PASSED");
	                    }else{
	                                    GenericUtils.Verify("Both values not matched:=="+sFunctionality+"::==UI Value ==>"+sInput1+" & DB value==>"+sInput2,"FAILED");
	                    }
	                    
	    }
	
	public void CompareTwoValues(String sFunctionality,HashSet<String> sInputarrylist,String sExpectedoutput){
	    
		for (String DBValue : sInputarrylist) {
			
				 if(sExpectedoutput.contains(DBValue.trim()))
				 {
			         GenericUtils.Verify("Both values matched:=="+sFunctionality+"::==UI Value "+sExpectedoutput+" & DB value==>"+DBValue,"PASSED");
				 }
				 else
				 {
			             GenericUtils.Verify("Both values not matched:=="+sFunctionality+"::==UI Value "+sExpectedoutput+" & DB value==>"+DBValue,"FAILED");
				 }
		}
		
	   
	    
	}
	
	public void CompareTwoValues(String sFunctionality,ArrayList<String> sInputarrylist,String sExpectedoutput){
	    
		for (String DBValue : sInputarrylist) {
			
				 if(sExpectedoutput.contains(DBValue.trim()))
				 {
			         GenericUtils.Verify("Both values matched:=="+sFunctionality+"::==UI Value "+sExpectedoutput+" & DB value==>"+DBValue,"PASSED");
				 }
				 else
				 {
			             GenericUtils.Verify("Both values not matched:=="+sFunctionality+"::==UI Value "+sExpectedoutput+" & DB value==>"+DBValue,"FAILED");
				 }
		}
		
	   
	    
	}
	
	public void CompareTwoValues(String sFunctionality,List<String> sInputarrylist,String sExpectedoutput){
	    
		for (String DBValue : sInputarrylist) {
			
				 if(sExpectedoutput.contains(DBValue.trim()))
				 {
			         GenericUtils.Verify("Both values matched:=="+sFunctionality+"::==UI Value "+sExpectedoutput+" & DB value==>"+DBValue,"PASSED");
				 }
				 else
				 {
			             GenericUtils.Verify("Both values not matched:=="+sFunctionality+"::==UI Value "+sExpectedoutput+" & DB value==>"+DBValue,"FAILED");
				 }
		}
		
	   
	    
	}
//==============================================================================================================================================>

	public ArrayList<Long> Convert_the_given_array_into_given_format(ArrayList<String> arraylist) {
		
		ArrayList<Long> LongList=new ArrayList<>();
		
			
			for (int i = 0; i < arraylist.size(); i++) {
				
				
			LongList.add(Long.valueOf(String.valueOf(arraylist.get(i))));	
				
				
				
			}
			return LongList;
			
			
	}

	//==============================================================================================================================================>
	
	public static String Months_Sorting_funcitonality(String Month1,String Month2)
	{
		
		String Months="JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC";
		List<String> CompareMonthslist=Arrays.asList(StringUtils.substringAfter(Months, Month1+",").split(","));
		
		
		for (int i = 0; i < CompareMonthslist.size(); i++) 
		{
			if(Month2.equalsIgnoreCase(CompareMonthslist.get(i)))
			{
			
				System.out.println("Months are in Ascending order");
				return "Months are in Ascending order";
			}
			else
			{
				System.out.println("Months are in Descending order");
				return "Months are in Descending order";
			}
		}
		
		
		return null;
		
		
		
	}
	
	
	public static void validate_the_sorting_funtionality(ArrayList<String> savingslist,String Order,String colname) {
			
			System.out.println("sorting list size ==>"+savingslist.size());
			
			for (int k = 0; k < (savingslist.size()- 1); k++) {
	
				int compare = savingslist.get(k).trim().compareTo(savingslist.get(k + 1).trim());
	
				System.out.println(savingslist.get(k).trim() + "," + savingslist.get(k + 1).trim());
	
				System.out.println(compare);
	
				
					if (Order.equalsIgnoreCase("Ascending order") || Order.equalsIgnoreCase("Sort Ascending")) {
						if (compare == 0 || compare < 0) {
							System.out.println( colname + " is displayed successfully in " + Order + ",for the data:"+savingslist.get(k).trim() + "," + savingslist.get(k + 1).trim());
						} else {
							Assert.assertTrue(colname + " is not displayed in " + Order + ",for the data:"+savingslist.get(k).trim() + "," + savingslist.get(k + 1).trim(), false);
						}
					} else {
						if (compare < 0) {
							Assert.assertTrue(colname + " is not displayed in " + Order + ",for the data:"+savingslist.get(k).trim() + "," + savingslist.get(k + 1).trim(), false);
	
						} else {
							System.out.println(colname + "  is displayed succesfully in " + Order + ",for the data:"+savingslist.get(k).trim() + "," + savingslist.get(k + 1).trim());
						}
					}
	
				
			}
			
		}
	
	public static String Check_the_savings_with_decimal_value_or_not(String savings) {
		
		
		
		
		if(savings.contains("."))
		{
			
			/*if(StringUtils.substringAfter(savings, ".").equalsIgnoreCase("5"))
			{
				savings=StringUtils.substringBefore(savings, ".");
			}
			else*/
			{
				savings=String.valueOf(Math.round(Double.valueOf(savings)));	
			}
			
			
			
		}
		else
		{
			return savings;
		}
		
		return savings;
	}
	
	
	//==============================================================================================================================================>
	
	  public static String getCurrentTimeStamp() {
			// Date object
			Date date = new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			return StringUtils.replace(ts.toString(), ":", "-");
		}

	//==============================================================================================================================================>
	  
	  public static String RetrivetheexactMonth(String reuquiredSubsequentRelease) {
			String month=reuquiredSubsequentRelease.substring(0,3);
			String monthname = null;
			
			switch(month)
			{
			case "JAN":
				monthname="01";
				break;
			case "FEB":
				monthname="02";
				break;
			case "MAR":
				monthname="03";
				break;
			case "APR":
				monthname="04";
				break;
			case "MAY":
				monthname="05";
				break;
			case "JUN":
				monthname="06";
				break;
			case "JUL":
				monthname="07";
				break;
			case "AUG":
				monthname="08";
				break;
			case "SEP":
				monthname="09";
				break;
			case "OCT":
				monthname="10";
				break;
			case "NOV":
				monthname="11";
				break;
			case "DEC":
				monthname="12";
				break;
			
			
			}
			
			
			return monthname;
		}
		
	// ===============================================================================================================================================================================
		
			public static String Retrieve_the_insuranceDesc_from_insuranceKey(String Insurancekey) {
				String InsuranceName = null;
				switch (Insurancekey) {
				case "1":
					InsuranceName = "Medicare";
					break;
				case "2":
					InsuranceName = "Medicaid";
					break;
				case "7":
					InsuranceName = "Commercial";
					break;
				case "3":
					InsuranceName = "Dual Eligible";
					break;
				case "8":
					InsuranceName = "BlueCard";
					break;
				case "9":
					InsuranceName = "Federal Employee Program";
					break;
				default:
					Assert.assertTrue("Case not found=>" + Insurancekey, false);
					break;
				}

				return InsuranceName;
			}		
	  
			// ===============================================================================================================================================================================
			// //

			public static String Retrieve_the_insuranceKey_from_insurance(String Insurancekey) {
				String InsuranceName = null;
				switch (Insurancekey.toUpperCase()) {
				case "MEDICARE":

					InsuranceName = "1";
					break;
				case "MEDICAID":

					InsuranceName = "2";
					break;
				case "COMMERCIAL":

					InsuranceName = "7";
					break;
				case "DUAL ELIGIBLE":

					InsuranceName = "3";
					break;
				case "BLUECARD":

					InsuranceName = "8";
					break;
				case "FEDERAL EMPLOYEE PROGRAM":

					InsuranceName = "9";
					break;
				default:
					Assert.assertTrue("Case not found=>" + Insurancekey.toUpperCase(), false);
					break;
				}
		//System.out.println(InsuranceName);
				return InsuranceName;
			}

		public static List<String> generateRandomCombinations(List<String> sItems, int noOfValues){
	        
	        List<String> sArrays = new ArrayList<String>();
	        IntStream sRandomStream = ThreadLocalRandom.current().ints(0, sItems.size()).distinct().limit(noOfValues);
	        int[] sList = sRandomStream.toArray();
	        
	        for (int i=0; i<sList.length;i++){
	                    sArrays.add(sItems.get(sList[i]));
	        }
	        System.out.println(sArrays);               
	        return sArrays;
	        
	    }

		public static String getDateGivenFormat(String format) {
			
			
			DateFormat dateFormat = new SimpleDateFormat(format);
			Date date = new Date();
			//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
			
			return dateFormat.format(date);
		}

}


