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
package alanutilites.util.random;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class UUIDGenerator {
    private final String DEFAULT_VALID_CHARACTERS = "0123456789abcdef";
    private String validCharacters; 
    private final HashMap<String, String> previousIDs;
    private BigInteger numberOfCombinations;
    private BigInteger counter;
    
    /**
     * Constructs a new UUID Generator
     */
    public UUIDGenerator() {
        previousIDs = new HashMap<>();
        validCharacters = DEFAULT_VALID_CHARACTERS.replaceAll("(.)\\1{1,}", "$1");
        numberOfCombinations = BigInteger.valueOf(validCharacters.length());
        numberOfCombinations = numberOfCombinations.pow(32);
        counter = BigInteger.ZERO;
    }
    
    /**
     * Constructs a new UUID Generator
     * @param characters  the character set for the generator
     */
    public UUIDGenerator(String characters) {
        previousIDs = new HashMap<>();
        validCharacters = characters.replaceAll("(.)\\1{1,}", "$1");
        numberOfCombinations = BigInteger.valueOf(validCharacters.length());
        numberOfCombinations = numberOfCombinations.pow(32);
        counter = BigInteger.ZERO;
    }
    
    /**
     * Generates an UUID
     * @return  returns a UUID that has never been used before
     * if all possible UUID's are used up will return null
     */
    public String generateUDID() {
        if(counter == numberOfCombinations){
            return null;
        }
        else{
            StringBuilder builder = new StringBuilder();
            builder.append(Random.randomString(validCharacters, 8));
            builder.append("-");
            builder.append(Random.randomString(validCharacters, 4));
            builder.append("-");
            builder.append(Random.randomString(validCharacters, 4));
            builder.append("-");
            builder.append(Random.randomString(validCharacters, 4));
            builder.append("-");
            builder.append(Random.randomString(validCharacters, 12));
            String string = builder.toString();
            if(previousIDs.containsKey(string)){
                return generateUDID();
            }
            else{
                counter = counter.add(BigInteger.ONE);
                previousIDs.put(string, string);
                return builder.toString();
            }
        }
    }
}
