package DAO;

import Model.Message;
import java.util.List;

public interface MessageDAO {
    /**
     * Creates a new message in the database.
     *
     * @param msg the Message object containing the details of the message to be created
     * @return the newly created Message object with its generated message_id if successful,
     *         or null if the message could not be created
     */
    Message createMessage(Message msg);

    /**
     * Retrieves a message from the database based on the specified message ID.
     *
     * @param msgId the ID of the message to be retrieved
     * @return the Message object if found, or null if no message exists with the given ID
     */
    Message getMessageById(int msgId);

    /**
     * Retrieves all messages from the database.
     *
     * @return a list of Message objects containing all the messages stored in the database,
     *         or an empty list if no messages are found
     */
    List<Message> getAllMessages();

    /**
     * Retrieves all messages posted by a specific account, identified by the given account ID.
     *
     * @param accountId the ID of the account for which messages need to be retrieved
     * @return a list of Message objects associated with the specified account ID,
     *         or an empty list if no messages are found
     */
    List<Message> getAllMessagesByAccountId(int accountId);

    /**
     * Updates the text of an existing message identified by its message ID.
     *
     * @param msgId the ID of the message to be updated
     * @param newText the new text to update the message with
     * @return the updated Message object if the update is successful,
     *         or null if no message exists with the specified ID
     */
    Message updateMessageText(int msgId, String newText);

    /**
     * Deletes the message identified by the specified message ID from the database.
     *
     * @param msgId the ID of the message to be deleted
     * @return the Message object that was deleted if the deletion is successful,
     *         or null if no message exists with the specified ID
     */
    Message deleteMessage(int msgId);
}
