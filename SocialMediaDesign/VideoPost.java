public class VideoPost extends Post {
    private String videoUrl;
    private int duration;
    private String caption;

    public VideoPost(String id, String author, Visibility visibility ,String videoUrl, int duration, String caption) {
        super(id, author , visibility);
        this.videoUrl = videoUrl;
        this.duration = duration;
        this.caption = caption;
    }

    @Override
    public void display() {
        System.out.println("🎬 VIDEO POST");
        System.out.println("Author: " + getAuthor());
        System.out.println("URL: " + videoUrl);
        System.out.println("Duration: " + duration + " sec");
        System.out.println("Caption: " + caption);
        System.out.println("Likes: " + getLikeCount());
        System.out.println("Comments: " + getCommentCount());
        System.out.println("-------------------");
    }
}
