package model;


import java.util.Locale;
import java.util.ResourceBundle;

public class MessageService {
    private static final String BASE_NAME = "i18n.global.messages";

    public String getMessage(String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
        return bundle.getString(key);
    }
}