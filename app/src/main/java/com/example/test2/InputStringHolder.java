package com.example.test2;

public class InputStringHolder {
    private static InputStringHolder instance = null;
    private String inputString;

    private InputStringHolder() {}

    public static InputStringHolder getInstance() {
        if (instance == null) {
            instance = new InputStringHolder();
        }
        return instance;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }
}
