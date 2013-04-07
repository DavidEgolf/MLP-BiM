/*
	Author: David Egolf
	File: CellState.java
	Description: This is an enumeration of all the space states:
		empty : water, not fired at yet.
		ship : there's a ship here, not hit yet.
		miss : water, fired at.
		hit : there's a ship here, it's been hit.
	Date: 2/27/13
*/

public enum CellState {EMPTY, SHIP, MISS, HIT};
