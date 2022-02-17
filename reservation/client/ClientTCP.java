package reservation.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

public class ClientTCP {
    private Socket socket;
    private reservation.MyLogger logger = new reservation.MyLogger(ClientTCP.class.getName());

    ClientTCP(Socket socket) {
        this.socket = socket;
    }

    public void query(String request) throws IOException {

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