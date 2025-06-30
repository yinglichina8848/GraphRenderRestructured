package com.example.renderer.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.factory.Shape;
import com.example.renderer.singleton.PersistenceManager;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

public class SwingUITest {
    // 空实现测试子类，用于重写对话框方法

    private SwingUI swingUI;
    private MockedStatic<PersistenceManager> mockedPersistence;
    private MockedStatic<JOptionPane> mockedJOptionPane;

    @BeforeEach
    void setUp() {
        swingUI = new SwingUI();
        swingUI.setVisible(false); // 避免实际渲染
        mockedPersistence = mockStatic(PersistenceManager.class);
        mockedJOptionPane = mockStatic(JOptionPane.class);
    }
    
    @AfterEach
    void tearDown() {
        mockedPersistence.close(); // 清除静态mock
        mockedJOptionPane.close();
    }

    @Test
    void testButtonInitialization() {
        assertComponentClassExists(swingUI, "btnCircle", JButton.class);
        assertComponentClassExists(swingUI, "btnRect", JButton.class);
        assertComponentClassExists(swingUI, "btnEllipse", JButton.class);
        assertComponentClassExists(swingUI, "btnTriangle", JButton.class);
        assertComponentClassExists(swingUI, "btnUndo", JButton.class);
        assertComponentClassExists(swingUI, "btnRedo", JButton.class);
        assertComponentClassExists(swingUI, "btnSave", JButton.class);
        assertComponentClassExists(swingUI, "btnLoad", JButton.class);
    }

    private void assertComponentClassExists(SwingUI ui, String componentName, Class<?> clazz) {
        Component[] components = ui.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof Container) {
                Component[] subComponents = ((Container) comp).getComponents();
                for (Component subComp : subComponents) {
                    if (subComp.getName() != null && subComp.getName().equals(componentName) && clazz.isInstance(subComp)) {
                        return;
                    }
                }
            }
        }
        fail("Component " + componentName + " of type " + clazz.getSimpleName() + " not found");
    }

    @Test
    void testAddShape() {
        int initialSize = swingUI.shapes.size();
        JButton btnCircle = (JButton) findComponentByName(swingUI, "btnCircle");
        btnCircle.doClick();
        assertEquals(initialSize + 1, swingUI.shapes.size());
    }

    private Component findComponentByName(SwingUI ui, String name) {
        Component[] components = ui.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof Container) {
                Component[] subComponents = ((Container) comp).getComponents();
                for (Component subComp : subComponents) {
                    if (name.equals(subComp.getName())) {
                        return subComp;
                    }
                }
            }
        }
        return null;
    }

    @Test
    void testSaveLoadShapes() throws Exception {
        // 创建临时文件对象
        final File saveFile = new File("test_save.json");
        final File openFile = new File("test_load.json");

        // 创建测试子类，重写对话框方法
        SwingUI testUI = new SwingUI() {
            @Override
            protected File selectSaveFile() {
                return saveFile;
            }

            @Override
            protected File selectOpenFile() {
                return openFile;
            }

            @Override
            protected void showMessage(String message) {
                // 空实现，避免显示对话框
            }
        };
        testUI.setVisible(false);
        
        PersistenceManager manager = mock(PersistenceManager.class);
        mockedPersistence.when(PersistenceManager::getInstance).thenReturn(manager);

        // 测试保存操作
        JButton btnSave = (JButton) findComponentByName(testUI, "btnSave");
        btnSave.doClick();
        // 使用文件对象的绝对路径进行验证
        verify(manager).saveShapesToFile(anyList(), eq(saveFile.getAbsolutePath()));

        // 测试加载操作
        JButton btnLoad = (JButton) findComponentByName(testUI, "btnLoad");
        btnLoad.doClick();
        // 使用文件对象的绝对路径进行验证
        verify(manager).loadShapesFromFile(eq(openFile.getAbsolutePath()));
    }

    // 确保保存和加载按钮存在于UI中
    @Test
    void testSaveLoadButtonsExist() {
        JButton btnSave = (JButton) findComponentByName(swingUI, "btnSave");
        JButton btnLoad = (JButton) findComponentByName(swingUI, "btnLoad");
        
        assertNotNull(btnSave, "保存按钮不存在");
        assertNotNull(btnLoad, "加载按钮不存在");
    }

    @Test
    void testSelectSaveFile() {
        // 创建测试子类，模拟文件选择对话框选择"Approve"
        SwingUI testUI = new SwingUI() {
            @Override
            protected File selectSaveFile() {
                // 返回一个文件实例表示用户选择了文件
                return new File("test.json");
            }
        };
        testUI.setVisible(false);

        File file = testUI.selectSaveFile();
        assertNotNull(file);

        // 创建另一个测试子类，模拟文件选择对话框选择"Cancel"
        SwingUI testUI2 = new SwingUI() {
            @Override
            protected File selectSaveFile() {
                return null; // 模拟取消操作
            }
        };
        testUI2.setVisible(false);

        assertNull(testUI2.selectSaveFile());
    }

    @Test
    void testShowMessage() {
        SwingUI testUI = new SwingUI();
        testUI.setVisible(false);
        testUI.showMessage("Test message");

        // 验证两个参数的showMessageDialog被调用
        mockedJOptionPane.verify(() -> JOptionPane.showMessageDialog(
            eq(testUI), 
            eq("Test message")
        ), times(1));
    }

    @Test
    void testCreateFallbackRenderer() {
        SwingUI testUI = new SwingUI();
        testUI.setVisible(false);
        Renderer renderer = testUI.createFallbackRenderer();
        assertNotNull(renderer);
    }
}
