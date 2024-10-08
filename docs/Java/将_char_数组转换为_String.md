# 在 Java 中将 char 数组转换为 String


这篇文章将讨论如何在 Java 中将 char 数组转换为字符串。应该分配一个新字符串来表示 char 数组中包含的字符序列。由于字符串在Java中是不可变的，因此后续对字符数组的修改不会影响分配的字符串。

1. 使用字符串构造函数
最简单的解决方案是将 char 数组传递给 String 构造函数。 String 构造函数内部使用 Arrays.copyOf() 复制字符数组的内容。

```java
//在Java中将char数组转换为字符串
class Main
{
    public static void main(String[] args)
    {
        char[] chars = {'T', 'e', 'c', 'h', 'i', 'e', ' ',
                        'D', 'e', 'l', 'i', 'g', 'h', 't'};
 
        String string = new String(chars);
        System.out.println(string);
    }
}
```

输出:

```java
Techie Delight
```



2.使用 String.valueOf() 或者 String.copyValueOf() 方法
我们可以使用封装字符串构造函数调用 String.valueOf() 或者 String.copyValueOf() 在内部做同样的事情。

```java
//在Java中将char数组转换为字符串
class Main
{
    public static void main(String[] args)
    {
        char[] chars = {'T', 'e', 'c', 'h', 'i', 'e', ' ',
                        'D', 'e', 'l', 'i', 'g', 'h', 't'};
 
        String string = String.copyValueOf(chars);
        System.out.println(string);
    }
}
```



输出:



```java
Techie Delight
```



3.使用 StringBuilder
将 char 数组转换为字符串的另一种可行方法是使用 StringBuilder 在Java。这个想法是遍历字符并将每个字符附加到 StringBuilder.最后，调用 toString() 上的方法 StringBuilder 当迭代所有字符时。

```java
//在Java中将char数组转换为字符串
class Main
{
    public static void main(String[] args)
    {
        char[] chars = {'T', 'e', 'c', 'h', 'i', 'e', ' ',
                        'D', 'e', 'l', 'i', 'g', 'h', 't'};
 
        StringBuilder sb = new StringBuilder();
        for (char ch: chars) {
            sb.append(ch);
        }
 
        String string = sb.toString();
        System.out.println(string);
    }
}
```

输出:

```java
Techie Delight
```



4.使用 toString() 方法
(不建议)

这里的想法是获取指定数组的字符串表示形式。字符串表示由数组元素的列表组成，括在方括号 ("[]")，所有相邻元素用逗号分隔，后跟空格 ", ".

我们可以通过调用 substring() 方法和逗号+空格使用 replaceAll() 方法。

```java
import java.util.Arrays;
 
//在Java中将char数组转换为字符串
class Main
{
    public static void main(String[] args)
    {
        char[] chars = {'T', 'e', 'c', 'h', 'i', 'e', ' ',
                        'D', 'e', 'l', 'i', 'g', 'h', 't'};
 
        String string = Arrays.toString(chars)
                            .substring(1, 3*chars.length-1)
                            .replaceAll(", ", "");
 
        System.out.println(string);
    }
}
```



输出:

```java
Techie Delight
```

