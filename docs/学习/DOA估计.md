# DOA估计

MIMO阵列信号来向DOA估计实现

> [代码](https://github.com/highskyno1/MIMO_DOA)

## 前言

波达方向估计(Direction Of Arrival, DOA)也称为测向、空间谱估计，为利用电磁波来获取目标或信源对天线阵列的角度信息，主要应用于雷达、通信、电子侦察与对抗等领域。

本文利用MIMO天线阵列实现DOA相关算法的总结，主要仿真实现了常规波束形成(CBF)、Capon和最大似然估计(ML)三种常规方法，多重信号分类法(MUSIC)、LS-ESPRIT和TLS-ESPRIT三种子空间方法，欠定系统聚焦法(FOCUSS)、正交匹配追踪法(OMP)、凸优化法(CVX)、伪逆法(PINV)和期望最大化-稀疏贝叶斯学习法(EM-SBL)等稀疏恢复方法。对比了上述方法在常规、低信噪比、低快拍以及信源相干情况下的性能，并研究了空间平滑算法在处理相干信源问题上的表现。

## 仿真原理

### 离散时间阵列信号模型

仿真的前提为信源发射的信号为窄带信号且为远场信号，阵元为全向天线其阵元之间没有互耦和误差。

仿真采用了离散时间阵列信号模型：
$$
\begin{bmatrix}
 x_1(n)\\
 x_2(n)\\
 \vdots\\
 x_M(n)\\

\end{bmatrix}=\begin{bmatrix}
  1&  1&  \cdots & 1\\
  e^{-j2\pi d \sin \theta _1 / \lambda }&  e^{-j2\pi d \sin \theta _2 / \lambda }&  \cdots & e^{-j2\pi d \sin \theta _P / \lambda }\\
  \vdots&  \vdots&  \ddots&  \vdots\\
  e^{-j2\pi (M-1) d \sin \theta _1 / \lambda }&  e^{-j2\pi (M-1)d \sin \theta _2 / \lambda }&  \cdots & e^{-j2\pi (M-1) d \sin \theta _P / \lambda }\\
\end{bmatrix}\begin{bmatrix}
 s_1(n)\\
 s_2(n)\\
 \vdots\\
 s_P(n)\\

\end{bmatrix}+\begin{bmatrix}
 n_1(n)\\
 n_2(n)\\
 \vdots\\
 n_M(n)\\

\end{bmatrix}
$$

$$
\mathbf{x}=\mathbf{A}(\theta )\mathbf{s}+\mathbf{n}
$$

其中，$\mathbf{x}\in \mathbb{C}^{M \times N}$ 为天线阵列接收到的信号；$\mathbf{A}\in \mathbb{C}^{M \times P}$ 为空域导向矢量；$\mathbf{s} \in \mathbb{C}^{p \times N}$ 为目标发出的信号；$\mathbf{n} \in \mathbb{C}^{M \times N}$ 为噪声；$d$ 为阵元间隔；$\lambda$ 为载频信号的波长；$N$ 为快拍数；$M$为阵元数量；$P$ 为目标数量。

**波束形成矩阵(完备字典)**

该矩阵也称为DOA栅格矩阵，用于估算在每个角度上的功率，该矩阵的表达式为：

$$
a(\theta _0, \theta_1, \cdots,  \theta _k) = \begin{bmatrix}
  1&  1&  \cdots & 1\\
  e^{-j2\pi d \sin \theta _1 / \lambda }&  e^{-j2\pi d \sin \theta _2 / \lambda }&  \cdots & e^{-j2\pi d \sin \theta _k / \lambda }\\
  \vdots&  \vdots&  \ddots&  \vdots\\
  e^{-j2\pi (M-1) d \sin \theta _1 / \lambda }&  e^{-j2\pi (M-1)d \sin \theta _2 / \lambda }&  \cdots & e^{-j2\pi (M-1) d \sin \theta _k / \lambda }\\
\end{bmatrix}
$$

其中，$\theta_0,\theta_1,\dots,\theta_k$ 为需要做功率估计的等间距的来向角度；$\mathbb{C}^{M \times (k+1)}$ 为波束形成矩阵。

**回波生成**

为了保证信号的正交性，省去上变频步骤，本仿真使用随机数+带通滤波方法实现指定带宽与中心频率的信源生成。针对每个信源在 $[f_0-bw,f_0+bw]$ 之间等间隔地划分 $k$ 个频段，用高斯分布的随机数初始化矩阵 $s$ 后，对矩阵的每一行执行对应频段的带通滤波以获得频域正交的信号，~~是不是有种OFDM的感觉了~~。

**空间平滑滤波**

在实际使用中，由于存在多径传输等影响因素。阵列接收到的信源信号往往是相干而非完全独立的，为了解决相干源的DOA估计问题，需要使用空间平滑滤波破坏掉接收信号的相干性。该方法将等数量的相邻的实际阵列构成子阵列，并利用所有子阵列的自相关矩阵的平均数作为阵列接收信号的自相关矩阵估计，从而解决实际自相关矩阵的相干问题。当子阵大小L越大时，去相关能力越强，但等效阵列数会越小，从而降低空间分辨率。该方法可表示为：

$$
\hat {R}_{xx}=E\{ R_i \}
$$ 

其中 $R_i$ 为第 $i$ 个子阵列的自相关矩阵。

## 传统方法

**CBF~常规波束成型**

该方法大约在二战期间被提出，其本质是傅里叶变换在空域的直接应用，其分辨率受限于瑞丽限。该方法可表示为：

$$
J_{CBF}(\theta)=\frac {a^H(\theta)R_{xx}a(\theta)}{|a^H(\theta)a(\theta)|^{2}}
$$

**Capon~最小方差无失真响应法**

这是一种自适应方法，提出于60年代，它将维纳滤波的思想应用于空域处理，相比于CBF法，分辨率得到了一定的提高。Capon法可表示为：
$$
J_{Capon}(\theta)=\frac{1}{a^H(\theta)R_{xx}^{-1}a(\theta)}
$$

**ML~最大似然估计法**

最大似然 (Maximum Likelihood，ML) 估计方法就是贝叶斯估计方法的一种特例，是在已知高斯噪声情况下的贝叶斯最优估计。

最大似然估计在九十年代提出，实质为给定观测数据，评估模型参数。在DOA估计中，ML法可表示为：
$$
J_{ML}(\theta)=\mathrm {tr}\left(\frac{a(\theta)a^H(\theta)}{(a^H(\theta)a(\theta))^{-1}}*R_{xx}\right)/M
$$

在只有一个目标信源时，该方法等效于CBF法，在多个信源时，ML法为多目标优化问题，运算量较大。


## 子空间方法

**MUSIC~多重信号分类法**

时间回到上世纪八十年代，多重信号分类法在这个时候被提出。相比于CBF和Capon法仅使用接收数据的自相关矩阵直接处理，该方法考虑了信号和噪声的分布特性，通过对自相关矩阵做特征分解得到信号子空间和噪声子空间，利用两个子空间的正交特性进行DOA估计。

由于接收信号的自相关矩阵为赫米特(Hermite)矩阵，满足 $R^H=R$，根据相关引理，R RR酉相似于对角矩阵，可进行酉对角化分解，即存在 酉矩阵$U$和对角阵$V$，使 $U^HRU=V$，且V主对角线上的元素为 $R$ 的特征值。

MUSIC方法可表示为：
$$
\begin{align*}
J_{MUSIC}(\theta)&=\frac{a^H(\theta)a(\theta)}{a^H(\theta)(I-U_sU_s^H)a(\theta)}\, 或 \\ \, \\ J_{MUSIC}(\theta)&=\frac{a^H(\theta)a(\theta)}{a^H(\theta)(U_nU_n^H)a(\theta)}
\end{align*}
$$

为信号子空间，为$U$中$k$个最大特征值所对应的特征列向量组成的矩阵；$U_n\in \mathbb{C}^{M\times (M-k)}$ 为噪声子空间；$k$ 表示信源个数。在应用中可根据信源数量选择信号子空间解法或噪声子空间解法。

**ESPRIT~旋转不变子空间法**

与MUSIC法在同一时期被提出的子空间方法还有旋转不变子空间法，区别于MUSIC使用酉对角化后信号和噪声子空间的正交性，ESPRIT利用信号子空间的旋转不变特性来求解DOA。

假设存在两个完全相同的子阵列，且子阵列的间距 $\Delta$ 已知，假设两个子阵列接收到数据分别为 $x_1$ 和 $x_2$，则：
$$
x_1=As+n_1 \\ \, \\ x_2=A\Phi s+n_2 \\ \, \\ \Phi=\mathrm {diag}(e^{j\phi_1} \, e^{j\phi_2} \, \cdots \, e^{j\phi_p})
$$ 
只要得到两个子阵列的旋转不变关系 $\Phi$，就能得到信号的到达角，完成DOA估计。

仿真时，令$x_1$ 和 $x_2$ 分别为接收到的信号 $\mathbf{x}$ 的前$M − 1$ 行和后$M − 1$ 行所构成的两个子矩阵，令回波子阵列合并为 $x_{exp}=[x_1;x_2]$，计算$x_{exp}$ 的自相关矩阵$R_{esp}=E\{x_{exp}*x_{exp}^H\}$，对 $R_{esp}$ 做酉对角化分解，得到酉矩阵$U_{esp}$ 和特征值对角矩阵$V_{esp}$。根据目标数量从$U_{esp}$ 中提取信号子空间并进行拆分得到$U_s$ 的前$M-1$行和后$M-1$行，接下来需要求解ESPRIT矩阵$\Xi$，使：
$$
U_s\Xi=U_{s2}
$$

和特征值对角矩阵 $V_{exp}$。根据目标数量从 $U_{esp}$ 中提取信号子空间并进行拆分得到 $U_s$ 的前 $M-1$ 行 $U_{s1}$ 和后M-1行 $U_{s2}$ ，接下来需要求解ESPRIT矩阵 $\Xi$，使：
$$
U_{s1}\Xi = U_{s2}
$$
 

求解出ESPRIT矩阵 $\Xi$ 后，通过求取该矩阵的特征值并求其复角度即可得到来向角的正弦值，即：
$$
DOA_{exp} = \mathrm{arcsin}\left(\frac{-\mathrm{angle}(V_{esp})\lambda}{2\pi d}\right)
$$

其中，$V_{esp} \in \mathbb{C}^{P \times 1}$ 是由$\Xi$的特征值构成的向量。
求解ESPRIT矩阵 $\Xi$ 可使用最小二乘准则或总体最小二乘准则。

**最小二乘准则**
在最小二乘准则下，ESPRIT矩阵$\Xi_{LS}$的解为：
$$
\Xi_{LS} = \mathrm{pinv}(U_{s1}) \cdot U_{s2}
$$
 

**总体最小二乘准则**
在总体最小二乘准则下，令：
$$
V_{TLS-esp} = [U_{s1}, U_{s2}]
$$
对$V$做奇异值分解得到右奇异矩阵$V_{TLS-ESP} \in \mathbb{C}^{(P*2)\times (P*2)}$，将$V_{TLS-ESP}$等分为4个方阵并取右上角方阵$E_{12}$和右下角方阵$E_{22}$，则ESPRIT矩阵$\Xi_{TLS}$的解为：
$$
\Xi_{TLS} = -E_{12} \cdot \mathrm{inv}(E_{22})
$$

## 稀疏恢复方法

### 压缩感知理论
2004年，陶哲轩、Emmanuel Candes和David Donoho等人证明了"如果信号是稀疏的，那么它可以由远低于采样定理要求的采样点恢复信号"，提出了压缩感知概念。

**前提条件：**
1. 信号稀疏性：信号在某个变换域(时域、频域、空域等)上零点的数量远大于非零点
2. 观测矩阵和稀疏表示基不相关

**实现步骤：**

1. **压缩采样：**
$$
y = \Phi \Psi s = \Theta s
$$
其中：
- $\Phi$: 观测矩阵
- $\Psi$: 稀疏表示基  
- $\Theta$: 传感矩阵

2. **信号重构：**
$$
\min_s \| s \|_0 \quad \text{s.t.} \quad y = \Theta s
$$
其中$\| \cdot \|_0$为$l_0$范数，表示非零元素个数。实际求解中常松弛为$l_1$或$l_2$范数。

对于方程：$y=\Theta s$，已知 $y$ 和 $\Theta$ 求解未知数 $x$ 时，如果超定方程，问题实质为拟合问题；如果 $\Theta$ 为满秩的方阵，问题则为线性方程组求解；当 $\Theta$ 为欠定时，才是稀疏恢复问题。
在仿真中，对接收信号的自相关矩阵$R_{xx}$ 做酉对角化后，取酉矩阵中最大特征值对应的特征向量作为稀疏恢复的观测值矩阵 $y$，将波束形成矩阵 $a$ 作为传感器矩阵，稀疏恢复得到的 $s$ 即为DOA估计结果，即求解方程：
$$
y = a s
$$


### FOCUSS方法 (欠定系统聚焦法)

该方法由原始的FOCUSS方法改进而来，参考文献：
Gorodnitsky I F, Rao B D. Sparse signal reconstruction from limited data using FOCUSS: A re-weighted minimum norm algorithm[J]. IEEE Transactions on signal processing, 1997, 45(3): 600-616.

**算法步骤：**

1. **初始化：**
$$
s_0 = a^H \cdot \mathrm{inv}(aa^H) \cdot y
$$

2. **迭代过程：**
$$
W = \mathrm{diag}(s_k^{1-\lambda_{spe}/2})
$$
$$
s_{k+1} = WW^Ha^H \cdot \mathrm{inv}(aWW^Ha^H + \lambda_{reg}I) \cdot y
$$

**参数说明：**
- $\lambda_{reg}$: 正则化因子，过大趋于0解，过小结果发散
- $\lambda_{spe}$: 稀疏因子，等效于稀疏解的范数约束

3. **终止条件：**
迭代次数超过最大值或满足：
$$
\frac{\|s_{k+1}-s_k\|_2}{\|s_k\|} < \lambda_{err}
$$
其中$\lambda_{err}$为误差限制阈值。

### OMP方法 (正交匹配追踪法)

**算法步骤：**

1. **初始化：**
$$
\Omega_{mask} = \emptyset \\
r_0 = y
$$

2. **迭代过程：**
   - 辨识：
   $$
   j_k = \arg\max|\langle r_k, \phi_j \rangle|
   $$
   - 更新标签集：
   $$
   \Omega_{mask} = \Omega_{mask} \cup \{j_k\}
   $$
   - 估计：
   $$
   x_k = \mathrm{inv}(\Phi_{\Omega_k}^H\Phi_{\Omega_k}) \cdot \Phi_{\Omega_k}^H y
   $$
   - 更新残差：
   $$
   r_{k+1} = [I - \Phi_{\Omega_k}(\Phi_{\Omega_k}^T\Phi_{\Omega_k})^{-1}\Phi_{\Omega_k}^T] y
   $$

3. **终止条件：**
   - 迭代次数达到字典栅格数
   - 或 $a(:,\Omega_{mask})'*a(:,\Omega_{mask})$ 为奇异矩阵

4. **输出结果：**
$$
x(i) = \begin{cases}
x_k(i), & i \in \Omega_{mask} \\
0, & \text{其它}
\end{cases}
$$
 
### 伪逆法

**算法描述：**

对于方程：
$$
y = a s
$$

其解为：
$$
s = y^H \cdot \mathrm{pinv}(a)^H
$$

**特点：**
- 运算量较大
- 产生的解不是稀疏解
- 实际工程中很少使用
- 仿真中主要用于效果对比

### EM-SBL方法 (最大期望-稀疏贝叶斯学习法)

> [证明](./稀疏贝叶斯.md)

SBL算法是稀疏信号重构的重要算法，涉及高斯分布、最大似然估计、贝叶斯公式等数学理论。

**算法步骤：**

1. **初始化：**
$$
\Gamma_0 = 0.1 \cdot I
$$

2. **E-step：**
$$
\sigma_x = \mathrm{pinv}(\sigma(a^H a + \mathrm{pinv}(\Gamma_k)))
$$
$$
\mu_x = \sigma_x \sigma^{-1} a^H R_{xx}
$$
其中：
- $\sigma$: 阵列接收信号的估计噪声方差
- $R_{xx}$: 接收信号的自相关矩阵

3. **M-step：**
$$
\Gamma_{k+1} = \mu_x(\theta) \cdot \mu_x(\theta)^H / N + \sigma_x(\theta)
$$

4. **终止条件：**
迭代次数达到最大值或满足：
$$
\|\Gamma_{k+1} - \Gamma_k\|_1 < \text{误差限}
$$

**注：** 由于推导过程较为复杂，此处仅展示关键步骤。如需详细推导，可参考相关文献。
CVX~凸优化法
### CVX方法 (凸优化法)

**算法描述：**

将方程求解转化为凸优化问题：
$$
\arg\min_s \| s \|_p \quad \text{s.t.} \quad \|y - a s\|_2 < \lambda_{lim}
$$

**实现方式：**
- 使用凸优化工具箱进行求解
- 可调整范数$p$约束稀疏解：
  - $p=1$时产生稀疏解
  - $p$增大时增加松弛量，解密度增大

**参数说明：**
- $\lambda_{lim}$: 收敛控制量
  - 过小可能导致发散
  - 过大可能产生非稀疏解

仿真实现