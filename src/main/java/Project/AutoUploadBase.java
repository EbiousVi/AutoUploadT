package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static Project.FilesWalkerBase.Walker;

public class AutoUploadBase {
    private static int downloadCount = 0;
    private static int recountLoadedFiles = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Путь к директории");
        String dirPath = reader.readLine();
        System.out.println("Ссылка на процедуру");
        String url = reader.readLine();
        Walker(dirPath);
        reader.close();

        org.openqa.selenium.WebDriver driver = Project.WebDriver.getChromeWebDriver();
        driver.get(url);
        Thread.sleep(20000);

        WebElement alertWindow = driver.findElement(By.xpath("//*[text() = 'Закрыть']"));
        JavascriptExecutor closeAlertWindow = (JavascriptExecutor) driver;
        closeAlertWindow.executeScript("arguments[0].click();", alertWindow);
        Thread.sleep(2000);

        WebElement qualSection = driver.findElement(By.xpath("//*[text() = 'Квалификационная часть предложения']/ancestor::a"));
        qualSection.click();
        Thread.sleep(2000);

        WebElement qualSection2 = driver.findElement(By.xpath("//*[text() = 'Квалификационная часть предложения']/ancestor::a"));
        JavascriptExecutor repeatQual = (JavascriptExecutor) driver;
        repeatQual.executeScript("arguments[0].click();", qualSection2);
        Thread.sleep(2000);

        for (String s : FilesWalkerBase.qualList) {
            WebElement qualFile = driver.findElement(By.xpath("//*[contains(text(), 'Квалификационная документация')]/ancestor::fieldset//input[@type='file']"));
            qualFile.sendKeys(s);
            WebDriverWait wait1 = new WebDriverWait(driver, 100, 2000);
            wait1.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
            WebDriverWait wait2 = new WebDriverWait(driver, 100, 2000);
            wait2.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Пожалуйста подождите']"), "Пожалуйста подождите"));
            Thread.sleep(2000);
            downloadCount++;
        }

        WebElement techSection = driver.findElement(By.xpath("//*[text() = 'Техническая часть предложения']/ancestor::a"));
        techSection.click();
        Thread.sleep(2000);

        WebElement techSection2 = driver.findElement(By.xpath("//*[text() = 'Техническая часть предложения']/ancestor::a"));
        JavascriptExecutor repeatTech = (JavascriptExecutor) driver;
        repeatTech.executeScript("arguments[0].click();", techSection2);
        Thread.sleep(2000);

        for (String s : FilesWalkerBase.techList) {
            WebElement techFile = driver.findElement(By.xpath("//*[contains(text(), 'Техническая документация')]/ancestor::fieldset//input[@type='file']"));
            techFile.sendKeys(s);
            WebDriverWait wait1 = new WebDriverWait(driver, 100, 2000);
            wait1.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
            WebDriverWait wait2 = new WebDriverWait(driver, 100, 2000);
            wait2.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Пожалуйста подождите']"), "Пожалуйста подождите"));
            Thread.sleep(2000);
            downloadCount++;
        }


        WebElement right1 = driver.findElement(By.xpath("//div[@class='x-tab-scroller-right x-unselectable']"));
        right1.click();
        Thread.sleep(2000);


        WebElement commSection = driver.findElement(By.xpath("//*[text() = 'Коммерческая часть предложения']/ancestor::a"));
        commSection.click();
        Thread.sleep(2000);

        WebElement commSection2 = driver.findElement(By.xpath("//*[text() = 'Коммерческая часть предложения']/ancestor::a"));
        JavascriptExecutor repeatComm = (JavascriptExecutor) driver;
        repeatComm.executeScript("arguments[0].click();", commSection2);
        Thread.sleep(2000);

        for (String s : FilesWalkerBase.commList) {
            WebElement commFile = driver.findElement(By.xpath("//*[contains(text(), 'Коммерческое предложение и иные документы')]/ancestor::fieldset//input[@type='file']"));
            commFile.sendKeys(s);
            WebDriverWait wait1 = new WebDriverWait(driver, 100, 2000);
            wait1.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
            WebDriverWait wait2 = new WebDriverWait(driver, 100, 2000);
            wait2.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Пожалуйста подождите']"), "Пожалуйста подождите"));
            Thread.sleep(2000);
            downloadCount++;
        }

        try {
            List<WebElement> uploadedQualFiles = driver.findElements(By.xpath("//*[contains(text(), 'Квалификационная документация')]/ancestor::fieldset//a[contains(@href,'file')]"));
            recountLoadedFiles += uploadedQualFiles.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<WebElement> uploadedTechFiles = driver.findElements(By.xpath("//*[contains(text(), 'Техническая документация')]/ancestor::fieldset//a[contains(@href,'file')]"));
            recountLoadedFiles += uploadedTechFiles.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<WebElement> uploadedCommFiles = driver.findElements(By.xpath("//*[contains(text(), 'Коммерческое предложение и иные документы')]/ancestor::fieldset//a[contains(@href,'file')]"));
            recountLoadedFiles += uploadedCommFiles.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Файлов в папке - " + (FilesWalkerBase.qualList.size() + FilesWalkerBase.techList.size() + FilesWalkerBase.commList.size()));
        System.out.println("Вовремя загрузки = " + downloadCount);
        System.out.println("Проверка после загрузки = " + recountLoadedFiles);
    }
}
