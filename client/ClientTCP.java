package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP {
    private Socket socket;

    ClientTCP(Socket socket) {
        this.socket = socket;
    }

    public void query(String request) throws IOException {
        Logger logger = Logger.getLogger(ClientTCP.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        logger.setLevel(Level.FINE);
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);

        logger.log(Level.FINEST, "Connection au serveur");

        PrintWriter query = new PrintWriter(
            new BufferedWriter(
                new OutputStreamWriter(
                    socket.getOutputStream()
                )
            ), true
        );

        BufferedReader response = new BufferedReader(
            new InputStreamReader(
                socket.getInputStream()
            )
        );

        query.println(request);

        logger.log(Level.INFO, response.readLine());
        logger.log(Level.FINE, "From "+socket.getLocalPort()+":"+socket.getPort());
        socket.close();
    }
}