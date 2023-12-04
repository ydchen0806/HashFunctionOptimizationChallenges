import random
import math

def generate_key_with_exact_distribution(length, digit_counts,keys):
    key = ""
    while True:
        
        digits = []
        for i in range(length):
            available_digits = [i for i, count in enumerate(digit_counts[i]) if count > 0]
            digit = random.choice(available_digits)
            key += str(digit)
            digits.append(digit)
        if key not in keys:
            for i, digit in enumerate(digits):
                digit_counts[i][digit] -= 1
            break
        else:
            key = ""
    return key


def calculate_digit_counts(probabilities, num_keys):
    L = len(probabilities)
    counts = [[0 for _ in range(10)] for _ in range(L)]
    total_per_row = [0 for _ in range(L)]

    for i in range(L):
        row_probabilities = probabilities[i]
        for j in range(10):
            counts[i][j] = math.floor(row_probabilities[j] * num_keys)
            total_per_row[i] += counts[i][j]

        adjust_counts_if_needed(counts[i], row_probabilities, total_per_row[i], num_keys)

    return counts

def adjust_counts_if_needed(counts, probabilities, current_total, expected_total):
    difference = expected_total - current_total

    while difference != 0:
        remaining_probabilities = [prob - (count / expected_total) for prob, count in zip(probabilities, counts)]
        max_prob = max(remaining_probabilities)
        max_prob_indices = [i for i, prob in enumerate(remaining_probabilities) if prob == max_prob]

        index = random.choice(max_prob_indices)
        if difference > 0 and counts[index] < expected_total:
            counts[index] += 1
            difference -= 1
        elif difference < 0 and counts[index] > 0:
            counts[index] -= 1
            difference += 1

def generate_data_file(file_name, key_length, probabilities, num_keys):
    keys = set()
    digit_counts = calculate_digit_counts(probabilities, num_keys)

    while len(keys) < num_keys:
        key = generate_key_with_exact_distribution(key_length, digit_counts, keys)
        keys.add(key)
     
           

    with open(file_name, 'w') as file:
        for key in keys:
            file.write(key + "\n")


if __name__ == '__main__':
    # 示例概率分布和参数
    probabilities = [
        [1/3 ,0 ,0 ,0 ,0 ,1/3 ,0 ,1/3 ,0, 0], # 每个数字的概率
        [0.1] * 10,
        [0.2, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.5, 0, 0],
    ]

    key_length = 3 # 密钥的长度
    num_keys = 20 # 生成密钥的数量( 注意不能太多，否则会生成不出来，因为概率分布的问题)

    generate_data_file("test_data.txt", key_length, probabilities, num_keys)
