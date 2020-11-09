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
import org.json.simple.JSONArray;
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
        name = "Manage Members on Board or Team", label = "Manage Members",
        node_label = "{{manage}} members to {{type}} {{name}} in session {{sessionName}}", description = "Manage members on board or team in Trello", icon = "trello.svg",
        comment = true ,  text_color = "#7B848B" , background_color =  "#a6a6a6")


public class AddMembers {
    @Sessions
    private Map<String, Object> sessions;

    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    @com.automationanywhere.commandsdk.annotations.GlobalSessionContext
    private com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext;

    public void setGlobalSessionContext(com.automationanywhere.bot.service.GlobalSessionContext globalSessionContext) {
        this.globalSessionContext = globalSessionContext;
    }
    @Execute
    public void action(
            @Idx(index = "1", type = TEXT) @Pkg(label = "Session name", default_value_type = STRING,  default_value = "Default") @NotEmpty String sessionName,
            @Idx(index = "2", type = RADIO, options = {
                    @Idx.Option(index ="2.1", pkg = @Pkg(label = "Add", value = "PUT")),
                    @Idx.Option(index ="2.2", pkg = @Pkg(label = "Remove", value = "DELETE"))
            }) @Pkg(label = "Action") @NotEmpty String manage,
            @Idx(index = "3", type = RADIO, options = {
                    @Idx.Option(index ="3.1", pkg = @Pkg(label = "Team", value = "organizations")),
                    @Idx.Option(index ="3.2", pkg = @Pkg(label = "Board", value = "boards"))
            }) @Pkg(label = "Members to or from") @NotEmpty String type,
            @Idx(index = "4", type = AttributeType.TEXT) @Pkg(label = "Board or Team Name", description = "e.g. My Team Board") @NotEmpty String name,
            @Idx(index = "5", type = AttributeType.TEXTAREA) @Pkg(label = "Member User Names", description = "e.g. bobsmith11 - multiple user names must be separated by commas") @NotEmpty String members
    ) throws IOException, ParseException {

        if ("".equals(name.trim())) {
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "id"));
        }
        String ID = "";
        TrelloServer trelloServer = (TrelloServer) this.sessions.get(sessionName);
        String apiKey = trelloServer.getSID();
        String token = trelloServer.getToken();

        if(type.equals("boards")){
            ID = TrelloActions.GetBoardID(apiKey, token, name);
        } else if (type.equals("organizations")){
            ID = TrelloActions.GetTeamID(apiKey, token, name);
        }
        //Find Member IDs and add to board
        String[] elements = members.split(",");
        List<String> fixedLengthList = Arrays.asList(elements);
        ArrayList<String> listOfMembers = new ArrayList<String>(fixedLengthList);
        for (int j=0; j<listOfMembers.size(); j++){
            String currentMember = listOfMembers.get(j).trim();
            String memberBoardAdd = HTTPRequest.Request("https://api.trello.com/1/"+type+"/"+ID+"/members/"+currentMember+"?key="+apiKey+"&token="+token+"&type=normal", manage);
        }
    }
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
}
