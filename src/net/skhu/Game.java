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

}
