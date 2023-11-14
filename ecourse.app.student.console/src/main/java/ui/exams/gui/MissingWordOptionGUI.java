package ui.exams.gui;

import java.util.List;

public class MissingWordOptionGUI {
    private final String missingWord;
    private final List<String> options;

    public MissingWordOptionGUI(String missingWord, List<String> options) {
        this.missingWord = missingWord;
        this.options = options;
    }

    public String getMissingWord() {
        return missingWord;
    }

    public List<String> getOptions() {
        return options;
    }
}


