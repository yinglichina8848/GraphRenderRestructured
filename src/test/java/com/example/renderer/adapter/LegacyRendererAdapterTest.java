package com.example.renderer.adapter;

import com.example.renderer.legacy.LegacyRenderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LegacyRendererAdapterTest {
    
    @Test
    public void testAdapterForwardsCalls() {
        LegacyRenderer mockLegacy = mock(LegacyRenderer.class);
        LegacyRendererAdapter adapter = new LegacyRendererAdapter(mockLegacy);
        
        adapter.drawCircle(100, 100, 50);
        verify(mockLegacy).drawLegacyCircle(100, 100, 50);
        
        adapter.drawRectangle(50, 50, 100, 80);
        verify(mockLegacy).drawLegacyRectangle(50, 50, 100, 80);
    }

    @Test
    public void testInvalidParameters() {
        LegacyRenderer mockLegacy = mock(LegacyRenderer.class);
        LegacyRendererAdapter adapter = new LegacyRendererAdapter(mockLegacy);
        
        assertThrows(IllegalArgumentException.class,
            () -> adapter.drawCircle(0, 0, -1));
        
        verifyNoInteractions(mockLegacy);
    }

    @Test
    public void testNullLegacyRenderer() {
        assertThrows(NullPointerException.class,
            () -> new LegacyRendererAdapter(null));
    }
}
