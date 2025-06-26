/** @page architecture 系统架构

# 图形渲染系统架构文档


## 系统概述
本系统提供跨平台的2D图形渲染能力，支持多种渲染后端(Swing/SVG/Legacy)，采用桥接模式将渲染接口与实现分离。

## 核心架构

### 1. 桥接模式 (Bridge Pattern)
- `Renderer`接口定义统一的渲染API
- 具体实现：
  - `SwingRenderer`: 基于Java Swing的渲染
  - `SVGRenderer`: 生成SVG矢量图形
  - `LegacyRendererAdapter`: 适配旧版渲染系统

### 2. 工厂模式 (Factory Pattern)
- `RendererFactory`: 统一创建各种渲染器实例
- `ShapeFactory`: 创建各种图形对象

### 3. 命令模式 (Command Pattern)
- `Command`接口及实现类提供撤销/重做功能
- `UndoManager`管理命令历史

### 4. 观察者模式 (Observer Pattern)
- `ShapeObserver`接口监控图形变化
- `LoggingShapeObserver`实现日志记录

### 5. 访问者模式 (Visitor Pattern)
- `ExportVisitor`接口支持不同格式导出
- `JSONExportVisitor`/`XMLExportVisitor`具体实现

## 核心组件

### 渲染相关
- `Renderer.java`: 定义渲染器核心接口
  - 支持帧控制(beginFrame/endFrame)
  - 样式管理(setStyle)
  - 多图形绘制API
- `SwingRenderer.java`: Swing实现
  - 基于Graphics2D
  - 自动抗锯齿
  - 需要先setGraphics()
- `SVGRenderer.java`: SVG实现
  - 控制台输出SVG标记
  - 轻量级无依赖
- `LegacyRendererAdapter.java`: 旧版适配器
  - 适配LegacyRenderer接口
  - 添加参数校验
  - 异常转换
- `RemoteRendererProxy.java`: 远程渲染代理
  - 添加重试机制
  - 调用日志记录
  - 透明代理模式

## 典型工作流程

1. 通过`RendererFactory`获取渲染器实例
2. 使用`ShapeFactory`创建图形对象
3. 执行`Command`操作图形
4. 渲染器将图形绘制到目标
5. 观察者记录变更
6. 访问者导出图形数据

*/
