package reservation.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerTCP extends java.lang.Thread {
    final static int taille = 1024;
    private Socket socket;

    ServerTCP(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Logger logger = Logger.getLogger(ServerTCP.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        logger.setLevel(Level.FINE);
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);

        logger.log(Level.INFO, "Server started on "+this.socket.getLocalAddress());
        try {
            BufferedReader query = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );
            
            PrintWriter response = new PrintWriter(
                new BufferedWriter(
                    new OutputStreamWriter(
                        socket.getOutputStream()
                    )
                ), true
            );


            logger.log(Level.FINE, socket.getInetAddress()+" requested "+query.readLine());
        
            String toBeSend;

            switch (query.readLine()) {
                case "voitures":
                    toBeSend="Listes des voitures et tout";
                break;
    
                case "test":
                    try {
                        sleep(5000);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    toBeSend="Slept";
                break;
            
                default:
                    toBeSend="404 Not Found";
                break;
            }

            response.println(toBeSend);
            //socket.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.valueOf(e));
        }
    }
}