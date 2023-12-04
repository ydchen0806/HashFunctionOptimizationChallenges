# 哈希表应用程序

这个项目包括一个简单的哈希表实现，一个用于生成测试数据的数据生成器，以及一个应用程序来展示哈希表的插入和查找功能。

## 文件说明

- `CustomHashTable.java`: 实现了哈希表的基本功能。
- `HashTableApplication.java`: 用户界面，用于从文件中读取数据并操作哈希表。
- `DataGenerator.java`: 用于生成符合特定条件的测试数据文件。

## 编译方法

1. 确保您的计算机上安装了 Java Development Kit (JDK)。
2. 打开终端或命令提示符，导航到包含这些文件的目录。
3. 运行以下命令来编译所有文件：
javac CustomHashTable.java HashTableApplication.java DataGenerator.java


## 运行方法

### 生成测试数据

1. 运行 `DataGenerator` 来创建测试数据文件。运行以下命令：
java DataGenerator

这将在当前目录下创建一个名为 `testData.txt` 的文件。

### 运行哈希表应用程序

1. 运行 `HashTableApplication` 来使用测试数据文件：
java HashTableApplication

2. 当程序提示您输入文件名时，输入 `testData.txt`。
3. 按照程序提示进行操作，例如查看哈希表或搜索特定的键。

## 注意事项

- 确保在运行 `HashTableApplication` 之前已经运行了 `DataGenerator` 以生成数据文件。
- `DataGenerator` 可以多次运行以生成不同的测试数据。

