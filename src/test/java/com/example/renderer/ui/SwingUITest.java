package com.example.renderer.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.factory.Shape;
import com.example.renderer.singleton.PersistenceManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class SwingUITest {

    private SwingUI swingUI;
    private MockedStatic<PersistenceManager> mockedPersistence;

    @BeforeEach
    void setUp() {
        swingUI = new SwingUI();
        mockedPersistence = mockStatic(PersistenceManager.class);
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
    void testRemoveFallbackRenderer() {
        Renderer fallback = mock(Renderer.class);
        SwingUI spyUI = spy(swingUI);
        when(spyUI.createFallbackRenderer()).thenReturn(fallback);
        
        Renderer result = spyUI.createFallbackRenderer();
        assertNotNull(result);
        assertEquals(fallback, result);
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
        PersistenceManager manager = mock(PersistenceManager.class);
        mockedPersistence.when(PersistenceManager::getInstance).thenReturn(manager);

        List<Shape> mockShapes = new ArrayList<>();
        when(manager.loadShapesFromFile(anyString())).thenReturn(mockShapes);

        JButton btnSave = (JButton) findComponentByName(swingUI, "btnSave");
        JButton btnLoad = (JButton) findComponentByName(swingUI, "btnLoad");

        btnLoad.doClick();
        verify(manager, times(1)).loadShapesFromFile(anyString());

        btnSave.doClick();
        verify(manager, times(1)).saveShapesToFile(anyList(), anyString());
    }
}
