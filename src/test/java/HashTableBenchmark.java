
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashTableBenchmark {

    private static final Random random = new Random();

    private HashTable<Integer, Integer> hashTable;
    private int minSize;
    private int maxSize;
    private int step;

    public HashTableBenchmark(int minSize, int maxSize, int stepSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.step = stepSize;
        hashTable = new HashTable<>();
    }

    public int[][] start() {
        Map<Integer, Long> resultsForAdd = new HashMap<>();
        Map<Integer, Long> resultsForRemove = new HashMap<>();

        for (int tableSize = minSize; tableSize <= maxSize; tableSize += step) {
            Set<Integer> values = IntStream.range(0, tableSize).map(i -> random.nextInt(100)).boxed().collect(Collectors.toSet());
            long startTime = System.nanoTime();
            values.forEach(val -> hashTable.put(val, val));
            long addResult = System.nanoTime() - startTime;
            values.forEach(hashTable::remove);
            long removeResult = System.nanoTime() - startTime + addResult;
            resultsForAdd.put(tableSize, addResult);
            resultsForRemove.put(tableSize, removeResult);
        }
        return show(resultsForAdd, resultsForRemove);
    }

    private int[][] show(Map<Integer, Long> resultsForAdd, Map<Integer, Long> resultsForRemove) {
        return showGraphic(resultsForAdd);
    }

    private int[][] showGraphic(Map<Integer, Long> xAndY) {
        long maxValue = xAndY.values().stream().max(Long::compareTo).get();
        int maxTableSize = xAndY.keySet().stream().max(Integer::compareTo).get();
        int[][] rawGraphic = new int[(int) (maxValue / 10000) + 1][maxTableSize + 1];
        xAndY.forEach((key, value) -> rawGraphic[(int) (value / 10000)][key] = 1);

        xAndY.forEach((key, value) -> System.out.println("size=" + key + ", time=" + value));

        return rawGraphic;
    }
}