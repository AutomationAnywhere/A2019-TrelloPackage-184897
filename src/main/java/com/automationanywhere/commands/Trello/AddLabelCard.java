package com.automationanywhere.commands.Trello;

import Utils.HTTPRequest;
import Utils.TrelloActions;
import Utils.TrelloServer;
import static com.automationanywhere.commandsdk.model.AttributeType.*;
import static com.automationanywhere.commandsdk.model.DataType.STRING;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.AttributeType;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.automationanywhere.commandsdk.model.DataType;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * @author James Dickson
 *
 */
@BotCommand

//CommandPks adds required information to be displayable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "Add label to card", label = "Add Label",
        node_label = "Add label {{label}} to card {{card}} in session {{sessionName}}", description = "Adds label to card", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6")

public class AddLabelCard {
    @Sessions
    private Map<String, Object> sessions;

    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public void action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Board Name", description = "e.g. My Team Board") @NotEmpty String board,
            @Idx(index = "3", type = AttributeType.TEXT) @Pkg(label = "Card Name", description = "Name of card to which the label will be added") @NotEmpty String card,
            @Idx(index = "4", type = TEXT) @Pkg(label = "Label Name", description = "Name of the label to add") @NotEmpty String label
    ) throws IOException, ParseException {

        if ("".equals(board.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "board"));
        }
        if ("".equals(label.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "label"));
        }
        if ("".equals(card.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "card"));
        }

        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();
        String boardID = TrelloActions.GetBoardID(apiKey, token, board);
        String labelID = TrelloActions.GetLabelID(apiKey, token, label, boardID);
        String cardID = TrelloActions.GetCardID(apiKey, token, card, boardID);
        String url = "https://api.trello.com/1/cards/"+cardID+"/idlabels?key="+apiKey+"&token="+token+"&value="+labelID;
        String response = HTTPRequest.Request(url, "POST");

    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
