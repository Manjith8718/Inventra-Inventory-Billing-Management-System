import Services.CustomerService;
import Services.OwnerService;
import java.util.Scanner;
public class Main
{
    public  static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== INVENTRA SYSTEM ====");
            System.out.println("1. Owner");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    OwnerService.start();
                    break;
                case 2:
                    CustomerService.start();
                    break;
                case 3:
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}


