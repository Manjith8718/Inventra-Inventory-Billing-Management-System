package Services;
import DAO.OrderDAO;
import DAO.ProductsDAO;
import Models.CartItem;
import Models.Products;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService {

    private static List<CartItem> cart = new ArrayList<>();
    public static void start() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== CUSTOMER PANEL ====");
            System.out.println("1. View All Products");
            System.out.println("2. View Products By Category");
            System.out.println("3. Add To Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Generate Bill");
            System.out.println("6. Back To Main Menu");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    viewProductsByCategory();
                    break;
                case 3:
                    addToCart();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    generateBill();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewAllProducts() {
        System.out.println("\n=== All Available Products ===");
        List<Products> products = ProductsDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Products p : products) {
            System.out.println(
                    "ID: " + p.getProductId() +
                            " | Name: " + p.getName() +
                            " | Category: " + p.getCategory() +
                            " | Price: " + p.getPrice() +
                            " | Stock: " + p.getStock()
            );
        }
    }

    private static void viewProductsByCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter category: ");
        String category = sc.nextLine();

        List<Products> products = ProductsDAO.getProductsByCategory(category);

        if (products.isEmpty()) {
            System.out.println("No products found in this category.");
            return;
        }

        System.out.println("\n=== Products in " + category + " ===");

        for (Products p : products) {
            System.out.println(
                    "ID: " + p.getProductId() +
                            " | Name: " + p.getName() +
                            " | Price: " + p.getPrice() +
                            " | Stock: " + p.getStock()
            );
        }
    }

    private static void addToCart() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();

        Products product = ProductsDAO.getProductById(productId);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        for (CartItem item : cart) {

            if (item.getProduct().getProductId() == productId) {

                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > product.getStock()) {
                    System.out.println("Insufficient stock available.");
                    return;
                }

                item.setQuantity(newQuantity);
                System.out.println("Cart quantity updated successfully.");
                return;
            }
        }

        if (quantity > product.getStock()) {
            System.out.println("Insufficient stock available.");
            return;
        }

        cart.add(new CartItem(product, quantity));
        System.out.println("Product added to cart successfully.");
    }


    private static void viewCart() {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n==== YOUR CART ====");

        double grandTotal = 0;

        for (CartItem item : cart) {

            int id = item.getProduct().getProductId();
            String name = item.getProduct().getName();
            double price = item.getProduct().getPrice();
            int quantity = item.getQuantity();
            double total = item.getTotalPrice();

            grandTotal += total;

            System.out.println(
                    "ID: " + id +
                            " | Name: " + name +
                            " | Price: " + price +
                            " | Quantity: " + quantity +
                            " | Total: " + total
            );
        }

        System.out.println("----------------------------");
        System.out.println("Grand Total: " + grandTotal);
    }


    private static void generateBill() {

        if (cart.isEmpty()) {
            System.out.println("No items in cart. Cannot generate bill.");
            return;
        }

        String invoiceNumber = "INV-" + System.currentTimeMillis();
        double grandTotal = 0;

        for (CartItem item : cart) {
            grandTotal += item.getTotalPrice();
        }

        int orderId = OrderDAO.insertOrder(invoiceNumber, grandTotal);

        if (orderId == -1) {
            System.out.println("Error generating bill.");
            return;
        }

        for (CartItem item : cart) {

            OrderDAO.insertOrderItem(
                    orderId,
                    item.getProduct().getProductId(),
                    item.getQuantity(),
                    item.getProduct().getPrice()
            );

            ProductsDAO.updateStock(
                    item.getProduct().getProductId(),
                    item.getQuantity()
            );
        }

        createInvoiceFile(invoiceNumber, grandTotal);

        cart.clear();

        System.out.println("Bill generated successfully!");
    }


    private static void createInvoiceFile(String invoiceNumber, double grandTotal) {

        try {

            String filePath = "C:/Users/manji/OneDrive/Desktop/Inventra - Inventory Billing Management System/src/InventraBills/invoice_"
                    + invoiceNumber + ".txt";

            FileWriter fw = new FileWriter(filePath);

            fw.write("===== INVENTRA BILL =====\n");
            fw.write("Invoice Number: " + invoiceNumber + "\n");
            fw.write("Date: " + java.time.LocalDateTime.now() + "\n\n");

            for (CartItem item : cart) {

                fw.write(
                        item.getProduct().getName() +
                                " | Qty: " + item.getQuantity() +
                                " | Price: " + item.getProduct().getPrice() +
                                " | Total: " + item.getTotalPrice() + "\n"
                );
            }

            fw.write("\n----------------------------\n");
            fw.write("Grand Total: " + grandTotal + "\n");

            fw.close();

            System.out.println("Invoice created successfully at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
