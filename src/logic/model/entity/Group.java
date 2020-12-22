package logic.model.entity;

import java.util.List;

public class Group {
	
	private String groupName;
	private int nOfHunters;
	private boolean election;
	private List<Hunt> huntList;
	private List<User> members;
	private boolean visibility;
	
	public Group(String groupName, int nOfHunters, boolean election, List<Hunt> huntList, List<User> members, boolean visibility) {
		this.groupName = groupName;
		this.nOfHunters = nOfHunters;
		this.election = election;
		this.huntList = huntList;
		this.members = members;
		this.visibility = visibility;
	}
	
	
	
}
