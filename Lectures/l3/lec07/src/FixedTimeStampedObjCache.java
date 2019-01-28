package l3.lec07.src;

// Caches most recently created time-stamped object
public class FixedTimeStampedObjCache {
    static public volatile FixedTimeStampedObj lastObjCreated =
    		FixedTimeStampedObj.newInstance(new Object ());
}
