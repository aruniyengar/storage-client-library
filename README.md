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
