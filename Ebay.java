package appiumpackage;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Ebay 
{
	public static void main(String[] args) throws Exception 
	{
		//get input from keyboard
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Firstname");
		String fn=sc.nextLine();
		System.out.println("Enter lastname");
		String ln=sc.nextLine();
		System.out.println("Enter email");
		String em=sc.nextLine();
		System.out.println("Enter password");
		String pw=sc.nextLine();
		
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium -a 0.0.0.0 -p 4723\"");
		Thread.sleep(10000);
		URL u = new URL("http://0.0.0.0:4723/wd/hub");
		//Details of ARD and app under testing
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","a3379a40");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","5.1.1");
		dc.setCapability("appPackage","com.ebay.mobile");
		dc.setCapability("appActivity","com.ebay.mobile.activities.MainActivity");
		
		//create driver object to launch app
		AndroidDriver driver = new AndroidDriver(u,dc);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//login into ebay
		driver.findElement(By.xpath("//*[@text='Register']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@class='android.view.View']")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		driver.findElement(By.xpath("//*[@resource-id='firstname']")).sendKeys(fn);
		driver.findElement(By.xpath("//*[@resource-id='lastname']")).sendKeys(ln);
		driver.findElement(By.xpath("//*[@resource-id='email']")).sendKeys(em);
		driver.findElement(By.xpath("//*[@resource-id='PASSWORD']")).sendKeys(pw);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		if(driver.isKeyboardShown())
		{
			driver.hideKeyboard();
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@resource-id='ppaFormSbtBtn']")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.switchTo().alert();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(em);
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(pw);
		driver.findElement(By.xpath("//*[@text='sign in']")).click();
		
		//search for an item
		driver.findElement(By.xpath("//*[@text='Search for anything']")).sendKeys("christmas sale");
		
		//scroll up for a required element
		int w = driver.manage().window().getSize().getWidth();
		int h =driver.manage().window().getSize().getHeight();
		TouchAction ta = new TouchAction(driver);
		int x= w/2;
		int y= (int)(h*0.9);
		int temp = (int)(h*0.7);
		while(2>1)
		{
			try
			{
				driver.findElement(By.xpath("//*[@text='Christmas Sale - Tiffany Red Butterfly Accent Stained Glass Table Lamp']")).click();
				break;
			}
			catch(Exception e)
			{
				ta.press(x,y).moveTo(0,temp-y).release().perform();
			}
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		
		//perform rotations
		if(driver.getOrientation().name().equals("PORTRAIT"))
		{
			driver.rotate(ScreenOrientation.LANDSCAPE);
		}
		else
		{
			driver.rotate(ScreenOrientation.PORTRAIT);
		}
		
		//Add to cart
		driver.findElement(By.xpath("//*[@text='Add to cart']")).click();
		driver.findElement(By.xpath("//*[@text='View in cart']")).click();
		
		//purchasing the item
		driver.findElement(By.xpath("//*[@text='Checkout']")).click();
		driver.findElement(By.xpath("//*[@text='Select payment option']")).click();
		driver.findElement(By.xpath("//*[@contect-desc='PayPal' or @contect-desc='Credit or debit card' or @contect-desc='Bank deposit']")).click();
		driver.findElement(By.xpath("//*[@text='Commit to buy']")).click();	
		
		//close app
		driver.closeApp();
		
		//stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");		
	}			
			
}
