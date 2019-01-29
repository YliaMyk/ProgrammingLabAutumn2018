
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashTableTest {

    private static final int SIZE = 10;
    private static final Random random = new Random();

    @Test
    void addTest() {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> table = new HashTable<>();

        IntStream.range(0, SIZE).map(i -> random.nextInt(100)).forEach(key -> {
            String value = String.valueOf(random.nextInt());
            table.put(key, value);
            map.put(key, value);
        });


        assertEquals(new HashSet<>(map.values()), new HashSet<>(table.values()));
    }

    @Test
    void removeTest() {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> table = new HashTable<>();
        List<Integer> keys = IntStream.range(0, SIZE).map(i -> random.nextInt(100)).boxed().collect(Collectors.toList());
        keys.forEach(key -> {
            String value = String.valueOf(key);
            map.put(key, value);
            table.put(key, value);
        });

        keys.subList(0, random.nextInt(SIZE - 1)).forEach(val -> {
            table.remove(val);
            map.remove(val);
        });

        assertEquals(new HashSet<>(map.values()), new HashSet<>(table.values()));
    }

    @Test
    void containsTest() {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> table = new HashTable<>();

        IntStream.range(0, SIZE).map(i -> random.nextInt(100)).forEach(key -> {
            String value = String.valueOf(random.nextInt());
            table.put(key, value);
            map.put(key, value);
        });

        map.forEach((key, value) -> {
            assertTrue(table.containsKey(key));
            assertTrue(table.containsValue(value));
        });
    }

    @Test
    void getTest() {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> table = new HashTable<>();
        List<Integer> keys = IntStream.range(0, SIZE).map(i -> random.nextInt(100)).boxed().collect(Collectors.toList());
        keys.forEach(key -> {
            String value = String.valueOf(key);
            map.put(key, value);
            table.put(key, value);
        });

        map.forEach((key, value) -> assertEquals(map.get(key), table.get(key)));
    }

}
