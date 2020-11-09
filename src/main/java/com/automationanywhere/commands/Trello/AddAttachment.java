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
        name = "Add Attachment to Card", label = "Add Attachment to Card",
        node_label = "Add Attachment to Card {{card}} in session {{sessionName}}", description = "Attaches a file to a card", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6",
        return_label = "Assign attachment ID to a String", return_type = STRING, return_description = "Outputs the ID of the attached file")

public class AddAttachment {
    @Sessions
    private Map<String, Object> sessions;

    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    @Idx(index = "4", type = GROUP)
    @Pkg(label = "Attachment Details")
    String nameGroup;
    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public StringValue action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Board Name", description = "e.g. My Team Board") @NotEmpty String board,
            @Idx(index = "3", type = TEXT) @Pkg(label = "Card Name", description = "Name of the card to add attachment") @NotEmpty String card,
            @Idx(index = "4.1", type = AttributeType.FILE) @Pkg(label = "File to attach") @NotEmpty String file,
            @Idx(index = "4.2", type = AttributeType.TEXT) @Pkg(label = "Name of Attachment") String name
    ) throws IOException, ParseException {

        if ("".equals(board.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "name"));
        }
        if ("".equals(card.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "name"));
        }
        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String boardID = TrelloActions.GetBoardID(apiKey, token, board);
        String cardID = TrelloActions.GetCardID(apiKey, token, card, boardID);
        String url = "https://api.trello.com/1/cards/" + cardID + "/attachments?key=" + apiKey + "&token=" + token + "&name=" + name;
        String response = HTTPRequest.POSTwFile(url, file);
        String attachmentID = TrelloActions.ReturnCreatedID(response);
        return new StringValue(attachmentID);
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
