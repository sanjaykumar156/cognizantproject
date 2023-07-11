package Pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Base.Base;

public class StocksToolsApps extends Base{
	String gstock,cstock;
	By email=By.xpath("//input[@type='email']");
	By next=By.xpath("//input[@type='submit']");
	By pass=By.name("passwd");
	By acc=By.id("user-name");
	By yes=By.xpath("//input[@value='Yes']");
	By search=By.xpath("//input[@title='Search']");
	By s1=By.xpath("//span[text()='Cognizant']");
	By gs=By.xpath("//*[@id=\"kp-wp-tab-overview\"]/div[1]/div/div/div/div/div/div[2]/div/div/span[2]/span[3]");
	By cs=By.xpath("//div[@class=\"stock-ticker-header__price\"]/span");
	By apps=By.xpath("//span[@class=\"icomoon-windows8\"]");
	By app1=By.xpath("//*[@id=\"mCSB_2_container\"]/div[1]");
	By app2=By.xpath("//*[@id=\"header-menu-item-AppsAndTools\"]/apps-and-tools/div/div/div/div/tabset/div/div/button[2]");
	By app3=By.xpath("//*[@id=\"header-menu-item-AppsAndTools\"]/apps-and-tools/div/div/div/div/tabset/div/div/button[3]");
	By name=By.xpath("//apps-and-tools-tab-item/div/a/span[2]");
	
	public void getGoogleData() {
		logger = report.createTest("Get Cognizant Stock value from Google.");
		try {
		driver.get("https://www.google.co.in/");
		driver.findElement(search).sendKeys(prop.getProperty("search5"));
		wait(20,s1);
		driver.findElement(s1).click();
		reportPass("Cognizant is searched.");
		gstock=driver.findElement(gs).getText();
		System.out.println("Cognizant Stock according to Google:");
		System.out.println("CTSH (NASDAQ): "+gstock);
		reportPass("Cognizant Stock value from Google is obtained sucessfully");
		
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void login() {
		logger = report.createTest("Login into Becognizant.");
		try {
		wait(20,email);
		driver.findElement(email).sendKeys(prop.getProperty("email"));
		driver.findElement(next).click();
		wait(20,pass);
		driver.findElement(pass).sendKeys(prop.getProperty("password"));
		driver.findElement(next).click();
		Thread.sleep(1000);
		reportPass("Email and Password Verified sucessfully");
		wait(120,yes);
		driver.findElement(yes).click();
		//Verify Title
		if (driver.getTitle().contains("Be.Cognizant"))
			// Pass
			System.out.println("Page title contains Be.Cognizant");
		else
			// Fail
			System.out.println("Page title doesn't contains Be.Cognizant");
		String name=driver.findElement(acc).getText();
		System.out.println("The name for the Acoount is: "+name);
		Screenshot("Account");
		reportPass("Be.Cognizant Page is reached sucessfully");
		
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void getCogStock() {
		logger = report.createTest("Obtain Cognizant Stock value from Be.Cognizant");
		try {
		cstock=driver.findElement(cs).getText();
		System.out.println("Cognizant Stock according to Be.Cogmizant:");
		System.out.println("CTSH (NASDAQ): "+cstock);
		if(gstock.contains(cstock)) {
			System.out.println("The Stock values are similar");
		} else {
			System.out.println("The Stock values are different");
		}
		reportPass("Cognizant Stock value from Be.Cognizant is obtained sucessfully");
		
	   } catch (Exception e) {
		reportFail(e.getMessage());
	  }
	}
	
	public void getAppsTools() throws IOException, InterruptedException {
		logger = report.createTest("Get Apps under Different App search");
		try {
		driver.findElement(apps).click();
		FileInputStream file=new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Data.xlsx");
		workbook=new XSSFWorkbook(file);
		sh=workbook.getSheet("Apps");
		String testData=String.valueOf(sh.getRow(1).getCell(2));
		reportPass(testData+" is selected");
		if(testData.matches("MyApps")) {
			String info=driver.findElement(app1).getText();
			System.out.println(info);
		} else if(testData.matches("Office365")) {
			driver.findElement(app2).click();
			wait(20,name);
			Screenshot("Apps");
			List<WebElement> names = driver.findElements(name);
			System.out.println("************************************************");
			System.out.println("        The Apps under Office 365 are: ");
			System.out.println("************************************************");
			for (int i = 0; i <names.size(); i++) {
				System.out.println(names.get(i).getText());
			}
		} else if(testData.matches("Company")) {
			driver.findElement(app3).click();
			wait(20,name);
			Screenshot("Apps");
			List<WebElement> names = driver.findElements(name);
			System.out.println("************************************************");
			System.out.println("           The Company Apps are: ");
			System.out.println("************************************************");
			Thread.sleep(5000);
			for (int i = 0; i <names.size(); i++) {
				System.out.println(names.get(i).getText());
			}
		}
		reportPass("Apps are obtained sucessfully");
		
	  } catch (Exception e) {
		reportFail(e.getMessage());
	  }
	}
	}
	

