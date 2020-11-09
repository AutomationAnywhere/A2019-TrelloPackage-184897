package com.automationanywhere.commands.Trello;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CreateBoardCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(CreateBoardCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    CreateBoard command = new CreateBoard();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("sessionName") && parameters.get("sessionName") != null && parameters.get("sessionName").get() != null) {
      convertedParameters.put("sessionName", parameters.get("sessionName").get());
      if(!(convertedParameters.get("sessionName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sessionName", "String", parameters.get("sessionName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("sessionName") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","sessionName"));
    }

    if(parameters.containsKey("nameGroup") && parameters.get("nameGroup") != null && parameters.get("nameGroup").get() != null) {
      convertedParameters.put("nameGroup", parameters.get("nameGroup").get());
      if(!(convertedParameters.get("nameGroup") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","nameGroup", "String", parameters.get("nameGroup").get().getClass().getSimpleName()));
      }
    }
    if(parameters.containsKey("name") && parameters.get("name") != null && parameters.get("name").get() != null) {
      convertedParameters.put("name", parameters.get("name").get());
      if(!(convertedParameters.get("name") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","name", "String", parameters.get("name").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("name") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","name"));
    }

    if(parameters.containsKey("description") && parameters.get("description") != null && parameters.get("description").get() != null) {
      convertedParameters.put("description", parameters.get("description").get());
      if(!(convertedParameters.get("description") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","description", "String", parameters.get("description").get().getClass().getSimpleName()));
      }
    }


    if(parameters.containsKey("idOrganization") && parameters.get("idOrganization") != null && parameters.get("idOrganization").get() != null) {
      convertedParameters.put("idOrganization", parameters.get("idOrganization").get());
      if(!(convertedParameters.get("idOrganization") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","idOrganization", "String", parameters.get("idOrganization").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("background") && parameters.get("background") != null && parameters.get("background").get() != null) {
      convertedParameters.put("background", parameters.get("background").get());
      if(!(convertedParameters.get("background") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","background", "String", parameters.get("background").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("background") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","background"));
    }
    if(convertedParameters.get("background") != null) {
      switch((String)convertedParameters.get("background")) {
        case "blue" : {

        } break;
        case "red" : {

        } break;
        case "pink" : {

        } break;
        case "orange" : {

        } break;
        case "green" : {

        } break;
        case "purple" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","background"));
      }
    }

    if(parameters.containsKey("permission") && parameters.get("permission") != null && parameters.get("permission").get() != null) {
      convertedParameters.put("permission", parameters.get("permission").get());
      if(!(convertedParameters.get("permission") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","permission", "String", parameters.get("permission").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("permission") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","permission"));
    }
    if(convertedParameters.get("permission") != null) {
      switch((String)convertedParameters.get("permission")) {
        case "org" : {

        } break;
        case "private" : {

        } break;
        case "public" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","permission"));
      }
    }

    if(parameters.containsKey("voting") && parameters.get("voting") != null && parameters.get("voting").get() != null) {
      convertedParameters.put("voting", parameters.get("voting").get());
      if(!(convertedParameters.get("voting") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","voting", "String", parameters.get("voting").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("voting") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","voting"));
    }
    if(convertedParameters.get("voting") != null) {
      switch((String)convertedParameters.get("voting")) {
        case "disabled" : {

        } break;
        case "members" : {

        } break;
        case "observers" : {

        } break;
        case "org" : {

        } break;
        case "public" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","voting"));
      }
    }

    if(parameters.containsKey("comments") && parameters.get("comments") != null && parameters.get("comments").get() != null) {
      convertedParameters.put("comments", parameters.get("comments").get());
      if(!(convertedParameters.get("comments") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","comments", "String", parameters.get("comments").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("comments") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","comments"));
    }
    if(convertedParameters.get("comments") != null) {
      switch((String)convertedParameters.get("comments")) {
        case "disabled" : {

        } break;
        case "members" : {

        } break;
        case "observers" : {

        } break;
        case "org" : {

        } break;
        case "public" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","comments"));
      }
    }

    if(parameters.containsKey("invitations") && parameters.get("invitations") != null && parameters.get("invitations").get() != null) {
      convertedParameters.put("invitations", parameters.get("invitations").get());
      if(!(convertedParameters.get("invitations") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","invitations", "String", parameters.get("invitations").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("invitations") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","invitations"));
    }
    if(convertedParameters.get("invitations") != null) {
      switch((String)convertedParameters.get("invitations")) {
        case "admins" : {

        } break;
        case "members" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","invitations"));
      }
    }

    command.setSessions(sessionMap);
    command.setGlobalSessionContext(globalSessionContext);
    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("name"),(String)convertedParameters.get("description"),(String)convertedParameters.get("idOrganization"),(String)convertedParameters.get("background"),(String)convertedParameters.get("permission"),(String)convertedParameters.get("voting"),(String)convertedParameters.get("comments"),(String)convertedParameters.get("invitations")));
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
