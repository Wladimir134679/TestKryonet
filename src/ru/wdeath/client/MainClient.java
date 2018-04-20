package ru.wdeath.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.util.InputStreamSender;
import java.io.IOException;
import org.json.JSONObject;
import ru.wdeath.network.Network;
import ru.wdeath.network.packet.PJSON;
import ru.wdeath.network.packet.PMessage;

/**
 * @author WDeath
 */
public class MainClient {

    public static Client client;
    
    public static void main(String[] args) throws IOException{
        System.out.println("===== Start Client =====");
        client = new Client(1024 * 1024, 1024 * 1024);
        client.start();
        client.connect(2000, "localhost", 2106, 2101);
        Network.init(client.getKryo());
        
        client.addListener(new Listener(){
            @Override
            public void connected(Connection connection){
                System.out.println("Connection: " + connection.getID());
            }

            @Override
            public void received(Connection connection, Object object){
                System.out.println("Received: " + object);
            }

            @Override
            public void idle(Connection connection){
                System.out.println("Idle");
            }

            @Override
            public void disconnected(Connection connection){
                System.out.println("Disconnected");
            }
            
        });
        
        PMessage mess = new PMessage();
        mess.text = "Привет";
        client.sendUDP(mess);
        
        JSONObject obj = new JSONObject();
        obj.put("action", "ACtIon 1");
        obj.put("min", 0);
        obj.put("max", 2000);
        
        int min = obj.getInt("min");
        int max = obj.getInt("max");
        for(int i = min; i < max; i++){
            obj.put(String.valueOf("Key " + i), i);
        }
        
        PJSON json = new PJSON();
        json.setJson(obj);
        client.sendTCP(json);
    }
}
