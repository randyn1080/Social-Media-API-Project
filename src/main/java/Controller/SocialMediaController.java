package Controller;

import java.util.List;
import java.util.Objects;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.AccountServiceImpl;
import Service.MessageService;
import Service.MessageServiceImpl;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class to handle social media functionalities, including
 * user account management (register, login) and message management
 * (creation, retrieval, update, and deletion).
 *
 * This class uses Javalin to define and configure the API endpoints and
 * interacts with the underlying services for business logic execution.
 */
public class SocialMediaController {
    private static final Logger logger = LoggerFactory.getLogger(SocialMediaController.class);
    /**
     * The AccountService and MessageService instances will be injected into the controller.
     */
    private final AccountService accountService;
    private final MessageService messageService;

    /**
     * The constructor for the SocialMediaController.
     */
    public SocialMediaController() {
        this.accountService = new AccountServiceImpl();
        this.messageService = new MessageServiceImpl();
        logger.info("Social Media Controller started");
    }

    /**
     * Configures and starts the Javalin API with all defined endpoints.
     *
     * The API provides endpoints for user registration, login, message management,
     * and retrieving messages by specific criteria. These endpoints include:
     * - POST /register: Handles user registration.
     * - POST /login: Handles user login.
     * - POST /messages: Creates a new message.
     * - GET /messages: Retrieves all messages.
     * - GET /messages/{message_id}: Retrieves a specific message by its ID.
     * - DELETE /messages/{message_id}: Deletes a specific message by its ID.
     * - PATCH /messages/{message_id}: Updates the text of a specific message.
     * - GET /accounts/{account_id}/messages: Retrieves all messages associated with a user.
     *
     * @return A Javalin instance configured with the defined endpoints.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        logger.info("Configuring API endpoints");

        app.post("/register", this::registerUser);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        logger.info("API endpoints configured successfully");
        return app;
    }

    /**
     * Handles user registration requests.
     *
     * Endpoint: POST /register
     * Request Body: JSON representation of an Account (without account_id)
     *
     * @param ctx The Javalin context for this request
     *
     * Response Codes:
     * - 200 OK: Registration successful, returns the created Account with account_id
     * - 400 Bad Request: Registration failed (username blank, password too short, or username already exists)
     */
    private void registerUser(Context ctx) {
        logger.info("Received request: POST /register");
        try {
            Account account = ctx.bodyAsClass(Account.class);

            Account registeredAccount = accountService.registerUser(account);

            if (registeredAccount != null) {
                logger.info("Registration successful for user ID: {}", registeredAccount.getAccount_id());
                ctx.json(registeredAccount);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage());
            ctx.status(400).result("Error registering user: " + e.getMessage());
        }
    }

    /**
     * Handles user login requests.
     *
     * Endpoint: POST /login
     * Request Body: JSON representation of an Account (without account_id)
     *
     * @param ctx The Javalin context for this request
     *
     * Response Codes:
     * - 200 OK: Login successful, returns the Account with account_id
     * - 401 Unauthorized: Login failed (username or password incorrect)
     */
    private void login(Context ctx) {
        logger.info("Received request: POST /login");
        try {
            Account account = ctx.bodyAsClass(Account.class);
            Account loggedInAccount = accountService.login(account);
            if (loggedInAccount != null) {
                logger.info("Login successful for user ID: {}", loggedInAccount.getAccount_id());
                ctx.json(loggedInAccount);
            } else {
                logger.warn("Login failed");
                ctx.status(401);
            }
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage());
            ctx.status(401).result("Error logging in: " + e.getMessage());
        }
    }

    /**
     * Handles message creation requests.
     *
     * Endpoint: POST /messages
     * Request Body: JSON representation of a Message (without message_id)
     *
     * @param ctx The Javalin context for this request
     *
     * Response Codes:
     * - 200 OK: Message created successfully, returns the created Message with message_id
     * - 400 Bad Request: Message creation failed (message_text blank or too long, or posted_by user doesn't exist)
     */
    private void createMessage(Context ctx) {
        logger.info("Received request: POST /messages");
        try {
            Message message = ctx.bodyAsClass(Message.class);
            Message validMessage = messageService.createMessage(message);
            if (validMessage != null) {
                logger.info("Message created successfully for user ID: {}", validMessage.getPosted_by());
                ctx.json(validMessage);
            } else {
                logger.warn("Message creation failed");
                ctx.status(400);
            }
        } catch (Exception e) {
            logger.error("Error during message creation: {}", e.getMessage());
            ctx.status(400).result("Error creating message: " + e.getMessage());
        }
    }

    /**
     * Retrieves all messages in the system.
     *
     * Endpoint: GET /messages
     *
     * @param ctx The Javalin context for this request
     *
     * Response:
     * - 200 OK: Returns a JSON array of all messages (empty array if no messages exist)
     */
    private void getAllMessages(Context ctx) {
        logger.info("Received request: GET /messages");
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);

    }

    /**
     * Retrieves a specific message by its ID.
     *
     * Endpoint: GET /messages/{message_id}
     * Path Parameter: message_id - The ID of the message to retrieve
     *
     * @param ctx The Javalin context for this request
     *
     * Response:
     * - 200 OK: Returns the requested message as JSON, or an empty response if the message doesn't exist
     */
    private void getMessageById(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            logger.info("Received request: GET /messages/{}", messageId);
            Message message = messageService.getMessageById(messageId);

            ctx.json(Objects.requireNonNullElse(message, ""));
        } catch (Exception e) {
            logger.error("Error retrieving message: {}", e.getMessage());
            ctx.json("");
        }

    }

    /**
     * Deletes a message by its ID.
     *
     * Endpoint: DELETE /messages/{message_id}
     * Path Parameter: message_id - The ID of the message to delete
     *
     * @param ctx The Javalin context for this request
     *
     * Response:
     * - 200 OK: Returns the deleted message as JSON if it existed, or an empty response if it didn't exist
     */
    private void deleteMessage(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            logger.info("Received request: DELETE /messages/{}", messageId);
            Message deletedMessage = messageService.deleteMessage(messageId);
            ctx.json(Objects.requireNonNullElse(deletedMessage, ""));
        } catch (Exception e) {
            logger.error("Error deleting message: {}", e.getMessage());
            ctx.json("");
        }

    }

    /**
     * Updates the text of an existing message.
     *
     * Endpoint: PATCH /messages/{message_id}
     * Path Parameter: message_id - The ID of the message to update
     * Request Body: JSON containing a message_text field
     *
     * @param ctx The Javalin context for this request
     *
     * Response Codes:
     * - 200 OK: Message updated successfully, returns the complete updated Message
     * - 400 Bad Request: Update failed (message doesn't exist, new text is blank or too long)
     */
    private void updateMessage(Context ctx) {
        try {
            int msgId = Integer.parseInt(ctx.pathParam("message_id"));
            logger.info("Received request: PATCH /messages/{}", msgId);

            Message messageUpdate = ctx.bodyAsClass(Message.class);

            Message updatedMessage = messageService.updateMessageText(msgId, messageUpdate.getMessage_text());

            if (updatedMessage != null) {
                logger.info("Message updated successfully for user ID: {}", updatedMessage.getPosted_by());
                ctx.json(updatedMessage);
            } else {
                logger.warn("Message update failed for ID: {}", msgId);
                ctx.status(400);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid message ID format: {}", ctx.pathParam("message_id"));
            ctx.status(400).result("Invalid message ID format: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating message: {}", e.getMessage());
            ctx.status(400).result("Error updating message: " + e.getMessage());
        }

    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * Endpoint: GET /accounts/{account_id}/messages
     * Path Parameter: account_id - The ID of the account whose messages to retrieve
     *
     * @param ctx The Javalin context for this request
     *
     * Response:
     * - 200 OK: Returns a JSON array of all messages posted by the specified user
     *           (empty array if the user has no messages or doesn't exist)
     */
    private void getMessagesByUser(Context ctx) {
        try {
            int accountId = Integer.parseInt(ctx.pathParam("account_id"));
            logger.info("Received request: GET /accounts/{}/messages", accountId);

            List<Message> messages = messageService.getAllMessagesByAccountId(accountId);

            ctx.json(messages);
        } catch (Exception e) {
            logger.error("Error retrieving messages by user: {}", e.getMessage());
            ctx.json(List.<Message>of());
        }

    }

}