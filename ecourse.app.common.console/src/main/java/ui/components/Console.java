
package ui.components;


import eapli.framework.strings.util.StringPredicates;
import eapli.framework.time.util.Calendars;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.*;
import java.util.Calendar;

public final class Console {


    /**
     * Reads input text from the user
     *
     * @param prompt - Prompt to show the user
     * @return - the text read from the user
     */
    public static String readLine(final String prompt) {
        try {
            System.out.print(prompt);
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            return in.readLine();
        } catch (IOException var3) {
            return var3.getMessage();
        }
    }


    /**
     * Reads a non-empty line from the user
     *
     * @param prompt  - Prompt to show the user
     * @param message - Message to show if the user doesn't input anything (error message)
     * @return - the line read from the user
     */
    public static String readNonEmptyLine(final String prompt, final String message) {
        while (true) {
            try {
                System.out.print(prompt);
                InputStreamReader converter = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(converter);
                String text = in.readLine();
                if (!StringPredicates.isNullOrEmpty(text)) {
                    return text;
                }

                System.out.printf("%s\n\n", message);
            } catch (IOException var5) {
                return var5.getMessage();
            }
        }
    }


    /**
     * Reads a non-empty line from the user
     *
     * @param prompt  - Prompt to show the user
     * @param message - Message to show if the user doesn't input anything (error message)
     * @return - the line read from the user
     */
    public static String readNonBlankLine(final String prompt, final String message) {
        while (true) {
            try {
                System.out.print(prompt);
                InputStreamReader converter = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(converter);
                String text = in.readLine();
                if (!StringPredicates.isNullOrWhiteSpace(text)) {
                    return text;
                }

                System.out.printf("%s\n\n", message);
            } catch (IOException var5) {
                return var5.getMessage();
            }
        }
    }


    /**
     * Reads a password from the user
     *
     * @param prompt  - Prompt to show the user
     * @param message - Message to show if the user doesn't input anything (error message)
     * @return - the password read from the user
     */
    public static String readPassword(final String prompt, final String message) {
        String password;
        java.io.Console console = System.console();
        if (console == null) {
            return readNonEmptyLine(prompt, message);
        } else {
            password = new String(console.readPassword(prompt));
        }
        return password;
    }


    /**
     * Reads an integer from the user
     *
     * @param prompt - Prompt to show the user
     * @return - the integer read from the user
     */
    public static int readInteger(final String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                return Integer.parseInt(input);
            } catch (NumberFormatException ignored) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - INVALID OPTION");
            }
        }
    }


    /**
     * Reads a byte from the user
     *
     * @param prompt - Prompt to show the user
     * @return - the byte read from the user
     */
    public static int readByte(final String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                return Byte.parseByte(input);
            } catch (NumberFormatException ignored) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - INVALID OPTION");
            }
        }
    }


    /**
     * Reads a positive integer from the user
     *
     * @param prompt - Prompt to show the user
     * @return - the positive integer read from the user
     */
    public static int readPositiveInteger(final String prompt) {
        while (true) {
            int number = readInteger(prompt);
            if (number >= 0) {
                return number;
            } else {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - NUMBER MUST BE POSITIVE");
            }
        }
    }


    /**
     * Reads an option from the user
     *
     * @param low  - Lowest option
     * @param high - Highest option
     * @param exit - Exit option
     * @return - the option selected by the user
     */
    public static int readOption(final int low, final int high, final int exit) {
        int option;
        boolean selectedInvalidOption = true;
        do {
            option = readInteger("Select an option: ");

            if (option != 0 && (option < low || option > high)) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - INVALID OPTION");
            } else {
                selectedInvalidOption = false;
            }

        } while (option != exit && selectedInvalidOption);

        return option;
    }


    /**
     * Reads an option from the user
     *
     * @param low  - Lowest option
     * @param high - Highest option
     * @return the option selected by the user
     */
    public static int readOption(final int low, final int high) {
        int option;
        boolean selectedInvalidOption;
        do {
            option = readInteger("Select an option: ");

            if (option < low || option > high) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - INVALID OPTION");
                selectedInvalidOption = true;
            } else {
                selectedInvalidOption = false;
            }

        } while (selectedInvalidOption);

        return option;
    }


    /**
     * Reads a date from the user
     *
     * @param prompt - Prompt to show the user
     * @return - The date selected by the user
     */
    public static LocalDate readDate(final String prompt) {
        return readDate(prompt, "yyyy/MM/dd");
    }


    /**
     * Reads a date from the user
     *
     * @param prompt     - Prompt to show the user
     * @param dateFormat - Date format to use
     * @return - The date selected by the user
     */
    public static LocalDate readDate(final String prompt, final String dateFormat) {
        while (true) {
            try {
                String strDate = readLine(prompt);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                return LocalDate.parse(strDate, formatter);
            } catch (DateTimeParseException ignored) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - INVALID DATE FORMAT");
            }

        }
    }

    /**
     * Read time local time.
     *
     * @param prompt the prompt
     * @return the local time
     */
    public static LocalTime readTime(final String prompt) {
        return readTime(prompt, "HH:mm");
    }

    /**
     * Read time local time.
     *
     * @param prompt     the prompt
     * @param timeFormat the time format
     * @return the local time
     */
    public static LocalTime readTime(final String prompt, final String timeFormat) {
        while (true) {
            try {
                String strTime = readLine(prompt);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
                return LocalTime.parse(strTime, formatter);
            } catch (DateTimeParseException ignored) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - INVALID TIME FORMAT");
            }

        }
    }


    /**
     * Reads a time from the user
     *
     * @param prompt - Prompt to show the user
     * @return - The time selected by the user
     */
    public static Calendar readCalendar(final String prompt) {
        return readCalendar(prompt, "dd-MM-yyyy");
    }


    /**
     * Reads a time from the user
     *
     * @param prompt     - Prompt to show the user
     * @param dateFormat - Date format to use
     * @return - The time selected by the user
     */
    public static Calendar readCalendar(final String prompt, final String dateFormat) {
        while (true) {
            try {
                String strDate = readLine(prompt);
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                return Calendars.fromDate(df.parse(strDate));
            } catch (ParseException ignored) {
            }
        }
    }


    /**
     * Reads a decimal number from the user
     *
     * @param prompt - Prompt to show the user
     * @return - The decimal number selected by the user
     */
    public static double readDouble(final String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException ignored) {
            }
        }
    }


    /**
     * Reads a float number from the user
     * @param prompt - Prompt to show the user
     * @return - The decimal number selected by the user
     */
    public static Float readFloat(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - NUMBER MUST BE POSITIVE");
            }
        }
    }


    /**
     * Reads a float number from the user
     * @param prompt - Prompt to show the user
     * @return - The decimal number selected by the user
     */
    public static Float readPositiveFloat(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                float number = Float.parseFloat(input);
                if (number > 0)
                    return number;
                else {
                    System.out.printf(
                            ColorCode.RED.getValue() +
                                    "%s \n\n" +
                                    ColorCode.RESET.getValue(), "ERROR - NUMBER MUST BE POSITIVE");
                }
            } catch (NumberFormatException ignored) {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - NUMBER MUST BE POSITIVE");
            }
        }
    }


    public static boolean readBoolean(String prompt) {
        while (true) {
            String bool = readLine(prompt);
            bool = bool.toLowerCase();
            if (bool.equals("true") || bool.equals("false"))
                return Boolean.parseBoolean(bool);

            else {
                System.out.printf(
                        ColorCode.RED.getValue() +
                                "%s \n\n" +
                                ColorCode.RESET.getValue(), "ERROR - NUMBER MUST BE TRUE OR FALSE");
            }
        }
    }

    public static File selectFileWithFileExplorer() {
        FileDialog fileDialog = new FileDialog((Frame) null, "Select TXT file", FileDialog.LOAD);
        fileDialog.setFile("*.txt");
        fileDialog.setDirectory(System.getProperty("user.dir"));
        fileDialog.setVisible(true);

        String filename = fileDialog.getFile();
        if (filename == null) {
            throw new IllegalArgumentException("No file selected.");
        }

        File selectedFile = new File(fileDialog.getDirectory(), filename);
        if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
            throw new IllegalArgumentException("The selected file is not a TXT file.");
        }

        return selectedFile;
    }

    public static File selectFileWithPath() {
        String path = Console.readNonEmptyLine("Insert the path to the txt file: ",
                "A path must be provided");
        System.out.println();
        File selectedFile = new File(path);

        if (!selectedFile.exists()) {
            throw new IllegalArgumentException("The selected file does not exist.");
        }

        if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
            throw new IllegalArgumentException("The selected file is not a TXT file.");
        }

        return selectedFile;
    }


    /**
     * Makes user select a directory
     * @return the path to the directory
     */
    public static File selectDirectoryWithFileExplorer() {
        FileDialog fileDialog = new FileDialog((Frame) null, "Select Directory", FileDialog.LOAD);
        fileDialog.setDirectory(System.getProperty("user.dir"));
        fileDialog.setFilenameFilter((dir, name) -> new File(dir, name).isDirectory());
        fileDialog.setFile("directory_selected.txt"); // Set a dummy file name to trick the file dialog
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        if (directory == null) {
            throw new IllegalArgumentException("No directory selected.");
        }

        File selectedDirectory = new File(directory);
        if (!selectedDirectory.isDirectory()) {
            throw new IllegalArgumentException("The selected path is not a directory.");
        }

        return selectedDirectory;
    }







    /**
     * Makes user select a directory
     * @return the path to the directory
     */
    public static File selectDirectoryWithPath() {
        String path = Console.readNonEmptyLine("Insert the path to the directory: ", "A path must be provided");
        System.out.println();
        File selectedDirectory = new File(path);

        if (!selectedDirectory.exists()) {
            throw new IllegalArgumentException("The selected directory does not exist.");
        }

        if (!selectedDirectory.isDirectory()) {
            throw new IllegalArgumentException("The selected path is not a directory.");
        }

        return selectedDirectory;
    }





}

