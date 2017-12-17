package tetris;

import java.awt.image.BufferedImage;

public class Cell {
	protected int row;
	protected int col;
	protected static final int length = 24;
	protected BufferedImage image;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public void move() {
		col--;
	}
	

}
