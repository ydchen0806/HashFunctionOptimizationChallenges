import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class CustomHashTable {
    private Integer[] table; // 哈希表数组
    private int capacity; // 哈希表的容量，即 n
    private int prime; // 辅助哈希函数的除数，即 m
    private int totalOperations; // 总操作步数

    // 构造函数
    public CustomHashTable(int capacity, int prime) {
        this.capacity = capacity;
        this.prime = prime;
        this.table = new Integer[capacity];
        this.totalOperations = 0;
    }

    // 主哈希函数
    private int hash(int key) {
        totalOperations++; // 每次哈希操作计为一步
        return key % capacity;
    }

    // 辅助哈希函数
    private int auxiliaryHash(int key) {
        totalOperations++; // 每次辅助哈希操作也计为一步
        return (key % prime) + 1;
    }

    // 探测函数
    private int probe(int key, int i) {
        if (i == 0) {
            return hash(key); // 第一次探测直接返回哈希值
        } else {
            int h = hash(key); // 主哈希值
            int g = auxiliaryHash(key); // 辅助哈希值
            totalOperations += 2; // 乘法和加法各计为一步操作
            return (h + i * g) % capacity; // 完整的探测公式
        }
    }
    // 添加一个新的方法来打印哈希表的内容
    public void printTable() {
        System.out.println("哈希表内容：");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("索引 " + i + ": " + table[i]);
            }
        }
    }
    
    // 插入键
    public void insert(int key) {
        int i = 0;
        int index = probe(key, i);
        while (table[index] != null) {
            i++; // 探测次数增加
            index = probe(key, i); // 探测新位置
            totalOperations++; // 每次比较是否为null计为一步操作
        }
        table[index] = key; // 插入键
        totalOperations++; // 插入操作计为一步
    }

    // 查找键
    public int search(int key) {
        int i = 0;
        int index = probe(key, i);
        while (table[index] != null && !table[index].equals(key)) {
            i++; // 探测次数增加
            index = probe(key, i); // 探测新位置
            totalOperations++; // 每次比较是否为目标键计为一步操作
        }
        if (table[index] == null) {
            return -1; // 键不存在于表中
        }
        return index; // 返回找到键的索引
    }

    // 获取总操作步数
    public int getTotalOperations() {
        return totalOperations;
    }

    // 重置哈希表
    public void reset() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        totalOperations = 0; // 操作步数重置
    }
}
