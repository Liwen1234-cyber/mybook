# 在 conda 中管理 Python 版本的完整指南

Python是一种流行的编程语言，它有许多不同的版本，每个版本都有自己的特性和优势。如果您使用conda来管理您的Python环境和包，您可能想知道如何在conda中更改Python版本，以便使用不同的功能或兼容不同的库。

在本文中，我们将介绍如何在conda中查看、安装、切换和更新Python版本的方法，以及如何创建和删除不同Python版本的环境。我们还将回答一些常见的问题，帮助您更好地理解conda和Python之间的关系。

## 查看可用的Python版本

要查看conda可以安装的Python版本的列表，您可以在终端窗口或Anaconda提示符中运行以下命令：

```
conda search --full-name python
```

这将列出所有名称完全为python的包，以及它们的版本和构建信息。例如：

```
# Name                       Version           Build  Channel       
python                         2.7.10               0  pkgs/free     
python                         2.7.10               1  pkgs/free     
python                         2.7.10               2  pkgs/free     
python                         2.7.11               0  pkgs/free     
python                         2.7.12               0  pkgs/free     
python                         2.7.12               1  pkgs/free     
python                         2.7.13               0  pkgs/free     
python                         2.7.14      h4a10d90_30  pkgs/main     
python                         2.7.14      h4a10d90_31  pkgs/main     
python                         2.7.14      h4a10d90_32  pkgs/main     
python                         2.7.15      h2880e7c_29  pkgs/main     
python                         2.7.15      h2880e7c_30  pkgs/main     
python                         2.7.15      h2880e7c_31  pkgs/main     
python                         2.7.15      h2880e7c_32  pkgs/main     
python                         2.7.15      h2880e7c_33  pkgs/main     
python                         2.7.15      h2880e7c_34  pkgs/main     
python                         2.7.15      h2880e7c_35  pkgs/main     
python                         2.7.15      h2880e7c_36  pkgs/main     
python                         2.7.15      h2880e7c_37  pkgs/main     
python                         2.7.15      h2880e7c_38  pkgs/main     
...
```

您可以看到，conda支持从Python **2** 到 Python **3** 的多个版本，每个版本都有不同的构建选项。您可以根据您的需要选择合适的版本和构建来安装。

## 安装不同版本的Python

要安装不同版本的Python，而不覆盖当前版本，您需要创建一个新的环境，并在其中安装第二个Python版本。这样做的好处是，您可以在不同的环境中使用不同的Python版本，而不会影响其他环境或系统级别的Python。

要创建一个新的环境，并安装一个特定版本的Python，您可以在终端窗口或Anaconda提示符中运行以下命令：

```
conda create -n ENV_NAME python=VERSION
```

其中`ENV_NAME`是您要创建的环境的名称，`VERSION`是您要安装的Python版本。例如，要创建一个名为`py39`的环境，并安装Python **3** .9，您可以运行：

```
conda create -n py39 python=3.9
```

这将在`/envs/`目录下创建一个名为`py39`的环境，并在其中安装Python **3** .9和一些基本的包。如果您想在创建环境时安装更多的包，您可以在命令中添加它们，例如：

```
conda create -n py39 python=3.9 numpy pandas matplotlib
```

这将在`py39`环境中安装Python **3** .9和numpy、pandas、matplotlib等包。

## 切换不同版本的Python

要切换到有不同版本Python的环境，您需要激活该环境。激活环境意味着将其添加到系统路径中，以便您可以使用该环境中安装的程序和包。

要激活一个环境，您可以在终端窗口或Anaconda提示符中运行以下命令：

```
conda activate ENV_NAME
```

其中`ENV_NAME`是您要激活的环境的名称。例如，要激活`py39`环境，您可以运行：

```
conda activate py39
```

这将在终端窗口或Anaconda提示符前面显示当前激活的环境名称，例如：

```
(py39) $
```

这意味着您现在可以使用该环境中安装的Python和其他程序和包了。要验证当前环境使用的Python版本，您可以运行：

```
python --version
```

这将显示当前使用的Python版本，例如：

```
(py39) $ python --version
Python **3** .9 .8
```

要退出当前激活的环境，并返回到系统级别或基础级别（base）的环境，您可以运行：

```
conda deactivate
```

这将取消当前激活的环境，并从系统路径中移除它。如果没有其他激活的环境，则会返回到基础级别（base）或默认级别（default）。

## 更新或升级Python

使用终端窗口或Anaconda提示符进行以下步骤。

如果您在一个有Python **3** .4 .2 版本的环境中，则以下命令将更新Python到 **3** .4 分支中最新版：

```
conda update python
```

以下命令通过安装该版本来升级Python到另一个分支—— **3** .8 。这并不推荐做法，而是最好创建一个新环境。解析器必须非常努力地确定究竟应该升级哪些包。但是这是可能做到，并且命令是：

```
conda install python=3.8
```

## 创建和删除不同Python版本的环境

如果您想尝试不同版本的Python而不影响现有环境或系统级别（system-level）或基础级别（base-level） 的 Python ，则最好创建一个新环境并在其中安装所需版本。

我们已经介绍了如何使用 `conda create -n ENV_NAME python=VERSION` 命令来创建并安装特定 Python 版本到新建立之环境。

如果您想删除一个已经存在且已经激活过之 Python 版本之 conda 环境，请先退出该 conda 环境并执行以下命令：

```
conda remove --name ENV_NAME --all
```

其中 `ENV_NAME` 是你想删除之 conda 环境名称。请注意，在删除 conda 环境之

前，您需要先用 `conda deactivate` 命令来退出环境。 `--all` 参数表示删除环境中安装的所有包。

例如，要删除名为 `py39` 的环境，您可以运行：

```
conda deactivate
conda remove --name py39 --all
```

这将从 `/envs/` 目录下删除 `py39` 环境，并释放磁盘空间。

## 常见问题解答

在本节中，我们将回答一些关于在 conda 中更改 Python 版本的常见问题。

### 为什么要在 conda 中使用不同版本的 Python？

有许多原因可能导致您想要在 conda 中使用不同版本的 Python 。例如：

- 您可能想要尝试 Python 的最新特性或修复，而不影响您的现有项目或环境。
- 您可能需要使用某些只与特定版本的 Python 兼容的库或框架。
- 您可能需要为不同的客户或平台开发或测试不同版本的 Python 的应用程序或脚本。
- 您可能想要学习或教授不同版本的 Python 的语法或功能。

使用 conda ，您可以轻松地创建和管理不同版本的 Python 的环境，并在它们之间切换，而不会影响其他环境或系统级别（system-level）或基础级别（base-level） 的 Python 。

### 在 conda 中更改 Python 版本会影响其他环境吗？

不会。在 conda 中更改 Python 版本只会影响您当前激活的环境。其他环境或系统级别（system-level）或基础级别（base-level） 的 Python 不会受到任何影响。

这是因为 conda 使用隔离的环境来存储和运行不同版本的 Python 和其他程序和包。每个环境都有自己的目录和路径，与其他环境相互独立。当您激活一个环境时，您只能访问该环境中安装的程序和包。当您退出一个环境时，您将返回到系统级别（system-level）或基础级别（base-level） 的环境。

### 在 conda 中更改 Python 版本会影响已安装的包吗？

可能会。在 conda 中更改 Python 版本可能会导致一些已安装的包不再可用或不再兼容。这是因为一些包可能依赖于特定版本的 Python 或其他包，而更改 Python 版本可能会破坏这些依赖关系。

为了避免这种情况，建议您在更改 Python 版本之前，先创建一个新的环境，并在其中安装所需的包。这样，您可以保留原来的环境和包，而不会影响它们的功能。

如果您已经在一个现有的环境中更改了 Python 版本，并且发现一些包不再可用或不再兼容，您可以尝试以下方法来解决问题：

- 使用 `conda update --all` 命令来更新所有已安装的包，以匹配新的 Python 版本。
- 使用 `conda install PACKAGE=VERSION` 命令来安装特定版本的包，以匹配新的 Python 版本。
- 使用 `conda remove PACKAGE` 命令来卸载不再需要或不再兼容的包。
- 使用 `conda list --revisions` 命令来查看您对环境所做的更改历史，并使用 `conda install --revision REVISION` 命令来恢复到之前的状态。

### 在 conda 中如何查看当前使用的是哪个版本的 Python？

要查看当前使用的是哪个版本的 Python ，您可以在终端窗口或Anaconda提示符中运行以下命令：

```
python --version
```

这将显示当前使用的Python版本，例如：

```
$ python --version
Python **3** .9 .8
```

如果您想查看当前激活的是哪个环境，您可以运行以下命令：

```
conda env list
```

这将列出所有可用的环境，并在当前激活的环境前面显示一个星号（*），例如：

```
# conda environments:
#
base                     /Users/userxyz/anaconda3
py27                     /Users/userxyz/anaconda3/envs/py27
py36                     /Users/userxyz/anaconda3/envs/py36
py39                  *  /Users/userxyz/anaconda3/envs/py39
```

### 在 conda 中如何升级到最新版本的 Python？

要在 conda 中升级到最新版本的 Python ，您可以使用以下命令：

```
conda update python
```

这将更新当前激活的环境中安装的Python到最新可用版本。例如，如果您当前使用Python **3** .9 .8 ，则该命令将更新Python到 **3** .9 .9 （如果可用）。

如果您想升级到另一个分支（branch）上最新可用版本，例如从 **3** .9 到 **3** .10 ，则可以使用以下命令：

```
conda install python=3.10
```

这将安装Python **3** .10 到当前激活的环境中，并尝试解决任何依赖性问题。请注意，这种方法并不推荐，因为它可能导致一些包不再可用或不再兼容。建议您创建一个新的环境，并在其中安装所需版本。

## 结论

在本文中，我们介绍了如何在 conda 中查看、安装、切换和更新Python版本的方法，以及如何创建和删除不同Python版本的环境。我们还回答了一些常见问题，帮助您更好地理解 conda 和Python之间的关系。
