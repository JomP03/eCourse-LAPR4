package appsettings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The application settings.
 *
 * @author Paulo Gandra Sousa
 * @author Gustavo Jorge
 */
public class AppSettings {
    private static final Logger LOGGER = LogManager.getLogger(AppSettings.class);

    private static final String SCHEMA_GENERATION_KEY = "javax.persistence.schema-generation.database.action";
    // Name of the file to search the application pre-defined settings from
    private static final String PROPERTIES_RESOURCE = "appSettings.properties";
    // Key to search in the appSettings file, to determine which will be the factory used for the repositories
    private static final String REPOSITORY_FACTORY_KEY = "persistence.repositoryFactory";
    private static final String UI_MENU_LAYOUT_KEY = "ui.menu.layout";
    // Key to search in the appSettings file, to determine which will be the persistenceUnit used in the application
    private static final String PERSISTENCE_UNIT_KEY = "persistence.persistenceUnit";
    // Key to search in the appSettings file, to determine if there will be used eventful controllers (true or false)
    private static final String USE_EVENTFUL_CONTROLLERS = "UseEventfulControllers";
    // Key to search in the appSettings file, to determine the maximum length of the acronym of a teacher
    private static final String MAX_TEACHER_ACRONYM_LENGTH = "maxTeacherAcronymLength";
    // Key to search in the appSettings file, to determine the slot duration time of classes
    private static final String SLOT_DURATION_TIME = "slotDurationTime";
    // Key to search in the appSettings file, to determine the max class duration in minutes
    private static final String MAX_CLASS_DURATION = "maxClassDuration";
    // Key to search in the appSettings file, to determine the start time of classes (eg: classes start from 8am)
    private static final String START_TIME = "startTime";
    // Key to search in the appSettings file, to determine the end time of classes (eg: classes end at 8pm)
    private static final String END_TIME = "endTime";
    // Key to search in the appSettings file, to determine the start date of classes (eg: classes start from 1st of September)
    private static final String START_DATE = "startDate";
    // Key to search in the appSettings file, to determine the end date of classes (eg: classes end at 31st of July)
    private static final String END_DATE = "endDate";
    // Key to search in the appSettings file, to determine the number of weeks from now where teacher can update classes from ( eg: if the value is 10
    // teachers can only update classes that are 10 weeks from now, but can still schedule classes for the next year for example)
    private static final String NUMBER_OF_WEEKS_FROM_NOW = "numberOfWeeksFromNow";
    private static final String MAX_NUMBER_OF_ROWS = "maxRows";
    // Key to search in the appSettings file, to determine the maximum number of columns a board can have
    private static final String MAX_NUMBER_OF_COLUMNS = "maxColumns";
    // Key to search in the appSettings file, to determine the maximum number of columns a board can have
    private static final String TCP_SERVER_PORT = "tcpServerPort";
    // Key to search in the appSettings file the server's tcp port
    private static final String SERVER_HOST = "serverHost";
    // Key to search in the appSettings file the server's host
    private static final String HTTP_SERVER_PORT = "httpServerPort";
    // Key to search in the appSettings file the server's http port

    private final Properties applicationProperties = new Properties();

    public AppSettings() {
        loadProperties();
    }

    // Method used to load the application configs from the appSettings file
    private void loadProperties() {
        try (InputStream propertiesStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (propertiesStream == null) {
                throw new FileNotFoundException(
                        "Property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
            }
            applicationProperties.load(propertiesStream);
            try {
                validateProperties();
            } catch (final IllegalArgumentException ex) {
                String message = "\u001B[31m ERROR -" + ex.getMessage() + "\u001B[0m";
                System.out.println("\n" + "=".repeat(message.length()));
                System.out.println(message);
                System.out.println("=".repeat(message.length()));
                System.exit(0);
            }

        } catch (final IOException exio) {
            setDefaultProperties();

            LOGGER.warn("Loading default properties", exio);
        }
    }

    // Sets some default properties
    private void setDefaultProperties() {
        applicationProperties.setProperty(REPOSITORY_FACTORY_KEY, "jpa.JpaRepositoryFactory");
        applicationProperties.setProperty(UI_MENU_LAYOUT_KEY, "horizontal");
        applicationProperties.setProperty(PERSISTENCE_UNIT_KEY, "eCoursePU");
        applicationProperties.setProperty(MAX_TEACHER_ACRONYM_LENGTH, "3");
    }

    public boolean isMenuLayoutHorizontal() {
        return "horizontal".equalsIgnoreCase(this.applicationProperties.getProperty(UI_MENU_LAYOUT_KEY));
    }

    public String persistenceUnitName() {
        return applicationProperties.getProperty(PERSISTENCE_UNIT_KEY);
    }

    public String repositoryFactory() {
        return applicationProperties.getProperty(REPOSITORY_FACTORY_KEY);
    }

    public String getProperty(final String prop) {
        return applicationProperties.getProperty(prop);
    }

    public int getMaxTeacherAcronymLength() {
        return Integer.parseInt(applicationProperties.getProperty(MAX_TEACHER_ACRONYM_LENGTH));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Map extendedPersistenceProperties() {
        final Map ret = new HashMap();
        ret.put(SCHEMA_GENERATION_KEY, applicationProperties.getProperty(SCHEMA_GENERATION_KEY));
        return ret;
    }

    public boolean useEventfulControllers() {
        return Boolean.parseBoolean(applicationProperties.getProperty(USE_EVENTFUL_CONTROLLERS));
    }

    public Integer getSlotDurationTime() {
        return Integer.parseInt(applicationProperties.getProperty(SLOT_DURATION_TIME));
    }

    public Integer getMaxClassDuration() {
        return Integer.parseInt(applicationProperties.getProperty(MAX_CLASS_DURATION));
    }

    public String getStartTime() {
        return applicationProperties.getProperty(START_TIME);
    }

    public String getEndTime() {
        return applicationProperties.getProperty(END_TIME);
    }

    public String getNumberOfWeeksFromNow() {
        return applicationProperties.getProperty(NUMBER_OF_WEEKS_FROM_NOW);
    }

    /**
     * @return the server's port
     */
    public int getTcpServerPort() {
        return Integer.parseInt(applicationProperties.getProperty(TCP_SERVER_PORT));
    }

    public int getHttpServerPort() {
        return Integer.parseInt(applicationProperties.getProperty(HTTP_SERVER_PORT));
    }

    /**
     * @return the server's host
     */
    public String getServerHost() {
        return applicationProperties.getProperty(SERVER_HOST);
    }

    private void validateProperties() {
        validateSlotDurationTime();
        validateClassDuration();
        validateClassTime();
        validateNumberOfWeeksFromNow();
        validateMaxNumberOfRows();
        validateMaxNumberOfColumns();
    }

    private void validateNumberOfWeeksFromNow() {

        final String numberOfWeeksFromNow = getNumberOfWeeksFromNow();

        if (numberOfWeeksFromNow == null || numberOfWeeksFromNow.isEmpty()) {
            throw new IllegalArgumentException("The number of weeks from now in the application.properties is Invalid. It must be a number.");
        }

        try {
            Integer.parseInt(numberOfWeeksFromNow);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The number of weeks from now in the application.properties is Invalid. It must be a number.");
        }

        if (Integer.parseInt(numberOfWeeksFromNow) <= 0) {
            throw new IllegalArgumentException("The number of weeks from now in the application.properties is Invalid. It must be greater than 0.");
        }
    }


    private void validateClassTime() {
        final String startTime = getStartTime();
        final String endTime = getEndTime();

        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        if (startParts.length != 2 || endParts.length != 2) {
            throw new IllegalArgumentException("The start time or end time in the application.properties is Invalid. It must be in the format HH:MM.");
        }

        if (Integer.parseInt(startParts[0]) >= 24 || Integer.parseInt(startParts[0]) < 0) {
            throw new IllegalArgumentException("The start time in the application.properties is Invalid. It must be under the end time, and between 0 and 23.");
        }

        if (Integer.parseInt(endParts[0]) > 24 || Integer.parseInt(endParts[0]) <= 0) {
            throw new IllegalArgumentException("The end time in the application.properties is Invalid. It must be bigger than the start time, and between 1 and 24.");
        }

        if (Integer.parseInt(endParts[1]) >= 60 || Integer.parseInt(endParts[1]) % 5 != 0 || Integer.parseInt(startParts[1]) >= 60 || Integer.parseInt(startParts[1]) % 5 != 0) {
            throw new IllegalArgumentException("The start time or end time in the application.properties is Invalid.");
        }

        if (Integer.parseInt(startParts[0]) == Integer.parseInt(endParts[0]) && Integer.parseInt(startParts[1]) >= Integer.parseInt(endParts[1])) {
            throw new IllegalArgumentException("The start time in the application.properties is Invalid. It must be under the end time.");
        }

        if (Integer.parseInt(startParts[0]) > Integer.parseInt(endParts[0])) {
            throw new IllegalArgumentException("The start time in the application.properties is Invalid. It must be under the end time.");
        }

    }


    /**
     * Validates if the slot duration time is under or equal to 60 minutes.
     */
    private void validateSlotDurationTime() {
        final int slotDurationTime = getSlotDurationTime();
        if (slotDurationTime > 60) {
            throw new IllegalArgumentException("The slot duration time in the application.properties is Invalid. It must be under or equal to 60 minutes and must be a multiple of 5.");
        }
        if (slotDurationTime % 5 != 0) {
            throw new IllegalArgumentException("The slot duration time in the application.properties is Invalid. It must be a multiple of 5 and must be under or equal to 60 minutes.");
        }
    }

    /**
     * Validates if the max class duration is a multiple of the slot duration time.
     */
    private void validateClassDuration() {
        final int maxClassDuration = getMaxClassDuration();
        final int slotDurationTime = getSlotDurationTime();
        if (maxClassDuration % slotDurationTime != 0) {
            throw new IllegalArgumentException("The max class duration in the application.properties is Invalid. It must be a multiple of the slot duration time.");
        }
    }

    public boolean isOperativeSystemLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public int getMaxNumberOfColumns() {
        return Integer.parseInt(applicationProperties.getProperty(MAX_NUMBER_OF_COLUMNS));
    }

    public int getMaxNumberOfRows() {
        return Integer.parseInt(applicationProperties.getProperty(MAX_NUMBER_OF_ROWS));
    }

    /*
     * Validates if the max number of columns is under or equal to 10.
     */
    private void validateMaxNumberOfColumns() {
        final int maxNumberOfColumns = getMaxNumberOfColumns();
        if (maxNumberOfColumns > 10) {
            throw new IllegalArgumentException("The max number of columns in the application.properties is Invalid. The maximum number of columns must be 10.");
        }
    }

    /*
     * Validates if the max number of rows is under or equal to 20.
     */
    private void validateMaxNumberOfRows() {
        final int maxNumberOfRows = getMaxNumberOfRows();
        if (maxNumberOfRows > 20) {
            throw new IllegalArgumentException("The max number of rows in the application.properties is Invalid. The maximum number of rows must be 20.");
        }
    }

}
