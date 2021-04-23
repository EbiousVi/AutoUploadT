package project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import project.options.OptionsTemplate;

public class Driver {

    protected static WebDriver getChromeWebDriver(OptionsTemplate optionsTemplate) {
        return new ChromeDriver(optionsTemplate.getOptions());
    }
}
