/**
 * LegacyRendererAdapter的单元测试类。
 * 
 * <p>验证适配器模式是否正确转换新旧接口：
 * <ul>
 *   <li>方法调用被正确转发到LegacyRenderer</li>
 *   <li>参数传递正确</li>
 *   <li>接口转换完整性</li>
 * </ul>
 *
 * <p>测试策略：
 * <ul>
 *   <li>使用Mockito模拟LegacyRenderer</li>
 *   <li>验证方法转发逻辑</li>
 *   <li>参数传递验证</li>
 *   <li>边界条件测试</li>
 * </ul>
 *
 * @author Aider+DeepSeek
 * @version 1.0
 * @see LegacyRendererAdapter
 * @since 2025-06-24
 */
package com.example.renderer.adapter;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.adapter.LegacyRendererAdapter;
import com.example.renderer.legacy.LegacyRenderer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LegacyRendererAdapter 的单元测试。
 */
public class LegacyRendererAdapterTest {

    /** 模拟的LegacyRenderer实例，用于验证方法调用 */
    private LegacyRenderer mockLegacyRenderer;
    
    /** 待测试的适配器实例 */
    private Renderer renderer;

    @BeforeEach
    public void setUp() {
        mockLegacyRenderer = Mockito.mock(LegacyRenderer.class);
        renderer = new LegacyRendererAdapter(mockLegacyRenderer);
    }

    /**
     * 测试适配器将drawCircle()转发到drawLegacyCircle()。
     * 
     * <p>验证点：
     * <ul>
     *   <li>调用适配器的drawCircle()</li>
     *   <li>验证LegacyRenderer的drawLegacyCircle()被调用</li>
     *   <li>参数传递正确</li>
     * </ul>
     * 
     * <p>测试方法：
     * <ol>
     *   <li>创建Mock LegacyRenderer和适配器实例</li>
     *   <li>调用适配器的drawCircle()</li>
     *   <li>验证Mock是否收到正确调用</li>
     * </ol>
     */
    /**
     * 测试drawCircle()方法是否正确转发到LegacyRenderer。
     * 
     * <p>验证点：
     * <ul>
     *   <li>LegacyRenderer.drawLegacyCircle()被调用一次</li>
     *   <li>圆心坐标和半径参数传递正确</li>
     *   <li>无返回值处理</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testDrawCircle_ForwardsToLegacyRenderer() {
        int x = 10, y = 20, radius = 30;
        renderer.drawCircle(x, y, radius);
        verify(mockLegacyRenderer).drawLegacyCircle(x, y, radius);
    }

    /**
     * 测试drawRectangle()方法是否正确转发到LegacyRenderer。
     * 
     * <p>验证点：
     * <ul>
     *   <li>LegacyRenderer.drawLegacyRectangle()被调用一次</li>
     *   <li>参数传递正确</li>
     *   <li>无返回值处理</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testDrawRectangle_ForwardsToLegacyRenderer() {
        int x = 10, y = 20, width = 40, height = 50;
        renderer.drawRectangle(x, y, width, height);
        verify(mockLegacyRenderer).drawLegacyRectangle(x, y, width, height);
    }

    /**
     * 测试drawTriangle()方法是否正确转发到LegacyRenderer。
     * 
     * <p>验证点：
     * <ul>
     *   <li>LegacyRenderer.drawLegacyTriangle()被调用一次</li>
     *   <li>所有顶点坐标传递正确</li>
     *   <li>调用顺序正确</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testDrawTriangle_ForwardsToLegacyRenderer() {
        int x1 = 10, y1 = 20, x2 = 30, y2 = 40, x3 = 50, y3 = 60;
        renderer.drawTriangle(x1, y1, x2, y2, x3, y3);
        verify(mockLegacyRenderer).drawLegacyTriangle(x1, y1, x2, y2, x3, y3);
    }

    /**
     * 测试drawEllipse()方法是否正确转发到LegacyRenderer。
     * 
     * <p>验证点：
     * <ul>
     *   <li>LegacyRenderer.drawLegacyEllipse()被调用一次</li>
     *   <li>中心坐标和半径参数传递正确</li>
     *   <li>无返回值处理</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    /**
     * 测试drawEllipse()方法是否正确转发到LegacyRenderer。
     * 
     * <p>验证点：
     * <ul>
     *   <li>LegacyRenderer.drawLegacyEllipse()被调用一次</li>
     *   <li>中心坐标和半径参数传递正确</li>
     *   <li>无返回值处理</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testDrawEllipse_ForwardsToLegacyRenderer() {
        int x = 10, y = 20, width = 30, height = 40;
        renderer.drawEllipse(x, y, width, height);
        verify(mockLegacyRenderer).drawLegacyEllipse(x, y, width, height);
    }
    /**
     * 测试构造器对null参数的处理。
     * 
     * <p>验证点：
     * <ul>
     *   <li>传入null参数时抛出NullPointerException</li>
     *   <li>错误消息包含有意义的信息</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testConstructor_NullParameter() {
        Exception exception = assertThrows(NullPointerException.class, () -> 
            new LegacyRendererAdapter(null));
        assertTrue(exception.getMessage().contains("LegacyRenderer cannot be null"));
    }

    /**
     * 测试drawCircle()方法处理极端坐标的情况。
     * 
     * <p>验证点：
     * <ul>
     *   <li>超大坐标值被正确传递</li>
     *   <li>不会抛出算术异常</li>
     *   <li>参数传递顺序正确</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testDrawCircle_ExtremeCoordinates() {
        renderer.drawCircle(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE/2);
        verify(mockLegacyRenderer).drawLegacyCircle(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE/2);
    }

    /**
     * 测试后置清理，在每个测试方法执行后运行。
     * 
     * <p>清理：
     * <ul>
     *   <li>重置模拟对象</li>
     *   <li>释放资源</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @AfterEach
    public void tearDown() {
        mockLegacyRenderer = null;
        renderer = null;
    }
}
