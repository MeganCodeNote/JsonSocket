/* Created by meganlee on 11/20/14. */

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class JsonSocketUtils {
    /* The utility member for json converting */
    public static ObjectMapper jsonUtil = new ObjectMapper();

    /* Buffer size for receiving data */
    private static final int CHUNKSIZE = 8192;


    /*
     * For sending the data
     * @param data: if data is null, will only sent length -1 and no actual data to peer,
     *              then the peer will know to close the connection automatically
     */
    public static void send(Socket socket, Object data) throws Exception {
        // step 1: serialize data into json string
        String jsonString = "";
        try {
            if (data != null) {
                jsonString = jsonUtil.writeValueAsString(data);
            }
        } catch (IOException e) {
            throw new Exception("Parameter 'data' is non-JSON-serializable!");
        }

        // step 2: send the length first and then send the content
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("\n--------- start sending string ------------");
            if (data != null) {
                System.out.println("  data length: " + jsonString.length());
                writer.write(jsonString.length() + "\n"); // length
                writer.write(jsonString);  // content
                writer.flush();
            } else {
                System.out.println("  data length: " + "-1");
                writer.write("-1\n");   // send -1 to indicate the end of communication
                writer.flush();
            }
            System.out.println("--------- end sending string ------------");

        } catch (IOException e) {
            throw new Exception("Cannot get output stream from Socket!");
        }
    }

    /*
     * For receiving the data
     * @return, if returned data is null, indicates that the peer wants to end the communication
     *          so please close connection with peer automatically
     */
    public static Object receive(Socket socket) {
        try {
            // 1. read the length
            StringBuilder lengthStr = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                char ch = (char)reader.read();
                if (ch == '\n') {
                    break;
                } else {
                    lengthStr.append(ch);
                }
            }
            int length = Integer.valueOf(lengthStr.toString());
            System.out.println("\n--------- start receiving string ------------");
            System.out.println("  data length: " + length);

            // 2. read the content
            if (length == -1) {
                return null;
            }

            char[] buffer = new char[length];
            int offset = 0;
            while (length > offset) {
                int received = reader.read(buffer, offset, Math.min(CHUNKSIZE, length - offset));
                offset += received;
            }
            // ERROR: DO NOT close the reader if you still want to use the socket
            // reader.close();
            System.out.println("--------- end receiving string ------------");

            // 3. loads content into an object and return it
            return jsonUtil.readValue(new String(buffer).trim(), Object.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * A util function for read content of a file into a java String
     */
    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }


    /*
     * A util function for write content into a file
     */
    public static void writeToFile(String filename, Object data) throws IOException {
        // System.out.println(data);
        PrintWriter out = new PrintWriter(filename);
        try {
            out.print(data.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            out.close();
        }
    }
}
