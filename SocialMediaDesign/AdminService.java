import java.util.Scanner;

public class AdminService {

    private static Scanner scanner = new Scanner(System.in);

    public static void banUser() {

        System.out.print("Enter username to ban: ");
        String username = scanner.nextLine();

        User user = Database.findUserByUsername(username);

        if (user == null) {
            System.out.println("User not found");
            return;
        }

        if (user.isBanned()) {
            System.out.println("User already banned");
            return;
        }

        user.ban();
        System.out.println("User banned successfully");
    }

    public static void togglePinPost()
    {

        System.out.print("Enter post id: ");
        String id = scanner.nextLine();

        for (Post post : Database.posts)
        {

            if (post.getId().equals(id))
            {

                if (post.isPinned())
                {
                    post.unpin();
                    System.out.println("Post unpinned.");
                }
                else
                {
                    post.pin();
                    System.out.println("Post pinned.");
                }
                return;
            }
        }

        System.out.println("Post not found.");
    }
    public static void viewAllUsers()
    {
        if (Database.users.isEmpty())
        {
            System.out.println("No users found.");
            return;
        }

        for (User user : Database.users)
        {
            System.out.println("Username: " + user.getUsername() + " | Role: " + user.getRole());
        }
    }
}
