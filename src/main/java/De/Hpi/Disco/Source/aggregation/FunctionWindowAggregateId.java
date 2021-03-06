package De.Hpi.Disco.Source.aggregation;

import De.Hpi.Disco.Source.utility.Event;
import de.tub.dima.scotty.core.WindowAggregateId;

import java.util.Objects;

public class FunctionWindowAggregateId {
    public final static int NO_CHILD_ID = -1;

    private final WindowAggregateId windowId;
    private final int functionId;
    private final int childId;
    private final int key;

    public FunctionWindowAggregateId(WindowAggregateId windowId, int functionId) {
        this(windowId, functionId, NO_CHILD_ID);
    }

    public FunctionWindowAggregateId(WindowAggregateId windowId, int functionId, int childId) {
        this(windowId, functionId, childId, Event.NO_KEY);
    }

    public FunctionWindowAggregateId(WindowAggregateId windowId, int functionId, int childId, int key) {
        this.windowId = windowId;
        this.functionId = functionId;
        this.childId = childId;
        this.key = key;
    }

    public FunctionWindowAggregateId(FunctionWindowAggregateId functionWindowAggregateId, int childId) {
        this(functionWindowAggregateId, childId, Event.NO_KEY);
    }

    public FunctionWindowAggregateId(FunctionWindowAggregateId functionWindowAggregateId, int childId, int key) {
        this(functionWindowAggregateId.getWindowId(), functionWindowAggregateId.getFunctionId(), childId, key);
    }

    public static FunctionWindowAggregateId sessionStartId(long windowId, long sessionStart, int childId, int key) {
        WindowAggregateId windowAggregateId = new WindowAggregateId(windowId, sessionStart, sessionStart);
        return new FunctionWindowAggregateId(windowAggregateId, 0, childId, key);
    }

    public FunctionWindowAggregateId keylessCopy() {
        return new FunctionWindowAggregateId(this, NO_CHILD_ID, Event.NO_KEY);
    }

    public FunctionWindowAggregateId withKey(int key) {
        return new FunctionWindowAggregateId(this, this.childId, key);
    }

    public FunctionWindowAggregateId withChildId(int childId) {
        return new FunctionWindowAggregateId(this, childId, this.key);
    }

    public WindowAggregateId getWindowId() {
        return windowId;
    }

    public int getFunctionId() {
        return functionId;
    }

    public int getChildId() {
        return childId;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "FunctionWindowAggregateId{" +
                "windowId=" + windowId +
                ", functionId=" + functionId +
                ", (childId=" + childId + ")" +
                ", (key=" + key + ")" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FunctionWindowAggregateId that = (FunctionWindowAggregateId) o;
        return functionId == that.functionId &&
                key == that.key &&
                Objects.equals(windowId, that.windowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowId, functionId, key);
    }
}
