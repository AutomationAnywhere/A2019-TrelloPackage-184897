package Utils;

import Utils.HTTPRequest;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * @author James Dickson
 *
 */

public class TrelloActions {

    public static String boardID;
    public static String teamID;
    public static String listID;
    public static String cardID;
    public static String createdID;
    public static String labelID;
    private static final Messages MESSAGES = MessagesFactory.getMessages("com.automationanywhere.botcommand.demo.messages");

    public static String GetBoardID(String apiKey, String token, String name) throws IOException, ParseException {
        String urlBoardIDs = "https://api.trello.com/1/members/me/boards?key="+apiKey+"&token="+token;
        boardID="";
        String boardsList = HTTPRequest.Request(urlBoardIDs, "GET"); //call to get list of boards
        Object obj = new JSONParser().parse(boardsList);
        JSONArray boardsInfo = (JSONArray) obj; //JSON response is an array of JSON objects
        //find Board ID
        for(int i=0; i<boardsInfo.size(); i++){
            JSONObject currentBoard = (JSONObject) boardsInfo.get(i);
            if(currentBoard.get("name").equals(name)){
                boardID = (String) currentBoard.get("id");
                break;
            }
        }
        if (boardID.equals("")){throw new BotCommandException(MESSAGES.getString("nameNotFound"));}
        return boardID;
    }

    public static String GetTeamID(String apiKey, String token, String name) throws IOException, ParseException {
        String urlTeamIDs = "https://api.trello.com/1/members/me/organizations?key="+apiKey+"&token="+token;
        teamID="";
        String teamsList = HTTPRequest.Request(urlTeamIDs, "GET"); //call to get list of boards
        Object obj = new JSONParser().parse(teamsList);
        JSONArray teamsInfo = (JSONArray) obj; //JSON response is an array of JSON objects
        //find Board ID
        for(int i=0; i<teamsInfo.size(); i++){
            JSONObject currentTeam = (JSONObject) teamsInfo.get(i);
            if(currentTeam.get("displayName").equals(name)){
                teamID = (String) currentTeam.get("id");
                break;
            }
        }
        if (teamID.equals("")){throw new BotCommandException(MESSAGES.getString("teamNotFound"));}
        return teamID;
    }

    public static String GetListID(String apiKey, String token, String name, String boardID) throws IOException, ParseException {
        String urlListIDs = "https://api.trello.com/1/boards/"+boardID+"/lists?key="+apiKey+"&token="+token;
        listID="";
        String lists = HTTPRequest.Request(urlListIDs, "GET"); //call to get list of boards
        Object obj = new JSONParser().parse(lists);
        JSONArray listsInfo = (JSONArray) obj; //JSON response is an array of JSON objects
        //find Board ID
        for(int i=0; i<listsInfo.size(); i++){
            JSONObject currentTeam = (JSONObject) listsInfo.get(i);
            if(currentTeam.get("name").equals(name)){
                listID = (String) currentTeam.get("id");
                break;
            }
        }
        if (listID.equals("")){throw new BotCommandException(MESSAGES.getString("listNotFound"));}
        return listID;
    }

    public static String GetCardID(String apiKey, String token, String name, String boardID) throws IOException, ParseException {
        String urlCardIDs = "https://api.trello.com/1/boards/"+boardID+"/cards?key="+apiKey+"&token="+token;
        cardID="";
        String lists = HTTPRequest.Request(urlCardIDs, "GET"); //call to get list of boards
        Object obj = new JSONParser().parse(lists);
        JSONArray cardsInfo = (JSONArray) obj; //JSON response is an array of JSON objects
        //find Board ID
        for(int i=0; i<cardsInfo.size(); i++){
            JSONObject currentTeam = (JSONObject) cardsInfo.get(i);
            if(currentTeam.get("name").equals(name)){
                cardID = (String) currentTeam.get("id");
                break;
            }
        }
        if (cardID.equals("")){throw new BotCommandException(MESSAGES.getString("cardNotFound"));}
        return cardID;
    }

    public static String GetLabelID(String apiKey, String token, String name, String boardID) throws IOException, ParseException {
        String urlLabelIDs = "https://api.trello.com/1/boards/"+boardID+"/labels?key="+apiKey+"&token="+token;
        labelID="";
        String labels = HTTPRequest.Request(urlLabelIDs, "GET"); //call to get list of boards
        Object obj = new JSONParser().parse(labels);
        JSONArray labelsInfo = (JSONArray) obj; //JSON response is an array of JSON objects
        //find Board ID
        for(int i=0; i<labelsInfo.size(); i++){
            JSONObject currentTeam = (JSONObject) labelsInfo.get(i);
            if(currentTeam.get("name").equals(name)){
                labelID = (String) currentTeam.get("id");
                break;
            }
        }
        if (labelID.equals("")){throw new BotCommandException(MESSAGES.getString("labelNotFound"));}
        return labelID;
    }

    public static String ReturnCreatedID(String response) throws ParseException, BotCommandException {
        try{
            Object obj = new JSONParser().parse(response);
            JSONObject jsonObj = (JSONObject) obj;
            createdID = (String) jsonObj.get("id");
            return createdID;
        }
        catch(Exception e){
            throw new BotCommandException(MESSAGES.getString("UnexpectedResponse"));
        }
    }

}
