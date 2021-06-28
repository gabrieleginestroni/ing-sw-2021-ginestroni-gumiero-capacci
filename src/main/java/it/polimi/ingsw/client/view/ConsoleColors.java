package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that contains color codes for fancy CLI and symbols used to represent resources
 */
public class ConsoleColors {

    /**
     * Map of colors and their code for console
     */
    public static final Map<String, String> colorMap = new HashMap<>(){{
        put("RESET", "\033[0m");
        put("RED", "\033[0;31m");
        put("GREEN", "\033[0;32m");
        put("YELLOW", "\033[0;33m");
        put("BLUE", "\033[0;34m");
        put("PURPLE", "\033[0;35m");
        put("WHITE", "\033[0;97m");
        put("GREY", "\033[0;90m");
        put("BLACK", "\033[0;30m");
    }};


    /**
     * Map of resources and their symbol for representation
     */
    public static final Map<Resource, String> resourceMap = new HashMap<>(){{
        put(Resource.SHIELD, "\u15E0");//, or \u07DC or \u080F
        put(Resource.SERVANT, "\u1333");//👨 2A30, or 0536
        put(Resource.COIN, "$");//💰
        put(Resource.STONE, "\u058D");//💎17D9
        put(Resource.FAITH, "\u256C");//✝, or \u2628
        put(Resource.WHITE, "-");//□
    }};

    /*
    //safe version
    public static final Map<Resource, String> resourceMap = new HashMap<>(){{
        put(Resource.SHIELD, "S");//🛡
        put(Resource.SERVANT, "U");//👨
        put(Resource.COIN, "C");//💰
        put(Resource.STONE, "R");//💎
        put(Resource.FAITH, "F");//or ✝
        put(Resource.WHITE, "-");//□
    }};

    //perfect symbols, but too large in console
    public static final Map<Resource, String> resourceMap = new HashMap<>(){{
        put(Resource.SHIELD, "\uD83D\uDEE1");//🛡
        put(Resource.SERVANT, "\uD83D\uDC68");//👨
        put(Resource.COIN, "\uD83D\uDCB0");//💰
        put(Resource.STONE, "\uD83D\uDC8E");//💎
        put(Resource.FAITH, "✞");//or ✝
        put(Resource.WHITE, "□");//□
    }};
    */
}
