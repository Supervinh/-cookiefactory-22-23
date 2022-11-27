package fr.unice.polytech.cf;

import fr.unice.polytech.cf.components.CartHandler;
import fr.unice.polytech.cf.components.Catalog;
import fr.unice.polytech.cf.components.Stock;
import fr.unice.polytech.cf.entities.cookies.Cookie;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StockDefinition {
    CartHandler cartHandler;
    Catalog catalog;
    Stock stock;
    Cookie cookie;

    @Given("I want to order {int} cookie")
    public void iWantToOrderCookie(int nbCookies) {
        cartHandler = new CartHandler();
        stock = new Stock();
        catalog = new Catalog(stock);
        cookie = catalog.getCookies().get(0);
    }

    @When("the ingredients used for the cookies are available")
    public void theIngredientsUsedForTheCookiesAreAvailable() {
        if (stock.removeCookieFromStock(cookie))
            cartHandler.addCookie(cookie, 1);
    }


    @When("the ingredients used for the cookies aren't available")
    public void theIngredientsUsedForTheCookiesArenTAvailable() {
        stock.removeIngredient(cookie.getFlavour(), 4);
        try {
            stock.removeCookieFromStock(cookie);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Then("the cart should contain {int} cookie")
    public void the_cart_should_contain_cookies(Integer number) {
        assert (cartHandler.getNbCookies()==number);
    }
}
