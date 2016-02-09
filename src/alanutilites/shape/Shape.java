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
 */
package alanutilites.shape;

import java.awt.geom.Area;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Shape {
    
    /**
     * Do not let anyone instantiate this class.
     */
    private Shape(){}
    
    /**
     * Rotates a point certain amount of degrees
     * @param origin  the center of rotation
     * @param point  the point
     * @param degrees  the degrees(0 - 360) to rotate
     * @return  returns a rotated point
     */
    public static Point rotatePoint(Point origin, Point point, double degrees){
        point.x -= origin.x;
        point.y -= origin.y;

        double newX = point.x * Math.cos(degrees) - point.y * Math.sin(degrees);
        double newY = point.x * Math.sin(degrees) + point.y * Math.cos(degrees);

        point.x = newX + origin.x;
        point.y = newY + origin.y;
        return new Point(newX, newY);
    }
    
    /**
     * Checks if an area intersects another area
     * @param area1  the first area
     * @param area2  the second area
     * @return  if area1 intersects area2 then returns true, else returns false
     */
    public static boolean intersects(Area area1, Area area2){
        return area1.intersects(area2.getBounds2D());
    }
    
    /**
     * Checks if a rectangle intersects another rectangle
     * @param r1x  rectangle 1's x
     * @param r1y  rectangle 1's y
     * @param r1w  rectangle 1's width
     * @param r1h  rectangle 1's height
     * @param r2x  rectangle 2's x
     * @param r2y  rectangle 2's y
     * @param r2w  rectangle 2's width
     * @param r2h  rectangle 2's height
     * @return  if rectangle 1 intersects rectangle 2 then returns true, else returns false
     */
    public static boolean intersects(
            double r1x, double r1y, double r1w, double r1h,
            double r2x, double r2y, double r2w, double r2h){
        return (r1x<r2x+r2w && r1x+r1w>r2x) && (r1y<r2y+r2h && r1y+r1h>r2y);
    }
}
