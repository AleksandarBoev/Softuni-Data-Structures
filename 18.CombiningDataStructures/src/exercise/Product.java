package exercise;

import java.math.BigDecimal;

public class Product implements Comparable<Product>{
    private static int idCounter = 0; // only actual way to differentiate products, since name, price and producer can be duplicated

    private int id;
    private String name;
    private BigDecimal price;
    private String producer;

    public Product(String name, BigDecimal price, String producer) {
        id = idCounter;
        setName(name);
        setPrice(price);
        setProducer(producer);

        idCounter++; // setter might contain some validations
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getProducer() {
        return producer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public int compareTo(Product product) {
        int comparisonResult = this.name.compareTo(product.name);

        if (comparisonResult != 0) {
            return comparisonResult;
        }

        comparisonResult = this.producer.compareTo(product.producer);

        if (comparisonResult != 0) {
            return comparisonResult;
        }

        comparisonResult = this.price.compareTo(product.price);

        if (comparisonResult != 0) {
            return comparisonResult;
        }

        // if all sorting is equal, then they will be ordered ascending by addition in collection
        return Integer.compare(id, product.id);
    }

    @Override
    public int hashCode() {
        // probably a bad hashcode, will produce clusters and collisions
        return name.hashCode() + price.hashCode() + producer.hashCode() + id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) { // not possible to be equal. Null.equals() will immediately throw exception
            return false;
        }

        if (this == other) { // same address
            return true;
        }

        if (!(other instanceof Product)) { // need to be of same class
            return false;
        }

        Product otherProduct = (Product) other;
        return name.equals(otherProduct.name)
                && price.equals(otherProduct.price)
                && producer.equals(otherProduct.producer)
                && id == otherProduct.id;
    }

    // for easier debugging
    @Override
    public String toString() {
        return String.format("{%s;%s;%.2f}", name, producer, price);
    }
}
