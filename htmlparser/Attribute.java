package htmlparser;

import java.util.Objects;

public class Attribute {

    private String key;
    private String value;

    Attribute(String k, String v) {
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
        // NO
        if (!(o instanceof Attribute)) return false;
        return key.equals(((Attribute) o).key);
    }
}


