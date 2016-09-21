/*
 * The MIT License
 *
 * Copyright 2016 500120133.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package alanutilites.util.collision;

/**
 *
 * @author Alan Tsui
 */
public class CollisionBox2D extends CollisionBox{

    public CollisionBox2D(int x, int y, int width, int height, boolean passable) {
        super(x, y, width, height, passable);
    }
    
    public CollisionBox2D(CollisionBox2D box){
        super(box);
    }
    
    public int getX2D(){
        return (int)super.getX();
    }
    
    public int getY2D(){
        return (int)super.getY();
    }
    
    public int getWidth2D(){
        return (int)super.getWidth();
    }
    
    public int getHeight2D(){
        return (int)super.getHeight();
    }
}
