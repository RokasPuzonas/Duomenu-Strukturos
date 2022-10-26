package utils;

import models.Order;
import models.Product;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InOutUtils {
    public static void readOrders(String filePath, List<Order> outputData) {
        FileInputStream fileStream = null;
        Scanner scanner = null;

        try {
            fileStream = new FileInputStream(filePath);
            scanner = new Scanner(fileStream, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String[] lines = scanner.nextLine().split(",");
                String customerSurname = lines[0].trim();
                String customerName = lines[1].trim();
                String productID = lines[2].trim();
                int productAmount = Integer.parseInt(lines[3].trim());
                outputData.add(new Order(customerSurname, customerName, productID, productAmount));
            }
            fileStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public static void readProducts(String filePath, List<Product> outputData) {
        FileInputStream fileStream = null;
        Scanner scanner = null;

        try {
            fileStream = new FileInputStream(filePath);
            scanner = new Scanner(fileStream, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String[] lines = scanner.nextLine().split(",");
                String ID = lines[0].trim();
                String name = lines[1].trim();
                float price = Float.parseFloat(lines[2].trim());
                outputData.add(new Product(ID, name, price));
            }
            fileStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public static void printOrders(LinkedList<Order> orders, String header)
    {
        if (orders.get(0) == null) {
            System.out.println("Tuščia");
            return;
        }

        System.out.println("-".repeat(49));
        System.out.printf("| %-45s |%n", header);
        System.out.println("-".repeat(49));
        System.out.printf("| %-10s | %-6s | %7s | %13s |%n", "Pavardė", "Vardas", "Įtaisas", "Įtaiso kiekis");
        System.out.println("-".repeat(49));
        for (Order e : orders) {
            System.out.printf("| %-10s | %-6s | %7s | %13d |%n", e.customerSurname, e.customerName, e.productID, e.productAmount);
        }
        System.out.println("-".repeat(49));
    }

    public static void printProducts(LinkedList<Product> products, String header)
    {
        if (products.get(0) == null) {
            System.out.println("Tuščia");
            return;
        }

        System.out.println("-".repeat(35));
        System.out.printf("| %-31s |%n", header);
        System.out.println("-".repeat(35));
        System.out.printf("| %-2s | %-18s | %5s |%n", "ID", "Vardas", "Kaina");
        System.out.println("-".repeat(35));
        for (Product e : products)
        {
            System.out.printf("| %-2s | %-18s | %5.2f |%n", e.ID, e.name, e.price);
        }
        System.out.println("-".repeat(35));
    }

    public static void printOrdersWithPrices(LinkedList<Order> orders, LinkedList<Product> products, String header)
    {
        if (orders.get(0) == null) {
            System.out.println("Tuščia");
            return;
        }

        System.out.println("-".repeat(53));
        System.out.printf("| %-49s |%n", header);
        System.out.println("-".repeat(53));
        System.out.printf("| %-10s | %-6s | %13s | %11s |%n", "Pavardė", "Vardas", "Įtaiso kiekis", "Kaina, eur.");
        System.out.println("-".repeat(53));
        for (Order order : orders) {
            Product product = TaskUtils.FindByID(products, order.productID);
            System.out.printf("| %-10s | %-6s | %13s | %11.2f |%n", order.customerSurname, order.customerName, order.productAmount, order.productAmount * product.price);
        }
        System.out.println("-".repeat(53));
    }

    public static void printMostPopularProducts(LinkedList<Order> orders, LinkedList<Product> products, String header)
    {
        if (products.get(0) == null) {
            System.out.println("Tuščia");
            return;
        }

        System.out.println("-".repeat(62));
        System.out.printf("| %-58s |%n", header);
        System.out.println("-".repeat(62));
        System.out.printf("| %-2s | %-10s | %19s | %18s |%n", "ID", "Vardas", "Įtaisų kiekis, vnt.", "Įtaisų kaina, eur.");
        System.out.println("-".repeat(62));
        for (Product e : products)
        {
            int sales = TaskUtils.CountProductSales(orders, e.ID);
            System.out.printf("| %-2s | %-10s | %19d | %18.2f |%n", e.ID, e.name, sales, sales * e.price);
        }
        System.out.println("-".repeat(62));
    }
}
