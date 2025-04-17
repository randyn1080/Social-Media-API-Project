package Service;

import java.util.List;

import DAO.MessageDAO;
import DAO.MessageDAOImpl;
import Model.Message;

public class MessageServiceImpl implements MessageService{
    private final MessageDAO messageDAO;
    private final AccountService accountService;

    public MessageServiceImpl() {
        this.messageDAO = new MessageDAOImpl();
        this.accountService = new AccountServiceImpl();
    }

    @Override
    public Message createMessage(Message msg) {
        if (msg.getMessage_text() == null ||
                msg.getMessage_text().isBlank() ||
                msg.getMessage_text().length() > 255) {
            return null;
        }
        // check if the posted by account exists
        if (accountService.accountExists(msg.getPosted_by()) == true) {
            return messageDAO.createMessage(msg);
        }

        return null;
    }

    @Override
    public Message getMessageById(int msgId) {
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public List<Message> getAllMessagesByAccountId(int accountId) {
        return null;
    }

    @Override
    public Boolean updateMessage(Message message) {
        return false;
    }

    @Override
    public Boolean deleteMessage(int msgId) {
        return false;
    }
    
}
