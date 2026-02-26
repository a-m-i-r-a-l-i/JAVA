import java.util.Scanner;

public class PostService
{

    private static Scanner scanner = new Scanner(System.in);

    public static void createPost(User currentUser)
    {
        System.out.println("1- Image");
        System.out.println("2- Video");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String id = "P" + (Database.posts.size() + 1);

        if (choice == 1)
        {
            System.out.println("Visibility:");
            System.out.println("1- Public");
            System.out.println("2- Private");
            int v = scanner.nextInt();
            scanner.nextLine();

            Visibility visibility = (v == 2)
                    ? Visibility.PRIVATE : Visibility.PUBLIC;

            System.out.print("Image URL: ");
            String url = scanner.nextLine();

            System.out.print("Caption: ");
            String caption = scanner.nextLine();

            Database.posts.add(new ImagePost(id, currentUser.getUsername(),visibility ,url, caption));
        }
        else if (choice == 2)
        {
            System.out.println("Visibility:");
            System.out.println("1- Public");
            System.out.println("2- Private");
            int v = scanner.nextInt();
            scanner.nextLine();

            Visibility visibility = (v == 2)
                    ? Visibility.PRIVATE : Visibility.PUBLIC;


            System.out.print("Video URL: ");
            String url = scanner.nextLine();

            System.out.print("Duration: ");
            int duration = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Caption: ");
            String caption = scanner.nextLine();

            Database.posts.add(new VideoPost(id, currentUser.getUsername(),visibility ,url, duration, caption));
        }

        System.out.println("Post created ");
    }

    public static void addComment(Post post, User user) {
        System.out.print("Enter comment: ");
        String text = scanner.nextLine();

        Comment comment = new Comment(user.getUsername(), text);
        post.addComment(comment);

        System.out.println("Comment added ");
    }
    public static void showComments(Post post)
    {
        if (post.getComments().isEmpty())
        {
            System.out.println("No comments yet.");
            return;
        }

        for (Comment c : post.getComments()) {
            System.out.println(c.getAuthor() + ": " + c.getText());
        }
    }

    public static void removePost()
    {
        System.out.print("Enter post id: ");
        String id = scanner.nextLine();

        for (Post post : Database.posts)
        {
            if (post.getId().equals(id))
            {
                post.remove();
                System.out.println("Post removed.");
                return;
            }
        }
        System.out.println("Post not found.");
    }



    public static void explore(User currentUser)
    {
        if (Database.posts.isEmpty()) {
            System.out.println("No posts.");
            return;
        }
        for (Post post : Database.posts) {
            if (post.isPinned() && !post.isRemoved() && post.isVisibleTo(currentUser)) {
                post.display();
            }
        }
        for (Post post : Database.posts) {
            if (!post.isPinned() && !post.isRemoved() && post.isVisibleTo(currentUser)) {
                post.display();
            }
        }


        System.out.println("Select post (0 to back): ");
        int choice = scanner.nextInt();

        scanner.nextLine();

        if(choice == 0) return;

        Post selectedPost = Database.posts.get(choice - 1);

        System.out.println("1- Like / Unlike");
        System.out.println("2- View comments");
        System.out.println("3- Add comment");
        System.out.println("0- Back");

        int action = scanner.nextInt();
        scanner.nextLine();
        switch (action)
        {
            case 1:
                selectedPost.toggleLike(currentUser.getUsername());
                break;
            case 2:
                showComments(selectedPost);
                break;
            case 3:
                addComment(selectedPost, currentUser);
                break;

        }

    }
    public static void feed(User currentUser)
    {

        boolean found = false;

        for (Post post : Database.posts)
        {

            if (post.isRemoved()) continue;

            if (currentUser.getFollowing().contains(post.getAuthor()))
            {
                post.display();
                found = true;
            }
        }

        if (!found)
        {
            System.out.println("No posts in your feed.");
        }
    }
}
