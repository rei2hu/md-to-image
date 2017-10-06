package htmlparser.tokens;

import java.util.Hashtable;
import java.lang.StringBuilder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Attributes implements java.lang.Iterable<Attribute> {

    private Hashtable<String, Attribute> attributes;
    // private static Pattern styleRegex = Pattern.compile("style=((\".*?\")|('.*?'))");
    private static Pattern attributesRegex = Pattern.compile("(.+?=(?:(?:\".*?\")|(?:'.*?')))");
    // private static Pattern srcRegex = Pattern.compile("src=((\".*?\")|('.*?'))");

    Attributes() {
        attributes = new Hashtable<>();
    }

    Attributes(String keyvalues) {
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
        /*
        for (int i = 0; i < m.groupCount(); i++) {
            // this is xxxx="something";
            // System.out.println(m.group(i));
            System.out.println(m.group(i));
            String[] keyvalue = m.group(i).split("=");
            if (keyvalue[0].equalsIgnoreCase("style")) {
                String[] declarations = keyvalue[1].substring(1, keyvalue[1].length() - 1).split(";");
                for (String s: declarations) {
                    String[] styleValue = s.split(":");
                    add(styleValue[0], styleValue[1]);
                }
            } else if (keyvalue[0].equalsIgnoreCase("src")) {
                add(keyvalue[0], keyvalue[1]);
            }
        }

//        return;
//        String[] declarations = styleDeclaration.split("=");
//        // now we have
//        // declarations[0]: style=
//        // declarations[1]: "margin-left: 18pt;"
//        if (declarations.length < 2) {
//            // some error
//        } else {
//            declarations = declarations[1].substring(1, declarations[1].length() - 1).split(";");
//            for (String s: declarations) {
//                String[] keyvalue = s.split(":");
//                add(keyvalue[0].trim(), keyvalue[1].trim());
//            }
//
//        }
        */
    }

    public Iterator<Attribute> iterator() {
        return attributes.values().iterator();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Attribute a: attributes.values()) {
            sb.append(" ").append(a.toString());
        }
        return sb.toString();
    }

    void add(String key, String value) {
        Attribute a = new Attribute(key, value);
        attributes.put(key, a);
    }

    public Attribute get(String key) {
        return attributes.get(key);
    }
}
