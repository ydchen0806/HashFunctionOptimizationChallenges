import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class DataGenerator {
    private static final Random random = new Random();
    // 根据给定的概率分布生成单个数字
    // private static int generateDigit(double[] probabilities, Random random) {
    //     double p = random.nextDouble();
    //     double cumulativeProbability = 0.0;
    //     for (int i = 0; i < probabilities.length; i++) {
    //         cumulativeProbability += probabilities[i];
    //         if (p <= cumulativeProbability) {
    //             return i;
    //         }
    //     }
    //     return probabilities.length - 1; // 如果概率分布有问题，返回最后一个数字
    // }

    // 生成一个特定长度和概率分布的密钥
    // private static String generateKey(int length, double[][] probabilities, Random random) {
    //     StringBuilder keyBuilder = new StringBuilder();
    //     for (int i = 0; i < length; i++) {
    //         int digit = generateDigit(probabilities[i], random);
    //         keyBuilder.append(digit);
    //     }
    //     return keyBuilder.toString();
    // }

    // 生成指定数量的唯一密钥，并写入到文件中
    // public static void generateDataFile(String fileName, int keyLength, double[][] probabilities, int numKeys) throws IOException {
    //     Random random = new Random();
    //     Set<String> keys = new HashSet<>();

    //     while (keys.size() < numKeys) {
    //         String key;
    //         do {
    //             key = generateKey(keyLength, probabilities, random);
    //         } while (!keys.add(key)); // 确保每个生成的密钥是唯一的
    //     }

    //     // 将生成的密钥写入文件
    //     try (FileWriter writer = new FileWriter(fileName)) {
    //         for (String key : keys) {
    //             writer.write(key + "\n");
    //         }
    //     }
    // }
    private static void generateDataFile(String fileName, int keyLength, double[][] probabilities, int numKeys) throws IOException {
        Random random = new Random();
        Set<String> keys = new HashSet<>();

        // 预先计算每个数字应该出现的次数
        int[][] digitCounts = calculateDigitCounts(probabilities, numKeys);

        while (keys.size() < numKeys) {
            String key = generateKeyWithExactDistribution(keyLength, digitCounts, random);
            keys.add(key); // 确保每个生成的密钥是唯一的
        }

        // 将生成的密钥写入文件
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String key : keys) {
                writer.write(key + "\n");
            }
        }
    }

    private static int[][] calculateDigitCounts(double[][] probabilities, int numKeys) {
        int L = probabilities.length;
        int[][] counts = new int[L][10];
        int[] totalPerRow = new int[L]; // 每行的总计数
    
        for (int i = 0; i < L; i++) {
            double[] rowProbabilities = probabilities[i];
            for (int j = 0; j < 10; j++) {
                counts[i][j] = (int) Math.round(rowProbabilities[j] * numKeys);
                totalPerRow[i] += counts[i][j];
            }
    
            // 确保每行的总计数等于密钥数量
            adjustCountsIfNeeded(counts[i], rowProbabilities, totalPerRow[i], numKeys);
        }
        return counts;
    }
    
    
    private static void adjustCountsIfNeeded(int[] counts, double[] probabilities, int currentTotal, int expectedTotal) {
        Random random = new Random();
        int difference = expectedTotal - currentTotal;

        while (difference != 0) {
            // 计算每个数字的剩余概率
            double[] remainingProbabilities = new double[counts.length];
            for (int i = 0; i < counts.length; i++) {
                remainingProbabilities[i] = probabilities[i] - ((double) counts[i] / expectedTotal);
            }

            // 找出剩余概率最高的数字
            double maxProb = Arrays.stream(remainingProbabilities).max().getAsDouble();
            List<Integer> maxProbIndices = new ArrayList<>();
            for (int i = 0; i < remainingProbabilities.length; i++) {
                if (remainingProbabilities[i] == maxProb) {
                    maxProbIndices.add(i);
                }
            }

            // 从概率最高的数字中随机选择一个
            int index = maxProbIndices.get(random.nextInt(maxProbIndices.size()));

            if (difference > 0 && counts[index] < expectedTotal) {
                counts[index]++;
                difference--;
            } else if (difference < 0 && counts[index] > 0) {
                counts[index]--;
                difference++;
            }
        }
    }

    

    // 生成密钥，确保每个数字出现次数符合预计的分布
    private static String generateKeyWithExactDistribution(int length, int[][] digitCounts, Random random) {
        StringBuilder keyBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = randomDigitWithCount(digitCounts[i], random);
            keyBuilder.append(digit);
        }
        return keyBuilder.toString();
    }

    // 随机生成一个数字，考虑到每个数字的剩余可用次数
    private static int randomDigitWithCount(int[] counts, Random random) {
        List<Integer> availableDigits = new ArrayList<>();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                availableDigits.add(i);
            }
        }
        int index = random.nextInt(availableDigits.size());
        int digit = availableDigits.get(index);
        counts[digit]--;
        return digit;
    }

    // 验证概率分布是否合法（每位数字概率总和为1）
    private static boolean validateProbabilities(double[][] probabilities) {
        for (double[] distribution : probabilities) {
            double sum = 0;
            for (double p : distribution) {
                sum += p;
            }
            if (Math.abs(sum - 1.0) > 0.00001) { // 允许一定的误差
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入密钥长度 (L): ");
        int L = scanner.nextInt();
        System.out.println("输入要生成的密钥数量: ");
        int numKeys = scanner.nextInt();

        double[][] probabilities = new double[L][10];
        for (int i = 0; i < L; i++) {
            System.out.println("输入第 " + (i + 1) + " 位的概率（10个数字，可以是分数，例如 '1/3'，用空格分隔）:");
            scanner.nextLine(); // 清空缓冲区

            String[] probabilityInputs = scanner.nextLine().split(" ");
            if (probabilityInputs.length != 10) {
                System.out.println("输入错误，每位数字的概率必须是10个。");
                i--; // 让用户重新输入这一行
                continue;
            }

            for (int j = 0; j < 10; j++) {
                probabilities[i][j] = parseProbability(probabilityInputs[j]);
            }
        }

        if (!validateProbabilities(probabilities)) {
            System.out.println("概率总和不为 1，请重新输入概率分布。");
            return;
        }

        try {
            generateDataFile("testData.txt", L, probabilities, numKeys);
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    // 解析概率字符串，支持分数输入
    private static double parseProbability(String probabilityStr) {
        if (probabilityStr.contains("/")) {
            String[] parts = probabilityStr.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        } else {
            return Double.parseDouble(probabilityStr);
        }
    }
}