package GUI;

import java.awt.Button;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.skhu.Ai;
import net.skhu.Game;
import net.skhu.User;

//첫 화면 frame
public class MainFrame extends JFrame{
	private UserGUI usergui = new UserGUI();
	private AiGUI aigui = new AiGUI();
	private Game game;
	private List list = new List();
	private JLabel label;
	private JTextField textField;
	private User user;
	private Ai ai;
	private int bet;
	Button button;

	public MainFrame(Game game) {
		this.game = game;
	}

	// 게임시작 버튼 클릭시 화면 repaint
	public void MainViewchange(JPanel panel) {

		getContentPane().removeAll();
		getContentPane().add(panel);
		revalidate();
		repaint();
	}

	public void gameViewChange(JPanel panel, AiGUI aigui) {

	}

	// 메인 화면 설정
	public JPanel mainView() {
		JPanel panel = new JPanel(null);

		Label label = new Label("INDIAN   POKER\r\n");
		label.setFont(new Font("Dialog", Font.PLAIN, 34));
		label.setBounds(110, 31, 423, 68);
		panel.add(label);

		JLabel jlabel = new JLabel("");
		ImageIcon icon = new ImageIcon(getClass().getResource("/image/joker.png"));
		jlabel.setIcon(icon);
		icon.setImage(icon.getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
		jlabel.setBounds(149, 105, 200, 250);
		panel.add(jlabel);

		button = new Button("\uAC8C\uC784\uC2DC\uC791\r\n");
		button.setBounds(177, 382, 128, 29);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = gameView(game);
				MainViewchange(panel);
			}
		});
		return panel;
	}

	// 게임 시작 누르고 다음 화면 설정
	public JPanel gameView(Game game) {
		changeText(game,0);
		JPanel panel = new JPanel(null);
		Button batting = new Button("배팅");
		batting.setBounds(20, 300, 128, 29); //배팅
		Button die = new Button("다이");
		die.setBounds(170, 300, 128, 29); //다이
		Button cont = new Button("계속");
		cont.setBounds(330, 300, 128, 29); //계속

		panel.add(aigui.changeView(game));
		panel.add(batting);
		panel.add(die);
		panel.add(cont);
		panel.add(usergui.changeView(game, 0));
		panel.add(list);

		Button betBtn = new Button("입력");
		betBtn.setBounds(243, 335, 50, 27);
		panel.add(betBtn);
		betBtn.setEnabled(false);

		label = new JLabel();
		label.setBounds(new Rectangle(86,310,120,79));
		label.setText("배팅할 코인 입력: ");
		panel.add(label);

		JTextField inCoin = new JTextField();
		inCoin.setColumns(3);
		inCoin.setBounds(190, 337, 50, 25);
		panel.add(inCoin);
		inCoin.setEnabled(false);

		betBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String s =inCoin.getText();
				JOptionPane.showMessageDialog(panel, s + "개의 코인이 입력됐습니다");
				inCoin.setText("");
				String msg="User 배팅="+s;
				bet=Integer.parseInt(s);
				if(bet > game.getUser().getUserCoin())//배팅 건 갯수가 더크다면
					list.add("배팅 건 숫자가 더많습니다!");
				else
				{
					game.getUser().betCoin(bet);
					list.add(msg);
				}			
			}

		});
		batting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				betBtn.setEnabled(true);
				inCoin.setEnabled(true);

			}
		});


		// die 버튼 클릭시
		die.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove(usergui.im(game));
				panel.add(usergui.changeView(game, 1));
				changeText(game,1);
				panel.repaint();
				game.getUser().setCoin(game.getUser().getUserCoin()-1);
				String dieMsg="die 하셨습니다 User의 코인이 하나 줄고 다음 라운드로 넘어갑니다.";
				list.add(dieMsg);
			}
		});
		
		cont.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				betBtn.setEnabled(false);
				inCoin.setEnabled(false);
				game.setRound();
				panel.remove(usergui.im(game));
				panel.remove(aigui.im(game));
				panel.add(aigui.changeView(game));
				panel.add(usergui.changeView(game, 0));
				changeText(game,0);
				panel.repaint();
			}
		});

		return panel;
	}

	// 게임화면 밑에 나오는 글씨들
	public void changeText(Game game,int check) {
		list.setBounds(0, 366, 484, 85);
		if(check==1){
			String msg = "사용자의 숫자는" + game.getUser().number(game);
			list.add(msg);
		}
		else if (game.getRound() == 1) 
		{
			int first = ((int) (Math.random() * 2)) +1;
			list.add("INDIAN 포커에 오신걸 환영합니다.");
			list.add("1라운드를 시작하겠습니다.");
			String currentCoin= String.format("User 코인: %d, Ai 코인: %d",game.getUser().getUserCoin(),game.getAi().getAiCoin());
			list.add(currentCoin);
			String betFirstMsg = first == 1 ? "사용자가 먼저 배팅을 시작합니다 15초 시간제한있습니다!배팅안하면 자동으로 배팅됩니다" : "Ai가 먼저 배팅을 시작합니다.";
			list.add(betFirstMsg);
			if(first ==1) //사용자 배팅 시작
			{
				//사용자한테 배팅할 시간을 준다음에  배팅할 시간이 지나면 알아서 배팅이되고 AI차례로 넘어오는거 구현하기
				Timer timer = new Timer();
				TimerTask task = new TimerTask()
				{
					@Override
					public void run()
					{	
						//내가현재 갖고 있는 코인 수보다 적다면 ->즉  15초 안에 배팅을 걸었다는 얘기 -> 그러면 자동배팅 하면안되게 하는 조건문 
						if(game.getUser().getUserCoin() > game.getUser().nowBetCoin(bet))
						{
							battleAiWithUser();
							//AI가 배팅을 건다면  그다음은 추가 배팅필요
							
						}
						else //15초이내에 배팅을 못걸었다면
						{
							String msg = String.format("사용자가 배팅될 코인의 수는 -> %d", game.getUser().autoBetCoin((int)(Math.random()*3)+1));
							list.add(msg);
							battleAiWithUser();
						}
					}
				};
				timer.schedule(task, 4000);
			}
			
			else //ai 배팅 시작
			{
				 battleAiWithUser();
			}

		} 
		else if(game.getRound() <=10)//10라운드 수가 아니라면
		{
			if(game.exhaustion() == true)
			{
				list.add(game.coinExhaustion());
				return ;
			}
				list.add("\n");
				list.add(game.getRound() + "라운드를 시작하겠습니다.");
				String s= String.format("User 코인: %d, Ai 코인: %d",game.getUser().getUserCoin(),game.getAi().getAiCoin());
				list.add(s);	
				
		}
		else //라운드 수가 10초과인 라운드 넘어가면 
		{
			list.add(game.winner());
		}

	}
	//AI와 User배팅 싸움
	public void battleAiWithUser()
	{
		//aiBattingBattle이 aiDiePercentage보다 더크다면 -> 배팅할 확률이 더크다면
		if(game.getAi().aiBattingBattle(game.getUser().number(game)) > game.getAi().aiDiePercentage(game.getUser().number(game)))
		{
			String ai = String.format("ai가 배팅한 코인 수는 -> %d", game.getAi().aiBattingBattle(game.getUser().number(game)));
			game.getAi().aiBetCoin(game.getAi().aiBattingBattle(game.getUser().number(game)));
			list.add(ai);
		}
		else //죽을 확률이 더크다면
		{
			String message = "ai가  고민 하더니 다이했습니다";
			game.getUser().setCoin(game.getUser().getUserCoin()+1);
			game.getAi().aiBetCoin(1);
			list.add(message);
		}
	}
}