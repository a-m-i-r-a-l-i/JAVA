public class ChatMembership {
    private User user;
    private Chat chat;
    private String role; // MEMBER , ADMIN
    private boolean muted;

    public ChatMembership(User user, Chat chat, String role) {
        this.user = user;
        this.chat = chat;
        this.role = role;
        this.muted = false;
    }

    public User getUser() {
        return user;
    }

    public Chat getChat() {
        return chat;
    }

    public String getRole() {
        return role;
    }

    public boolean isMuted() {
        return muted;
    }

    public void mute() {
        muted = true;
    }

    public void unmute() {
        muted = false;
    }
}
