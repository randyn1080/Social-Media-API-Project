package DAO;

import Model.Message;
import java.util.List;

public interface MessageDAO {
    Message createMessage(Message msg);
    Message getMessageById(int msgId);
    List<Message> getAllMessages();
    List<Message> getAllMessagesByAccountId(int accountId);
    Boolean updateMessage(Message message);
    Boolean deleteMessage(int msgId);
}
