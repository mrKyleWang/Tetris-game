package tetris;

import java.awt.image.BufferedImage;

public class Tetromino {
	protected int row;
	protected int col;
	protected Cell[] cells;
	protected State state;
	protected State[] states;
	protected int stateIndex;
	protected BufferedImage image;
	
	public void moveLeft() {
		this.row--;
	}
	public void moveRight() {
		this.row++;
	}
	
	public void fall() {
		this.col++;
	}
}
// 块组有7种，每种有各自多种不同形态，通过某原点的相对坐标来表示出来
class O extends Tetromino {
	public O() {
		image = Tetris.imageO;
		states = new State[1];
		int[][] cellS = new int[4][2];
		cellS[0] = new int[] { -1, -1 };
		cellS[1] = new int[] { 0, -1 };
		cellS[2] = new int[] { -1, 0 };
		cellS[3] = new int[] { 0, 0 };
		states[0] = new State(cellS);
	}
}

class I extends Tetromino {
	public I() {
		image = Tetris.imageI;
		states = new State[2];
		int[][] cellS1 = new int[4][2];
		cellS1[0] = new int[] { -2, 0 };
		cellS1[1] = new int[] { -1, 0 };
		cellS1[2] = new int[] { 0, 0 };
		cellS1[3] = new int[] { 1, 0 };
		states[0] = new State(cellS1);
		int[][] cellS2 = new int[4][2];
		cellS2[0] = new int[] { 0, -2 };
		cellS2[1] = new int[] { 0, -1 };
		cellS2[2] = new int[] { 0, 0 };
		cellS2[3] = new int[] { 0, 1 };
		states[1] = new State(cellS2);
	}
}

class Z extends Tetromino {
	public Z() {
		image = Tetris.imageZ;
		states = new State[2];
		int[][] cellS1 = new int[4][2];
		cellS1[0] = new int[] { -1, -1 };
		cellS1[1] = new int[] { 0, -1 };
		cellS1[2] = new int[] { 0, 0 };
		cellS1[3] = new int[] { 1, 0 };
		states[0] = new State(cellS1);
		int[][] cellS2 = new int[4][2];
		cellS2[0] = new int[] { -1, 0 };
		cellS2[1] = new int[] { -1, -1 };
		cellS2[2] = new int[] { 0, -1 };
		cellS2[3] = new int[] { 0, -2 };
		states[1] = new State(cellS2);
	}
}

class S extends Tetromino {
	public S() {
		image = Tetris.imageS;
		states = new State[2];
		int[][] cellS1 = new int[4][2];
		cellS1[0] = new int[] { -1, 0 };
		cellS1[1] = new int[] { 0, 0 };
		cellS1[2] = new int[] { 0, -1 };
		cellS1[3] = new int[] { 1, -1 };
		states[0] = new State(cellS1);
		int[][] cellS2 = new int[4][2];
		cellS2[0] = new int[] { -1, -2 };
		cellS2[1] = new int[] { -1, -1 };
		cellS2[2] = new int[] { 0, -1 };
		cellS2[3] = new int[] { 0, 0 };
		states[1] = new State(cellS2);
	}
}

class J extends Tetromino {
	public J() {
		image = Tetris.imageJ;
		states = new State[4];
		int[][] cellS1 = new int[4][2];
		cellS1[0] = new int[] { 0, -2 };
		cellS1[1] = new int[] { 0, -1 };
		cellS1[2] = new int[] { -1, 0 };
		cellS1[3] = new int[] { 0, 0 };
		states[0] = new State(cellS1);
		int[][] cellS2 = new int[4][2];
		cellS2[0] = new int[] { -1, -1 };
		cellS2[1] = new int[] { -1, 0 };
		cellS2[2] = new int[] { 0, 0 };
		cellS2[3] = new int[] { 1, 0 };
		states[1] = new State(cellS2);
		int[][] cellS3 = new int[4][2];
		cellS3[0] = new int[] { -1, -1 };
		cellS3[1] = new int[] { 0, -1 };
		cellS3[2] = new int[] { -1, 0 };
		cellS3[3] = new int[] { -1, 1 };
		states[2] = new State(cellS3);
		int[][] cellS4 = new int[4][2];
		cellS4[0] = new int[] { -2, -1 };
		cellS4[1] = new int[] { -1, -1 };
		cellS4[2] = new int[] { 0, -1 };
		cellS4[3] = new int[] { 0, 0 };
		states[3] = new State(cellS4);
	}
}

class L extends Tetromino {
	public L() {
		image = Tetris.imageL;
		states = new State[4];
		int[][] cellS1 = new int[4][2];
		cellS1[0] = new int[] { -1, -2 };
		cellS1[1] = new int[] { -1, -1 };
		cellS1[2] = new int[] { -1, 0 };
		cellS1[3] = new int[] { 0, 0 };
		states[0] = new State(cellS1);
		int[][] cellS2 = new int[4][2];
		cellS2[0] = new int[] { -1, -1 };
		cellS2[1] = new int[] { 0, -1 };
		cellS2[2] = new int[] { 1, -1 };
		cellS2[3] = new int[] { -1, 0 };
		states[1] = new State(cellS2);
		int[][] cellS3 = new int[4][2];
		cellS3[0] = new int[] { -1, -1 };
		cellS3[1] = new int[] { 0, -1 };
		cellS3[2] = new int[] { 0, 0 };
		cellS3[3] = new int[] { 0, 1 };
		states[2] = new State(cellS3);
		int[][] cellS4 = new int[4][2];
		cellS4[0] = new int[] { -2, 0 };
		cellS4[1] = new int[] { -1, 0 };
		cellS4[2] = new int[] { 0, -1 };
		cellS4[3] = new int[] { 0, 0 };
		states[3] = new State(cellS4);
	}
}

class T extends Tetromino {
	public T() {
		image = Tetris.imageT;
		states = new State[4];
		int[][] cellS1 = new int[4][2];
		cellS1[0] = new int[] { -1, 0 };
		cellS1[1] = new int[] { 0, 0 };
		cellS1[2] = new int[] { 1, 0 };
		cellS1[3] = new int[] { 0, 1 };
		states[0] = new State(cellS1);
		int[][] cellS2 = new int[4][2];
		cellS2[0] = new int[] { -1, -1 };
		cellS2[1] = new int[] { -2, 0 };
		cellS2[2] = new int[] { -1, 0 };
		cellS2[3] = new int[] { -1, 1 };
		states[1] = new State(cellS2);
		int[][] cellS3 = new int[4][2];
		cellS3[0] = new int[] { -1, -2 };
		cellS3[1] = new int[] { -2, -1 };
		cellS3[2] = new int[] { -1, -1 };
		cellS3[3] = new int[] { 0, -1 };
		states[2] = new State(cellS3);
		int[][] cellS4 = new int[4][2];
		cellS4[0] = new int[] { 0, -2 };
		cellS4[1] = new int[] { 0, -1 };
		cellS4[2] = new int[] { 1, -1 };
		cellS4[3] = new int[] { 0, 0 };
		states[3] = new State(cellS4);
	}
}
