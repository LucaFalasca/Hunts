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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getnOfHunters() {
		return nOfHunters;
	}

	public void setnOfHunters(int nOfHunters) {
		this.nOfHunters = nOfHunters;
	}

	public boolean isElection() {
		return election;
	}

	public void setElection(boolean election) {
		this.election = election;
	}

	public List<Hunt> getHuntList() {
		return huntList;
	}

	public void setHuntList(List<Hunt> huntList) {
		this.huntList = huntList;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	
	
}
