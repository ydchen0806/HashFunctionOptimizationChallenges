import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class CustomHashTable {
    private Integer[] table;
    private int capacity;
    private int totalOperations;

    public CustomHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Integer[capacity];
        Arrays.fill(this.table, null);
        this.totalOperations = 0;
    }

    private int hashFunction(int key) {
        return key % capacity; // Simple modular hash function
    }

    private int probingFunction(int key, int probeNumber) {
        // Linear probing
        return (hashFunction(key) + probeNumber) % capacity;
    }

    public void insert(int key) {
        int probeNumber = 0;
        while (probeNumber < capacity) {
            int index = probingFunction(key, probeNumber);
            if (table[index] == null) {
                table[index] = key;
                totalOperations++;
                return;
            }
            probeNumber++;
            totalOperations++;
        }
        throw new IllegalStateException("Hash Table is full");
    }

    public int search(int key) {
        int probeNumber = 0;
        while (probeNumber < capacity) {
            int index = probingFunction(key, probeNumber);
            if (table[index] != null && table[index] == key) {
                return index;
            }
            probeNumber++;
        }
        return -1; // Key not found
    }

    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.println("Index: " + i + ", Key: " + table[i]);
            }
        }
    }

    public int getTotalOperations() {
        return totalOperations;
    }

    public void reset() {
        Arrays.fill(this.table, null);
        this.totalOperations = 0;
    }
}
