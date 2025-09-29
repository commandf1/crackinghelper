# CrackingHelper

CrackingHelper is a versatile tool for analyzing, debugging, and cracking other plugins, supporting Bukkit, BungeeCord, and Velocity platforms. It provides powerful class loader analysis, network monitoring, class decompilation, and more, making it an ideal tool for plugin developers and server administrators.

> English | [简体中文](README.MD)

## Features

### 1. Class Loader Analysis
- List all loaded class loaders
- View all classes loaded by a specific class loader
- Analyze detailed class information, including fields, methods, annotations, etc.

### 2. Class Operations
- Decompile specified classes to view source code
- Dump classes to .class files
- Dynamically load and execute Java code
- Block specific methods from being invoked

### 3. Network Monitoring
- Monitor plugin network connections
- Capture network response content
- Identify plugins initiating network requests

### 4. Method Tracking
- Track method invocations
- Record detailed method invocation information, including caller and parameters

### 5. Debugging Assistance
- Print current thread stack trace
- Real-time method execution monitoring

## Command List

### Main Command
[/crackinghelper](file://D:\Projects\crackinghelper\build\scripts\crackinghelper) - Main command, all functions are accessed through its subcommands

### Subcommands

#### `trackclass`
Track specified method calls
```
/crackinghelper trackclass <method name>
Example: /crackinghelper trackclass org.bukkit.Bukkit#broadcastMessage
```


#### `dump`
Dump classes to .class files
```
/crackinghelper dump <class loader hashcode> <package name or *>
Example: /crackinghelper dump abc123 *
```


#### `listclasses`
List all classes loaded by a specified class loader
```
/crackinghelper listclasses <class loader hashcode>
```


#### `loadclass`
Dynamically load and execute Java code
```
/crackinghelper loadclass <parent class loader hashcode> <class name> <file name> [args...]
```


#### `analysisclass`
Display complete class information
```
/crackinghelper analysisclass <class loader hashcode> <class name>
```


#### `printstacktrace`
Print current stack trace
```
/crackinghelper printstacktrace
```


#### `listclassloaders`
List all loaded class loaders
```
/crackinghelper listclassloaders
```


#### `decompile`
Decompile specified class
```
/crackinghelper decompile <class loader hashcode> <class name>
```


#### `blockmethod`
Prevent specified method from being invoked
```
/crackinghelper blockmethod <method name>
Example: /crackinghelper blockmethod org.bukkit.Bukkit#broadcastMessage
```


## Required JVM Parameters

To ensure all functions of CrackingHelper work properly, the following JVM parameters need to be added when starting the server:

```
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED
--add-opens=java.base/jdk.internal.reflect=ALL-UNNAMED
--add-opens=java.base/sun.net.www.protocol.http=ALL-UNNAMED
--add-opens=java.base/sun.net.www.protocol.https=ALL-UNNAMED
```


These parameters are used to open access to Java internal packages, enabling CrackingHelper to perform class operations and network monitoring.

## Configuration File

The plugin will automatically generate a configuration file to control network tracking functionality:

```toml
# Enable network tracking
enabled = true

# Detect network responses
detect-response = true
```


## Use Cases

1. **Plugin Developer Debugging** - Analyze other plugins' behavior, debug your own plugins
2. **Server Security Audit** - Monitor plugin network activities, detect potential security risks
3. **Reverse Engineering** - Decompile and analyze how plugins work
4. **Educational Research** - Learn about Java class loading mechanisms and bytecode manipulation

## Notes

1. This tool is powerful, please use it only in legally authorized environments
2. Some functions may affect server performance, recommended for use in test environments
3. Please pay attention to privacy protection when using network monitoring functions
4. The dynamic code loading function has security risks, please use it with caution

## Supported Platforms

- Bukkit/Spigot/Paper (1.8.8+)
- BungeeCord/Waterfall
- Velocity

CrackingHelper is a powerful tool for plugin development and server management that can help you gain deep insights into how plugins work internally and perform effective debugging and analysis.