public class AuthService {
    private static final String ADMIN_USERNAME = "root";
    private static final String ADMIN_PASSWORD = "root123";


    public static boolean signup(String username, String password, String email, Role role)
    {

        if (Database.foundUser(username))
            return false;

        Database.users.add(new User(username, password, email ,Role.USER));
            return true;
    }

    public static User login(String username, String password)
    {
        if ((username.equals(ADMIN_USERNAME)) && password.equals(ADMIN_PASSWORD))
        {
            return new User(ADMIN_USERNAME, "admin@system", ADMIN_PASSWORD, Role.ADMIN);
        }

        User user = Database.findUserByUsername(username);

        if (user == null)
                return null;

        if (user.isBanned()) {
            System.out.println("You are banned by admin.");
            return null;
        }

        if (user.getPassword().equals(password))
            return user;

        return null;
    }
}

