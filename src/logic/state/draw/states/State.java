package logic.state.draw.states;

import javafx.scene.canvas.GraphicsContext;

public interface State {

	boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY);
	
	void clean(GraphicsContext gc, double startX, double startY, double endX, double endY);
}
