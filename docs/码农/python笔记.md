# python笔记

pip 安装库

pip install -i https://mirrors.tuna.tsinghua.edu.cn/pypi/web/simple <some-package>

一切皆对象


```python
x = -3

s1 = "hello world" # single quotes and double quotes are same
s2 = 'hello world'
True + 1 == 2 # True is 1, False is 0

print(type(s1)) # 请注意，不要给字符串起名为 str，否则 str 对象会被篡改

```

    <class 'str'>


**内置函数**

在 C/C++ 中，很多常用函数都分散在不同的头文件中，但 Python 的解释器内置了许多实用且通用的函数，你可以直接使用而无需注意它们的存在，但这也带来了小问题，这些内置函数的名称多为常见单词，你需要注意避免给自己的变量起相同的名字，否则可能会产生奇怪的结果。

正如我们所看到的，Python 内置有整数、浮点数、字符串和布尔类型，可以类比为 C++ 中的 int，float，string 和 bool。但有一些明显的不同之处，比如没有 char 字符类型，也没有 double 类型（但 float 其实对应 C 中的双精度），如果需要更精确的浮点运算，可以使用标准库中的 decimal 模块，如果需要用到复数，Python 还内置了 complex 类型（而这也意味着最好不要给变量起名为 complex）。

可以看到这些类型都以 class 开头，而这正是 Python 不同于 C++ 的关键之处，Python 程序中的所有数据都是由对象或对象间关系来表示的，函数是对象，类型本身也是对象：


```python
type(int) # <class 'type'>
type(str) # <class 'type'>
type(type) # <class 'type'>
type(None) # <class 'NoneType'>
type(pow) # <class 'builtin_function_or_method'>

```




    builtin_function_or_method



**数字运算**

可以把你系统里装的 Python 当作一个多用计算器

在交互模式下，你可以在提示符 >>> 后面输入一个表达式，然后按回车键执行。

除法运算（/）永远返回浮点类型, 而整数除法（//）则会向下取整,% 运算符返回余数, ** 运算符表示乘方, 也可以 pow(a, b, mod)

**基本输入输出**

Python 中的输入输出主要通过内置函数 input() 和 print() 完成


```python
# s = input('请输入一串数字: ')
# print(s)
```

**字符串**

Python 3 提供了强大的基于 Unicode 的字符串类型，使用起来和 C++ 中的 string 类似，一些概念如转义字符也都相通，除了加号拼接和索引访问，还额外支持数乘 * 重复字符串，和 in 操作符。


```python
s1 = "o"
s1 += "iwiki"
print(s1)
print("oi" in s1)
s2 = s1[0] * 2 + s1[2:]
print(s2)

```

    oiwiki
    True
    oowiki


Python 支持多种复合数据类型，可将不同值组合在一起。最常用的 list，类型是用方括号标注、逗号分隔的一组值。例如，[1, 2, 3] 和 ['a','b','c'] 都是列表。

除了索引，字符串还支持切片，它的设计非常精妙，格式为 s[左闭索引:右开索引:步长]

在最新的 Python 3 版本中，字符串是以 Unicode 编码的，也就是说，Python 的字符串支持多语言。在 Python 中，可以对一个 Unicode 字符使用内置函数 ord() 将其转换为对应的 Unicode 编码，逆向的转换使用内置函数 chr()。C/C++ 中 char 类型也可以和 对应的 ASCII 码互转。

如果想把数字转换成对应的字符串，可以使用内置函数 str()，反之可以使用 int() 和 float()，你可以类比为 C/C++ 中的强制类型转换，但括号不是加在类型上而是作为函数的一部分括住参数。


```python
print(ord('中'))
print(chr(20013))
print(str(20013))
```

    20013
    中
    20013


**创建数组**

接近 C++ 中的 vector

**使用 list**

列表（list）大概是 Python 中最常用也最强大的序列类型，列表中可以存放任意类型的元素，包括嵌套的列表，这符合数据结构中「广义表」的定义。请注意不要将其与 C++ STL 中的双向链表 list 混淆。


```python
print([])
nums = [1, 2, 3, 4, 5]
print(nums)

nums.append(nums[-1] + nums[-2])
print(nums)

nums.pop()
print(nums)

nums.insert(0, 1)
print(nums)

nums.remove(1) # remove the first occurrence of 1
print(nums)

nums.reverse()
print(nums)

sorted(nums) # 获得排序后的列表
print(nums) # 原列表没变

nums.sort() # 直接对原列表进行排序
print(nums)

nums.extend([6, 7, 8]) # 在列表末尾添加元素
print(nums)

nums.count(2) # 统计元素2出现的次数
print(nums.index(5)) # 找出元素5的索引

nums.clear() # 清空列表
print(nums)
```

    []
    [1, 2, 3, 4, 5]
    [1, 2, 3, 4, 5, 9]
    [1, 2, 3, 4, 5]
    [1, 1, 2, 3, 4, 5]
    [1, 2, 3, 4, 5]
    [5, 4, 3, 2, 1]
    [5, 4, 3, 2, 1]
    [1, 2, 3, 4, 5]
    [1, 2, 3, 4, 5, 6, 7, 8]
    4
    []



```python
# 建立一个 [65, 70) 区间上的整数数组，range 也是一种类型，可看作左闭右开区间，第三个参数为步长可省略
a = list(range(65, 70))
print(a)

lst = [chr(i) for i in a]
print(lst)

# 字符串的 join 方法可以将列表中的元素连接成一个字符串
s = ''.join(lst)
print(s)


''.join([chr(ord(ch) + 32) for ch in s if ch >= 'A' and ch <= 'Z'])  # 转换为小写字母
s.lower()  # 转换为小写字母

```

    [65, 66, 67, 68, 69]
    ['A', 'B', 'C', 'D', 'E']
    ABCDE





    'abcde'




```python
vis = [[0] * 3] * 3 # [[0, 0, 0], [0, 0, 0], [0, 0, 0]]
vis[0][0] = 1 # [[1, 0, 0], [1, 0, 0], [1, 0, 0]]


a1 = [0, 0, 0]
a2 = a1
a3 = a1[:]
a1[0] = 1 # a1 = [1, 0, 0], a2 = [1, 0, 0], a3 = [0, 0, 0]
id(a1) == id(a2) and id(a1) != id(a3) # True

vis2 = vis[:]
vis[0][1] = 2 # vis = [[1, 2, 0], [1, 2, 0], [1, 2, 0]], vis2 = [[1, 2, 0], [1, 2, 0], [1, 2, 0]]， vis2 是切片拷贝还是被改了
id(vis) != id(vis2) # True， vis2 虽然不是 vis 的引用，但其中对应行都指向相同的对象
print([id(vis[i]) == id(vis2[i]) for i in range(3)])

print([id(x) for x in vis])

```

    [True, True, True]
    [2448367626240, 2448367626240, 2448367626240]


其实有一个重要的事实，Python 中赋值只传递了引用而非创建新值，你可以创建不同类型的变量并赋给新变量，验证发现二者的标识值是相同的，只不过直到现在我们才介绍了列表这一种可变类型，而给数字、字符串这样的不可变类型赋新值时实际上创建了新的对象，故而前后两个变量互不干扰。但列表是可变类型，所以我们修改一个列表的元素时，另一个列表由于指向同一个对象所以也被修改了。创建二维数组也是类似的情况，示例中用乘法创建二维列表相当于把 [0]*3 这个一维列表重复了 3 遍，所以涉及其中一个列表的操作会同时影响其他两个列表。

更不幸的是，在将二维列表赋给其他变量的时候，就算用切片来拷贝，也只是「浅拷贝」，其中的元素仍然指向相同的对象，解决这个问题需要使用标准库中的 deepcopy，或者尽量避免整个赋值二维列表。

不过还好，创建二维列表时避免创建重复的列表还是比较简单，只需使用「列表推导式」：


```python
vis1 = [[0] * 3 for _ in range(3)]
print([id(x) for x in vis1])

vis1[0][0] = 1 # [[1, 0, 0], [0, 0, 0], [0, 0, 0]]
```

    [2448367630272, 2448367620288, 2448367626304]


由于 Python 是高度动态的解释型语言，因此其程序运行有大量的额外开销。尤其是 for 循环在 Python 中运行的奇慢无比。因此在使用 Python 时若想获得高性能，尽量使用使用列表推导式，或者 filter,map 等内置函数直接操作整个序列来避免循环，当然这还是要根据具体问题而定。

**numpy**


```python
import numpy as np

print(np.empty(3))
print(np.zeros((2, 3)))

a = np.zeros((3, 3), dtype=int)
print(a)

a[0, 0] = 1
a[0][1] = 2
print(a)

print(a[:2, :3])

print(a.flatten())

print(np.sort(a, axis = 1)) # 沿行方向对数组进行排序，返回排序结果

a.sort(axis = 0) # 沿列方向对数组进行原地排序

```

    [4.24399158e-314 0.00000000e+000 0.00000000e+000]
    [[0. 0. 0.]
     [0. 0. 0.]]
    [[0 0 0]
     [0 0 0]
     [0 0 0]]
    [[1 2 0]
     [0 0 0]
     [0 0 0]]
    [[1 2 0]
     [0 0 0]]
    [1 2 0 0 0 0 0 0 0]
    [[0 1 2]
     [0 0 0]
     [0 0 0]]


Python 和 C 在代码风格上的重大差异：首先，Python 中不用 {} 而是用缩进表示块结构，如果缩进没有对齐会直接报错，如果 tab 和 空格混用也会报错；其次，块结构开始的地方比如 if 和 for 语句的行末要有冒号 :

**异常处理**

C++ 中有 try 块 用于异常处理

Python 中常见的是 EAFP 风格，故而代码中可能大量使用 try-except 语句


```python
s = "OI-wiki"
pat = "NOIP"
x = s.find(pat)  # find() 找不到返回 -1
try:
    y = s.index(pat)  # index() 找不到则抛出错误
    print(y)  # 这句被跳过
except ValueError:
    print("没找到")
    try:
        print(y)  # 此时 y 并没有定义，故又会抛出错误
    except NameError as e:
        print("无法输出 y")
        print("原因:", e)
```

    没找到
    无法输出 y
    原因: name 'y' is not defined


**文件读写**

Python 内置函数 open() 用于文件读写，为了防止读写过程中出错导致文件未被正常关闭，这里只介绍使用 with 语句的安全读写方法：


```python
# a = []
# with open("in.txt") as f:
#     N = int(f.readline())  # 读入第一行的 N
#     a[len(a) :] = [[int(x) for x in f.readline().split()] for i in range(N)]
#     print(a)

# with open("out.txt", "w") as f:
#     f.write("1\n")
```

**内置容器**

元组可以简单理解成不可变的列表，不过还需注意「不可变」的内涵，如果元组中的某元素是可变类型比如列表，那么仍可以修改该列表的值，元组中存放的是对列表的引用所以元组本身并没有改变。元组的优点是开销较小且「可哈希」，后者在创建字典和集合时非常有用。


```python
tup = tuple([[1, 2], 3])
tup[0].append(4)
print(tup)

a, b = 1, "oiwiki"
print(id(a), id(b))

b, a = a, b
print(id(a), id(b)) # 变量更像是名字，赋值只是让其指代对象

```

    ([1, 2, 4], 3)
    140712725780920 2450278306640
    2450278306640 140712725780920


字典就像 C++ STL 中的 map（请注意和 Python 中内置函数 map() 区分）用于存储键值对，形式类似 JSON，但 JSON 中键必须是字符串且以双引号括住，字典则更加灵活强大，可哈希的对象都可作为字典的键。需要注意 Python 几次版本更新后字典的特性有了较多变化，包括其中元素的顺序等。


```python
dic = {"key": "value"}  # 基本形式

dic = {chr(i): i for i in range(65, 91)}  # 大写字母到对应 ASCII 码的映射，注意断句
print(dic)
dic = dict(zip([chr(i) for i in range(65, 91)], range(65, 91)))  # 效果同上
dic = {dic[k]: k for k in dic}  # 将键值对逆转，for k in dic 迭代其键
print(dic)
dic = {v: k for k, v in dic.items()}  # 和上行作用相同，dic.items() 以元组存放单个键值对
print(dic)
dic = {
    k: v for k, v in sorted(dic.items(), key=lambda x: -x[1])
}  # 字典按值逆排序，用到了 lambda 表达式
print(dic)


print(dic["A"])  # 返回 dic 中 以 'A' 为键的项，这里值为65
dic["a"] = 97  # 将 d[key] 设为 value，字典中原无 key 就是直接插入
if "b" in dic:  # LBYL(Look Before You Leap) 风格
    print(dic["b"])  # 若字典中无该键则会出错，故先检查
else:
    dic["b"] = 98


```

    {'A': 65, 'B': 66, 'C': 67, 'D': 68, 'E': 69, 'F': 70, 'G': 71, 'H': 72, 'I': 73, 'J': 74, 'K': 75, 'L': 76, 'M': 77, 'N': 78, 'O': 79, 'P': 80, 'Q': 81, 'R': 82, 'S': 83, 'T': 84, 'U': 85, 'V': 86, 'W': 87, 'X': 88, 'Y': 89, 'Z': 90}
    {65: 'A', 66: 'B', 67: 'C', 68: 'D', 69: 'E', 70: 'F', 71: 'G', 72: 'H', 73: 'I', 74: 'J', 75: 'K', 76: 'L', 77: 'M', 78: 'N', 79: 'O', 80: 'P', 81: 'Q', 82: 'R', 83: 'S', 84: 'T', 85: 'U', 86: 'V', 87: 'W', 88: 'X', 89: 'Y', 90: 'Z'}
    {'A': 65, 'B': 66, 'C': 67, 'D': 68, 'E': 69, 'F': 70, 'G': 71, 'H': 72, 'I': 73, 'J': 74, 'K': 75, 'L': 76, 'M': 77, 'N': 78, 'O': 79, 'P': 80, 'Q': 81, 'R': 82, 'S': 83, 'T': 84, 'U': 85, 'V': 86, 'W': 87, 'X': 88, 'Y': 89, 'Z': 90}
    {'Z': 90, 'Y': 89, 'X': 88, 'W': 87, 'V': 86, 'U': 85, 'T': 84, 'S': 83, 'R': 82, 'Q': 81, 'P': 80, 'O': 79, 'N': 78, 'M': 77, 'L': 76, 'K': 75, 'J': 74, 'I': 73, 'H': 72, 'G': 71, 'F': 70, 'E': 69, 'D': 68, 'C': 67, 'B': 66, 'A': 65}
    65


集合就像 C++ STL 中的 set，不会保存重复的元素，可以看成只保存键的字典。需要注意集合和字典都用 {} 括住，不过单用 {} 会创建空字典而不是空集合

Python 中使用默认参数很有可能遇到坑


```python
def append_to(element, to=[]):
    to.append(element)
    return to


lst1 = append_to(12)
lst2 = append_to(42)
print(lst1, lst2)

# 你可能以为输出是 [12] [42]
# 但运行结果其实是 [12， 42] [12， 42]
# 原因是 append_to 函数的默认参数 to=[] 是一个空列表，当 append_to(12) 被调用时，to 被赋值为 [12]，
# 而 append_to(42) 被调用时，to 仍然是 [12]，因此 to.append(42) 实际上是修改了 to 的第一个元素，

```

    [12, 42] [12, 42]



```python
# 这是因为默认参数的值仅仅在函数定义的时候赋值一次
# 默认参数的值应该是不可变对象，使用 None 占位是一种最佳实践
def append_to(element, to=None):
    if to is None:
        to = []
    to.append(element)
    return to

lst1 = append_to(12)
lst2 = append_to(42)
print(lst1, lst2)
```

    [12] [42]


**类型标注**

Python 是一个动态类型检查的语言，以灵活但隐式的方式处理类型，Python 解释器仅仅在运行时检查类型是否正确，并且允许在运行时改变变量类型，俗话说「动态类型一时爽，代码重构火葬场」，程序中的一些错误可能在运行时才会暴露：


```python
if False:
    print(1 + "two")  # This line never runs, so no TypeError is raised
else:
    print(1 + 2)
```

    3


除了函数参数，变量也是可以类型标注的，你可以通过调用 __annotations__ 来查看函数中所有的类型标注。变量类型标注赋予了 Python 静态语言的性质，即声明与赋值分离：


```python
__annotations__
```




    {'nothing': str}



**装饰器**

装饰器是一个函数，接受一个函数或方法作为其唯一的参数，并返回一个新函数或方法，其中整合了修饰后的函数或方法，并附带了一些额外的功能。简而言之，可以在不修改函数代码的情况下，增加函数的功能。相关知识可以参考 官方文档。

比如 lru_cache，可以为函数自动增加记忆化的能力，在递归算法中非常实用：

`@lru_cache(maxsize=128,typed=False)`

传入的参数有 2 个：maxsize 和 typed，如果不传则 maxsize 的默认值为 128，typed 的默认值为 False。

其中 maxsize 参数表示的是 LRU 缓存的容量，即被装饰的方法的最大可缓存结果的数量。如果该参数值为 128，则表示被装饰方法最多可缓存 128 个返回结果；如果 maxsize 传入为 None 则表示可以缓存无限个结果。

如果 typed 设置为 True，不同类型的函数参数将被分别缓存，例如，f(3) 和 f(3.0) 会缓存两次。

以下是使用 lru_cache 优化计算斐波那契数列的例子：



```python
from functools import lru_cache


@lru_cache(maxsize=None)
def fib(n):
    if n < 2:
        return n
    return fib(n - 1) + fib(n - 2)

print(fib(10))
```

    55


**常用内置库**

在这里介绍一些写算法可能用得到的内置库，具体用法可以自行搜索或者阅读 [官方文档](https://docs.python.org/3/library/index.html)。

| 库名                                                         | 用途                         |
| :----------------------------------------------------------- | :--------------------------- |
| [`array`](https://docs.python.org/3/library/array.html)      | 定长数组                     |
| [`argparse`](https://docs.python.org/3/library/argparse.html) | 命令行参数处理               |
| [`bisect`](https://docs.python.org/3/library/bisect.html)    | 二分查找                     |
| [`collections`](https://docs.python.org/3/library/collections.html) | 有序字典、双端队列等数据结构 |
| [`fractions`](https://docs.python.org/3/library/fractions.html) | 有理数                       |
| [`heapq`](https://docs.python.org/3/library/heapq.html)      | 基于堆的优先级队列           |
| [`io`](https://docs.python.org/3/library/io.html)            | 文件流、内存流               |
| [`itertools`](https://docs.python.org/3/library/itertools.html) | 迭代器                       |
| [`math`](https://docs.python.org/3/library/math.html)        | 数学函数                     |
| [`os.path`](https://docs.python.org/3/library/os.html)       | 系统路径等                   |
| [`random`](https://docs.python.org/3/library/random.html)    | 随机数                       |
| [`re`](https://docs.python.org/3/library/re.html)            | 正则表达式                   |
| [`struct`](https://docs.python.org/3/library/struct.html)    | 转换结构体和二进制数据       |
| [`sys`](https://docs.python.org/3/library/sys.html)          | 系统信息                     |

Python中，

- 小括号 () 代表元组数据类型，
- 中括号 [] 代表列表数据类型，
- 大括号 {} 代表字典数据类型。


元组是一种不可变序列，创建方法很简单，大多时候都是用小括号括起来的。例如：

```python
tup = (1, 2, 3)
```

列表是一种可变序列，其创建方法即简单又特别。例如：

```python
list = ['a', 'b', 'c']
```

字典是由键对值组组成。冒号 : 分开键和值，逗号 , 隔开组。例如：

```python
dic = {'a': 1, 'b': 2, 'c': 3}
```


元组和列表是Python中两种常见的序列类型，它们之间的主要区别在于：

- 元组是不可变序列，而列表是可变序列。这意味着，元组的元素不能被修改、添加或删除，而列表可以。
- 元组使用小括号 () 表示，而列表使用中括号 [] 表示。
- 元组通常用于存储不可变的数据类型，如字符串、数字和其他元组。列表则通常用于存储可变的数据类型，如其他列表、字典和集合。





