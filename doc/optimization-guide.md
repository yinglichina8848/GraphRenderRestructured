┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 性能优化指南                                                                                                                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛


1. 文档概述                                                                                                                         

本文档提供图形渲染系统的性能优化方法和最佳实践，帮助开发人员识别和解决性能瓶颈。                                                    


2. 性能指标体系                                                                                                                     

2.1 关键性能指标(KPI)                                                                                                               

                                     
  指标类别   目标值     测量方法      
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  渲染延迟   <50ms/帧   帧耗时统计    
  内存占用   <100MB     JVM内存监控   
  命令执行   <10ms/次   操作耗时采样  
  启动时间   <2s        冷启动测量    
                                      


3. 渲染性能优化                                                                                                                     

3.1 渲染循环优化                                                                                                                    

                                                                                                                                    
// 优化前：每次创建新对象                                                                                                           
for (Shape shape : shapes) {                                                                                                        
    Graphics2D g = createNewGraphics();                                                                                             
    shape.render(g);                                                                                                                
}                                                                                                                                   
                                                                                                                                    
// 优化后：重用渲染上下文                                                                                                           
Graphics2D g = createSharedGraphics();                                                                                              
for (Shape shape : shapes) {                                                                                                        
    shape.render(g);  // 减少对象分配                                                                                               
}                                                                                                                                   
                                                                                                                                    

优化技巧：                                                                                                                          

 1 避免在渲染循环中创建临时对象                                                                                                     
 2 预计算不变的状态值                                                                                                               
 3 使用DoubleBuffering减少闪烁                                                                                                      

3.2 图形批处理                                                                                                                      

                                                                                                                                    
// 批处理示例                                                                                                                       
public class BatchRenderer {                                                                                                        
    private List<RenderCommand> batch = new ArrayList<>(1000);                                                                      
                                                                                                                                    
    public void addToBatch(RenderCommand cmd) {                                                                                     
        batch.add(cmd);                                                                                                             
        if (batch.size() >= 1000) {                                                                                                 
            flushBatch();                                                                                                           
        }                                                                                                                           
    }                                                                                                                               
                                                                                                                                    
    private void flushBatch() {                                                                                                     
        // 批量执行渲染命令                                                                                                         
    }                                                                                                                               
}                                                                                                                                   
                                                                                                                                    


4. 内存优化                                                                                                                         

4.1 对象池模式                                                                                                                      

                                                                                                                                    
public class ShapePool {                                                                                                            
    private static final int MAX_POOL_SIZE = 100;                                                                                   
    private static Queue<Circle> circlePool = new ConcurrentLinkedQueue<>();                                                        
                                                                                                                                    
    public static Circle acquire(int x, int y, int r) {                                                                             
        Circle c = circlePool.poll();                                                                                               
        return c != null ? c.reset(x,y,r) : new Circle(x,y,r);                                                                      
    }                                                                                                                               
                                                                                                                                    
    public static void release(Circle c) {                                                                                          
        if (circlePool.size() < MAX_POOL_SIZE) {                                                                                    
            circlePool.offer(c);                                                                                                    
        }                                                                                                                           
    }                                                                                                                               
}                                                                                                                                   
                                                                                                                                    

4.2 缓存策略                                                                                                                        

                                                     
  缓存类型       实现方式                适用场景      
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  软引用缓存     SoftReference<Bitmap>   大图形对象    
  LRU缓存        LinkedHashMap           常用图形资源  
  线程局部缓存   ThreadLocal             渲染临时对象  
                                                       


5. 并发优化                                                                                                                         

5.1 并行渲染                                                                                                                        

                                                                                                                                    
// 使用ForkJoin并行处理                                                                                                             
public class ParallelRenderer {                                                                                                     
    public void render(List<Shape> shapes) {                                                                                        
        ForkJoinPool.commonPool().submit(() ->                                                                                      
            shapes.parallelStream().forEach(shape ->                                                                                
                shape.render(threadLocalRenderer.get()))                                                                            
            .join();                                                                                                                
    }                                                                                                                               
}                                                                                                                                   
                                                                                                                                    

5.2 锁优化技巧                                                                                                                      

 1 减小锁粒度：                                                                                                                     
                                                                                                                                    
   // 粗粒度锁                                                                                                                      
   synchronized(this) { /* 整个方法 */ }                                                                                            
                                                                                                                                    
   // 细粒度锁                                                                                                                      
   synchronized(renderLock) { /* 仅渲染相关 */ }                                                                                    
                                                                                                                                    
 2 无锁数据结构：                                                                                                                   
                                                                                                                                    
   private final AtomicInteger renderCount = new AtomicInteger();                                                                   
                                                                                                                                    
   public void increment() {                                                                                                        
       renderCount.incrementAndGet();                                                                                               
   }                                                                                                                                
                                                                                                                                    


6. 算法优化                                                                                                                         

6.1 空间分区                                                                                                                        

                                                                                                                                    
public class SpatialGrid {                                                                                                          
    private final int cellSize;                                                                                                     
    private final Map<GridKey, List<Shape>> grid = new ConcurrentHashMap<>();                                                       
                                                                                                                                    
    public void addShape(Shape s) {                                                                                                 
        getGridKeys(s.bounds()).forEach(key ->                                                                                      
            grid.computeIfAbsent(key, k -> new CopyOnWriteArrayList()).add(s));                                                     
    }                                                                                                                               
                                                                                                                                    
    public List<Shape> query(Rectangle area) {                                                                                      
        // 只查询相关网格                                                                                                           
    }                                                                                                                               
}                                                                                                                                   
                                                                                                                                    

6.2 懒加载模式                                                                                                                      

                                                                                                                                    
public class LazyShape implements Shape {                                                                                           
    private volatile Shape realShape;                                                                                               
                                                                                                                                    
    public void render(Renderer r) {                                                                                                
        if (realShape == null) {                                                                                                    
            synchronized(this) {                                                                                                    
                if (realShape == null) {                                                                                            
                    realShape = loadShape();                                                                                        
                }                                                                                                                   
            }                                                                                                                       
        }                                                                                                                           
        realShape.render(r);                                                                                                        
    }                                                                                                                               
}                                                                                                                                   
                                                                                                                                    


7. 性能分析工具                                                                                                                     

7.1 工具矩阵                                                                                                                        

                                             
  工具名称    用途             使用场景      
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  JProfiler    CPU/Memory分析   深度性能调优  
  VisualVM     基础监控         快速问题定位  
  JMH          微基准测试       算法性能对比  
  GC日志分析   内存问题         OOM诊断       
                                              

7.2 典型分析流程                                                                                                                    

 1 收集数据：                                                                                                                       
                                                                                                                                    
   java -XX:+PrintGC -Xloggc:gc.log -jar app.jar                                                                                    
                                                                                                                                    
 2 识别热点：                                                                                                                       
                                                                                                                                    
   jstack <pid> > thread_dump.txt                                                                                                   
                                                                                                                                    
 3 优化验证：                                                                                                                       
                                                                                                                                    
   @Benchmark                                                                                                                       
   public void testRenderPerformance() {                                                                                            
       renderer.drawTestScene();                                                                                                    
   }                                                                                                                                
                                                                                                                                    


8. 优化案例库                                                                                                                       

8.1 案例1：渲染卡顿优化                                                                                                             

问题现象：                                                                                                                          

 • 复杂场景下FPS降至10以下                                                                                                          

解决方案：                                                                                                                          

 1 实现四叉树空间分区                                                                                                               
 2 增加视口裁剪                                                                                                                     
 3 使用分级细节(LOD)                                                                                                                

效果：                                                                                                                              

 • FPS提升至60+                                                                                                                     
 • CPU使用率降低40%                                                                                                                 

8.2 案例2：内存泄漏解决                                                                                                             

问题现象：                                                                                                                          

 • 长时间运行后OOM                                                                                                                  

根本原因：                                                                                                                          

 • 未释放的图形缓存引用                                                                                                             

修复方案：                                                                                                                          

                                                                                                                                    
// 使用弱引用管理缓存                                                                                                               
private static final Map<Key, WeakReference<Bitmap>> cache = new HashMap<>();                                                       
                                                                                                                                    


9. 性能测试方案                                                                                                                     

9.1 测试场景设计                                                                                                                    

                                                   
  场景编号   描述                    性能指标      
 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 
  PTS-001    1000基本图形渲染        帧耗时、内存  
  PTS-002    连续撤销/重做压力测试   操作延迟      
  PTS-003    大文件加载测试          IO耗时、内存  
                                                   

9.2 测试代码示例                                                                                                                    

                                                                                                                                    
public class RenderingBenchmark {                                                                                                   
    @Test                                                                                                                           
    public void stressTest() {                                                                                                      
        TimeMeter meter = new TimeMeter();                                                                                          
        for (int i = 0; i < 10000; i++) {                                                                                           
            meter.record(() -> renderer.drawComplexScene());                                                                        
        }                                                                                                                           
        assertTrue(meter.avgTime() < 50, "单帧超过50ms阈值");                                                                       
    }                                                                                                                               
}                                                                                                                                   
                                                                                                                                    


10. 持续优化机制                                                                                                                    

10.1 性能看板                                                                                                                       

 1 监控指标：                                                                                                                       
    • 实时FPS显示                                                                                                                   
    • 内存占用图表                                                                                                                  
    • 操作响应时间                                                                                                                  
 2 告警阈值：                                                                                                                       
                                                                                                                                    
   if (frameTime > 50) {                                                                                                            
       PerformanceMonitor.alert("渲染超时");                                                                                        
   }                                                                                                                                
                                                                                                                                    

10.2 优化工作流                                                                                                                     

                                                                                                                                    
[性能测试] -> [问题分析] -> [方案设计]                                                                                              
    -> [代码优化] -> [验证测试] -> [文档更新]                                                                                       
                                                                                                                                    

────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
版本记录：                                                                                                                          

 • v1.0 2025-06-20 初始版本                                                                                                         
 • v1.1 2025-06-25 新增并发优化章节                                                                                                 
 • v1.2 2025-06-27 补充实际案例                                                                                                     

归档位置：docs/performance/optimization-guide.md                                                                                    

