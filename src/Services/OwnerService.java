package Services;
import java.util.Scanner;
import DAO.OwnerDAO;
import Models.Owner;
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
            System.out.println("7. View All Bills");
            System.out.println("8. Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("Add Product");
                    break;

                case 2:
                    System.out.println("Update Product");
                    break;

                case 3:
                    System.out.println("Delete Product");
                    break;

                case 4:
                    System.out.println("View Products");
                    break;

                case 5:
                    System.out.println("Monthly Revenue");
                    break;

                case 6:
                    System.out.println("Best Selling Product");
                    break;

                case 7:
                    System.out.println("View All Bills");
                    break;

                case 8:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
