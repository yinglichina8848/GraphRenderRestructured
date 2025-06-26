以下是《核心API参考手册》和《部署手册》的完整内容框架及示例实现，采用技术文档标准格式：                                             

────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 核心API参考手册                                                                                                                  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛


1. 文档标识                                                                                                                         

 • 版本：v2.1                                                                                                                       
 • 适用版本：GraphRenderSystem ≥2.0                                                                                                 
 • 生成工具：JavaDoc 18+                                                                                                            
 • 最后更新：2025-06-28                                                                                                             


2. 渲染核心API                                                                                                                      

2.1 Renderer接口                                                                                                                    

                                                                                                                                    
/**                                                                                                                                 
 * 图形渲染核心接口                                                                                                                 
 * @implSpec 实现类必须保证线程安全                                                                                                 
 * @since 2.0                                                                                                                       
 */                                                                                                                                 
public interface Renderer {                                                                                                         
    /**                                                                                                                             
     * 设置渲染样式                                                                                                                 
     * @param stroke 十六进制颜色码（如"#FF0000"）                                                                                  
     * @param fill 填充色（"none"表示无填充）                                                                                       
     * @param width 线宽（像素，范围1-10）                                                                                          
     * @throws IllegalArgumentException 参数非法时抛出                                                                              
     */                                                                                                                             
    void setStyle(String stroke, String fill, int width);                                                                           
                                                                                                                                    
    // 其他方法...                                                                                                                  
}                                                                                                                                   
                                                                                                                                    

典型调用：                                                                                                                          

                                                                                                                                    
renderer.setStyle("#0000FF", "none", 2);  // 蓝色描边，无填充                                                                       
                                                                                                                                    


3. 图形对象API                                                                                                                      

3.1 Shape接口                                                                                                                       

                                                                                                                                    
/**                                                                                                                                 
 * 图形对象基础接口                                                                                                                 
 * @see Circle 圆形实现                                                                                                             
 * @see Rectangle 矩形实现                                                                                                          
 */                                                                                                                                 
public interface Shape {                                                                                                            
    /**                                                                                                                             
     * 渲染当前图形                                                                                                                 
     * @param renderer 非空渲染器实例                                                                                               
     * @throws NullPointerException 当renderer为null时抛出                                                                          
     */                                                                                                                             
    void render(Renderer renderer);                                                                                                 
}                                                                                                                                   
                                                                                                                                    

继承体系：                                                                                                                          

                                                                                                                                    
classDiagram                                                                                                                        
    Shape <|-- Circle                                                                                                               
    Shape <|-- Rectangle                                                                                                            
    Shape : +render(Renderer)                                                                                                       
    Circle : +int radius                                                                                                            
    Rectangle : +int width, height                                                                                                  
                                                                                                                                    


4. 命令系统API                                                                                                                      

4.1 Command接口                                                                                                                     

                                                                                                                                    
/**                                                                                                                                 
 * 命令模式基类                                                                                                                     
 * @see UndoManager 命令管理器                                                                                                      
 */                                                                                                                                 
public interface Command {                                                                                                          
    /**                                                                                                                             
     * 执行命令                                                                                                                     
     * @return 执行是否成功                                                                                                         
     * @throws IllegalStateException 当条件不满足时抛出                                                                             
     */                                                                                                                             
    boolean execute();                                                                                                              
                                                                                                                                    
    // undo/redo方法...                                                                                                             
}                                                                                                                                   
                                                                                                                                    

时序逻辑：                                                                                                                          

                                                                                                                                    
sequenceDiagram                                                                                                                     
    Client->>+Command: execute()                                                                                                    
    Command->>+Receiver: 执行业务操作                                                                                               
    Receiver-->>-Command: 结果                                                                                                      
    Command-->>-Client: 状态                                                                                                        
                                                                                                                                    

────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

文档维护说明：                                                                                                                      

 1 API手册随代码变更实时更新（通过CI自动生成）                                                                                      
 2 关键变更需在CHANGELOG.md中记录                                                                                                   

附件：                                                                                                                              

 • API完整索引                                                                                                                      
 • 部署检查清单                                                                                                                     

建议将这两个文档存放在docs/目录下：                                                                                                 

                                                                                                                                    
doc/                                                                                                                               
├── api/                                                                                                                            
│   ├── REFERENCE.md       # 核心API手册                                                                                            
│   └── diagrams/          # UML图源文件                                                                                            
└── operations/                                                                                                                     
    ├── DEPLOYMENT.md      # 部署手册                                                                                               
    └── monitoring/        # 监控配置模板                                                                                           
                                                                                                                                    



