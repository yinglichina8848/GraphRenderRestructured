package com.example.renderer.observer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShapeObservableTest {

    @Test
    void testAddAndNotifyObserver() {
        // 创建mock观察者
        ShapeObserver mockObserver = mock(ShapeObserver.class);
        
        // 创建被观察对象
        ShapeObservable observable = new ShapeObservable();
        observable.addObserver(mockObserver);
        
        // 触发通知
        observable.notifyChange();
        
        // 验证观察者方法被调用
        verify(mockObserver, times(1)).onShapeChanged();
    }

    @Test
    void testMultipleObservers() {
        ShapeObserver observer1 = mock(ShapeObserver.class);
        ShapeObserver observer2 = mock(ShapeObserver.class);
        
        ShapeObservable observable = new ShapeObservable();
        observable.addObserver(observer1);
        observable.addObserver(observer2);
        
        observable.notifyChange();
        
        verify(observer1, times(1)).onShapeChanged();
        verify(observer2, times(1)).onShapeChanged();
    }
}
