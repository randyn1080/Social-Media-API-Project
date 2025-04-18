package DAO;

import Model.Message;
import java.util.List;

public interface MessageDAO {
    Message createMessage(Message msg);
    Message getMessageById(int msgId);
    List<Message> getAllMessages();
    List<Message> getAllMessagesByAccountId(int accountId);
    Message updateMessageText(int msgId, String newText);
    Message deleteMessage(int msgId);
}
