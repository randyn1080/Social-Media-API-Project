package Service;

import Model.Message;
import java.util.List;

public interface MessageService {
    Message createMessage(Message msg);
    Message getMessageById(int msgId);
    List<Message> getAllMessages();
    List<Message> getAllMessagesByAccountId(int accountId);
    Message updateMessageText(int msgId, String newText);
    Boolean deleteMessage(int msgId);
}
