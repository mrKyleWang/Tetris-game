package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class demo extends JPanel{
	public static void main(String[] args) {
		int[][] arr = new int [20][10];
		for(int i=0;i<20;i++) {
			Arrays.fill(arr[i], -1);
		}
		
	}

}
