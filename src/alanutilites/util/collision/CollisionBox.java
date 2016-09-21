package alanutilites.util.collision;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Alan Tsui
 */
public class CollisionBox extends Object implements Serializable{
    private static final long serialVersionUID = 1337570192837465l;
    
    /**
     * 
     * these are objects that you want to ignore
     * if an object is in the {@link ArrayList} then <code>this</code> 
     * will not collide with it and will ignore it
     */
    private ArrayList<CollisionBox> ignore;
    
    private double x;
    private double y;
    private double width;
    private double height;
    
    private boolean passable;

    public CollisionBox(double x, double y, double width, double height, boolean passable) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.passable = passable;
        ignore = new ArrayList<>();
    }
    
    public CollisionBox(CollisionBox box){
        this.x = box.x;
        this.y = box.y;
        this.width = box.width;
        this.height = box.height;
        this.passable = box.passable;
        this.ignore = new ArrayList<>(box.ignore);
    }
    
    public CollisionBox copy(){
        CollisionBox box = new CollisionBox(this);        
        return box;
    }
    
    public boolean intersects(CollisionBox box){
        return Collision.intersects(this, box);
    }
    
    public CollisionBox getClosestCollisionBox(CollisionBox[] boxes){
        return Collision.getClosestCollisionBox(this, boxes);
    }
    
    public CollisionBox getClosestCollisionBox(ArrayList<? extends CollisionBox> boxes){
        return Collision.getClosestCollisionBox(this, boxes);
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }
    
    public void add(CollisionBox box){
        ignore.add(box);
    }
    
    public void addAll(ArrayList<CollisionBox> box){
        for(int i=0;i<box.size();i++){
            ignore.add(box.get(i));
        }
    }
    
    public void remove(CollisionBox box){
        ignore.remove(box);
    }
    
    public boolean isIgnored(CollisionBox box){
        return ignore.contains(box);
    }

    public ArrayList<CollisionBox> getIgnore() {
        return ignore;
    }

    public void setIgnore(ArrayList<CollisionBox> ignore) {
        this.ignore = ignore;
    }
    
    public Point2D.Double getCenter(){
        return new Point2D.Double(getCenterX(), getCenterY());
    }
    
    public double getCenterX() {
        return getX() + getWidth() / 2.0;
    }
    
    public double getCenterY() {
        return getY() + getHeight() / 2.0;
    }
    
    public Rectangle2D.Double getDrawableShape(){
        return new Rectangle.Double(x, y, width, height);
    }
    
    @Override
    public String toString(){
        return new StringBuffer()
                .append("X - ").append(getX()).append(";")
                .append("Y - ").append(getY()).append(";")
                .append("Width - ").append(getWidth()).append(";")
                .append("Height - ").append(getHeight()).append(";")
                .toString();
    }
}
