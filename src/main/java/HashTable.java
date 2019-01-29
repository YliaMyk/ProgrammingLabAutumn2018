

import java.util.*;

public class HashTable<K, V> implements Map<K, V> {

    private static final int DEFAULT_SIZE = 31;
    private int size;
    private Object[] storage;
    private Set<K> keySet;

    public HashTable() {
        this(DEFAULT_SIZE);
    }

    public HashTable(int size) {
        this.size = 0;
        storage = new Object[size];
        keySet = new HashSet<>();
    }

    private int doubleHash(int hash, int shift) {
        return hash + shift * ((hash % (size - 1)) * 11) + 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int shift = 0;
        int hash = doubleHash(key.hashCode(), shift);
        while (hash < storage.length && storage[hash] == null) {
            hash = doubleHash(key.hashCode(), shift);
        }
        return storage.length > hash && storage[hash] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Object o : storage) {
            if (o != null && o.equals(value)) return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (!keySet.contains(key)) return null;


        int hash = getHash(key);
        return storage.length > hash ? (V) storage[hash] : null;
    }

    private int getHash(Object key) {
        int shift = 0;
        int hash = doubleHash(key.hashCode(), shift);

        while (hash < storage.length && storage[hash] == null) {
            shift++;
            hash = doubleHash(key.hashCode(), shift);
        }
        return hash;
    }

    @Override
    public V put(K key, V value) {
        int shift = 0;
        int hash = doubleHash(key.hashCode(), shift);
        if (hash >= storage.length) resize(hash + 1);

        while (storage[hash] != null) {
            shift++;
            hash = doubleHash(key.hashCode(), shift);
            if (hash >= storage.length) {
                resize(hash + 1);
                break;
            }
        }
        keySet.add(key);
        storage[hash] = value;
        return value;
    }

    private void resize(int size) {
        storage = Arrays.copyOf(storage, size);
    }

    @Override
    public V remove(Object key) {
        int hash = getHash(key);

        keySet.remove(key);

        V removed = storage.length > hash ? (V) storage[hash] : null;
        storage[hash] = null;
        return removed;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        storage = new Object[DEFAULT_SIZE];
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        for (Object o : storage) {
            if (o != null) {
                list.add((V) o);
            }
        }
        return list;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new HashSet<>();

        keySet.forEach(key -> {
            entries.add(new Entry<>(key, get(key)));
        });

        return entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTable)) return false;
        HashTable<?, ?> hashTable = (HashTable<?, ?>) o;
        return size == hashTable.size &&
                Arrays.equals(storage, hashTable.storage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(storage);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                sb.append(i).append(": ").append(storage[i]).append(", ");
            }
        }
        return "{" + sb.substring(0, sb.length() - 2) + "}";
    }

    class Entry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
    }

}
