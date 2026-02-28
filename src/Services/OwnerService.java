package Services;
import java.util.List;
import java.util.Scanner;

import DAO.OrderDAO;
import DAO.OwnerDAO;
import DAO.ProductsDAO;
import Models.Owner;
import Models.Products;
import org.mindrot.jbcrypt.BCrypt;


public class OwnerService {

    static Scanner sc = new Scanner(System.in);

    public static void start() {

        while (true) {
            System.out.println("\n---- OWNER PANEL ----");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    if (login()) {
                        ownerDashboard();
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void register() {

        if (OwnerDAO.ownerExists()) {
            System.out.println("Owner already exists.");
            return;
        }

        System.out.println("---- Owner Registration ----");

        System.out.print("Enter Email: ");
        String email = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Owner owner = new Owner(email, hashed);

        if (OwnerDAO.createOwner(owner)) {
            System.out.println("Registered Successfully!");
            System.out.println("Please Login Now.");
            login();
        } else {
            System.out.println("Registration Failed.");
        }
    }


    public static boolean login() {

        System.out.println("---- Owner Login ----");

        System.out.print("Enter Email: ");
        String email = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        if (OwnerDAO.validateOwner(email, password)) {
            System.out.println("Login Successful!");
            return true;
        } else {
            System.out.println("Invalid Email or Password.");
            return false;
        }
    }


    public static void ownerDashboard() {

        while (true) {

            System.out.println("\n==== OWNER DASHBOARD ====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View All Products");
            System.out.println("5. View Monthly Revenue");
            System.out.println("6. View Best Selling Products");
            System.out.println("7. View Low Stock Products");
            System.out.println("8. Update Stock");
            System.out.println("9. Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addProductFlow();
                    break;

                case 2:
                    updateProductFlow();
                    break;

                case 3:
                    deleteProductFlow();
                    break;

                case 4:
                    viewAllProductsFlow();
                    break;

                case 5:
                    monthlyRevenueFlow();
                    break;

                case 6:
                    ProductsDAO.getBestSellingProducts();
                    break;

                case 7:
                    System.out.print("Enter stock threshold: ");
                    int threshold = sc.nextInt();
                    ProductsDAO.getLowStockProducts(threshold);
                    break;

                case 8:
                    updateStockFlow();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static void addProductFlow() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Stock: ");
        int stock = sc.nextInt();

        Products product = new Products(name, category, price, stock);

        if (ProductsDAO.addProduct(product)) {
            System.out.println("Product Added Successfully!");
        } else {
            System.out.println("Failed to Add Product.");
        }
    }

    private static void updateProductFlow() {

        System.out.print("Enter Product ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        Products existing = ProductsDAO.getProductById(id);

        if (existing == null) {
            System.out.println("Product Not Found.");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Category: ");
        String category = sc.nextLine();

        System.out.print("Enter New Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter New Stock: ");
        int stock = sc.nextInt();

        Products updated = new Products(id, name, category, price, stock);

        if (ProductsDAO.updateProduct(updated)) {
            System.out.println("Product Updated Successfully!");
        } else {
            System.out.println("Update Failed.");
        }
    }

    private static void deleteProductFlow() {

        System.out.print("Enter Product ID to delete: ");
        int id = sc.nextInt();

        if (ProductsDAO.deleteProduct(id)) {
            System.out.println("Product Deleted Successfully!");
        } else {
            System.out.println("Delete Failed.");
        }
    }

    private static void viewAllProductsFlow() {

        List<Products> products = ProductsDAO.getAllProducts();

        for (Products p : products) {
            System.out.println(
                    "ID: " + p.getProductId() +
                            " | Name: " + p.getName() +
                            " | Price: " + p.getPrice() +
                            " | Stock: " + p.getStock()
            );
        }
    }

    private static void monthlyRevenueFlow() {

        System.out.print("Enter Month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter Year: ");
        int year = sc.nextInt();

        double revenue = OrderDAO.getMonthlyRevenue(month, year);

        System.out.println("Monthly Revenue: " + revenue);
    }

    private static void updateStockFlow(){
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        System.out.print("Enter New Stock: ");
        int stock = sc.nextInt();

        ProductsDAO.updateStock(id, stock);
    }
}
