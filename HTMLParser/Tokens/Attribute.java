package HTMLParser.Tokens;

import java.util.Objects;

public class Attribute {
    String key;
    String value;

    public Attribute(String k, String v) {
        key = k;
        value = v;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return key + "=" + value;
    }

    public int hashCode() {
        return Objects.hashCode(key);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Attribute)) return false;
        return key.equals(((Attribute) o).key);
    }
}


