package HTMLParser.Tokens;

class Attributes {    

    public String attribute;
    public String value;

    public Attributes(String a, String v) {
        attribute = a;
        value = v;
    }

    public String toString() {
        return attribute + "=" + value;
    }

}
