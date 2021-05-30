package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleColors {
    public static final Map<String, String> colorMap = new HashMap<>(){{
        put("RESET", "\033[0m");
        put("RED", "\033[0;31m");
        put("GREEN", "\033[0;32m");
        put("YELLOW", "\033[0;33m");
        put("BLUE", "\033[0;34m");
        put("PURPLE", "\033[0;35m");
        put("WHITE", "\033[0;37m");
        put("GREY", "\033[0;30m");
    }}; //grey actually black


    //no symbols, to avoid shift because size of symbol is > 1
    public static final Map<Resource, String> resourceMap = new HashMap<>(){{
        put(Resource.SHIELD, "S");//🛡
        put(Resource.SERVANT, "U");//👨
        put(Resource.COIN, "C");//💰
        put(Resource.STONE, "P");//💎
        put(Resource.FAITH, "F");//or ✝
        put(Resource.WHITE, "-");//□
    }};
    /*

    //with symbols
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
