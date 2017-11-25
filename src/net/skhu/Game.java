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
	public Ai getAi(){ //ai 불러오기
		return ai;
	}
	public int getRound(){
		return round;
	}
	public void userDie(){
		ai.setUserCard((int)user.userCardDeck().get(round-1));
	}
	public User getUser(){ //user불러오기
		return user;
	}
	public void setRound(){
		round=round+1;
	}
	
	public String winner()
	{
		return user.getUserCoin() >ai.getAiCoin() ? "user가 승리하고 ai가 졌습니다" : "ai가  승리하고  user가 졌습니다";
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
		return user.getUserCoin()<=0 ? "ai가 승리하고 user가 졌습니다" : "user가 승리하고 ai가 졌습니다";
	}
	
	//ai의 배팅
	public int aiBattingBattle(int userCard) //내카드를 보고 있는 ai가 내카드가 크다면 배팅 갯수적게하고 내카드가 작다면 배팅 갯수 높임
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
