import java.util.ArrayList;
import java.util.List;

public abstract class Post {
    private Visibility visibility;
    private String id;
    private String author;
    private int likeCount = 0;
    private boolean pinned = false;

    private List<String> likedBy = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    public Post(String id, String author, Visibility visibility) {
        this.id = id;
        this.author = author;
        this.visibility = visibility;
    }

    // getters

    public Visibility getVisibility()
    {
        return visibility;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return comments.size();
    }

    public List<Comment> getComments() {
        return comments;
    }

    // like system
    public void toggleLike(String username) {
        if (likedBy.contains(username)) {
            likedBy.remove(username);
            likeCount--;
        } else {
            likedBy.add(username);
            likeCount++;
        }
    }

    // comment system
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public boolean isVisibleTo(User user)
    {
        if (visibility == Visibility.PUBLIC) return true;

        if (author.equals(user.getUsername())) return true;

        User postAuthor = Database.findUserByUsername(author);
        if (postAuthor == null) return false;

        return postAuthor.getFollowers().contains(user.getUsername());
    }

    //admin
    private boolean removed = false;

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }
//=================================//

    public boolean isPinned() {return pinned;}
    public void pin(){pinned = true;}
    public void unpin(){pinned = false;}

    public abstract void display();
}
