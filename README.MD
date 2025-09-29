# CrackingHelper

CrackingHelper 是一个用于分析、调试和破解其他插件的多功能工具，支持 Bukkit、BungeeCord 和 Velocity 平台。它提供了强大的类加载器分析、网络监控、类反编译等功能，是插件开发者和服务器管理员的理想工具。

> [English](README_EN.MD) | 简体中文

## 功能特性

### 1. 类加载器分析
- 列出所有已加载的类加载器
- 查看特定类加载器加载的所有类
- 分析类的详细信息，包括字段、方法、注解等

### 2. 类操作
- 反编译指定类查看源代码
- 将类转储为 .class 文件
- 动态加载并执行 Java 代码
- 阻止特定方法被调用

### 3. 网络监控
- 监控插件的网络连接
- 捕获网络响应内容
- 识别发起网络请求的插件

### 4. 方法跟踪
- 跟踪方法调用
- 记录方法调用的详细信息，包括调用者和参数

### 5. 调试辅助
- 打印当前线程的堆栈跟踪
- 实时监控方法执行

## 命令列表

### 主命令
[/crackinghelper](file://D:\Projects\crackinghelper\build\scripts\crackinghelper) - 主命令，所有功能都通过其子命令访问

### 子命令

#### `trackclass`
跟踪指定方法的调用
```
/crackinghelper trackclass <方法名>
示例: /crackinghelper trackclass org.bukkit.Bukkit#broadcastMessage
```


#### `dump`
将类转储为 .class 文件
```
/crackinghelper dump <类加载器哈希码> <包名或*>
示例: /crackinghelper dump abc123 *
```


#### `listclasses`
列出指定类加载器加载的所有类
```
/crackinghelper listclasses <类加载器哈希码>
```


#### `loadclass`
动态加载并执行 Java 代码
```
/crackinghelper loadclass <父类加载器哈希码> <类名> <文件名> [参数...]
```


#### `analysisclass`
显示类的完整信息
```
/crackinghelper analysisclass <类加载器哈希码> <类名>
```


#### `printstacktrace`
打印当前堆栈跟踪
```
/crackinghelper printstacktrace
```


#### `listclassloaders`
列出所有已加载的类加载器
```
/crackinghelper listclassloaders
```


#### `decompile`
反编译指定类
```
/crackinghelper decompile <类加载器哈希码> <类名>
```


#### `blockmethod`
阻止指定方法被调用
```
/crackinghelper blockmethod <方法名>
示例: /crackinghelper blockmethod org.bukkit.Bukkit#broadcastMessage
```


## 必需的JVM参数

为了确保 CrackingHelper 的所有功能正常运行，需要在启动服务器时添加以下 JVM 参数：

```
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED
--add-opens=java.base/jdk.internal.reflect=ALL-UNNAMED
--add-opens=java.base/sun.net.www.protocol.http=ALL-UNNAMED
--add-opens=java.base/sun.net.www.protocol.https=ALL-UNNAMED
```


这些参数用于打开 Java 内部包的访问权限，使 CrackingHelper 能够进行类操作和网络监控。

## 配置文件

插件会自动生成配置文件，用于控制网络跟踪功能：

```toml
# 是否启用网络跟踪
enabled = true

# 是否检测网络响应
detect-response = true
```


## 使用场景

1. **插件开发者调试** - 分析其他插件的行为，调试自己的插件
2. **服务器安全审计** - 监控插件的网络活动，检测潜在的安全风险
3. **逆向工程** - 反编译和分析插件的工作原理
4. **教学研究** - 学习 Java 类加载机制和字节码操作

## 注意事项

1. 本工具功能强大，请仅在合法授权的环境中使用
2. 某些功能可能影响服务器性能，建议在测试环境中使用
3. 使用网络监控功能时请注意隐私保护
4. 动态加载代码功能存在安全风险，请谨慎使用

## 支持平台

- Bukkit/Spigot/Paper (1.8.8+)
- BungeeCord/Waterfall
- Velocity

CrackingHelper 是插件开发和服务器管理的强大工具，可以帮助您深入了解插件的内部工作原理并进行有效的调试和分析。