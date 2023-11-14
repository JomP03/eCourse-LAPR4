package boardcommunication;

/**
 * Enum used to represent the message code.
 */
public enum TcpMessageCode {
    /**
     * Communication test code.
     */
    COMMTEST(0),
    /**
     * End of session request code.
     */
    DISCONN(1),
    /**
     * Success code.
     */
    ACK(2),
    /**
     * Error code.
     */
    ERR(3),
    /**
     * Auth request code.
     */
    AUTH(4);

    private final int code;

    TcpMessageCode(int code) {
        this.code = code;
    }

    /**
     * Retrieves the int value of a code.
     *
     * @return the int
     */
    public int value() {
        return code;
    }
}
