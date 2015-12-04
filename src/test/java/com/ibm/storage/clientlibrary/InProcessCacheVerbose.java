/**
 * 
 */
package com.ibm.storage.clientlibrary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.ibm.storage.clientlibrary.InProcessCache;
import com.ibm.storage.clientlibrary.InProcessCacheStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




/**
 * @author ArunIyengar
 * 
 */
public class InProcessCacheVerbose {

    long defaultExpiration = 6000;
    int numObjects = 2000;
    InProcessCache<String, Integer> spc = new InProcessCache<String, Integer>(
            numObjects, defaultExpiration);
    String key1 = "key1";
    String key2 = "key2";
    String key3 = "key3";
    long lifetime = 3000;



    
    @Test
    public void testPutGetGetStatistics() {
        spc.put(key1, 42, lifetime);
        spc.printCacheEntry(key1);
        spc.printCacheEntry(key2);
        spc.put(key2, 43, lifetime);
        spc.put(key3, 44, lifetime);
        System.out.println(spc.toString());
        InProcessCacheStats stats1 = spc.getStatistics();
        assertEquals("Cache size should be 3", 3, spc.size());
        assertEquals("Hit rate should be 1.0", 1.0,
                stats1.getStats().hitRate(), .0001);
        System.out.println("Cache size: " + spc.size());
        System.out.println("Hit rate: " + stats1.getStats().hitRate());
    }

    @Test
    public void testClear() {
        spc.put(key1, 42, lifetime);
        spc.put(key2, 43, lifetime);
        spc.put(key3, 44, lifetime);
        assertEquals("Cache size should be 3", 3, spc.size());
        System.out.println("Cache size: " + spc.size());
        System.out.println(spc.toString());
        spc.clear();
        assertEquals("Cache size should be 0", 0, spc.size());
        System.out.println("Cache size: " + spc.size());
        System.out.println(spc.toString());
    }

    @Test
    public void testDelete() {
        spc.put(key1, 42, lifetime);
        spc.put(key2, 43, lifetime);
        spc.put(key3, 44, lifetime);
        assertEquals("Cache size should be 3", 3, spc.size());
        System.out.println(spc.toString());
        spc.delete(key2);
        assertEquals("Cache size should be 2", 2, spc.size());
        System.out.println(spc.toString());
        spc.put(key2, 50, lifetime);
        spc.put("key4", 59, lifetime);
        spc.put("key5", 80, lifetime);

        ArrayList<String> list = new ArrayList<String>();
        list.add(key1);
        list.add(key2);
        spc.deleteAll(list);
        assertEquals("Cache size should be 3", 3, spc.size());
        spc.delete("adjkfjadfjdf");
        spc.delete("adfkasdklfjil");
        System.out.println(spc.toString());
    }

    @Test
    public void testPutAll() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(key1, 42);
        map.put(key2, 43);
        map.put(key3, 44);
        spc.putAll(map, lifetime);
        System.out.println(spc.toString());
        assertEquals("Cache size should be 3", 3, spc.size());
    }

    static <K, V> void printMap(Map<K, V> map) {
        System.out.println("printMap: outputting map contents ");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }
    }

    @Test
    public void testGetAll() {
        spc.put(key1, 42, lifetime);
        spc.put(key2, 43, lifetime);
        spc.put(key3, 44, lifetime);
        ArrayList<String> list = new ArrayList<String>();
        list.add(key1);
        list.add(key2);
        list.add(key3);
        Map<String, Integer> map = spc.getAll(list);
        printMap(map);
        assertEquals("Returned map size should be 3", 3, map.size());
    }

    @Test
    public void testUpdate() {
        spc.put(key1, 42, lifetime);
        System.out.println(spc.toString());
        spc.put(key1, 43, lifetime);
        System.out.println(spc.toString());
        spc.put(key1, 44, lifetime);
        System.out.println(spc.toString());
        assertEquals("Cache size should be 1", 1, spc.size());
    }
 
    @Test
    public void testExpiration() {
        long lifespan = 1000;
        Integer val1;

        spc.clear();
        val1 = spc.get(key1);
        assertNull("Val1 should be null, value is " + val1, val1);
        spc.put(key1, 42, lifespan);
        val1 = spc.get(key1);
        assertNotNull("Val1 should not be null, value is " + val1, val1);
        System.out.println(val1);
        try {
            Thread.sleep(lifespan + 200);
        }
        catch (Exception e) {
        } 
        val1 = spc.get(key1);
        assertNull("Val1 should be null, value is " + val1, val1);
    }
    
}
