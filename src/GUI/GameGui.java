package GUI;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.skhu.Game;

public interface GameGui {

	JPanel changeView(Game game);
	JLabel changeImage(List list,int check);
}
