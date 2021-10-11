package essentials;

import events.BasicAdminCommands;
import events.BasicCommands;
import events.MemeSubmissionsSystem;
import events.NicknameChangingSystem;
import events.TicketSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.security.auth.login.LoginException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
	public String prefix = "josh$";
	public static Properties prop = new Properties();
	
	public static void main(String[] args) throws LoginException {
		//Jframe
		JFrame jframe = new JFrame();
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		label.setText("Working successfully!");
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new GridLayout(0,1));
		panel.add(label);
		
		jframe.add(panel, BorderLayout.CENTER);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(300,100);
		jframe.setTitle("E Bot (Discord Bot)");
		jframe.setResizable(false);
		//Loading properties file
		if(Methods.testFileDire(System.getProperty("user.home") + "/Optivat's Inc/Discord E Bot/config.properties") == false) {
			Methods.createPropFile("/Optivat's Inc/Discord E Bot/config.properties");
			System.out.println("File has been made!");
		}
		System.out.println(System.getProperty("user.home") + "/Optivat's Inc/Discord E Bot/config.properties");
		FileInputStream ip = null;
		try {ip = new FileInputStream(System.getProperty("user.home") + "/Optivat's Inc/Discord E Bot/config.properties");System.out.println("Config file loaded successfully!");} catch (FileNotFoundException e) {System.out.println("Config file loading failed!"); e.printStackTrace();}
		try {prop.load(ip);System.out.println("Config file initialization is succesful!");}catch (IOException e) {System.out.println("Config file initialization failed!"); e.printStackTrace();}
		
		//E Bot
		String token = "NzMwODMyMTY2Njk4ODc2OTY5.XwdOfg.2R3Zx3UgtdcTqiqjfSBSLHu7_wM";
		//Beta E Bot
		//String token = "ODIyMTcyNzE0MzcwMzM0NzIw.YFOaAg.uU5QG3hC-bGFVnFda7K7jyGN5W4";
		JDA jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGE_REACTIONS).build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.listening("Mario's stream"));
		jda.addEventListener(new TicketSystem(), new BasicAdminCommands(), new NicknameChangingSystem(), new MemeSubmissionsSystem(), new BasicCommands());
	}
}
