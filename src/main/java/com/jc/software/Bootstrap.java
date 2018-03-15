package com.jc.software;

import com.jc.software.client.main.GlobalChatClient;
import com.jc.software.server.main.GlobalChatServer;

/**
 * Created by jonataschagas on 15/03/18.
 */
public class Bootstrap {

    public static void main(String args[]) throws Exception {

        if(args.length < 2) {
            System.out.println("Please provide mode and hostname. Mode can be server or client. " +
                    "Ex: java -jar global-chat.jar server 10.0.0.2");
            return;
        }

        String mode = args[0];

        if(mode.equals("server")) {
            GlobalChatServer.main(args);
        } else {
            GlobalChatClient.main(args);
        }

    }

}
