package com.my;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Sergey on 09.05.2015.
 */
@Controller
@RequestMapping
public class MController {

    private static final Logger logger = Logger.getLogger(MController.class);

    private static final List<Message> messages = new ArrayList<Message>();
    private static final Map<String, String> keys = new HashMap<String, String>();

    public static final int G = 5;
    public static final int P = 23;

    @RequestMapping(value = "message", produces = {"application/json"}, method = RequestMethod.POST)
    public
    @ResponseBody
    List<Message> postMessage(@RequestBody Message message) throws JsonProcessingException {
        logger.debug("Start handling request with login: " + message);
        logger.debug("Start handling request with message: " + message);

        Message messageObject = message;

        messages.add(messageObject);

        return messages;
    }

    @RequestMapping(value = "message", produces = {"application/json"}, method = RequestMethod.GET)
    public
    @ResponseBody
    List<Message> getMessages() throws JsonProcessingException {
        logger.debug("Returning all messages");
        return messages;
    }

    @RequestMapping(value = "gp", produces = {"application/json"}, method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Integer> getAAndB() throws JsonProcessingException {
        logger.debug("Returning g and p");
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("g", G);
        map.put("p", P);
        return map;
    }

    @RequestMapping(value = "key", produces = {"application/json"}, method = RequestMethod.GET)
    public
    @ResponseBody
    String[] getKeys(@RequestParam(value = "login") String login){
        for (String s : keys.keySet()) {
            if(!s.equals(login)){
                logger.debug("returning key with login: " + login + " and value " + keys.get(s));
                return new String[]{keys.get(s)};
            }
        }
        return null;
    }

    @RequestMapping(value = "key", produces = {"application/json"}, method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> putKey(@RequestParam(value = "login") String login,
                               @RequestParam(value="key") String key){
        logger.debug("Putting key with login: " + login);
        logger.debug("Putting key with value: " + key);

        keys.put(login, key);

        return keys;
    }

}