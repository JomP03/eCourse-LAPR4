import java.io.*;
import java.net.*;

import appsettings.*;
import boardcommunication.TcpMessageCode;
import boardcommunication.TcpRequestMessage;
import ui.components.ListSelector;

import static java.lang.Thread.sleep;

public class BoardClient {
    static InetAddress serverIP;
    static Socket socket;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static int SERVER_PORT;
    private static String SERVER_HOST;

    public void sendRequest() throws IOException {
        // read from config file
        readConfigFile();
        // Connection
        connection();

        // Create input and output streams to communicate with the server
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);

        InputStream inputStream = socket.getInputStream();
        DataInputStream in = new DataInputStream(inputStream);


        // Request data from the user
        TcpRequestMessage message = getUserInput();
        if (message == null)
            return;


        // Send the message to the server
        byte[] messageBytes = message.getBytes();
        out.writeInt(messageBytes.length);
        out.write(messageBytes);

        // Receive the server response
        int responseLength = in.readInt();
        byte[] responseBytes = in.readNBytes(responseLength);
        TcpRequestMessage response = new TcpRequestMessage(responseBytes);
        System.out.println(response);

        try {
            sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Close the streams and the socket
        socket.close();
    }

    /**
     * Read the config file
     */
    private void readConfigFile() {
        SERVER_PORT = Application.settings().getTcpServerPort();
        SERVER_HOST = Application.settings().getServerHost();
    }

    /**
     * Establishes a TCP connection with the server
     */
    private void connection() {
        // Establish a TCP connection with the server
        try {
            serverIP = InetAddress.getByName(SERVER_HOST);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + SERVER_HOST);
            System.exit(1);
        }

        try {
            socket = new Socket(serverIP, SERVER_PORT);
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }
    }

    /**
     * Get the user input
     *
     * @return Request message
     */
    private static TcpRequestMessage getUserInput() {
        byte version = 1;

        // Select the message code to send
        ListSelector<TcpMessageCode> codes = new ListSelector<>("Select a Message Code", TcpRequestMessage.clientCodes());
        codes.showAndSelect();
        TcpMessageCode code = codes.getSelectedElement();

        // If code is 0 or 1, then the message has no data
        if (code.value() <= 1) {
            return new TcpRequestMessage(version, code);
        }
        else {
            System.out.print("Enter the message: ");
            String data;
            try {
                data = br.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input");
                return null;
            }
            return new TcpRequestMessage(version, code, data);
        }
    }
}
