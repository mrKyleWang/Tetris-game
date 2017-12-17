package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
//csdn  
@SuppressWarnings("serial")
public class Tetris extends JPanel {

	public static final int WIDTH = 245;
	public static final int HEIGHT = 580;

	public static BufferedImage background;
	public static BufferedImage imageO;
	public static BufferedImage imageI;
	public static BufferedImage imageZ;
	public static BufferedImage imageS;
	public static BufferedImage imageJ;
	public static BufferedImage imageL;
	public static BufferedImage imageT;

	public static Tetromino tetromino;
	public static int[][] indexSave = new int[20][10]; // 将所有静态块按所在行和列存储到数组
	public static ArrayList<Cell> cell = new ArrayList<Cell>(); // 所有静态块的集合
	private Timer timer; // 定时器
	private int intervel = 1000 / 100; // 时间间隔
	private int timeIndex = 0; // 用来实现固定时间间隔执行
	private int score; // 分数
	private int fullCol = -1; // 重置fullCol

	private int gameState;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;

	// 静态代码块初始化图片
	static {
		try {
			background = ImageIO.read(Tetris.class.getResource("images/background.png"));
			imageO = ImageIO.read(Tetris.class.getResource("images/1.png"));
			imageI = ImageIO.read(Tetris.class.getResource("images/2.png"));
			imageZ = ImageIO.read(Tetris.class.getResource("images/3.png"));
			imageS = ImageIO.read(Tetris.class.getResource("images/4.png"));
			imageJ = ImageIO.read(Tetris.class.getResource("images/5.png"));
			imageL = ImageIO.read(Tetris.class.getResource("images/6.png"));
			imageT = ImageIO.read(Tetris.class.getResource("images/7.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 绘制所有
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintCell(g);
		paintTetromino(g);
		paintScore(g);
		paintState(g);
	}

	// 画块组
	public void paintTetromino(Graphics g) {
		for (int i = 0; i < 4; i++) {
			State s = tetromino.states[tetromino.stateIndex];
			tetromino.cells[i] = new Cell(s.cellS[i][0], s.cellS[i][1]);
			tetromino.cells[i].row += tetromino.row;
			tetromino.cells[i].col += tetromino.col;
			tetromino.cells[i].image = tetromino.image;
		}
		for (int i = 0; i < tetromino.cells.length; i++) {
			int cellX = tetromino.cells[i].row * 24;
			int cellY = tetromino.cells[i].col * 24;
			g.drawImage(tetromino.cells[i].image, cellX, cellY, null);
		}

	}

	// 画块
	public void paintCell(Graphics g) {
		for (int i = 0; i < cell.size(); i++) {
			int cellX = cell.get(i).row * 24;
			int cellY = cell.get(i).col * 24;
			g.drawImage(cell.get(i).image, cellX, cellY, null);
		}
	}

	// 画分数
	public void paintScore(Graphics g) {
		int x = 10;
		int y = 500;
		g.clearRect(x, y - 17, 200, 45); // 清除选中区域
		g.setColor(new Color(0x3A3B3B));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("得分：" + score, x, y);
		g.drawString("空格暂停/继续", x, y + 20);
	}

	// 画游戏状态
	public void paintState(Graphics g) {
		int x = 50;
		int y = 250;
		switch (gameState) {
		case START:
			g.setColor(Color.RED);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			g.drawString("空格开始！", x, y);
			break;
		case PAUSE:
			g.setColor(Color.RED);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
			g.drawString("游戏暂停！", x, y);
			break;
		case GAME_OVER:
			g.setColor(Color.RED);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
			g.drawString("游戏结束！", x, y);
			break;
		}
	}

	// 获得当前下落的块组
	public void addTetromino() {
		Random rm = new Random();
		// 随机获得类型
		int index = rm.nextInt(7);
		switch (index) {
		case 0:
			tetromino = new O();
			break;
		case 1:
			tetromino = new I();
			break;
		case 2:
			tetromino = new Z();
			break;
		case 3:
			tetromino = new S();
			break;
		case 4:
			tetromino = new J();
			break;
		case 5:
			tetromino = new L();
			break;
		case 6:
			tetromino = new T();
			break;
		}
		// 随机获得状态
		tetromino.stateIndex = rm.nextInt(tetromino.states.length);
		tetromino.state = tetromino.states[tetromino.stateIndex];
		// 生成块组内的块
		tetromino.row = 5;
		tetromino.col = 0;
		tetromino.cells = new Cell[4];
		for (int i = 0; i < 4; i++) {
			State s = tetromino.states[0];
			tetromino.cells[i] = new Cell(s.cellS[i][0], s.cellS[i][1]);
			tetromino.cells[i].row += tetromino.row;
			tetromino.cells[i].col += tetromino.col;
			tetromino.cells[i].image = tetromino.image;
		}
	}

	// 块组转成块
	public void changeToCell() {
		for (int i = 0; i < tetromino.cells.length; i++) {
			cell.add(tetromino.cells[i]);
		}
	}

	// 块组转换状态
	public void changeState() {
		tetromino.stateIndex++;

		if (tetromino.stateIndex == tetromino.states.length) {
			tetromino.stateIndex = 0;
		}
		State s = tetromino.states[tetromino.stateIndex];
		for (int i = 0; i < 4; i++) {
			tetromino.cells[i] = new Cell(s.cellS[i][0], s.cellS[i][1]);
			tetromino.cells[i].row += tetromino.row;
			tetromino.cells[i].col += tetromino.col;
			tetromino.cells[i].image = tetromino.image;
		}
	}

	// 转换后是否出界或碰撞判断
	public boolean outOfBounds() {
		boolean outOfBounds = false;
		Cell[] temp = new Cell[4];
		int index = tetromino.stateIndex;
		index++;
		if (index == tetromino.states.length) {
			index = 0;
		}
		// 转换后的临时块组各块属性赋值
		State s = tetromino.states[index];
		for (int i = 0; i < 4; i++) {
			temp[i] = new Cell(s.cellS[i][0], s.cellS[i][1]);
			temp[i].row += tetromino.row;
			temp[i].col += tetromino.col;
		}
		for (Cell c : temp) {
			// 判断是否出界
			if (c.row < 0 || c.row > 9) {
				outOfBounds = true;
			}
			// 判断是否重叠
			for (Cell C : cell) {
				if (c.row == C.row && c.col == C.col) {
					outOfBounds = true;
				}
			}
		}
		return outOfBounds;
	}

	// 下边界判断
	public boolean underEdge() {
		boolean underEdge = false;
		for (int i = 0; i < tetromino.cells.length; i++) {
			for (int j = 0; j < cell.size(); j++) {
				if ((tetromino.cells[i].col == cell.get(j).col - 1) && (tetromino.cells[i].row == cell.get(j).row)) {
					underEdge = true;
				}
			}
		}
		for (int i = 0; i < tetromino.cells.length; i++) {
			if (tetromino.cells[i].col >= 19) {
				underEdge = true;
			}
		}
		return underEdge;
	}

	// 左边界判断
	public boolean leftEdge() {
		boolean leftEdge = false;
		for (Cell c : tetromino.cells) {
			if (c.row == 0) { // 已经接触左边界
				leftEdge = true;
			}
		}
		// 左边有块
		for (Cell c : tetromino.cells) {
			for (Cell other : cell) {
				if ((c.row == other.row + 1) && (c.col == other.col)) {
					leftEdge = true;
				}
			}
		}
		return leftEdge;
	}

	// 右边界判断
	public boolean rightEdge() {
		boolean rightEdge = false;
		for (Cell c : tetromino.cells) {
			if (c.row == 9) { // 已经接右边界
				rightEdge = true;
			}
		}
		// 右边有块
		for (Cell c : tetromino.cells) {
			for (Cell other : cell) {
				if ((c.row == other.row - 1) && (c.col == other.col)) {
					rightEdge = true;
				}
			}
		}
		return rightEdge;
	}

	// 将所有块按位置储存其索引
	public void saveIndex() {
		for (int i = 0; i < 20; i++) {
			Arrays.fill(indexSave[i], -1);
		}
		// 将块索引存入数组
		for (int i = 0; i < cell.size(); i++) {
			int r = cell.get(i).row;
			int c = cell.get(i).col;
			indexSave[c][r] = i;
		}
	}

	// 判断是否有满行，设置满行行数
	public boolean isFull() {
		boolean isFull = false;
		loop: for (int i = 19; i >= 0; i--) {
			for (int j = 0; j < 10; j++) {
				if (indexSave[i][j] == -1) {
					continue loop;
				}
			}
			fullCol = i; // 设置满行行数
			isFull = true;
			break;
		}
		return isFull;
	}

	// 成排消除
	public void clear() {
		Arrays.sort(indexSave[fullCol]); // 将每行排序方便remove
		for (int i = 0; i < 10; i++) {
			cell.remove(indexSave[fullCol][i] - i); // remove后，后面的索引会减少，remove一次就多减1，直接减k
		}
		score += 100;
		saveIndex(); // 重排，使存储索引和行列保持对应

		for (int i = (fullCol - 1); i >= 0; i--) {
			for (int j = 0; j < 10; j++) {
				if (indexSave[i][j] != -1) {
					int index = indexSave[i][j];
					cell.get(index).col++;
				}
			}
		}
		saveIndex();
	}

	// 判定游戏结束
	public boolean isGameOver() {
		boolean isGameOver = false;
		for (Cell c : cell) {
			if (c.col <= 0) {
				isGameOver = true;
			}
		}
		return isGameOver;
	}

	// 重置
	public void reset() {
		cell = new ArrayList<Cell>();
		tetromino = new Tetromino();
		addTetromino();
		score = 0;
	}

	// 主流程
	public void action() {
		timer = new Timer(); // 主流程控制
		addTetromino();

		//键盘监听
		KeyAdapter k = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:	//空格键改变游戏状态
					switch (gameState) {
					}
					if (gameState == START) {
						gameState = RUNNING;
						break;
					}
					if (gameState == RUNNING) {
						gameState = PAUSE;
						repaint();
						break;
					}
					if (gameState == PAUSE) {
						gameState = RUNNING;
						break;
					}
					if (gameState == GAME_OVER) {
						gameState = START;
						repaint();
						break;
					}
				case KeyEvent.VK_LEFT:	//左方向键移动
					if (!leftEdge() && gameState == RUNNING) {
						tetromino.moveLeft();
					}
					repaint();
					break;
				case KeyEvent.VK_RIGHT:	//右方向键移动
					if (!rightEdge() && gameState == RUNNING) {
						tetromino.moveRight();
					}
					repaint();
					break;
				case KeyEvent.VK_DOWN:	//上方向键向下移动
					if (!underEdge() && gameState == RUNNING) {
						tetromino.fall();
					}
					repaint();
					break;
				case KeyEvent.VK_UP:	//上方向键转换形态
					if (!outOfBounds() && gameState == RUNNING) {
						changeState();
					}
					repaint();
					break;
				}
			}
		};
		this.addKeyListener(k);
		this.requestFocus();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (gameState == RUNNING) {
					timeIndex++;
					// 可移动块组进行移动
					if (tetromino == null) {
						addTetromino();
					}
					if (!underEdge()) {
						if (timeIndex % 40 == 0) { // 固定时间间隔下落
							tetromino.fall();
						}
					} else {
						// 延时处理，方便到底后插入
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						changeToCell();
						tetromino = null;
						addTetromino();
					}
					// 游戏结束
					if (isGameOver()) {
						gameState = GAME_OVER;
						reset();
					} else {
						saveIndex(); // 索引重存
						if (isFull()) { // 执行判断，清理满行
							clear();
							fullCol = -1;
						}
					}
					repaint(); // 重绘，调用paint方法
				}
			}
		}, intervel, intervel);
	}

	public static void main(String[] args) {
		Tetris tetris = new Tetris();
		JFrame frame = new JFrame("Tetris");
		frame.add(tetris);
		frame.setSize(WIDTH, HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		tetris.action();
	}

}
