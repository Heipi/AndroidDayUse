package com.fight.light.uncache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * LruCache base implement
 */
public class UseLruCache  {

    private LruCache<String,Bitmap> mLruCache;
    public UseLruCache() {
        initSize();
    }

    private void initSize(){
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/8;
      mLruCache = new LruCache<String,Bitmap>(cacheSize){
          @Override
          protected int sizeOf(String key, Bitmap value) {
              return value.getRowBytes()* value.getHeight()/1024; //转为kb
          }
      };
    }
    // 把Bitmap对象加入到缓存中
    public void addBitmapToMemory(String key,Bitmap bitmap){
    if (getBitmapFromCache(key) == null){
        mLruCache.put(key,bitmap);
    }
    }
    // 从缓存中得到Bitmap对象
    public Bitmap getBitmapFromCache(String key){
       return mLruCache.get(key);
    }
    //从缓存中删除指定的Bitmap
    public void removeBitmapFromCache(String key){
        mLruCache.remove(key);
    }
}
