┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 架构设计规范                                                                                                                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛


1. 文档概述                                                                                                                         

本文档定义了图形渲染系统的架构设计规范和标准，确保系统开发遵循统一的设计原则和最佳实践。                                            


2. 设计原则                                                                                                                         

2.1 核心原则                                                                                                                        

 1 单一职责原则 (SRP)                                                                                                               
    • 每个类/模块只承担一个明确的职责                                                                                               
    • 示例：Renderer只负责渲染，Shape只定义图形属性                                                                                 
 2 开闭原则 (OCP)                                                                                                                   
    • 通过扩展而非修改来增加新功能                                                                                                  
    • 示例：通过实现Renderer接口添加新渲染器                                                                                        
 3 依赖倒置原则 (DIP)                                                                                                               
    • 高层模块不依赖低层模块，都依赖抽象                                                                                            
    • 示例：UI层通过Renderer接口使用渲染功能                                                                                        

2.2 补充原则                                                                                                                        

 1 最少知识原则                                                                                                                     
    • 模块只与直接朋友通信                                                                                                          
    • 示例：Command不需要知道DrawingPanel内部实现                                                                                   
 2 约定优于配置                                                                                                                     
    • 采用合理的默认值减少配置                                                                                                      
    • 示例：RendererFactory内置默认渲染器                                                                                           


3. 分层规范                                                                                                                         

3.1 层次定义                                                                                                                        

                                                                        
  层级         组件示例                        职责             允许依赖        
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  表现层       SwingUI, DrawingPanel           用户交互和展示   业务逻辑层      
  业务逻辑层   Command, UndoManager            核心业务流程     服务层、领域层  
  服务层       RendererFactory, ShapeFactory   技术服务提供     领域层          
  领域层       Shape, Renderer                 核心业务模型     无              
  基础设施层   PersistenceManager              技术实现细节     被各层依赖      
                                                                                

3.2 层间通信规则                                                                                                                    

 1 严格分层：只允许上层调用下层                                                                                                     
 2 跨层调用：必须通过接口抽象                                                                                                       
 3 数据传递：使用DTO而非领域对象跨层                                                                                                


4. 模块设计规范                                                                                                                     

4.1 模块划分标准                                                                                                                    

 1 功能内聚：相关功能放在同一模块                                                                                                   
    • 示例：所有渲染器实现在bridge模块                                                                                              
 2 变更隔离：可能同时变更的内容放在一起                                                                                             
    • 示例：legacy模块隔离旧版代码                                                                                                  
 3 复用粒度：按复用需求划分模块                                                                                                     
    • 示例：core模块包含可复用基础设施                                                                                              

4.2 模块依赖规则                                                                                                                    

                                                                                                                                    
graph TD                                                                                                                            
    ui --> core                                                                                                                     
    ui --> bridge                                                                                                                   
    command --> core                                                                                                                
    bridge --> legacy                                                                                                               
    factory --> core                                                                                                                
    persistence --> factory                                                                                                         
                                                                                                                                    


5. 接口设计规范                                                                                                                     

5.1 接口定义要求                                                                                                                    

 1 明确职责：                                                                                                                       
                                                                                                                                    
   /**                                                                                                                              
    * 图形渲染接口                                                                                                                  
    * @implSpec 实现类必须保证线程安全                                                                                              
    */                                                                                                                              
   public interface Renderer {                                                                                                      
       void drawCircle(int x, int y, int radius);                                                                                   
   }                                                                                                                                
                                                                                                                                    
 2 参数校验：                                                                                                                       
                                                                                                                                    
   default void drawCircle(int x, int y, int radius) {                                                                              
       Objects.requireNonNull(renderer);                                                                                            
       if (radius <= 0) throw new IllegalArgumentException();                                                                       
       // 实际实现                                                                                                                  
   }                                                                                                                                
                                                                                                                                    

5.2 接口演化策略                                                                                                                    

 1 新增方法：使用默认实现保持兼容                                                                                                   
 2 方法弃用：用@Deprecated标注并说明替代方案                                                                                        
 3 重大变更：创建新接口版本                                                                                                         


6. 类设计规范                                                                                                                       

6.1 类职责矩阵                                                                                                                      

                                                         
  类别     示例              职责数量   方法数量   属性数量  
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  控制类   UndoManager       1          5-10       2-5       
  实体类   Circle            1          5-8        3-6       
  服务类   RendererFactory   1          3-5        1-3       
                                                             

6.2 类设计约束                                                                                                                      

 1 不可变性：值对象应设计为不可变                                                                                                   
                                                                                                                                    
   public final class Point {                                                                                                       
       private final int x;                                                                                                         
       private final int y;                                                                                                         
       // 只有getter方法                                                                                                            
   }                                                                                                                                
                                                                                                                                    
 2 构造规范：                                                                                                                       
    • 避免过长参数列表（≤5个）                                                                                                      
    • 复杂对象使用Builder模式                                                                                                       


7. 设计模式应用规范                                                                                                                 

7.1 模式选用标准                                                                                                                    

                                             
  场景       首选模式   替代方案   禁用情况  
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  对象创建   工厂方法   简单工厂   直接new   
  行为扩展   访问者     策略       继承      
  状态管理   备忘录     状态       标志位    
                                             

7.2 模式实现模板                                                                                                                    

工厂方法示例：                                                                                                                      

                                                                                                                                    
public interface RendererFactory {                                                                                                  
    // 标准工厂方法                                                                                                                 
    Renderer create();                                                                                                              
                                                                                                                                    
    // 带参数的变体                                                                                                                 
    Renderer create(String config);                                                                                                 
}                                                                                                                                   
                                                                                                                                    

命令模式示例：                                                                                                                      

                                                                                                                                    
public abstract class AbstractCommand implements Command {                                                                          
    protected final List<Shape> shapes;                                                                                             
                                                                                                                                    
    // 模板方法                                                                                                                     
    public final void execute() {                                                                                                   
        if (!canExecute()) throw new IllegalStateException();                                                                       
        doExecute();                                                                                                                
    }                                                                                                                               
                                                                                                                                    
    protected abstract void doExecute();                                                                                            
}                                                                                                                                   
                                                                                                                                    


8. 异常处理规范                                                                                                                     

8.1 异常分类                                                                                                                        

                                                     
  类型       示例                    处理方式        
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  业务异常   InvalidShapeException   展示给用户      
  系统异常   RenderingException      日志记录后恢复  
  致命错误   OutOfMemoryError        终止应用        
                                                     

8.2 异常处理模板                                                                                                                    

                                                                                                                                    
try {                                                                                                                               
    renderer.drawShape(shape);                                                                                                      
} catch (RenderingException e) {                                                                                                    
    LOGGER.error("渲染失败", e);                                                                                                    
    fallbackRenderer.drawPlaceholder();                                                                                             
} finally {                                                                                                                         
    renderer.cleanup();                                                                                                             
}                                                                                                                                   
                                                                                                                                    


9. 性能设计规范                                                                                                                     

9.1 性能敏感场景                                                                                                                    

 1 渲染循环：                                                                                                                       
                                                                                                                                    
   // 避免在渲染循环中创建对象                                                                                                      
   for (Shape shape : shapes) {                                                                                                     
       shape.render(reusedRenderer);                                                                                                
   }                                                                                                                                
                                                                                                                                    
 2 内存缓存：                                                                                                                       
                                                                                                                                    
   public class ShapeCache {                                                                                                        
       private static final int MAX_SIZE = 100;                                                                                     
       private static LinkedHashMap<Key, Shape> cache =                                                                             
           new LinkedHashMap<>(..., 0.75f, true) {                                                                                  
               protected boolean removeEldestEntry(Map.Entry eldest) {                                                              
                   return size() > MAX_SIZE;                                                                                        
               }                                                                                                                    
           };                                                                                                                       
   }                                                                                                                                
                                                                                                                                    

9.2 并发控制                                                                                                                        

 1 线程安全级别：                                                                                                                   
                                                                                                                                    
   @ThreadSafe                                                                                                                      
   public class GlobalConfig {                                                                                                      
       // 使用volatile保证可见性                                                                                                    
       private volatile String renderMode;                                                                                          
   }                                                                                                                                
                                                                                                                                    
 2 锁粒度控制：                                                                                                                     
                                                                                                                                    
   public class ThreadSafeRenderer implements Renderer {                                                                            
       private final Object lock = new Object();                                                                                    
                                                                                                                                    
       public void drawCircle(...) {                                                                                                
           synchronized(lock) {                                                                                                     
               // 关键区代码                                                                                                        
           }                                                                                                                        
       }                                                                                                                            
   }                                                                                                                                
                                                                                                                                    


10. 文档化规范                                                                                                                      

10.1 代码注释标准                                                                                                                   

 1 类注释：                                                                                                                         
                                                                                                                                    
   /**                                                                                                                              
    * 圆形图形实现                                                                                                                  
    *                                                                                                                               
    * <p><b>线程安全：</b>此类是不可变对象，线程安全</p>                                                                            
    *                                                                                                                               
    * @see Shape 基础接口                                                                                                           
    * @since 2.0                                                                                                                    
    */                                                                                                                              
   public final class Circle implements Shape {                                                                                     
                                                                                                                                    
 2 方法注释：                                                                                                                       
                                                                                                                                    
   /**                                                                                                                              
    * 绘制圆形                                                                                                                      
    * @param radius 半径(必须>0)                                                                                                    
    * @throws IllegalArgumentException 如果半径≤0                                                                                   
    * @throws RenderingException 当底层渲染失败时                                                                                   
    */                                                                                                                              
   public void drawCircle(int radius) {                                                                                             
                                                                                                                                    

10.2 设计决策记录                                                                                                                   

 1 ADR模板：                                                                                                                        
                                                                                                                                    
   # 1. 使用适配器模式整合旧版渲染器                                                                                                
                                                                                                                                    
   ## 状态                                                                                                                          
   已采纳                                                                                                                           
                                                                                                                                    
   ## 决策因素                                                                                                                      
   - 需要支持遗留系统                                                                                                               
   - 最小化修改现有代码                                                                                                             
   - 20%性能开销可接受                                                                                                              
                                                                                                                                    
   ## 替代方案                                                                                                                      
   1. 直接重写旧版代码（高风险）                                                                                                    
   2. 并行维护两套系统（高成本）                                                                                                    
                                                                                                                                    


附录A：架构质量检查表                                                                                                               

 1 [ ] 所有模块依赖关系符合分层规范                                                                                                 
 2 [ ] 关键接口有明确的@implSpec说明                                                                                                
 3 [ ] 性能敏感代码经过Profiler验证                                                                                                 
 4 [ ] 异常处理覆盖所有已知错误场景                                                                                                 
 5 [ ] 设计决策已记录在ADR文档中                                                                                                    


附录B：参考架构                                                                                                                     

🌆 参考架构图                                                                                                                       

版本控制：                                                                                                                          

 • v1.0 2025-06-20 初始版本                                                                                                         
 • v1.1 2025-06-25 添加性能设计规范                                                                                                 
 • v2.0 2025-06-27 重构分层规范                                                                                                     

归档位置：docs/architecture/design-specification.md    
