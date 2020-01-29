package exercise;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ShoppingCentreImplTest {
    ShoppingCentreImpl shoppingCentre;
    Product product0;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;

    @Before
    public void init() {
        shoppingCentre = new ShoppingCentreImpl();

        shoppingCentre.addProduct("Beer", new BigDecimal("2.00"), "Zagorka");
        shoppingCentre.addProduct("Beer", new BigDecimal("2.00"), "Kamenitza");
        shoppingCentre.addProduct("Beer", new BigDecimal("2.20"), "Arianna");
        shoppingCentre.addProduct("Peanuts", new BigDecimal("2.00"), "Arianna");
        shoppingCentre.addProduct("Beer Opener", new BigDecimal("2.20"), "Kamenitza");
        shoppingCentre.addProduct("Beer Socks", new BigDecimal("2.00"), "Zagorka");
    }

    @Test
    public void findProductsByName_whenSearchingBeer_find3CorrectProducts() {
        Product product0 = new Product("Beer", new BigDecimal("2.00"), "Zagorka");
        Product product1 = new Product("Beer", new BigDecimal("2.00"), "Kamenitza");
        Product product2 = new Product("Beer", new BigDecimal("2.20"), "Arianna");


        for (Product product : shoppingCentre.findProductsByName("Beer")) {

        }
    }

    private boolean productsAreEqualByNamePriceProducer(Product product1, Product product2) {
        return product1.getName().equals(product2.getName())
                && product1.getPrice().equals(product2.getPrice())
                && product1.getProducer().equals(product2.getProducer());
    }
}