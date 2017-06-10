compare_collections
============================

### Introduction
Compare the contents of two collections, calculating add, sub and update members from collection 1 to 2.

### Installation
    // just copy my code to your project!
    
### Usage 
##### For more details, see [Test.java](https://github.com/nuo9/compare_collections/blob/master/src/test/java/Test.java)
##### Construction method:
    // Approach 1: the the most ordinary way
    // PK is used to identify an object, T is the very object  
    create(Map<PK, T> before, Map<PK, T> after);
    // Approach 2: applying two collections and a method that getting PK from the object
    create(before, after, t -> t.getId());
    // Approach 3: applying two collections with itself as PK
    create(before, after);
##### Getting results:
    CompareCollections<> cc = CompareCollections.create(...);
    cc.getAddMap();
    cc.getSubMap();
    cc.getUpdateMap();
    cc.getUpdatedMap();
##### Warning: 
Approach 2 and 3 may cause java.lang.IllegalStateException: Duplicate key.  
This exception is thrown by Collectors.toMap() method.
