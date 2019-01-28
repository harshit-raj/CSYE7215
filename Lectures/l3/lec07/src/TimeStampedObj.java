package l3.lec07.src;

import java.util.Date;

// Class of objects holding time-stamp
// Adapted from example by Evan Golub

public class TimeStampedObj {
	Object payload;
	Date timeStamp;

	public TimeStampedObj(Object o) {
		this.payload = o;
		//TimeStampedObjCache.lastObjCreated = this; 
		timeStamp = new Date();
		TimeStampedObjCache.lastObjCreated = this; 
	}

	public Date getTimeStamp() {
			return timeStamp;
	}
	
	public Object getPayload() {
		return payload;
	}
}
