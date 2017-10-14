package htmlparser;

import java.util.Hashtable;
import java.lang.StringBuilder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is for representing the different attributes a tag can have
 */

public class Attributes implements java.lang.Iterable<Attribute> {

    private Hashtable<String, Attribute> attributes;
    // private static Pattern styleRegex = Pattern.compile("style=((\".*?\")|('.*?'))");
    private static Pattern attributesRegex = Pattern.compile("(.+?=(?:(?:\".*?\")|(?:'.*?')))");
    // private static Pattern srcRegex = Pattern.compile("src=((\".*?\")|('.*?'))");

    /**
     * Default constructor
     */
    public Attributes() {
        attributes = new Hashtable<>();
    }

    /**
     * A constructor that takes a string in the form key=value key=value... with some sort of delimiter
     * @param keyvalues the string of key values
     */
    public Attributes(String keyvalues) {
        attributes = new Hashtable<>();
        // cant split by space because attributes can have space in them
        // well right now we'll only care about style="..." so maybe only match that
        // try this regex maybe
        // style=((".*?")|('.*?'))
        /*
        Matcher m = styleRegex.matcher(keyvalues);
        if (!m.matches()) return;
        */
        Matcher m = attributesRegex.matcher(keyvalues);
        while (m.find()) {
            String[] keyvalue = m.group().trim().split("=");
            if (keyvalue[0].equals("style")) {
                String[] declarations = keyvalue[1].substring(1, keyvalue[1].length() - 1).split(";");
                for (String s: declarations) {
                    String[] styleValue = s.split(":", -1);
                    if (styleValue.length < 2)
                        add(styleValue[0], "");
                    else
                        add(styleValue[0], styleValue[1]);
                }
            } else {
                add(keyvalue[0], keyvalue[1]);
            }
        }
    }

    /**
     * Get's an iterator for this object
     * @return An iterator over each attribute.
     */
    public Iterator<Attribute> iterator() {
        return attributes.values().iterator();
    }

    /**
     * Basic to string method for debugging really
     * @return a string in the from key=value key=value...
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Attribute a: attributes.values()) {
            sb.append(" ").append(a.toString());
        }
        return sb.toString();
    }

    /**
     * Add an attribute
     * @param key The attribute's key
     * @param value The attribute's value
     */
    public void add(String key, String value) {
        Attribute a = new Attribute(key, value);
        attributes.put(key, a);
    }

    /**
     * Get an attribute with it's key
     * @param key the key to find
     * @return the attribute
     */
    public Attribute get(String key) {
        return attributes.get(key);
    }
}
