package events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import essentials.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemeSubmissionsSystem extends ListenerAdapter {

	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(main.prefix + "startsubmissions") && (e.getAuthor().getId().equalsIgnoreCase("263066454004465665") || e.getMember().isOwner())) {
			int finalmessagereactions = -1;
			Message finalmessage = null;
			TextChannel channel = e.getGuild().getTextChannelById("767950319031681065");
			System.out.println(channel.getName());
			List<Message> messages = channel.getHistoryFromBeginning(100).complete().getRetrievedHistory();
			System.out.println(messages.size());
			for (int i = 0; i < messages.size(); i++) {
				if(!(messages.get(i).getReactions().isEmpty())) {
					System.out.println(messages.get(i).getReactions().get(0).retrieveUsers().complete().size());	
				}
				if (!(messages.get(i).getReactions().isEmpty()) && messages.get(i).getReactions().get(0).retrieveUsers().complete().size() > finalmessagereactions) {
					finalmessage = messages.get(i);
					finalmessagereactions = messages.get(i).getReactions().get(0).retrieveUsers().complete().size();
				}
			}
			e.getGuild().getTextChannelById("767950353928290316").sendMessage("Credit to <@!" + finalmessage.getAuthor().getId() + ">\n" + finalmessage.getAttachments().get(0).getProxyUrl()).queue();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
			LocalDateTime now = LocalDateTime.now();  
			e.getGuild().getTextChannelById("767950353928290316").sendMessage(dtf.format(now).toString()).queue();
			//e.getChannel().sendMessage("Credit to <@!" + finalmessage.getAuthor().getId() + ">\n" + finalmessage.getAttachments().get(0).getProxyUrl()).queue();
			//e.getChannel().sendMessage("From now on I shall be the one deciding who gets in #meme-of-the-week, better watch out fool.").queue();
			for (int i = 0; i < messages.size(); i++) {
				messages.get(i).delete().queue();
			}
		}
	}
}
