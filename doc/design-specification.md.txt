┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 图形渲染系统架构设计文档                                                                                                         ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛


1. 系统概述                                                                                                                         

本系统是一个基于Java的图形渲染框架，采用模块化设计，支持多种渲染后端和图形类型。系统核心目标是提供灵活、可扩展的图形渲染能力，同时保
持代码的可维护性和高性能。                                                                                                          


2. 架构设计原则                                                                                                                     

 1 分层架构：清晰分离表现层、业务逻辑层和数据访问层                                                                                 
 2 松耦合：通过接口定义模块边界                                                                                                     
 3 可扩展性：支持新图形类型和渲染器的动态添加                                                                                       
 4 可测试性：各组件可独立测试                                                                                                       


3. 核心架构图                                                                                                                       

                                                                                                                                    
+---------------------+                                                                                                             
|       Client        |                                                                                                             
+---------------------+                                                                                                             
          |                                                                                                                         
          v                                                                                                                         
+---------------------+                                                                                                             
|       UI Layer      |                                                                                                             
| (SwingUI, Panels)   |                                                                                                             
+---------------------+                                                                                                             
          |                                                                                                                         
          v                                                                                                                         
+---------------------+                                                                                                             
|   Business Logic    |                                                                                                             
| (Commands, Factory) |                                                                                                             
+---------------------+                                                                                                             
          |                                                                                                                         
          v                                                                                                                         
+---------------------+                                                                                                             
|   Rendering Layer   |                                                                                                             
| (Renderers, Adapter)|                                                                                                             
+---------------------+                                                                                                             
          |                                                                                                                         
          v                                                                                                                         
+---------------------+                                                                                                             
|  Persistence Layer  |                                                                                                             
| (Serialization, IO) |                                                                                                             
+---------------------+                                                                                                             
                                                                                                                                    


4. 核心组件说明                                                                                                                     

4.1 UI层组件                                                                                                                        

                                                                                                                                    
/**                                                                                                                                 
 * 主界面框架，基于Swing实现                                                                                                        
 *                                                                                                                                  
 * <p>职责：                                                                                                                        
 * <ul>                                                                                                                             
 *   <li>提供用户交互界面</li>                                                                                                      
 *   <li>管理绘图面板</li>                                                                                                          
 *   <li>协调各组件交互</li>                                                                                                        
 * </ul>                                                                                                                            
 *                                                                                                                                  
 * @see DrawingPanel 绘图面板实现                                                                                                   
 */                                                                                                                                 
public class SwingUI extends JFrame {                                                                                               
    // 实现细节...                                                                                                                  
}                                                                                                                                   
                                                                                                                                    

4.2 业务逻辑层                                                                                                                      

4.2.1 命令模式实现                                                                                                                  

                                                                                                                                    
/**                                                                                                                                 
 * 命令模式基类，支持撤销/重做操作                                                                                                  
 *                                                                                                                                  
 * <p>设计特点：                                                                                                                    
 * <ul>                                                                                                                             
 *   <li>封装操作逻辑</li>                                                                                                          
 *   <li>支持事务性操作</li>                                                                                                        
 *   <li>与UndoManager配合实现命令历史</li>                                                                                         
 * </ul>                                                                                                                            
 */                                                                                                                                 
public interface Command {                                                                                                          
    void execute();                                                                                                                 
    void undo();                                                                                                                    
    void redo();                                                                                                                    
}                                                                                                                                   
                                                                                                                                    

4.2.2 工厂模式实现                                                                                                                  

                                                                                                                                    
/**                                                                                                                                 
 * 图形工厂接口，支持创建各种图形对象                                                                                               
 *                                                                                                                                  
 * <p>扩展机制：                                                                                                                    
 * <ol>                                                                                                                             
 *   <li>实现ShapeFactory接口</li>                                                                                                  
 *   <li>注册到FactoryRegistry</li>                                                                                                 
 *   <li>通过createXxx方法创建实例</li>                                                                                             
 * </ol>                                                                                                                            
 */                                                                                                                                 
public interface ShapeFactory {                                                                                                     
    Circle createCircle(int x, int y, int radius);                                                                                  
    // 其他工厂方法...                                                                                                              
}                                                                                                                                   
                                                                                                                                    

4.3 渲染层设计                                                                                                                      

4.3.1 渲染器接口                                                                                                                    

                                                                                                                                    
/**                                                                                                                                 
 * 渲染器核心接口，定义基本绘图操作                                                                                                 
 *                                                                                                                                  
 * <p>实现说明：                                                                                                                    
 * <ul>                                                                                                                             
 *   <li>各实现类负责具体渲染逻辑</li>                                                                                              
 *   <li>支持多种渲染后端(Swing/SVG/Test等)</li>                                                                                    
 *   <li>通过RendererFactory获取实例</li>                                                                                           
 * </ul>                                                                                                                            
 */                                                                                                                                 
public interface Renderer {                                                                                                         
    void drawCircle(int x, int y, int radius);                                                                                      
    // 其他渲染方法...                                                                                                              
}                                                                                                                                   
                                                                                                                                    

4.3.2 适配器模式                                                                                                                    

                                                                                                                                    
/**                                                                                                                                 
 * 旧版渲染器适配器，实现新旧系统兼容                                                                                               
 *                                                                                                                                  
 * <p>工作原理：                                                                                                                    
 * <ol>                                                                                                                             
 *   <li>接收LegacyRenderer实例</li>                                                                                                
 *   <li>实现Renderer接口</li>                                                                                                      
 *   <li>方法调用转发</li>                                                                                                          
 * </ol>                                                                                                                            
 */                                                                                                                                 
public class LegacyRendererAdapter implements Renderer {                                                                            
    // 实现细节...                                                                                                                  
}                                                                                                                                   
                                                                                                                                    

4.4 持久化层                                                                                                                        

                                                                                                                                    
/**                                                                                                                                 
 * 持久化管理器，负责图形序列化/反序列化                                                                                            
 *                                                                                                                                  
 * <p>特性：                                                                                                                        
 * <ul>                                                                                                                             
 *   <li>单例模式确保全局唯一</li>                                                                                                  
 *   <li>支持JSON格式</li>                                                                                                          
 *   <li>类型安全的多态序列化</li>                                                                                                  
 * </ul>                                                                                                                            
 */                                                                                                                                 
public class PersistenceManager {                                                                                                   
    // 实现细节...                                                                                                                  
}                                                                                                                                   
                                                                                                                                    


5. 关键设计模式应用                                                                                                                 

                                                                 
  设计模式     应用场景           实现类                         
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  工厂模式     创建图形和渲染器   RendererFactory, ShapeFactory     
  适配器模式   旧版渲染器兼容     LegacyRendererAdapter             
  命令模式     操作封装与撤销     Command接口及实现                 
  观察者模式   图形变更通知       ShapeObserver                     
  单例模式     全局配置管理       GlobalConfig, PersistenceManager  
  访问者模式   图形导出功能       ExportVisitor                     
                                                                    


6. 模块依赖关系                                                                                                                     

                                                                                                                                    
graph-render                                                                                                                        
├── ui                                                                                                                              
│   ├── depends on → core                                                                                                           
│   └── depends on → bridge                                                                                                         
├── core                                                                                                                            
│   ├── depends on → factory                                                                                                        
│   └── depends on → command                                                                                                        
├── bridge                                                                                                                          
│   ├── depends on → legacy                                                                                                         
│   └── depends on → proxy                                                                                                          
└── persistence                                                                                                                     
    └── depends on → factory                                                                                                        
                                                                                                                                    


7. 性能考量                                                                                                                         

 1 渲染性能：                                                                                                                       
    • Swing渲染器直接操作Graphics2D                                                                                                 
    • SVG渲染器使用StringBuilder缓存输出                                                                                            
    • 测试渲染器无实际渲染开销                                                                                                      
 2 内存管理：                                                                                                                       
    • 图形对象轻量化设计                                                                                                            
    • 大对象使用懒加载                                                                                                              
 3 多线程安全：                                                                                                                     
    • 关键组件使用线程安全实现                                                                                                      
    • 避免共享可变状态                                                                                                              


8. 扩展性设计                                                                                                                       

8.1 添加新图形类型                                                                                                                  

 1 实现Shape接口                                                                                                                    
 2 实现对应的工厂方法                                                                                                               
 3 更新PersistenceManager的类型注册                                                                                                 

8.2 添加新渲染器                                                                                                                    

 1 实现Renderer接口                                                                                                                 
 2 在RendererFactory中注册                                                                                                          
 3 更新GlobalConfig支持新类型                                                                                                       


9. 已知限制                                                                                                                         

 1 SwingUI与渲染逻辑存在一定耦合                                                                                                    
 2 旧版渲染器适配有约20%性能开销                                                                                                    
 3 复杂图形组合性能待优化                                                                                                           


10. 未来演进计划                                                                                                                    

 1 v2.1：Web渲染器支持                                                                                                              
 2 v2.2：硬件加速渲染                                                                                                               
 3 v3.0：分布式渲染架构                                                                                                             

────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
文档版本：2.0                                                                                                                       
最后更新：2025-06-27                                                                                                                
归档位置：docs/architecture/design-specification.md                                                                                 


图形渲染系统设计文档（续）                                                                                                          

5. 动态行为建模                                                                                                                     

5.1 图形绘制顺序图                                                                                                                  

                                                                                                                                    
sequenceDiagram                                                                                                                     
    participant User                                                                                                                
    participant SwingUI                                                                                                             
    participant Command                                                                                                             
    participant Shape                                                                                                               
    participant Renderer                                                                                                            
    participant DrawingPanel                                                                                                        
                                                                                                                                    
    User->>SwingUI: 点击"添加圆形"按钮                                                                                              
    SwingUI->>Command: 创建AddShapeCommand                                                                                          
    Command->>Shape: 创建Circle实例                                                                                                 
    SwingUI->>Command: execute()                                                                                                    
    Command->>DrawingPanel: 添加Shape到列表                                                                                         
    DrawingPanel->>Renderer: setGraphics()                                                                                          
    loop 对每个Shape                                                                                                                
        DrawingPanel->>Shape: render(Renderer)                                                                                      
        Shape->>Renderer: drawCircle(x,y,radius)                                                                                    
    end                                                                                                                             
    Renderer-->>DrawingPanel: 绘制完成                                                                                              
    DrawingPanel-->>SwingUI: 重绘请求                                                                                               
    SwingUI-->>User: 显示更新后的图形                                                                                               
                                                                                                                                    

5.2 图形绘制状态图                                                                                                                  

                                                                                                                                    
stateDiagram-v2                                                                                                                     
    [*] --> Idle                                                                                                                    
    Idle --> Rendering: 开始绘制                                                                                                    
    Rendering --> Rendering: 添加图形元素                                                                                           
    Rendering --> Processing: 提交绘制命令                                                                                          
    Processing --> Rendering: 撤销操作                                                                                              
    Processing --> Persisting: 保存图形                                                                                             
    Persisting --> Idle: 保存完成                                                                                                   
    Processing --> Idle: 完成绘制                                                                                                   
                                                                                                                                    

5.3 Undo/Redo顺序图                                                                                                                 

                                                                                                                                    
sequenceDiagram                                                                                                                     
    participant User                                                                                                                
    participant SwingUI                                                                                                             
    participant UndoManager                                                                                                         
    participant Command                                                                                                             
    participant DrawingPanel                                                                                                        
                                                                                                                                    
    User->>SwingUI: 点击"撤销"按钮                                                                                                  
    SwingUI->>UndoManager: undo()                                                                                                   
    UndoManager->>Command: undo()                                                                                                   
    Command->>DrawingPanel: 从图形列表移除元素                                                                                      
    DrawingPanel-->>SwingUI: 重绘请求                                                                                               
    SwingUI-->>User: 显示更新后的图形                                                                                               
                                                                                                                                    
    User->>SwingUI: 点击"重做"按钮                                                                                                  
    SwingUI->>UndoManager: redo()                                                                                                   
    UndoManager->>Command: redo()                                                                                                   
    Command->>DrawingPanel: 重新添加图形元素                                                                                        
    DrawingPanel-->>SwingUI: 重绘请求                                                                                               
    SwingUI-->>User: 显示更新后的图形                                                                                               
                                                                                                                                    

5.4 Undo/Redo状态图                                                                                                                 

                                                                                                                                    
stateDiagram-v2                                                                                                                     
    [*] --> Clean                                                                                                                   
    Clean --> HasHistory: 执行命令                                                                                                  
    HasHistory --> HasHistory: 执行新命令(清空redo栈)                                                                               
    HasHistory --> Undoing: 执行undo                                                                                                
    Undoing --> Redoing: 执行redo                                                                                                   
    Redoing --> Undoing: 执行undo                                                                                                   
    Undoing --> Clean: undo到初始状态                                                                                               
    Redoing --> HasHistory: redo到最后状态                                                                                          
                                                                                                                                    

6. 关键流程说明                                                                                                                     

6.1 图形绘制流程                                                                                                                    

 1 初始化阶段：                                                                                                                     
                                                                                                                                    
   // 伪代码示例                                                                                                                    
   Renderer renderer = RendererFactory.create("swing");                                                                             
   DrawingPanel panel = new DrawingPanel(shapes, renderer);                                                                         
   SwingUI ui = new SwingUI(panel);                                                                                                 
                                                                                                                                    
 2 绘制执行阶段：                                                                                                                   
                                                                                                                                    
   // 命令模式执行                                                                                                                  
   Command cmd = new AddShapeCommand(shapes, new Circle(100,100,50));                                                               
   undoManager.executeCommand(cmd);                                                                                                 
                                                                                                                                    
 3 渲染阶段：                                                                                                                       
                                                                                                                                    
   // 在DrawingPanel的paintComponent中                                                                                              
   for (Shape shape : shapes) {                                                                                                     
       shape.render(renderer);                                                                                                      
   }                                                                                                                                
                                                                                                                                    

6.2 Undo/Redo实现机制                                                                                                               

 1 命令历史管理：                                                                                                                   
                                                                                                                                    
   public class UndoManager {                                                                                                       
       private Deque<Command> undoStack = new ArrayDeque<>();                                                                       
       private Deque<Command> redoStack = new ArrayDeque<>();                                                                       
                                                                                                                                    
       public void executeCommand(Command cmd) {                                                                                    
           cmd.execute();                                                                                                           
           undoStack.push(cmd);                                                                                                     
           redoStack.clear();                                                                                                       
       }                                                                                                                            
                                                                                                                                    
       public void undo() {                                                                                                         
           Command cmd = undoStack.pop();                                                                                           
           cmd.undo();                                                                                                              
           redoStack.push(cmd);                                                                                                     
       }                                                                                                                            
   }                                                                                                                                
                                                                                                                                    
 2 状态恢复：                                                                                                                       
                                                                                                                                    
   flowchart LR                                                                                                                     
       A[执行命令] --> B[保存到undo栈]                                                                                              
       B --> C[清空redo栈]                                                                                                          
       D[撤销] --> E[从undo栈弹出]                                                                                                  
       E --> F[执行undo]                                                                                                            
       F --> G[保存到redo栈]                                                                                                        
       H[重做] --> I[从redo栈弹出]                                                                                                  
       I --> J[执行redo]                                                                                                            
       J --> K[保存到undo栈]                                                                                                        
                                                                                                                                    

7. 异常处理流程                                                                                                                     

7.1 渲染异常处理                                                                                                                    

                                                                                                                                    
sequenceDiagram                                                                                                                     
    participant Shape                                                                                                               
    participant Renderer                                                                                                            
    participant DrawingPanel                                                                                                        
    participant ErrorHandler                                                                                                        
                                                                                                                                    
    Shape->>Renderer: drawCircle(x,y,radius)                                                                                        
    alt 渲染成功                                                                                                                    
        Renderer-->>Shape: 正常返回                                                                                                 
    else 渲染失败                                                                                                                   
        Renderer->>ErrorHandler: 捕获异常                                                                                           
        ErrorHandler->>DrawingPanel: 标记错误状态                                                                                   
        DrawingPanel->>Renderer: 恢复上下文                                                                                         
    end                                                                                                                             
                                                                                                                                    

7.2 Undo/Redo边界条件                                                                                                               

 1 空栈处理：                                                                                                                       
                                                                                                                                    
   public void undo() {                                                                                                             
       if (undoStack.isEmpty()) {                                                                                                   
           return; // 或抛出特定异常                                                                                                
       }                                                                                                                            
       // ...正常处理                                                                                                               
   }                                                                                                                                
                                                                                                                                    
 2 命令不可撤销：                                                                                                                   
                                                                                                                                    
   public interface Command {                                                                                                       
       default boolean canUndo() {                                                                                                  
           return true;                                                                                                             
       }                                                                                                                            
   }                                                                                                                                
                                                                                                                                    

附录：典型场景示例                                                                                                                  

场景1：绘制并撤销圆形                                                                                                               

 1 用户点击"添加圆形"按钮                                                                                                           
 2 系统创建Circle(100,100,50)并执行AddShapeCommand                                                                                  
 3 用户点击"撤销"按钮                                                                                                               
 4 系统从图形列表移除该圆形并重绘                                                                                                   

场景2：重做操作                                                                                                                     

 1 用户执行步骤1-3                                                                                                                  
 2 用户点击"重做"按钮                                                                                                               
 3 系统重新添加圆形并重绘                                                                                                           

────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
文档版本：2.1                                                                                                                       
最后更新：2025-06-27                                                                                                                
图表工具：Mermaid 9.3.0                                                                                                             
相关文档：                                                                                                                          

 • 架构设计规范.md                                                                                                                  
 • 核心API参考.md                                                                                                                   
 • 性能优化指南.md                                                                                                                  


