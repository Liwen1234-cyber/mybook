你好，我是林晓斌，网名“丁奇”，欢迎加入我的专栏，和我一起开始MySQL学习之旅。我曾先后在百度和阿里任职，从事MySQL数据库方面的工作，一步步地从一个数据库小白成为MySQL内核开发人员。回想起来，从我第一次带着疑问翻MySQL的源码查到答案至今，已经有十个年头了。在这个过程中，走了不少弯路，但同时也收获了很多的知识和思考，希望能在这个专栏里分享给你。

记得刚开始接触MySQL，是我在百度贴吧做权限系统的时候。我们遇到了一个奇怪的问题，一个正常10毫秒就能完成的SQL查询请求偶尔要执行100多毫秒才结束。当时主管问我是什么原因，我其实也搞不清楚，就上网查答案，但怎么找都找不到，又脸皮薄不想说自己不知道，只好硬着头皮翻源码。后来遇到了越来越多的问题，也是类似的情景，所以我逐步养成了通过分析源码理解原理的习惯。

当时，我自己的感觉是，即使我只是一个开发工程师，只是MySQL的用户，在了解了一个个系统模块的原理后，再来使用它，感觉是完全不一样的。当在代码里写下一行数据库命令的时候，我就能想到它在数据库端将怎么执行，它的性能是怎么样的，怎样写能让我的应用程序访问数据库的性能最高。进一步，哪些数据处理让数据库系统来做性能会更好，哪些数据处理在缓存里做性能会更好，我心里也会更清楚。在建表和建索引的时候，我也会更有意识地为将来的查询优化做综合考虑，比如确定是否使用递增主键、主键的列怎样选择，等等。

但随后我又有了一个新的困惑，我觉得自己了解的MySQL知识点是零散的，没有形成网络。于是解决完一个问题后，很容易忘记。再碰到类似的问题，我又得再翻一次代码。

所幸在阿里工作的时候，我参与了阿里云关系型数据库服务内核的开发，并且负责开发开源分支AliSQL，让我对MySQL内核和源码有了更深层次的研究和理解。在服务内部客户和公有云客户的过程中，我有机会面对和解决足够多的问题，再通过手册进行系统的学习，算是比较坎坷地将MySQL的知识网络补了起来。

所以，在回顾这个过程的时候，我的第一个感受是，如果一开始就有一些从理论到实战的系统性指导，那该多好啊，也许我可以学习得更快些。

在极客时间团队跟我联系策划这个专栏的时候，我还是持怀疑态度的。为什么呢？现在不比当年了，犹记得十余年前，你使用MySQL的过程中碰到问题的话，基本上都只能到代码里去找答案，因为那时网上的资料太少了。

而近十年来，MySQL在中国广泛普及，技术分享文章可以说是浩如烟海。所以，现在要系统地介绍一遍MySQL的话，恐怕里面提及的大多数知识点，都可以在社区文章中找到。那么我们做这个专栏的意义在哪里，而它又凭什么可以收费呢？

直到收到极客时间团队的答复，我才开始对这个专栏“想做和可以做”的事情感觉清晰起来。数据库是一个综合系统，其背后是发展了几十年的数据库理论。同时，数据库系统也是一个应用系统，可能一个业务开发人员用了两三年MySQL，还未必清楚那些自己一直在用的“最佳实践”为什么是最佳的。

于是，我希望这个专栏能够帮助这样的一些开发者：他们正在使用MySQL，知道如何写出逻辑正确的SQL语句来实现业务目标，却不确定这个语句是不是最优的；他们听说了一些使用数据库的最佳实践，但是更想了解为什么这么做；他们使用的数据库偶尔会出问题，亟需了解如何更快速、更准确地定位问题，甚至自己解决问题……

在过去的七年里，我带过十几个应届毕业生，看着他们成长，要求他们原理先行，再实践验证。几年下来，他们的成长速度都很快，其中好几个毕业没两年就成为团队的骨干力量了。我也在社招的时候面试过很多有着不错的运维实践经验和能力的候选人，但都因为对数据库原理仅有一知半解的了解，而最终遗憾地没有通过面试。

因此，我希望这个专栏能够激发开发者对数据库原理的探索欲，从而更好地理解工作中遇到的问题，更能知道背后的为什么。所以**我会选那些平时使用数据库时高频出现的知识，如事务、索引、锁等内容构成专栏的主线**。这些主线上是一个个的知识点。每个点就是一个概念、一个机制或者一个原理说明。在每个说明之后，我会和你讨论一个实践相关的问题。

希望能以这样的方式，让你对MySQL的几条主线有一个整体的认识，并且了解基本概念。在之后的实践篇中，我会引用到这些主线的知识背景，并着力说明它们是怎样指导实践的。这样，**你可以从点到线，再到面，形成自己的MySQL知识网络。**

在这里，有一份目录，你也可以先了解下整个专栏的知识结构。

![](./images/b736f37014d28199c2457a67ed669bc2.jpg)

如前面说的，这几条主线上的每个知识点几乎都不是最新的，有些甚至十年前就这样，并没有改过。但我希望针对这些点的说明，可以让你在使用MySQL时心里更有底，知道怎么做选择，并且明白为什么。了解了原理，才能在实践中不断创新，提升个人的价值和工作输出。

从这里开始，跟我一起搞懂MySQL!
<div><strong>精选留言（15）</strong></div><ul>
<li><span>Alpha</span> 👍（231） 💬（16）<p>提个建议，音频中My-S-Q-L的读音是不是不符合习俗？</p>2018-11-12</li><br/><li><span>和🍀</span> 👍（261） 💬（10）<p>关于MySQL发音的官方答案：
The official way to pronounce “MySQL” is “My Ess Que Ell” (not “my sequel”), but we do not mind if you pronounce it as “my sequel” or in some other localized way.</p>2018-11-12</li><br/><li><span>小风风</span> 👍（33） 💬（3）<p>老师，我是个大专生，学校开了MySQL这门课，但是我学习的不是太好，希望再您后面可以学到更多，能作用的也很多。</p>2018-11-13</li><br/><li><span>汪炜</span> 👍（24） 💬（1）<p>当下大流量的情况下，数据库是瓶颈，想解决问题，可以用缓存，用缓存又存在数据一致性问题，又不太了解数据库真正能扛多大qps,所以对这种大流量的方案选择和策略，没有一个定量的总结，不知道学习了老师的课程，能否有一些答案。🙂</p>2018-11-13</li><br/><li><span>知行合一</span> 👍（20） 💬（1）<p>晓斌哥，你好！我是一名大四学生，接触mysql已经2年了，自己对mysql数据库这方面也很兴趣，想以后从事数据库工程师这方面的工作，请问您对于我这种情况，根据您十多年的经验，有什么好的建议或者推荐的书籍或者一些好的资料呢？请问您的微博号是什么呀？作为您的忠实迷弟，但是却无法关注您的微博，想随时看到您的一些最新想法和经验，指引我前进的方向，希望您能抽出您的一点宝贵的时间，解答一下我的疑问，给迷茫中的我一点希望，谢谢！</p>2018-11-13</li><br/><li><span>peyton</span> 👍（9） 💬（1）<p>什么时候开始上课
</p>2018-11-12</li><br/><li><span>郭_永順</span> 👍（7） 💬（1）<p>已购，目录中没有提到MySQL占CPU和内存太多的问题，不知道课程中会不会提到，很期待。🙏</p>2018-11-13</li><br/><li><span>闫钟峰</span> 👍（6） 💬（3）<p>打算做一个数据集成系统，大概功能就是把不同业务系统里的信息综合进来，只用于数据管理，不对外服务，推荐用mysql吗？</p>2018-11-14</li><br/><li><span>damaom</span> 👍（6） 💬（1）<p>我很挑剔，期待精彩内容</p>2018-11-12</li><br/><li><span>江湖小虾</span> 👍（5） 💬（3）<p>丁奇老师你好，我想知道MySql的外键是不是在现实使用中，基本不使用了。最近看的文章都说要在应用层实现外键逻辑关系就好。能否给个解答？</p>2019-01-01</li><br/><li><span>jeffery</span> 👍（3） 💬（1）<p>第一遍看完不过瘾、总是吸引着、再过过瘾、大神的文章总是回味无穷、如美食般、慢慢品味</p>2019-05-06</li><br/><li><span>如果可以改变</span> 👍（3） 💬（1）<p>本节总结:
学习方法:原理先行，再实践验证。
课程主线:数据库高频出现的知识，如事务、索引、锁等内容。
愿各位拿到理想中的offer，心想事成！</p>2019-03-15</li><br/><li><span>我是啊</span> 👍（2） 💬（1）<p>很棒的一个专栏！非常感谢老师抽丝剥茧地解析，让我对mysql的认知一步步深入。同时在看《高性能mysql》，未来的目标是希望能达到源码级！</p>2019-01-16</li><br/><li><span>小豹子</span> 👍（1） 💬（1）<p>这个专栏在刚开的时候我就买了，那个时候我也刚开始接触mysql，基础太差，没学下去。现在过了大半年再来看这个专栏，真是把我这半年来mysql的疑惑全部解开了。每篇文章都值得细细体会。</p>2019-10-16</li><br/><li><span>Long</span> 👍（1） 💬（1）<p>大家别犹豫了，买起来吧，绝对不亏。</p>2019-01-09</li><br/>
</ul>