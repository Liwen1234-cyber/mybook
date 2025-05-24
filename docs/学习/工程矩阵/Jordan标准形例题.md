## Jordan标准形例题

### 例1
设 $A=\begin{bmatrix}2&1&0\\0&2&1\\0&0&2\end{bmatrix}$，求 $A$ 的 Jordan 标准形，并写出相似变换矩阵 $P$。

**解：**
$A$ 的特征多项式为 $|A-\lambda I|=(2-\lambda)^3$，唯一特征值 $\lambda=2$，代数重数为3。

$A-2I=\begin{bmatrix}0&1&0\\0&0&1\\0&0&0\end{bmatrix}$，秩为2，$	ext{nullity}=1$，几何重数为1。

$(A-2I)^2=\begin{bmatrix}0&0&1\\0&0&0\\0&0&0\end{bmatrix}$，秩为1，$	ext{nullity}=2$。

所以 $A$ 的 Jordan 标准形为 $J=\begin{bmatrix}2&1&0\\0&2&1\\0&0&2\end{bmatrix}$，即只有一个3阶Jordan块。

广义特征向量组可取 $v_1=\begin{bmatrix}1\\0\\0\end{bmatrix}, v_2=\begin{bmatrix}0\\1\\0\end{bmatrix}, v_3=\begin{bmatrix}0\\0\\1\end{bmatrix}$，$P=[v_1,v_2,v_3]$。

---

### 例2
设 $A=\begin{bmatrix}3&1&0\\0&3&0\\0&0&4\end{bmatrix}$，求 $A$ 的 Jordan 标准形。

**解：**
特征多项式 $|A-\lambda I|=(3-\lambda)^2(4-\lambda)$，特征值3（重数2），4（重数1）。

$A-3I=\begin{bmatrix}0&1&0\\0&0&0\\0&0&1\end{bmatrix}$，对3的几何重数为1。

所以 $A$ 的 Jordan 标准形为 $J=\begin{bmatrix}3&1&0\\0&3&0\\0&0&4\end{bmatrix}$。

---

### 例3
设 $A=\begin{bmatrix}0&1&0\\0&0&1\\0&0&0\end{bmatrix}$，求 $A$ 的 Jordan 标准形。

**解：**
特征多项式 $|A-\lambda I|=(-\lambda)^3$，唯一特征值0，代数重数3。

$A$ 的几何重数为1，Jordan标准形为 $J=\begin{bmatrix}0&1&0\\0&0&1\\0&0&0\end{bmatrix}$。

---

### 例4
设 $A=\begin{bmatrix}1&1&0\\0&1&0\\0&0&2\end{bmatrix}$，求 $A$ 的 Jordan 标准形。

**解：**
特征多项式 $|A-\lambda I|=(1-\lambda)^2(2-\lambda)$，特征值1（重数2），2（重数1）。

$A-1I=\begin{bmatrix}0&1&0\\0&0&0\\0&0&1\end{bmatrix}$，对1的几何重数为1。

Jordan标准形为 $J=\begin{bmatrix}1&1&0\\0&1&0\\0&0&2\end{bmatrix}$。 