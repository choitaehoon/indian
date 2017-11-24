package net.skhu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ai {
	private List<Integer> cardList;
	private List<Integer> usercardList;
	private int coin;
	
	public Ai(){
		cardList=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		usercardList = new ArrayList<>();
		coin = 20;
		Collections.shuffle(cardList);
	}
	public List<Integer> getCard(){
		return cardList;
	}
	public void setUserCard(int usercard){
		usercardList.add(usercard);
	}
	public int number(Game game){
		return usercardList.get(game.getRound()-1);
	}
	public int getAiCoin() {
		return coin;
	}
	public int aiBetCoin(int coin)
	{
		return this.coin -=coin;
	}
	
}
