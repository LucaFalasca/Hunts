package logic.parser;

public class Parser {

	public static double parseToPercent(double value, double max) {
		return value * 100 / max;
	}
	
	public static double parseFromPercent(double valuePercent, double max) {
		return valuePercent * max / 100;
	}
}
