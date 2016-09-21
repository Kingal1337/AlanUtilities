/*
 * The MIT License
 *
 * Copyright 2016 Alan Tsui.
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

package alanutilites.util;

import alanutilites.util.random.Random;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author Alan Tsui
 */
public class MarkovChainGenerator {    
    private HashMap<String, ArrayList<String>> chain; 
    public MarkovChainGenerator(){
        chain = new HashMap<>();
        chain.put("_start_", new ArrayList<>());
        chain.put("_end_", new ArrayList<>());
    }
    
    public void addWords(String words){
        String[] sentences = words.split("(?<=\\.)");
        for(String s : sentences){
            addWords(s.trim().split(" "));
        }
    }
    
    public void addWords(String[] words){
        for(int i=0;i<words.length;i++){
            if (i == 0){
                ArrayList<String> startWords = chain.get("_start_");
                startWords.add(words[i]);

                ArrayList<String> array = chain.get(words[i]);
                if(array == null){
                    array = new ArrayList<>();
                    array.add(words[i+1]);
                    chain.put(words[i], array);
                }
            }
            else if (i == words.length-1){
                ArrayList<String> endWords = chain.get("_end_");
                endWords.add(words[i]);
            }
            else{	
                ArrayList<String> array = chain.get(words[i]);
                if (array == null) {
                    array = new ArrayList<>();
                    array.add(words[i+1]);
                    chain.put(words[i], array);
                } else {
                    array.add(words[i+1]);
                    chain.put(words[i], array);
                }
            }
        }
    }
    
    public String generatorSentence(){
        ArrayList<String> sentence = new ArrayList<>();
        
        String nextWord;

        ArrayList<String> startWords = chain.get("_start_");
        int startWordsLength = startWords.size();
        nextWord = startWords.get(Random.randomNumber(startWordsLength-1));
        sentence.add(nextWord);
        
        while (nextWord.isEmpty() || nextWord.charAt(nextWord.length()-1) != '.') {
            ArrayList<String> wordSelection = chain.get(nextWord);
            int wordSelectionLength = wordSelection.size();
            nextWord = wordSelection.get(Random.randomNumber(wordSelectionLength-1));
            sentence.add(nextWord);
        }
        
        String sentenceString = "";
        
        for(int i=0;i<sentence.size();i++){
            sentenceString += sentence.get(i);
            if(i < sentence.size()-1){
                sentenceString+=" ";
            }
        }
        return sentenceString;
        
    }
    
}
