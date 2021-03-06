package De.Hpi.Disco.Source.aggregation;

import De.Hpi.Disco.Source.utility.Event;

public abstract class BaseWindowAggregate<T> {
    protected final String windowType;
    protected final T value;
    protected final int key;

    public static String DELIMITER = ":";

    protected BaseWindowAggregate(String windowType, T value) {
        this(windowType, value, Event.NO_KEY);
    }

    protected BaseWindowAggregate(String windowType, T value, int key) {
        this.windowType = windowType;
        this.value = value;
        this.key = key;
    }

    public String getWindowType() {
        return windowType;
    }

    public T getValue() {
        return value;
    }

    public boolean equalsString(String actualWindow) {
        String[] windowParts = actualWindow.split(DELIMITER);
        if (windowParts.length != 3) return false;
        if (!windowParts[0].equals(windowType)) return false;
        if (!valueAsString().equals(windowParts[1])) return false;
        return key == Event.NO_KEY || key == Integer.parseInt(windowParts[2]);
    }

    public String asString() {
        return this.windowType + DELIMITER + this.valueAsString() + DELIMITER + this.key;
    }

    abstract public String valueAsString();

    @Override
    public String toString() {
        return "BaseWindowAggregate{" +
                "windowType=" + windowType +
                ", value=" + value +
                ", (key=" + (key == Event.NO_KEY ? "none" : key) + ")" +
                '}';
    }
}
