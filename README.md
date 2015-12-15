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
