# LaTex常用数学符号整理

<iframe src="/files/刘海洋-latex入门.pdf" width="100%" height="600px"></iframe>

## 1.常用公式

|       描述       |                写法                |               效果展示               |
| :--------------: | :--------------------------------: | :----------------------------------: |
|      上下标      |             a_0 = 3^2              |             $a_0 = 3^2$              |
| 上下标（多字符） |          A_{ij} = 2^{i+j}          |          $A_{ij} = 2^{i+j}$          |
|       角度       |            A = 90^\circ            |            $A = 90^\circ$            |
|   上下方水平线   |  \overline{m+n} / \underline{m+n}  |  $\overline{m+n} / \underline{m+n}$  |
|   上下方大括号   | \overbrace{m+n} / \underbrace{m+n} | $\overbrace{m+n} / \underbrace{m+n}$ |
|       向量       |               \vec a               |               $\vec a$               |
|       分数       |            \frac{1}{2}             |            $\frac{1}{2}$             |
|       求和       |            \sum_{i=1}^n            |            $\sum_{i=1}^n$            |
|       积分       |           \int_0^{\pi/2}           |           $\int_0^{\pi/2}$           |
|       累乘       |           \prod_\epsilon           |           $\prod_\epsilon$           |
|       优化       |     \mathop{\arg\min}_{\theta}     |     $\mathop{\arg\min}_{\theta}$     |
|     上左箭头     |        \overleftarrow{a+b}         |        $\overleftarrow{a+b}$         |
|     上右箭头     |        \overrightarrow{a+b}        |        $\overrightarrow{a+b}$        |
|     下左箭头     |        \underleftarrow{a+b}        |        $\underleftarrow{a+b}$        |
|     下右箭头     |       \underrightarrow{a+b}        |       $\underrightarrow{a+b}$        |

## 2.矩阵

```
\begin{pmatrix} a&b\\ c&d \end{pmatrix} \quad
\begin{bmatrix} a&b\\ c&d \end{bmatrix} \quad
\begin{Bmatrix} a&b\\ c&d \end{Bmatrix} \quad
\begin{vmatrix} a&b\\ c&d \end{vmatrix} \quad
\begin{Vmatrix} a&b\\ c&d \end{Vmatrix} 
```

$$
\begin{pmatrix} a&b\\ c&d \end{pmatrix} \quad
\begin{bmatrix} a&b\\ c&d \end{bmatrix} \quad
\begin{Bmatrix} a&b\\ c&d \end{Bmatrix} \quad
\begin{vmatrix} a&b\\ c&d \end{vmatrix} \quad
\begin{Vmatrix} a&b\\ c&d \end{Vmatrix}
$$



#### 2.1常规表示

不同的列用符号 **&** 分隔，行用 **\\ \\ ** 分隔。

```
A =
  \begin{pmatrix}
  a_{11} & a_{12} & a_{13} \\ 
  0 & a_{22} & a_{23} \\ 
  0 & 0 & a_{33}
  \end{pmatrix}
```

$$
A =
  \begin{pmatrix}
  a_{11} & a_{12} & a_{13} \\ 
  0 & a_{22} & a_{23} \\ 
  0 & 0 & a_{33}
  \end{pmatrix}
$$

矩阵中常常使用各种省略号，即 **\dots**，**\vdots**，**\ddots** 等。

```
A =
\begin{bmatrix}
a_{11} & \dots & a_{1n} \\ 
 & \ddots & \vdots \\ 
0 &  & a_{nn}
\end{bmatrix}_{n \times n}
```

$$
A =
\begin{bmatrix}
a_{11} & \dots & a_{1n} \\ 
 & \ddots & \vdots \\ 
0 &  & a_{nn}
\end{bmatrix}_{n \times n}
$$



#### 2.2分块矩阵

分块矩阵可以理解为矩阵的嵌套。

```
\begin{pmatrix}
  \begin{matrix}
  1 & 0\\ 
  0 & 1
  \end{matrix}
  & \text{\Large 0}\\ 
  \text{\Large 0} &
  \begin{matrix}
  1 & 0\\ 
  0 & -1
  \end{matrix}
\end{pmatrix}
```

$$
\begin{pmatrix}
  \begin{matrix}
  1 & 0\\ 
  0 & 1
  \end{matrix}
  & \text{\Large 0}\\ 
  \text{\Large 0} &
  \begin{matrix}
  1 & 0\\ 
  0 & -1
  \end{matrix}
\end{pmatrix}
$$



#### 2.3矩阵的其他用法

在行内公式中使用很小的矩阵，用的矩阵环境是 **smallmatrix** 环境。

```
矩阵在行内表示 $
  \begin{smallmatrix}
  x & -y \\ 
  y & x
  \end{smallmatrix}
$，但是没有括号。
```

矩阵在行内表示 $
  \begin{smallmatrix}
  x & -y \\ 
  y & x
  \end{smallmatrix}
$，但是没有括号。

用 **\substack** 命令排版列矩阵，用以处理多行内容的插入。

```
\sum_{\substack{0<i<n \\ 0<j<i}} A_{ij}
```

$$
\sum_{\substack{0<i<n \\ 0 < j < i}} A_{ij}
$$

或者是用 **\subarray** 指令，还必须指定对齐方式为 **l（左对齐）**，**c（居中）**，**r（右对齐）**。

```
\sum_{
  \begin{subarray}{l}
  i<10 \\ 
  j<100 \\ 
  k<1000
  \end{subarray}
} X(i,j,k)
```

$$
\sum_{
  \begin{subarray}{l}
  i<10 \\ 
  j<100 \\ 
  k<1000
  \end{subarray}
} X(i,j,k)
$$



## 3. 括号&空格

#### 3.1 括号

| 符号 |     写法      | 符号 |     写法      |
| :--: | :-----------: | :--: | :-----------: |
|  [   | \[ or \lbrack |  ]   | \] or \rbrack |
|  {   | \{ or \lbrace |  }   | \} or \rbrace |
|  ⟨   |    \langle    |  ⟩   |    \rangle    |
|  ⌊   |    \lfloor    |  ⌋   |    \rfloor    |
|  /   |       /       |  ∖   |  \backslash   |
|  ⟮   |    \lgroup    |  ⟯   |    \rgroup    |
|  ⏐   |  \arrowvert   |  ∥   |  \Arrowvert   |
|  ↑   |   \uparrow    |  ↓   |  \downarrow   |
|  ↕   | \updownarrow  |  \|  |     \vert     |
|  ⇑   |   \Uparrow    |  ⇓   |  \Downarrow   |
|  ⇕   | \Updownarrow  |  ∥   |   \parallel   |
|  ⌈   |    \lceil     |  ⌉   |    \rceil     |
|  ⎰   |  \lmoustache  |  ⎱   |  \rmoustache  |

- 不同大小的括号 \Bigg ( \bigg [ \Big \{\big \langle \left \vert \parallel \frac{a}{b} \parallel \right \vert \big \rangle \Big \} \bigg ] \Bigg ):$([{⟨∣∣∥ab∥∣∣⟩}])$
- 自适应大小的括号 \left( \frac{a}{b} \right)：$\left( \frac{a}{b} \right)$
- 单边括号 \left . \frac{a}{b} \right \rbrace:$\left . \frac{a}{b} \right \rbrace$
- 混合括号 \left \langle \psi \right \vert :$\left \langle \psi \right \vert$

#### 3.2空格

LaTex 在数学公式中的字符间隔是默认自动调整的，直接输入空格会被忽略掉，当我们需要自定义字母间隔时，需要手动输入空格：

|     空格     |    写法    |     显示     |    备注     |
| :----------: | :--------: | :----------: | :---------: |
| 两个quad空格 | a \qquad b | $a \qquad b$ | 两个m的宽度 |
|   quad空格   | a \quad b  | $a \quad b$  | 一个m的宽度 |
|    大空格    |    a\:b    |    $a\:b$    | 1/3m的宽度  |
|   中等空格   |    a\;b    |    $a\;b$    | 2/7m的宽度  |
|    小空格    |    a\,b    |    $a\,b$    |  1/6m宽度   |
|   没有空格   |     ab     |     $ab$     |             |

## 4.重音符

|    符号     |   写法    |     符号      |    写法     |      符号       |     写法      |
| :---------: | :-------: | :-----------: | :---------: | :-------------: | :-----------: |
|  $\hat{a}$  |  \hat{a}  |  $\check{a}$  |  \check{a}  |   $\tilde{a}$   |   \tilde{a}   |
| $\acute{a}$ | \acute{a} |  $\grave{a}$  |  \grave{a}  |    $\dot{a}$    |    \dot{a}    |
| $\ddot{a}$  | \ddot{a}  |  $\breve{a}$  |  \breve{a}  |    $\bar{a}$    |    \bar{a}    |
|  $\vec{a}$  |  \vec{a}  | $\widehat{A}$ | \widehat{A} | $\widetilde{A}$ | \widetilde{A} |

## 5.希腊字母

| 字母名称 | 大写 |  小写  |    基本命令     |
| :------: | :--: | :----: | :-------------: |
|  alpha   |  A   |   α    |     \alpha      |
|   beta   |  B   |   β    |      \beta      |
|  delta   |  Δ   |   δ    |     \delta      |
| epsilon  |  E   |   ϵ    |    \epsilon     |
|          |      |   ε    |   \varepsilon   |
|   zeta   |  Z   |   ζ    |      \zeta      |
|   eta    | \Eta |   η    |      \eta       |
|  gamma   |  Γ   |   γ    |     \gamma      |
|  theta   |  Θ   |   θ    |     \theta      |
|  sigma   |  Σ   |   σ    |     \sigma      |
|  omega   |  Ω   |   ω    |     \omega      |
|  lambda  |  Λ   |   λ    |     \lambda     |
|    mu    | \Mu  |   μ    |       \mu       |
|   phi    |  Φ   | ϕ 或 φ | \phi 或 \varphi |
|    pi    |  Π   |   π    |       \pi       |
|   psi    |  Ψ   |   ψ    |      \psi       |

## 6.二元符号

| 符号 |       写法       | 符号 |     写法      | 符号 |      写法      |
| :--: | :--------------: | :--: | :-----------: | :--: | :------------: |
|  ≥   |   \geq or \ge    |  ≤   |  \leq or \le  |  ≡   |     \equiv     |
|  ≪   |       \ll        |  ≫   |      \gg      |  ≐   |     \doteq     |
|  ≺   |      \prec       |  ≻   |     \succ     |  ∼   |      \sim      |
|  ⪯   |     \preceq      |  ⪰   |    \succeq    |  ≃   |     \simeq     |
|  ≈   |     \approx      |  ⊂   |    \subset    |  ⊃   |    \supset     |
|  ⊆   |    \subseteq     |  ⊇   |   \supseteq   |  ⊏   |   \sqsubset    |
|  ⊐   |    \sqsupset     |  ⊑   |  \sqsubseteq  |  ⊒   |  \sqsupseteq   |
|  ≅   |      \cong       |  ⋈   |     \Join     |  ⋈   |    \bowtie     |
|  ∝   |     \propto      |  ∈   |      \in      |  ∋   |  \ni or \owns  |
|  ⊢   |      \vdash      |  ⊣   |    \dashv     |  ⊨   |    \models     |
|  ∣   |       \mid       |  ∥   |   \parallel   |  ⊥   |     \perp      |
|  ⌣   |      \smile      |  ⌢   |    \frown     |  ≍   |     \asymp     |
|  ∉   |      \notin      |  ≠   |     \neq      |  :   |       :        |
|  ×   |      \times      |  ÷   |     \div      |  ±   |      \pm       |
|  ∓   |       \mp        |  ◃   | \triangleleft |  ▹   | \triangleright |
|  ⋅   |      \cdot       |  ∖   |   \setminus   |  ⋆   |     \star      |
|  ∗   |       \ast       |  ∪   |     \cup      |  ∩   |      \cap      |
|  ⊔   |      \sqcup      |  ⊓   |    \sqcap     |  ∨   |  \vee or \lor  |
|  ∧   | \wedge or \land  |  ∘   |     \circ     |  ∙   |    \bullet     |
|  ⊕   |      \oplus      |  ⊖   |    \ominus    |  ⊙   |     \odot      |
|  ⊘   |     \oslash      |  ⊗   |    \otimes    |  ◯   |    \bigcirc    |
|  ⋄   |     \diamond     |  ⊎   |    \uplus     |  △   | \bigtriangleup |
|  ▽   | \bigtriangledown |  ⊲   |     \lhd      |  ⊳   |      \rhd      |
|  ⊴   |      \unlhd      |  ⊵   |    \unrhd     |  ⨿   |     \amalg     |
|  ≀   |       \wr        |  †   |    \dagger    |  ‡   |    \ddagger    |

## 7.箭头

| 符号 |        写法         | 符号 |        写法        | 符号 |        写法         |
| :--: | :-----------------: | :--: | :----------------: | :--: | :-----------------: |
|  ←   | \leftarrow or \gets |  →   | \rightarrow or \to |  ⟵   |   \longleftarrow    |
|  ⟶   |   \longrightarrow   |  ↔   |  \leftrightarrow   |  ⟷   | \longleftrightarrow |
|  ⇐   |     \Leftarrow      |  ⟸   |   \Longleftarrow   |  ⇒   |     \Rightarrow     |
|  ⟹   |   \Longrightarrow   |  ⇔   |  \Leftrightarrow   |  ⟺   | \Longleftrightarrow |
|  ↦   |       \mapsto       |  ⟼   |    \longmapsto     |  ↗   |      \nearrow       |
|  ↘   |      \searrow       |  ↙   |      \swarrow      |  ↖   |      \nwarrow       |
|  ↩   |   \hookleftarrow    |  ↪   |  \hookrightarrow   |  ⇌   | \rightleftharpoons  |
|  ⟺   |        \iff         |  ↼   |   \leftharpoonup   |  ⇀   |   \rightharpoonup   |
|  ↽   |  \leftharpoondown   |  ⇁   | \rightharpoondown  |  ⇝   |      \leadsto       |

## 8.其他符号

| 符号 |     写法     | 符号 |     写法      | 符号 |   写法    |
| :--: | :----------: | :--: | :-----------: | :--: | :-------: |
|  …   |    \dots     |  ⋯   |    \cdots     |  ⋮   |  \vdots   |
|  ⋱   |    \ddots    |  ℏ   |     \hbar     |  ı   |  \imath   |
|  ȷ   |    \jmath    |  ℓ   |     \ell      |  ℜ   |    \Re    |
|  ℑ   |     \Im      |  ℵ   |    \aleph     |  ℘   |    \wp    |
|  ∀   |   \forall    |  ∃   |    \exists    |  ℧   |   \mho    |
|  ∂   |   \partial   |  ′   |    \prime     |  ∅   | \emptyset |
|  ∞   |    \infty    |  ∇   |    \nabla     |  △   | \triangle |
|  □   |     \Box     |  ◊   |   \Diamond    |  ⊥   |   \bot    |
|  ⊤   |     \top     |  ∠   |    \angle     |  √   |   \surd   |
|  ♢   | \diamondsuit |  ♡   |  \heartsuit   |  ♣   | \clubsuit |
|  ♠   |  \spadesuit  |  ¬   | \neg or \lnot |  ♭   |   \flat   |
|  ♮   |   \natural   |  ♯   |    \sharp     |  §   |    \S     |