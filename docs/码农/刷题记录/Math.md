# Math

## 素数分解

每一个数都可以分解成素数的乘积，例如 84 = 22 _ 31 _ 50 _ 71 _ 110 _ 130 _ 170 \* …

## 整除

令 $x = 2^{m_0} * 3^{m_1} * 5^{m_2} * 7^{m_3} * 11^{m_4} * \cdots$

令 $x = 2^{n_0} * 3^{n_1} * 5^{n_2} * 7^{n_3} * 11^{n_4} * \cdots$

如果 x 整除 y（$y \mod x == 0$），则对于所有 i，$m_i <= n_i$。

## 最大公约数最小公倍数

x 和 y 的最大公约数为：$gcd(x,y) = 2\min(m_0,n_0) * 3\min(m_1,n_1) * 5\min(m_2,n_2) * ...$

x 和 y 的最小公倍数为：$lcm(x,y) = 2\max(m_0,n_0) * 3\max(m_1,n_1) * 5\max(m_2,n_2) * ...$

### [生成素数序列](https://leetcode-cn.com/problems/count-primes/description/)

埃拉托斯特尼筛法在每次找到一个素数时，将能被素数整除的数排除掉。

解答：

```java
class Solution {

    public int countPrimes(int n) {
        if(n == 1 && n == 2) return 0;
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for(int i = 2; i < n; i++){
            if(notPrime[i]) continue;
            else {
                count++;
                for(long j = (long) i * i; j < n; j += i){ //i*i之前的素数已经被标记为true，所以只需要标记i*i之后的数
                    notPrime[(int) j] = true;
                }
            }
        }
        return count;
    }
}
```

### 最大公约数

```java
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
}
```

最小公倍数为两数的乘积除以最大公约数。

```java
int lcm(int a, int b) {
    return a * b / gcd(a, b);
}
```

### 使用位操作和减法求解最大公约数

对于 a 和 b 的最大公约数 f(a, b)，有：

- 如果 a 和 b 均为偶数，f(a, b) = 2\*f(a/2, b/2);
- 如果 a 是偶数 b 是奇数，f(a, b) = f(a/2, b);
- 如果 b 是偶数 a 是奇数，f(a, b) = f(a, b/2);
- 如果 a 和 b 均为奇数，f(a, b) = f(b, a-b);

乘 2 和除 2 都可以转换为移位操作。

```java
int gcd(int n, int m) {
    if(n < m) return gcd(m, n);
    if(m == 0) return n;
    boolean nisEven = false;
    boolean misEven = false;
    if((n & 1) == 0) nisEven = true;
    if((m & 1) == 0) misEven = true;
    if(nisEven && misEven) return gcd(n >> 1, m >> 1) << 1;
    else if(nisEven) return gcd(n >> 1, m);
    else if(misEven) return gcd(n, m >> 1);
    else return gcd(n - m, m);
}
```

## 进制转换

### 7 进制

[504.Base 7](https://leetcode.com/problems/base-7/description/)

解答：

```java
// 语法糖
class Solution {
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}

// 自己实现
class Solution {
    public String convertToBase7(int num) {
        if(num == 0) return "0";
        else if(num < 0) return "-" + convertToBase7(-num);

        StringBuilder sb = new StringBuilder();
        while(num > 0){
            sb.insert(0, num % 7);
            num /= 7;
        }

        return sb.toString();
    }
}
```

### 16 进制

[405. Convert a Number to Hexadecimal](https://leetcode.com/problems/convert-a-number-to-hexadecimal/description/)

解答：

> `>>>` 是 Java 中的无符号右移运算符。它将一个数的二进制位向右移动指定的位数，并且在左侧填充 0。无符号右移不考虑符号位，而只是简单地移动位。

```java
class Solution {
    public String toHex(int num) {
        char[] map = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        if (num == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (num != 0) {
            sb.append(map[num & 0b1111]); // 0b是二进制
            num >>>= 4; // 因为考虑的是补码形式，因此符号位就不能有特殊的意义，需要使用无符号右移，左边填 0
        }
        return sb.reverse().toString();
    }
}
```

### 26 进制

[168. Excel Sheet Column Title](https://leetcode.com/problems/excel-sheet-column-title/description/)

```java
class Solution {
    public String convertToTitle(int columnNumber){
        if(columnNumber == 0) return "";
        columnNumber--; // To start from A instead of 1

        return convertToTitle(columnNumber/26) + (char)((columnNumber % 26)+'A');
    }
}
```

## 阶乘

### 统计阶乘尾部有多少个 0

[172. Factorial Trailing Zeroes](https://leetcode-cn.com/problems/factorial-trailing-zeroes/description/)

尾部的 0 由 2 \* 5 得来，2 的数量明显多于 5 的数量，因此只要统计有多少个 5 即可。

对于一个数 N，它所包含 5 的个数为：N/5 + N/52 + N/53 + ...，其中 N/5 表示不大于 N 的数中 5 的倍数贡献一个 5，N/52 表示不大于 N 的数中 52 的倍数再贡献一个 5 ...。

解答：

```java
class Solution {
    public int trailingZeroes(int n) { // 0的个数就是2*5的个数，所以只需要计算5的个数即可
        if(n == 0) return 0;
        return n/5 + trailingZeroes(n/5); //注意25 == 5 * 5
    }
}
```

## 字符串加法减法

### 二进制加法

[67. Add Binary](https://leetcode-cn.com/problems/add-binary/description/)

解答：

```java
class Solution {
    public String addBinary(String a, String b) {
        int len1 = a.length();
        int len2 = b.length();

        if(len1 == 0 || len2 == 0) return a.length() == 0 ? b : a;
        if(a.charAt(len1-1) == '0' && b.charAt(len2-1) == '0') {
            return addBinary(a.substring(0, len1-1), b.substring(0, len2-1)) + "0";
        }
        else if(a.charAt(len1-1) == '1' && b.charAt(len2-1) == '1'){
            return addBinary(addBinary(a.substring(0, len1-1), "1"), b.substring(0, len2-1)) + "0";
        }
        else if(a.charAt(len1-1) == '1' || b.charAt(len2-1) == '1'){
            return addBinary(a.substring(0, len1-1), b.substring(0, len2-1)) + "1";
        }
        return "";
    }
}

class Solution {
    public String addBinary(String a, String b) {
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 || j >= 0) {
            if(i >= 0 && a.charAt(i--) == '1') carry++;
            if(j >= 0 && b.charAt(j--) == '1') carry++;
            sb.append(carry % 2);
            carry >>= 1;
        }
        if(carry == 1) sb.append("1");
        return sb.reverse().toString();
    }
}
```

### 字符串加法

[415. Add Strings](https://leetcode-cn.com/problems/add-strings/description/)

字符串的值为非负整数。

解答：

```java
class Solution {
    public String addStrings(String num1, String num2) {
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1;
        while (i >= 0 || j >= 0 || carry > 0) {
            if (i >= 0) carry += num1.charAt(i--) - '0';
            if (j >= 0) carry += num2.charAt(j--) - '0';
            sb.append(carry % 10);
            carry = carry / 10;
        }
        return sb.reverse().toString();
    }
}
```

## 相遇问题

### 改变数组元素使所有的数组元素都相等

[462. Minimum Moves to Equal Array Elements II](https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements-ii/description/)

每次可以对一个数组元素加一或者减一，求最小的改变次数。

这是个典型的相遇问题，移动距离最小的方式是所有元素都移动到**中位数**。

解答：

```java
import java.util.Arrays;

class Solution {
    public int minMoves2(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int meannum = nums[n/2];
        int cnt = 0;
        for (int num : nums) {
            cnt += Math.abs(num - meannum);
        }
        return cnt;
    }
}
```

## 多数投票问题

### 数组中出现次数多于 n / 2 的元素

[169. Majority Element](https://leetcode-cn.com/problems/majority-element/description/)

先对数组排序，最中间那个数出现次数一定多于 n / 2。

解答：

```java
// O(n)
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > nums.length / 2) return num;
        }
        return 0;
    }
}

// O(nlogn)
import java.util.Arrays;

class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }
}
```

### 平方数

[367. Valid Perfect Square](https://leetcode-cn.com/problems/valid-perfect-square/description/)

平方序列：1,4,9,16,..

间隔：3,5,7,...

间隔为等差数列，使用这个特性可以得到从 1 开始的平方序列。

解答：

```java
// O(log(N))
class Solution {
    public boolean isPerfectSquare(int num) {
        if(num == 1) return true;
        int left = 1, right = num;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if((long)mid * mid == num) return true;
            else if((long)mid * mid < num) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
}

// O(sqrt(N))
class Solution {
    public boolean isPerfectSquare(int num) {
        if(num == 1) return true;
        int subnum = 1;
        while(num > 0){
            num -= subnum;
            subnum += 2;
        }
        return num == 0;
    }
}
```

### 3 的 n 次方

[326. Power of Three](https://leetcode-cn.com/problems/power-of-three/description/)

`-231 <= n <= 231 - 1`

解答：

```java
// 约数 O(1)
class Solution {
    public boolean isPowerOfThree(int n) {
        return n > 0 && (1162261467 % n == 0);
    }
}

// O(log n)
class Solution {
    public boolean isPowerOfThree(int n) {
        if(n <= 0) return false;
        while(n % 3 == 0) n /= 3;
        return n == 1;
    }
}
```

### 乘积数组

[238. Product of Array Except Self](https://leetcode-cn.com/problems/product-of-array-except-self/description/)

要求时间复杂度为 O(N)，并且不能使用除法。

解答：

```java
import java.util.Arrays;

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, 1);
        int left = 1;
        for(int i = 1; i < n; ++i){
            left *= nums[i-1];
            res[i] *= left;
        }
        int right = 1;
        for(int i = n - 2; i >= 0; --i){
            right *= nums[i+1];
            res[i] *= right;
        }
        return res;
    }
}
```

### 找出数组中的乘积最大的三个数

[628. Maximum Product of Three Numbers](https://leetcode-cn.com/problems/maximum-product-of-three-numbers/description/)

解答：

```java
import java.util.Arrays;

class Solution {
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        if(n == 3 || nums[0] >= 0 || nums[1] >= 0 || nums[n-1] <= 0) return nums[n-1] * nums[n-2] * nums[n-3];
        return Math.max(nums[0] * nums[1] * nums[n-1], nums[n-1] * nums[n-2] * nums[n-3]);
    }
}

// 原理的很重要
class Solution {
    public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE, min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }

            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }
        return Math.max(max1*max2*max3, max1*min1*min2);
    }
}
```

## 从集合到位运算

在高中，我们学了集合论（set theory）的相关知识。例如，包含若干整数的集合 $ S $。在编程中，通常用哈希表（hash table）表示集合。例如 Java 中的 `HashSet`，C++ 中的 `std::unordered_set`。

在集合论中，有交集、并集、包含于等等概念。如果编程实现「求两个哈希表的交集」，需要一个一个地遍历哈希表中的元素。那么，有没有效率更高的做法呢？

答案是：用二进制。

集合可以用二进制表示，二进制**从低到高**第 $ i $ 位为 1 表示元素 $ i $ 在集合中，为 0 表示不在集合中。例如集合 $\{0, 2, 3\}$ 可以用二进制数 $1101_2$ 表示；反过来，二进制数 $1101_2$ 就对应着集合 $\{0, 2, 3\}$。

正式地说，包含非负整数的集合 $ S $ 可以用如下方式「压缩」成一个数字：

$$
S = \{i_1, i_2, \ldots, i_k\} \quad \Longrightarrow \quad s = \sum_{j=1}^k 2^{i_j}
$$

例如集合 $\{0, 2, 3\}$ 可以压缩成 $1 + 4 + 8 = 13$，也就是二进制数 $1101_2$。

利用位运算「并行计算」的特点，我们可以高效地做一些和集合有关的运算。按照常见的应用场景，可以分为以下四类：

1. 集合与集合
2. 集合与元素
3. 遍历集合
4. 枚举集合

---

### 一、集合与集合

其中 $\&$ 表示按位与， $|$ 表示按位或， $\oplus$ 表示按位异或， $\sim$ 表示按位取反。

两个集合的「对称差」是只属于其中一个集合，而不属于另一个集合的元素组成的集合，也就是不在交集中的元素组成的集合。

| 术语       | 集合表示                                          | 位运算表达式                         | 集合示例                              | 位运算示例                    |
| ---------- | ------------------------------------------------- | ------------------------------------ | ------------------------------------- | ----------------------------- |
| 交集       | $A \cap B$                                        | $a \& b$                             | $\{0,2\} \cap \{1,2\} = \{2\}$        | $101_2 \& 110_2 = 100_2$      |
| 并集       | $A \cup B$                                        | $a | b$                              | $\{0,2\} \cup \{1,2\} = \{0,1,2\}$    | $101_2 | 110_2 = 111_2$       |
| 对称差     | $A \triangle B = (A \cup B) \setminus (A \cap B)$ | $a \oplus b$                         | $\{0,1,2\} \setminus \{2\} = \{0,1\}$ | $101_2 \oplus 110_2 = 011_2$  |
| 差         | $A \setminus B$                                   | $a \& \sim b$                        | $\{0,2\} \setminus \{1,2\} = \{0\}$   | $101_2 \& \sim 110_2 = 001_2$ |
| 差（子集） | $A \subseteq B$ 判断                              | $(a \& \sim b) == 0$ 或 $a | b == b$ | 判断是否是子集                        | 见注释                        |

> **注 1**：按位取反的例子中，仅列出最低 $n$ 个比特位取反后的结果，即 $\sim 110_2 = 001_2$（假设只看3位）。
>
> **注 2**：包含于（判断子集）的两种位运算写法是等价的，在编程时只需判断其中任意一种。此外，还可以用 `(a & ~b) == 0` 判断，如果成立，也表示 $A$ 是 $B$ 的子集。
>
> **注 3**：编程时，请注意运算符的优先级。例如 `==` 在某些语言中优先级比位运算更高。

---

### 二、集合与元素

通常会用到移位运算。

其中 $\ll$ 表示左移， $\gg$ 表示右移。

> 注：左移 $k$ 位相当于乘以 $2^k$，右移 $k$ 位相当于除以 $2^k$。

| 术语                     | 集合表示             | 位运算表达式                | 集合示例               | 位运算示例        |
| ------------------------ | -------------------- | --------------------------- | ---------------------- | ----------------- |
| 空集                     | $\emptyset$          | $0$                         | $\emptyset$            | $0$               |
| 单元素集合               | $\{i\}$              | $1 \ll i$                   | $\{2\}$                | $1 << 2 = 100_2$  |
| 全集                     | $\{0,1,\ldots,n-1\}$ | $(1 \ll n) - 1$             | $\{0,1,2\}$            | $2^3 - 1 = 111_2$ |
| 补集                     | $\overline{S}$       | $\sim s \& ((1 \ll n) - 1)$ | 补集取决于全集         | 见注释            |
| 属于                     | $i \in S$            | $(s \& (1 \ll i)) \neq 0$   | 判断元素是否在集合中   | 见示例            |
| 不属于                   | $i \notin S$         | $(s \& (1 \ll i)) == 0$     | 判断元素是否不在集合中 | 见示例            |
| 添加元素                 | $S \cup \{i\}$       | $s | (1 \ll i)$             | 添加元素               | 见示例            |
| 删除元素                 | $S \setminus \{i\}$  | $s \& \sim(1 \ll i)$        | 删除元素               | 见示例            |
| 删除元素（一定在集合中） |                      | $s = s \& (s - 1)$          | 删除最低位的1          | 见下方示例        |
| 删除最小元素             |                      | $s = s \& (s - 1)$          | 删除集合中最小元素     | 见示例            |

特别地，如果 $s$ 是 $2^k$ 的幂，那么 $s \& (s-1) = 0$。

此外，编程语言提供了一些和二进制有关的库函数，例如：

- 计算二进制中的1的个数，也就是**集合大小**；
- 计算二进制长度，**减一**后得到**集合最大元素**；
- 计算二进制尾零个数，也就是**集合最小元素**。

调用这些函数的时间复杂度都是 $O(1)$。

| 术语         | Python                      | Java                                   | C++                     | Go                      |
| ------------ | --------------------------- | -------------------------------------- | ----------------------- | ----------------------- |
| 集合大小     | `s.bit_count()`             | `Integer.bitCount(s)`                  | `__builtin_popcount(s)` | `bits.OnesCount(s)`     |
| 二进制长度   | `s.bit_length()`            | `32 - Integer.numberOfLeadingZeros(s)` | `__lg(s) + 1`           | `bits.Len(s)`           |
| 集合最大元素 | `s.bit_length() - 1`        | `31 - Integer.numberOfLeadingZeros(s)` | `__lg(s)`               | `bits.Len(s) - 1`       |
| 集合最小元素 | `(s & -s).bit_length() - 1` | `Integer.numberOfTrailingZeros(s)`     | `__builtin_ctz(s)`      | `bits.TrailingZeros(s)` |

请特别注意 $s=0$ 的情况。对于 C++ 来说，`__lg(0)` 和 `__builtin_ctz(0)` 是未定义行为。其他语言请查阅 API 文档。

此外，对于 C++ 的 `long long`，需使用相应的 `__builtin_popcountll` 等函数，即函数名后缀添加 `ll`（两个小写字母 L）。`__lg` 支持 `long long`。

特别地，只包含最小元素的子集，即二进制最低位的1及其后面的0，也叫 **lowbit**，可以用 `s & -s` 算出。举例说明：
$$
s = 101100_2
~s = 010011_2
$$
$(\sim s)+1 = 010100_2 $// 根据补码的定义，这就是 -s

$ s \& -s = 000100_2$ // lowbit

---

### 三、遍历集合

设元素范围从 $0$ 到 $n-1$，枚举范围中的元素 $i$，判断 $i$ 是否在集合 $s$ 中。

```python
for i in range(n):
	if (s >> i) & 1: # i 在 s 中 # 处理 i 的逻辑
```

也可以直接遍历集合 $s$ 中的元素：不断地计算集合最小元素、去掉最小元素，直到集合为空。

```python
t = s
while t:
	lowbit = t & -t
	t ^= lowbit
	i = lowbit.bit_length() - 1 # 处理 i 的逻辑
```

---

### 四、枚举集合

#### §4.1 枚举所有集合

设元素范围从 $0$ 到 $n-1$，从空集 $0$ 枚举到全集 $(1 << n) - 1$：

```python
for s in range(1 << n): # 处理 s 的逻辑
```

#### §4.2 枚举非空子集

设集合为 $s$，**从大到小**枚举 $s$ 的所有**非空**子集 $sub$：

```python
sub = s
while sub: # 处理 sub 的逻辑
	sub = (sub - 1) & s
```

为什么要写成 `sub = (sub - 1) & s` 呢？

暴力做法是从 $s$ 出发，不断减一，直到 0。但这样做，中途会遇到很多并不是 $s$ 的子集的情况。例如 $s=110_2$ 时，减一得到 $101_2$，这是 $s$ 的子集。但再减一就得到 $100_2$ 了，这并不是 $s$ 的子集，下一个子集应该是 $110_2$ 的其他子集。

把所有的合法子集按顺序列出来，会发现我们做的相当于「压缩版」的二进制减法。

如何快速跳到下一个子集呢？比如，怎么从 $sub=110_2$ 跳到 $101_2$？

- 普通的二进制减法，是 $110_2 - 1 = 101_2$，也就是把最低位的 1 变成 0，同时把最低位的 1 右边的 0 都变成 1。
- 压缩版的二进制减法也是类似的，对于 $sub$，也会把最低位的 1 变成 0，对于最低位的 1 右边的 0，并不是都变成 1，只有在 $s$ 中的 0 才会变成 1。怎么做到？减一后再与 $s$ 按位与就行，也就是 `sub = (sub - 1) & s`。

#### §4.3 枚举子集（包含空集）

如果要从大到小枚举 $s$ 的所有子集（从 $s$ 枚举到空集 $0$），可以这样写：

```python
sub = s
while True: # 处理 sub 的逻辑
	if sub == 0:
	break
sub = (sub - 1) & s
```

其中 Java 和 C++ 的原理是，当 $sub=0$ 时（空集），再减一就得到 $-1$，对应的二进制为全1，再与 $s$ 按位与就得到了 $s$。所以当循环到 $sub=0$，说明最后一次循环的 $sub=0$（空集），$s$ 的所有子集都枚举到了，退出循环。

> **注**：还可以枚举全集 $n$ 的所有大小**恰好**为 $k$ 的子集，这一技巧叫做 **Gosper's Hack**，具体请看 [视频讲解](https://leetcode.cn/link/?target=https%3A%2F%2Fwww.bilibili.com%2Fvideo%2FBV1na41137jv%3Ft%3D15m43s)。

#### §4.4 枚举超集

如果 $s \subseteq t$，那么称 $t$ 是 $s$ 的**超集**（superset）。

枚举超集的原理和上文枚举子集是类似的，这里通过**或运算**保证枚举的集合 $s$ 一定包含集合 $t$ 中的所有元素。

枚举 $s$，满足 $s$ 是 $t$ 的超集，也是全集的子集。

```python
s = t
while s < (1 << n): # 处理 s 的逻辑
	s = (s + 1) | t
```

