package code.shubham.commons;

public class ConfigUtils {

    public static String get(String key, String defaultValue) {
        return System.getenv(key) == null
                ? System.getProperty(key) == null ? defaultValue : System.getProperty(key)
                : System.getenv(key);
    }

}
