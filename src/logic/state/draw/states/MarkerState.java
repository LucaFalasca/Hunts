package logic.state.draw.states;

import javafx.scene.canvas.GraphicsContext;

public class MarkerState implements State {

private static MarkerState INSTANCE = new MarkerState();
	
	private MarkerState() {
		
	}
	
	public static MarkerState getInstance() {
		return INSTANCE;
	}
	
	@Override
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		gc.fillOval(endX - 5, endY - 5, 10, 10);
		return false;
	}

}
