package exercise;

import com.sun.source.tree.Tree;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCentreImpl implements ShoppingCentre {
    // Duplicate products are allowed. So every key will hold a collection of products.

    private HashMap<String, TreeSet<Product>> producerProducts;
    private HashMap<String, HashMap<String, HashSet<Product>>> nameProducerProducts;
    private HashMap<String, TreeSet<Product>> nameProducts;
    private TreeMap<BigDecimal, TreeSet<Product>> priceProducts;

    public ShoppingCentreImpl() {
        producerProducts = new HashMap<>();
        nameProducerProducts = new HashMap<>();
        nameProducts = new HashMap<>();
        priceProducts = new TreeMap<>();
    }

    @Override
    public void addProduct(String name, BigDecimal price, String producer) {
        Product newProduct = new Product(name, price, producer);

        //producerProducts
        if (!producerProducts.containsKey(producer)) {
            producerProducts.put(producer, new TreeSet<>());
        }
        producerProducts.get(producer).add(newProduct);

        //nameProducerProducts
        if (!nameProducerProducts.containsKey(name)) {
            nameProducerProducts.put(name, new HashMap<>());
        }
        if (!nameProducerProducts.get(name).containsKey(producer)) {
            nameProducerProducts.get(name).put(producer, new HashSet<>());
        }
        nameProducerProducts.get(name).get(producer).add(newProduct);

        //nameProducts
        if (!nameProducts.containsKey(name)) {
            nameProducts.put(name, new TreeSet<>());
        }
        nameProducts.get(name).add(newProduct);

        //priceProducts
        if (!priceProducts.containsKey(price)) {
            priceProducts.put(price, new TreeSet<>());
        }
        priceProducts.get(price).add(newProduct);
    }

    @Override
    public int deleteProducts(String producer) {
        TreeSet<Product> productsToDelete = producerProducts.get(producer);

        if (productsToDelete == null || productsToDelete.isEmpty()) {
            return 0;
        }

        producerProducts.remove(producer); // O(1)

        // removeAll is also O(N logN) for balanced trees, but it would need to be called three times
        // (and collection iterated three times). So this way is more efficient (i think)
        for (Product currentProductToDelete : productsToDelete) { // O(N logN), because the collection needs to be iterated
            nameProducts.get(currentProductToDelete.getName()).remove(currentProductToDelete); // O(logN) to find and O(logN) to delete
            if (nameProducts.get(currentProductToDelete.getName()).isEmpty()) {
                // if not deleted, then a reference to an empty useless collection will remain and still use memory.
                // I think this could be considered a memory leak.
                nameProducts.remove(currentProductToDelete.getName());
            }

            priceProducts.get(currentProductToDelete.getPrice()).remove(currentProductToDelete);
            if (priceProducts.get(currentProductToDelete.getPrice()).isEmpty()) {
                priceProducts.remove(currentProductToDelete.getPrice());
            }

            nameProducerProducts
                    .get(currentProductToDelete.getName())
                    .get(currentProductToDelete.getProducer())
                    .remove(currentProductToDelete);
            if (nameProducerProducts
                    .get(currentProductToDelete.getName())
                    .get(currentProductToDelete.getProducer())
                    .isEmpty()) {

                nameProducerProducts
                        .get(currentProductToDelete.getName())
                        .remove(currentProductToDelete.getProducer());

                if (nameProducerProducts.get(currentProductToDelete.getName()).isEmpty()) {
                    nameProducerProducts.remove(currentProductToDelete.getName());
                }
            }
        }

        return productsToDelete.size();
    }

    @Override
    public int deleteProducts(String name, String producer) {
        HashMap<String, HashSet<Product>> producerProducts = nameProducerProducts.get(name);

        if (producerProducts == null || producerProducts.isEmpty()) {
            return 0;
        }

        HashSet<Product> productsToDelete = producerProducts.get(producer);

        if (productsToDelete == null || producerProducts.isEmpty()) {
            return 0;
        }

        producerProducts.remove(producer);
        if (producerProducts.isEmpty()) {
            nameProducerProducts.remove(name);
        }

        for (Product currentProductToDelete : productsToDelete) {
            this.producerProducts.remove(currentProductToDelete.getProducer());
            nameProducts.remove(currentProductToDelete.getName());
            priceProducts.remove(currentProductToDelete.getPrice());
        }

        return productsToDelete.size();
    }

    @Override
    public Iterable<Product> findProductsByName(String name) {
        return getEmptyCollectionIfNull(nameProducts.get(name));
    }

    @Override
    public Iterable<Product> findProductsByProducer(String producer) {
        return getEmptyCollectionIfNull(producerProducts.get(producer));
    }

    @Override
    public Iterable<Product> findProductsByPriceRange(BigDecimal fromPrice, BigDecimal toPrice) {
        BigDecimal oneCentHigherPrice = toPrice.add(new BigDecimal("0.01")); // submap is exclusive on second parameter

        TreeSet<Product> result = new TreeSet<>();
        Collection<TreeSet<Product>> products = null;
        if (oneCentHigherPrice == null) {
            products = priceProducts.subMap(fromPrice, toPrice).values();
        } else {
            products = priceProducts.subMap(fromPrice, oneCentHigherPrice).values();
        }

        for (TreeSet<Product> productTreeSet : products) {
            result.addAll(productTreeSet);
        }

        return result;
    }

    private Iterable<Product> getEmptyCollectionIfNull(Iterable<Product> iterable) {
        return iterable == null ? new ArrayList<>(0) : iterable;
    }
}
