import java.util.Scanner;

public class UserService
{

    private static Scanner scanner = new Scanner(System.in);

    public static void followUser(User currentUser)
    {

        System.out.print("Enter username to follow: ");
        String username = scanner.nextLine();

        User target = Database.findUserByUsername(username);

        if (target == null || target == currentUser)
        {
            System.out.println("Invalid user");
            return;
        }

        currentUser.follow(target);
        System.out.println("Followed successfully");
    }
}
