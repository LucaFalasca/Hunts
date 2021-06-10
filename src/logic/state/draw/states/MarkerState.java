package logic.state.draw.states;

import javafx.scene.canvas.GraphicsContext;

public class MarkerState implements State {

private static MarkerState instance = new MarkerState();
	
	private MarkerState() {
		
	}
	
	public static MarkerState getInstance() {
		return instance;
	}
	
	@Override
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		gc.fillOval(endX - 5, endY - 5, 10, 10);
		return false;
	}
	
	@Override
	public void clean(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		double width = endX - startX;
    	double height = endY - startY;
    	
		gc.clearRect(startX, startY, width, height);
	}

	@Override
	public String toString() {
		return "MarkerState";
	}
	
	

}
