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
        name = "Create Team", label = "Create Team",
        node_label = "Create team {{name}} in session {{sessionName}}", description = "Creates a new team in Trello", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6",
        return_label = "Assign team ID to a String", return_type = STRING, return_description = "Outputs the ID of the newly created team")

public class CreateTeam {
    @Sessions
    private Map<String, Object> sessions;

    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    @Idx(index = "2", type = GROUP)
    @Pkg(label = "Team Details")
    String nameGroup;
    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public StringValue action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING,  default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2.1", type = AttributeType.TEXT) @Pkg(label = "Team Name", description = "e.g. My New Team") @NotEmpty String name,
            @Idx(index = "2.2", type = TEXTAREA) @Pkg(label = "Team Description") String description
    ) throws IOException, ParseException {

        if ("".equals(name.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "name"));
        }
        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        description = URLEncoder.encode(description, StandardCharsets.UTF_8);
        String url = "https://api.trello.com/1/organizations?key="+apiKey+"&token="+token+"&displayName="+name+"&desc="+description;
        String response = HTTPRequest.Request(url, "POST");
        String teamID = TrelloActions.ReturnCreatedID(response);
        return new StringValue(teamID);
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
