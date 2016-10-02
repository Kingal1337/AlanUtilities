/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alan Tsui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package alanutilites.shape;

/**
 *
 * @author Alan Tsui
 */
public class Rectangle extends Polygon implements Shape, Transformable{
    private double x;
    private double y;
    private double width;
    private double height;
    private double rotation;
    private Point origin;
    public Rectangle(double x, double y, double width, double height) {
        super(new double[]{x,x+width,x+width,x}, new double[]{y,y,y+height,y+height});
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rotation = 0;
    }

    @Override
    public void rotate(double theta) {
        rotate(theta, getCenter());
    }

    @Override
    public void rotate(double theta, double x, double y) {
        Point origin = new Point(x, y);
        rotate(theta, origin);
    }

    @Override
    public void rotate(double theta, Point origin) {
        super.rotate(theta, origin);
        this.origin = origin;
        theta = theta > 360 ? theta % 360 : theta;
        rotation = theta;
        rotation = rotation > 360 ? rotation % 360 : rotation;        
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        setXpoints(x,x+width,x+width,x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        setYpoints(y,y,y+height,y+height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        setXpoints(x,x+width,x+width,x);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        setYpoints(y,y,y+height,y+height);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }
    
    public void setOrigin(double x, double y){
        this.origin = new Point(x, y);
    }

    @Override
    public boolean equals(Object obj) {            
        if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj;
            return ((x == r.x) &&
                    (y == r.y) &&
                    (width == r.width) &&
                    (height == r.height));
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.width) ^ (Double.doubleToLongBits(this.width) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.height) ^ (Double.doubleToLongBits(this.height) >>> 32));
        return hash;
    }

    @Override
    public String toString() {        
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("X:").append(x).append(";");
        builder.append("Y:").append(y).append(";");
        builder.append("Width:").append(width).append(";");
        builder.append("Height:").append(height).append(";");
        
        builder.append("Center:").append(getCenter()).append(";");
        builder.append("Area:").append(getArea()).append(";");
        builder.append("Perimeter:").append(getPerimeter()).append(";");
        
        builder.append("]");
        return builder.toString();
    }
    
    
    
    
}
