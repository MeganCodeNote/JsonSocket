/* Created by meganlee on 11/19/14.*/

import java.net.Socket;

public class Client {
    private Socket socket;
    private int port;
    private String host;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Client send(Object data) throws Exception{
        if (socket == null) {
            throw new Exception("No socket is available for sending data, please connect to a socket server!");
        }
        JsonSocketUtils.send(socket, data);
        return this;
    }

    public Object receive() throws Exception{
        if (socket == null) {
            throw new Exception("No socket is available for receving data, please connect to a socket server!");
        }
        return JsonSocketUtils.receive(socket);
    }

    public void close() throws Exception{
        if (socket != null) {
            socket.close();
        }
    }


    ////////////////////  FOR TEST ONLY  ///////////////////////

    public static void main(String[] args) {
        try {
            // create a socket server with port 20000
            Client client = new Client("127.0.0.1", 20000);

            // receive the data from client and store it to "received.txt"
            Object data = client.receive();
            JsonSocketUtils.writeToFile("received.txt", data);

            // send the same data back to test functionality
            client.send(data).close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
