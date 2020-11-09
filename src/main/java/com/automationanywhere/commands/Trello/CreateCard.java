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
        name = "Create Card", label = "Create Card",
        node_label = "Create Card {{name}} on list {{list}} in session {{sessionName}}", description = "Creates a new card on a list", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6",
        return_label = "Assign card ID to a String", return_type = STRING, return_description = "Outputs the ID of the newly created list")

public class CreateCard {
    @Sessions
    private Map<String, Object> sessions;

    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    @Idx(index = "4", type = GROUP)
    @Pkg(label = "Card Details")
    String nameGroup;
    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public StringValue action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Board Name", description = "e.g. My Team Board") @NotEmpty String board,
            @Idx(index = "3", type = TEXT) @Pkg(label = "List Name", description = "Name of the list the card should be added to") @NotEmpty String list,
            @Idx(index = "4.1", type = AttributeType.TEXT) @Pkg(label = "Card Name", description = "e.g. My First Card") @NotEmpty String name,
            @Idx(index = "4.2", type = TEXTAREA) @Pkg(label = "Card Description", description = "This text shows up in the main description section at top of card") String description,
            @Idx(index = "5", type = RADIO, options = {
                    @Idx.Option(index ="5.1", pkg = @Pkg(label = "Top", value = "top")),
                    @Idx.Option(index ="5.2", pkg = @Pkg(label = "Bottom", value = "bottom"))
            }) @Pkg(label = "Position", default_value_type = STRING, default_value = "top", description = "Default is top") String pos,
            @Idx(index = "6", type = AttributeType.TEXT) @Pkg(label = "Due Date", description = "e.g. mm/dd/yyyy") String due
    ) throws IOException, ParseException {

        if ("".equals(board.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "name"));
        }
        if ("".equals(list.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "name"));
        }
        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();
        String boardID = TrelloActions.GetBoardID(apiKey, token, board);
        String listID = TrelloActions.GetListID(apiKey, token, list, boardID);
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        description = URLEncoder.encode(description, StandardCharsets.UTF_8);
        String url = "https://api.trello.com/1/cards?key=" + apiKey + "&token=" + token + "&name=" + name + "&desc=" + description + "&pos="+pos+"&due="+due+"&idList="+listID;
        String response = HTTPRequest.Request(url, "POST");
        String cardID = TrelloActions.ReturnCreatedID(response);
        return new StringValue(cardID);
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
