package tetris;

//状态类，存储每个块组的 四个块 的 相对位置
public class State {
	protected int[][] cellS;
	public State(int[][] cellS) {
		this.cellS = cellS;
	}


}
