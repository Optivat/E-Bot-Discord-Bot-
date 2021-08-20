package events;

import essentials.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NicknameChangingSystem extends ListenerAdapter {
	
	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
	String[] args = e.getMessage().getContentRaw().split("\\s+");
	if(args[0].equalsIgnoreCase(main.prefix + "setupnicknamesystem") && e.getAuthor().getId().equalsIgnoreCase("263066454004465665")) {
		if(Main.prop.get("nicknamechannel") != null) {
			
		} else {
			
		}
		}
	}
}
