package Service;

import java.util.List;

import DAO.MessageDAO;
import DAO.MessageDAOImpl;
import Model.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageServiceImpl implements MessageService{
    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final MessageDAO messageDAO;
    private final AccountService accountService;

    public MessageServiceImpl() {
        this.messageDAO = new MessageDAOImpl();
        this.accountService = new AccountServiceImpl();
    }

    @Override
    public Message createMessage(Message msg) {
        if (msg.getMessage_text() == null ||
                msg.getMessage_text().isBlank() ){
            logger.warn("Message text is blank");
            return null;
        }
        if (msg.getMessage_text().length() > 255) {
            logger.warn("Message text too long");
            return null;
        }
        // check if the posted by account exists
        if (!accountService.accountExists(msg.getPosted_by())) {
            logger.warn("Account does not exist");
            return messageDAO.createMessage(msg);
        }

        logger.info("User validated, creating message for user ID: {}", msg.getPosted_by());
        return messageDAO.createMessage(msg);
    }

    @Override
    public Message getMessageById(int msgId) {
        Message message = messageDAO.getMessageById(msgId);

        if (message != null) {
            logger.info("Message found for ID: {}", msgId);
        } else {
            logger.warn("No message found for ID: {}", msgId);
        }

        return message;
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        logger.info("Retrieved {} messages", messages.size());
        return messages;
    }

    @Override
    public List<Message> getAllMessagesByAccountId(int accountId) {
        List<Message> messages = messageDAO.getAllMessagesByAccountId(accountId);
        logger.info("Retrieved {} messages for account ID: {}", messages.size(), accountId);
        return messages;
    }

    @Override
    public Message updateMessageText(int msgId, String newText) {
        Message currentMessage = messageDAO.getMessageById(msgId);
        if (currentMessage == null) {
            logger.warn("Message update failed: No message found for ID: {}", msgId);
            return null;
        }

        if (newText == null ||
                newText.isBlank()){
            logger.warn("Message update failed: New message text is blank");
            return null;
        }

        if (newText.length() > 255) {
            logger.warn("Message update failed: New message text too long (exceeding 255 characters)");
            return null;
        }

        logger.info("Updating message with ID: {}", msgId);
        Message updatedMessage = messageDAO.updateMessageText(msgId, newText);

        if (updatedMessage != null) {
            logger.info("Message with ID: {} updated successfully", msgId);
        } else {
            logger.error("Message update failed");
        }


        return updatedMessage;
    }

    @Override
    public Message deleteMessage(int msgId) {
        Message deletedMessage = messageDAO.deleteMessage(msgId);

        if (deletedMessage != null) {
            logger.info("Message with ID: {} deleted successfully", msgId);
        } else {
            logger.error("Message with ID: {} not found", msgId);
        }

        return deletedMessage;
    }

}