/**
 * Copyright (c) 2015, biezhi 王爵 (biezhi.me@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blade.cache.impl;

import java.util.Iterator;

import blade.cache.AbstractCache;
import blade.cache.CacheObject;

/**
 * 
 * <p>
 * FIFO实现
 * </p>
 *
 * @author	<a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since	1.0
 */
public class FIFOCache<K, V> extends AbstractCache<K, V> {

	public FIFOCache(int cacheSize) {
		super(cacheSize);
	}

	@Override
	protected int eliminateCache() {

		int count = 0;
		K firstKey = null;

		Iterator<CacheObject<K, V>> iterator = _mCache.values().iterator();
		while (iterator.hasNext()) {
			CacheObject<K, V> cacheObject = iterator.next();

			if (cacheObject.isExpired()) {
				iterator.remove();
				count++;
			} else {
				if (firstKey == null)
					firstKey = cacheObject.getKey();
			}
		}

		if (firstKey != null && isFull()) {// 删除过期对象还是满,继续删除链表第一个
			_mCache.remove(firstKey);
		}

		return count;
	}

}