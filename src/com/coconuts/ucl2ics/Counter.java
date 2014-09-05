package com.coconuts.ucl2ics;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Counter {	
	@PrimaryKey
	@Persistent
	private String key;
	
	@Persistent
	private long lastKey;
	
	public Counter() {
		this.key = "Counter";
		this.lastKey = 0;
	}
	
	public void setKey(long key) {
		this.lastKey = key;
	}

	public long getKey() {
		return lastKey;
	}
	
	public long getNextKey() {
		this.lastKey++;
		return lastKey;
	}
}
