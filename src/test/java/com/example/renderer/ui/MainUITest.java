package com.example.renderer.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.Test;

public class MainUITest {

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> MainUI.main(new String[0]));
    }

    @Test
    void testSwingThreadInitialization() {
        try (MockedStatic<SwingUtilities> mockedSwing = mockStatic(SwingUtilities.class)) {
            MainUI.main(new String[0]);
            mockedSwing.verify(() -> SwingUtilities.invokeLater(any(Runnable.class)));
        }
    }
}
