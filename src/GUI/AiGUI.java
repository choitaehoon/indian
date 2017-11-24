package GUI;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.skhu.Game;

public class AiGUI extends JFrame implements GameGui {
	
	private ImageIcon icon;
	private JLabel jlabel;
	private JPanel panel;

	@Override
	public JPanel changeView(Game game) {
		panel = new JPanel(null);
		panel.setBounds(270,0,250,280);
		panel.add(changeImage(game.getAi().getCard(),game.getRound()));
		
		return panel;
	}

	@Override
	public JLabel changeImage(List list,int round) {
		// TODO Auto-generated method stub
		
		jlabel =new JLabel("");
		icon=new ImageIcon(getClass().getResource("/image/2_"+list.get(round-1)+".png"));
		jlabel.setIcon(icon);
		icon.setImage(icon.getImage().getScaledInstance(180, 200, Image.SCALE_SMOOTH));
		jlabel.setBounds(10, 35, 250, 250);
		
		return jlabel;
	}
	public JPanel im(Game game){
		return panel;
	}
	
	
	
	

}
