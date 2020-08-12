package sample;

import javafx.scene.control.TextArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;

public class StatsReaderClass {
    private final Controller controller;
    private String username;
    private String region;
    private TextArea dataField;
    private String URL;

    public StatsReaderClass(Controller _controller){
        controller = _controller;
    }

    public boolean validateRegion(String _region){
        return _region.equals("eune");
    }

    public void setData(String _username, String _region, TextArea _dataField){
        username = _username;
        region = _region;
        dataField = _dataField;
    }

    public void getDataFromWebsite(){
        try {
            Document doc = Jsoup.connect(URL).get();
            getAll(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createURL(){
        URL = "https://" + region + ".op.gg/summoner/userName=" + username;
    }

    public void getAll(Document doc){
        getTier(doc);
        getTierInfo(doc);
        getKDARatio(doc);
    }

    public void getTier(Document doc){
        Elements element = doc.select("div.TierRank");
        dataField.setText(element.text());
    }

    public void getTierInfo(Document doc){
        Elements element = doc.select("div.TierInfo");
        dataField.setText(dataField.getText() + "\n" + element.text());
    }

    public void getKDARatio(Document doc){
        Elements elements = doc.select("div.KDARatio");
        for(Element element : elements){
            dataField.setText(dataField.getText() + "\n" + element.text());
        }
    }

    public void updateData(){
        System.setProperty("webdriver.chrome.driver","src/sample/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        createURL();

        driver.get(URL);
        WebElement element = driver.findElement(By.id("SummonerRefreshButton"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);

        driver.close();
        driver.quit();
    }
}
