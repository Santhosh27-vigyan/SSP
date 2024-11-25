package Resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class base {
	private static WebDriver driver;
	private static base b;
	private base(String BrowserName)
	{
		if(BrowserName.equalsIgnoreCase("chrome"))
		this.driver=new ChromeDriver();
		else if(BrowserName.equalsIgnoreCase("firefox"))
			this.driver=new FirefoxDriver();
		else
		System.out.println("Browser not found");
	}
	public static base CreateObject(String BrowserName)
	{
		if(driver==null)
		{
			synchronized(base.class) 
			{
			if(driver==null)
			{
				b=new base(BrowserName);
			}
			}
		}
		return b;
	}
	public WebDriver GetDriver()
	{
		return driver;
	}

}
