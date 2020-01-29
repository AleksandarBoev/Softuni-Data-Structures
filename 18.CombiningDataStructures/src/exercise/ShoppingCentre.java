package exercise;

import java.math.BigDecimal;

public interface ShoppingCentre {
    void addProduct(String name, BigDecimal price, String producer);

    int deleteProducts (String producer);

    int deleteProducts (String name, String producer);

    Iterable<Product> findProductsByName(String name);

    Iterable<Product> findProductsByProducer(String producer);

    Iterable<Product> findProductsByPriceRange(BigDecimal fromPrice, BigDecimal toPrice);
}
