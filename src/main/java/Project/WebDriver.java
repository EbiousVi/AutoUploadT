package Project;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriver {
    protected static org.openqa.selenium.WebDriver getChromeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        System.setProperty("webdriver.chrome.binary", "/opt/google/chrome/google-chrome");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=/home/v/.config/google-chrome");
        options.addArguments("profile-directory=Profile 1");
        return new ChromeDriver(options);
    }
}
