package com.automationanywhere.commands.tests;

import Utils.ParseResponse;
import Utils.TrelloActions;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Utils.HTTPRequest;

import java.io.FileReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class MiscTests {

    public static void main(String[] args) throws IOException, ParseException, JSONException {
        String list = "Active Projects";
        String board = "AP Invoice Coding Process";
        String card = "Postman new card";
        String labelname = "projects";
        String apiKey = "b92d7c697cbecd0bf6684f6a413c37ea";
        String token = "d8b3c387fc2c0620a8ff165a423e4fccdfa3e0f7b7d148f6af90658ce0d3fc22";


        //labelname = URLEncoder.encode(labelname, StandardCharsets.UTF_8);

        String boardID = TrelloActions.GetBoardID(apiKey, token, board);
        String labelID = TrelloActions.GetLabelID(apiKey, token, labelname, boardID);
        String cardID = TrelloActions.GetCardID(apiKey, token, card, boardID);
        String url = "https://api.trello.com/1/cards/"+cardID+"/idlabels?key="+apiKey+"&token="+token+"&value="+labelID;
        String response = HTTPRequest.Request(url, "POST");
        //String labelID = TrelloActions.ReturnCreatedID(response);
        //Object obj = new JSONParser().parse(response);
        //JSONObject jsonObj = (JSONObject) obj;
        //String boardID = (String) jsonObj.get("id");
        System.out.println(response);

    }
}
