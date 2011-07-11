package edu.vu.isis.ammo.api;

public class TimeInterval {
	public enum Unit {
		MILLISEC, SECOND, MINUTE, HOUR, DAY, MONTH, YEAR
	};
	static public final int UNLIMITED = Integer.MAX_VALUE;
	
	final private Unit units;
	public Unit unit() { return this.units; }
	
	final private long amount;
	public long amount() { return this.amount; }
	
	public TimeInterval(Unit unit, long amount) {
		this.units = unit;
		this.amount = amount;
	}
	
	public TimeInterval(Unit unit) {
		this.units = unit;
		this.amount = 1;
	}
	
	public TimeInterval(long seconds) {
		this.units = Unit.SECOND;
		this.amount = seconds;
	}
	
}
