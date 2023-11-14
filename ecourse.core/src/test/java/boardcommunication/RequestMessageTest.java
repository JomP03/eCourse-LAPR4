package boardcommunication;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RequestMessageTest {

    @Test
    public void ensureRequestMessageIsCreatedWithValidInformation() {
        // Arrange
        byte version = 1;
        TcpMessageCode code = TcpMessageCode.COMMTEST;
        String data = "test";

        // Act
        TcpRequestMessage requestMessage = new TcpRequestMessage(version, code, data);
        String dataString = "Code: " + TcpRequestMessage.getCodeFromString(code) + "\n";

        // Assert
        assertEquals(dataString, requestMessage.toString());
    }


    @Test
    public void ensureRequestMessageIsCreateWithNoDataField() {
        // Arrange
        byte version = 1;
        TcpMessageCode code = TcpMessageCode.COMMTEST;

        // Act
        TcpRequestMessage requestMessage = new TcpRequestMessage(version, code);
        String dataString = "Code: " + TcpRequestMessage.getCodeFromString(code) + "\n";

        // Assert
        assertEquals(dataString, requestMessage.toString());
    }


    @Test
    public void ensureRequestMessageIsCreatedWithValidMInformation2() {
        // Arrange
        byte[] message = new byte[]{1, 0, 0, 4, 't', 'e', 's', 't'};

        // Act
        TcpRequestMessage requestMessage = new TcpRequestMessage(message);
        String dataString = "Code: " + TcpRequestMessage.getCodeFromString(TcpMessageCode.COMMTEST) + "\n";

        // Assert
        assertEquals(dataString, requestMessage.toString());
    }


    @Test
    public void ensureRequestMessageIsNotCreatedWithInvalidCode2() {
        // Arrange
        byte[] message = new byte[]{1, 5, 0, 4, 't', 'e', 's', 't'};

        // Act
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new TcpRequestMessage(message);
        });
    }


    @Test
    public void ensureGetBytesTransformsTheMessageAsItShould() {
        // Arrange
        byte version = 1;
        TcpMessageCode code = TcpMessageCode.COMMTEST;
        String data = "test";
        TcpRequestMessage requestMessage = new TcpRequestMessage(version, code, data);
        byte[] message = new byte[]{1, 0, 0, 4, 't', 'e', 's', 't'};

        // Act
        byte[] bytes = requestMessage.getBytes();

        // Assert
        assertEquals(message.length, bytes.length);
        for (int i = 0; i < message.length; i++) {
            assertEquals(message[i], bytes[i]);
        }
    }


    @Test
    public void ensureGetCodeFromStringReturnsTheCorrectCode() {
        // Arrange
        byte code = 0;

        // Act
        byte codeFromString = TcpRequestMessage.getCodeFromString(TcpMessageCode.COMMTEST);

        // Assert
        assertEquals(code, codeFromString);
    }


    @Test
    public void ensureGetCodeFromStringReturnsTheCorrectCode2() {
        // Arrange
        byte code = 1;

        // Act
        byte codeFromString = TcpRequestMessage.getCodeFromString(TcpMessageCode.DISCONN);

        // Assert
        assertEquals(code, codeFromString);
    }


    @Test
    public void ensureGetCodeFromStringReturnsTheCorrectCode3() {
        // Arrange
        byte code = 4;

        // Act
        byte codeFromString = TcpRequestMessage.getCodeFromString(TcpMessageCode.AUTH);

        // Assert
        assertEquals(code, codeFromString);
    }


    // Test the method codes
    @Test
    public void ensureCodesReturnsTheCorrectCodes() {
        // Arrange
        TcpMessageCode[] codes = {TcpMessageCode.COMMTEST, TcpMessageCode.DISCONN, TcpMessageCode.AUTH};

        // Act
        List<TcpMessageCode> messageCodes = TcpRequestMessage.clientCodes();

        // Assert
        assertEquals(codes.length, messageCodes.size());
        for (int i = 0; i < codes.length; i++) {
            assertEquals(codes[i], messageCodes.get(i));
        }
    }
}
