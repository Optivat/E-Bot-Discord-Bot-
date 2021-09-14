package events;

import essentials.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemeSubmissionsSystem extends ListenerAdapter {

	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		int finalmessagereactions = -1;
		String finalmessage = "";
		TextChannel channel = e.getGuild().getTextChannelById("822481555057410068");
		if(args[0].equalsIgnoreCase(main.prefix + "startsubmissions") && e.getAuthor().getId().equalsIgnoreCase("263066454004465665")) {
			while (channel.getLatestMessageId() != null) {
				String currentmessageid = channel.getLatestMessageId();
				Message message = channel.retrieveMessageById(currentmessageid).complete();
				if(message.getReactions().size() > finalmessagereactions) {
					finalmessage = message.getId();
					finalmessagereactions = message.getReactions().size();
				}
			}
			e.getChannel().sendMessage(finalmessage)
		}
	}
}
