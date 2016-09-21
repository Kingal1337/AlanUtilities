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
package alanutilites.util.image;

import alanutilites.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class Album {
    private String albumTitle;
    private ArrayList<Photo> photos;

    public Album(String albumTitle, ArrayList<Photo> photos) {
        this.albumTitle = albumTitle;
        this.photos = photos;
    }
    
    public Album(Album album){
        albumTitle = album.albumTitle;
        photos = album.photos;
    }
    
    public void add(Photo icon){
        photos.add(icon);
    }
    
    public void remove(ArrayList<Integer> indexes){
        Collections.sort(indexes);
        for(int i=indexes.size()-1;i>=0;i--){
            remove(indexes.get(i));
        }
    }
    
    public Photo remove(int index){
        if(Arrays.doesIndexExist(photos.toArray(), index)){
            return photos.remove(index);
        }
        return null;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
    
}
