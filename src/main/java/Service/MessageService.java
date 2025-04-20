package Service;

import Model.Message;
import java.util.List;

public interface MessageService {
    /**
     * Creates a new message based on the provided input message.
     * This method processes the given message and returns the newly created message object.
     * The input message should contain valid information such as the user who posted it,
     * the message text, and the timestamp.
     *
     * @param msg the Message object containing the details of the message to be created
     * @return the newly created Message object
     */
    Message createMessage(Message msg);

    /**
     * Retrieves a specific message based on the provided message ID.
     *
     * @param msgId the unique identifier of the message to be retrieved
     * @return the Message object corresponding to the specified ID, or null if no such message exists
     */
    Message getMessageById(int msgId);

    /**
     * Retrieves a list of all messages available in the system.
     *
     * @return a list of Message objects representing all messages in the system
     */
    List<Message> getAllMessages();

    /**
     * Retrieves all messages associated with a specified account ID.
     *
     * @param accountId the unique identifier of the account whose messages are to be retrieved
     * @return a list of Message objects associated with the specified account ID
     */
    List<Message> getAllMessagesByAccountId(int accountId);

    /**
     * Updates the text of an existing message identified by the provided message ID.
     * The method locates the message with the specified ID and replaces its content
     * with the new text. If the update is successful, the updated Message object is returned.
     * If the message ID does not exist, null may be returned.
     *
     * @param msgId the unique identifier of the message to update
     * @param newText the new text content to replace the existing message text
     * @return the updated Message object if the update is successful,
     *         or null if the message with the specified ID does not exist
     */
    Message updateMessageText(int msgId, String newText);

    /**
     * Deletes the message associated with the specified message ID.
     * If the message exists, it is removed from the system, and the deleted message object is returned.
     * If the message cannot be found, null may be returned.
     *
     * @param msgId the unique identifier of the message to delete
     * @return the deleted Message object if the operation is successful, or null if the message does not exist
     */
    Message deleteMessage(int msgId);
}
