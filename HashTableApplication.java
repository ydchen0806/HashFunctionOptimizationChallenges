import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTableApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 获取用户输入的哈希表容量
        System.out.print("请输入哈希表的容量（建议值1024）: ");
        int capacity = scanner.nextInt();

        // 获取用户输入的质数，用于辅助哈希函数
        System.out.print("请输入用于辅助哈希函数的质数（应小于哈希表容量）: ");
        // 通常，质数应该接近但不等于哈希表容量的一半。这样选择是为了尽可能减少因模运算导致的周期性模式，这些模式可能会在不同键值之间产生冲突。
        int prime = scanner.nextInt();

        // 创建哈希表实例，传入容量和质数
        CustomHashTable hashTable = new CustomHashTable(capacity, prime);

        scanner.nextLine(); // 清除缓冲区的换行符

        while (true) {
            System.out.print("请输入文件名（或输入 'Q' 退出）: ");
            String fileName = scanner.nextLine();

            if (fileName.equalsIgnoreCase("Q")) {
                break; // 如果用户输入 'Q'，退出循环
            }

            try {
                File file = new File(fileName);
                Scanner fileScanner = new Scanner(file);

                int itemCount = 0;
                // 第一次扫描文件，计算文件中的整数数量
                while (fileScanner.hasNextInt()) {
                    fileScanner.nextInt();
                    itemCount++;
                }

                // 检查文件中的整数数量是否在指定的范围内
                if (itemCount < capacity * 0.75 || itemCount > capacity * 0.95) {
                    System.out.println("错误：文件中的项目数 (" + itemCount + ") 未满足指定条件 (n * 0.75 ≤ m ≤ n * 0.95)。");
                    continue;
                }

                // 重置文件扫描器，准备重新读取文件以插入键到哈希表
                fileScanner = new Scanner(file);

                try {
                    // 从文件中读取键并插入到哈希表中
                    while (fileScanner.hasNextInt()) {
                        int key = fileScanner.nextInt();
                        hashTable.insert(key);
                    }
                } finally {
                    fileScanner.close(); // 确保文件扫描器被关闭
                }

                // 打印哈希表的总操作步数
                System.out.println("总操作步数: " + hashTable.getTotalOperations());

                System.out.print("是否打印哈希表？(Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    hashTable.printTable(); // 如果用户选择 'Y'，打印哈希表
                }

                System.out.print("输入要搜索的键（或输入 'N' 跳过）: ");
                String searchKey = scanner.nextLine();
                if (!searchKey.equalsIgnoreCase("N")) {
                    int key = Integer.parseInt(searchKey);
                    int index = hashTable.search(key); // 搜索键
                    if (index >= 0) {
                        System.out.println("键值 " + key + " 找到于索引: " + index);
                    } else {
                        System.out.println("键值 " + key + " 不在哈希表中。");
                    }
                }

                hashTable.reset(); // 重置哈希表以进行下一轮操作
            } catch (FileNotFoundException e) {
                System.out.println("文件未找到: " + fileName);
            } catch (Exception e) {
                System.out.println("发生错误: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
