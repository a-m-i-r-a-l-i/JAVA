import java.util.ArrayList;
import java.util.List;

public class Database
{
    public static List<User> users = new ArrayList<>();
    public static List<Post> posts = new ArrayList<>();
    public static List<Chat> chats = new ArrayList<>();

    public static boolean foundUser(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public static User findUserByUsername(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equals(username))
            {
                return user;
            }
        }
        return null;
    }
}
