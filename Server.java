import java.net.*;

class Server {
    final static int port = 8532;
    final static int taille = 1024;
    final static byte buffer[] = new byte[taille];
    
    public static void main(String argv[]) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            while(true) {
                DatagramPacket data = new DatagramPacket(buffer, buffer.length);
                socket.receive(data);
                String received = new String(data.getData(), 0, data.getLength());

                System.out.println(data.getAddress()+"requested"+received);

                String toBeSend;

                switch (received) {
                    case "voitures":
                        toBeSend="Listes des voitures et tout";
                    break;
                
                    default:
                        toBeSend="Commande Inconnue !";
                    break;
                }

                byte newBuffer[] = new byte[taille];
                newBuffer=(toBeSend).getBytes();
                data.setLength(newBuffer.length);
                data.setData(newBuffer);
                socket.send(data);
            }
        }
    }
}