package events;
import java.awt.Color;
import java.util.EnumSet;

import essentials.Main;
import essentials.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketSystem extends ListenerAdapter {
	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(main.prefix + "setupticketsystem") && e.getAuthor().getId().equalsIgnoreCase("263066454004465665")) {
			e.getChannel().sendMessage("Working...").queue();
			if(Main.prop.get("modroleid") == null || Main.prop.get("adminroleid") == null) {
				e.getChannel().sendMessage("Error! Please make sure you set both the Mod Role and the Admin Role using the commands. Do, " + main.prefix + "adminhelp, to get the list of commands to set it up!").queue();
			} else {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Tickets", null);
				eb.setColor(Color.red);
				eb.setDescription("You need help or want to complain to a superior? This ticket system will allow you to be in a 1 on 1 with a admin to exchange words between each other.");
				if(Main.prop.getProperty("ticketscategory") == null) {
					String categoryid = e.getGuild().createCategory("Tickets").complete().getId();
					Methods.writeProp("ticketscategory", categoryid);
					String channelid = e.getGuild().getCategoryById(categoryid).createTextChannel("🎫Tickets🎫").addPermissionOverride(e.getGuild().getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE)).complete().getId();
					String messageid = e.getGuild().getTextChannelById(channelid).sendMessageEmbeds(eb.build()).complete().getId();
					e.getGuild().getTextChannelById(channelid).addReactionById(messageid, "🎫").queue();
					Methods.writeProp("ticketmessage", messageid);
					Methods.writeProp("ticketchannel", channelid);
				}
				e.getChannel().sendMessage("Done!").queue();	
			}
		}
	}
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		if(e.getReactionEmote().isEmoji() == false) {return;}
		if(e.getReactionEmote().getEmoji().equalsIgnoreCase("🎫") && e.getMessageId().equalsIgnoreCase((String) Main.prop.get("ticketmessage")) && e.getUser().isBot() == false) {
			e.getReaction().removeReaction(e.getUser()).queue();
			String ticket;
			if(Main.prop.getProperty("totalticketsmade") == null) {
				Methods.writeProp("totalticketsmade", "0");
			}
			int totalticketsmade = Integer.parseInt(Main.prop.getProperty("totalticketsmade"))+1;
			Methods.writeProp("totalticketsmade", String.valueOf(totalticketsmade));
			ticket = "sussy-ticket" + Main.prop.getProperty("totalticketsmade");
			String channelid = e.getGuild().getCategoryById(Main.prop.getProperty("ticketscategory")).createTextChannel(ticket)
			.addPermissionOverride(e.getGuild().getPublicRole(), null, EnumSet.of(Permission.MESSAGE_READ))
			.addPermissionOverride(e.getMember(), EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE), null)
			.addPermissionOverride(e.getGuild().getRoleById(Main.prop.getProperty("modroleid")), EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE), null)
			.addPermissionOverride(e.getGuild().getRoleById(Main.prop.getProperty("adminroleid")), EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE), null).complete().getId();
			EmbedBuilder eb1 = new EmbedBuilder();
			eb1.setTitle("Ticket Channel", null);
			eb1.setColor(Color.red);
			eb1.setDescription("Dicuss words here! Once the conversation has ended both parties may delete the text channel by clicking on the lock emoji.");
			String messageid = e.getGuild().getTextChannelById(channelid).sendMessageEmbeds(eb1.build()).complete().getId();
			e.getGuild().getTextChannelById(channelid).sendMessage("<@!" + e.getMember().getId() + ">").queue();
			e.getGuild().getTextChannelById(channelid).addReactionById(messageid, "🔒").queue();
			e.getUser().openPrivateChannel().queue(channel -> {
				EmbedBuilder eb2 = new EmbedBuilder();
				eb2.setTitle("New Ticket Opened!", null);
				eb2.setColor(Color.red);
				eb2.setDescription("You've opened a ticket: #" + ticket);
				channel.sendMessageEmbeds(eb2.build()).queue();
			});
		}
		if(e.getReactionEmote().getEmoji().equalsIgnoreCase("🔒") && e.getUser().isBot() == false && e.getTextChannel().getName().contains("sussy-ticket")) {
			e.getGuild().getGuildChannelById(e.getChannel().getId()).delete().queue();
		}
	}
}
