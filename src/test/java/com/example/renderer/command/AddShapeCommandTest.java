package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddShapeCommandTest {

    @Test
    public void testExecuteAddsShape() {
        List<Shape> shapes = new ArrayList<>();
        Shape mockShape = mock(Shape.class);
        AddShapeCommand cmd = new AddShapeCommand(shapes, mockShape);
        
        cmd.execute();
        
        assertEquals(1, shapes.size());
        assertTrue(shapes.contains(mockShape));
    }

    @Test
    public void testUndoRemovesShape() {
        List<Shape> shapes = new ArrayList<>();
        Shape mockShape = mock(Shape.class);
        AddShapeCommand cmd = new AddShapeCommand(shapes, mockShape);
        
        cmd.execute();
        cmd.undo();
        
        assertTrue(shapes.isEmpty());
    }
}
