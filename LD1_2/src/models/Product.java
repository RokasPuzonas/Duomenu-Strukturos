package models;

public class Product implements Comparable<Product> {
    public String ID;
    public String name;
    public float price;

    public Product(String ID, String name, float price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Product))
            return false;

        Product product = (Product)o;
        return ID.equals(product.ID) &&
                name.equals(product.name) &&
                price == product.price;
    }

    @Override
    public int compareTo(Product o) {
        return ID.compareTo(o.ID);
    }
}
