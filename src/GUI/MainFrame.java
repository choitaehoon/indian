package GUI;

import java.awt.Button;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private int aiBet;
	private int userBet;
	private int aiDie;
	private int totalAiBet;
	//추가배팅count수
	private int count = 0;
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
//				JOptionPane.showMessageDialog(panel, s + "개의 코인이 입력됐습니다");
				inCoin.setText("");
				String msg="User 배팅="+s;
				userBet=Integer.parseInt(s);
				if(userBet > game.getUser().getUserCoin()) //배팅 건 갯수가 더크다면
					list.add("배팅 건 숫자가 더많습니다!");
				else
					list.add(msg);
					battleAiWithUser();
				//AI가 죽었다면
				if(aiDie == 5)
				{
					if(aiBet == 0 && userBet >0) //내가 처음에 배팅걸고 ai가 바로죽는다면
					{
						game.getUser().setCoin(game.getUser().getUserCoin()-userBet+1+userBet);
						game.getAi().aiBetCoin(1);
						count = 0; //0으로초기화
						list.add("유저가 승리했습니다!");
						panel.remove(usergui.im(game));
						panel.add(usergui.changeView(game, 1));
						changeText(game,1);
						panel.repaint();
					}
					else if(aiBet > 0 || userBet>0) //배팅을 걸었는데 AI가 죽었다면 AI가배팅건 코인들과 user가 그코인들을 가져가야 한다 
					{
						game.getUser().setCoin(game.getUser().getUserCoin()-userBet+aiBet+userBet);
						game.getAi().setCoin(game.getAi().getAiCoin()-totalAiBet);
						count = 0; //0으로초기화
						list.add("유저가 승리했습니다!");
						panel.remove(usergui.im(game));
						panel.add(usergui.changeView(game, 1));
						changeText(game,1);
						panel.repaint();
					}
//					else //배팅을 안걸고 죽었다면 
//					{
//						game.getUser().setCoin(game.getUser().getUserCoin()+1);
//						game.getAi().aiBetCoin(1);
//						count = 0; //0으로초기화
//						list.add("유저가 승리했습니다!");
//					}
				}
				//AI가 배팅걸었다면
				else
				{
					if(count < 2) //추가배팅 3회로 제한하기
					{
						//배팅 누를때마다 위에 addActionListener호출 된다
						list.add("사용자는 추가배팅 원하시면 배팅누르고 입력하세요!");
						count ++;
						list.add(String.format("count 수는 %d", count));
					}
					else //추가배팅이 3회가 지나면
					{
						panel.add(usergui.changeView(game, 1));
//						if(game.getUser().number(game) >game.getAi().number(game))//내카드가 더크다면
//						{
//							game.getUser().setCoin(game.getUser().getUserCoin()+aiBet+userBet); //내가 배팅했던 코인과 ai배팅했던코인먹기
//							game.getAi().setCoin(game.getAi().getAiCoin()-aiBet);
//							count = 0; //0으로 초기화
//							list.add("유저가 승리했습니다!");
//						}
//						else //AI카드가 더 크다면 
//						{
//							game.getAi().setCoin(game.getAi().getAiCoin()+aiBet+userBet);
//							game.getUser().setCoin(game.getUser().getUserCoin()-aiBet-userBet);
//							count = 0; //0으로 초기화
//							list.add("AI가 승리했습니다");
//						}
					}
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
			String betFirstMsg = first == 1 ? "사용자가 먼저 배팅을 시작합니다" : "Ai가 먼저 배팅을 시작합니다.";
			list.add(betFirstMsg);
			if(first ==1) //사용자 배팅 시작
			{ 
				
//				//사용자가 배팅을 하면 AI는 배팅걸지 아니면 죽을지 결정
//				if(game.getUser().getUserCoin() > game.getUser().getUserCoin()-bet)
//				{
//					battleAiWithUser();
//					//AI가 배팅을 건다면  그다음은 추가 배팅필요 ->다이할경우 유저가 승리
//					//AI가 죽은 경우 
//					if(die == 5)
//					{
//						game.getAi().aiBetCoin(1);
//						game.getUser().setCoin(game.getUser().getUserCoin()+1);
//						//다음 라운드를 위해 초기값 으로 돌아옴
//						die = 0;
//						list.add(String.format("이번 %d 라운드 승자는 유저입니다!", game.getRound()));
//					}
//
//				}
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
		//aiBattingBattle이 aiDiePercentage보다 더크다면 -> 배팅할 확률이 더 크다면
		if(game.getAi().aiBattingBattle(game.getUser().number(game)) > game.getAi().aiDiePercentage(game.getUser().number(game)))
		{ 
			String ai = String.format("ai가 배팅한 코인 수는 -> %d", aiBet = game.getAi().aiBattingBattle(game.getUser().number(game)));
			totalAiBet += aiBet; 
//			game.getAi().aiBetCoin(game.getAi().aiBattingBattle(game.getUser().number(game)));
			list.add(String.format("totalAiBet의 수는 -> %d", totalAiBet));
			list.add(ai);
		}
		else //죽을 확률이 더크다면
		{
			String message = "ai가  고민 하더니 다이했습니다";
//			//내가 죽었는지 안죽었는지 확인하는 변수 ->숫자5면 죽었다는 소리
			aiDie =5;
			list.add(message);
		}
	}
}