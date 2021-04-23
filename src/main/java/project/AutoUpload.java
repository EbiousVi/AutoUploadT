package project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.options.WIN10Options;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static project.FilesWalker.collectLists;

public class AutoUpload {
    private static int downloadCount = 0;
    private static int recountUploadedFiles = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        String url = setUrlAndDirPath();

        WebDriver driver = Driver.getChromeWebDriver(new WIN10Options());
        driver.get(url);
        TimeUnit.MILLISECONDS.sleep(25000);

        closeAlert(driver);
        uploadQual(driver);
        uploadTech(driver);
        uploadComm(driver);
        validation(driver);
        rightScroll(driver);
        contracts(driver);
    }

    private static void contracts(WebDriver driver) throws IOException, InterruptedException {
        WebElement contracts = driver.findElement(By.xpath("//*[text() = 'Заключение договора']/ancestor::a"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", contracts);
        Thread.sleep(1000);

        List<WebElement> contractFilesToDownload = driver.findElements(By.xpath("//*[contains(text(), 'Сторона по договору (Покупатель):')]/ancestor::fieldset//a[contains(@href,'file')]"));

        for (WebElement elem : contractFilesToDownload) {
            WebDriverWait wait = new WebDriverWait(driver, 100, 1000);
            wait.until(ExpectedConditions.elementToBeClickable(elem));
            JavascriptExecutor download = (JavascriptExecutor) driver;
            download.executeScript("arguments[0].click();", elem);
            Thread.sleep(1000);
        }

        Map<Long, String> map = FilesWalkerForContracts.collectContracts();
        ArrayList<String> values = new ArrayList<>(map.values());

        List<WebElement> webElements = driver.findElements(By.xpath("//button[@style='background-image: url(\"/images/icons/silk/page_add.png\");']"));
        webElements.remove(webElements.size() - 1);

        if (values.size() == webElements.size()) {
            for (int i = 0; i < webElements.size(); i++) {
                JavascriptExecutor click = (JavascriptExecutor) driver;
                click.executeScript("arguments[0].click();", webElements.get(i));
                TimeUnit.MILLISECONDS.sleep(1000);
                WebElement input = driver.findElement(By.xpath("//*[contains(text(), 'Шаблон')]/ancestor::form//input[@type='file']"));
                input.sendKeys(values.get(i));
                WebElement uploadFile = driver.findElement(By.xpath("//*[text() = 'Загрузить']"));
                click.executeScript("arguments[0].click();", uploadFile);
                waiting(driver);
            }
        }
    }

    private static void validation(WebDriver driver) {
        List<WebElement> uploadedQualFiles = driver.findElements(By.xpath("//*[contains(text(), 'Квалификационная документация')]/ancestor::fieldset//a[contains(@href,'file')]"));
        recountUploadedFiles += uploadedQualFiles.size();

        List<WebElement> uploadedTechFiles = driver.findElements(By.xpath("//*[contains(text(), 'Техническая документация')]/ancestor::fieldset//a[contains(@href,'file')]"));
        recountUploadedFiles += uploadedTechFiles.size();

        List<WebElement> uploadedCommFiles1 = driver.findElements(By.xpath("//*[contains(text(), 'Дополнительные документы')]/ancestor::fieldset//a[contains(@href,'file')]"));
        if (uploadedCommFiles1.size() == 0) {
            List<WebElement> uploadedCommFiles2 = driver.findElements(By.xpath("//*[contains(text(), 'Коммерческое предложение и иные документы')]/ancestor::fieldset//a[contains(@href,'file')]"));
            recountUploadedFiles += uploadedCommFiles2.size();
        } else {
            recountUploadedFiles += uploadedCommFiles1.size();
        }

        System.out.println("Файлов в папке - "
                + (FilesWalker.qualList.size()
                + FilesWalker.techList.size()
                + FilesWalker.commList.size()));
        System.out.println("Вовремя загрузки = " + downloadCount);
        System.out.println("Проверка после загрузки = " + recountUploadedFiles);
    }

    private static void closeAlert(WebDriver driver) throws InterruptedException {
        WebElement alertWindow = driver.findElement(By.xpath("//*[text() = 'Закрыть']"));
        JavascriptExecutor closeAlertWindow = (JavascriptExecutor) driver;
        closeAlertWindow.executeScript("arguments[0].click();", alertWindow);
        Thread.sleep(2000);
    }

    private static void uploadComm(WebDriver driver) throws InterruptedException {
        WebElement commSection = driver.findElement(By.xpath("//*[text() = 'Коммерческая часть предложения']/ancestor::a"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", commSection);
        Thread.sleep(2000);

        for (String s : FilesWalker.commList) {
            WebElement commFile;
            try {
                commFile = driver.findElement(By.xpath("//*[contains(text(), 'Дополнительные документы')]/ancestor::fieldset//input[@type='file']"));
            } catch (Exception e) {
                commFile = driver.findElement(By.xpath("//*[contains(text(), 'Коммерческое предложение и иные документы')]/ancestor::fieldset//input[@type='file']"));
            }
            commFile.sendKeys(s);
            waiting(driver);
            downloadCount++;
        }
    }

    private static void uploadTech(WebDriver driver) throws InterruptedException {
        WebElement techSection = driver.findElement(By.xpath("//*[text() = 'Техническая часть предложения']/ancestor::a"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", techSection);
        Thread.sleep(2000);

        for (String s : FilesWalker.techList) {
            WebElement techFile = driver.findElement(By.xpath("//*[contains(text(), 'Техническая документация')]/ancestor::fieldset//input[@type='file']"));
            techFile.sendKeys(s);
            waiting(driver);
            downloadCount++;
        }
    }

    private static void uploadQual(WebDriver driver) throws InterruptedException {
        WebElement qualSection = driver.findElement(By.xpath("//*[text() = 'Квалификационная часть предложения']/ancestor::a"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", qualSection);
        Thread.sleep(2000);

        for (String s : FilesWalker.qualList) {
            WebElement qualFile = driver.findElement(By.xpath("//*[contains(text(), 'Квалификационная документация')]/ancestor::fieldset//input[@type='file']"));
            qualFile.sendKeys(s);
            waiting(driver);
            downloadCount++;
        }
    }

    private static String setUrlAndDirPath() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Путь к директории");
        String dirPath = reader.readLine();
        System.out.println("Ссылка на процедуру");
        String url = reader.readLine();
        collectLists(dirPath);
        reader.close();
        return url;
    }

    private static void rightScroll(org.openqa.selenium.WebDriver driver) throws InterruptedException {
        WebElement right1 = driver.findElement(By.xpath("//div[@class='x-tab-scroller-right x-unselectable']"));
        Actions builder = new Actions(driver);
        builder.moveToElement(right1).click(right1);
        builder.perform();
        Thread.sleep(2000);
    }

    private static void waiting(org.openqa.selenium.WebDriver driver) throws InterruptedException {
        WebDriverWait wait1 = new WebDriverWait(driver, 100, 2000);
        wait1.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Загрузка файла']"), "Загрузка файла"));
        WebDriverWait wait2 = new WebDriverWait(driver, 100, 2000);
        wait2.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Пожалуйста подождите']"), "Пожалуйста подождите"));
        Thread.sleep(2000);
    }
}
