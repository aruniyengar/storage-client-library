# storage-client-library
Java library for storage clients which implements caching, compression, encryption
This github repository is intended to be used for a client accessing a server for storing and retrieving data.  In general, there may
be considerable overhead for accessing the server.  This library can reduce this overhead via client-side caching.  It can also compress and
encrypt data.

This library can be incorporated into a Java project managed by Maven via the Maven dependency:
```xml
  	<dependency>
  		<groupId>com.ibm.storage</groupId>
  		<artifactId>clientlibrary</artifactId>
  		<version>1.0</version>
  	</dependency>
```

An overview of this storage client library is available from:
* Arun Iyengar, [Enhanced Storage Clients](http://domino.watson.ibm.com/library/CyberDig.nsf/papers/16214813202B330D85257F2A004A2187/$File/rc25584.pdf), IBM Research Report RC25584 (WAT1512-042), December 23, 2015.

## Getting Started

## Caching
In order to use an in-process cache with lifetimes, the following import statements should be used:
~~~ java
import com.ibm.storage.clientlibrary.CacheWithLifetimes;
import com.ibm.storage.clientlibrary.InProcessCache;
~~~
One can create an in-process cache of integers indexed by strings using the Guava cache with a maximum cache size of “numObjects” and a default lifetime of “defaultLifetime” in milliseconds via:
~~~ java
    CacheWithLifetimes<String, Integer> cache = new InProcessCache<String, Integer>(numObjects, defaultLifetime);
~~~
In order to use a remote process cache, Redis is a preferred implementation.  The following import statements should be used:
~~~ java
import com.ibm.storage.clientlibrary.CacheWithLifetimes;
import com.ibm.storage.clientlibrary.RedisCacheExtended;
~~~
One can connect to a redis cache of byte arrays indexed by strings running on the same node from a Java program via:
~~~ java
    CacheWithLifetimes<String, byte[]> cache = new RedisCacheExtended<String, byte[]>("localhost", 6379, 60, defaultExpiration);
~~~
Here, 6379 is the port number, and 60 indicates that idle connections should be closed after 60 seconds.

The following method call adds 42 to the cache indexed by “key1”.  “lifetime” is the lifetime of the cached value in milliseconds:
~~~ java
        cache.put(key1, 42, lifetime);
~~~
The following method call adds all key-value pairs corresponding to “map” to the cache and assigns each key-value pair a lifetime of “lifetime” milliseconds:
 ~~~ java
       cache.putAll(map, lifetime);
~~~
The following method call returns the value corresponding to “key2”.  It returns null if “key2” is not found in the cache, or if the value is expired.
~~~ java
        val = cache.get(key2);
~~~
In the following method call, “list” is a list of keys.  getAll looks up all key-value pairs corresponding to keys in “list” and returns a map containing them.  In this example, cache keys are strings, and values are integers.
~~~ java
        Map<String, Integer> map = cache.getAll(list);
~~~
The following method call deletes the key-value pair corresponding to “key2” from the cache if present: 
~~~ java
        cache.delete(key2);
~~~
In the following method call, “list” is a list of keys.  deleteAll deletes all key-value pairs corresponding to a key in “list”:
~~~ java
        cache.deleteAll(list);
~~~
The following method call deletes all objects in the cache:
~~~ java
        cache.clear();
~~~
The size method returns the number of cached objects:
~~~ java
        System.out.println("Cache size: " + cache.size());
~~~
The following displays the contents of the entire cache.  It should not be invoked if the cache contains a large amount of data as the data outputted would be prohibitively large:
~~~ java
        System.out.println(cache.toString());
~~~
The following displays cache statistics, such as hit rates: 
~~~ java
        System.out.println(cache.getStatistics().getStats());
~~~
When Redis is being used as a remote process cache, it may be desirable to use features of Redis which go beyond the methods offered by the CacheWithLifetimes interface.  The following RedisCacheExtended method returns a data structure corresponding to the Jedis interface for Redis which allows application programs to access the cache using Jedis methods:
~~~ java
    /**
     * Return underlying Jedis object for applications to explicitly use.
     * 
     * @return value underlying Jedis object representing cache
     * 
     * */
    public Jedis getJedis();
~~~
## Encryption and Decryption


In order to use encryption and decryption, the following class should be imported:
~~~ java
import com.ibm.storage.clientlibrary.Encryption;
~~~
The following method call generates an encryption key for encrypting data: 
~~~ java
        Encryption.Key secretKey = Encryption.generateKey();
~~~
The following method call encrypts “hm” using encryption key “secretKey”: 
~~~ java
        SealedObject so = Encryption.encrypt(hm, secretKey);
~~~
The following method call decrypts “so” using encryption key “secretKey”: 
~~~ java
        HashMap<String, Integer> hm2 = Encryption.decrypt(so, secretKey);
~~~
## Compression and Decompression

In order to use compression and decompression, the following class should be imported:
~~~ java
import com.ibm.storage.clientlibrary.Util;
~~~
The following method call compresses “hm”: 
~~~ java
        byte[] compressed = Util.compress(hm);
~~~
The following method call decompresses “compressed”: 
~~~ java
        HashMap<String, Integer> hm2 = Util.decompress(compressed);
~~~

