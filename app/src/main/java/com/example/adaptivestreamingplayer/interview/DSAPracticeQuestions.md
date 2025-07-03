# DSA Interview Practice Questions

## Understanding Time and Space Complexity

### Time Complexity
Time complexity measures how the runtime of an algorithm grows as the input size increases. It's expressed using Big O notation (O).

#### Common Time Complexities
1. **O(1) - Constant Time**
   - Operations that take the same time regardless of input size
   - Example: Accessing an array element by index
   ```java
   int getElement(int[] arr, int index) {
       return arr[index];  // O(1)
   }
   ```

2. **O(log n) - Logarithmic Time**
   - Runtime grows logarithmically with input size
   - Example: Binary search
   ```java
   int binarySearch(int[] arr, int target) {
       int left = 0, right = arr.length - 1;
       while (left <= right) {
           int mid = left + (right - left) / 2;
           if (arr[mid] == target) return mid;
           if (arr[mid] < target) left = mid + 1;
           else right = mid - 1;
       }
       return -1;
   }
   ```

3. **O(n) - Linear Time**
   - Runtime grows linearly with input size
   - Example: Linear search
   ```java
   int linearSearch(int[] arr, int target) {
       for (int i = 0; i < arr.length; i++) {f
           if (arr[i] == target) return i;
       }
       return -1;
   }
   ```

4. **O(n log n) - Linearithmic Time**
   - Common in efficient sorting algorithms
   - Example: Merge sort, Quick sort
   ```java
   void mergeSort(int[] arr) {
       if (arr.length <= 1) return;
       int mid = arr.length / 2;
       int[] left = Arrays.copyOfRange(arr, 0, mid);
       int[] right = Arrays.copyOfRange(arr, mid, arr.length);
       mergeSort(left);
       mergeSort(right);
       merge(arr, left, right);
   }
   ```

5. **O(n²) - Quadratic Time**
   - Runtime grows quadratically with input size
   - Example: Bubble sort, Selection sort
   ```java
   void bubbleSort(int[] arr) {
       for (int i = 0; i < arr.length; i++) {
           for (int j = 0; j < arr.length - i - 1; j++) {
               if (arr[j] > arr[j + 1]) {
                   swap(arr, j, j + 1);
               }
           }
       }
   }
   ```

6. **O(2ⁿ) - Exponential Time**
   - Runtime doubles with each additional input element
   - Example: Recursive Fibonacci without memoization
   ```java
   int fibonacci(int n) {
       if (n <= 1) return n;
       return fibonacci(n - 1) + fibonacci(n - 2);
   }
   ```

7. **O(n!) - Factorial Time**
   - Runtime grows factorially with input size
   - Example: Permutations of a string
   ```java
   void permutations(String str, String ans) {
       if (str.length() == 0) {
           System.out.println(ans);
           return;
       }
       for (int i = 0; i < str.length(); i++) {
           char ch = str.charAt(i);
           String ros = str.substring(0, i) + str.substring(i + 1);
           permutations(ros, ans + ch);
       }
   }
   ```

### Space Complexity
Space complexity measures how much additional memory an algorithm needs as the input size grows.

#### Common Space Complexities
1. **O(1) - Constant Space**
   - Uses a fixed amount of memory regardless of input size
   - Example: Finding maximum in an array
   ```java
   int findMax(int[] arr) {
       int max = arr[0];
       for (int num : arr) {
           if (num > max) max = num;
       }
       return max;
   }
   ```

2. **O(n) - Linear Space**
   - Memory usage grows linearly with input size
   - Example: Creating a copy of an array
   ```java
   int[] copyArray(int[] arr) {
       int[] copy = new int[arr.length];
       for (int i = 0; i < arr.length; i++) {
           copy[i] = arr[i];
       }
       return copy;
   }
   ```

3. **O(n²) - Quadratic Space**
   - Memory usage grows quadratically with input size
   - Example: Creating a 2D array
   ```java
   int[][] createMatrix(int n) {
       int[][] matrix = new int[n][n];
       for (int i = 0; i < n; i++) {
           for (int j = 0; j < n; j++) {
               matrix[i][j] = i * j;
           }
       }
       return matrix;
   }
   ```

### Important Notes
1. **Auxiliary Space vs Total Space**
   - Auxiliary Space: Extra space used by the algorithm
   - Total Space: Space used by input + auxiliary space

2. **Space-Time Tradeoff**
   - Often, you can reduce time complexity by increasing space complexity
   - Example: Using a hash table to reduce lookup time from O(n) to O(1)

3. **Best, Average, and Worst Case**
   - Best Case: Minimum time/space required
   - Average Case: Expected time/space for random input
   - Worst Case: Maximum time/space required

4. **Amortized Analysis**
   - Considers the average time per operation over a sequence of operations
   - Example: Dynamic array resizing (ArrayList)

5. **Common Pitfalls**
   - Ignoring constants in Big O notation
   - Not considering space complexity
   - Overlooking worst-case scenarios
   - Forgetting about input size constraints

### Practice Questions
1. What is the time and space complexity of:
   ```java
   void printPairs(int[] arr) {
       for (int i = 0; i < arr.length; i++) {
           for (int j = 0; j < arr.length; j++) {
               System.out.println(arr[i] + ", " + arr[j]);
           }
       }
   }
   ```
   Answer: Time: O(n²), Space: O(1)

2. What is the time and space complexity of:
   ```java
   int[] fibonacciDP(int n) {
       int[] dp = new int[n + 1];
       dp[0] = 0;
       dp[1] = 1;
       for (int i = 2; i <= n; i++) {
           dp[i] = dp[i - 1] + dp[i - 2];
       }
       return dp;
   }
   ```
   Answer: Time: O(n), Space: O(n)

## 1. Array/String Manipulation

### Two Sum Problem
**Question:** Given an array of integers, find two numbers that add up to a specific target.

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    return new int[] {};
}
```
- Time Complexity: O(n)
- Space Complexity: O(n)

## 2. LinkedList

### Reverse a LinkedList
**Question:** Reverse a LinkedList both iteratively and recursively.

```java
// Iterative Solution
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}

// Recursive Solution
public ListNode reverseListRecursive(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode rest = reverseListRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return rest;
}
```

## 3. Tree Traversal

### Binary Tree Inorder Traversal
**Question:** Implement inorder traversal of a binary tree.

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        result.add(curr.val);
        curr = curr.right;
    }
    return result;
}
```

## 4. Dynamic Programming

### Longest Increasing Subsequence
**Question:** Find the length of the longest increasing subsequence in an array.

```java
public int lengthOfLIS(int[] nums) {
    if (nums.length == 0) return 0;
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);
    int maxLen = 1;
    
    for (int i = 1; i < nums.length; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        maxLen = Math.max(maxLen, dp[i]);
    }
    return maxLen;
}
```

## 5. Graph Problems

### Cycle Detection in Directed Graph
**Question:** Detect if there's a cycle in a directed graph using DFS.

```java
public boolean hasCycle(int[][] graph) {
    int n = graph.length;
    boolean[] visited = new boolean[n];
    boolean[] recursionStack = new boolean[n];
    
    for (int i = 0; i < n; i++) {
        if (dfs(i, graph, visited, recursionStack)) {
            return true;
        }
    }
    return false;
}

private boolean dfs(int node, int[][] graph, boolean[] visited, boolean[] recursionStack) {
    if (recursionStack[node]) return true;
    if (visited[node]) return false;
    
    visited[node] = true;
    recursionStack[node] = true;
    
    for (int neighbor : graph[node]) {
        if (dfs(neighbor, graph, visited, recursionStack)) {
            return true;
        }
    }
    
    recursionStack[node] = false;
    return false;
}
```

## Interview Tips

### Before Starting
1. Ask clarifying questions about:
   - Input constraints
   - Expected output format
   - Edge cases to consider
   - Performance requirements

### During Problem Solving
1. Explain your approach
2. Discuss time and space complexity
3. Consider alternative solutions
4. Write clean, readable code
5. Use meaningful variable names
6. Handle edge cases
7. Add comments for complex logic

### After Solution
1. Test with different test cases
2. Discuss potential optimizations
3. Explain trade-offs in your approach

### Common Follow-up Questions
1. How would you optimize the solution for large inputs?
2. What if the input is a stream of data?
3. How would you handle concurrent access?
4. Can we reduce the space complexity?
5. How would you scale this solution?

## Practice Problems by Category

### Arrays
1. Find the missing number in an array of 1 to N
2. Find duplicates in an array
3. Rotate an array by k positions
4. Maximum subarray sum (Kadane's algorithm)
5. Merge two sorted arrays

### Strings
1. Check if a string is a palindrome
2. Find the longest substring without repeating characters
3. Implement strStr() (string matching)
4. Group anagrams
5. Longest common prefix

### Linked Lists
1. Detect cycle in a linked list
2. Find the intersection of two linked lists
3. Merge two sorted linked lists
4. Remove Nth node from end of list
5. Add two numbers represented as linked lists

### Trees
1. Check if a binary tree is balanced
2. Find the lowest common ancestor
3. Serialize and deserialize a binary tree
4. Validate binary search tree
5. Path sum problems

### Dynamic Programming
1. Coin change problem
2. Knapsack problem
3. Longest common subsequence
4. Edit distance
5. Maximum product subarray

### Graphs
1. Clone a graph
2. Course schedule (topological sort)
3. Word ladder
4. Number of islands
5. Network delay time

Remember to practice implementing these solutions and understanding their underlying concepts thoroughly. Good luck with your interviews! 