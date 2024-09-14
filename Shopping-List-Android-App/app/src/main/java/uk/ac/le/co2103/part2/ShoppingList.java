package uk.ac.le.co2103.part2;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private int listId; //unique internal field not shown anywhere in app

    private String name; //unique

    private String image; //optional

    private List<Product> products;

    public ShoppingList(int listId, String name, String image) {
        this.listId = listId;
        this.name = name;
        this.image = image;
        this.products = new ArrayList<>();
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProd(Product product) {
        this.products.add(product);
    }
}
