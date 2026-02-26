public class Message {

    private User sender;
    private String text;
    private boolean seen = false;
    private boolean deleted = false;

    public Message(User sender, String text)
    {
        this.sender = sender;
        this.text = text;
    }

    public User getSender()
    {
        return sender;
    }

    public String getText()
    {
        return deleted ? "[Deleted]" : text;
    }
    public boolean isSeen()
    {
        return seen;
    }
    public void markSeen()
    {
        this.seen = true;
    }
    public boolean isDeleted()
    {
        return deleted;
    }
    public void delete()
    {
        deleted = true;
    }
}
