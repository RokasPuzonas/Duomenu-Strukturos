package utils;

import models.Order;
import models.Product;
import java.util.HashMap;

public class TaskUtils {

    public static LinkedList<String> FindMostPopularProducts(Iterable<Order> orders) {
        HashMap<String, Integer> productSales = new HashMap<String, Integer>();
        for (Order order : orders) {
            if (!productSales.containsKey(order.productID)) {
                productSales.put(order.productID, order.productAmount);
            } else {
                productSales.replace(order.productID, productSales.get(order.productID) + order.productAmount);
            }
        }

        LinkedList<String> mostPopularProducts = new LinkedList<String>();
        int mostPopularCount = 0;
        for (String product : productSales.keySet()) {
            int count = productSales.get(product);
            if (count > mostPopularCount) {
                mostPopularCount = count;
                mostPopularProducts = new LinkedList<String>();
                mostPopularProducts.add(product);
            } else if (count == mostPopularCount) {
                mostPopularProducts.add(product);
            }
        }

        return mostPopularProducts;
    }

    public static int CountProductSales(Iterable<Order> orders, String product) {
        int sales = 0;
        for (Order order : orders) {
            if (order.productID.equals(product)) {
                sales += order.productAmount;
            }
        }
        return sales;
    }

    public static Product FindByID(Iterable<Product> products, String id) {
        for (Product product : products) {
            if (product.ID.equals(id)) {
                return product;
            }
        }
        return null;
    }

    public static LinkedList<Product> FindByID(Iterable<Product> products, Iterable<String> ids) {
        LinkedList<Product> foundProducts = new LinkedList<Product>();
        for (String id : ids) {
            foundProducts.add(FindByID(products, id));
        }
        return foundProducts;
    }

    public static LinkedList<Product> FilterByQuantitySoldAndPrice(Iterable<Product> products, Iterable<Order> orders, int minSold, float maxPrice) {
        LinkedList<Product> filtered = new LinkedList<Product>();
        for (Product product : products) {
            if (product.price < maxPrice) {
                int sold = CountProductSales(orders, product.ID);
                if (sold >= minSold) {
                    filtered.add(product);
                }
            }
        }
        return filtered;
    }

    public static LinkedList<Order> MergeOrders(Iterable<Order> orders)
    {
        HashMap<String, Order> ordersByName = new HashMap<>();
        for (Order order : orders)
        {
            var key = order.customerSurname + order.customerName + order.productID;
            if (ordersByName.containsKey(key))
            {
                ordersByName.get(key).productAmount += order.productAmount;
            } else
            {
                ordersByName.put(key, new Order(order.customerSurname, order.customerName, order.productID, order.productAmount));
            }
        }

        LinkedList<Order> mergedOrders = new LinkedList<Order>();
        for (Order order : ordersByName.values())
        {
            mergedOrders.add(order);
        }
        return mergedOrders;
    }

    public static LinkedList<Order> FindCustomerWithSingleProduct(Iterable<Order> orders) {
        HashMap<String, LinkedList<Order>> ordersByCustomer = new HashMap<>();
        for (Order order : MergeOrders(orders)) {
            var key = order.customerName + order.customerSurname;
            if (!ordersByCustomer.containsKey(key)) {
                ordersByCustomer.put(key, new LinkedList<>());
            }
            ordersByCustomer.get(key).add(order);
        }

        LinkedList<Order> finalList = new LinkedList<Order>();
        for (LinkedList<Order> customerOrders : ordersByCustomer.values())
        {
            if (customerOrders.get(1) == null)
            {
                finalList.add(customerOrders.get(0));
            }
        }
        return finalList;
    }
}
