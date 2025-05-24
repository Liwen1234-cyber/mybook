## 正规矩阵、Hermite 矩阵

**Schur 引理**

**定义：**设 $A, B \in \mathbb {C}^{n \times n}\left (\right. 或  \left.\mathbb {R}^{n \times n}\right)$，若存在 $U \in U^{n \times n}\left (\right. 或  \left.E^{n \times n}\right)$，使得
$$
{U}^{\mathrm {H}} {A} {U}={U}^{-1} {A} {U}={B}\left (\text {或 } {U}^{\mathrm {T}} {A} {U}={U}^{-1} {A} {U}={B}\right)
$$

则说 $A$ 酉相似（或正交相似）于 $B$  
**（Schur 引理）**任何一个 $n$ 阶复矩阵 $A$ 酉相似于一个上（下）三角矩阵

___

例 1

已知 $A = \begin {bmatrix} 0&3&3\\-1&8&6\\2&-14&-10\end {bmatrix}$，求酉矩阵 $U$，使得 $U^HAU$ 是一个上三角矩阵

**解：**$|\lambda E-A|=\lambda (\lambda+1)^2$，当 $\lambda=0$ 时，$A$ 有单位特征向量

$$
\eta_1 = (\frac{2}{\sqrt{6}},\frac{1}{\sqrt{6}},-\frac{1}{\sqrt{6}})^T
$$

解与 $\eta_1$ 内积为零的方程 $2x_1+x_2-x_3=0$，求得一个单位解向量

$$
\eta_2=[0,\frac{1}{\sqrt{2}},\frac{1}{\sqrt{2}}]^T
$$

解与 $\eta_1,\eta_2$ 内积为零的方程

$$
\begin{cases}
2x_2+x_2-x_3=0\\
x_2+x_3=0
\end{cases}
$$

又求得其一个单位解向量

$$
\eta_3 = [-\frac{1}{\sqrt{3}}, \frac{1}{\sqrt{3}},-\frac{1}{\sqrt{3}}]^T
$$

于是取

$$
U_1=\begin{bmatrix}\frac{2}{\sqrt{6}}&0&-\frac{1}{\sqrt{3}}\\\frac{1}{\sqrt{6}}&\frac{1}{\sqrt{2}}&\frac{1}{\sqrt{3}}\\-\frac{1}{\sqrt{6}}&\frac{1}{\sqrt{2}}&-\frac{1}{\sqrt{3}}\end{bmatrix}
$$

经计算得

$$
U_1^HAU_1=\begin{bmatrix}0 & \frac{50}{\sqrt{12}} & \frac{9}{\sqrt{18}} \\
0 & -5 & -\frac{3}{\sqrt{6}} \\
0 & \frac{32}{\sqrt{6}} & 3\end{bmatrix}=\left[\begin{array}{ccc}
0 & \frac{50}{\sqrt{12}} & \frac{9}{\sqrt{18}} \\
0 & \\
& & A_{1} \\
0 & &
\end{array}\right]
$$

其中

$$
A_1=\begin{bmatrix}-5 & -\frac{3}{\sqrt{6}} \\
 \frac{32}{\sqrt{6}} & 3\end{bmatrix}
$$

可得 $|\lambda E - A_1|=(\lambda +1)^2$，对于 $\lambda =-1$ 时，求得一个单位特征向量 $\gamma_1=[-\frac {3}{\sqrt {105}}, \frac {4\sqrt {6}}{\sqrt {105}}]^T$

再求得一个与 $\gamma_1$ 正交的向量 $\gamma_2=[\frac {4\sqrt {6}}{\sqrt {105}}, \frac {3}{\sqrt {105}}]^T$，令

$$
V_1 = \begin{bmatrix}-\frac{3}{\sqrt{105}} &\frac{4\sqrt{6}}{\sqrt{105}}\\ \frac{4\sqrt{6}}{\sqrt{105}}&\frac{3}{\sqrt{105}}\end{bmatrix}
$$

经计算可得

$$
V_1^HA_1V_1=\begin{bmatrix}-1&-\frac{1225\sqrt{6}}{35\sqrt{6}}\\0&-1\end{bmatrix}
$$

令

$$
U_2=\begin{bmatrix}1&0&0\\0&-\frac{3}{\sqrt{105}} &\frac{4\sqrt{6}}{\sqrt{105}}\\ 0&\frac{4\sqrt{6}}{\sqrt{105}}&\frac{3}{\sqrt{105}}\end{bmatrix}
$$

记

$$
U = U_1U_2=\begin{bmatrix}
\frac{2}{\sqrt{6}} & -\frac{4 \sqrt{2}}{\sqrt{105}} & \frac{1}{\sqrt{35}} \\
\frac{1}{\sqrt{6}} & \frac{5}{\sqrt{210}} & \frac{5}{\sqrt{35}} \\
-\frac{1}{\sqrt{6}} & -\frac{11}{\sqrt{210}} & \frac{3}{\sqrt{35}}
\end{bmatrix}
$$

则 $U^HAU=\begin {bmatrix} 0 & \frac {50}{\sqrt {12}} & \frac {9}{\sqrt {18}} \\0 & -1 & \frac {1225}{35 \sqrt {6}} \\0 & 0 & -1\end {bmatrix}$

___

**正规矩阵**

**定理：**$\exists U\in U^{n\times n}$，使得 $U^{-1} AU$ 为对角矩阵的充分必要条件为 $A^HA=AA^H$

**定义：**如果矩阵 $A$ 满足 $A^HA=AA^H$，则称其为正规矩阵

___

**Hermite 矩阵**

**定义：**$A\in \mathbb {C}^{n\times n}$，若 $A^H=A$，则称 $A$ 为 Hermite 矩阵

**定理：**Hermite 矩阵是正规矩阵，Hermite 矩阵的特征值是实数

___

**Rayleigh 商**

**定理：**设 $A\in \mathbb {C}^{n\times n}$ 是 Hermite 矩阵，则 $\forall x\in \mathbb {C}^n$，$x^HAx$ 为实数

**定义：**$A\in \mathbb {C}^{n\times n}$ 是 Hermite 矩阵，$\forall x\in \mathbb {C}^n$，$x \neq 0$，则

$$
R(x)=\frac{x^HAx}{x^Hx}
$$

为实数，称 $R (x)$ 为矩阵 $A$ 的 Rayleigh 商

**定理：**由于 Hermite 矩阵的特征值全部为实数，不妨排列成

$$
\lambda_1 ≥ \lambda_2 ≥ ···≥ \lambda_n
$$

则

1.  $\lambda_n ≤ R(x) ≤ \lambda_1$
2.  $\lambda_1 = \mathop{\max}\limits_{x\neq 0} R(x), \lambda_n = \mathop{\min}\limits_{x\neq 0} R(x)$

> 注：本节内容实际上都是一些定义或定理，看上去并不多，然而实际上如果仔细证明每一条定理，会有相当多的内容，不过这里我觉得没有必要写上证明，读者有需要自行谷歌搜索或者翻书即可

___

例 2

已知 $A=\begin {bmatrix} 3&0&8\\3&-1&6\\-2&0&-5\end {bmatrix}$，试求酉矩阵 $U$，使得 $U^HAU$ 是一个上三角矩阵

**解：**首先求出其特征多项式 $|\lambda E-A|=(\lambda +1)^3$，当 $\lambda=-1$ 时，求出属于特征值 - 1 的一个单位特征向量为

$$
\eta_1 = [-\frac{2}{\sqrt{6}},\frac{1}{\sqrt{6}},\frac{1}{\sqrt{6}}]
$$

解与 $\eta_1$ 内积为零的方程 $-2x_1+x_2+x_3=0$，求得一个单位解向量

$$
\eta_2=[\frac{\sqrt{3}}{3},\frac{\sqrt{3}}{3},\frac{\sqrt{3}}{3}]^T
$$

解与 $\eta_1,\eta_2$ 内积为零的方程

$$
\begin{cases}
-2x_2+x_2+x_3=0\\
x_1+x_2+x_3=0
\end{cases}
$$

又求得其一个单位解向量

$$
\eta_3 = [0, -\frac{\sqrt{2}}{2},\frac{\sqrt{2}}{2}]^T
$$

于是取

$$
U_1=\begin{bmatrix}-\frac{2}{\sqrt{6}}&\frac{\sqrt{3}}{3}&0\\\frac{1}{\sqrt{6}}&\frac{\sqrt{3}}{3}&-\frac{\sqrt{2}}{2}\\\frac{1}{\sqrt{6}}&\frac{\sqrt{3}}{3}&\frac{\sqrt{2}}{2}\end{bmatrix}
$$

经计算得

$$
U_1^HAU_1=\begin{bmatrix}-1 & -\frac{7 \sqrt{2}}{2} & -\frac{7 \sqrt{3}}{3} \\
0 & 4 & \frac{5 \sqrt{6}}{3} \\
0 & -\frac{5 \sqrt{6}}{2} & -6\end{bmatrix}
$$

记

$$
A_1=\begin{bmatrix}4 & \frac{5 \sqrt{6}}{3} \\
 -\frac{5 \sqrt{6}}{2} & -6\end{bmatrix}
$$

可得 $|\lambda E - A_1|=(\lambda +1)^2$，对于 $\lambda =-1$ 时，求得一个单位特征向量 $\gamma_1=[-\frac {\sqrt {10}}{5}, \frac {\sqrt {15}}{5}]^T$

再求得一个与 $\gamma_1$ 正交的向量 $\gamma_2=[\frac {\sqrt {15}}{5}, \frac {\sqrt {10}}{5}]^T$，令

$$
V_1 = \begin{bmatrix}-\frac{\sqrt{10}}{5} &\frac{\sqrt{15}}{5}\\ \frac{\sqrt{15}}{5}&\frac{\sqrt{10}}{5}\end{bmatrix}
$$

经计算可得

$$
V_1^HA_1V_1=\begin{bmatrix}-1&-\frac{25\sqrt{6}}{6}\\0&-1\end{bmatrix}
$$

令

$$
U_2=\begin{bmatrix}1&0&0\\0&-\frac{\sqrt{10}}{5} &\frac{\sqrt{15}}{5}\\0&\frac{\sqrt{15}}{5}&\frac{\sqrt{10}}{5}\end{bmatrix}
$$

记

$$
U = U_1U_2=\begin{bmatrix}-\frac{2}{\sqrt{6}} & -\frac{\sqrt{30}}{15} & \frac{\sqrt{5}}{5} \\
\frac{1}{\sqrt{6}} & -\frac{\sqrt{30}}{6} & 0 \\
\frac{1}{\sqrt{6}} & \frac{\sqrt{30}}{30} & \frac{2 \sqrt{5}}{5}\end{bmatrix}
$$

则 $U^HAU=\begin {bmatrix}-1 & \frac {\sqrt {30}}{15} & -\frac {7 \sqrt {15}}{20} \\ 0 & -1 & -\frac {25 \sqrt {6}}{6} \\ 0 & 0 & -1\end {bmatrix}$

___

例 3

验证矩阵 $A=\begin {bmatrix}\frac {1}{3}&-\frac {1}{3\sqrt {2}}&-\frac {1}{\sqrt {6}}\\-\frac {1}{3\sqrt {2}}&\frac {1}{6}&-\frac {1}{2\sqrt {3}}\\-\frac {1}{\sqrt {6}}&-\frac {1}{2\sqrt {3}}&\frac {1}{2}\end {bmatrix}$ 是正规矩阵，并求酉矩阵 $U$，使得 $U^HAU$ 为对角矩阵

**解：**$A^H=\begin {bmatrix}\frac {1}{3}&-\frac {1}{3\sqrt {2}}&-\frac {1}{\sqrt {6}}\\-\frac {1}{3\sqrt {2}}&\frac {1}{6}&-\frac {1}{2\sqrt {3}}\\-\frac {1}{\sqrt {6}}&-\frac {1}{2\sqrt {3}}&\frac {1}{2}\end {bmatrix}$，因为 $AA^H=A^HA$，所以 $A$ 是正规矩阵。求得其特征值为

$$
\lambda_1=\lambda_2=0,\lambda_3=1
$$

对于特征值 $\lambda_1=\lambda_2=0$，求得两个线性无关的特征向量

$$
X_1= [\frac{\sqrt{2}}{2}, 1, 0]^T, X_2=[\frac{\sqrt{6}}{2}\mathrm{i}, 0, 1]^T
$$

对于特征值 $\lambda_3=1$，求得一个线性无关的特征向量 $X_3 = [-\frac {\sqrt {6}}{3}\mathrm {i}, \frac {\sqrt {3}}{3}\mathrm {i}, 1]^T$，将 $X_1,X_2$ 正交化与单位化得

$$
\alpha_1 = [\frac{\sqrt{3}}{3}, \frac{\sqrt{6}}{3}, 0]^T\\
\alpha_2 = [\frac{\sqrt{3}}{3}\mathrm{i}, -\frac{\sqrt{6}}{6}\mathrm{i}, \frac{\sqrt{2}}{2}]^T
$$

将 $X_3$ 单位化得 $\alpha_3 = [-\frac {\sqrt {3}}{3} i, \frac {\sqrt {6}}{6} i, \frac {\sqrt {2}}{2}]^T$，于是取

$$
U = \begin{bmatrix}\frac{\sqrt{3}}{3} & \frac{\sqrt{3}}{3} \mathrm{i} & -\frac{\sqrt{3}}{3} \mathrm{i} \\
\frac{\sqrt{6}}{3} & -\frac{\sqrt{6}}{6} \mathrm{i} & \frac{\sqrt{6}}{6} \mathrm{i} \\
0 & \frac{\sqrt{2}}{2} & \frac{\sqrt{2}}{2}\end{bmatrix}
$$

而且有

$$
U^HAU = \begin{bmatrix}0&0&0\\0&0&0\\0&0&1\end{bmatrix}
$$

___

例 4

已知 $A=\begin {bmatrix} 2&-2&0\\-2&1&-2\\0&-2&0\end {bmatrix}$，求正交矩阵 $Q$，使 $Q^TAQ$ 为对角矩阵

**解：**计算 $A$ 的特征值与（单位化）特征向量得

$$
\lambda_1 = -2, \alpha_1 = [\frac{1}{3}, \frac{2}{3}, \frac{2}{3}]^T\\
\lambda_2 = 4, \alpha_2 = [\frac{2}{3}, -\frac{2}{3}, \frac{1}{3}]^T\\
\lambda_3 = 1, \alpha_3 = [\frac{2}{3}, \frac{1}{3}, -\frac{2}{3}]^T
$$

于是取

$$
Q = \begin{bmatrix}\frac{1}{3} & \frac{2}{3} & \frac{2}{3} \\
\frac{2}{3} & -\frac{2}{3} & \frac{1}{3} \\
\frac{2}{3} & \frac{1}{3} & -\frac{2}{3}\end{bmatrix}
$$

那么有

$$
Q^TAQ = \begin{bmatrix}-2&0&0\\0&4&0\\0&0&1\end{bmatrix}
$$


