# Math

## 素数分解

每一个数都可以分解成素数的乘积，例如 84 = 22 * 31 * 50 * 71 * 110 * 130 * 170 * …

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

- 如果 a 和 b 均为偶数，f(a, b) = 2*f(a/2, b/2);
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

###  7 进制

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

> `>>>` 是Java中的无符号右移运算符。它将一个数的二进制位向右移动指定的位数，并且在左侧填充0。无符号右移不考虑符号位，而只是简单地移动位。

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

### 统计阶乘尾部有多少个0

[172. Factorial Trailing Zeroes](https://leetcode-cn.com/problems/factorial-trailing-zeroes/description/)

尾部的 0 由 2 * 5 得来，2 的数量明显多于 5 的数量，因此只要统计有多少个 5 即可。

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

##  字符串加法减法

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



未完待续~~~
