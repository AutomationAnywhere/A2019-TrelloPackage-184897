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
        name = "Create Label on Board", label = "Create Label",
        node_label = "Create label {{label}} on board {{board}} in session {{sessionName}}", description = "Creates label on board", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6",
        return_label = "Assign label ID to a String", return_type = STRING, return_description = "Outputs the ID of the label")

public class CreateLabel {
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
    public StringValue action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Board Name", description = "e.g. My Team Board") @NotEmpty String board,
            @Idx(index = "3", type = TEXT) @Pkg(label = "Label Name", description = "Name of the label to add") @NotEmpty String label,
            @Idx(index = "4", type = AttributeType.SELECT, options = {
                    @Idx.Option(index = "4.1", pkg = @Pkg(label = "Blue", value = "blue")),
                    @Idx.Option(index = "4.2", pkg = @Pkg(label = "Red", value = "red")),
                    @Idx.Option(index = "4.3", pkg = @Pkg(label = "Pink", value = "pink")),
                    @Idx.Option(index = "4.4", pkg = @Pkg(label = "Orange", value = "orange")),
                    @Idx.Option(index = "4.5", pkg = @Pkg(label = "Green", value = "green")),
                    @Idx.Option(index = "4.6", pkg = @Pkg(label = "Purple", value = "purple"))
            })
            @Pkg(label = "Label Color", default_value = "blue", default_value_type = DataType.STRING, description = "Default color is blue") @NotEmpty String color
    ) throws IOException, ParseException {

        if ("".equals(board.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "board"));
        }
        if ("".equals(label.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "card"));
        }
        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();
        label = URLEncoder.encode(label, StandardCharsets.UTF_8);
        String boardID = TrelloActions.GetBoardID(apiKey, token, board);
        //String cardID = TrelloActions.GetCardID(apiKey, token, card, boardID);
        String url = "https://api.trello.com/1/labels?key="+apiKey+"&token="+token+"&name="+label+"&color="+color+"&idBoard="+boardID;
        String response = HTTPRequest.Request(url, "POST");
        String labelID = TrelloActions.ReturnCreatedID(response);
        return new StringValue(labelID);
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
