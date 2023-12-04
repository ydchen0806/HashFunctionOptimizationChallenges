# HashFunctionOptimizationChallenges
Hash problem

# Computer Science Problems: Hash Tables and Hashing Functions

## Overview
This problem set delves into the concepts of hash tables and hashing functions. Participants are required to write programs and provide “paper-and-pencil” solutions. Any programming language can be used, provided it is freely and easily installable on both Mac and Windows platforms.

### Submission Requirements
- **Correct Code**: Submit all code files, including headers for languages like C++. Include all necessary import statements.
- **Test Data**: Provide test data used for your code.
- **Documentation**: Include clear documentation and instructions. Avoid including personal information in comments or file names.

### File Specifications
- Each file should start with comments indicating the problem number, programming language, development framework, and platform used.
- Instructions for running the program, including how to call functions and pass inputs, should be clear.
- The source code should be well-organized, with clear separation between problems, meaningful names for variables and functions, minimal code repetition, and efficient use of storage structures and programming constructs.

## Main Problem Description
The primary challenge involves constructing custom-made hash functions and probing functions for non-uniform key distributions to minimize total operations during insertion and collision resolution. Keys are positive integers with fixed-length decimal representations, where each digit has a distinct non-uniform distribution.

### Problem Aspects
- **Hash Table Performance**: Optimize performance based on the total number of operations needed for inserting numbers and collision resolution.
- **Operation Counting**: Operations like addition, multiplication, modulo, bitwise operations, copying, and comparisons are counted.
- **Custom Functions**: Develop hash and probing functions based on given distributions.

## Problem 3: Implementation
Participants should write a program that:
- Reads digit distributions and validates them.
- Determines hash table capacity and selects appropriate hash and probing functions.
- Outputs hash table size and prompts for file names containing test data.
- Inserts elements into the table using the hash function and counts the operations.
- Offers options to print the hash table and search for elements.

### Restrictions
- Limited use of additional memory.
- No storing of information about inserted elements or element counts.
- No look-ahead in data.

### Evaluation Criteria
- Correctness of the hash function and operation counting.
- Clarity and explanation of the algorithms.
- Minimization of operations on test runs.
- Quality of test examples and setup for input and output.
