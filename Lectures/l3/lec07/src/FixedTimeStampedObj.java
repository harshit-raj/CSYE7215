package l3.lec07.src;

import java.util.Date;

// Fixed class of objects holding time-stamp
// Adapted from example by Evan Golub

public class FixedTimeStampedObj {
	Object payload;
	Date timeStamp;

	// To avoid publishing this in constructor, make it private and
	// do not assign to cache
	private FixedTimeStampedObj(Object o) {
		this.payload = o;
		timeStamp = new Date();
	}
	
	// Static factory method is what users use to create objects now	
	public static FixedTimeStampedObj newInstance  (Object o) {
		FixedTimeStampedObj tso = new FixedTimeStampedObj(o);
		FixedTimeStampedObjCache.lastObjCreated = tso; 
		return tso;
	}

	public Date getTimeStamp() {
			return timeStamp;
	}
	
	public Object getPayload() {
		return payload;
	}
}
