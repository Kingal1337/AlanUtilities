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
package alanutilites.util.collision;

import alanutilites.shape.Line;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * 
 * @author Alan Tsui
 */
public class Collision {
    
    /**
     * Checks to see if a line is inside a specific area
     * @param x  line1's start X point
     * @param y  line1's start Y point
     * @param x2  line1's end X point
     * @param y2  line1's end Y point
     * @param area  the area that needs to check if the line is in this area
     * @return  returns true if line is in area
     */
    public static boolean isLineInPolygon(int x,int y,int x2, int y2,Area area) {
        ArrayList<Point> allPoints = new ArrayList<>();
        int w = x2 - x;
        int h = y2 - y;
        double m = h / (double) w;
        double j = y;
        for (int i = x; i <= x2; i++) {
            allPoints.add(new Point(i, (int) j));
            j += m;
        }
        System.out.println(allPoints.size());
        boolean isPointInPolygon = false;
        for(Point point : allPoints){
            if(area.contains(point)){
                isPointInPolygon = true;
                break;
            }
            else{
                isPointInPolygon = false;                
            }
        }
        return isPointInPolygon;
    }
    
    public static boolean intersects(Area area1,Area area2){
        return area1.intersects(area2.getBounds2D());
    }
    
    public static boolean intersects(Ellipse2D circle1,Ellipse2D circle2){
        return intersects(new Area(circle1),new Area(circle2));
    }
    
    /**
     * Sees if CollisionBox 1 is touching CollisionBox 2
     * @param r1  CollisionBox 1
     * @param r2  CollisionBox 2
     * @return  returns true if the two CollisionBox's are touching else returns false
     */
    public static boolean intersects(CollisionBox r1,CollisionBox r2){
        boolean isTouching = false;
        if((r1.getX()<r2.getX()+r2.getWidth() && r1.getX()+r1.getWidth()>r2.getX()) && (r1.getY()<r2.getY()+r2.getHeight() && r1.getY()+r1.getHeight()>r2.getY())){
            isTouching = true;
        }
        return isTouching;
    }
    
    public static boolean intersects(CollisionBox r1, ArrayList<CollisionBox> entitys){
        boolean isTouching = false;
        for (CollisionBox entity : entitys) {
            isTouching = intersects(r1, entity);
            if(isTouching){
               break; 
            }
        }
        return isTouching;
    } 
    
    public static boolean intersects(
            int r1x,int r1y, int r1w, int r1h, 
            int r2x, int r2y, int r2w, int r2h){
        boolean isTouching = false;
        if((r1x<r2x+r2w && r1x+r1w>r2x) && (r1y<r2y+r2h && r1y+r1h>r2y)){
            isTouching = true;
        }
        return isTouching;
    }
    
    public static boolean intersects(
            double r1x, double r1y, double r1w, double r1h, 
            double r2x, double r2y, double r2w, double r2h){
        boolean isTouching = false;
        if((r1x<r2x+r2w && r1x+r1w>r2x) && (r1y<r2y+r2h && r1y+r1h>r2y)){
            isTouching = true;
        }
        return isTouching;
    }
    
    public static boolean move(int direction,CollisionBox r1,ArrayList<? extends CollisionBox> rr2, double speed){
        boolean alreadyColliding = false;
//        if(!rr2.isEmpty()){
//            if(rr2.get(0) instanceof CollisionBox){
                CollisionBox rectangle = null;
                for(int i=0;i<rr2.size();i++){
                    if(alreadyColliding){
                        break;
                    }
                    CollisionBox currentBox = rr2.get(i);
                    if(!r1.getIgnore().contains(currentBox)){
                        if(direction == 3){
                            if(willCollide(r1,currentBox,3,speed)){ 
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                        if(direction == 1){
                            if(willCollide(r1,currentBox,1,speed)){ 
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                        if(direction == 4){
                            if(willCollide(r1,currentBox,4,speed)){
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                        if(direction == 2){
                            if(willCollide(r1,currentBox,2,speed)){  
                                alreadyColliding = true;
                                rectangle = currentBox;
                            }
                        }
                    }
                }

                if(direction == 3){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,3,speed)){ 
                            double something = rectangle.getY()+rectangle.getHeight()-r1.getY();
        //                    System.out.println(something);
                            if(something <= 0){
                                r1.setY(r1.getY()+something);
                            }
                            else{
                                r1.setY(r1.getY()-speed);
                            }
                        }
                    }
                    else{
                        r1.setY(r1.getY()-speed);
                    }
                }

                if(direction == 1){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,1,speed)){ 
                            double something = r1.getY()+r1.getHeight()-rectangle.getY();
        //                    System.out.println(something);
                            if(something <= 0){
                                r1.setY(r1.getY()-something);
                            }     
                        }
                        else{       
                            r1.setY(r1.getY()+speed);
                        }
                    }
                    else{       
                        r1.setY(r1.getY()+speed);
                    }
                }

                if(direction == 4){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,4,speed)){
                            double something = rectangle.getX()+rectangle.getWidth()-r1.getX();
        //                    System.out.println(something);
                            if(something <= 0){
                                r1.setX(r1.getX()+something);
                            }
                        }
                        else{
                            r1.setX(r1.getX()-speed);
                        }
                    }
                    else{
                        r1.setX(r1.getX()-speed);
                    }
                }

                if(direction == 2){
                    if(rectangle != null){
                        if(willCollide(r1,rectangle,2,speed)){  
                            double something = r1.getX()+r1.getWidth()-rectangle.getX();
        //                    System.out.println(something);
                            if(something <= 0){
                                r1.setX(r1.getX()-something);
                            }
                            else{
                                r1.setX(r1.getX()+speed);
                            }
                        }
                    }
                    else{
                        r1.setX(r1.getX()+speed);
                    }
                }
//            }
//        }
        return alreadyColliding;
    }
    
    /**
     * Will move the CollisionBox and do collision
     * @param direction  the direction the entity is going in
     * 1 = down; 
     * 2 = right; 
     * 3 = up; 
     * 4 = left;
     * 
     * @param r1  CollisionBox 1
     * @param rr2  all entitys that need to be checked
     * @param speed  the speed that entity 1 is going in
     * @return  returns true if <code>r1</code> is colliding with another CollisionBox
     */
    public static boolean move(int direction,CollisionBox r1,CollisionBox[] rr2, int speed){
        boolean alreadyColliding = false;
        CollisionBox rectangle = null;
        for(CollisionBox r2 : rr2){
            if(alreadyColliding){
                break;
            }
            if(direction == 3){
                if(willCollide(r1,r2,3,speed)){ 
                    alreadyColliding = true;
                    rectangle = r2;
                }
            }
            if(direction == 1){
                if(willCollide(r1,r2,1,speed)){ 
                    alreadyColliding = true;
                    rectangle = r2;
                }
            }
            if(direction == 4){
                if(willCollide(r1,r2,4,speed)){
                    alreadyColliding = true;
                    rectangle = r2;
                }
            }
            if(direction == 2){
                if(willCollide(r1,r2,2,speed)){  
                    alreadyColliding = true;
                    rectangle = r2;
                }
            }            
        }
        
        if(direction == 3){
            if(rectangle != null){
                if(willCollide(r1,rectangle,3,speed)){ 
                    double something = rectangle.getY()+rectangle.getHeight()-r1.getY();
//                    System.out.println(something);
                    if(something <= 0){
                        r1.setY(r1.getY()+something);
                    }
                    else{
                        r1.setY(r1.getY()-speed);
                    }
                }
            }
            else{
                r1.setY(r1.getY()-speed);
            }
        }
        
        if(direction == 1){
            if(rectangle != null){
                if(willCollide(r1,rectangle,1,speed)){ 
                    double something = r1.getY()+r1.getHeight()-rectangle.getY();
//                    System.out.println(something);
                    if(something <= 0){
                        r1.setY(r1.getY()-something);
                    }     
                }
                else{       
                    r1.setY(r1.getY()+speed);
                }
            }
            else{       
                r1.setY(r1.getY()+speed);
            }
        }
        
        if(direction == 4){
            if(rectangle != null){
                if(willCollide(r1,rectangle,4,speed)){
                    double something = rectangle.getX()+rectangle.getWidth()-r1.getX();
//                    System.out.println(something);
                    if(something <= 0){
                        r1.setX(r1.getX()+something);
                    }
                }
                else{
                    r1.setX(r1.getX()-speed);
                }
            }
            else{
                r1.setX(r1.getX()-speed);
            }
        }
        
        if(direction == 2){
            if(rectangle != null){
                if(willCollide(r1,rectangle,2,speed)){  
                    double something = r1.getX()+r1.getWidth()-rectangle.getX();
//                    System.out.println(something);
                    if(something <= 0){
                        r1.setX(r1.getX()-something);
                    }
                    else{
                        r1.setX(r1.getX()+speed);
                    }
                }
            }
            else{
                r1.setX(r1.getX()+speed);
            }
        }
        return alreadyColliding;
    }
    
    /**
     * Sees if the entity will collide on the next move
     * @param r1 CollisionBox 1
     * @param r2 CollisionBox 2
     * @param direction direction rect1 is coming from; 
     * 1 = above; 
     * 2 = left; 
     * 3 = below; 
     * 4 = right;
     * @param speed  speed of CollisionBox 1
     * @return if cant move returns true else return false
     */
    public static boolean willCollide(CollisionBox r1, CollisionBox r2, int direction, double speed) {
        if(!r2.isPassable()){
            int trueHorizontalDirection = getDirectionHorizontal(r1, r2);
            int trueVerticalDirection = getDirectionVertical(r1, r2);
            if(direction == 1 && trueVerticalDirection == 1){
                if((r1.getX()+r1.getWidth() > r2.getX()) && (r1.getX() < r2.getX()+r2.getWidth())){
                    if(r1.getY()+r1.getHeight()+speed > r2.getY()){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else if(direction == 2 && trueHorizontalDirection == 2){
                if((r1.getY()+r1.getHeight() > r2.getY()) && (r1.getY() < r2.getY()+r2.getHeight())){
                    if(r1.getX()+r1.getWidth()+speed > r2.getX()){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else if(direction == 3 && trueVerticalDirection == 3){
                if((r1.getX()+r1.getWidth() > r2.getX()) && (r1.getX() < r2.getX()+r2.getWidth())){
    //                System.out.println((r1.getY()-speed)+" "+(r2.getY()+r2.getHeight()));
                    if((r1.getY()-speed) < (r2.getY()+r2.getHeight())){
                        return true;
                    }
                    else{
                        return false;
                    }  
                }
                else{
                    return false;
                }
            }
            else if(direction == 4 && trueHorizontalDirection == 4){
                if((r1.getY()+r1.getHeight() > r2.getY()) && (r1.getY() < r2.getY()+r2.getHeight())){
                    if(r1.getX()-speed < r2.getX()+r2.getWidth()){
                        return true;
                    }
                    else{
                        return false;
                    }       
                }
                else{
                    return false;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }
    
    private static int getDirectionHorizontal(CollisionBox rect1,CollisionBox rect2){
        double rect1CenterX = rect1.getCenterX();
        double rect2CenterX = rect2.getCenterX();
        if(rect1CenterX < rect2CenterX){
            return 2;
        }
        if(rect1CenterX > rect2CenterX){
            return 4;
        }
        
        else{
            return 0;
        }
    }
    
    private static int getDirectionVertical(CollisionBox rect1,CollisionBox rect2){
        double rect1CenterY = rect1.getCenterY();
        double rect2CenterY = rect2.getCenterY();
        if(rect1CenterY < rect2CenterY){
            return 1;
        }
        if(rect1CenterY > rect2CenterY){
            return 3;
        }
        
        else{
            return 0;
        }
    }
    
    /**
     * Finds the closest CollisionBox from <code>box</code>
     * @param box  the box
     * @param boxes  the boxes to compare <code>box</code> to
     * @return  returns the closest CollisionBox, if the array <code>boxes</code>
     *          is empty then returns null
     */
    public static CollisionBox getClosestCollisionBox(CollisionBox box, CollisionBox[] boxes){
        if(box == null || boxes == null || boxes.length == 0){
            return null;
        }
        CollisionBox closest = boxes[0];
        Line line = new Line(box.getCenterX(), box.getCenterY(), closest.getCenterX(), closest.getCenterY());
        double currentClosestLineDistance = line.distance();
        for(int i=1;i<boxes.length;i++){
            CollisionBox theBox = boxes[i];
            line.setEndPoint(theBox.getCenterX(), theBox.getCenterY());
            double compareDistance = line.distance();
            if(currentClosestLineDistance > compareDistance){
                closest = theBox;
                currentClosestLineDistance = compareDistance;
            }
        }
        return closest;
    }
    
    /**
     * Finds the closest CollisionBox from <code>box</code>
     * @param box  the box
     * @param boxes  the boxes to compare <code>box</code> to
     * @return  returns the closest CollisionBox, if the array <code>boxes</code>
     *          is empty then returns null
     */
    public static CollisionBox getClosestCollisionBox(CollisionBox box, ArrayList<? extends CollisionBox> boxes){
        if(box == null || boxes == null || boxes.isEmpty()){
            return null;
        }
        CollisionBox closest = boxes.get(0);
        Line line = new Line(box.getCenterX(), box.getCenterY(), closest.getCenterX(), closest.getCenterY());
        double currentClosestLineDistance = line.distance();
        for(int i=1;i<boxes.size();i++){
            CollisionBox theBox = boxes.get(i);
            line.setEndPoint(theBox.getCenterX(), theBox.getCenterY());
            double compareDistance = line.distance();
            if(currentClosestLineDistance > compareDistance){
                closest = theBox;
                currentClosestLineDistance = compareDistance;
            }
        }
        return closest;
    }
}

