package com.fight.light.resload;

import android.content.Context;

import java.io.File;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * ClassLoader Manager
 */
public class DLClassLoader extends DexClassLoader {

    private static final HashMap<String,DLClassLoader> mPluginClassLoaders = new HashMap<>();
    public DLClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
    }

      public static DLClassLoader getClassLoader(String dexPath, Context context,ClassLoader parentLoader){
        DLClassLoader dlClassLoader = mPluginClassLoaders.get(dexPath);
        if (dlClassLoader != null)
            return dlClassLoader;

          File dexOutputDir =  context.getDir("dex",Context.MODE_PRIVATE);
           final String dexOutputPath = dexOutputDir.getAbsolutePath();
           dlClassLoader = new DLClassLoader(dexPath,dexOutputPath,null,parentLoader);
           mPluginClassLoaders.put(dexPath,dlClassLoader);
        return dlClassLoader;
     }


}
