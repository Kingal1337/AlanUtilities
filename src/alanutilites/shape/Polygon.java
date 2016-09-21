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

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Alan Tsui
 * @version 1.0
 * @since 1.1
 */
public class Polygon implements Shape, Transformable{
    public static void main(String[] args) {
//        double[] x = new double[]{4,4,0,14};
//        double[] y = new double[]{4,9,0,4};
//        double[] x = new double[]{4,9,11,2};
//        double[] y = new double[]{10,7,2,2};
        double[] x = new double[]{0,10,10,0};
        double[] y = new double[]{0,0,10,10};
        Polygon poly = new Polygon(x, y);
        poly.rotate(46);
        System.out.println(poly);
        Rectangle rectangle = new Rectangle(0,0,10,10);
        rectangle.rotate(46);
        System.out.println(rectangle);
    }
    private ArrayList<Double> xpoints;
    private ArrayList<Double> ypoints;
    
    private static final int MIN_SIZE = 3;
    
    public Polygon(){
        this(new double[3], new double[3]);
    }
    
    /**
     * Constructs a polygon using x and y points
     * Note : place x and y coordinates in a clock-wise position
     * @param xpoints  the x points
     * @param ypoints  the y points
     */
    public Polygon(double[] xpoints, double[] ypoints){
        if(xpoints.length != ypoints.length){
            throw new IndexOutOfBoundsException("xpoints.length != ypoints.length");
        }
        if(xpoints.length < MIN_SIZE || ypoints.length < MIN_SIZE){
            throw new IndexOutOfBoundsException("xpoints.length < MIN_SIZE || ypoints.length < MIN_SIZE");
        }
        this.xpoints = new ArrayList<>(xpoints.length);
        for(double d : xpoints){
            this.xpoints.add(d);
        }
        this.ypoints = new ArrayList<>(ypoints.length);
        for(double d : ypoints){
            this.ypoints.add(d);
        }
    }
    
    public Polygon(ArrayList<Double> xpoints, ArrayList<Double> ypoints){
        if(xpoints.size() != ypoints.size()){
            throw new IndexOutOfBoundsException("xpoints.size() != ypoints.size()");
        }
        if(xpoints.size() < MIN_SIZE || ypoints.size() < MIN_SIZE){
            throw new IndexOutOfBoundsException("xpoints.size() < MIN_SIZE || ypoints.size() < MIN_SIZE");
        }
        this.xpoints = xpoints;
        this.ypoints = ypoints;
    }
    
    public Polygon(ArrayList<Point> points){
        if(points.size() < MIN_SIZE){
            throw new IndexOutOfBoundsException("points.size() < MIN_SIZE");
        }
        xpoints = new ArrayList<>(points.size());
        ypoints = new ArrayList<>(points.size());
        for(Point point : points){
            xpoints.add(point.x);
            ypoints.add(point.y);
        }
    }
    
    public Polygon(Point[] points){
        if(points.length < MIN_SIZE){
            throw new IndexOutOfBoundsException("points.length < MIN_SIZE");
        }
        xpoints = new ArrayList<>(points.length);
        ypoints = new ArrayList<>(points.length);
        for(Point point : points){
            xpoints.add(point.x);
            ypoints.add(point.y);
        }
    }
    
    public Polygon(java.awt.Point[] points){
        if(points.length < MIN_SIZE){
            throw new IndexOutOfBoundsException("points.length < MIN_SIZE");
        }
        xpoints = new ArrayList<>(points.length);
        ypoints = new ArrayList<>(points.length);
        for(java.awt.Point point : points){
            xpoints.add((double)point.x);
            ypoints.add((double)point.y);
        }
    }
    
    public Point getTopMostPoint(){
        int index = 0;
        for(int i=0;i<ypoints.size();i++){
            if(ypoints.get(i) <= ypoints.get(index)){
                index = i;
            }
        }
        return new Point(xpoints.get(index), ypoints.get(index));
    }
    
    public Point getBottomMostPoint(){
        int index = 0;
        for(int i=0;i<ypoints.size();i++){
            if(ypoints.get(i) >= ypoints.get(index)){
                index = i;
            }
        }
        return new Point(xpoints.get(index), ypoints.get(index));
    }
    
    public Point getLeftMostPoint(){
        int index = 0;
        for(int i=0;i<xpoints.size();i++){
            if(xpoints.get(i) <= xpoints.get(index)){
                index = i;
            }
        }
        return new Point(xpoints.get(index), xpoints.get(index));
    }
    
    public Point getRightMostPoint(){
        int index = 0;
        for(int i=0;i<xpoints.size();i++){
            if(xpoints.get(i) >= xpoints.get(index)){
                index = i;
            }
        }
        return new Point(xpoints.get(index), xpoints.get(index));
    }
    
    public Rectangle2D.Double getBounds(){
        double x = getLeftMostPoint().x;
        double y = getTopMostPoint().y;
        double w = getRightMostPoint().x-getLeftMostPoint().x;
        double h = getBottomMostPoint().y-getTopMostPoint().y;
        return new Rectangle2D.Double(x,y,w,h);
    }
    
    /**
     * Gets the direction of the vertices
     * either counter-clockwise or clockwise
     * @return  returns 0 for clockwise and 1 for counter-clockwise
     * 
     */
    public int getDirectionOfVertices(){
        double total = 0;
        int j=1;
        for(int i=0;i<xpoints.size();i++){
            if(j>=xpoints.size()){
                j=0;
            }
            total+=((xpoints.get(i)*ypoints.get(j))-(ypoints.get(i)*xpoints.get(j)));
            j++;
        }
        if(total/2 > 0){
            return 0;
        }
        else if(total/2 < 0){
            return 1;
        }
        else{
            return -1;
        }
        
    }

    @Override
    public synchronized double getArea() {
        double total = 0;
        int j=1;
        for(int i=0;i<xpoints.size();i++){
            if(j>=xpoints.size()){
                j=0;
            }
            total+=((xpoints.get(i)*ypoints.get(j))-(ypoints.get(i)*xpoints.get(j)));
            j++;
        }
        return Math.abs(total/2);
    }

    @Override
    public synchronized  double getPerimeter() {
        Line line = new Line();
        double total = 0;
        int j=1;
        for(int i=0;i<xpoints.size();i++){
            if(j>=xpoints.size()){
                j=0;
            }
            line.setLine(xpoints.get(i), ypoints.get(i), xpoints.get(j), ypoints.get(j));
            total+=line.distance();
            j++;
        }
        return total;
    }

    @Override
    public synchronized boolean contains(double x, double y) {
        boolean result = false;
        int j=1;
        for (int i = 0;i<xpoints.size();i++) {
            if(j>=xpoints.size()){
                j=0;
            }
            if ((ypoints.get(i) > y) != (ypoints.get(j) > y)
                    && (x < (xpoints.get(j) - xpoints.get(i)) * (y - ypoints.get(i)) / (ypoints.get(j) - ypoints.get(i)) + xpoints.get(i))) {
                result = !result;
            }
            j++;
        }
        return result;
    }

    @Override
    public synchronized Point getCenter() {
        double x = 0;
        double y = 0;
        int j=1;
        for(int i=0;i<xpoints.size();i++){
            if(j>=xpoints.size()){
                j=0;
            }
            x+=(xpoints.get(i)+xpoints.get(j))*(xpoints.get(i)*ypoints.get(j)-xpoints.get(j)*ypoints.get(i));
            y+=(ypoints.get(i)+ypoints.get(j))*(xpoints.get(i)*ypoints.get(j)-xpoints.get(j)*ypoints.get(i));
            j++;
        }
        double area = getArea();
        int direction = getDirectionOfVertices();
        area = direction == 0 ? area : -area;
        x = x*(1/(6*area));
        y = y*(1/(6*area));
        System.out.println("CENTER : "+x+", "+y);
        return new Point(x, y);
    }

    @Override
    public synchronized void translate(double x, double y) {
        for(int i=0;i<xpoints.size();i++){
            xpoints.set(i, xpoints.get(i) + x);
            ypoints.set(i, ypoints.get(i) + y);
        }
    }

    @Override
    public void scale(double scale) {
        for(int i=0;i<xpoints.size();i++){
            xpoints.set(i, xpoints.get(i) * scale);
            ypoints.set(i, ypoints.get(i) * scale);
        }
    }
    

    @Override
    public synchronized void translate(Point point) {
        translate(point.x, point.y);
    }

    @Override
    public synchronized void rotate(double theta) {
        rotate(theta, getCenter());
    }

    @Override
    public synchronized void rotate(double theta, double x, double y) {
        Point origin = new Point(x, y);
        rotate(theta, origin);
    }

    @Override
    public synchronized void rotate(double theta, Point origin) {
        for(int i=0;i<xpoints.size();i++){
            Point point = ShapeUtil.rotatePoint(origin, xpoints.get(i), ypoints.get(i), theta);
            xpoints.set(i, point.x);
            ypoints.set(i, point.y);
        }
    }

    public ArrayList<Double> getXpoints() {
        return xpoints;
    }

    public ArrayList<Double> getYpoints() {
        return ypoints;
    }
    
    public void setXpoints(double... x){
        if(x.length != ypoints.size()){
            throw new IndexOutOfBoundsException("x.length != ypoints.length");
        }
        if(x.length < MIN_SIZE){
            throw new IndexOutOfBoundsException("x.length < MIN_SIZE");
        }
        xpoints.clear();
        for(double d : x){
            xpoints.add(d);
        }
    }
    
    public void setXpoints(ArrayList<Double> x){
        if(x.size() != ypoints.size()){
            throw new IndexOutOfBoundsException("x.size() != ypoints.size()");
        }
        if(x.size() < MIN_SIZE){
            throw new IndexOutOfBoundsException("x.size() < MIN_SIZE");
        }
        xpoints.clear();
        for(double d : x){
            xpoints.add(d);
        }
    }
    
    public void setYpoints(double... y){
        if(xpoints.size() != y.length){
            throw new IndexOutOfBoundsException("xpoints.size() != y.length");
        }
        if(y.length < MIN_SIZE){
            throw new IndexOutOfBoundsException("y.length < MIN_SIZE");
        }
        ypoints.clear();
        for(double d : y){
            ypoints.add(d);
        }
    }
    
    public void setYPoints(ArrayList<Double> y){
        if(xpoints.size() != y.size()){
            throw new IndexOutOfBoundsException("xpoints.size() != y.size()");
        }
        if(y.size() < MIN_SIZE){
            throw new IndexOutOfBoundsException("y.size() < MIN_SIZE");
        }
        ypoints.clear();
        for(double d : y){
            ypoints.add(d);
        }
    }
    
    public void setXYPoints(double[] x, double[] y){
        if(x.length != y.length){
            throw new IndexOutOfBoundsException("x.length != y.length");
        }
        if(x.length < MIN_SIZE || y.length < MIN_SIZE){
            throw new IndexOutOfBoundsException("x.length < MIN_SIZE || y.length < MIN_SIZE");
        }
        xpoints.clear();
        for(double d : y){
            xpoints.add(d);
        }
        ypoints.clear();
        for(double d : y){
            ypoints.add(d);
        }
    }
    
    public void setXYPoints(ArrayList<Double> x, ArrayList<Double> y){
        if(x.size() != y.size()){
            throw new IndexOutOfBoundsException("x.size() != y.size()");
        }
        if(x.size() < MIN_SIZE || y.size() < MIN_SIZE){
            throw new IndexOutOfBoundsException("x.size() < MIN_SIZE || y.size() < MIN_SIZE");
        }
        xpoints.clear();
        for(double d : y){
            xpoints.add(d);
        }
        ypoints.clear();
        for(double d : y){
            ypoints.add(d);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polygon) {
            Polygon polygon = (Polygon)obj;
            return (
                    xpoints.equals(polygon.xpoints) && 
                    ypoints.equals(polygon.ypoints));
        }
        return super.equals(obj);
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.xpoints);
        hash = 37 * hash + Objects.hashCode(this.ypoints);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(getClass().getName());
        builder.append("[# of points:").append(xpoints.size()).append("; [Points:(");
        for(int i=0;i<xpoints.size();i++){
            builder.append("Point #").append(i+1)
                    .append(":(").append(xpoints.get(i))
                    .append(", ")
                    .append(ypoints.get(i))
                    .append(")");
            if(i<xpoints.size()-1){
                builder.append(", ");
            }
        }
        builder.append(")]");
        
        builder.append("Center:").append(getCenter()).append(";");
        builder.append("Area:").append(getArea()).append(";");
        builder.append("Perimeter:").append(getPerimeter()).append(";");
        
        builder.append("]");
        builder.append("]");
        return builder.toString();
    }

    @Override
    public void render(Graphics2D gd, int x, int y, double scale) {
        int[] xp = new int[xpoints.size()];
        int[] yp = new int[ypoints.size()];
        for(int i=0;i<xp.length;i++){
            xp[i] = (int)(xpoints.get(i).intValue()+x);
            yp[i] = (int)(ypoints.get(i).intValue()+y);
            scale(xp[i]);
            scale(yp[i]);
        }
        gd.drawPolygon(xp, yp, xp.length);
    }
}
