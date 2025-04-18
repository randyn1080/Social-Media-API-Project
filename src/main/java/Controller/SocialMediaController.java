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

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountServiceImpl();
        this.messageService = new MessageServiceImpl();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerUser);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        return app;
    }

    private void registerUser(Context ctx) {
        try {
            Account account = ctx.bodyAsClass(Account.class);

            Account registeredAccount = accountService.registerUser(account);

            if (registeredAccount != null) {
                ctx.json(registeredAccount);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400).result("Error registering user: " + e.getMessage());
        }
    }

    private void login(Context ctx) {
        try {
            Account account = ctx.bodyAsClass(Account.class);
            Account loggedInAccount = accountService.login(account);
            if (loggedInAccount != null) {
                ctx.json(loggedInAccount);
            } else {
                ctx.status(401);
            }
        } catch (Exception e) {
            ctx.status(401).result("Error logging in: " + e.getMessage());
        }
    }

    private void createMessage(Context ctx) {
        try {
            Message message = ctx.bodyAsClass(Message.class);
            Message validMessage = messageService.createMessage(message);
            if (validMessage != null) {
                ctx.json(validMessage);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400).result("Error creating message: " + e.getMessage());
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.json("");
        }
        
    }

    private void deleteMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        ctx.json(Objects.requireNonNullElse(deletedMessage, ""));
    }

    private void updateMessage(Context ctx) {
        int msgId = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageUpdate = ctx.bodyAsClass(Message.class);

        Message updatedMessage = messageService.updateMessageText(msgId, messageUpdate.getMessage_text());

        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesByUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);

        ctx.json(messages);
    }


}