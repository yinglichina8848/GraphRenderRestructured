package com.example.renderer.bridge;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.Rectangle;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class MockGraphics2D extends Graphics2D {
    // 记录绘制调用
    public String lastDrawCall = "";
    public int drawCallCount = 0;
    public Color lastColorSet = null;
    public Stroke lastStrokeSet = null;
    
    @Override
    public void draw(Shape s) {
        lastDrawCall = "draw:" + s.getClass().getSimpleName();
        drawCallCount++;
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        lastDrawCall = "drawImage:" + img;
        return true;
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        lastDrawCall = "drawImage:" + img;
    }

    @Override
    public void drawString(String str, int x, int y) {
        lastDrawCall = "drawString:" + str;
    }

    @Override
    public void drawString(String str, float x, float y) {
        lastDrawCall = "drawStringFloat:" + str;
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        lastDrawCall = "drawString:attributed";
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        lastDrawCall = "drawString:attributedFloat";
    }

    @Override
    public void drawGlyphVector(java.awt.font.GlyphVector g, float x, float y) {
        lastDrawCall = "drawGlyphVector";
    }

    @Override
    public void fill(Shape s) {
        lastDrawCall = "fill:" + s.getClass().getSimpleName();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Font getFont() {
        return new Font("Arial", Font.PLAIN, 12);
    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return new Canvas().getFontMetrics(f);
    }

    @Override
    public void setColor(Color c) {
        lastColorSet = c;
    }

    @Override
    public void setFont(Font font) {
        // no-op for testing
    }

    @Override
    public void setPaintMode() {
        // no-op for testing
    }

    @Override
    public void setXORMode(Color c1) {
        // no-op for testing
    }

    @Override
    public void translate(int x, int y) {
        // no-op for testing
    }

    @Override
    public void translate(double tx, double ty) {
        // no-op for testing
    }

    @Override
    public void rotate(double theta) {
        // no-op for testing
    }

    @Override
    public void rotate(double theta, double x, double y) {
        // no-op for testing
    }

    @Override
    public void scale(double sx, double sy) {
        // no-op for testing
    }

    @Override
    public void shear(double shx, double shy) {
        // no-op for testing
    }

    @Override
    public void transform(AffineTransform Tx) {
        // no-op for testing
    }

    @Override
    public void setTransform(AffineTransform Tx) {
        // no-op for testing
    }

    @Override
    public AffineTransform getTransform() {
        return new AffineTransform();
    }

    @Override
    public Paint getPaint() {
        return getColor();
    }

    @Override
    public Composite getComposite() {
        return AlphaComposite.SrcOver;
    }

    @Override
    public void setPaint(Paint paint) {
        // no-op for testing
    }

    @Override
    public void setComposite(Composite comp) {
        // no-op for testing
    }

    @Override
    public void setBackground(Color color) {
        // no-op for testing
    }

    @Override
    public Color getBackground() {
        return Color.WHITE;
    }

    @Override
    public Stroke getStroke() {
        return new BasicStroke(1);
    }

    @Override
    public void setStroke(Stroke s) {
        lastStrokeSet = s;
    }

    @Override
    public void clip(Shape s) {
        // no-op for testing
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return new FontRenderContext(null, false, false);
    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return false; // 简单实现，测试中不需要实际命中检测
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();
    }

    @Override
    public Graphics create() {
        return this;
    }

    @Override
    public Graphics create(int x, int y, int width, int height) {
        return this;
    }

    @Override
    public void dispose() {
        // no-op for testing
    }

    // 其他必要的方法实现...
    @Override
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {}
    
    @Override
    public Object getRenderingHint(RenderingHints.Key hintKey) { return null; }
    
    @Override
    public void setRenderingHints(Map<?,?> hints) {}
    
    @Override
    public void addRenderingHints(Map<?,?> hints) {}
    
    @Override
    public RenderingHints getRenderingHints() { return null; }
    
    @Override
    public void clipRect(int x, int y, int width, int height) {}
    
    @Override
    public void setClip(int x, int y, int width, int height) {}
    
    @Override
    public void setClip(Shape clip) {}
    
    @Override
    public Shape getClip() { return null; }
    
    @Override
    public Rectangle getClipBounds() { return null; }
    
    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {}
    
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {}
    
    @Override
    public void fillRect(int x, int y, int width, int height) {}
    
    @Override
    public void clearRect(int x, int y, int width, int height) {}
    
    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
    
    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
    
    @Override
    public void drawOval(int x, int y, int width, int height) {
        lastDrawCall = "drawOval:" + x + "," + y + "," + width + "," + height;
        drawCallCount++;
    }
    
    @Override
    public void fillOval(int x, int y, int width, int height) {}
    
    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
    
    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
    
    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {}
    
    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        lastDrawCall = "drawPolygon:points=" + nPoints;
        drawCallCount++;
    }
    
    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
    
    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) { return true; }
    
    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) { return true; }
    
    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) { return true; }
    
    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) { return true; }
    
    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) { return true; }
    
    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) { return true; }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        lastDrawCall = "drawRenderableImage";
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        lastDrawCall = "drawRenderedImage";
    }
}
