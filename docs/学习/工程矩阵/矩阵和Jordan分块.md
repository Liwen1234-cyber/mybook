## $\lambda$矩阵和 Jordan 分块

**多项式**

定义：$n$ 是非负整数，$\mathbb {F}$ 是一个数域，$a_0,a_1,...,a_n\in\mathbb {F}$

$$
f(\lambda)=a_n\lambda^n+a_{n-1}\lambda^{n-1}+···a_1\lambda+a_0
$$

称为数域上关于 $\lambda$ 的一元多项式

如果 $a_n\neq 0$，则称 $a_n\lambda^n$ 为 $f (\lambda)$ 的首项，$n$ 称为多项式的次数，记为 $\partial (f (\lambda))$，于是 $\partial (f (\lambda))=n$

如果 $a_0=a_1=・・・=a_n=0$，称该多项式为**零多项式**，规定 $\partial (f (\lambda))=-∞$

如果 $a_0\neq 0, a_1=・・・=a_n=0$，称该多项式为**零次多项式**，$\partial (f (\lambda))=0$，即该多项式为非零常数

**多项式的带余除法**

定义：$f (\lambda),g (\lambda)\in\mathbb {F}[\lambda]$，如果 $g (\lambda)\neq 0$，则存在 $q (\lambda),r (\lambda)\in \mathbb {F}[\lambda]$，使得

$$
f(\lambda)=g(\lambda)q(\lambda)+r(\lambda)
$$

其中，要么 $r (\lambda)=0$，要么 $r (\lambda)\neq 0$ 且 $\partial (r (\lambda))<\partial (g (\lambda))$

$q (\lambda)$ 称为 $g (\lambda)$ 除 $f (\lambda)$ 的商，$r (\lambda)$ 称为余式

如果 $r (\lambda)=0$，则称 $g (\lambda)$ 整除 $f (\lambda)$，记为 $g (\lambda)|f (\lambda)$

**多项式的公因式，公倍式**

-   $f (\lambda),g (\lambda),d (\lambda)\in \mathbb {F}[\lambda]$，如果 $d (\lambda)|f (\lambda)$ 且 $d (\lambda)|g (\lambda)$，则称 $d (\lambda)$ 为 $f (\lambda),g (\lambda)$ 的公因式
-   $f (\lambda),g (\lambda),d (\lambda)\in \mathbb {F}[\lambda]$，如果 $f (\lambda)|d (\lambda)$ 且 $g (\lambda)|d (\lambda)$，则称 $d (\lambda)$ 为 $f (\lambda),g (\lambda)$ 的公倍式
-   最大公因式 GCD：次数最大的公因式
-   最小公倍式 LCM：次数最小的公倍式

如果 $GCD (f (\lambda),g (\lambda))=1$，$f (\lambda)$ 和 $g (\lambda)$ 称为互质

**质因式分解**

$$
f(\lambda)=(q_1(\lambda))^{r_1}(q_2(\lambda))^{r_2}···(q_s(\lambda))^{r_s}
$$

其中 $q_i (\lambda)$ 为不可约多项式，即 $q_i (\lambda)$ 不能表示成两个次数比 $q_i (\lambda)$ 低的多项式的乘积

> 类比实数域中的，任何一个合数都可以分解为几个质数的乘积

一个多项式是否可约，关键要看数域 $\mathbb {F}$，例如

$$
\mathbb {F}=\mathbb {R},\ \lambda^2+1,\ 不可约 \\
\mathbb {F}=\mathbb {C},\ \lambda^2+1=(\lambda+i)(\lambda-i),\ 可约
$$

**常见数域的不可约多项式**

$\mathbb {R}[\lambda]$ 上不可约多项式只有两种

$$
\begin{aligned}
&a\lambda+b \ \ (a,b\in \mathbb{R}\  \&\ a\neq 0)\\
&a\lambda^2+b\lambda+c \ \ (a, b, c\in \mathbb{R} \  \& \ a\neq 0 \ \& \ b^2-4ac < 0)
\end{aligned}
$$

$\mathbb {C}[\lambda]$ 上不可约多项式只有一种

$$
a\lambda+b \ \ (a,b\in \mathbb{C}\ \&\ a\neq 0)
$$

___

**$\lambda$ 矩阵**

以多项式为元素的矩阵称为多项式矩阵，简称为 $\lambda$ 矩阵。记号 $\mathbb {F}^{m\times n}[\lambda]$ 表示所有 $m$ 行 $n$ 列的 $\lambda$ 矩阵的集合，矩阵的元素是系数在 $\mathbb {F}$ 中的 $\lambda$ 的多项式。也就是说，$A (\lambda)\in \mathbb {F}^{m\times n}[\lambda]$ 表示 $A (\lambda)=[a_{ij}(\lambda)]_{m\times n}$，其中，$a_{ij}(\lambda)\in \mathbb {F}[\lambda]$

方阵 $A$ 的特征矩阵 $\lambda I-A$ 也是 $\lambda$ 矩阵，例如

$$
A = (a_{ij})_{n\times m}\\
\lambda I-A = \begin{bmatrix}\lambda-a_{11}&-a_{12}&\cdots &-a_{1n}\\-a_{21}&\lambda-a_{22}&\cdots & -a_{2n}\\ \vdots \\-a_{n1}&-a_{n2}&\cdots & \lambda-a_{nn}\end{bmatrix}
$$

**数字矩阵是 $\lambda$ 矩阵的特例（由零多项式或零次多项式为元素构成的矩阵）**

**以多项式为元素的矩阵和以矩阵为系数的多项式**

如

$$
A(\lambda)=\begin{bmatrix}\lambda^2+\lambda+1&\lambda-1\\2\lambda&1\end{bmatrix}
$$

可以写成

$$
A(\lambda)=\begin{bmatrix}1&0\\0&0\end{bmatrix}\lambda^2+\begin{bmatrix}1&1\\2&0\end{bmatrix}\lambda+\begin{bmatrix}1&-1\\0&1\end{bmatrix}
$$

多项式矩阵和通常矩阵的主要区别在于：其元素所在的运算系统 —— 多项式环 $\mathbb {F}[x]$—— 不是一个域，所以通常矩阵的性质中，涉及到除法的，对于多项式矩阵不再成立

**$\lambda$ 矩阵的秩**

$\lambda$ 矩阵的秩，也用 rank 表示，是指其值为非零多项式的子行列式的最大阶数。换言之，多项式矩阵的秩为 $r$ 是指：存在 $r$ 阶子行列式，其值为非零多项式；且所有阶数 $≥r+1$ 的子行列式的值均为零多项式。零矩阵的秩为 0

**可逆的 $\lambda$ 矩阵**

一个 $n$ 阶 $\lambda$ 矩阵是可逆的，若存在多项式矩阵 $V (\lambda)\in \mathbb {F}^{n\times n}[\lambda]$ 使得

$$
U(\lambda)V(\lambda)=V(\lambda)U(\lambda)=I_n
$$

这里 $I_n$ 是 $n$ 阶单位阵，其中称为 $U (\lambda)$ 的逆矩阵，记为 $U^{-1}(\lambda)$

**定理：**一个 $n$ 阶 $\lambda$ 矩阵 $U (\lambda)$ 可逆的充要条件是 $\det U (\lambda)$ 是一个非零常数

> 注：$n$ 阶 $\lambda$ 矩阵 $U (\lambda)$ 的秩为 $n$，不等价于 $U (\lambda)$ 可逆，这是与数字矩阵不相同之处，例如 $U (\lambda)=\begin {bmatrix}\lambda &1\\1&\lambda\end {bmatrix}$ 的秩为 2，但是它不可逆

___

**Jordan 块**

定义形如 $J (\lambda_i)\begin {bmatrix}\lambda_i&1\\&\lambda_i&\ddots\\&&\ddots&1\\&&&\lambda_i\end {bmatrix}_{k\times k}$ 的矩阵称为 **Jordan 块**。Jordan 块是一个上三角矩阵，其主对角线上的元素均相等；主对角线上面一条斜线的元素均为 1；其余元素均为 0

特殊地，当 $k=1$ 时，一阶 Jordan 块是 $[\lambda_i]$

**Jordan 形矩阵**

形如 $J=\begin {bmatrix} J (\lambda_1)\\&J (\lambda_2)\\&&\ddots\\&&&J (\lambda_s)\end {bmatrix}$（其中 $J (\lambda_i)$ 均为 Jordan 块）的矩阵称为 **Jordan 形矩阵**。Jordan 形矩阵是一个分块对角阵，其中每一个小分块都是 Jordan 块

**Jordan 标准形**

若矩阵 $A$ 与 Jordan 形矩阵 $J$ 相似，则称 $J$ 是 $A$ 的 Jordan 标准形

___

例 1

判断下列矩阵是否为 Jordan 形矩阵

$$
\begin{bmatrix}1&0&0\\0&2&0\\0&0&2\end{bmatrix},\begin{bmatrix}1&1&0\\0&2&1\\0&0&2\end{bmatrix},\begin{bmatrix}1&1&0\\0&2&0\\0&0&2\end{bmatrix},\begin{bmatrix}1&0&0\\0&2&1\\0&0&2\end{bmatrix}\\
\begin{bmatrix}2&1&0\\0&0&1\\0&0&0\end{bmatrix},\begin{bmatrix}2&0&0\\0&0&1\\0&0&0\end{bmatrix},\begin{bmatrix}1&0&0&0\\0&2&1&0\\0&0&2&0\\0&0&0&1\end{bmatrix}
$$

**解：**对于正确的 Jordan 形矩阵，用黑线给出了分块形式；对于错误的 Jordan 形矩阵，指出了需要修改的元素

![](./images/cH9Mo6.png)

___

**Jordan 标准形的存在性、唯一性**

**对矩阵 $A$ 的 Jordan 标准形中每个分块改变排列顺序，得到的依然是 Jordan 标准形**

若 $J=\begin {bmatrix} J_1\\&J_2\\&&\ddots\\&&&J_s\end {bmatrix}$ 是矩阵 $A$ 的 Jordan 标准形，而 $K=\begin {bmatrix} J_{i_1}\\&J_{i_2}\\&&\ddots \\&&&J_{i_s}\end {bmatrix}$，其中 $J_{i_1},J_{i_2},...,J_{i_s}$ 是 $J_1,J_2,...,J_s$ 的一个排列，则 $K$ 也是 $A$ 的 Jordan 标准形

那么所谓的 Jordan 标准形的唯一性是如何定义的呢？**除了相差 Jordan 块的次序外，矩阵的 Jordan 标准形是存在的、唯一的**

**性质**

1.  若 $A$ 与 $J$ 相似，$\lambda_0$ 是数，则对一切正整数 $k$，$\mathrm {rank}((A-\lambda_0E)^k)=\mathrm {rank}((J-\lambda_0E)^k)$
2.  若 $n\times n$ 矩阵 $N=\begin {bmatrix} 0&1\\&0&\ddots\\&&\ddots&1\\&&&1\end {bmatrix}$，则 $\mathrm {rank}(N^{k-1})-\mathrm {rank}(N^k)=\begin {cases} 1, 若 k≤n\\0, 若 k>n\end {cases}$
3.  若 $J$ 是 Jordan 矩阵，则 $\mathrm {rank}((J-\lambda_0E)^{k-1})-\mathrm {rank}((J-\lambda_0E)^l)$ 等于 $J$ 中阶数 $≥k$ 的，以 $\lambda_0$ 为主对角元的 Jordan 块的块数

**定理**

设 $\lambda_0$ 是矩阵 $A$ 的特征值，则 $A$ 的 Jordan 标准形中以 $\lambda_0$ 为主对角元的 $k$ 阶 Jordan 块的块数为

$$
\mathrm{rank}(B^{k-1})-2·\mathrm{rank}(B^k)+\mathrm{rank}(B^{k+1})
$$

其中，$B=A-\lambda_0E$

根据这个定理，我们只要针对矩阵 $A$ 的每一个特征值 $\lambda_0$，依次求解相应于这个特征值的每一阶 Jordan 块的块数，就可以把矩阵 $A$ 的 Jordan 标准形完全求解出来。但实际上，这一工作需要大量的计算，在实际中不会采用。我们只需要明确，**根据一个矩阵 $A$ 的特征值、$A-\lambda_0E$、阶数 $k$ 等信息就能够完全确定矩阵 $A$ 对应的 Jordan 标准形**

___

**Jordan 标准形的求法**

-   求矩阵 $A$ 的特征多项式 $|\lambda E-A| = (\lambda - \lambda_1)^{k_1}(\lambda-\lambda_2)^{k_2}...(\lambda - \lambda_s)^{k_s}$，其中 $k_i$ 是特征值 $\lambda_i$ 的**代数重数，决定了对角线上特征值 $\lambda_i$ 的个数**；
-   对 $\lambda_i$，由 $(A-\lambda_i E) X=0$，求 $A$ 的**线性无关的特征向量** $\alpha_1,\alpha_2, ...,\alpha_{t_i}$，其中 $t_i$ 是特征值 $\lambda_i$ 的**几何重数，决定了 Jordan 块的个数**；
  
    -   如果 $k_i = t_i$，即**代数重数等于几何重数**，说明 $\lambda_i$ 对应的 Jordan 块是对角阵；
    -   如果 $t_i < k_i$，就选择合适的特征向量 $\alpha_j$，利用方程 **${\color {red} {(A-\lambda_i E) = \alpha_j}}$ 求 Jordan 链**，确定每一个小 Jordan 块的阶数。
-   将所有特征值 $\lambda_i$ 对应的 Jordan 块组合起来，形成 Jordan 矩阵 $J_A$

___

例 2

求矩阵 $A=\begin {bmatrix}-1&-2&6\\-1&0&3\\-1&-1&4\end {bmatrix}$ 的 Jordan 标准形

**解：**容易求得矩阵 $A$ 的特征多项式为 $C (\lambda)=(\lambda-1)^3$，所以 $A$ 的 Jordan 标准形矩阵只可能是以下三种情况

$$
\begin{bmatrix}1&0&0\\0&1&0\\0&0&1\end{bmatrix},\begin{bmatrix}1&0&0\\0&1&1\\0&0&1\end{bmatrix},\begin{bmatrix}1&1&0\\0&1&1\\0&0&1\end{bmatrix}
$$

验证第一个矩阵，若 $A$ 与 $\begin {bmatrix} 1&0&0\\0&1&0\\0&0&1\end {bmatrix}$ 相似，那么存在一个可逆矩阵 $P$，使得 $P^{-1} AP=J=E$，则 $AP=P$，即 $A=E$，与题意不符，故排除

验证第二和第三个矩阵，记第二个矩阵为 $J_2$，第三个矩阵为 $J_3$，通过计算得 $\mathrm {rank}(J_2-E)=1,\mathrm {rank}(J_3-E)=2$，因为矩阵 $A$ 相似于 $J$，所以有 $\mathrm {rank}(A-E)=\mathrm {rank}(J-E)$，通过计算得 $\mathrm {rank}(A-E)=1$，由此可知最终的 Jordan 标准形矩阵就是 $J_2$

___

例 3

求矩阵 $B=\begin {bmatrix} 13&16&16\\-5&-7&-6\\-6&-8&-7\end {bmatrix}$ 的 Jordan 标准形

**解：**容易求得矩阵 $B$ 的特征多项式为 $C (\lambda)=(\lambda+3)(\lambda-1)^2$，所以 $B$ 的 Jordan 标准形只可能是以下两种情况

$$
J_1=\begin{bmatrix}-3&0&0\\0&1&0\\0&0&1\end{bmatrix}\\
J_2=\begin{bmatrix}-3&0&0\\0&1&1\\0&0&1\end{bmatrix}
$$

通过计算得 $\mathrm {rank}(J_1-E)=1,\mathrm {rank}(J_2-E)=2$，因为 $\mathrm {rank}(B-E)=2$，所以最终的 Jordan 标准形矩阵就是 $J_2$

___

例 4

用初等变换把 $\lambda$ 矩阵 $\begin {bmatrix} 1-\lambda&\lambda^2&\lambda\\\lambda&\lambda&-\lambda\\1+\lambda^2&\lambda^2&\lambda\end {bmatrix}$ 化为标准形

**解：**

$$
\begin{aligned}
\begin{bmatrix}1-\lambda&\lambda^2&\lambda\\\lambda&\lambda&-\lambda\\1+\lambda^2&\lambda^2&\lambda\end{bmatrix}&\to\begin{bmatrix}1&0&\lambda\\0&\lambda+\lambda^2&-\lambda\\1+\lambda+\lambda^2&0&\lambda\end{bmatrix}\\
&\to\begin{bmatrix}1&0&\lambda\\0&\lambda+\lambda^2&-\lambda\\0&0&-\lambda^3-\lambda^2\end{bmatrix}\\
&\to\begin{bmatrix}1&0&0\\0&\lambda+\lambda^2&-\lambda\\0&0&-\lambda^3-\lambda^2\end{bmatrix}\\
&\to\begin{bmatrix}1&0&0\\0&\lambda&0\\0&0&\lambda^2(1+\lambda^2)\end{bmatrix}
\end{aligned}
$$

___

例 5

证明：Jordan 块

$$
J(a)=\begin{bmatrix}a&1&0\\0&a&1\\0&0&a\end{bmatrix}
$$

相似于矩阵

$$
\begin{bmatrix}a&\epsilon &0\\0&a&\epsilon\\0&0&a\end{bmatrix}
$$

其中 $\epsilon \neq 0$ 为任意实数

**证：**由定理 **$A\sim B$ 的充要条件是 $A,B$ 有相同的不变因子**，即判断下面两个 $\lambda$ 矩阵

$$
\begin{bmatrix}\lambda -a&-1&0\\0&\lambda-a&-1\\0&0&\lambda-a\end{bmatrix},\begin{bmatrix}\lambda-a&-\epsilon&0\\0&\lambda-a&-\epsilon\\0&0&\lambda-a\end{bmatrix}
$$

是否等价，容易求出这两个 $\lambda$ 矩阵的不变因子均为 $1,1,(\lambda-a)^2$，故 $J (a)$ 与 $\begin {bmatrix} a&\epsilon &0\\0&a&\epsilon\\0&0&a\end {bmatrix}$ 相似

___

例 6

已知 10 阶矩阵 $A=\begin {bmatrix} a&1\\&a&1\\&&\ddots&\ddots\\&&&\ddots &1\\&&&&a\end {bmatrix}_{10\times 10},B=\begin {bmatrix} a&1\\&a&1\\&&\ddots&\ddots\\&&&\ddots &1\\\epsilon&&&&a\end {bmatrix}_{10\times 10}$，其中 $\epsilon=10^{-10}$，证明 $A\nsim B$

**证：**只需判断 $\lambda E-A$ 与 $\lambda E-B$ 是否等价，对于 $\lambda$ 矩阵

$$
A=\begin{bmatrix}\lambda-a&-1\\&\lambda-a&-1\\&&\ddots&\ddots\\&&&\ddots &-1\\&&&&\lambda-a\end{bmatrix}_{10\times 10}
$$

其不变因子为 $1,1,...,(\lambda-a)^{10}$；对于 $\lambda$ 矩阵

$$
B=\begin{bmatrix}\lambda-a&-1\\&\lambda-a&-1\\&&\ddots&\ddots\\&&&\ddots &-1\\-\epsilon&&&&\lambda-a\end{bmatrix}_{10\times 10}
$$

其不变因子为 $1,1,...,(\lambda-a)^{10}-\epsilon$，显然 $A$ 与 $B$ 不具有相同的不变因子，从而 $A\nsim B$

___

例 7

设 $A\neq 0,A^k=0\ \ (k≥2)$，证明 $A$ 不能与对角矩阵相似

**证：**用反证法，设 $A$ 可以对角化，则存在可逆矩阵 $P$ 使得

$$
P^{-1}AP=\begin{bmatrix}\lambda_1\\&\ddots\\&&\lambda_n\end{bmatrix}
$$

因为 $A^k=0$，所以

$$
A^k=(P\begin{bmatrix}\lambda_1\\&\ddots\\&&\lambda_n\end{bmatrix}P^{-1})^k=0
$$

即

$$
\begin{bmatrix}\lambda_1\\&\ddots\\&&\lambda_n\end{bmatrix}=0
$$

由此可知 $\lambda_1=\lambda_2=・・・=\lambda_n=0$，故 $P^{-1} AP=\begin {bmatrix} 0\\&\ddots\\&&0\end {bmatrix}$，这表明 $A=0$，与已知 $A\neq 0$ 矛盾，故 $A$ 不能与对角矩阵相似

___

例 8

已知 $A^2=A$，证明 $A$ 相似于矩阵 $\begin {bmatrix} 1\\&\ddots\\&&1\\&&&0\\&&&&\ddots\\&&&&&0\end {bmatrix}$

**证：**设矩阵 $A$ 的 Jordan 标准形为

$$
J=\begin{bmatrix}J_1\\&J_2\\&&\ddots\\&&&J_s\end{bmatrix},J_i=\begin{bmatrix}\lambda_i&1\\&\lambda_i&1\\&&\ddots&\ddots\\&&&&1\\&&&&\lambda_i\end{bmatrix}_{n_i\times n_i}
$$

则存在可逆矩阵 $P$，使 $P^{-1} A^kP=J$

由于 $A^2=A$，所以 $J^2=(P^{-1} AP)=P^{-1} A^2P=J=P^{-1} AP$，则

$$
J_i^2=J_i,\quad{i=1,2,...,s}
$$

即

$$
\begin{bmatrix}\lambda_i^2&2\lambda_i&1&0&\cdots&0\\0&\lambda_i^2&2\lambda_i&1\\\vdots &&&\ddots&\ddots&\vdots\\&&&&&1\\&&&&&2\lambda_i\\0&&\cdots &&0 &\lambda_i^2\end{bmatrix}=\begin{bmatrix}\lambda_i&1\\&\lambda_i&1\\&&\ddots&\ddots\\&&&&1\\&&&&\lambda_i\end{bmatrix}
$$

从而 $\lambda_i=0$ 或 $\lambda_i=1$，所以 $J$ 为一个对角矩阵且主对角线上的元素只能为 1 或 0，只当调整对角线上元素的顺序，可得方阵 $\begin {bmatrix} 1\\&\ddots\\&&1\\&&&0\\&&&&\ddots\\&&&&&0\end {bmatrix}$

而且 $A$ 相似于此方阵

___

例 9

求矩阵 $A=\begin {bmatrix} 1&2&0\\0&2&0\\-2&-2&1\end {bmatrix}$ 的 Jordan 标准形及其变换矩阵 $P$

**解：**因为

$$
\lambda E-A=\begin{bmatrix}\lambda-1&-2&0\\0&\lambda-2&0\\2&2&\lambda-1\end{bmatrix}\to\begin{bmatrix}1\\&1\\&&(\lambda-2)(\lambda-1)^2\end{bmatrix}
$$

所以 $A$ 的 Jordan 标准形可能为

$$
\begin{bmatrix}2&0&0\\0&1&1\\0&0&1\end{bmatrix},\begin{bmatrix}1&0&0\\0&1&0\\0&0&2\end{bmatrix}
$$

因为 $\mathrm {rank}(E-A)=2$，故 $A$ 的 Jordan 标准形只能是 $J=\begin {bmatrix} 2&0&0\\0&1&1\\0&0&1\end {bmatrix}$

故 $\exists P$，使得 $AP=PJ$，令 $P=[X_1,X_2,X_3]$，则

$$
[AX_1,AX_2,AX_3]=[X_1,X_2,X_3]\begin{bmatrix}2&0&0\\0&1&1\\0&0&1\end{bmatrix}\\
\Rightarrow \begin{cases}AX_1=2X_1\\AX_2=X_2\\AX_3=X_2+X_3\end{cases}\\
\Rightarrow \begin{cases}(2E-A)X_1=0\\(E-A)X_2=0\\(E-A)X_2=X_3\end{cases}
$$

由齐次线性方程组 $(2E-A) X_1=0$ 可得 $X_1=[2,1,-6]^T$

由齐次线性方程租 $(E-A) X_2=0$ 可得 $X_2=[0,0,1]^T$，带入 $(E-A) X_2=X_3$ 得 $X_3=[-\frac {1}{2},0,0]^T$

所以 $P=[X_1,X_2,X_3]=\begin {bmatrix} 2&0&-\frac {1}{2}\\3&0&0\\-6&1&0\end {bmatrix}$

___

例 10

已知 $A=\begin {bmatrix} 2&1&0\\0&0&1\\0&1&0\end {bmatrix}$，求 $A^{100}$

**解：**

$$
\lambda E-A=\begin{bmatrix}\lambda-2&-1&0\\0&\lambda&-1\\0&-1&\lambda\end{bmatrix}\to\begin{bmatrix}1\\&1\\&(\lambda^2-1)(\lambda-2)\end{bmatrix}
$$

所以 $A$ 的 Jordan 标准形为 $J=\begin {bmatrix}-1&0&0\\0&1&0\\0&0&2\end {bmatrix}$

故 $\exists P$，使得 $AP=PJ$，令 $P=[X_1,X_2,X_3]$，则

$$
[AX_1,AX_2,AX_3]=[X_1,X_2,X_3]\begin{bmatrix}-1&0&0\\0&1&0\\0&0&2\end{bmatrix}\\
\Rightarrow \begin{cases}AX_1=-X_1\\AX_2=X_2\\AX_3=2X_3\end{cases}\\
\Rightarrow \begin{cases}(E+A)X_1=0\\(E-A)X_2=0\\(2E-A)X_3=0\end{cases}\\
\Rightarrow \begin{cases}X_1=(1,-3,3)^T\\X_2=(-1,1,1)^T\\X_3=(1,0,0)^T\end{cases}
$$

所以 $P=\begin {bmatrix} 1&-1&1\\-3&1&0\\3&1&0\end {bmatrix},P^{-1}=\begin {bmatrix} 0&-\frac {1}{6}&\frac {1}{6}\\0&\frac {1}{2}&\frac {1}{2}\\1&\frac {2}{3}&\frac {1} 3\end {bmatrix}$，则

$$
\begin{aligned}
A^{100}&=PJ^{100}P^{-1}\\
&=\begin{bmatrix}1&-1&1\\-3&1&0\\3&1&0\end{bmatrix}\begin{bmatrix}(-1)^{100}&0&0\\0&1^{100}&0\\0&0&2^{100}\end{bmatrix}\begin{bmatrix}0&-\frac{1}{6}&\frac{1}{6}\\0&\frac{1}{2}&\frac{1}{2}\\1&\frac{2}{3}&\frac{1}3\end{bmatrix}\\
&=\begin{bmatrix}1&-1&2^{100}\\-3&1&0\\3&1&0\end{bmatrix}\begin{bmatrix}0&-\frac{1}{6}&\frac{1}{6}\\0&\frac{1}{2}&\frac{1}{2}\\1&\frac{2}{3}&\frac{1}3\end{bmatrix}\\
&=\begin{bmatrix}2^{100}&\frac{2^{99}-2}{3}&\frac{2^{100}-1}{3}\\0&1&0\\0&0&1\end{bmatrix}
\end{aligned}
$$