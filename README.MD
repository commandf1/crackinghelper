
---

# CrackingHelper

[![License](https://img.shields.io/github/license/commandf1/crackinghelper)](https://mit-license.org/)

## 简介 (Introduction in Chinese)

CrackingHelper 是一个多功能的 Minecraft 服务端插件，旨在帮助开发者分析、调试和“破解”其他插件。它支持 Bukkit/Spigot/Paper、BungeeCord 和 Velocity 平台。

该工具提供了强大的运行时检查功能，包括类加载追踪、网络请求监控、字节码反编译以及动态类加载与执行等。这些功能对于深入理解插件行为、排查问题或进行安全研究非常有用。

**注意**: 此工具应仅用于学习、开发和授权的安全测试目的。请遵守当地法律法规，并尊重软件版权。

### 主要特性 (Key Features)

- **跨平台支持**: 兼容 Bukkit/Spigot/Paper, BungeeCord, 和 Velocity.
- **类操作**:
    - 列出已加载的类和类加载器 (`listclasses`, `listclassloaders`)
    - 转储 (dump) 类的字节码以便进一步分析
    - 反编译类文件查看源码 ([decompile](file://D:\Projects\crackinghelper\crackinghelper-common\src\main\java\space\commandf1\crackinghelper\common\util\ClassUtil.java#L26-L28))
    - 分析类结构，显示详细信息 (`analysisclass`)
    - 动态加载并执行指定类的 [main](file://D:\Projects\crackinghelper\crackinghelper-common\src\main\java\space\commandf1\crackinghelper\common\run\CrackingHelperMain.java#L6-L8) 方法 (`loadclass`)
- **运行时追踪**:
    - 追踪特定类的方法调用 (`trackclasses`)
    - 监控 HTTP/HTTPS 网络请求及响应 (`network-tracker`)
    - 打印当前线程堆栈跟踪 (`printstacktrace`)
- **命令行界面**: 提供直观的 [/crackinghelper](file://D:\Projects\crackinghelper\build\scripts\crackinghelper) 命令及其子命令进行交互。

---

## Introduction (English)

CrackingHelper is a versatile Minecraft server plugin designed to assist developers in analyzing, debugging, and "cracking" other plugins. It supports the Bukkit/Spigot/Paper, BungeeCord, and Velocity platforms.

This tool offers powerful runtime inspection capabilities, including class loading tracking, network request monitoring, bytecode decompilation, and dynamic class loading and execution. These features are very useful for understanding plugin behavior in depth, troubleshooting issues, or conducting security research.

**Note:** This tool should be used for learning, development, and authorized security testing purposes only. Please comply with local laws and regulations and respect software copyrights.

### Key Features

- **Cross-platform Support**: Compatible with Bukkit/Spigot/Paper, BungeeCord, and Velocity.
- **Class Operations**:
    - List loaded classes and class loaders (`listclasses`, `listclassloaders`)
    - Dump class bytecode for further analysis
    - Decompile class files to view source code ([decompile](file://D:\Projects\crackinghelper\crackinghelper-common\src\main\java\space\commandf1\crackinghelper\common\util\ClassUtil.java#L26-L28))
    - Analyze class structure and show detailed information (`analysisclass`)
    - Dynamically load and execute the [main](file://D:\Projects\crackinghelper\crackinghelper-common\src\main\java\space\commandf1\crackinghelper\common\run\CrackingHelperMain.java#L6-L8) method of a specified class (`loadclass`)
- **Runtime Tracking**:
    - Track method calls of specific classes (`trackclasses`)
    - Monitor HTTP/HTTPS network requests and responses (`network-tracker`)
    - Print the current thread stack trace (`printstacktrace`)
- **Command Line Interface**: Provides an intuitive [/crackinghelper](file://D:\Projects\crackinghelper\build\scripts\crackinghelper) command with sub-commands for interaction.

---

## 构建 (Build)

本项目使用 Gradle 构建。要构建项目，请在终端中执行以下命令：

```bash
./gradlew build
```


构建后的 JAR 文件将位于各子项目下的 `build/libs` 目录中。

---

## 使用说明 (Usage)

1. 将对应平台的插件 JAR 文件放入服务器的 `plugins` 目录。
2. 启动服务器。配置文件将在首次启动时自动生成。
3. 使用 [/crackinghelper](file://D:\Projects\crackinghelper\build\scripts\crackinghelper) 命令开始探索其功能。

例如：

```
/crackinghelper listclasses <classloader_hashcode>
/crackinghelper decompile <classloader_hashcode> <full_class_name>
/crackinghelper trackclasses <full_class_name>#<method_name>
```


---

## 许可证 (License)

本项目基于 [GNU General Public License v3.0](https://mit-license.org/) 许可证发行。

---

## 致谢 (Acknowledgements)

本项目依赖于众多优秀的开源库，包括但不限于:

- [ByteBuddy](https://bytebuddy.net/)
- [CFR](http://www.benf.org/other/cfr/)
- [Lombok](https://projectlombok.org/)

感谢这些项目为本工具提供的强大支持。

---