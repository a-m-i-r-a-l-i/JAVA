public class GroupChat extends Chat {

    private String name;

    public GroupChat(String id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
