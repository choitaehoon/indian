package net.skhu;

public class Game {
	private User user;
	private Ai ai;
	static public int round;
	
	public Game(){
		user = new User();
		ai = new Ai();
		round = 1;
	}
	public Ai getAi(){ //ai �ҷ�����
		return ai;
	}
	public int getRound(){
		return round;
	}
	public void userDie(){
		ai.setUserCard((int)user.userCardDeck().get(round-1));
	}
	public User getUser(){ //user�ҷ�����
		return user;
	}
	public void setRound(){
		round=round+1;
	}
	
	public String winner()
	{
		return user.getUserCoin() >ai.getAiCoin() ? "user�� �¸��ϰ� ai�� �����ϴ�" : "ai��  �¸��ϰ�  user�� �����ϴ�";
	}
	
	public boolean exhaustion()
	{
		if(user.getUserCoin()<=0 || user.getUserCoin()<=0)
			return true;
		else
			return false;
	}
	
	public String coinExhaustion()
	{
		return user.getUserCoin()<=0 ? "ai�� �¸��ϰ� user�� �����ϴ�" : "user�� �¸��ϰ� ai�� �����ϴ�";
	}
	
	//ai�� ����
	public int aiBattingBattle(int userCard) //��ī�带 ���� �ִ� ai�� ��ī�尡 ũ�ٸ� ���� ���������ϰ� ��ī�尡 �۴ٸ� ���� ���� ����
	{
		int bet =0;
		if(userCard == 1)
		{
			bet = (int)(Math.random()*5)+1;	
			ai.aiBetCoin(bet);
			return bet;
		}
		else if(userCard>=2 && userCard<=5) 
		{
			bet = (int)(Math.random()*3)+1;
			ai.aiBetCoin(bet);
			return bet;
		}
		else if(userCard<=6 && userCard<=8)
		{
			bet = (int)(Math.random()*1)+1;	
			ai.aiBetCoin(bet);
			return bet;
		}
		else
		{
			bet = (int)(Math.random()*1)+1;	
			ai.aiBetCoin(bet);
			return bet;
		}
	}
	

}
