import auth.*;
import boardcommunication.TcpMessageCode;
import boardcommunication.TcpRequestMessage;
import usermanagement.domain.SessionManager;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

public class TcpServerMessageHandler implements Runnable {
    private final Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public TcpServerMessageHandler(Socket clientSocket) {
        socket = clientSocket;
    }

    public void run() {
        InetAddress clientIP;

        clientIP = socket.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() +
                ", port number " + socket.getPort());

        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            // Read the length of the message
            int length = in.readInt();

            // Read the message
            byte[] message = in.readNBytes(length);
            byte version = message[0];
            byte code = message[1];
            String data = new String(Arrays.copyOfRange(message, 4, message.length), StandardCharsets.UTF_8);
            System.out.println("Version: " + version);
            System.out.println("Code: " + code);
            System.out.println("Data: " + data);

            // Act upon the code
            codeFactory(code, version, data);

            // Disconnect the client
            System.out.println("Client " + clientIP.getHostAddress() + ", port number: " + socket.getPort() +
                    " disconnected");

            socket.close();
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }


    /**
     * Acts upon the code received from the client
     *
     * @param code    - Message's code
     * @param version - Message's version
     * @param data    - Message's data
     * @throws IOException - If an I/O error occurs
     */
    private void codeFactory(byte code, byte version, String data) throws IOException {
        switch (code) {
            case 0:
                // COMMTEST
                sendSuccessResponse(version, out);
                break;
            case 1:
                // DISCONN
                sendSuccessResponse(version, out);
                disconn();
                break;
            case 4:
                // AUTH
                authenticationHandler(version, data);
                break;

            default:
                sendErrorResponse(version, "Invalid Option", out);
        }
    }


    /**
     * Handles the authentication request
     *
     * @throws IOException - If an I/O error occurs
     */
    private void authenticationHandler(byte version, String data) throws IOException {
        // Handle the received data
        String[] dataSplit;
        try {
            dataSplit = data.split(";");
            if (dataSplit.length != 2) {
                throw new Exception();
            }
        } catch (Exception e) {
            sendErrorResponse(version, "Invalid data for authentication", out);
            return;
        }

        // Split by username and password
        final String username = dataSplit[0];
        final String password = dataSplit[1];


        try {
            // Authenticate the user
            boolean result = authenticate(username, password);

            // Send the response
            if (result) {
                sendSuccessResponse(version, out);
            } else {
                sendErrorResponse(version, "Authentication failed", out);
            }
        } catch (Exception e) {
            sendErrorResponse(version, e.getMessage(), out);
        }

    }

    // Method responsible for authenticating the user
    private boolean authenticate(String username, String password) {
        // Use the SessionManager to authenticate the user
        SessionManager sessionManager = SessionManager.getInstance();
        Optional<SessionToken> sessionToken = sessionManager.login(username, password);
        if (sessionToken.isPresent()) {
            System.out.println(sessionToken.get());
            System.out.println(sessionManager.isUserLoggedIn(sessionToken.get()));
            System.out.println(sessionManager.getLoggedInUser(sessionToken.get()));

            return true;
        } else {
            return false;
        }
    }


    /**
     * Disconnect the client and the server
     */
    private void disconn() {
        try {
            System.out.println("Disconnecting client");
            socket.close();
            System.out.println("Disconnected server");
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Error disconnecting client");
        }
    }


    /**
     * Send a response to the client - success
     *
     * @param version Version of the message
     * @param out     Output stream
     * @throws IOException If an I/O error occurs
     */
    private void sendSuccessResponse(byte version, DataOutputStream out) throws IOException {
        // Create a success response
        TcpRequestMessage response = new TcpRequestMessage(version, TcpMessageCode.ACK);

        // Send response
        byte[] responseBytes = response.getBytes();
        out.writeInt(responseBytes.length);
        out.write(responseBytes);
    }


    /**
     * Send a response to the client - error
     *
     * @param version Version of the message
     * @param out     Output stream
     * @param data    Data to send
     * @throws IOException If an I/O error occurs
     */
    private void sendErrorResponse(byte version, String data, DataOutputStream out) throws IOException {
        // Create an error response
        TcpRequestMessage response = new TcpRequestMessage(version, TcpMessageCode.ERR, data);

        // Send response
        byte[] responseBytes = response.getBytes();
        out.writeInt(responseBytes.length);
        out.write(responseBytes);
    }
}
