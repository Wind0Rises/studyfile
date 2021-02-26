# HashMap与ConcurrentHashMap

## 一、HashMap

### 1.1  JDK1.7中的HashMap

#### 1.1.1 HashMap#put()的流程。

1. 首先判断HashMap的内部数组是否是空的，如果是空的，首先要初始化数组。
2. 判断put操作的Key是否为null，如果为null，进行key为null的put操作。
3. **[计算Key对应的Hash值](#hashmap计算出的hashcode的)**，并计算出给定key对应的内部数组的位置。**[如何计算内部数组的位置](#如何计算内部数组的位置)**
4. 遍历内部数组位置上的元素，判断该Key对应的元素是否存在，如果存在，更新为传进来的新值，并把老值返回回去。
5. 判断是否需要扩展，如果需要扩容进行扩容操作。**如何判断是否需要扩容的**
6. 构建一个新的Entry元素，并把元素添加进去。首先判断对应数组位置上是否存在元素，如果不存在元素，直接把这个新的Entry放在这个数组上，如果位置上存在元素，则把新的Entry元素的next指向已经存在数组位置上的老的Entry元素，并把自己放在数组位置上。


#### 1.1.2 <a id="如何计算内部数组的位置">为什么HashMap的数组容量是2的幂次方</a>

这个问题可以解决如何计算内部数组的位置。具体的原因情况下面分析。

``` java
/** 
 * 假设一个key的hash值的二进制位：1010 0101；内部数组的长度为16， 二进制表示为0001 0000   
 *  
 * 				  高位  低位	
 * hash			: 1010 0101
 * length 		: 0001 0000
 * length - 1	: 0000 1111
 *
 * 进行运算：
 * 				  高位  低位
 * hash			: 1010 0101
 * length - 1	: 0000 1111
 * 结果		   : 0000 0101
 * 
 * 结论：运算结果，高位始终都是0000，低位都是与hash的低位保持一致，低位的值始终在0000 - 1111之间。这个刚好在0 - （length - 1）之间。
 * 
 */
static int indexFor(int h, int length) {
	return h & (length-1);
}
```

从上面分析可以看出，实际参与运算的只有低位的数据，这样会导致计算的出来的位置离散性过小，导致链表的长度过长，会产生大量的极端链表情况。<a id="hashmap计算出的hashcode的">分析如下：</a>

``` java
/**
 * 主要是使hashcode的高低位都参与计算。
 */
final int hash(Object k) {
    int h = hashSeed;
    if (0 != h && k instanceof String) {
        return sun.misc.Hashing.stringHash32((String) k);
    }

    h ^= k.hashCode();

    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}
```



#### 1.1.3 头插法和尾插法



#### 1.1.4 JDK1.7如何判断是否需要扩容

JDK1.7的HashMap是通过if ((size >= threshold) && (null != table[bucketIndex]))判断是否需要进行扩容，

1. 首先判断元素的个数是否大于等于阈值。
2. 并且新增的元素，对应的数组位置不能为空

只有满足上面两个条件才会进行扩容。

``` java
/**
 * if ((size >= threshold) && (null != table[bucketIndex]))进行判断是否进行扩容
 */
void addEntry(int hash, K key, V value, int bucketIndex) {
    if ((size >= threshold) && (null != table[bucketIndex])) {
        resize(2 * table.length);
        hash = (null != key) ? hash(key) : 0;
        bucketIndex = indexFor(hash, table.length);
    }

    createEntry(hash, key, value, bucketIndex);
}
```





### 1.2 JDK1.8中的HashMap



### 1.3 HahsMap的存在的问题

* 线程不安全的？在哪里不安全？？



## 二、ConcurrentHashMap

### 2.1



## 三、其他问题

### 3.1 为什么HashMap的key可以为null但是ConcurrentHashMap的值不能为null。
