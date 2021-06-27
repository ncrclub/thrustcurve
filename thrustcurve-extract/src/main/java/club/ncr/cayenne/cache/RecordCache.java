package club.ncr.cayenne.cache;

import club.ncr.cayenne.DAO;
import org.apache.cayenne.BaseDataObject;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public abstract class RecordCache<K extends Comparable<K>, V extends BaseDataObject> {

    private final DAO<V> dao;
    private Map<K, V> cache = new TreeMap<>();
    private boolean autoCreate;

    public abstract K key(V value);


    public RecordCache(DAO<V> dao, boolean autoCreate) {
        this.autoCreate = autoCreate;
        this.dao = dao;
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

    public Collection<V> update(Collection<V> values) {
        if (values != null) {
            values.forEach(v -> put(key(v), v));
        }
        return values;
    }

    public void refresh() {
        update(dao.getAll());
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
        if (key == null) { return null; }
        if (isEmpty()) { refresh(); }
        return cache.get(key);
    }

    public Collection<V> values() {
        return cache.values();
    }

    public int size() {
        return cache.size();
    }

    /*
    public CayenneDao<V> dao() {
        return dao;
    }
     */
}
