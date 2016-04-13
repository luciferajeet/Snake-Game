package snakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent; 
import java.awt.geom.Dimension2D; 
import java.awt.image.BufferedImage; 
 
import javax.swing.ImageIcon; 
import javax.swing.JPanel; 
import javax.swing.Timer; 
 
 
public class Board extends JPanel implements ActionListener{ 
	private final int B_WIDTH=300;
	private final int B_HEIGHT=300;
	private final int DOT_SIZE=10;
	private final int ALL_DOTS=1500;
	private final int RAND_POS=30;
	public int DELAY=200;
	private final int x[]=new int[ALL_DOTS];
	private final int y[]=new int[ALL_DOTS];
	private int dots;
	private int apple_x;
	private int apple_y;
	private int apple_x1;
	private int apple_y1;
	private boolean leftD=false;
	private boolean rightD=true;
	private boolean upD=false;
	private boolean downD=false;
	private boolean inGame=true;
	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;
	private Image bonus;
 
	private int points=0;
 
	public Board(int fast){ 
		addKeyListener(new TAdapter()); 
		DELAY/=fast;
		setBackground(Color.white);
		setFocusable(true); 
		setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
		loadImages(); 
		initGame(); 
	} 
 
	private void loadImages(){ 
		ImageIcon iid=new ImageIcon("ball.png");
		ball=iid.getImage();
		Image newimg = ball.getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);
		ball=newIcon.getImage();
 
		ImageIcon iia=new ImageIcon("apple.png");
		apple=iia.getImage();
		Image newimg1= apple.getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon1 = new ImageIcon(newimg1);
		apple=newIcon1.getImage();
 
		ImageIcon iib=new ImageIcon("apple.png");
		bonus=iib.getImage();
		Image newimg3= bonus.getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon3 = new ImageIcon(newimg3);
		bonus=newIcon3.getImage();
 
		ImageIcon iih=new ImageIcon("head.png");
		head=iih.getImage();
		Image newimg2= head.getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon2= new ImageIcon(newimg2);
		head=newIcon2.getImage();
	} 
 
	private void initGame(){ 
		dots=5;
		for(int i=0;i<dots;i++){
			x[i]=50-i*10;
			y[i]=150;
		} 
		locateApple(); 
		timer=new Timer(DELAY,this);
		timer.start();
	} 
 
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	} 
 
	private void doDrawing(Graphics g){
		if(inGame){
			score(g);
 
			long startingTime=System.currentTimeMillis();
			long timePassed=0;
 
			if(points%2==0 && points>0){
				//for(timePassed=0;timePassed<2000;timePassed=System.currentTimeMillis()-startingTime){ 
 
				//} 
			} 
			//g.drawImage(bonus,apple_x1,apple_y1,this); 
			g.drawImage(apple,apple_x,apple_y,this);
			for(int i=0;i<dots;i++){
				if(i==0)
					g.drawImage(head,x[i],y[i],this);
				else 
					g.drawImage(ball,x[i],y[i],this);
			} 
			Toolkit.getDefaultToolkit().sync(); 
		} 
		else 
			gameOver(g);
	} 
 
	private void score(Graphics g){
		String scr="SCORE";
		String point=""+points;
		Font small=new Font("Helvetica",Font.BOLD,14);
		FontMetrics metr=getFontMetrics(small);
		g.setColor(Color.black);
		g.setFont(small);
		g.drawString(scr,(B_WIDTH-metr.stringWidth(scr))/2,12);
		g.drawString(point,15+(B_WIDTH-metr.stringWidth(scr))/2,25);
	} 
 
	private void gameOver(Graphics g){
		String msg="Game Over";
		Font small=new Font("Helvetica",Font.BOLD,14);
		FontMetrics metr=getFontMetrics(small);
		g.setColor(Color.black);
		g.setFont(small);
		g.drawString(msg,(B_WIDTH-metr.stringWidth(msg))/2,B_HEIGHT/2);
	} 
 
	private void checkApple(){ 
		if((x[0]==apple_x) && (y[0]==apple_y)){
			dots++;
			points++;
			locateApple(); 
		} 
	} 
 
	private void move(){ 
		for(int i=dots;i>0;i--){
			x[i]=x[i-1];
			y[i]=y[i-1];
		} 
		if(leftD)
			x[0]-=DOT_SIZE;
		if(rightD)
			x[0]+=DOT_SIZE;
		if(upD)
			y[0]-=DOT_SIZE;
		if(downD)
			y[0]+=DOT_SIZE;
	} 
 
	private void checkCollision(){ 
		for(int i=dots;i>0;i--){
			if((i>4) &&(x[0]==x[i]) && (y[0]==y[i]))
				inGame=false;
		} 
		if(y[0]>=B_HEIGHT)
			inGame=false;
		if(y[0]<0)
			inGame=false;
		if(x[0]>=B_WIDTH)
			inGame=false;
		if(x[0]<0)
			inGame=false;
		if(!inGame)
			timer.stop();
	} 
 
	private void locateApple(){ 
		int r=(int)(Math.random()*RAND_POS);
		apple_x=((r*DOT_SIZE));
		r=(int)(Math.random()*RAND_POS);
		apple_y=((r*DOT_SIZE));
 
		int r1=(int)(Math.random()*RAND_POS);
		apple_x1=((r1*DOT_SIZE));
		r1=(int)(Math.random()*RAND_POS);
		apple_y1=((r1*DOT_SIZE));
	} 
 
	@Override 
	public void actionPerformed(ActionEvent e){
		if(inGame){
			checkApple(); 
			checkCollision(); 
			move(); 
		} 
		repaint(); 
	} 
 
	private class TAdapter extends KeyAdapter{ 
		@Override 
		public void keyPressed(KeyEvent e){
			int key=e.getKeyCode();
			if((key==KeyEvent.VK_LEFT) && (!rightD)){
				leftD=true;
				upD=false;
				downD=false;
			} 
			if((key==KeyEvent.VK_RIGHT) && (!leftD)){
				rightD=true;
				upD=false;
				downD=false;
			} 
			if((key==KeyEvent.VK_UP) && (!downD)){
				leftD=false;
				upD=true;;
				rightD=false;
			} 
			if((key==KeyEvent.VK_DOWN) && (!upD)){
				leftD=false;
				downD=true;;
				rightD=false;
			} 
		} 
	} 
} 