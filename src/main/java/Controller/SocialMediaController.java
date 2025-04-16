package Controller;

import Model.Account;
import Service.AccountService;
import Service.AccountServiceImpl;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final AccountService accountService;

    public SocialMediaController() {
        this.accountService = new AccountServiceImpl();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerUser);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}", this::getMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerUser(Context ctx) {
        try {
            // parse JSON from the request body into account object
            Account account = ctx.bodyAsClass(Account.class);

            // call service method, which houses validation
            Account registeredAccount = accountService.registerUser(account);

            // if an account is not null, return it as JSON
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
    }

    private void createMessage(Context ctx) {
    }

    private void getAllMessages(Context ctx) {
    }

    private void getMessageById(Context ctx) {
    }

    private void deleteMessage(Context ctx) {
    }

    private void updateMessage(Context ctx) {
    }

    private void getMessagesByUser(Context ctx) {
    }


}