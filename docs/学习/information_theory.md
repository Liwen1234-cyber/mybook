### 阶乘$x!$与$\begin{pmatrix}N\\ r\end{pmatrix}$

推导斯特林的近似值：

平均值为 λ 的泊松分布为
$$
P(r | \lambda)=e^{-\lambda}\frac{\lambda^r}{r!}\quad r\in\{0,1,2,\dots\}
$$
对于较大的 $λ$，至少在 $r ≃ λ$ 附近，这种分布可以很好地近似于均值为 $λ$、方差为 $λ$ 的高斯分布：
$$
e^{-\lambda}\frac{\lambda^r}{r!}\simeq\frac{1}{\sqrt{2\pi\lambda}} e^{-\frac{(r-\lambda)^2}{2\lambda}}
$$
把 $r = λ$ 插到这个公式中：
$$
\begin{array}{rcl}e^{-\lambda}\frac{\lambda^\lambda}{\lambda!}&\simeq&\frac{1}{\sqrt{2\pi\lambda}}\\ \Rightarrow\lambda!&\simeq&\lambda^\lambda e^{-\lambda}\sqrt{2\pi\lambda}\end{array}
$$
阶乘函数的斯特林近似：
$$
\begin{matrix}x!\simeq x^x e^{-x}\sqrt{2\pi x}&\Leftrightarrow&\ln x!\simeq x\ln x-x+\frac{1}{2}\ln2\pi x\end{matrix}
$$
现在我们将斯特林近似应用到$\begin{pmatrix}N\\ r\end{pmatrix}$：
$$
\ln\begin{pmatrix}N\\ r\end{pmatrix}\equiv\ln\frac{N!}{(N-r)!r!}\quad\simeq\quad(N-r)\ln\frac{N}{N-r}+r\ln\frac{N}{r}
$$
现在我们用 "ln "表示自然对数${(\log_{e})}$，用 "log "表示以 2 为底的对数$(\log_2)$

如果我们引入二元熵函数，
$$
H_2(x)\equiv x\log\frac{1}{x}+(1-x)\log\frac{1}{(1-x)}
$$
可以将式子重写为
$$
\log\begin{pmatrix}N\\ r\end{pmatrix}\simeq NH_2(r/N)
$$
以及更加精确的解：
$$
\log\begin{pmatrix}N\\ r\end{pmatrix}\simeq NH_2(r/N)-\frac{1}{2}\log\left[2\pi N \frac{N-r}{N} \frac{r}{N}\right]
$$


未完待续~~~