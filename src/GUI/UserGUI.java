package GUI;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.skhu.Game;

public class UserGUI {
	
	private JPanel panel;
	
	public JPanel changeView(Game game,int check){
		panel = new JPanel();
		JLabel userCard =new JLabel("");
		panel.setLayout(null);
		panel.setBounds(0,0,250,280);
		panel.add(changeImage(game.getUser().card(),check,game.getRound()));
		return panel;
	}
	public JLabel changeImage(List list,int check,int round){
		if(check ==0){
			JLabel userCard =new JLabel("");
			ImageIcon icon=new ImageIcon(getClass().getResource("/image/back.png"));
			userCard.setIcon(icon);
			icon.setImage(icon.getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
			userCard.setBounds(25, 35, 250, 250);
			return userCard;

		}
		else{
			JLabel userCard =new JLabel("");
			ImageIcon icon=new ImageIcon(getClass().getResource("/image/1_"+list.get(round-1)+".png"));
			userCard.setIcon(icon);
			icon.setImage(icon.getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
			userCard.setBounds(25, 35, 250, 250);
			return userCard;
		}
	}
	public JPanel im(Game game){
		return panel;
	}
}
