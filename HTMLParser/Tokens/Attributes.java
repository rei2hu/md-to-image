package HTMLParser.Tokens;

import java.util.Hashtable;
import java.lang.StringBuilder;
import java.util.Iterator;

public class Attributes implements java.lang.Iterable<Attribute> {    

   private Hashtable<String, Attribute> attributes;

    public Attributes() {
        attributes = new Hashtable<>();
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

    public void add(String key, String value) {
        Attribute a = new Attribute(key, value);
        attributes.put(key, a);
    }

    public Attribute get(String key) {
        return attributes.get(key);
    }
}
