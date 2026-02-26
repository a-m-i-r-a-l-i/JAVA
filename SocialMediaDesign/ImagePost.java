public class ImagePost extends Post {
    private String imageUrl;
    private String caption;

    public ImagePost(String id, String author,Visibility visibility ,String imageUrl, String caption) {
        super(id, author, visibility);
        this.imageUrl = imageUrl;
        this.caption = caption;
    }

    @Override
    public void display() {
        System.out.println("🖼 IMAGE POST");
        System.out.println("Author: " + getAuthor());
        System.out.println("URL: " + imageUrl);
        System.out.println("Caption: " + caption);
        System.out.println("Likes: " + getLikeCount());
        System.out.println("Comments: " + getCommentCount());
        System.out.println("-------------------");
    }
}
