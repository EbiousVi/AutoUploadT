package project.options;

import org.openqa.selenium.chrome.ChromeOptions;


public class WIN8Options implements OptionsTemplate {
    @Override
    public ChromeOptions getOptions() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.binary", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\Петров\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 8");
        return options;
    }
}
