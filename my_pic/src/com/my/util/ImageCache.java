
package com.my.util;

import java.util.WeakHashMap;

import android.graphics.Bitmap;

/**
 * Caches downloaded images, saves bandwidth and user's
 * packets
 * 
 * @author 
 */
public class ImageCache extends WeakHashMap<String, Bitmap> {

	private static final long VersionUID = 1L;
	
	public boolean isCached(String url){
		return containsKey(url) && get(url) != null;
	}

}
