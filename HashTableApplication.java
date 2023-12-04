import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTableApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int capacity = 1024; // Assuming fixed capacity for simplicity
        CustomHashTable hashTable = new CustomHashTable(capacity);

        while (true) {
            System.out.print("Enter file name (or 'Q' to quit): ");
            String fileName = scanner.nextLine();

            if (fileName.equalsIgnoreCase("Q")) {
                break;
            }

            try {
                File file = new File(fileName);
                Scanner fileScanner = new Scanner(file);

                int itemCount = 0;
                while (fileScanner.hasNextInt()) {
                    fileScanner.nextInt();
                    itemCount++;
                }

                if (itemCount < capacity * 0.75 || itemCount > capacity * 0.95) {
                    System.out.println("Error: Number of items in the file (" + itemCount + ") does not meet the specified criteria (n * 0.75 ≤ m ≤ n * 0.95).");
                    continue;
                }

                // Reset fileScanner to the beginning of the file
                fileScanner = new Scanner(file);

                // Now proceed with the insertion
                while (fileScanner.hasNextInt()) {
                    int key = fileScanner.nextInt();
                    hashTable.insert(key);
                }

                fileScanner.close();
                System.out.println("Total operations: " + hashTable.getTotalOperations());

                System.out.print("Do you want to print the hash table? (Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    hashTable.printTable();
                }

                System.out.print("Enter a key to search (or 'N' to skip): ");
                String searchKey = scanner.nextLine();
                if (!searchKey.equalsIgnoreCase("N")) {
                    int key = Integer.parseInt(searchKey);
                    int index = hashTable.search(key);
                    if (index >= 0) {
                        System.out.println("Key found at index: " + index);
                    } else {
                        System.out.println("Key not in the table.");
                    }
                }

                hashTable.reset();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + fileName);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
