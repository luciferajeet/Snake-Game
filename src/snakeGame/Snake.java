package snakeGame;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame; 
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
 
public class Snake extends JFrame{ 
	int faster;
	public Snake(int fast){ 
		faster = fast;
		setLayout(new FlowLayout());
		JMenuBar menu = new JMenuBar();
		JMenu settings = new JMenu("Settings");
		JMenuItem speed = new JMenuItem("Speed");
		settings.add(speed);
		speed.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
				JFrame ex=new Snake(faster*2);
				ex.setVisible(true);
			}
		});
		menu.add(settings);
		add(menu);
		add(new Board(fast)); 
		setResizable(false); 
		pack(); 
		setTitle("Snake"); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	} 
 
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override 
			public void run(){ 
				JFrame ex=new Snake(1);
				ex.setVisible(true);
			} 
		}); 
	} 
} 