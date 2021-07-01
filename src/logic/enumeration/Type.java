package logic.enumeration;

public enum Type {
	RECTANGLE, OVAL;
	
	@Override
	public String toString() {
		if(this == RECTANGLE) 	return "Rectangle";
		else					return "Oval";
	}
}
