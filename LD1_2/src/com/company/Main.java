package com.company;

import models.Order;
import models.Product;
import utils.InOutUtils;
import utils.LinkedList;
import utils.TaskUtils;

public class Main {
    public static void main(String[] args) {
        LinkedList<Order> orders = new LinkedList<Order>();
        LinkedList<Product> products = new LinkedList<Product>();

        InOutUtils.readProducts("U24a.txt", products);
        InOutUtils.readOrders("U24b.txt", orders);

        int n = 1000;
        float k = 10.0f;

        var mostPopularProductIds = TaskUtils.FindMostPopularProducts(orders);
        var mostPopularProducts = TaskUtils.FindByID(products, mostPopularProductIds);
        var filteredProducts = TaskUtils.FilterByQuantitySoldAndPrice(products, orders, n, k);
        var customersWithSingleProduct = TaskUtils.FindCustomerWithSingleProduct(orders);

        InOutUtils.printProducts(products, "Įtaisai");
        InOutUtils.printOrders(orders, "Pirkėjai");
        InOutUtils.printMostPopularProducts(orders, mostPopularProducts, "Populiariausi įtaisai");
        InOutUtils.printOrdersWithPrices(customersWithSingleProduct, products, "Vienos rūšies pirkėjai");
        InOutUtils.printProducts(filteredProducts, String.format("Atrinkti įtaisai (n=%d, k=%.2f)", n, k));
    }
}
