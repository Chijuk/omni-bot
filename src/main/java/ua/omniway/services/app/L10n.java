package ua.omniway.services.app;

import ua.omniway.bot.Emoji;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class L10n {
    private static final String STRINGS_FILE = "localization.strings";
    private static final Object lock = new Object();

    private static final List<Language> supportedLanguages = new ArrayList<>();
    private static final Utf8ResourceBundle defaultLanguage;
    private static final Utf8ResourceBundle english;
    private static final Utf8ResourceBundle ukrainian;
    public static final String DEFAULT_LANGUAGE_CODE = "uk";
    private static final Utf8ResourceBundle russian;
    private static final String DEFAULT_LANGUAGE_NAME = "Українська";
    private static final String DEFAULT_EMOJI = Emoji.UKRAINIAN.toString();

    static {
        synchronized (lock) {
            defaultLanguage = new Utf8ResourceBundle(STRINGS_FILE, Locale.ROOT);
            supportedLanguages.add(new Language(DEFAULT_LANGUAGE_CODE, DEFAULT_LANGUAGE_NAME, DEFAULT_EMOJI));

            english = new Utf8ResourceBundle(STRINGS_FILE, new Locale("en", "US"));
            supportedLanguages.add(new Language("en", "English", Emoji.ENGLISH.toString()));

            ukrainian = new Utf8ResourceBundle(STRINGS_FILE, new Locale("uk", "UA"));
            supportedLanguages.add(new Language("uk", "Українська", Emoji.UKRAINIAN.toString()));

            russian = new Utf8ResourceBundle(STRINGS_FILE, new Locale("ru", "RU"));
            supportedLanguages.add(new Language("ru", "Русский", Emoji.RUSSIAN.toString()));
        }
    }

    private L10n() {

    }

    /**
     * Get a string in default language (en)
     *
     * @param key key of the resource to fetch
     * @return fetched string or error message otherwise
     */
    public static String getString(String key) {
        String result;
        try {
            result = defaultLanguage.getString(key);
        } catch (MissingResourceException e) {
            result = "String not found";
        }

        return result;
    }

    /**
     * Get a string in optional language
     *
     * @param key key of the resource to fetch
     * @return fetched string or error message otherwise
     */
    public static String getString(String key, String language) {
        String result;
        try {
            switch (language.toLowerCase()) {
                case "en":
                    result = english.getString(key);
                    break;
                case "uk":
                    result = ukrainian.getString(key);
                    break;
                case "ru":
                    result = russian.getString(key);
                    break;
                default:
                    result = defaultLanguage.getString(key);
                    break;
            }
        } catch (MissingResourceException e) {
            result = defaultLanguage.getString(key);
        }

        return result;
    }

    public static List<Language> getSupportedLanguages() {
        return supportedLanguages.stream().distinct().collect(Collectors.toList());
    }

    public static Language getLanguageByCode(String languageCode) {
        return supportedLanguages.stream().filter(x -> x.getCode().equals(languageCode)).findFirst().orElse(null);
    }

    public static Language getLanguageByName(String languageName) {
        return supportedLanguages.stream().filter(x -> x.getName().equals(languageName)).findFirst().orElse(null);
    }

    public static String getLanguageCodeByName(String language) {
        return supportedLanguages.stream().filter(x -> x.getName().equals(language))
                .map(Language::getCode).findFirst().orElse(null);
    }

    public static class Language {
        private String code;
        private String name;
        private String emoji;

        public Language(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public Language(String code, String name, String emoji) {
            this(code, name);
            this.emoji = emoji;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmoji() {
            return emoji;
        }

        public void setEmoji(String emoji) {
            this.emoji = emoji;
        }

        @Override
        public String toString() {
            if (emoji == null || emoji.isEmpty()) {
                return name + ", " + code;
            } else {
                return emoji + " " + name + ", " + code;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Language language = (Language) o;
            return Objects.equals(code, language.code) && Objects.equals(name, language.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, name);
        }
    }

    private static class Utf8ResourceBundle extends ResourceBundle {

        private static final String BUNDLE_EXTENSION = "properties";
        private static final Control UTF8_CONTROL = new UTF8Control();

        Utf8ResourceBundle(String bundleName, Locale locale) {
            setParent(ResourceBundle.getBundle(bundleName, locale, UTF8_CONTROL));
        }

        @Override
        protected Object handleGetObject(String key) {
            return parent.getObject(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return parent.getKeys();
        }


        private static class UTF8Control extends Control {
            @Override
            public ResourceBundle newBundle
                    (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                    throws IllegalAccessException, InstantiationException, IOException {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
                ResourceBundle bundle = null;
                InputStream stream = null;
                if (reload) {
                    URL url = loader.getResource(resourceName);
                    if (url != null) {
                        URLConnection connection = url.openConnection();
                        if (connection != null) {
                            connection.setUseCaches(false);
                            stream = connection.getInputStream();
                        }
                    }
                } else {
                    stream = loader.getResourceAsStream(resourceName);
                }
                if (stream != null) {
                    try {
                        bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
                    } finally {
                        stream.close();
                    }
                }
                return bundle;
            }
        }
    }
}
