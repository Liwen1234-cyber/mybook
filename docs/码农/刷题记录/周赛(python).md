# 周赛

## 双周赛87

### [2409. 统计共同度过的日子数](https://leetcode.cn/problems/count-days-spent-together/)

Alice 和 Bob 计划分别去罗马开会。

给你四个字符串 `arriveAlice` ，`leaveAlice` ，`arriveBob` 和 `leaveBob` 。Alice 会在日期 `arriveAlice` 到 `leaveAlice` 之间在城市里（**日期为闭区间**），而 Bob 在日期 `arriveBob` 到 `leaveBob` 之间在城市里（**日期为闭区间**）。每个字符串都包含 5 个字符，格式为 `"MM-DD"` ，对应着一个日期的月和日。

请你返回 Alice和 Bob 同时在罗马的天数。

你可以假设所有日期都在 **同一个** 自然年，而且 **不是** 闰年。每个月份的天数分别为：`[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]` 。

解答:

```python
DAY_SUM = list(accumulate((31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31), initial = 0))

def cal_day(data: str) -> int:
    return DAY_SUM[int(data[:2])-1] + int(data[3:])
    
class Solution:
    def countDaysTogether(self, arriveAlice: str, leaveAlice: str, arriveBob: str, leaveBob: str) -> int:
        start = cal_day(max(arriveAlice, arriveBob))
        end = cal_day(min(leaveAlice, leaveBob))
        return max(end - start + 1, 0)
```

### [2410. 运动员和训练师的最大匹配数](https://leetcode.cn/problems/maximum-matching-of-players-with-trainers/)

给你一个下标从 **0** 开始的整数数组 `players` ，其中 `players[i]` 表示第 `i` 名运动员的 **能力** 值，同时给你一个下标从 **0** 开始的整数数组 `trainers` ，其中 `trainers[j]` 表示第 `j` 名训练师的 **训练能力值** 。

如果第 `i` 名运动员的能力值 **小于等于** 第 `j` 名训练师的能力值，那么第 `i` 名运动员可以 **匹配** 第 `j` 名训练师。除此以外，每名运动员至多可以匹配一位训练师，每位训练师最多可以匹配一位运动员。

请你返回满足上述要求 `players` 和 `trainers` 的 **最大** 匹配数。

解答:

```python
class Solution:
    def matchPlayersAndTrainers(self, players: List[int], trainers: List[int]) -> int:
        players.sort()
        trainers.sort()
        cnt = 0
        j, m = 0, len(trainers)
        for i, p in enumerate(players):
            while j < m and p > trainers[j]:
                j += 1
            if j >= m:    
                return i
            j += 1
        return len(players)
```



### [2411. 按位或最大的最小子数组长度](https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/)

给你一个长度为 `n` 下标从 **0** 开始的数组 `nums` ，数组中所有数字均为非负整数。对于 `0` 到 `n - 1` 之间的每一个下标 `i` ，你需要找出 `nums` 中一个 **最小** 非空子数组，它的起始位置为 `i` （包含这个位置），同时有 **最大** 的 **按位或运算值** 。

- 换言之，令 `Bij` 表示子数组 `nums[i...j]` 的按位或运算的结果，你需要找到一个起始位置为 `i` 的最小子数组，这个子数组的按位或运算的结果等于 `max(Bik)` ，其中 `i <= k <= n - 1` 。

一个数组的按位或运算值是这个数组里所有数字按位或运算的结果。

请你返回一个大小为 `n` 的整数数组 `answer`，其中 `answer[i]`是开始位置为 `i` ，按位或运算结果最大，且 **最短** 子数组的长度。

**子数组** 是数组里一段连续非空元素组成的序列。

**方法一：利用或运算的性质**

首先，我们有如下 $O(n^2)$ 的暴力算法：

    从左到右正向遍历 nums，对于 x=nums[i]，从 i−1 开始倒着遍历 nums[j]，更新 nums[j]=nums[j] ∣ x，如果 nums[j] 变大，则更新 ans[j]=i−j+1。

下面来优化该算法。

我们可以把二进制数看成集合，二进制数第 i 位为 1 表示 i 在集合中。两个二进制数的或，就可以看成是两个集合的并集。

对于两个二进制数 a 和 b，如果 a ∣ b=a，从集合的角度上看，b 对应的集合是 a 对应的集合的子集。

据此我们可以提出如下改进后的算法：

从左到右正向遍历 nums，对于 x=nums[i]，从 i−1 开始倒着遍历 nums[j]：

    如果 nums[j] ∣ x=nums[j]，说明 nums[j] 可以变大（集合元素增多），更新 nums[j]=nums[j] ∣ x；
    如果 nums[j] ∣ x=nums[j]，从集合的角度看，此时 x 不仅是 nums[j] 的子集，同时也是 nums[k] (k<j) 的子集（因为循环保证了每个集合都是其左侧相邻集合的子集），那么后续的循环都无法让元素变大，退出循环；
    在循环中，如果 nums[j] 可以变大，则更新 ans[j]=i−j+1。

解答:

```python
class Solution:
    def smallestSubarrays(self, nums: List[int]) -> List[int]:
        n = len(nums)
        res = [0] * n
        ors = []
        for i, x in enumerate(nums):
            res[i] = 1
            for j in range(i-1, -1, -1):
                if nums[j] | x == nums[j]:
                    break
                nums[j] = nums[j] | x
                res[j] = i-j+1

        return res
```



**方法二：更加通用的模板(区间维护的思想)**

该模板可以做到

    求出所有子数组的按位或的结果，以及值等于该结果的子数组的个数。
    求按位或结果等于任意给定数字的子数组的最短长度/最长长度。

末尾列出了一些题目，均可以用该模板秒杀。

思考：对于起始位置为 i 的子数组的按位或，至多有多少种不同的结果？

根据或运算的性质，我们可以从 x=nums[i] 开始，不断往右扩展子数组，按位或的结果要么使 x 不变，要么让 x 的某些比特位的值由 0 变 1。最坏情况下从 x=0 出发，每次改变一个比特位，最终得到 $2^{29}−1<10^9$，因此至多有 30 种不同的结果。这意味着我们可以递推计算所有按位或的结果。

另一个结论是，相同的按位或对应的子数组右端点会形成一个连续的区间，从而保证下面去重逻辑的正确性（这一性质还可以用来统计按位或结果及其对应的子数组的个数）。

据此，我们可以倒着遍历 nums，在遍历的同时，用一个数组 ors 维护以 i 为左端点的子数组的按位或的结果，及其对应的子数组右端点的最小值。继续遍历到 nums[i−1] 时，我们可以把 nums[i−1] 和 ors 中的每个值按位或，合并值相同的结果。

这样在遍历时，ors 中值最大的元素对应的子数组右端点的最小值，就是要求的最短子数组的右端点。

注：下面代码用到了原地去重的技巧，如果你对此并不熟悉，可以先做做 [26. 删除有序数组中的重复项](https://leetcode.cn/problems/remove-duplicates-from-sorted-array/)。

复杂度分析

    时间复杂度：O(nlogU)，其中 n 为 nums 的长度，U=max(nums)。
    空间复杂度：O(logU)。返回值不计入。

可以用模板秒杀的题目

按位或：

[898. 子数组按位或操作](https://leetcode.cn/problems/bitwise-ors-of-subarrays/)

按位与：

[1521. 找到最接近目标值的函数值](https://leetcode.cn/problems/find-a-value-of-a-mysterious-function-closest-to-target/)

最大公因数（GCD）：

- [Codeforces 475D. CGCDSSQ](https://codeforces.com/problemset/problem/475/D)
- [Codeforces 1632D. New Year Concert](https://codeforces.com/problemset/problem/1632/D)

乘法：

- [蓝桥杯2021年第十二届国赛真题-和与乘积](https://leetcode.cn/link/?target=https%3A%2F%2Fwww.dotcpp.com%2Foj%2Fproblem2622.html)

思考题

如果是异或要怎么做？

依然是倒序遍历，求后缀异或和，然后可以用 421. 数组中两个数的最大异或值 的字典树方法，需要额外存后缀异或和对应的下标，如果有多个相同的，存下标最小的。

解答:

```python
class Solution:
    def smallestSubarrays(self, nums: List[int]) -> List[int]:
        n = len(nums)
        res = [0] * n
        ors = [] # 按位或的值 + 对应子数组的右端点的最小值
        for i in range(n-1, -1, -1):
            num = nums[i]
            ors.append([0, i]) #开启一个新的右端点
            k = 0
            for p in ors:
                p[0] |= num
                if p[0] == ors[k][0]:
                    ors[k][1] = p[1] # 合并相同值，下标取最小的
                else:
                    k += 1
                    ors[k] = p
            del ors[k+1:] #删除重复值
            res[i] = ors[0][1] - i + 1 # 本题只用到了 ors[0]，如果题目改成任意给定数值，可以在 ors 中查找
        
        return res
```

### [2412. 完成所有交易的初始最少钱数](https://leetcode.cn/problems/minimum-money-required-before-transactions/)

给你一个下标从 **0** 开始的二维整数数组 `transactions`，其中`transactions[i] = [costi, cashbacki]` 。

数组描述了若干笔交易。其中每笔交易必须以 **某种顺序** 恰好完成一次。在任意一个时刻，你有一定数目的钱 `money` ，为了完成交易 `i` ，`money >= costi` 这个条件必须为真。执行交易后，你的钱数 `money` 变成 `money - costi + cashbacki` 。

请你返回 **任意一种** 交易顺序下，你都能完成所有交易的最少钱数 `money` 是多少。

「任意一种交易顺序下，都能完成所有交易」意味着要考虑在最坏情况下，需要多少初始钱数 initMoney。

什么是最坏情况？

先亏钱（cost>cashback），再赚钱（cost≤cashback），主打一个欲扬先抑。

初始钱数必须满足，在最穷困潦倒的时候，也能完成交易。

什么时候最穷？完成所有亏钱交易后最穷。

记 totalLose 为所有亏钱的 cost−cashback 之和。

所有情况取最大值，就能保证在任意一种交易顺序下，都能完成所有交易。

- 如果赚钱，即 cost≤cashback，那么 totalLose 加上的是二者的较小值 cost。
- 如果亏钱，即 cost>cashback，那么 totalLose 加上的也是二者的较小值 cashback。

综上所述，初始钱数 initMoney 等于 totalLose 加上 min(cost,cashback) 的最大值。

```python
class Solution:
    def minimumMoney(self, transactions: List[List[int]]) -> int:
        total_loss = mx = 0
        for cost, cashback in transactions:
            total_loss += max(cost - cashback, 0)
            mx = max(mx, min(cost, cashback))
        return total_loss + mx
```

复杂度分析

- 时间复杂度：O(n)，其中 n 为 transactions 的长度。
- 空间复杂度：O(1)，仅用到若干变量。

思考题

如果把题干的「任意一种」改成「至少一种」要怎么做？

可以看[1665. 完成所有任务的最少初始能量](https://leetcode.cn/problems/minimum-initial-energy-to-finish-tasks/)
