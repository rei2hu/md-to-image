package htmlparser;

import java.util.Objects;

/**
 * This class represents a single attribute a tag can have
 */

public class Attribute {

    private String key;
    private String value;

    /**
     * The constructor of an attribute
     * @param k The key
     * @param v The value
     */
    Attribute(String k, String v) {
        key = k;
        value = v;
    }

    /**
     * Get the value of this attribute
     * @return The value of this attribute
     */
    public String getValue() {
        return value;
    }

    /**
     * A basic to string method
     * @return key=string
     */
    public String toString() {
        return key + "=" + value;
    }

    /**
     * For hashtable things
     * @return The hash of the object;
     */
    public int hashCode() {
        return Objects.hashCode(key);
    }

    /**
     * Determines if an object is equal to this attribute
     * @param o The object to compare
     * @return True if they have the same key, false otherwise
     */
    public boolean equals(Object o) {
        // NO
        if (!(o instanceof Attribute)) return false;
        return key.equals(((Attribute) o).key);
    }
}


