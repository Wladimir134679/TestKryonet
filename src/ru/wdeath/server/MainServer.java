package ru.wdeath.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import org.json.JSONObject;
import ru.wdeath.network.Network;
import ru.wdeath.network.packet.PJSON;

/**
 * @author WDeath
 */
public class MainServer {

    public static Server server;
    
    public static void main(String[] args) throws IOException{
        System.out.println("===== Start Server =====");
        server = new Server(1024 * 1024, 1024 * 1024); // Указываем буфер, для производительности, чем меньше, тем больше
        server.start(); // Начинаем подключения
        server.bind(2106, 2101); // Подключаемся к портам
        Network.init(server.getKryo());
        
        server.addListener(new Listener(){
            @Override
            public void connected(Connection connection){
                System.out.println("Connection: " + connection.getID());
            }

            @Override
            public void received(Connection connection, Object object){
                System.out.println("Received: " + object); // Обрабатываем пакет
                if(object instanceof PJSON){
                    JSONObject obj = ((PJSON)object).getJson();
                    System.out.println("Json / Action: " + obj.getString("action"));
                    int min = obj.getInt("min");
                    int max = obj.getInt("max");
                    for(int i = min; i < max; i++){
                        int num = obj.getInt(String.valueOf("Key " + i));
                        if(num % 100 == 0)
                            System.out.println(" Json | NUM = " +num);
                    }
                }
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
    }
}
