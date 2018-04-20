package ru.wdeath.network;

import com.esotericsoftware.kryo.Kryo;
import ru.wdeath.network.packet.PJSON;
import ru.wdeath.network.packet.PMessage;

/**
 * @author WDeath
 */
public class Network {

    public static void init(Kryo kryo){
        kryo.register(PMessage.class);
        kryo.register(PJSON.class);
    }
}
