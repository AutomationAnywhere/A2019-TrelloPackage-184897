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
        name = "Create Board", label = "Create Board",
        node_label = "Create Board {{name}} in session {{sessionName}}", description = "Creates a new board in Trello", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6",
        return_label = "Assign board ID to a String", return_type = STRING, return_description = "Outputs the ID of the newly created board")

public class CreateBoard {
    @Sessions
    private Map<String, Object> sessions;

    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    @Idx(index = "2", type = GROUP)
    @Pkg(label = "Board Details")
    String nameGroup;
    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public StringValue action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING,  default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2.1", type = AttributeType.TEXT) @Pkg(label = "Board Name", description = "e.g. My New Board") @NotEmpty String name,
            @Idx(index = "2.2", type = TEXTAREA) @Pkg(label = "Board Description") String description,
            @Idx(index = "3", type = TEXT) @Pkg(label = "Team", description = "Name of the team the board should belong to") String idOrganization,
            @Idx(index = "4", type = AttributeType.SELECT, options = {
                    @Idx.Option(index = "4.1", pkg = @Pkg(label = "Blue", value = "blue")),
                    @Idx.Option(index = "4.2", pkg = @Pkg(label = "Red", value = "red")),
                    @Idx.Option(index = "4.3", pkg = @Pkg(label = "Pink", value = "pink")),
                    @Idx.Option(index = "4.4", pkg = @Pkg(label = "Orange", value = "orange")),
                    @Idx.Option(index = "4.5", pkg = @Pkg(label = "Green", value = "green")),
                    @Idx.Option(index = "4.6", pkg = @Pkg(label = "Purple", value = "purple"))
            })
            @Pkg(label = "Background Color", default_value = "blue", default_value_type = DataType.STRING, description = "default color is blue") @NotEmpty String background,
            @Idx(index = "5", type = AttributeType.SELECT, options = {
                    @Idx.Option(index = "5.1", pkg = @Pkg(label = "Organization/Team", value = "org")),
                    @Idx.Option(index = "5.2", pkg = @Pkg(label = "Private", value = "private")),
                    @Idx.Option(index = "5.3", pkg = @Pkg(label = "Public", value = "public"))
            })
            @Pkg(label = "Permission Level", default_value = "private", default_value_type = DataType.STRING, description = "Default is private") @NotEmpty String permission,
            @Idx(index = "6", type = AttributeType.SELECT, options = {
                    @Idx.Option(index = "6.1", pkg = @Pkg(label = "Disabled", value = "disabled")),
                    @Idx.Option(index = "6.2", pkg = @Pkg(label = "Members", value = "members")),
                    @Idx.Option(index = "6.3", pkg = @Pkg(label = "Observers", value = "observers")),
                    @Idx.Option(index = "6.4", pkg = @Pkg(label = "Organization", value = "org")),
                    @Idx.Option(index = "6.5", pkg = @Pkg(label = "Public", value = "public"))
            })
            @Pkg(label = "Voting", default_value = "disabled", default_value_type = DataType.STRING, description = "Specifies who has permission to vote on cards - default is disabled. Cannot exceed permission level set above") @NotEmpty String voting,
            @Idx(index = "7", type = AttributeType.SELECT, options = {
                    @Idx.Option(index = "7.1", pkg = @Pkg(label = "Disabled", value = "disabled")),
                    @Idx.Option(index = "7.2", pkg = @Pkg(label = "Members", value = "members")),
                    @Idx.Option(index = "7.3", pkg = @Pkg(label = "Observers", value = "observers")),
                    @Idx.Option(index = "7.4", pkg = @Pkg(label = "Organization", value = "org")),
                    @Idx.Option(index = "7.5", pkg = @Pkg(label = "Public", value = "public"))
            })
            @Pkg(label = "Comments", default_value = "members", default_value_type = DataType.STRING, description = "Specifies who has permission to comment - default is members") @NotEmpty String comments,
            @Idx(index = "8", type = AttributeType.SELECT, options = {
                    @Idx.Option(index = "8.1", pkg = @Pkg(label = "Admins", value = "admins")),
                    @Idx.Option(index = "8.2", pkg = @Pkg(label = "Members", value = "members"))
            })
            @Pkg(label = "Invitations", default_value = "members", default_value_type = DataType.STRING, description = "Specifies who has permission to invite other users - default is members") @NotEmpty String invitations
    ) throws IOException, ParseException {

        if ("".equals(name.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "name"));
        }
        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();
        name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        description = URLEncoder.encode(description, StandardCharsets.UTF_8);
        //Get Team ID from name
        String teamID = TrelloActions.GetTeamID(apiKey, token, idOrganization);
        //idOrganization= URLEncoder.encode(idOrganization, StandardCharsets.UTF_8);
        String url = "https://api.trello.com/1/boards/?key="+apiKey+"&token="+token+"&name="+name+"&desc="+description+"&idOrganization="+teamID+"&prefs_permissionLevel="+permission+"&prefs_voting="+voting+"&prefs_comments="+comments+"&prefs_invitations="+invitations+"&prefs_background="+background;
        String response = HTTPRequest.Request(url, "POST");
        String boardID = TrelloActions.ReturnCreatedID(response);
        return new StringValue(boardID);
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
