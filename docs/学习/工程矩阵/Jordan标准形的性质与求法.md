## Jordan标准形的性质与求法

### Jordan标准形的性质

1. $A$ 的 Jordan 标准形 $J$ 的主对角线元素为 $A$ 的特征值。
2. $A$ 的特征多项式 $f(\lambda)$ 的根及其重数等于 $J$ 中各 Jordan 块的特征值及阶数之和。
3. $A$ 的最小多项式 $m(\lambda)$ 的每个因式 $(\lambda-\lambda_i)$ 的次数等于 $J$ 中对应 $\lambda_i$ 的最大 Jordan 块阶数。
4. $A$ 的几何重数等于 $J$ 中对应特征值 $\lambda_i$ 的 Jordan 块个数。
5. $A$ 的代数重数等于 $J$ 中对应特征值 $\lambda_i$ 的所有 Jordan 块阶数之和。

### Jordan标准形的存在性与唯一性

- 任意 $n$ 阶方阵 $A$ 都存在 Jordan 标准形 $J$，且 $A$ 与 $J$ 相似。
- $J$ 在各 Jordan 块的排列顺序不同时唯一。

### Jordan标准形的求法（常用步骤）

1. 求 $A$ 的特征多项式 $f(\lambda)$，计算所有特征值及其重数。
2. 对每个特征值 $\lambda_i$，计算 $A-\lambda_i I$ 的秩，确定几何重数。
3. 计算 $\ker((A-\lambda_i I)^k)$ 的维数，确定各 Jordan 块阶数。
4. 构造出 $A$ 的 Jordan 标准形 $J$。
5. 若需相似变换矩阵 $P$，可进一步构造广义特征向量组。

> Jordan 标准形常用于化简矩阵、解微分方程、研究线性算子的结构等。 