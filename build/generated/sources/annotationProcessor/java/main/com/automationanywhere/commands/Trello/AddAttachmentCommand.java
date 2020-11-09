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

public final class AddAttachmentCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(AddAttachmentCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    AddAttachment command = new AddAttachment();
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

    if(parameters.containsKey("board") && parameters.get("board") != null && parameters.get("board").get() != null) {
      convertedParameters.put("board", parameters.get("board").get());
      if(!(convertedParameters.get("board") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","board", "String", parameters.get("board").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("board") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","board"));
    }

    if(parameters.containsKey("card") && parameters.get("card") != null && parameters.get("card").get() != null) {
      convertedParameters.put("card", parameters.get("card").get());
      if(!(convertedParameters.get("card") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","card", "String", parameters.get("card").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("card") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","card"));
    }

    if(parameters.containsKey("nameGroup") && parameters.get("nameGroup") != null && parameters.get("nameGroup").get() != null) {
      convertedParameters.put("nameGroup", parameters.get("nameGroup").get());
      if(!(convertedParameters.get("nameGroup") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","nameGroup", "String", parameters.get("nameGroup").get().getClass().getSimpleName()));
      }
    }
    if(parameters.containsKey("file") && parameters.get("file") != null && parameters.get("file").get() != null) {
      convertedParameters.put("file", parameters.get("file").get());
      if(!(convertedParameters.get("file") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","file", "String", parameters.get("file").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("file") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","file"));
    }

    if(parameters.containsKey("name") && parameters.get("name") != null && parameters.get("name").get() != null) {
      convertedParameters.put("name", parameters.get("name").get());
      if(!(convertedParameters.get("name") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","name", "String", parameters.get("name").get().getClass().getSimpleName()));
      }
    }


    command.setSessions(sessionMap);
    command.setGlobalSessionContext(globalSessionContext);
    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("board"),(String)convertedParameters.get("card"),(String)convertedParameters.get("file"),(String)convertedParameters.get("name")));
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
