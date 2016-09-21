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

import alanutilites.util.collision.CollisionBox;
import java.awt.geom.Rectangle2D;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.1
 */
public class Line {
    private Point point1;
    private Point point2;
    
    /**
     * Sets the default endpoints to 0,0
     */
    public Line(){
        point1 = new Point(0,0);
        point2 = new Point(0,0);
    }
    
    /**
     * Preset the endpoints
     * @param point1  the first endpoint
     * @param point2  the seconds endpoint
     */
    public Line(Point point1, Point point2){
        this.point1 = point1;
        this.point2 = point2;
    }
    
    /**
     * Preset the endpoints
     * @param x1  the first endpoint x
     * @param y1  the first endpoint y
     * @param x2  the second endpoint x
     * @param y2  the second endpoint y
     */
    public Line(double x1, double y1, double x2, double y2){
        point1 = new Point(x1,y1);
        point2 = new Point(x2,y2);
    }
    
    /**
     * Copies a line 
     * @param line  the line to copy
     */
    public Line(Line line){
        point1 = new Point(line.point1);
        point2 = new Point(line.point2);
    }
    
    /**
     * Gets the midpoint
     * @return  returns the midpoint
     */
    public Point midpoint(){
        return new Point(((point1.x + point2.x)/2), ((point1.y + point2.y)/2));
    }
    
    /**
     * Gets the distance from point1 to point2
     * @return  returns the distance from point1 to point 2
     */
    public double distance(){
        return Math.sqrt(Math.pow((point2.x-point1.x),2)+Math.pow((point2.y-point1.y),2));
    }
    
    /**
     * Gets the slope of this line
     * @return  returns the slope of this line
     */
    public double slope(){
        return ((point2.y - point1.y)/(point2.x - point1.x));
    }
    
    /**
     * Gets the negative reciprocal slope of this line
     * @return  returns the negative reciprocal slope
     */
    public double negativeRecipSlope(){
        return -1/slope();
    }
    
    /**
     * Gets the distance from point1 to point2 on the x -axis
     * @return  returns the distance
     */
    public double distanceX(){
        return point2.x - point1.x;
    }
    
    /**
     * Gets the distance from point1 to point2 on the x -axis
     * @return  returns the distance
     */
    public double distanceY(){
        return point2.y - point1.y;
    }
    
    /**
     * Gets a point 
     * @param fraction  the distance 
     * @param closerTo  
     * 1 = closer to first point
     * else = closer to second point
     * @return  returns that point
     */
    public Point getPoint(double fraction, int closerTo){
        double addToX = distanceX()*fraction;
        double addToY = distanceY()*fraction;
        double newX;
        double newY;
        if(closerTo == 1){
            newX = point1.x+addToX;
            newY = point1.y+addToY;
        }
        else{
            newX = point2.x+addToX;
            newY = point2.y+addToY;
        }
        return new Point(newX, newY);
    }
    
    /**
     * Translates the line by <code>deltaX</code> and <code>deltaY</code>
     * @param deltaX  the amount to translate along the x-axis
     * @param deltaY  the amount to translate along the y-axis
     */
    public void translate(double deltaX, double deltaY){
        point1.translate(deltaX, deltaY);
        point2.translate(deltaX, deltaY);
    }
    
    /**
     * Rotates a the line a certain amount of degrees
     * @param origin  the center of rotation
     * @param degrees  the degrees(0 - 360) to rotate
     */
    public void rotate(Point origin, double degrees){
        point1.rotate(origin, degrees);
        point2.rotate(origin, degrees);
    }
    
    
    /**
     * Checks to see if this line contains the point
     * @param point  the point
     * @return  returns true if the point lies on this line
     */
    public boolean containsPoint(Point point){
        return containsPoint(point.x,point.y);
    }
    
    /**
     * Checks to see if this line contains the point
     * @param x  the x value
     * @param y  the y value
     * @return  returns true if the point lies on this line
     */
    public boolean containsPoint(double x, double y){
        double slope = slope();
        return y-point1.y == slope*(x-point1.x);
    }
    
    /**
     * Checks to see if a rectangle is intersecting this line
     * @param r  
     * @return 
     */
    public boolean intersects(Rectangle2D r) {
        return r.intersectsLine(getX1(), getY1(), getX2(), getY2());
    }
    
    public boolean intersects(CollisionBox box){
        return intersects(new Rectangle2D.Double(box.getX(), box.getY(), box.getWidth(), box.getHeight()));
    }
    
    /**
     * if all three points are colinear then it
     * checks to see if all 3 points are on a segment
     * @param p1x  point 1's x  (part of line segment)
     * @param p1y  point 1's y  (part of line segment)
     * @param p2x  point 2's x
     * @param p2y  point 2's y  
     * @param p3x  point 3's x  (part of line segment)
     * @param p3y  point 3's y  (part of line segment)
     * @return  returns true if p2 is on line segment p1 p3, if p2 
     * is not on line segment p1 p3 then returns false
     */
    private boolean onSegment(
            double p1x, double p1y, 
            double p2x, double p2y,
            double p3x, double p3y
        ){
        return p2x <= Math.max(p1x, p3x) && 
               p2x >= Math.min(p1x, p3x) &&
               p2y <= Math.max(p1y, p3y) && 
               p2y >= Math.min(p1y, p3y);
    }
    
    /**
     * Checks the orientation of the three points
     * @param p1x  point 1's x (part of line segment)
     * @param p1y  point 1's y (part of line segment)
     * @param p2x  point 2's x (part of line segment)
     * @param p2y  point 2's y (part of line segment)
     * @param p3x  point 3's x
     * @param p3y  point 3's y
     * @return  
     * returns 0 if the points are colinear
     * returns 1 if the points are going clockwise
     * returns 2  if the points are going counter-clock wise
     */
    private int orientation(
            double p1x, double p1y,
            double p2x, double p2y,
            double p3x, double p3y
        ) {
        
        int val = (int) ((p2y - p1y) * (p3x - p2x)
                - (p2x - p1x) * (p3y - p2y));
        if (val == 0) {
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }
    
    /**
     * Checks to see if 2 lines intersect
     * @param p1x  point 1's x (line segment 1)
     * @param p1y  point 1's y (line segment 1)
     * @param p2x  point 2's x (line segment 1)
     * @param p2y  point 2's y (line segment 1)
     * @param p3x  point 3's x (line segment 2)
     * @param p3y  point 3's y (line segment 2)
     * @param p4x  point 4's x (line segment 2)
     * @param p4y  point 4's y (line segment 2)
     * @return  returns if 2 lines intersects
     */
    private boolean intersectsLine(
            double p1x, double p1y, 
            double p2x, double p2y, 
            double p3x, double p3y, 
            double p4x, double p4y
        ) {
        int o1 = orientation(p1x, p1y, p2x, p2y, p3x, p3y);
        int o2 = orientation(p1x, p1y, p2x, p2y, p4x, p4y);
        int o3 = orientation(p3x, p3y, p4x, p4y, p1x, p1y);
        int o4 = orientation(p3x, p3y, p4x, p4y, p2x, p2y);
        
        if (o1 != o2 && o3 != o4) {
            return true;
        }
        if (o1 == 0 && onSegment(p1x, p1y, p3x, p3y, p2x, p2y)) {
            return true;
        }
        if (o2 == 0 && onSegment(p1x, p1y, p4x, p4y, p2x, p2y)) {
            return true;
        }
        if (o3 == 0 && onSegment(p3x, p3y, p1x, p1y, p4x, p4y)) {
            return true;
        }
        if (o4 == 0 && onSegment(p3x, p3y, p2x, p2y, p4x, p4y)) {
            return true;
        }
        return false;
    }
    
    /**
     * Gets a point a certain distance from another point
     * @param from  the initial point
     * @param distance  the distance to travel
     * @return  returns a new point
     */
    public Point getPointFromPoint(Point from, double distance){
        double lineDistance = distance();
        return new Point(
                from.x+((distance/lineDistance)*(point2.x-point1.x)),
                from.y+((distance/lineDistance)*(point2.y-point1.y)));
    }
    
    /**
     * Tests to see if this line intersects the line ((x1, y1) (x2,y2))
     * @param x1  x of point1
     * @param y1  y of point1
     * @param x2  x of point2
     * @param y2  y of point2
     * @return  returns true if this line intersects the other line
     */
    public boolean intersectsLine(double x1, double y1, double x2, double y2) {
        return intersectsLine(x1, y1, x2, y2, getX1(), getY1(), getX2(), getY2());
    }
    
    /**
     * Tests to see if this line intersects line
     * @param line  the other line to see if they intersect
     * @return  returns true if this line intersects line
     */
    public boolean intersectsLine(Line line) {
        return intersectsLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }
    
    /**
     * Tests to see if this line intersects line
     * @param point1  the 1st point of the line segment
     * @param point2  the 2nd point of the line segment
     * @return  returns true if this line intersects line
     */
    public boolean intersectsLine(Point point1, Point point2){
        return intersectsLine(point1.x, point1.y, point2.x, point2.y);
    }
    
    public void setLine(double x1, double y1, double x2, double y2){
        point1.setLocation(x1, y1);
        point2.setLocation(x2, y2);
    }
    
    public void setLine(Point point1, Point point2){
        setLine(point1.x, point1.y, point2.x, point2.y);
    }
    
    public void setLine(Line line){
        setLine(line.getStartPoint(), line.getEndPoint());
    }
    
    public void setStartPoint(double x, double y){
        setX1(x);
        setY1(y);
    }
    
    public void setEndPoint(double x, double y){
        setX2(x);
        setY2(y);
    }
    
    public void setStartPoint(Point point1){
        this.point1 = point1;
    }
    
    public Point getStartPoint(){
        return point1;
    }
    
    public void setEndPoint(Point point2){
        this.point2 = point2;
    }
    
    public Point getEndPoint(){
        return point2;
    }
    
    public void setX1(double x1){
        point1.x = x1;
    }
    
    public double getX1(){
        return point1.x;
    }
    
    public void setY1(double y1){
        point1.y = y1;
    }
    public double getY1(){
        return point1.y;
    }
    
    public void setX2(double x2){
        point2.x = x2;
    }
    
    public double getX2(){
        return point2.x;
    }
    
    public void setY2(double y2){
        point2.y = y2;
    }
    
    public double getY2(){
        return point2.y;
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("Endpoints [").append(point1).append(", ").append(point2).append("]").append(", ");
        builder.append("Midpoint(").append(midpoint()).append(")").append(", ");
        builder.append("Distance[").append(distance()).append("]").append(", ");
        builder.append("Slope[").append(slope()).append("]").append(", ");
        builder.append("Negative Reciporical of Slope[").append(negativeRecipSlope()).append("]").append(", ");
        builder.append("]");
        return builder.toString();
    }
}
