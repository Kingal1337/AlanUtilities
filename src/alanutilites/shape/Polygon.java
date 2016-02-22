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

/**
 *
 * @author Alan Tsui
 * @version 1.0
 * @since 1.1
 */
public class Polygon {
    private double[] xpoints;
    private double[] ypoints;
    
    private static final int MIN_SIZE = 3;
    public Polygon(double[] xpoints, double[] ypoints){
        if(xpoints.length != ypoints.length){
            throw new IndexOutOfBoundsException("xpoints.length != ypoints.length");
        }
        if(xpoints.length != MIN_SIZE || ypoints.length != MIN_SIZE){
            throw new IndexOutOfBoundsException("xpoints.length != MIN_SIZE || ypoints.length != MIN_SIZE");
        }
        this.xpoints = xpoints;
        this.ypoints = ypoints;
    }
    
    
}
