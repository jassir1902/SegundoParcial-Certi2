import com.google.common.collect.Ordering;
import groovy.lang.GString;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestSauceDemo extends BaseTest {

    @Test
    public void removeFromCartTest() throws InterruptedException {
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        addProductToCart("Sauce Labs Onesie");
        backToProducts();
        addProductToCart("Sauce Labs Backpack");
        backToProducts();
        removeProductFromCart("Sauce Labs Backpack");

        WebElement cartIcon = driver.findElement(By.xpath("//*[@id='shopping_cart_container']/a/span"));

        String actualCartText = cartIcon.getText();
        Assertions.assertEquals("1",actualCartText );

        Thread.sleep(5000);
        driver.quit();

    }

    @Test
    public void goToCartTest() throws InterruptedException {
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        addProductToCart("Sauce Labs Onesie");
        backToProducts();
        goToCart();

        WebElement yourCartText = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span"));

        String actualCartText = yourCartText.getText();
        Assertions.assertEquals("Your Cart",actualCartText );

        Thread.sleep(5000);
        driver.quit();

    }

    @Test
    public void continueShoppingTest() throws InterruptedException {
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        goToCart();
        continueShopping();

        WebElement productosText = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span"));

        String actualProductsText = productosText.getText();
        Assertions.assertEquals("Products",actualProductsText );

        Thread.sleep(5000);
        driver.quit();

    }

    @Test
    public void goToCheckout() throws InterruptedException {
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        goToCart();
        gocheckout();

        WebElement checkoutText = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span"));

        String actualCheckoutText = checkoutText.getText();
        Assertions.assertEquals("Checkout: Your Information",actualCheckoutText);

        Thread.sleep(5000);
        driver.quit();

    }

    @Test
    public void verificarDatosIngresados() throws InterruptedException {
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        goToCart();
        gocheckout();
        ingresarDatos("a","a","a");
        continueButton();

        Boolean present = isElementPresentbyXPath("//*[@id='checkout_info_container']/div/form/div[1]/div[4]");
        Assertions.assertTrue(present);

        Thread.sleep(5000);
        driver.quit();

    }

    public void addProductToCart(String productName){
        WebElement product = driver.findElement(By.xpath("//div[text()='"+productName+"']"));
        product.click();
        WebElement addToCartButton = driver.findElement(By.cssSelector("button.btn_primary.btn_inventory"));
        addToCartButton.click();

    }
    public void removeProductFromCart(String productName){
        WebElement product = driver.findElement(By.xpath("//div[text()='"+productName+"']"));
        product.click();
        WebElement removeFromCartButton = driver.findElement(By.cssSelector("#remove"));
        removeFromCartButton.click();

    }

    public void backToProducts(){
        WebElement back = driver.findElement(By.cssSelector("#back-to-products"));
        back.click();

    }

    public void goToCart(){
        WebElement cart = driver.findElement(By.cssSelector("#shopping_cart_container>a"));
        cart.click();

    }
    public void continueShopping(){
        WebElement products = driver.findElement(By.cssSelector("#continue-shopping"));
        products.click();

    }
    public void gocheckout(){
        WebElement checkout = driver.findElement(By.cssSelector("#checkout"));
        checkout.click();

    }
    public void ingresarDatos(String fn, String ln, String pc){
        WebElement firtname = driver.findElement(By.cssSelector("#first-name"));
        firtname.sendKeys(fn);
        WebElement lastname = driver.findElement(By.cssSelector("#last-name"));
        lastname.sendKeys(ln);
        WebElement postalcode = driver.findElement(By.cssSelector("#postal-code"));
        postalcode.sendKeys(pc);

    }
    public void continueButton(){
        WebElement continueButton = driver.findElement(By.cssSelector("#continue"));
        continueButton.click();

    }

    public boolean isElementPresentbyXPath(String locator) {
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
