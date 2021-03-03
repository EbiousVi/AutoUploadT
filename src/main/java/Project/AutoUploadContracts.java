package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static Project.FilesWalkerContract.Walker;

public class AutoUploadContracts {
    public static void main(String[] args) throws IOException, InterruptedException {
        int countRequiredButtons = 0;
        ArrayList<String> fileList = new ArrayList<>();
        List<WebElement> requiredButtons = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ссылка на процедуру");
        String url = reader.readLine();
        reader.close();

        Walker();
        FilesWalkerContract.filesMap.forEach((k, v) -> fileList.add(v));

        org.openqa.selenium.WebDriver driver = Project.WebDriver.getChromeWebDriver();
        driver.get(url);
        Thread.sleep(20000);

        WebElement alertWindow = driver.findElement(By.xpath("//*[text() = 'Закрыть']"));
        JavascriptExecutor closeAlertWindow = (JavascriptExecutor) driver;
        closeAlertWindow.executeScript("arguments[0].click();", alertWindow);
        Thread.sleep(2000);

        WebElement contractSection = driver.findElement(By.cssSelector("#ext-gen401"));
        JavascriptExecutor pickContractSection = (JavascriptExecutor) driver;
        pickContractSection.executeScript("arguments[0].click();", contractSection);
        Thread.sleep(10000);

        List<WebElement> buttonsList = driver.findElements(By.tagName("button"));

        for (WebElement webElement : buttonsList) {
            if (webElement.getAttribute("style") != null &&
                    webElement.getAttribute("style").equals("background-image: url(\"/images/icons/silk/page_add.png\");")) {
                countRequiredButtons++;
                requiredButtons.add(webElement);
            }
        }
        System.out.println("Size of requiredButtons: " + countRequiredButtons);

        for (int i = 0, loopList = 0; i < countRequiredButtons; i++, loopList++) {
            if (loopList == fileList.size()) {
                loopList = 0;
            }
            System.out.println("Button number: " + i);
            Thread.sleep(1000);
            JavascriptExecutor clicker = (JavascriptExecutor) driver;
            clicker.executeScript("arguments[0].click();", requiredButtons.get(i));
            Thread.sleep(2000);
            WebElement file = driver.findElement(By.className("x-form-file"));
            file.sendKeys(fileList.get(loopList));
            WebElement uploadButton = driver.findElement(By.xpath("//*[text() = 'Загрузить']"));
            clicker.executeScript("arguments[0].click();", uploadButton);
            WebDriverWait wait1 = new WebDriverWait(driver, 100, 2000);
            wait1.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
            WebDriverWait wait2 = new WebDriverWait(driver, 100, 2000);
            wait2.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Пожалуйста подождите']"), "Пожалуйста подождите"));//x-progress-text x-progress-text-back - wait2;
            Thread.sleep(2000);
            System.out.println("NEXT");
        }
    }
}
