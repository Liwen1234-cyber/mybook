# java 笔记

## List

```java
//Iterator
import java.util.Iterator;
import java.util.List;

public class HelloWorld {

	public static void main(String[] args) {
		List<String> list = List.of("Hello", "world");//只读List
		for(Iterator<String> it = list.iterator(); it.hasNext();) {
			String str = it.next();
			System.out.print(str);
		}
//        for (String s : list) {
//            System.out.println(s);
//        }
	}
}
```

```java
// Array to List
Integer[] array = { 1, 2, 3 };
List<Integer> list = List.of(array);//只读List

//List to Array
Object[] array = list.toArray();
Integer[] array = list.toArray(new Integer[3]);
Integer[] array = list.toArray(new Integer[list.size()]);
Integer[] array = list.toArray(Integer[]::new);//best
```

```java
//List内部并不是通过==判断两个元素是否相等，而是使用equals()方法判断两个元素是否相等
boolean contains(Object o)//该方法判断List是否包含某个指定元素

int indexOf(Object o)//该方法返回某个元素的索引，如果元素不存在，就返回-1
```

## Map

```java
Map<K, V>//是一种键-值映射表
put(K key, V value)//把key和value做了映射并放入Map
V get(K key)//通过key获取到对应的value。如果key不存在，则返回null
boolean containsKey(K key)//查询某个key是否存在

for (String key : map.keySet()) {
    Integer value = map.get(key);
    System.out.println(key + " = " + value);
}//遍历key

for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
    System.out.println(key + " = " + value);
}//同时遍历key和value
```

## Set

```java
//如果我们只需要存储不重复的key，并不需要存储映射的value，那么就可以使用Set
boolean add(E e)//将元素添加进Set<E>
boolean remove(Object e)//将元素从Set<E>删除
boolean contains(Object e)//判断是否包含元素
```

## Collections

```java
Collections.sort(list);//排序
Collections.shuffle(list);// 洗牌算法shuffle可以随机交换List中的元素位置:

```

## Lambda表达式

```java
String[] array = new String[] { "Apple", "Orange", "Banana", "Lemon" };
Arrays.sort(array, (s1, s2) -> {
    return s1.compareTo(s2);
});

//直接传入方法引用
public class Main {
    public static void main(String[] args) {
        String[] array = new String[] { "Apple", "Orange", "Banana", "Lemon" };
        Arrays.sort(array, Main::cmp);
        System.out.println(String.join(", ", array));
    }

    static int cmp(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
```

## Stream

创建Stream最简单的方式是直接用Stream.of()静态方法

```java
import java.util.stream.Stream;
public class Main {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("A", "B", "C", "D");
        // forEach()方法相当于内部循环调用，
        // 可传入符合Consumer接口的void accept(T t)的方法引用：
        stream.forEach(System.out::println);
    }
}



//基于Supplier创建的Stream会不断调用Supplier.get()方法来不断产生下一个元素，这种Stream保存的不是元素，而是算法，它可以用来表示无限序列
```

第二种创建Stream的方法是基于一个数组或者Collection，这样该Stream输出的元素就是数组或者Collection持有的元素

```java
public static void main(String[] args) {
    Stream<String> stream1 = Arrays.stream(new String[] { "A", "B", "C" });
    Stream<String> stream2 = List.of("X", "Y", "Z").stream();
    stream1.forEach(System.out::println);
    stream2.forEach(System.out::println);
}

```

把数组变成Stream使用Arrays.stream()方法。对于Collection（List、Set、Queue等），直接调用stream()方法就可以获得Stream。
上述创建Stream的方法都是把一个现有的序列变为Stream，它的元素是固定的。



基于`Supplier`创建的`Stream`会不断调用`Supplier.get()`方法来不断产生下一个元素，这种`Stream`保存的不是元素，而是算法，它可以用来表示无限序列

例如，我们编写一个能不断生成自然数的`Supplier`，它的代码非常简单，每次调用`get()`方法，就生成下一个自然数

```java
import java.util.function.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Stream<Integer> natual = Stream.generate(new NatualSupplier());
        // 注意：无限序列必须先变成有限序列再打印:
        natual.limit(20).forEach(System.out::println);
    }
}

class NatualSupplier implements Supplier<Integer> {
    int n = 0;
    public Integer get() {
        n++;
        return n;
    }
}
```

`IntStream`、`LongStream`和`DoubleStream`这三种使用基本类型的`Stream`，它们的使用方法和泛型`Stream`没有大的区别，设计这三个`Stream`的目的是提高运行效率:

```java
// 将int[]数组变为IntStream:
IntStream is = Arrays.stream(new int[] { 1, 2, 3 });
// 将Stream<String>转换为LongStream:
LongStream ls = List.of("1", "2", "3").stream().mapToLong(Long::parseLong);
```

`Stream.map()`是`Stream`最常用的一个转换方法

所谓`map`操作，就是把一种操作运算，映射到一个序列的每一个元素上

```java
Stream<Integer> s = Stream.of(1, 2, 3, 4, 5);
Stream<Integer> s2 = s.map(n -> n * n);
```



## PriorityQueue

```java
class Pair<U, V>{
    public final U first;
    public final V second;
    public Pair(U first, V second){
        this.first = first;
        this.second = second;
    }
}

class MyComparator implements Comparator<Pair<Integer, Integer>>{
    @Override
    public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b){
        return Integer.compare(a.second, b.second);
    }
}

PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(new MyComparator());

```

