package boardcommunication;

import java.util.*;

/**
 * Class used to create a request message
 */
public class TcpRequestMessage {

    private final byte version;
    private final byte code;
    private byte d_lenght_1;
    private byte d_lenght_2;
    private final byte[] data;


    /**
     * Instantiates a new Request message, with data.
     *
     * @param version the version
     * @param code    the code
     * @param data    the data
     *
     * @throws IllegalArgumentException if the code is invalid
     */
    public TcpRequestMessage(byte version, TcpMessageCode code, String data) {
        byte codeByte = getCodeFromString(code);
        if (codeByte < 0 || codeByte > TcpMessageCode.values().length)
            throw new IllegalArgumentException("Code is invalid");

        this.version = version;
        this.code = codeByte;
        this.data = data.getBytes();
        dLengths();
    }

    /**
     * Instantiates a new Request message with no data.
     *
     * @param version the version
     * @param code    the code
     *
     * @throws IllegalArgumentException if the code is invalid
     */
    public TcpRequestMessage(byte version, TcpMessageCode code) {
        byte codeByte = getCodeFromString(code);
        if (codeByte < 0 || codeByte > TcpMessageCode.values().length)
            throw new IllegalArgumentException("Code is invalid");

        this.version = version;
        this.code = codeByte;
        this.data = new byte[0];
        dLengths();
    }

    /**
     * Instantiates a new Request message, with a byte array.
     *
     * @param message the message
     */
    public TcpRequestMessage(byte[] message) {
        if (message[1] < 0 || message[1] > 4)
            throw new IllegalArgumentException("Code is invalid");

        this.version = message[0];
        this.code = message[1];
        this.d_lenght_1 = message[2];
        this.d_lenght_2 = message[3];
        this.data = Arrays.copyOfRange(message, 4, message.length);
    }

    private void dLengths() {
        int dataLength = data.length;

        // Split the length into two bytes in network byte order
        d_lenght_1 = (byte) (dataLength >> 8);
        d_lenght_2 = (byte) (dataLength & 0xFF);
    }

    /**
     * Gets the code byte from the enum.
     *
     * @param code the code
     * @return the code from string
     */
    public static byte getCodeFromString(TcpMessageCode code) {
        byte codeByte = -1;
        switch (code) {
            case COMMTEST:
                return 0;
            case DISCONN:
                return 1;
            case ACK:
                return 2;
            case ERR:
                return 3;
            case AUTH:
                return 4;
            default:
                return codeByte;
        }
    }

    /**
     * Retrieves the list of codes that the client can send
     *
     * @return the list
     */
    public static List<TcpMessageCode> clientCodes() {
        return Arrays.asList(TcpMessageCode.COMMTEST, TcpMessageCode.DISCONN, TcpMessageCode.AUTH);
    }

    /**
     * Transforms the message into a byte array
     *
     * @return the byte array of the message
     */
    public byte[] getBytes() {
        int messageLength;
        messageLength = 4 + data.length;
        byte[] message = new byte[messageLength];

        message[0] = version;
        message[1] = code;
        message[2] = d_lenght_1;
        message[3] = d_lenght_2;

        System.arraycopy(data, 0, message, 4, data.length);

        return message;
    }

    @Override
    public String toString() {
        if (code < 3) {
            return "Code: " + code + "\n";
        }
        return "Code: " + code + "\n" +
                "Data: " + new String(data) + "\n";
    }
}
