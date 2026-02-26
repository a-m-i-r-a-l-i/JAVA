import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatService {

    public static void showMyChats(User user)
    {
        int index = 1;
        boolean hasChat = false;

        for (Chat chat : Database.chats)
        {
            if (chat.hasUser(user))
            {
                hasChat = true;
                System.out.print(index + "- ");

                if (chat instanceof DirectChat)
                {
                    User other = getOtherUser((DirectChat) chat, user);
                    System.out.println("Direct with " + other.getUsername());
                }
                else if (chat instanceof GroupChat)
                {
                    GroupChat g = (GroupChat) chat;
                    System.out.println("Group: " + g.getName());
                }
                index++;
            }
        }

        if (!hasChat)
        {
            System.out.println("You have no chats.");
        }
    }

    private static User getOtherUser(DirectChat chat, User me)
    {
        for (ChatMembership cm : chat.getMembers()) {
            if (!cm.getUser().equals(me))
                return cm.getUser();
        }
        return null;
    }

    public static void createDirectChat(User currentUser, Scanner scanner)
    {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        User other = Database.findUserByUsername(username);

        if (other == null)
        {
            System.out.println("User not found.");
            return;
        }

        if (other.equals(currentUser))
        {
            System.out.println("You cannot chat with yourself.");
            return;
        }

        // چک چت تکراری
        for (Chat chat : Database.chats) {
            if (chat instanceof DirectChat && chat.hasUser(currentUser) && chat.hasUser(other))
            {
                System.out.println("Chat already exists.");
                return;
            }
        }

        String id = "C" + (Database.chats.size() + 1);
        DirectChat chat = new DirectChat(id);

        chat.addMember(new ChatMembership(currentUser, chat, "MEMBER"));
        chat.addMember(new ChatMembership(other, chat, "MEMBER"));

        Database.chats.add(chat);

        System.out.println("Direct chat created.");
    }
    public static void createGroupChat(User currentUser, Scanner scanner)
    {

        System.out.print("Group name: ");
        String name = scanner.nextLine();

        String id = "G" + (Database.chats.size() + 1);
        GroupChat group = new GroupChat(id, name);

        group.addMember(new ChatMembership(currentUser, group, "ADMIN"));

        Database.chats.add(group);

        System.out.println("Group created.");
    }

    public static Chat selectChat(User user, Scanner scanner)
    {

        List<Chat> myChats = new ArrayList<>();

        int index = 1;
        for (Chat chat : Database.chats)
        {
            if (chat.hasUser(user))
            {
                myChats.add(chat);
                System.out.println(index + "- " + chatTitle(chat, user));
                index++;
            }
        }

        if (myChats.isEmpty())
        {
            System.out.println("No chats.");
            return null;
        }

        System.out.print("Select chat (0 to back): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) return null;

        return myChats.get(choice - 1);
    }



    private static String chatTitle(Chat chat, User user)
    {
        if (chat instanceof DirectChat) {
            for (ChatMembership cm : chat.getMembers()) {
                if (!cm.getUser().equals(user))
                    return "Direct with " + cm.getUser().getUsername();
            }
        }
        if (chat instanceof GroupChat) {
            return "Group: " + ((GroupChat) chat).getName();
        }
        return "Chat";
    }

    private static void showMessages(Chat chat, User viewer)
    {

        if (chat.getMessages().isEmpty())
        {
            System.out.println("No messages yet.");
            return;
        }
        int index = 1;
        for (Message m : chat.getMessages())
        {
            System.out.println(index + ") " + m.getSender().getUsername() + ": " + m.getText());
            index++;
            if(!m.isSeen() && !m.getSender().equals(viewer))
            {
                m.markSeen();
            }
            if(m.isSeen())
            {
                System.out.println("✔");
            }
            System.out.println();
        }
    }

    private static void sendMessage(User user, Chat chat, Scanner scanner) {

        for (ChatMembership cm : chat.getMembers()) {
            if (cm.getUser().equals(user) && cm.isMuted()) {
                System.out.println("You are muted in this chat.");
                return;
            }
        }

        System.out.print("Message: ");
        String text = scanner.nextLine();

        Message message = new Message(user, text);
        chat.addMessage(message);

        System.out.println("Message sent.");
    }


    public static void openChat(User user, Chat chat, Scanner scanner) {

        while (true) {
            System.out.println("=== Chat ===");
            System.out.println("1- View messages");
            System.out.println("2- Send message");
            if(chat instanceof GroupChat && chat.isAdmin(user))
            {
                System.out.println("3- Add member");
                System.out.println("4- Remove member");
                System.out.println("5- Mute / Unmute member");
            }
            System.out.println("6- Leave group");
            if (user.getRole() == Role.ADMIN)
            {
                System.out.println("7- Delete chat");
            }
            System.out.println("8- Delete message");
            System.out.println("0- Back");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showMessages(chat ,user);
                    break;
                case 2:
                    sendMessage(user, chat, scanner);
                    break;
                case 3:
                    ChatService.addMemberToGroup(user ,chat,scanner);
                case 4:
                    ChatService.removeMember(user ,chat,scanner);
                    break;
                case 5:
                    ChatService.toggleMute(user ,chat,scanner);
                    break;
                case 6:
                    ChatService.leaveGroup(user , chat);
                    break;
                case 7:
                    ChatService.leaveGroup(user , chat);
                    return;
                case 8:
                    System.out.print("Message number: ");
                    int msgNum = scanner.nextInt();
                    scanner.nextLine();
                    ChatService.deleteMessage(user, chat, msgNum - 1);
                    break;
                case 0:
                    return;
            }
        }
    }

    public static void addMemberToGroup(User currentUser, Chat chat, Scanner scanner)
    {
        if(!(chat instanceof GroupChat))
        {
            System.out.println("This is  not a group.");
            return;
        }
        if (!chat.isAdmin(currentUser))
        {
            System.out.println("You are not an admin.");
            return;
        }
        System.out.println("Enter member Username to add to group: ");
        String username = scanner.nextLine();

        User newUser = Database.findUserByUsername(username);
        if(newUser == null)
        {
            System.out.println("User not found.");
            return;
        }
        if (chat.hasUser(newUser))
        {
            System.out.println("You are already member of this chat.");
            return;
        }
        chat.addMember(new ChatMembership(newUser, chat, "MEMBER"));
        System.out.println("Member added.");
    }

    public static void removeMember(User currentuser, Chat chat, Scanner scanner)
    {
        if(!(chat instanceof GroupChat))
        {
            System.out.println("This is  not a group.");
            return;
        }
        if (!chat.isAdmin(currentuser))
        {
            System.out.println("You are not an admin.");
            return;
        }
        System.out.println("Enter member Username to remove: ");
        String username = scanner.nextLine();
        ChatMembership target = null;

        for(ChatMembership cm : chat.getMembers())
        {
            if(cm.getUser().getUsername().equals(username))
            {
                target = cm;
                break;
            }
        }
        if(target == null)
        {
            System.out.println("User not found.");
            return;
        }
        if (target.getUser().equals(currentuser))
        {
            System.out.println("Admin cannot remove himself");
            return;
        }

        chat.getMembers().remove(target);
        System.out.println("Member removed.");
    }
    public static void toggleMute(User currenyUser, Chat chat, Scanner scanner)
    {
        if (!(chat instanceof GroupChat))
        {
            System.out.println("This is  not a group.");
            return;
        }
        if(!chat.isAdmin(currenyUser))
        {
            System.out.println("You are not an admin.");
            return;
        }
        System.out.println("Enter mute Username to toggle: ");
        String username = scanner.nextLine();
        for(ChatMembership cm : chat.getMembers())
        {
            if(cm.getUser().getUsername().equals(username))
            {
                if(cm.isMuted())
                {
                    cm.unmute();
                    System.out.println("User unmuted.");
                }
                else
                {
                    cm.mute();
                    System.out.println("User muted.");
                }
                return;
            }
        }
        System.out.println("User not found.");
    }

    public static void leaveGroup(User User, Chat chat)
    {
        if(!(chat instanceof GroupChat))
        {
            System.out.println("This is  not a group.");
            return;
        }

        ChatMembership target = null;

        for(ChatMembership cm : chat.getMembers())
        {
            if(cm.getUser().equals(User))
            {
                target = cm;
                break;
            }
        }
        if (target == null)
        {
            System.out.println("User not found.");
            return;
        }
        chat.getMembers().remove(target);
        System.out.println("You left the group.");

        if (chat.getMembers().isEmpty())
        {
            Database.chats.remove(chat);
            System.out.println("Group deleted (no members left).");
        }

    }

    public static void deleteMessage(User user, Chat chat, int messageIndex)
    {
        if(messageIndex < 0 || messageIndex >= chat.getMembers().size())
        {
            System.out.println("Invalid massage number.");
            return;
        }
        Message msg = chat.getMessages().get(messageIndex);

        boolean isOwner = msg.getSender().equals(user);
        boolean isAdmin = chat.isAdmin(user);
        boolean isRoot = user.getRole() == Role.ADMIN;

        if (isAdmin || isRoot || isOwner)
        {
            msg.delete();
            System.out.println("Message deleted.");
        }
        else
        {
            System.out.println("You don't have permission.");
        }
    }
    public static void deleteChat(User currentUser, Chat chat)
    {

        if (currentUser.getRole() != Role.ADMIN)
        {
            System.out.println("Only system admin can delete chats.");
            return;
        }

        Database.chats.remove(chat);
        System.out.println("Chat deleted by admin.");
    }

}
