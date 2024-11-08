# How browsers work



> Behind the scenes of modern web browsers

!> [原文](https://web.dev/articles/howbrowserswork)

## 序言

这是一部关于 WebKit 和 Gecko 内部操作的综合性入门文章，是以色列开发者 Tali Garsiel 进行大量研究的成果。几年来，她查看了所有已发布的有关浏览器内部结构的数据，并花了大量时间阅读网络浏览器源代码。她写道：

> 作为 Web 开发者，**了解浏览器操作的内部机制有助于您做出更明智的决策，并了解开发最佳实践背后的理由**。虽然本文档内容较长，但我们建议您花些时间仔细阅读。您会很高兴。
> 
> Paul Irish，Chrome 开发者关系团队

## 简介

网络浏览器是最广泛使用的软件。在本入门课程中，我将介绍它们在后台的工作原理。我们将看看您在地址栏中输入 `google.com` 后会发生什么，直到您在浏览器屏幕上看到 Google 页面。

## 我们将介绍的浏览器

目前，桌面设备上有五种主要浏览器：Chrome、Internet Explorer、Firefox、Safari 和 Opera。在移动设备上，主要浏览器包括 Android 浏览器、iPhone、Opera Mini 和 Opera Mobile、UC 浏览器、Nokia S40/S60 浏览器和 Chrome，其中除了 Opera 浏览器外，所有浏览器均基于 WebKit。我将举例说明开源浏览器 Firefox 和 Chrome，以及 Safari（部分开源）。根据 [StatCounter 统计数据](http://gs.statcounter.com/)（截至 2013 年 6 月），Chrome、Firefox 和 Safari 占全球桌面浏览器使用量的约 71%。在移动设备上，Android 浏览器、iPhone 和 Chrome 的使用量约占 54%。

## 浏览器的主要功能

浏览器的主要功能是从服务器请求您选择的 Web 资源，并将其显示在浏览器窗口中。资源通常是 HTML 文档，但也可能是 PDF、图片或其他类型的内容。资源的位置由用户使用 URI（统一资源标识符）指定。

浏览器解释和显示 HTML 文件的方式在 HTML 和 CSS 规范中指定。 这些规范由 W3C（万维网联盟）组织维护，该组织是网络标准组织。多年来，浏览器都只遵守了这些规范中的部分要求，并且一直在开发自己的扩展程序。这给网站作者带来了严重的兼容性问题。目前，大多数浏览器或多或少都符合规范。

浏览器界面有很多共同之处。常见的界面元素包括：

1.  用于插入 URI 的地址栏
2.  “返回”和“前进”按钮
3.  书签选项
4.  用于刷新或停止加载当前文档的“刷新”和“停止”按钮
5.  用于前往首页的主屏幕按钮

奇怪的是，浏览器的界面并没有任何正式的规范，这只是源自多年来积累的良好实践以及浏览器彼此模仿的结果。 HTML5 规范未定义浏览器必须具备的界面元素，但列出了一些常见元素。其中包括地址栏、状态栏和工具栏。当然，有些功能是特定浏览器独有的，例如 Firefox 的下载管理器。

## 概要基础架构

浏览器的主要组件包括：

1.  **界面**：包括地址栏、后退/前进按钮、书签菜单等。浏览器界面的每个部分，但显示请求网页的窗口除外。
2.  **浏览器引擎**：在界面和渲染引擎之间协调操作。
3.  **呈现引擎**：负责显示请求的内容。例如，如果请求的内容是 HTML，呈现引擎会解析 HTML 和 CSS，并在屏幕上显示解析后的内容。
4.  **网络**：对于 HTTP 请求等网络调用，在平台无关接口后面为不同平台使用不同的实现。
5.  **界面后端**：用于绘制基本微件，例如组合框和窗口。此后端公开了与平台无关的通用接口。底层使用操作系统界面方法。
6.  **JavaScript 解释器**。用于解析和执行 JavaScript 代码。
7.  **数据存储**。这是一个持久层。浏览器可能需要在本地保存各种数据，例如 Cookie。浏览器还支持 localStorage、IndexedDB、WebSQL 和 FileSystem 等存储机制。

![浏览器组件](https://web.dev/static/articles/howbrowserswork/image/browser-components-9cd8ff834cc9c.png?hl=zh-cn)

图 1：浏览器组件

请务必注意，Chrome 等浏览器会运行多个呈现引擎实例：每个标签页对应一个实例。每个标签页都在单独的进程中运行。

## 渲染引擎

渲染引擎的职责是… 渲染，即在浏览器屏幕上显示请求的内容。

默认情况下，渲染引擎可以显示 HTML 和 XML 文档以及图片。它可以通过插件或扩展程序显示其他类型的数据；例如，使用 PDF 查看器插件显示 PDF 文档。不过，在本章中，我们将重点介绍主要用例：显示使用 CSS 设置格式的 HTML 和图片。

不同的浏览器使用不同的渲染引擎：Internet Explorer 使用 Trident、Firefox 使用 Gecko、Safari 使用 WebKit。Chrome 和 Opera（从 15 版开始）使用 Blink，它是 WebKit 的一个分支。

[WebKit](http://webkit.org/) 是一个开源渲染引擎，最初是 Linux 平台的引擎，后来被 Apple 修改为支持 Mac 和 Windows。

### 主流程

渲染引擎将开始从网络层获取请求的文档内容。这通常会以 8KB 的块进行。

之后，渲染引擎的基本流程如下：

![渲染引擎基本流程](https://web.dev/static/articles/howbrowserswork/image/rendering-engine-basic-fl-2fba02b24e871.png?hl=zh-cn)

图 2：渲染引擎基本流程

渲染引擎将开始解析 HTML 文档，并将元素转换为名为“内容树”的树中的 [DOM](https://web.dev/articles/howbrowserswork?hl=zh-cn#dom) 节点。该引擎将解析外部 CSS 文件和样式元素中的样式数据。样式信息以及 HTML 中的视觉说明将用于创建另一个树：[渲染树](https://web.dev/articles/howbrowserswork?hl=zh-cn#render_tree_construction)。

渲染树包含具有颜色和尺寸等视觉属性的矩形。矩形以正确的顺序显示在屏幕上。

渲染树构建完成后，进入“布局”流程。[](https://web.dev/articles/howbrowserswork?hl=zh-cn#layout)这意味着，为每个节点提供其应在屏幕上显示的确切坐标。下一个阶段是[绘制](https://web.dev/articles/howbrowserswork?hl=zh-cn#painting) - 系统会遍历渲染树，并使用界面后端层绘制每个节点。

请务必了解，这是一个渐进的过程。为了提供更好的用户体验，渲染引擎会尝试尽快在屏幕上显示内容。它不会等到所有 HTML 都解析完毕后才开始构建和布局渲染树。系统会解析并显示部分内容，同时继续处理不断从网络传入的其余内容。

#### 主要流程示例

![WebKit 主流程。](https://web.dev/static/articles/howbrowserswork/image/webkit-main-flow-b779d50c0cf28.png?hl=zh-cn)

图 3：WebKit 主流程

![Mozilla 的 Gecko 呈现引擎主流程。](https://web.dev/static/articles/howbrowserswork/image/mozillas-gecko-rendering-b18e445544965.jpg?hl=zh-cn)

图 4：Mozilla 的 Gecko 渲染引擎主流程

从图 3 和图 4 可以看出，虽然 WebKit 和 Gecko 使用的术语略有不同，但流程基本相同。

Gecko 将采用视觉格式的元素的树称为“帧树”。每个元素都是一个帧。WebKit 使用“渲染树”这一术语，它由“渲染对象”组成。WebKit 使用“布局”一词来表示元素的放置，而 Gecko 称之为“重新流式传输”。“附件”是 WebKit 用来连接 DOM 节点和视觉信息以创建渲染树的术语。一个次要的非语义差异是，Gecko 在 HTML 和 DOM 树之间增加了一层。它称为“内容接收器”，是用于制作 DOM 元素的工厂。我们将介绍该流程的各个部分：

### 解析 - 常规

解析是呈现引擎中非常重要的一个环节，因此我们将对此进行更深入的探讨。 我们先来简要介绍一下解析。

解析文档意味着将其转换为代码可以使用的结构。解析结果通常是表示文档结构的节点树。这称为解析树或语法树。

例如，解析表达式 `2 + 3 - 1` 可能会返回以下树：

![数学表达式树节点。](https://web.dev/static/articles/howbrowserswork/image/mathematical-expression-t-6681a2511ead2.png?hl=zh-cn)

图 5：数学表达式树节点

### 语法

解析基于文档遵循的语法规则：文档所用的语言或格式。您可以解析的每种格式都必须具有由词汇和语法规则组成的确定性语法。这种语言称为[无上下文语法](https://web.dev/articles/howbrowserswork?hl=zh-cn#not_a_context_free_grammar)。人类语言不是这种语言，因此无法使用传统的解析技术进行解析。

### 解析器 - 词法分析器组合

解析可分为两个子过程：词法分析和语法分析。

词法分析是将输入内容分解成多个词元的过程。 令牌是语言词汇：一系列有效的构建块。在人类语言中，它包含该语言的字典中出现的所有单词。

语法分析是指应用语言语法规则。

解析器通常会将工作分为两部分：负责将输入拆分为有效令牌的**词法分析器**（有时称为“分词器”）；以及负责根据语言语法规则分析文档结构以构建解析树的**解析器**。

词法分析器知道如何移除空格和换行符等无关紧要的字符。

![从源文档到解析树](https://web.dev/static/articles/howbrowserswork/image/from-source-document-par-c9c8c59da1ef2.png?hl=zh-cn)

图 6：从源文档到解析树

解析过程是迭代的。解析器通常会向词法分析器请求新的令牌，并尝试将该令牌与某个语法规则进行匹配。如果匹配到规则，系统会将与令牌对应的节点添加到解析树中，解析器会请求另一个令牌。

如果没有匹配的规则，解析器将在内部存储令牌，并不断请求令牌，直到找到与内部存储的所有令牌匹配的规则。如果未找到任何规则，解析器将引发异常。这意味着该文档无效且包含语法错误。

### 翻译

在许多情况下，解析树还不是最终产品。解析通常用于翻译：将输入文档转换为其他格式。编译就是这样一个例子。将源代码编译为机器代码的编译器会先将其解析为解析树，然后将该树转换为机器代码文档。

![编译流程](https://web.dev/static/articles/howbrowserswork/image/compilation-flow-57cfc3aa68a53.png?hl=zh-cn)

图 7：编译流程

#### 解析示例

在图 5 中，我们通过一个数学表达式构建了一个解析树。我们来尝试定义一种简单的数学语言，并了解解析过程。

语法：

1.  语言语法构成要素包括表达式、项和运算。
2.  我们的语言可以包含任意数量的表达式。
3.  表达式定义为“项”后跟“运算”后跟另一个“项”
4.  操作是加号令牌或减号令牌
5.  字词是整数标记或表达式

我们来分析一下输入 `2 + 3 - 1`。

与规则匹配的第一个子字符串是 `2`：根据规则 #5，它是一个字词。第二个匹配项是 `2 + 3`：这与第三条规则匹配：一个项接一个运算符，然后再接一个项。 只有在输入结束时，才会找到下一个匹配项。`2 + 3 - 1` 是一个表达式，因为我们已经知道 `2 + 3` 是一个项，因此我们有一个项，后跟一个运算，后跟另一个项。`2 + +` 与任何规则都不匹配，因此是无效的输入。

### 词汇和语法的正式定义

词汇通常由[正则表达式](http://www.regular-expressions.info/)表示。

例如，我们的语言将定义为：

```
<span>INTEGER</span><span>:</span><span> </span><span>0</span><span>|</span><span>[</span><span>1</span><span>-</span><span>9</span><span>][</span><span>0</span><span>-</span><span>9</span><span>]</span><span>*</span>
<span>PLUS</span><span>:</span><span> </span><span>+</span>
<span>MINUS</span><span>:</span><span> </span><span>-</span>
```

如您所见，整数由正则表达式定义。

语法通常采用名为 [BNF](http://en.wikipedia.org/wiki/Backus%E2%80%93Naur_Form) 的格式进行定义。我们的语言定义为：

```
<span>expression</span><span> </span><span>:=</span><span>  </span><span>term</span><span>  </span><span>operation</span><span>  </span><span>term</span>
<span>operation</span><span> </span><span>:=</span><span>  </span><span>PLUS</span><span> </span><span>|</span><span> </span><span>MINUS</span>
<span>term</span><span> </span><span>:=</span><span> </span><span>INTEGER</span><span> </span><span>|</span><span> </span><span>expression</span>
```

我们曾说过，如果某种语言的语法是无上下文语法，则可以由正则解析器解析。对无上下文语法的直观定义是：完全可以用 BNF 表示的语法。如需了解正式定义，请参阅[维基百科中有关与上下文无关的语法的文章](http://en.wikipedia.org/wiki/Context-free_grammar)

### 解析器类型

有两种类型的解析器：自上而下解析器和自下而上解析器。直观的解释是，自上而下的解析器会检查语法的宏观结构，并尝试找到匹配的规则。自下而上解析器从输入开始，从低级规则开始，逐步将其转换为语法规则，直到满足高级规则。

我们来看看这两种解析器如何解析我们的示例。

自上而下的解析器将从更高级别的规则开始：它将 `2 + 3` 识别为表达式。然后，它会将 `2 + 3 - 1` 识别为表达式（识别表达式的过程会不断演变，与其他规则匹配，但起点是最高级别的规则）。

自底向上解析器会扫描输入，直到匹配到规则。然后，它会将匹配的输入替换为规则。此过程将一直持续到输入结束。 部分匹配的表达式会放置在解析器的堆栈上。

这种自底向上的解析器称为“移位-规约”解析器，因为输入会向右移位（假设有一个指针先指向输入起始位置，然后向右移动），并逐渐规约为语法规则。

### 自动生成解析器

有一些工具可以生成解析器。您只需将所用语言的语法（词汇和语法规则）提供给他们，它们就会生成可正常运行的解析器。 创建解析器需要对解析有深刻的理解，而且手动创建经过优化的解析器并不容易，因此解析器生成器非常有用。

WebKit 使用两个众所周知的解析器生成器：[Flex](http://en.wikipedia.org/wiki/Flex_lexical_analyser) 用于创建词法分析器，[Bison](http://www.gnu.org/software/bison/) 用于创建解析器（您可能会遇到名称为 Lex 和 Yacc 的解析器）。Flex 输入是包含令牌的正则表达式定义的文件。Bison 的输入是 BNF 格式的语言语法规则。

## HTML 解析器

HTML 解析器的任务是将 HTML 标记解析为解析树。

### HTML 语法

HTML 的词汇和语法在 W3C 组织创建的规范中定义。

正如我们在解析简介中所了解到的，语法可以使用 BNF 等格式进行正式定义。

遗憾的是，所有的常规解析器都不适用于 HTML（我并不是开玩笑，它们会用于解析 CSS 和 JavaScript）。 HTML 无法轻松地通过解析器所需的无上下文语法进行定义。

定义 HTML 的正式格式是 DTD（文档类型定义），但它不是无上下文语法。

乍一看，这似乎很奇怪；HTML 与 XML 非常相似。有很多 XML 解析器可以使用。HTML 有一个 XML 变体 - XHTML，那么它们之间有什么重大区别？

不同之处在于 HTML 方法更加“宽容”：它允许您省略某些标记（然后以隐式方式添加），或者有时省略开始或结束标记，等等。 总体而言，它是一种“宽松”的语法，与 XML 的严格、苛刻的语法形成对比。

这些看似微不足道的细节大不相同。 一方面，这正是 HTML 如此受欢迎的主要原因：它可以容忍您的错误，让 Web 作者轻松上手。 另一方面，这会使编写正式语法变得困难。总而言之，由于 HTML 的语法不是无上下文的，因此传统解析器无法轻松解析 HTML。XML 解析器无法解析 HTML。

### HTML DTD

HTML 定义采用的是 DTD 格式。此格式用于定义 [SGML](http://en.wikipedia.org/wiki/Standard_Generalized_Markup_Language) 家族的语言。该格式包含所有允许的元素及其属性和层次结构的定义。如前所述，HTML DTD 不构成无上下文语法。

DTD 存在一些变体。严格模式仅符合规范，但其他模式支持浏览器过去使用的标记。目的是与旧版内容向后兼容。 您可以点击以下链接查看当前的严格 DTD： [www.w3.org/TR/html4/strict.dtd](http://www.w3.org/TR/html4/strict.dtd)

### DOM

输出树（“解析树”）是 DOM 元素和属性节点的树。 DOM 是文档对象模型的简称。它是 HTML 文档的对象呈现，也是 HTML 元素与外界（例如 JavaScript）的接口。

树的根是“[Document](http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core.html#i-Document)”对象。

DOM 与标记之间几乎是一对一的关系。例如：

```
&lt;html&gt;
  &lt;body&gt;
    &lt;p&gt;
      Hello World
    &lt;/p&gt;
    &lt;div&gt; &lt;img src="example.png"/&gt;&lt;/div&gt;
  &lt;/body&gt;
&lt;/html&gt;
```

该标记将转换为以下 DOM 树：

![示例标记的 DOM 树](https://web.dev/static/articles/howbrowserswork/image/dom-tree-the-example-mar-70be67fe14c9a.png?hl=zh-cn)

图 8：示例标记的 DOM 树

与 HTML 一样，DOM 由 W3C 组织指定。请参阅 [www.w3.org/DOM/DOMTR](http://www.w3.org/DOM/DOMTR)。 它是用于操作文档的通用规范。特定模块用于描述 HTML 专用元素。您可以访问以下网址查看 HTML 定义：[www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109/idl-definitions.html](http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109/idl-definitions.html)。

我所说的树包含 DOM 节点，指的是树是由实现了某个 DOM 接口的元素构成的。浏览器使用具有浏览器内部使用的其他属性的具体实现。

#### 解析算法

如我们在上一部分中所见，无法使用常规的从上到下或从下到上的解析器解析 HTML。

原因如下：

1.  语言的宽容性。
2.  浏览器具有传统的容错能力，可以支持众所周知的 HTML 无效情况。
3.  解析过程是可重入的。对于其他语言，来源在解析过程中不会发生变化，但在 HTML 中，动态代码（如包含 `document.write()` 调用的脚本元素）可能会添加额外的标记，因此解析过程实际上会修改输入内容。

由于无法使用常规的解析技术，浏览器就创建了自定义的解析器来解析 HTML。

[HTML5 规范详细介绍了解析算法](http://www.whatwg.org/specs/web-apps/current-work/multipage/parsing.html)。该算法包含两个阶段：令牌化和树构建。

词元化是词法分析，用于将输入解析为词元。HTML 令牌包括起始标记、结束标记、属性名称和属性值。

标记生成器识别该标记，将其提供给树构造函数，然后使用下一个字符来识别下一个标记，依此类推，直到输入结束。

![HTML 解析流程（摘自 HTML5 规范）](https://web.dev/static/articles/howbrowserswork/image/html-parsing-flow-taken-6118c51b92b56.png?hl=zh-cn)

图 9：HTML 解析流程（摘自 HTML5 规范）

### 令牌化算法

该算法的输出是 HTML 标记。 该算法以状态机的形式表示。每个状态都会使用输入流中的一个或多个字符，并根据这些字符更新下一个状态。此决定会受到当前的令牌化状态和树构建状态的影响。这意味着，对于正确的下一个状态，相同的已消耗字符会产生不同的结果，具体取决于当前状态。 该算法过于复杂，无法完整描述，因此我们来看一个简单的示例，以便了解其原理。

基本示例 - 将以下 HTML 标记化：

```
&lt;html&gt;
  &lt;body&gt;
    Hello world
  &lt;/body&gt;
&lt;/html&gt;
```

初始状态为“数据状态”。 当遇到 `<` 字符时，状态会更改为**“标记打开状态”**。 接收一个 `a-z` 字符会创建“起始标记令牌”，状态会更改为**“标记名称状态”**。 我们会一直保持此状态，直到 `>` 字符被消耗完。每个字符都会附加到新词元名称中。在本例中，创建的令牌是 `html` 令牌。

达到 `>` 标记后，系统会发出当前令牌，并且状态会恢复为**“数据状态”**。系统会按照相同的步骤处理 `<body>` 标记。到目前为止，`html` 和 `body` 标记已发出。现在，我们回到**“数据状态”**。 使用 `Hello world` 的 `H` 字符会导致创建并发送字符令牌，这种情况会持续到达到 `</body>` 的 `<` 为止。我们将为 `Hello world` 的每个字符发出一个字符令牌。

现在，我们回到**“代码处于打开状态”**。 使用下一个输入 `/` 会导致创建 `end tag token` 并移至**“标记名称状态”**。再次强调一下，我们会一直保持此状态，直到达到 `>`。然后，系统会发出新的代码令牌，我们会返回到**“数据状态”**。 系统会将 `</html>` 输入视为前面的示例。

![对示例输入进行标记化](https://web.dev/static/articles/howbrowserswork/image/tokenizing-example-input-9d0cc36689681.png?hl=zh-cn)

图 10：对示例输入进行标记化

#### 树构建算法

创建解析器时，系统会创建 Document 对象。在树构建阶段，系统会修改根目录中包含文档的 DOM 树，并向其中添加元素。分词器发出的每个节点都将由树构造函数处理。规范中会为每个令牌定义哪个 DOM 元素与其相关，并且将为该令牌创建该元素。元素会添加到 DOM 树以及打开的元素堆栈中。 此堆栈用于更正嵌套不匹配和未闭合标记。该算法还可描述为状态机。这些状态称为“插入模式”。

我们来看看示例输入的树构建过程：

```
&lt;html&gt;
  &lt;body&gt;
    Hello world
  &lt;/body&gt;
&lt;/html&gt;
```

树构建阶段的输入是来自标记化阶段的词元序列。第一种模式是**“初始模式”**。收到“html”令牌将导致系统切换到**“html 之前”**模式，并在该模式下重新处理令牌。这将导致创建 HTMLHtmlElement 元素，该元素将附加到根 Document 对象。

状态将更改为**“在 head 之前”**。然后，系统会收到“body”令牌。系统会隐式创建 HTMLHeadElement，即使我们没有“head”令牌，它也会被添加到树中。

现在，我们将进入**“在头部前面”**模式，然后进入**“在头部后面”**模式。系统会重新处理正文令牌，创建并插入 HTMLBodyElement，并将模式转换为**“in body”**。

现在，系统会收到“Hello world”字符串的字符令牌。第一个字符会导致创建并插入“文本”节点，其他字符会附加到该节点。

收到正文结束令牌后，系统会转换为**“正文后”**模式。现在，我们将收到 html 结束标记，这会将我们转换到**“body 后”**模式。收到文件结束令牌后，解析将结束。

![示例 HTML 的树构建。](https://web.dev/static/articles/howbrowserswork/image/tree-construction-exampl-4e9757a851f96.gif?hl=zh-cn)

图 11：示例 HTML 的树状结构

### 解析完成后的操作

在此阶段，浏览器会将文档标记为交互式，并开始解析处于“延迟”模式的脚本：这些脚本应在文档解析完毕后执行。然后，文档状态将设置为“完成”，并会触发“load”事件。

您可以[在 HTML5 规范中查看标记化和树构建的完整算法](http://www.w3.org/TR/html5/syntax.html#html-parser)。

### 浏览器的错误容错性

您在浏览 HTML 网页时绝不会遇到“语法无效”错误。 浏览器会修正所有无效内容，然后继续操作。

以下面的 HTML 为例：

```
&lt;html&gt;
  &lt;mytag&gt;
  &lt;/mytag&gt;
  &lt;div&gt;
  &lt;p&gt;
  &lt;/div&gt;
    Really lousy HTML
  &lt;/p&gt;
&lt;/html&gt;
```

我必须违反了大约一百万条规则（“mytag”不是标准标记，“p”和“div”元素的嵌套错误等等），但浏览器仍然能正确显示这些内容，而且毫无疑问。 因此，解析器代码的大部分内容都是用于修正 HTML 作者的错误。

浏览器中的错误处理方式非常一致，但令人惊讶的是，它并未包含在 HTML 规范中。就像书签和返回/前进按钮一样，这只是浏览器多年来发展起来的功能。已知无效 HTML 结构在许多网站上重复出现，这些浏览器会尝试按照其他浏览器的方式来修复这些元素。

HTML5 规范确实定义了其中一些要求。（WebKit 在 HTML 解析器类开头的注释中对此进行了很好的总结。）

解析器会将令牌化输入解析到文档中，从而构建文档树。如果文档格式正确，解析就很简单了。

遗憾的是，我们必须处理许多格式不正确的 HTML 文档，因此解析器必须容忍错误。

我们至少要处理以下错误情况：

1.  系统明确禁止在某些外部标记内添加相应元素。在这种情况下，我们应关闭所有标记，直到禁止该元素的标记，然后再添加该元素。
2.  我们不能直接添加该元素。编写文档的人员可能忘记了中间的某些标记（或者中间的标记是可选的）。以下代码段可能存在此问题：HTML HEAD BODY TBODY TR TD LI（我是不是漏了什么？）。
3.  我们希望在内嵌元素内添加一个块元素。关闭所有内嵌元素，直至下一个更高级别的 block 元素。
4.  如果这样没有帮助，请关闭元素，直到我们允许添加元素为止，或者忽略该标记。

我们来看一些 WebKit 错误容错示例：

#### `</br>` 代替 `<br>`

有些网站会使用 `</br>` 而非 `<br>`。为了与 IE 和 Firefox 兼容，WebKit 会将其视为 `<br>`。

代码：

```
<span>if</span><span> </span><span>(</span><span>t</span><span>-</span>&gt;<span>isCloseTag</span><span>(</span><span>brTag</span><span>)</span><span> &amp;&amp; </span><span>m_document</span><span>-</span>&gt;<span>inCompatMode</span><span>())</span><span> </span><span>{</span>
<span>     </span><span>reportError</span><span>(</span><span>MalformedBRError</span><span>);</span>
<span>     </span><span>t</span><span>-</span>&gt;<span>beginTag</span><span> </span><span>=</span><span> </span><span>true</span><span>;</span>
<span>}</span>
```

请注意，错误处理是在内部进行的：不会向用户显示。

#### 一个孤岛表

离散表格是指位于另一个表格中，但不属于某个表格单元格中的表格。

例如：

```
&lt;table&gt;
  &lt;table&gt;
    &lt;tr&gt;&lt;td&gt;inner table&lt;/td&gt;&lt;/tr&gt;
  &lt;/table&gt;
  &lt;tr&gt;&lt;td&gt;outer table&lt;/td&gt;&lt;/tr&gt;
&lt;/table&gt;
```

WebKit 将将层次结构更改为两个同级表：

```
&lt;table&gt;
  &lt;tr&gt;&lt;td&gt;outer table&lt;/td&gt;&lt;/tr&gt;
&lt;/table&gt;
&lt;table&gt;
  &lt;tr&gt;&lt;td&gt;inner table&lt;/td&gt;&lt;/tr&gt;
&lt;/table&gt;
```

代码：

```
<span>if</span><span> </span><span>(</span><span>m_inStrayTableContent</span><span> &amp;&amp; </span><span>localName</span><span> </span><span>==</span><span> </span><span>tableTag</span><span>)</span>
<span>        </span><span>popBlock</span><span>(</span><span>tableTag</span><span>);</span>
```

WebKit 会为当前元素内容使用一个堆栈：它会将内部表从外部表堆栈中弹出。现在，这些表将是同级表。

#### 嵌套表单元素

如果用户将一个表单放入另一个表单中，系统会忽略第二个表单。

代码：

```
<span>if</span><span> </span><span>(</span><span>!</span><span>m_currentFormElement</span><span>)</span><span> </span><span>{</span>
<span>        </span><span>m_currentFormElement</span><span> </span><span>=</span><span> </span><span>new</span><span> </span><span>HTMLFormElement</span><span>(</span><span>formTag</span><span>,</span><span>    </span><span>m_document</span><span>);</span>
<span>}</span>
```

#### 代码层次结构过深

注释已经说明了一切。

```
<span>bool</span><span> </span><span>HTMLParser</span><span>::</span><span>allowNestedRedundantTag</span><span>(</span><span>const</span><span> </span><span>AtomicString</span>&amp;<span> </span><span>tagName</span><span>)</span>
<span>{</span>

<span>unsigned</span><span> </span><span>i</span><span> </span><span>=</span><span> </span><span>0</span><span>;</span>
<span>for</span><span> </span><span>(</span><span>HTMLStackElem</span><span>*</span><span> </span><span>curr</span><span> </span><span>=</span><span> </span><span>m_blockStack</span><span>;</span>
<span>         </span><span>i</span><span> &lt; </span><span>cMaxRedundantTagDepth</span><span> &amp;&amp; </span><span>curr</span><span> &amp;&amp; </span><span>curr</span><span>-</span>&gt;<span>tagName</span><span> </span><span>==</span><span> </span><span>tagName</span><span>;</span>
<span>     </span><span>curr</span><span> </span><span>=</span><span> </span><span>curr</span><span>-</span>&gt;<span>next</span><span>,</span><span> </span><span>i</span><span>++</span><span>)</span><span> </span><span>{</span><span> </span><span>}</span>
<span>return</span><span> </span><span>i</span><span> </span><span>!=</span><span> </span><span>cMaxRedundantTagDepth</span><span>;</span>
<span>}</span>
```

#### 放错位置的 HTML 或正文结束标记

再次说明一下，评论本身就是最好的证明。

```
<span>if</span><span> </span><span>(</span><span>t</span><span>-</span>&gt;<span>tagName</span><span> </span><span>==</span><span> </span><span>htmlTag</span><span> </span><span>||</span><span> </span><span>t</span><span>-</span>&gt;<span>tagName</span><span> </span><span>==</span><span> </span><span>bodyTag</span><span> </span><span>)</span>
<span>        </span><span>return</span><span>;</span>
```

因此，Web 作者请注意，除非您想在 WebKit 错误容错代码段中作为示例出现，否则请编写格式正确的 HTML。

## CSS 解析

还记得本教程简介中介绍的解析概念吗？与 HTML 不同，CSS 是一种与上下文无关的语法，可以使用简介中描述的解析器类型进行解析。 事实上，[CSS 规范定义了 CSS 的词法和语法语法](http://www.w3.org/TR/CSS2/grammar.html)。

我们来看一些示例：

词法语法（词汇）由每个令牌的正则表达式定义：

```
comment   \/\*[^*]*\*+([^/*][^*]*\*+)*\/
num       [0-9]+|[0-9]*"."[0-9]+
nonascii  [\200-\377]
nmstart   [_a-z]|{nonascii}|{escape}
nmchar    [_a-z0-9-]|{nonascii}|{escape}
name      {nmchar}+
ident     {nmstart}{nmchar}*
```

“ident”是标识符（例如类名称）的简称。“name”是元素 ID（通过“#”来引用）

语法语法在 BNF 中进行了说明。

```
<span>ruleset</span>
<span>  </span><span>:</span><span> </span><span>selector</span><span> </span><span>[</span><span> </span><span>','</span><span> </span><span>S</span><span>*</span><span> </span><span>selector</span><span> </span><span>]*</span>
<span>    </span><span>'{'</span><span> </span><span>S</span><span>*</span><span> </span><span>declaration</span><span> </span><span>[</span><span> </span><span>';'</span><span> </span><span>S</span><span>*</span><span> </span><span>declaration</span><span> </span><span>]*</span><span> </span><span>'}'</span><span> </span><span>S</span><span>*</span>
<span>  </span><span>;</span>
<span>selector</span>
<span>  </span><span>:</span><span> </span><span>simple_selector</span><span> </span><span>[</span><span> </span><span>combinator</span><span> </span><span>selector</span><span> </span><span>|</span><span> </span><span>S</span><span>+</span><span> </span><span>[</span><span> </span><span>combinator</span><span>?</span><span> </span><span>selector</span><span> </span><span>]?</span><span> </span><span>]?</span>
<span>  </span><span>;</span>
<span>simple_selector</span>
<span>  </span><span>:</span><span> </span><span>element_name</span><span> </span><span>[</span><span> </span><span>HASH</span><span> </span><span>|</span><span> </span><span>class</span><span> </span><span>|</span><span> </span><span>attrib</span><span> </span><span>|</span><span> </span><span>pseudo</span><span> </span><span>]*</span>
<span>  </span><span>|</span><span> </span><span>[</span><span> </span><span>HASH</span><span> </span><span>|</span><span> </span><span>class</span><span> </span><span>|</span><span> </span><span>attrib</span><span> </span><span>|</span><span> </span><span>pseudo</span><span> </span><span>]+</span>
<span>  </span><span>;</span>
<span>class</span>
<span>  </span><span>:</span><span> </span><span>'.'</span><span> </span><span>IDENT</span>
<span>  </span><span>;</span>
<span>element_name</span>
<span>  </span><span>:</span><span> </span><span>IDENT</span><span> </span><span>|</span><span> </span><span>'*'</span>
<span>  </span><span>;</span>
<span>attrib</span>
<span>  </span><span>:</span><span> </span><span>'['</span><span> </span><span>S</span><span>*</span><span> </span><span>IDENT</span><span> </span><span>S</span><span>*</span><span> </span><span>[</span><span> </span><span>[</span><span> </span><span>'='</span><span> </span><span>|</span><span> </span><span>INCLUDES</span><span> </span><span>|</span><span> </span><span>DASHMATCH</span><span> </span><span>]</span><span> </span><span>S</span><span>*</span>
<span>    </span><span>[</span><span> </span><span>IDENT</span><span> </span><span>|</span><span> </span><span>STRING</span><span> </span><span>]</span><span> </span><span>S</span><span>*</span><span> </span><span>]</span><span> </span><span>']'</span>
<span>  </span><span>;</span>
<span>pseudo</span>
<span>  </span><span>:</span><span> </span><span>':'</span><span> </span><span>[</span><span> </span><span>IDENT</span><span> </span><span>|</span><span> </span><span>FUNCTION</span><span> </span><span>S</span><span>*</span><span> </span><span>[</span><span>IDENT</span><span> </span><span>S</span><span>*]</span><span> </span><span>')'</span><span> </span><span>]</span>
<span>  </span><span>;</span>
```

说明：

规则集的结构如下：

```
<span>div</span><span>.</span><span>error</span><span>,</span><span> </span><span>a</span><span>.</span><span>error</span><span> </span><span>{</span>
<span>  </span><span>color</span><span>:</span><span>red</span><span>;</span>
<span>  </span><span>font-weight</span><span>:</span><span>bold</span><span>;</span>
<span>}</span>
```

`div.error` 和 `a.error` 是选择器。大括号内的部分包含此规则集应用的规则。此结构的正式定义如下：

```
<span>ruleset</span>
<span>  </span><span>:</span><span> </span><span>selector</span><span> </span><span>[</span><span> </span><span>','</span><span> </span><span>S</span><span>*</span><span> </span><span>selector</span><span> </span><span>]*</span>
<span>    </span><span>'{'</span><span> </span><span>S</span><span>*</span><span> </span><span>declaration</span><span> </span><span>[</span><span> </span><span>';'</span><span> </span><span>S</span><span>*</span><span> </span><span>declaration</span><span> </span><span>]*</span><span> </span><span>'}'</span><span> </span><span>S</span><span>*</span>
<span>  </span><span>;</span>
```

这意味着规则集是一个选择器，或者可以是多个选择器（以英文逗号和空格分隔，S 代表空格）。规则集包含大括号，大括号中包含一个声明，或者可选地包含多个声明（以英文分号分隔）。 “声明”和“选择器”将在以下 BNF 定义中定义。

### WebKit CSS 解析器

WebKit 使用 [Flex 和 Bison](https://web.dev/articles/howbrowserswork?hl=zh-cn#generating_parsers_automatically) 解析器生成器从 CSS 语法文件自动创建解析器。如解析器简介中所述，Bison 会创建自底向上的移位-规约解析器。Firefox 使用手动编写的顶向下解析器。在这两种情况下，每个 CSS 文件都会解析为 StyleSheet 对象。每个对象都包含 CSS 规则。CSS 规则对象包含选择器和声明对象，以及其他与 CSS 语法对应的对象。

![解析 CSS。](https://web.dev/static/articles/howbrowserswork/image/parsing-css-4531ebee58764.png?hl=zh-cn)

图 12：解析 CSS

## 脚本和样式表的处理顺序

### 脚本

网络的模型是同步的。作者希望在解析器到达 `<script>` 标记时，脚本立即被解析并执行。文档解析将停止，直到脚本执行完毕。 如果脚本是外部脚本，则必须先从网络提取资源 - 这也是同步完成的，并且在提取资源之前，解析会暂停。这种模式多年来一直被采用，并且在 HTML4 和 5 规范中也有指定。作者可以向脚本添加“defer”属性，在这种情况下，脚本不会停止文档解析，而是会在文档解析完毕后执行。HTML5 添加了一个选项，用于将脚本标记为异步，以便由其他线程解析和执行。

### 推测解析

WebKit 和 Firefox 都会执行此优化。在执行脚本时，另一个线程会解析文档的其余部分，找出需要从网络加载的其他资源并加载它们。这样，系统就可以在并行连接上加载资源，从而提高整体速度。请注意：推测解析器仅解析对外部资源（如外部脚本、样式表和图片）的引用：它不会修改 DOM 树，而是由主解析器决定的。

### 样式表

另一方面，样式表采用的是不同的模型。 从概念上讲，由于样式表不会更改 DOM 树，因此似乎没有理由等待样式表并停止文档解析。不过，在文档解析阶段，脚本会请求样式信息，这会导致一个问题。如果样式尚未加载和解析，脚本将获得错误的答案，这显然会导致很多问题。这似乎是一个极端情况，但却很常见。Firefox 会在有样式表仍在加载和解析时阻止所有脚本。 只有当脚本尝试访问可能受未加载样式表影响的特定样式属性时，WebKit 才会阻止脚本。

## 渲染树构建

在构建 DOM 树时，浏览器会构建另一个树，即渲染树。此树状结构显示了视觉元素的显示顺序。它是文档的直观呈现。 此树的用途是按照正确的顺序绘制内容。

Firefox 将呈现树中的元素称为“框架”。WebKit 使用“渲染程序”或“渲染对象”一词。

渲染程序知道如何布局和绘制自身及其子项。

WebKit 的 RenderObject 类是渲染程序的基类，其定义如下：

```
<span>class</span><span> </span><span>RenderObject</span><span>{</span>
<span>  </span><span>virtual</span><span> </span><span>void</span><span> </span><span>layout</span><span>();</span>
<span>  </span><span>virtual</span><span> </span><span>void</span><span> </span><span>paint</span><span>(</span><span>PaintInfo</span><span>);</span>
<span>  </span><span>virtual</span><span> </span><span>void</span><span> </span><span>rect</span><span> </span><span>repaintRect</span><span>();</span>
<span>  </span><span>Node</span><span>*</span><span> </span><span>node</span><span>;</span><span>  </span><span>//the DOM node</span>
<span>  </span><span>RenderStyle</span><span>*</span><span> </span><span>style</span><span>;</span><span>  </span><span>// the computed style</span>
<span>  </span><span>RenderLayer</span><span>*</span><span> </span><span>containgLayer</span><span>;</span><span> </span><span>//the containing z-index layer</span>
<span>}</span>
```

每个渲染程序都表示一个矩形区域，通常对应于节点的 CSS 框（如 CSS2 规范中所述）。它包含宽度、高度和位置等几何信息。

框类型受与节点相关的样式属性的“display”值影响（请参阅[样式计算](https://web.dev/articles/howbrowserswork?hl=zh-cn#style_computation)部分）。 以下 WebKit 代码用于根据 display 属性，决定应为 DOM 节点创建哪种类型的渲染程序：

```
<span>RenderObject</span><span>*</span><span> </span><span>RenderObject</span><span>::</span><span>createObject</span><span>(</span><span>Node</span><span>*</span><span> </span><span>node</span><span>,</span><span> </span><span>RenderStyle</span><span>*</span><span> </span><span>style</span><span>)</span>
<span>{</span>
<span>    </span><span>Document</span><span>*</span><span> </span><span>doc</span><span> </span><span>=</span><span> </span><span>node</span><span>-</span>&gt;<span>document</span><span>();</span>
<span>    </span><span>RenderArena</span><span>*</span><span> </span><span>arena</span><span> </span><span>=</span><span> </span><span>doc</span><span>-</span>&gt;<span>renderArena</span><span>();</span>
<span>    </span><span>...</span>
<span>    </span><span>RenderObject</span><span>*</span><span> </span><span>o</span><span> </span><span>=</span><span> </span><span>0</span><span>;</span>

<span>    </span><span>switch</span><span> </span><span>(</span><span>style</span><span>-</span>&gt;<span>display</span><span>())</span><span> </span><span>{</span>
<span>        </span><span>case</span><span> </span><span>NONE</span><span>:</span>
<span>            </span><span>break</span><span>;</span>
<span>        </span><span>case</span><span> </span><span>INLINE</span><span>:</span>
<span>            </span><span>o</span><span> </span><span>=</span><span> </span><span>new</span><span> </span><span>(</span><span>arena</span><span>)</span><span> </span><span>RenderInline</span><span>(</span><span>node</span><span>);</span>
<span>            </span><span>break</span><span>;</span>
<span>        </span><span>case</span><span> </span><span>BLOCK</span><span>:</span>
<span>            </span><span>o</span><span> </span><span>=</span><span> </span><span>new</span><span> </span><span>(</span><span>arena</span><span>)</span><span> </span><span>RenderBlock</span><span>(</span><span>node</span><span>);</span>
<span>            </span><span>break</span><span>;</span>
<span>        </span><span>case</span><span> </span><span>INLINE_BLOCK</span><span>:</span>
<span>            </span><span>o</span><span> </span><span>=</span><span> </span><span>new</span><span> </span><span>(</span><span>arena</span><span>)</span><span> </span><span>RenderBlock</span><span>(</span><span>node</span><span>);</span>
<span>            </span><span>break</span><span>;</span>
<span>        </span><span>case</span><span> </span><span>LIST_ITEM</span><span>:</span>
<span>            </span><span>o</span><span> </span><span>=</span><span> </span><span>new</span><span> </span><span>(</span><span>arena</span><span>)</span><span> </span><span>RenderListItem</span><span>(</span><span>node</span><span>);</span>
<span>            </span><span>break</span><span>;</span>
<span>       </span><span>...</span>
<span>    </span><span>}</span>

<span>    </span><span>return</span><span> </span><span>o</span><span>;</span>
<span>}</span>
```

系统还会考虑元素类型：例如，表单控件和表格具有特殊的框架。

在 WebKit 中，如果元素想要创建特殊的渲染程序，则会替换 `createRenderer()` 方法。渲染程序指向包含非几何图形信息的样式对象。

### 渲染树与 DOM 树的关系

渲染程序与 DOM 元素相对应，但并非一一对应。非可视 DOM 元素不会插入渲染树中。例如，“head”元素。此外，如果元素的显示值为“none”，则这些元素不会显示在树中（可见性为“隐藏”的元素则会显示在树中）。

有些 DOM 元素对应于多个视觉对象。这些元素通常是具有复杂结构的元素，无法用单个矩形来描述。例如，“select”元素有三个渲染程序：一个用于显示区域，一个用于下拉列表框，一个用于按钮。此外，如果文本因宽度不足以显示一行而被拆分为多行，则系统会将新行添加为额外的渲染程序。

多渲染程序的另一个示例是损坏的 HTML。根据 CSS 规范，内嵌元素必须仅包含块元素或仅包含内嵌元素。对于混合内容，系统会创建匿名块渲染程序来封装内嵌元素。

某些渲染对象与 DOM 节点相对应，但不在树中的同一位置。浮动元素和绝对定位元素不在流中，放置在树的其他部分，并映射到真实框架。占位符框应该是它们原本所在的位置。

![呈现树和对应的 DOM 树。](https://web.dev/static/articles/howbrowserswork/image/the-render-tree-the-corr-f699894ef4c75.png?hl=zh-cn)

图 13：渲染树和相应的 DOM 树。“视口”是初始容器块。在 WebKit 中，它将是“RenderView”对象

#### 构建树的流程

在 Firefox 中，系统会将呈现方式注册为 DOM 更新监听器。 演示文稿会将帧创建委托给 `FrameConstructor`，然后构造函数会解析样式（请参阅[样式计算](https://web.dev/articles/howbrowserswork?hl=zh-cn#style_computation)）并创建帧。

在 WebKit 中，解析样式和创建渲染程序的过程称为“附加”。每个 DOM 节点都有一个“attach”方法。附加是同步进行的，向 DOM 树插入节点会调用新的节点“attach”方法。

处理 html 和 body 标记会导致构建渲染树根。根渲染对象对应于 CSS 规范中所称的“包含块”：包含所有其他块的顶级块。其尺寸为视口：浏览器窗口显示区域尺寸。 Firefox 将其称为 `ViewPortFrame`，WebKit 将其称为 `RenderView`。这是文档指向的渲染对象。树的其余部分构建为 DOM 节点插入。

请参阅[关于处理模型的 CSS2 规范](http://www.w3.org/TR/CSS21/intro.html#processing-model)。

### 样式计算

构建渲染树需要计算每个渲染对象的视觉属性。具体方法是计算每个元素的样式属性。

样式包括各种来源的样式表、HTML 中的内嵌样式元素和视觉属性（例如“bgcolor”属性）。后者会转换为匹配的 CSS 样式属性。

样式表的起源是浏览器的默认样式表、网页作者提供的样式表和用户样式表，这些是浏览器用户提供的样式表（浏览器允许您定义自己喜欢的样式。例如，在 Firefox 中，只需将样式表放入“Firefox 个人资料”文件夹中即可实现此目的。

样式计算会产生几个难题：

1.  样式数据是一个非常大的结构，存储了无数的样式属性，这可能会导致内存问题。
2.  如果不优化，为每个元素查找匹配规则可能会导致性能问题。对每个元素遍历整个规则列表以查找匹配项是一项繁重的工作。选择器可能具有复杂的结构，这可能会导致匹配过程从一个看似有希望的路径开始，但最终证明该路径无效，因此必须尝试其他路径。
    
    例如，以下复合选择器：
    
    ```
    <span>div</span><span> </span><span>div</span><span> </span><span>div</span><span> </span><span>div</span><span>{</span>
    <span>...</span>
    <span>}</span>
    ```
    
    这意味着规则适用于作为 3 个 div 的后代的 `<div>`。假设您想检查该规则是否适用于给定的 `<div>` 元素。您可以选择树中的某个路径进行检查。您可能需要向上遍历节点树，才能发现只有两个 div，并且规则不适用。然后，您需要尝试树中的其他路径。
    
3.  应用这些规则涉及到相当复杂的级联规则，用于定义规则的层次结构。
    

我们来看看浏览器是如何应对这些问题的：

### 共享样式数据

WebKit 节点引用样式对象 (RenderStyle)。 在某些情况下，这些对象可以由节点共享。节点是兄弟姐妹或堂表亲，并且：

1.  元素必须处于相同的鼠标状态（例如，一个元素不能处于 :hover 状态，而另一个元素不处于该状态）
2.  这两个元素都不能有 ID
3.  代码名称应保持一致
4.  类属性应匹配
5.  一组映射的属性必须完全相同
6.  链接状态必须一致
7.  焦点状态必须一致
8.  任何元素都不应受到属性选择器的影响，这里所说的“受影响的”是指，任何选择器匹配项在选择器中的任何位置都使用了某个属性选择器
9.  元素中不得有任何内嵌样式属性
10.  不得使用任何同级选择器。WebCore 在遇到任何同级选择器时，只会抛出一个全局开关，并在存在同级选择器时为整个文档停用样式共享。这包括 + 选择器和 :first-child 和 :last-child 等选择器。

### Firefox 规则树

Firefox 还提供了两个额外的树来简化样式计算：规则树和样式上下文树。WebKit 也具有样式对象，但它们未存储在样式上下文树等树中，只有 DOM 节点指向其相关样式。

![Firefox 样式的上下文树。](https://web.dev/static/articles/howbrowserswork/image/firefox-style-context-tre-f578b75b74df7.png?hl=zh-cn)

图 14：Firefox 样式上下文树。

样式上下文包含结束值。系统会按正确的顺序应用所有匹配规则，并执行一些操作将逻辑值转换为具体值，以便计算这些值。例如，如果逻辑值是屏幕的百分比，系统会对其进行计算并转换为绝对单位。规则树的想法非常巧妙。它支持在节点之间共享这些值，以避免再次计算这些值。这还可以节省空间。

所有匹配的规则都存储在树中。路径中的底部节点的优先级更高。该树包含找到的规则匹配的所有路径。存储规则是延迟执行的。系统不会在开始时为每个节点计算树，但每当需要计算节点样式时，系统都会将计算出的路径添加到树中。

其思路是将树路径视为词典中的单词。假设我们已经计算出了此规则树：

![计算的规则树](https://web.dev/static/articles/howbrowserswork/image/computed-rule-tree-f874f412bbaf.png?hl=zh-cn)

图 15：计算出的规则树。

假设我们需要为内容树中的另一个元素匹配规则，并发现匹配的规则（按正确顺序）为 B-E-I。我们已经在树中计算出路径 A-B-E-I-L，因此此路径已存在。现在，我们要做的工作会更少。

我们来看看树形图如何帮助我们节省工作量。

### 拆分为结构体

样式上下文可分为结构体。这些结构体包含边框或颜色等特定类别的样式信息。结构体中的所有属性都是继承的或非继承的。继承的属性是指除非由元素定义，否则会从其父元素继承的属性。如果未定义非继承属性（称为“重置”属性），则使用默认值。

该树会在树中缓存整个结构体（包含计算的结束值），从而帮助我们。其理念是，如果底部节点没有提供结构体的定义，则可以使用较高节点中的缓存结构体。

### 使用规则树计算样式上下文

在计算特定元素的样式上下文时，我们首先计算规则树中的路径，或使用现有的路径。然后，我们开始应用路径中的规则，以填充新样式上下文中的结构。我们从路径中具有最高优先级的底部节点（通常是最具体的选择器）开始，并向上遍历规则树，直到结构体填满。如果该规则节点中没有结构体规范，我们可以进行大幅优化 - 我们会向上遍历树，直到找到完全指定该结构体并指向它的节点 - 这是最佳优化方式，整个结构体会共享。这可以节省结束值的计算和内存。

如果我们找到了部分定义，则会向上遍历树，直到填充结构体为止。

如果我们找不到结构体的任何定义，那么如果结构体是“继承”类型，我们会在**上下文树**中指向父级结构体。在本例中，我们还成功共享了结构体。 如果它是重置结构体，则系统会使用默认值。

如果最具体的节点添加了值，那么我们需要再进行一些计算，以便将值转换为实际值。 然后，我们会将结果缓存在树节点中，以供子项使用。

如果某个元素具有指向同一树节点的兄弟或姐妹元素，则它们之间可以共享**整个样式上下文**。

我们来看一个示例：假设我们有这样一个 HTML

```
&lt;html&gt;
  &lt;body&gt;
    &lt;div class="err" id="div1"&gt;
      &lt;p&gt;
        this is a &lt;span class="big"&gt; big error &lt;/span&gt;
        this is also a
        &lt;span class="big"&gt; very  big  error&lt;/span&gt; error
      &lt;/p&gt;
    &lt;/div&gt;
    &lt;div class="err" id="div2"&gt;another error&lt;/div&gt;
  &lt;/body&gt;
&lt;/html&gt;
```

以及以下规则：

```
<span>div</span><span> </span><span>{</span><span>margin</span><span>:</span><span> </span><span>5</span><span>px</span><span>;</span><span> </span><span>color</span><span>:</span><span>black</span><span>}</span>
<span>.</span><span>err</span><span> </span><span>{</span><span>color</span><span>:</span><span>red</span><span>}</span>
<span>.</span><span>big</span><span> </span><span>{</span><span>margin-top</span><span>:</span><span>3</span><span>px</span><span>}</span>
<span>div</span><span> </span><span>span</span><span> </span><span>{</span><span>margin-bottom</span><span>:</span><span>4</span><span>px</span><span>}</span>
<span>#</span><span>div1</span><span> </span><span>{</span><span>color</span><span>:</span><span>blue</span><span>}</span>
<span>#</span><span>div2</span><span> </span><span>{</span><span>color</span><span>:</span><span>green</span><span>}</span>
```

为简单起见，假设我们只需填写两个结构体：颜色结构体和边距结构体。color 结构体仅包含一个成员：颜色。margin 结构体包含四个边。

生成的规则树将如下所示（节点用节点名称标记：它们指向的规则的编号）：

![规则树](https://web.dev/static/articles/howbrowserswork/image/the-rule-tree-23f05b0dac33f.png?hl=zh-cn)

图 16：规则树

上下文树将如下所示（节点名称：指向的规则节点）：

![上下文树。](https://web.dev/static/articles/howbrowserswork/image/the-context-tree-771124b7cb80d.png?hl=zh-cn)

图 17：上下文树

假设我们解析 HTML 并找到第二个 `<div>` 标记。我们需要为此节点创建一个样式上下文并填充其样式结构体。

通过匹配规则，我们发现 `<div>` 的匹配规则是 1、2 和 6。 这意味着，树中已经有一个可供我们的元素使用的路径，我们只需为规则 6（规则树中的节点 F）再添加一个节点即可。

我们将创建一个样式上下文，并将其放入上下文树中。新样式上下文将指向规则树中的节点 F。

现在，我们需要填充样式结构体。我们先填充 margin 结构体。由于最后一个规则节点 (F) 不会添加到边距结构体中，因此我们可以向上遍历树，直到找到在之前的节点插入中计算的缓存结构体并使用它。我们将在节点 B 上找到它，该节点是指定边距规则的顶部节点。

我们确实有一个颜色结构体定义，因此无法使用缓存的结构体。 由于 color 有一个属性，我们无需向上填充颜色树来填充其他属性。我们将计算结束值（将字符串转换为 RGB 等），并在此节点上缓存计算的结构。

处理第二个 `<span>` 元素的工作更简单。我们将匹配规则，并得出结论，它指向规则 G，就像上一个 span 一样。 由于我们的同级元素指向同一节点，因此我们可以共享整个样式上下文，并且只需指向上一个 span 的上下文即可。

对于包含从父项继承的规则的结构体，缓存是在上下文树上完成的（颜色属性实际上是继承的，但 Firefox 将其视为重置项，并将其缓存在规则树上）。

例如，如果我们在段落中添加字体规则：

```
<span>p</span><span> </span><span>{</span><span>font-family</span><span>:</span><span> </span><span>Verdana</span><span>;</span><span> </span><span>font</span><span> </span><span>size</span><span>:</span><span> </span><span>10</span><span>px</span><span>;</span><span> </span><span>font-weight</span><span>:</span><span> </span><span>bold</span><span>}</span>
```

然后，段落元素（是上下文树中 div 的子元素）可以与其父元素共享相同的字体结构。如果未为段落指定任何字体规则，则会出现这种情况。

在 WebKit（不具有规则树）中，系统会四次遍历匹配的声明。首先应用不重要的高优先级属性（由于其他属性依赖于这些属性，因此应首先应用这些属性，例如展示广告），然后是高优先级重要规则、普通优先级不重要规则，最后是普通优先级重要规则。 这意味着，重复出现的属性将按正确的级联顺序解析。最后的胜出者。

总而言之：共享样式对象（全部或其中的某些结构体）可解决问题 1 和 3。Firefox 规则树还有助于按正确的顺序应用属性。

### 操控规则以实现轻松匹配

样式规则有多个来源：

1.  外部样式表或样式元素中的 CSS 规则。 `css p {color: blue}`
2.  内嵌样式属性，例如 `html <p style="color: blue" />`
3.  HTML 视觉属性（映射到相关样式规则） `html <p bgcolor="blue" />` 最后两个属性很容易与元素匹配，因为它拥有样式属性，并且可以使用元素作为键来映射 HTML 属性。

如问题 2 中所述，CSS 规则匹配可能更为棘手。为了解决此问题，我们会操控规则，以便用户更轻松地访问。

解析样式表后，系统会根据选择器将规则添加到多个哈希映射之一。 有按 ID、类名称、标记名称的映射，以及适用于不属于这些类别的任何内容的通用映射。如果选择器是 ID，则规则将添加到 ID 映射中；如果选择器是类，则规则将添加到类映射中，以此类推。

这种操作可让规则匹配变得更加容易。无需查看每个声明：我们可以从映射中提取元素的相关规则。此优化会消除 95% 以上的规则，因此在匹配过程中甚至无需考虑这些规则(4.1)。

例如，我们来看以下样式规则：

```
<span>p</span><span>.</span><span>error</span><span> </span><span>{</span><span>color</span><span>:</span><span> </span><span>red</span><span>}</span>
<span>#</span><span>messageDiv</span><span> </span><span>{</span><span>height</span><span>:</span><span> </span><span>50</span><span>px</span><span>}</span>
<span>div</span><span> </span><span>{</span><span>margin</span><span>:</span><span> </span><span>5</span><span>px</span><span>}</span>
```

第一条规则将插入类表。第二个键会添加到 ID 映射中，第三个键会添加到标记映射中。

对于以下 HTML 片段：

```
&lt;p class="error"&gt;an error occurred&lt;/p&gt;
&lt;div id=" messageDiv"&gt;this is a message&lt;/div&gt;
```

我们先尝试找出 p 元素的规则。类映射将包含一个“error”键，其下可以找到“p.error”的规则。div 元素在 ID 表（键为 ID）和标记表中有相关规则。 剩下的工作就是查明通过键提取的哪些规则真正匹配。

例如，如果 div 的规则为：

```
<span>table</span><span> </span><span>div</span><span> </span><span>{</span><span>margin</span><span>:</span><span> </span><span>5</span><span>px</span><span>}</span>
```

它仍会从标记映射中提取，因为键是最右侧的选择器，但它与没有表祖先的 div 元素不匹配。

WebKit 和 Firefox 都会执行此操作。

### 样式表级联顺序

样式对象具有与每个视觉属性（所有 CSS 属性，但更通用）对应的属性。如果属性未由任何匹配的规则定义，则某些属性可以由父元素样式对象继承。其他属性具有默认值。

当有多个定义时，问题就开始出现了，这时就需要使用级联顺序来解决问题。

样式属性的声明可以出现在多个样式表中，也可以在一个样式表中出现多次。这意味着应用规则的顺序非常重要。这称为“级联”顺序。根据 CSS2 规范，级联顺序如下（从低到高）：

1.  浏览器声明
2.  用户常规声明
3.  作者常规声明
4.  作者重要声明
5.  向用户显示的重要声明

浏览器声明是最不重要的，用户只有在该声明被标记为重要时才会替换网页作者的声明。 具有相同顺序的声明将按特异性排序，然后按指定顺序。[](https://web.dev/articles/howbrowserswork?hl=zh-cn#specificity)HTML 可视化属性会转换为匹配的 CSS 声明。它们被视为低优先级的作者规则。

### 明确性

选择器特异性由 [CSS2 规范](http://www.w3.org/TR/CSS2/cascade.html#specificity)定义，如下所示：

1.  如果其所属的声明是“style”属性（而非带有选择器的规则），则计为 1，否则计为 0（= a）
2.  计算选择器中 ID 属性的数量 (= b)
3.  统计选择器中的其他属性和伪类的数量 (= c)
4.  计算选择器中的元素名称和伪元素的数量 (= d)

将这四个数字按 a-b-c-d（在大基数的数字系统中）进行连接可指定特异性。

您需要使用的数字基数由某个类别中的最高计数决定。

例如，如果 a=14，您可以使用十六进制。在极少数情况下，如果 a=17，您需要使用 17 位数的数字基数。如果选择器如下所示，就可能会出现后一种情况： html body div div p…（选择器中有 17 个标记…不太可能）。

一些示例：

 ```
<span> </span><span>*</span><span>             </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=0 d=0 -&gt; specificity = 0,0,0,0 */</span>
<span> </span><span>li</span><span>            </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=0 d=1 -&gt; specificity = 0,0,0,1 */</span>
<span> </span><span>li</span><span>:</span><span>first-line</span><span> </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=0 d=2 -&gt; specificity = 0,0,0,2 */</span>
<span> </span><span>ul</span><span> </span><span>li</span><span>         </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=0 d=2 -&gt; specificity = 0,0,0,2 */</span>
<span> </span><span>ul</span><span> </span><span>ol</span><span>+</span><span>li</span><span>      </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=0 d=3 -&gt; specificity = 0,0,0,3 */</span>
<span> </span><span>h1</span><span> </span><span>+</span><span> </span><span>*[</span><span>rel</span><span>=</span><span>up</span><span>]</span><span>{}</span><span>  </span><span>/* a=0 b=0 c=1 d=1 -&gt; specificity = 0,0,1,1 */</span>
<span> </span><span>ul</span><span> </span><span>ol</span><span> </span><span>li</span><span>.</span><span>red</span><span>  </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=1 d=3 -&gt; specificity = 0,0,1,3 */</span>
<span> </span><span>li</span><span>.</span><span>red</span><span>.</span><span>level</span><span>  </span><span>{}</span><span>  </span><span>/* a=0 b=0 c=2 d=1 -&gt; specificity = 0,0,2,1 */</span>
<span> </span><span>#</span><span>x34y</span><span>         </span><span>{}</span><span>  </span><span>/* a=0 b=1 c=0 d=0 -&gt; specificity = 0,1,0,0 */</span>
<span> </span><span>style</span><span>=</span><span>""</span><span>          </span><span>/* a=1 b=0 c=0 d=0 -&gt; specificity = 1,0,0,0 */</span>
 ```

### 对规则进行排序

规则匹配后，系统会根据级联规则对其进行排序。WebKit 会对小列表使用冒泡排序，对大列表使用归并排序。WebKit 通过替换规则的 `>` 运算符来实现排序：

```
<span>static</span><span> </span><span>bool</span><span> </span><span>operator</span><span> </span>&gt;<span>(</span><span>CSSRuleData</span>&amp;<span> </span><span>r1</span><span>,</span><span> </span><span>CSSRuleData</span>&amp;<span> </span><span>r2</span><span>)</span>
<span>{</span>
<span>    </span><span>int</span><span> </span><span>spec1</span><span> </span><span>=</span><span> </span><span>r1.selector()-&gt;specificity()</span><span>;</span>
<span>    </span><span>int</span><span> </span><span>spec2</span><span> </span><span>=</span><span> </span><span>r2.selector()-&gt;specificity()</span><span>;</span>
<span>    </span><span>return</span><span> </span><span>(spec1</span><span> </span><span>==</span><span> </span><span>spec2)</span><span> </span><span>:</span><span> </span><span>r1.position()</span><span> &gt; </span><span>r2.position()</span><span> </span><span>:</span><span> </span><span>spec1</span><span> &gt; </span><span>spec2</span><span>;</span>
<span>}</span>
```

### 是一个渐进的过程

WebKit 使用一个标记来标记是否已加载所有顶级样式表（包括 @imports）。如果在附加时未完全加载样式，则使用占位符并在文档中进行标记，然后在样式表加载完毕后重新计算样式。

## 布局

创建渲染程序并将其添加到树中后，它没有位置和尺寸。计算这些值的过程称为布局或重新流布局。

HTML 使用基于流的布局模型，这意味着在大多数情况下，可以一次性计算几何图形。较靠后的“流中”元素通常不会影响较早“流中”元素的几何形状，因此布局可以按从左到右、从上到下的顺序贯穿文档。不过，也有一些例外情况：例如，HTML 表格可能需要进行多次传递。

坐标系相对于根框架。使用向上和向左坐标。

布局是一个递归过程。它从根渲染程序开始，该渲染程序对应于 HTML 文档的 `<html>` 元素。布局会继续递归地遍历部分或全部帧层次结构，为需要的每个渲染程序计算几何信息。

根渲染程序的位置为 0,0，其尺寸为视口（浏览器窗口的可见部分）。

所有渲染程序都具有“布局”或“重新布局”方法，每个渲染程序都会调用其需要布局的子项的布局方法。

### 脏位系统

为了避免对每项细微更改都进行完整布局，浏览器使用“脏位”系统。更改或添加的渲染程序会将自身及其子项标记为“脏”：需要布局。

有两个标记：“dirty”和“children are dirty”，表示尽管渲染程序本身没有问题，但它至少有一个子项需要布局。

### 全局布局和增量布局

布局可在整个渲染树上触发，这称为“全局”布局。造成这种情况的原因可能是：

1.  会影响所有渲染程序的全局样式更改，例如字体大小更改。
2.  由于屏幕大小调整

布局可以是增量布局，只有脏渲染程序才会进行布局（这可能会导致一些损坏，需要额外的布局）。

当渲染程序为 dirty 时，会（异步）触发增量布局。例如，当额外内容从网络传入并添加到 DOM 树后，将新的渲染程序附加到渲染树中时。

![增量布局。](https://web.dev/static/articles/howbrowserswork/image/incremental-layout-da3da0a148135.png?hl=zh-cn)

图 18：增量布局 - 仅排列脏渲染程序及其子项

### 异步布局和同步布局

增量布局是异步执行的。Firefox 会为增量布局队列“重新流式传输命令”，并且调度程序会触发这些命令的批量执行。WebKit 还有一个用于执行增量布局的计时器，它会遍历树并布局“脏”渲染程序。

请求样式信息（例如“offsetHeight”）的脚本可以同步触发增量布局。

全局布局通常会同步触发。

有时，由于某些属性（例如滚动位置）发生变化，布局会在初始布局后作为回调触发。

### 优化

当布局由“调整大小”或渲染程序位置（而非大小）的更改触发时，渲染大小会从缓存中获取，而不会重新计算…

在某些情况下，系统只会修改子树，并且布局不会从根开始。如果更改是局部的，并且不会影响其周围环境（例如插入文本字段的文本），就可能会发生这种情况（否则，每次按键都会触发从根开始的布局）。

### 布局流程

布局通常采用以下模式：

1.  父级渲染程序确定自己的宽度。
2.  父级查看子级，并执行以下操作：
    1.  放置子渲染程序（设置其 x 和 y）。
    2.  在需要时调用子布局（如果它们已脏或我们处于全局布局中，或出于其他原因），以计算子项的高度。
3.  父级使用子级的累计高度以及边距和内边距的高度来设置自己的高度，父级渲染程序的父级将使用此高度。
4.  将其脏位设置为 false。

Firefox 使用“状态”对象 (nsHTMLReflowState) 作为布局（称为“重新流式传输”）的参数。此外，状态包括父级宽度。

Firefox 布局的输出为“metrics”对象(nsHTMLReflowMetrics)，其中包含渲染程序计算的高度。

### 宽度计算

渲染程序的宽度是使用容器块的宽度、渲染程序的样式“width”属性、边距和边框计算得出的。

例如，以下 div 的宽度：

```
&lt;div style="width: 30%"/&gt;
```

将由 WebKit 按如下方式计算（类 RenderBox 方法 calcWidth）：

-   容器的宽度是容器 availableWidth 和 0 中的较大值。 在本例中， availableWidth 是 contentWidth，计算公式如下：

```
<span>clientWidth</span><span>()</span><span> </span><span>-</span><span> </span><span>paddingLeft</span><span>()</span><span> </span><span>-</span><span> </span><span>paddingRight</span><span>()</span>
```

clientWidth 和 clientHeight 表示对象的内部（不包括边框和滚动条）。

-   元素的宽度是“width”样式属性。系统会通过计算容器宽度的百分比来计算绝对值。
    
-   现在添加了水平边框和内边距。
    

到目前为止，这是“首选宽度”的计算方式。 现在，将计算最小和最大宽度。

如果首选宽度大于最大宽度，则使用最大宽度。如果小于最小宽度（最小的不可破开单元），则使用最小宽度。

系统会缓存这些值以备需要布局时使用，但宽度不会变化。

### 换行

当布局中间的渲染程序决定需要中断时，该渲染程序会停止并向布局的父级传播，表明需要中断。父级会创建额外的渲染程序并对其调用布局。

## 绘画

在绘制阶段，系统会遍历渲染树，并调用渲染程序的“paint()”方法以在屏幕上显示内容。绘制使用界面基础架构组件。

### 全局和增量

与布局一样，绘制也可以是全局的（绘制整个树）或增量的。在增量绘制中，某些渲染程序的更改不会影响整个树。更改后的渲染程序使其在屏幕上的矩形失效。这会使操作系统将其视为“dirty 区域”，并生成“paint”事件。操作系统会巧妙地将多个区域合并为一个区域。在 Chrome 中，情况会更复杂，因为渲染器位于与主进程不同的进程中。Chrome 在一定程度上会模拟操作系统行为。 展示层会监听这些事件，并将消息委托给呈现根节点。系统会遍历该树，直到找到相关的渲染程序。它会重新绘制自身（通常还会绘制其子项）。

### 绘制顺序

[CSS2 定义了绘制流程的顺序](http://www.w3.org/TR/CSS21/zindex.html)。这实际上是元素在堆叠上下文中的堆叠顺序。[](https://web.dev/articles/howbrowserswork?hl=zh-cn#the_painting_order)由于堆叠是从后到前绘制，因此此顺序会影响绘制。块渲染程序的堆叠顺序如下：

1.  背景颜色
2.  背景图片
3.  边框
4.  孩子
5.  Outline

### Firefox 显示列表

Firefox 遍历呈现树，为绘制的矩形构建显示列表。 它包含与矩形相关的渲染程序，并按正确的绘制顺序（渲染程序的背景、边框等）排列。

这样一来，只需对树进行一次遍历即可进行一次重绘，而无需多次遍历（绘制所有背景、所有图片、所有边框等）。

Firefox 对此过程进行了优化，不会添加隐藏的元素，例如完全位于其他不透明元素之下的元素。

#### WebKit 矩形存储

在重新绘制之前，WebKit 会将旧矩形保存为位图。然后只绘制新旧矩形之间的增量。

### 动态更改

浏览器会尽量减少对更改做出的响应操作。因此，更改元素的颜色只会导致重新绘制该元素。更改元素位置会导致系统重新布局和重新绘制该元素及其子元素和可能的兄弟元素。添加 DOM 节点会导致该节点的布局和重绘。重大更改（例如增大“html”元素的字体大小）会导致缓存失效、对整个树进行重新布局和重新绘制。

### 呈现引擎的线程

呈现引擎采用单线程模式。几乎所有操作（除了网络操作）都是在单线程中进行的。在 Firefox 和 Safari 中，这是浏览器的主线程。在 Chrome 中，它是标签页进程的主线程。

网络操作可以由多个并行线程执行。并行连接数有限（通常为 2-6 个连接）。

### 事件循环

浏览器的主线程是事件循环。这是一个无限循环，可让进程保持活跃状态。它会等待事件（例如布局和绘制事件），并对其进行处理。以下是 Firefox 的主事件循环代码：

```
<span>while</span><span> </span><span>(</span><span>!</span><span>mExiting</span><span>)</span>
<span>    </span><span>NS_ProcessNextEvent</span><span>(</span><span>thread</span><span>);</span>
```

## CSS2 视觉模型

### 画布

根据 [CSS2 规范](http://www.w3.org/TR/CSS21/intro.html#processing-model)，“画布”一词指的是“呈现格式结构的空间”，即浏览器绘制内容的区域。

对于空间的每个尺寸，画布都是无限的，但浏览器会根据视口的尺寸选择初始宽度。

根据 [www.w3.org/TR/CSS2/zindex.html](http://www.w3.org/TR/CSS2/zindex.html)，如果画布包含在另一个画布中，则为透明；如果不包含，则为浏览器定义的颜色。

### CSS 框模型

[CSS 盒模型](http://www.w3.org/TR/CSS2/box.html)描述了为文档树中的元素生成的矩形框，并根据视觉格式设置模型进行布局。

每个框都有一个内容区域（例如文本、图片等）以及可选的周围内边距、边框和外边距区域。

![CSS2 盒模型](https://web.dev/static/articles/howbrowserswork/image/css2-box-model-9c2ab852d1fb4.jpg?hl=zh-cn)

图 19：CSS2 框模型

每个节点都会生成 0…n 个这样的框。

所有元素都有一个“display”属性，用于确定生成的框类型。

示例：

```
<span>block</span><span>:</span><span> </span><span>generates</span><span> </span><span>a</span><span> </span><span>block</span><span> </span><span>box</span><span>.</span>
<span>inline</span><span>:</span><span> </span><span>generates</span><span> </span><span>one</span><span> </span><span>or</span><span> </span><span>more</span><span> </span><span>inline</span><span> </span><span>boxes</span><span>.</span>
<span>none</span><span>:</span><span> </span><span>no</span><span> </span><span>box</span><span> </span><span>is</span><span> </span><span>generated</span><span>.</span>
```

默认是内嵌，但浏览器样式表可能设置其他默认值。例如，“div”元素的默认显示方式为“块”。

您可以在以下网址找到默认样式表示例：[www.w3.org/TR/CSS2/sample.html](http://www.w3.org/TR/CSS2/sample.html)。

### 定位方案

有三种方案：

1.  正常：对象会根据其在文档中的位置进行定位。这意味着，其在渲染树中的位置与其在 DOM 树中的位置一样，并会根据其盒子类型和尺寸进行布局
2.  浮动：对象首先像普通流一样进行布局，然后尽可能向左或向右移动
3.  绝对：将对象放置在渲染树中的位置与 DOM 树中的位置不同

定位方案由“position”属性和“float”属性设置。

-   static 和 relative 会导致正常流程
-   absolute 和 fixed 会导致绝对定位

在静态定位中，系统不会定义任何位置，而是使用默认定位。在其他方案中，作者指定位置：顶部、底部、左侧、右侧。

框的布局方式取决于：

-   盒子类型
-   框尺寸
-   定位方案
-   外部信息，例如图片大小和屏幕大小

### Box 类型

版块框：用于构成版块，在浏览器窗口中具有自己的矩形。

![屏蔽框。](https://web.dev/static/articles/howbrowserswork/image/block-box-b5c0bff4a44d2.png?hl=zh-cn)

图 20：区块框

内嵌盒：没有自己的块，但位于包含块内。

![内嵌框。](https://web.dev/static/articles/howbrowserswork/image/inline-boxes-a9ff03002e7e2.png?hl=zh-cn)

图 21：内嵌框

block 采用的是一个接一个的垂直格式，内嵌内容采用水平格式。

![Block 和 Inline 格式。](https://web.dev/static/articles/howbrowserswork/image/block-inline-formatting-a8450c63cf457.png?hl=zh-cn)

图 22：块级格式和内嵌格式

内嵌框放置在线条或“线条框”内。 这些线至少和最高的框一样高，但可以更高，当框按“基线”对齐时，这意味着元素的底部按照除底部以外的另一个框的位置对齐。 如果容器宽度不足，内嵌内容将被放置在多行中。这通常是段落中发生的情况。

![行。](https://web.dev/static/articles/howbrowserswork/image/lines-68c55a378a7ff.png?hl=zh-cn)

图 23：行

### 定位

#### 相对

相对定位 - 按常规方式定位，然后按所需的增量移动。

![相对定位。](https://web.dev/static/articles/howbrowserswork/image/relative-positioning-fceed0670b8c5.png?hl=zh-cn)

图 24：相对定位

#### 浮点数

浮动框会移动到行的左侧或右侧。有趣的是，其他框会围绕它流动。HTML：

```
&lt;p&gt;
  &lt;img style="float: right" src="images/image.gif" width="100" height="100"&gt;
  Lorem ipsum dolor sit amet, consectetuer...
&lt;/p&gt;
```

格式如下：

![浮点型。](https://web.dev/static/articles/howbrowserswork/image/float-dd9b790210284.png?hl=zh-cn)

图 25：浮点数

#### 绝对值和固定值

无论正常流程如何，布局都会精确定义。该元素不参与正常流程。这些尺寸是相对于容器而言的。在固定格式中，容器即为视口。

![固定定位。](https://web.dev/static/articles/howbrowserswork/image/fixed-positioning-881933fcb1a2f.png?hl=zh-cn)

图 26：固定定位

### 分层展示

这是由 z-index CSS 属性指定的。它表示盒子的第三个维度：沿“z 轴”的位置。

这些框分为多个堆栈（称为堆栈上下文）。在每个堆栈中，系统会先绘制后面的元素，然后在顶部绘制前面的元素，以便更靠近用户。如果重叠，最前面的元素将隐藏前面的元素。

堆叠项会按 z-index 属性排序。具有“z-index”属性的框会形成一个本地堆栈。视口包含外部堆栈。

示例：

```
&lt;style type="text/css"&gt;
  div {
    position: absolute;
    left: 2in;
    top: 2in;
  }
&lt;/style&gt;

&lt;p&gt;
  &lt;div
    style="z-index: 3;background-color:red; width: 1in; height: 1in; "&gt;
  &lt;/div&gt;
  &lt;div
    style="z-index: 1;background-color:green;width: 2in; height: 2in;"&gt;
  &lt;/div&gt;
&lt;/p&gt;
```

结果如下：

![图 27：固定定位](https://web.dev/static/articles/howbrowserswork/image/fixed-positioning-84a46b366dc9a.png?hl=zh-cn)

虽然红色 div 在标记中位于绿色 div 之前，并且在常规流程中会先于后者绘制，但其 z-index 属性更高，因此在根盒子所持的堆栈中位于更靠前的位置。

## 资源

1.  浏览器架构
    
    1.  Grosskurth, Alan. [网络浏览器参考架构 (pdf)](http://grosskurth.ca/papers/browser-refarch.pdf)
    2.  Gupta、Vineet。[浏览器的工作原理 - 第 1 部分 - 架构](http://www.vineetgupta.com/2010/11/how-browsers-work-part-1-architecture/)
2.  解析
    
    1.  Aho, Sethi, Ullman, Compilers: Principles, Techniques, and Tools（也称为“龙书”），Addison-Wesley，1986 年
    2.  Rick Jelliffe。[大胆而美丽：两种新的 HTML 5 草稿。](http://broadcast.oreilly.com/2009/05/the-bold-and-the-beautiful-two.html)
3.  Firefox
    
    1.  L. David Baron，[Faster HTML and CSS: Layout Engine Internals for Web Developers](http://dbaron.org/talks/2008-11-12-faster-html-and-css/slide-6.xhtml)（更快的 HTML 和 CSS：面向 Web 开发者的布局引擎内部）。
    2.  L. David Baron，[Fast HTML and CSS: Layout Engine Internals for Web Developers（Google 技术讲座视频）](https://www.youtube.com/watch?v=a2_6bGNZ7bA&hl=zh-cn)
    3.  L. David Baron，[Mozilla 的布局引擎](http://www.mozilla.org/newlayout/doc/layout-2006-07-12/slide-6.xhtml)
    4.  L. David Baron，[Mozilla 样式系统文档](http://www.mozilla.org/newlayout/doc/style-system.html)
    5.  Chris Waterson，[Notes on HTML Reflow](http://www.mozilla.org/newlayout/doc/reflow.html)
    6.  Chris Waterson，[Gecko 概览](http://www.mozilla.org/newlayout/doc/gecko-overview.htm)
    7.  Alexander Larsson，[HTML HTTP 请求的生命周期](https://developer.mozilla.org/en/The_life_of_an_HTML_HTTP_request)
4.  WebKit
    
    1.  David Hyatt，[实现 CSS（第 1 部分）](http://weblogs.mozillazine.org/hyatt/archives/cat_safari.html)
    2.  David Hyatt，[WebCore 概览](http://weblogs.mozillazine.org/hyatt/WebCore/chapter2.html)
    3.  David Hyatt，[WebCore 渲染](http://webkit.org/blog/114/)
    4.  David Hyatt，[FOUC 问题](http://webkit.org/blog/66/the-fouc-problem/)
5.  W3C 规范
    
    1.  [HTML 4.01 规范](http://www.w3.org/TR/html4/)
    2.  [W3C HTML5 规范](http://dev.w3.org/html5/spec/Overview.html)
    3.  [层叠样式表级别 2 修订版 1 (CSS 2.1) 规范](http://www.w3.org/TR/CSS2/)
6.  浏览器构建说明
    
    1.  Firefox。[https://developer.mozilla.org/Build\_Documentation](https://developer.mozilla.org/Build_Documentation)
    2.  WebKit。[http://webkit.org/building/build.html](http://webkit.org/building/build.html)
