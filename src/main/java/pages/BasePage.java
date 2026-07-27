package pages;

import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public abstract class BasePage {

    //<editor-fold desc="Variables and Helpers">
    protected final WebDriver driver;
    protected final ElementActions eActions;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.eActions = new ElementActions(driver);
    }
    //</editor-fold>

}