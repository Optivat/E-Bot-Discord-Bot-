package events;

import java.io.File;
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
			TextChannel submissionschannel = e.getGuild().getTextChannelById("767950319031681065");
			TextChannel memoftheweekchannel = e.getGuild().getTextChannelById("767950353928290316");
			System.out.println(submissionschannel.getName());
			List<Message> messages = submissionschannel.getHistoryFromBeginning(100).complete().getRetrievedHistory();
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
			if (e.getMessage().getAttachments().isEmpty()) {
				memoftheweekchannel.sendMessage("Credit to <@!" + finalmessage.getAuthor().getId() + ">\n" + finalmessage.getContentRaw()).queue();
			} else {
				e.getMessage().getAttachments().get(0).downloadToFile(System.getProperty("user.home") + "/Optivat's Inc/Discord E Bot/");
				memoftheweekchannel.sendMessage("Credit to <@!" + finalmessage.getAuthor().getId() + ">").queue();
				File file = new File(System.getProperty("user.home") + "/Optivat's Inc/Discord E Bot/" + e.getMessage().getAttachments().get(0).getFileName());
				memoftheweekchannel.sendFile(file).queue();
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
			LocalDateTime now = LocalDateTime.now();  
			memoftheweekchannel.sendMessage(dtf.format(now).toString()).queue();
			for (int i = 0; i < messages.size(); i++) {
				messages.get(i).delete().queue();
			}
		}
	}
}
