import java.util.ArrayList;
import java.util.List;

public class User
{
    private String username;
    private String password;
    private String email;
    private Role role;
    private boolean banned = false;
    private List<String> following = new ArrayList<>();
    private List<String> followers = new ArrayList<>();


    public User(String username, String password, String email,Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    //get
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }

    //set
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBanned() { return banned; }

    public void ban() { banned = true; }


    public void follow(User other)
    {
        if(!following.contains(other.getUsername()))
        {
            following.add(other.getUsername());
            other.followers.add(this.username);
        }
    }

    public void unfollow(User other)
    {
        following.remove(other.getUsername());
        other.followers.remove(this.username);
    }

    public List<String> getFollowing()
    {
        return following;
    }
    public List<String> getFollowers()
    {
        return followers;
    }
}
