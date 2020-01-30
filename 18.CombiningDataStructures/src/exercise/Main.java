package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws IOException {
        ShoppingCentre shoppingCentre = new ShoppingCentreImpl();
        StringBuilder outputResult = new StringBuilder();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        int numberOfInputs = Integer.parseInt(consoleReader.readLine());

        for (int i = 0; i < numberOfInputs; i++) {
            String[] inputTokens = consoleReader.readLine().split(" ", 2);

            switch (inputTokens[0]) {
                case "AddProduct":
                    String[] productTokens = inputTokens[1].split(";");
                    shoppingCentre.addProduct(productTokens[0], new BigDecimal(productTokens[1]), productTokens[2]);

                    outputResult.append("Product added").append(System.lineSeparator());
                    break;

                case "FindProductsByName":
                    Iterable<Product> productsByNameFound = shoppingCentre.findProductsByName(inputTokens[1]);
                    addIterableToOutput(productsByNameFound, outputResult);
                    break;

                case "FindProductsByProducer":
                    Iterable<Product> productsFoundByProducer = shoppingCentre.findProductsByProducer(inputTokens[1]);
                    addIterableToOutput(productsFoundByProducer, outputResult);
                    break;

                case "FindProductsByPriceRange":
                    Iterable<Product> productsFoundByPriceRange = shoppingCentre.findProductsByPriceRange(
                            new BigDecimal(inputTokens[1].split(";")[0]),
                            new BigDecimal(inputTokens[1].split(";")[1]));
                    addIterableToOutput(productsFoundByPriceRange, outputResult);
                    break;

                case "DeleteProducts":
                    int deletedProductsCount;

                    if (inputTokens[1].contains(";")) {
                        deletedProductsCount = shoppingCentre.deleteProducts(
                                inputTokens[1].split(";")[0],
                                inputTokens[1].split(";")[1]);
                    } else {
                        deletedProductsCount = shoppingCentre.deleteProducts(inputTokens[1]);
                    }

                    if (deletedProductsCount == 0) {
                        outputResult.append("No products found").append(System.lineSeparator());
                    } else {
                        outputResult.append(deletedProductsCount).append(" products deleted").append(System.lineSeparator());
                    }
                    break;
            }
        }
        consoleReader.close();

        System.out.println(outputResult.toString());
    }

    private static void addIterableToOutput(Iterable<Product> products, StringBuilder output) {
        if (!products.iterator().hasNext()) {
            output.append("No products found").append(System.lineSeparator());
        } else {
            for (Product product : products) {
                output.append(product.toString()).append(System.lineSeparator());
            }
        }
    }
}
