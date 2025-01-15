package Resources;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
public class Base1 {


		public String username;
		public String password;
		public WebDriver driver;
	public WebDriver InitializeDriver() throws IOException
	{
		Properties prop=new Properties();
		FileInputStream Fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");
		prop.load(Fis);
	
		//if you define browser name in property file use below line
		//String browserName = prop.getProperty("browser");
		
		//if you want to send  browser value directly you can use below line
			String browserName = "chrome";
			
		//if you send browser value through maven command use below line
		//String browserName = System.getProperty("browser");
				
				System.out.println(browserName);
				String type = prop.getProperty("type");
				if (browserName.contains("chrome")) { 
					DesiredCapabilities ch = new DesiredCapabilities();
					if (type.equalsIgnoreCase("remote")) {
						ch.setBrowserName("chrome");
						ch.setPlatform(Platform.WINDOWS);
						driver = new RemoteWebDriver(ch);
					}
					if (type.equalsIgnoreCase("local")) {
//			ch.acceptInsecureCerts();
//			ch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//			ch.setCapability("applicationCacheEnabled", false);
						ChromeOptions c = new ChromeOptions();
//			c.merge(ch);
						c.addArguments("--ignore-certificate-errors");
						//c.addArguments("incognito");
						if(browserName.contains("headless"))
							c.addArguments("headless");
						driver = new ChromeDriver(c);
					}
					return driver;
				} else if (browserName.equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
				} else if (browserName.equalsIgnoreCase("ie")) {
					driver = new InternetExplorerDriver();
				}
			//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				return driver;
			}

	}

