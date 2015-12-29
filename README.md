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
