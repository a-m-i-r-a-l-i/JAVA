import java.util.ArrayList;
import java.util.List;

public abstract class Chat {

    protected String id;
    protected List<Message> messages = new ArrayList<>();
    protected List<ChatMembership> members = new ArrayList<>();

    public Chat(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<ChatMembership> getMembers() {
        return members;
    }

    public void addMember(ChatMembership membership) {
        members.add(membership);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public boolean hasUser(User user) {
        for (ChatMembership cm : members) {
            if (cm.getUser().equals(user))
                return true;
        }
        return false;
    }

    public boolean isAdmin(User user)
    {
        for (ChatMembership cm : members)
        {
            if (cm.getUser().equals(user) && cm.getRole().equals("ADMIN"))
            {
                return true;
            }
        }
        return false;
    }
}
