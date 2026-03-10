package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePageControllerTest {

    @Test
    void createHomePageReturnsHomePageView() {
        HomePageController controller = new HomePageController();
        assertEquals("homePage", controller.createHomePage());
    }
}
