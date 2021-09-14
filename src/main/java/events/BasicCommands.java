package events;

import essentials.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BasicCommands extends ListenerAdapter {
	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(main.prefix + "unmutelance")) {
			if(e.getGuild().getMemberById("474411962474823713").getVoiceState().isGuildMuted()) {
				if(e.getMember().getId().equalsIgnoreCase("474411962474823713")) {
					e.getChannel().sendMessage("Lance you can't unumute yourself you fool.").queue();
				} else {
					e.getGuild().getMemberById("474411962474823713").mute(false).queue();
					e.getChannel().sendMessage("Lance is now unmuted, but why did you do it.").queue();
				}
			} else {
				e.getChannel().sendMessage("Lance is already unmuted bro.").queue();
			}
		}
		if(args[0].startsWith(main.prefix)) {
			switch(args[0].replace(main.prefix, " ").trim()) {
			case "mutelance":
				if(e.getGuild().getMemberById("474411962474823713").getVoiceState().isGuildMuted()) {
					e.getChannel().sendMessage("Lance is already muted bro.").queue();
				} else {
					if(e.getMember().getId().equalsIgnoreCase("474411962474823713")) {
						e.getChannel().sendMessage("You're not allowed to mute yourself lance, suffer child.").queue();
					} else {
						e.getGuild().getMemberById("474411962474823713").mute(true).queue();
						e.getChannel().sendMessage("Lance is now muted, lets goooooo.").queue();
					}
				}
				break;
			default:
				return;
			}
		}
	}
}
