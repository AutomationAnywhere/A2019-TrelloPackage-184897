package com.automationanywhere.commands.Trello;
import static com.automationanywhere.commandsdk.model.AttributeType.CREDENTIAL;
import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

import Utils.TrelloServer;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.automationanywhere.core.security.SecureString;
import org.json.simple.parser.*;

/**
 * @author James Dickson
 *
 */

@BotCommand

@CommandPkg(
        name = "Start Session", label = "Start Session",
        node_label = "Start Session {{sessionName}}", description = "Enter API Key and Token for Trello Account", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6"
        )

public class StartSession {

    @Sessions
    //private Map<String, Object> sessions;
    private Map<String, Object> sessions = new HashMap<String,Object>();
    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @Execute
    public void action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name",  default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2", type = CREDENTIAL) @Pkg(label = "API Key") @NotEmpty SecureString key,
            @Idx(index = "3", type = CREDENTIAL) @Pkg(label = "Account Token") @NotEmpty SecureString token

    ) throws IOException, ParseException {

        if (this.sessions.containsKey(sessionName)){
            throw new BotCommandException(MESSAGES.getString("Session name in use"));
        }
        String apiKey = key.getInsecureString();
        String accountToken = token.getInsecureString();
        TrelloServer trelloServer = new TrelloServer(apiKey, accountToken);
        this.sessions.put(sessionName, trelloServer);
    }
    public void setSessions(Map<String, Object> sessions) {this.sessions = sessions;}
}
