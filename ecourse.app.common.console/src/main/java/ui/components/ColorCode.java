package ui.components;

import appsettings.Application;

public enum ColorCode {

    RESET("\u001B[0m"),
    YELLOW("\u001B[33m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String color;

    ColorCode(String color) {
        this.color = color;
    }

    public String getValue() {
        if (Application.settings().isOperativeSystemLinux()) {
            return color;
        } else {
            return "";
        }
    }

}
