import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static User currentUser = null;

    public static void main(String[] args)
    {
        while (true)
        {
            showMainMenu();
        }
    }
    public static void showMainMenu()
    {
        System.out.println("1- Login");
        System.out.println("2- Sign Up");
        System.out.println("0- Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice)
        {
            case 1:
                login();
                break;
            case 2:
                signup();
                break;
            case 0:
                System.exit(0);
        }
    }
    public static void signup()
    {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (AuthService.signup(username, password, email,Role.USER))
            System.out.println("Signup successful");
        else
            System.out.println("Username exists");
    }

    public static void login()
    {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = AuthService.login(username, password);

        if (currentUser != null)
        {
            afterLoginMenu();
        }
        else
        {
            System.out.println("password or username not correct");
        }
    }
    public static void userMenu()
    {
        System.out.println("1- Profile");
        System.out.println("2- Explore");
        System.out.println("3- Create Post");
        System.out.println("4- Follow user");
        System.out.println("5- feed");
        System.out.println("6- Chat");
        if (currentUser.getRole() == Role.ADMIN)
        {
            System.out.println("7-adminMenu");
        }
        System.out.println("0- Logout");
    }
    public static void adminMenu()
    {
        System.out.println("1- Explore");
        System.out.println("2- 2- Remove post");
        System.out.println("3- Pin / Unpin post");
        System.out.println("4- View all users");
        System.out.println("5- Ban user");
        System.out.println("0- Logout");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice)
        {
            case 1:
                PostService.explore(currentUser);
                break;
            case 2:
                PostService.removePost();
                break;
            case 3:
                AdminService.togglePinPost();
                break;
            case 4:
                AdminService.viewAllUsers();
            case 5:
                AdminService.banUser();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }
    public static void chatsMenu()
    {
        System.out.println("1- My chats");
        System.out.println("2- New Direct Chat");
        System.out.println("3- New Group Chat");
        System.out.println("0- Back");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice)
        {
            case 1:
                Chat chat = ChatService.selectChat(currentUser, scanner);
                if (chat != null)
                    ChatService.openChat(currentUser, chat, scanner);
                break;

            case 2:
                ChatService.createDirectChat(currentUser , scanner);
                break;
            case 3:
                ChatService.createGroupChat(currentUser , scanner);
                break;
            case 0:
                return;
        }
    }

    public static void afterLoginMenu()
    {
        while (currentUser != null)
        {
                userMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice)
                {
                    case 1:
                        System.out.println(currentUser.getUsername());
                        System.out.println(currentUser.getEmail());
                        break;
                    case 2:
                        PostService.explore(currentUser);
                        break;
                    case 3:
                        PostService.createPost(currentUser);
                        break;
                    case 4:
                        UserService.followUser(currentUser);
                        break;
                    case 5:
                        PostService.feed(currentUser);
                        break;
                    case 6:
                        chatsMenu();
                        break;
                    case 7:
                        if (currentUser.getRole() == Role.ADMIN)
                        {
                            adminMenu();
                        }
                        else System.out.println("you are not admin");
                        break;
                    case 0:
                        currentUser = null;
                        break;
                }
            }
        }
    }

