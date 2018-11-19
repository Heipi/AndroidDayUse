package com.light.diskcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;

import static android.os.Environment.isExternalStorageRemovable;

public class ImageLoader {
    public static final String TAG = "ImageLoader";
    private static final long DISK_CACHE_SIZE = 1024*1024*50;//50M
    private static  final int DISK_CACHE_INDEX = 0;
    private static final int TAG_KEY_URI = 0;
    private LruCache<String,Bitmap> mLruCache;
    private DiskLruCache mDiskLruCache;
    private Context mContext;
    private boolean mIsDiskLruCacheCreated;
    public ImageLoader() {
        initSize();
    }
  public ImageLoader(Context context){
      mContext = context.getApplicationContext();
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
        File diskCacheDir =  getDiskCacheDir(mContext,"bitmap");
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE ){
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||!isExternalStorageRemovable()
                ? context.getExternalCacheDir().getPath()
                : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }



    private long getUsableSpace(File file){
        return  file.getUsableSpace();
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


    private Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can not visit network from UI Thread");
        }
        if (mDiskLruCache == null) return  null;
        String key = Md5Util.hashKeyFormUrl(url);

            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null){
                OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                 if (downloadUrlToStream(url,outputStream)){
                     editor.commit();
                 }else{
                     editor.abort();
                 }
                mDiskLruCache.flush();
            }
            return loadBitmapFromDiskCache(url,reqWidth,reqHeight);
    }

    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {

        if (Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can not visit network from UI Thread");
        }
        if (mDiskLruCache == null) return  null;
        String key = Md5Util.hashKeyFormUrl(url);
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);

        if (snapshot!=null){
         FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
         FileDescriptor fileDescriptor = fileInputStream.getFD();
         bitmap = BitmapUtils.decodeSampledBitmapFromFileDescriptor(fileDescriptor,reqWidth,reqHeight);
         if (bitmap != null){
             addBitmapToMemory(key,bitmap);
         }
        }
        return bitmap;
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            out = new BufferedOutputStream(outputStream,TAG_KEY_URI);
            int b;
            while ( (b = in.read()) != -1){
                out.write(b);
            }
              return  true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            Log.e(TAG,"downloadBitmap fail :"+e);
        }finally {

            try {
                if (urlConnection!= null)
                    urlConnection.disconnect();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     return  false;
    }

    public Bitmap loadBitmap(String uri,int reqWidth,int reqHeight){
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null){
            Log.d(TAG,"loadBitmapFromMemCache,url:"+uri);
            return bitmap;
        }

        try {
            bitmap = loadBitmapFromDiskCache(uri,reqWidth,reqHeight);
            if (bitmap != null){
                Log.d(TAG,"loadBitmapFromDisk,url:"+uri);
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(uri,reqWidth,reqHeight);
            Log.d(TAG,"loadBitmapFromHttp,url:"+uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null && !mIsDiskLruCacheCreated){
            Log.w(TAG,"error,DiskLruCache is not created");
            bitmap = downloadBitmapFromUrl(uri);
        }

        return bitmap;
    }

    private Bitmap downloadBitmapFromUrl(String uri) {

        return  null;
    }


    private Bitmap loadBitmapFromMemCache(String uri) {
        String key = Md5Util.hashKeyFormUrl(uri);
        return getBitmapFromCache(key);
    }



    public void bindBitmap(final String uri, ImageView imageView, final int reqWidth, final int reqHeight){
        imageView.setTag(TAG_KEY_URI,uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
         return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null){

                }
            }
        };

    }

}
