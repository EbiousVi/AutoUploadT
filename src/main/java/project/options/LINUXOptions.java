package project.options;

import org.openqa.selenium.chrome.ChromeOptions;

public class LINUXOptions implements OptionsTemplate {

    @Override
    public ChromeOptions getOptions() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        System.setProperty("webdriver.chrome.binary", "/opt/google/chrome/google-chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=/home/v/.config/google-chrome");
        options.addArguments("profile-directory=Profile 1");
        return options;
    }
}
