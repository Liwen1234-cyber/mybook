## Gram 矩阵

**欧氏空间**

$V$ 是 $\mathbb {R}$ 上的线性空间，定义映射

$$
\sigma: V\times V \to \mathbb{R}
$$

对于 $\alpha, \beta \in V$，将 $\sigma (\alpha, \beta)$ 记为 $\left<\alpha, \beta\right>$，若 $\sigma$ 满足：

1.  对称性：$\left<\alpha.\beta\right>=\left<\beta, \alpha\right>$
2.  （右）齐次性：$\left<\alpha, k\beta\right>=k\left<\alpha,\beta\right>$
3.  （右）可加性：$\left<\alpha, \beta+\gamma\right>=\left<\alpha,\beta\right>+\left<\alpha, \gamma\right>$
4.  非负性：$\left<\alpha,\alpha\right>≥0$，且 $\left<\alpha,\alpha\right>=0\Leftrightarrow\alpha=0$

则称 $\sigma$ 为 $V$ 上的（实）内积，**当 $V$ 是有限维时，称其为欧氏空间**（$\mathbb {R}^n$ 为标准欧氏空间）

实际上 $\alpha$ 是一个向量，$\beta$ 是一个向量，$\left<\alpha, \beta\right>$ 表示向量 $\alpha$ 与向量 $\beta$ 的内积，结果是一个实数

**实内积的性质**

1.  （左）齐次性：$\left<k\alpha, \beta\right>=k\left<\alpha,\beta\right>$
2.  （左）可加性：$\left<\alpha+\beta, \gamma\right>=\left<\alpha,\gamma\right>+\left<\beta, \gamma\right>$
3.  $\left<k_1\alpha_1+···+k_s\alpha_s,\beta\right>=k_1\left<\alpha_1,\beta\right>+···k_s\left<\alpha_s,\beta\right>$
4.  $\left<\alpha,k_1\beta_1+···+k_s\beta_s\right>=k_1\left<\alpha,\beta_1\right>+···k_s\left<\alpha,\beta_s\right>$

___

**复内积**

$V$ 是 $\mathbb {C}$ 上的线性空间，定义映射

$$
\sigma: V\times V \to \mathbb{C}
$$

对于 $\alpha, \beta \in V$，将 $\sigma (\alpha, \beta)$ 记为 $\left<\alpha, \beta\right>$，若 $\sigma$ 满足：

1.  共轭对称性：$\left<\alpha.\beta\right>=\overline {\left<\beta, \alpha\right>}$
2.  （右）齐次性：$\left<\alpha, k\beta\right>=k\left<\alpha,\beta\right>$
3.  （右）可加性：$\left<\alpha, \beta+\gamma\right>=\left<\alpha,\beta\right>+\left<\alpha, \gamma\right>$
4.  非负性：$\left<\alpha,\alpha\right>≥0$，且 $\left<\alpha,\alpha\right>=0\Leftrightarrow\alpha=0$

则称 $\sigma$ 为 $V$ 上的（复）内积，**当 $V$ 是有限维时，称其为酉空间**（$\mathbb {R}^n$ 为标准欧氏空间）

**复内积的性质**

1.  （左）齐次性：$\left<k\alpha, \beta\right>=\bar {k}\left<\alpha,\beta\right>$
2.  （左）可加性：$\left<\alpha+\beta, \gamma\right>=\left<\alpha,\gamma\right>+\left<\beta, \gamma\right>$
3.  $\left<k_1\alpha_1+···+k_s\alpha_s,\beta\right>=\overline{k_1}\left<\alpha_1,\beta\right>+···\overline{k_s}\left<\alpha_s,\beta\right>$
4.  $\left<\alpha,k_1\beta_1+···+k_s\beta_s\right>=k_1\left<\alpha,\beta_1\right>+···k_s\left<\alpha,\beta_s\right>$

___

**线性组合的内积的矩阵表示**

$\alpha_1,...,\alpha_s;\beta_1,...,\beta_t$ 是 $\mathbb {C}$ 上的内积空间 $V$ 中的两个向量组，则

$$
\begin{aligned}
\left<k_1\alpha_1+···+k_s\alpha_s,l_1\beta_1+···+l_t\beta_t\right>\\
=(\overline{k_1},...,\overline{k_s})\begin{bmatrix}\left<\alpha_1,\beta_1\right>&\cdots &\left<\alpha_1,\beta_t\right>\\ \vdots & \ddots &\vdots \\\left<\alpha_s,\beta_1\right> &\cdots & \left<\alpha_s,\beta_t\right>\end{bmatrix}\begin{bmatrix}l_1\\ \vdots \\ l_t\end{bmatrix}
\end{aligned}
$$

___

**Gram 矩阵**

$\alpha_1,...,\alpha_s;\beta_1,...,\beta_t$ 是 $\mathbb {C}$ 上的内积空间 $V$ 中的两个向量组，则

$$
\begin{bmatrix}\left<\alpha_1,\beta_1\right>&\cdots &\left<\alpha_1,\beta_t\right>\\ \vdots & \ddots &\vdots \\\left<\alpha_s,\beta_1\right> &\cdots & \left<\alpha_s,\beta_t\right>\end{bmatrix}
$$

称为 $\alpha_1,...,\alpha_s;\beta_1,...,\beta_t$ 的协 Gram 矩阵，记为 $G (\alpha_1,...,\alpha_s;\beta_1,...,\beta_t)$

$\alpha_1,...,\alpha_s$ 是 $\mathbb {C}$ 上的内积空间 $V$ 中的一个向量组，则

$$
\begin{bmatrix}\left<\alpha_1,\beta_1\right>&\cdots &\left<\alpha_1,\beta_t\right>\\ \vdots & \ddots &\vdots \\\left<\alpha_s,\beta_1\right> &\cdots & \left<\alpha_s,\beta_t\right>\end{bmatrix}
$$

称为 $\alpha_1,...,\alpha_s$ 的 Gram 矩阵，记为 $G (\alpha_1,...,\alpha_s)$

$\alpha_1,...,\alpha_s$ 是 $\mathbb {C}^n$ 中的一个向量组，记 $A=(\alpha_1,...,\alpha_s)$，则

$$
G(\alpha_1,...,\alpha_s)=A^HA
$$

其中，$A^H=(\bar {A})^T=\overline {(A^T)}$

$\alpha_1,...,\alpha_s$ 是 $\mathbb {R}^n$ 中的一个向量组，记 $A=(\alpha_1,...,\alpha_s)$，则

$$
G(\alpha_1,...,\alpha_s)=A^TA
$$

$\alpha_1,...,\alpha_s;\beta_1,...,\beta_t$ 是 $\mathbb {C}$ 上的内积空间 $V$ 中的两个向量组，如果 $\alpha_1,...,\alpha_s$ 可由 $\beta_1,...,\beta_t$ 线性表出，且

$$
(\alpha_1,...,\alpha_s)=(\beta_1,...,\beta_t)A
$$

则

$$
G(\alpha_1,...,\alpha_s)=A^HG(\beta_1,...,\beta_t)A
$$

**Gram 矩阵的性质**

1.  $\mathrm{rank}(G)=\mathrm{rank}(\alpha_1,...,\alpha_s)$
2.  Hermite 性：$G^H=G$
3.  非负性：$\forall x\in \mathbb {C}^s$，复二次型 $x^HGx≥0$，并且 $G$ 正定 $\Leftrightarrow \alpha_1,...,\alpha_s$ 线性无关