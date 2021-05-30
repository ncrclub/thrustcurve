package club.ncr.motors;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public abstract class CayenneRecordCache<K extends Comparable<K>, V extends BaseDataObject> {

    public abstract Collection<V> getAll();
    public abstract K key(V value);

    private Map<K, V> cache = new TreeMap<>();

    private boolean autoCreate;
    private final ObjectContext ctx;

    public CayenneRecordCache(ObjectContext ctx, boolean autoCreate) {
        this.autoCreate = autoCreate;
        this.ctx = ctx;
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("Key in map cannot be null. (value="+ value+")");
        }
        if (value == null) {
            throw new RuntimeException("Value in map cannot be null (key="+ key +")");
        }
        cache.put(key, value);
    }


    public void refresh() {
        final Map<K, V> cache= new TreeMap<>();
        for (V val : getAll()) {
            cache.put(key(val), val);
        }
        this.cache = cache;
    }

    public void setAutoCreate(boolean autoCreate) {
        this.autoCreate = autoCreate;
    }

    public boolean autoCreate() {
        return autoCreate;
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public V get(K key) {
        if (isEmpty()) {
            refresh();
        }
        return cache.get(key);
    }

    public Collection<V> values() {
        return cache.values();
    }

    protected ObjectContext context() {
        return ctx;
    }
}
