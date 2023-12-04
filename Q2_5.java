public class HashTableSimulation {
    public static void main(String[] args) {
        int[] keys = {44, 13, 16, 24, 18, 6, 21};
        int[] finalState = {44, 13, -1, -1, 16, 24, 18, 6, -1, -1, 21}; // -1 represents null
        int[] hashTable = new int[11];
        
        // Initialize hash table with -1 (representing null)
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = -1;
        }
        
        // Try to insert each key and build the hash table
        for (int key : keys) {
            boolean inserted = linearProbingInsert(hashTable, key);
            if (!inserted) {
                System.out.println("The given final state cannot be achieved with the keys provided.");
                return;
            }
        }
        
        // Check if the generated hash table matches the final state
        if (matchesTable(hashTable, finalState)) {
            System.out.println("The keys can be inserted to achieve the given final state.");
        } else {
            System.out.println("The given final state cannot be achieved with the keys provided.");
        }
    }

    // Linear probing insert function
    public static boolean linearProbingInsert(int[] table, int key) {
        int index = key % table.length;
        int startIndex = index;
        do {
            if (table[index] == -1) { // If slot is empty
                table[index] = key;
                return true;
            }
            index = (index + 1) % table.length;
        } while (index != startIndex); // Check if we've come full circle
        
        return false; // Table is full and key cannot be inserted
    }

    // Function to check if the current state of the table matches the given final state
    public static boolean matchesTable(int[] table, int[] finalState) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != finalState[i]) {
                return false;
            }
        }
        return true;
    }
}
