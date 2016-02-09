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
package alanutilites.util.random;

import java.util.HashMap;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class UUIDGenerator {
    private final int length;
    private final HashMap<String, Integer> previousIDs;
    
    /**
     * Constructs a new UUID Generator
     * @param length  the length of the UUID (Max Length : 19)
     * EX : 
     * length : 5  = (52048)
     * length : 1  = (9)
     * length : 9  = (192756236)
     * length : 19  = (9223372036854775807)
     */
    public UUIDGenerator(final int length) {
        this.length = length;
        previousIDs = new HashMap<>();
    }
    
    /**
     * Generates an UUID
     * @return  returns a UUID that has never been used before
     */
    public long generateUDID() {
        final String id = Random.randomNumber(0, length) + "";
        if (id.length() != length) {
            return generateUDID();
        } 
        else {
            final int idNum = Integer.parseInt(id);
            if (!previousIDs.containsKey(idNum+"")) {
                previousIDs.put(idNum+"",+idNum);
                return idNum;
            } 
            else if (previousIDs.containsKey(idNum+"")) {
                return generateUDID();
            }
            else {
                return -1;
            }
        }
    }
}