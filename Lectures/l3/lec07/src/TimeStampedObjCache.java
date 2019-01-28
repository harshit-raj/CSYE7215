package l3.lec07.src;

// Caches most recently created time-stamped object
public class TimeStampedObjCache {
    static public volatile TimeStampedObj lastObjCreated =
    		new TimeStampedObj (new Object ());
}
