package project.options;

import org.openqa.selenium.chrome.ChromeOptions;

public class WIN10Options implements OptionsTemplate {
    @Override
    public ChromeOptions getOptions() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.binary", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\v\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("profile-directory=Profile 1");
        return options;
    }
}
