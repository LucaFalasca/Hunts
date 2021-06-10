package logic.state.draw.states;

import javafx.scene.canvas.GraphicsContext;

public class RectangleState implements State{

	private static RectangleState instance = new RectangleState();
	
	private RectangleState() {
		
	}
	
	public static RectangleState getInstance() {
		return instance;
	}
	
	@Override
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		double width = endX - startX;
    	double height = endY - startY;
		
		gc.fillRect(startX, startY, width, height);
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
		return "RectangleState";
	}

}
