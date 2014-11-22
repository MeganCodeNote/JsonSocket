/*  Created by meganlee on 11/19/14. */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
    A JSON socket server used to communicate with a JSON socket client. All the
    data is serialized in JSON. How to use it:

    Sever server = new Server(port)
    while (true) {
        Object data = server.accept().recv();
        String response = "{\"status\": \"ok\"}";
        server.send(response)
    }
*/

public class Server {
    private int port;
    private Socket client;
    private ServerSocket server;
    private int backlog = 10;

    public Server(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port, backlog);
    }

    /**
     *  Accept the next connection from client
     */
    public Server accept() throws IOException {
        if (client != null) {
            client.close();
        }
        client = server.accept();
        return this;
    }

    /*
     * Shutdown the service and close the connection
     */
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
        if (server != null) {
            server.close();
        }
    }

    public Server send(Object data) throws Exception{
        if (client == null) {
            throw new Exception("No Client is connected");
        }
        JsonSocketUtils.send(client, data);
        return this;
    }

    public Object receive() throws Exception{
        if (client == null) {
            throw new Exception("No Client is connected");
        }
        return JsonSocketUtils.receive(client);
    }


    ////////////////////   FOR DEMONSTRATE HOW TO USE  ///////////////////////
    public static void main(String[] args) {
        try {
            // create a socket server with port 20000
            Server server = new Server(20000).accept();

            // send data ten times to socket client, and then send null to indicate
            // the end of communication, please see /py/server_socket_test.py for the
            // peer socket client usage
            String data = JsonSocketUtils.readFile("test.txt");
            for (int i = 0; i < 10; i++) {
                server.send(data);
            }
            server.send(null).close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}