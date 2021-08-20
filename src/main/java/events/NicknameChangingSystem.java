package events;

import java.awt.Color;

import essentials.Main;
import essentials.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NicknameChangingSystem extends ListenerAdapter {
	
	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
	String[] args = e.getMessage().getContentRaw().split("\\s+");
	if(args[0].equalsIgnoreCase(main.prefix + "togglenicknamesystem") && e.getAuthor().getId().equalsIgnoreCase("263066454004465665")) {
		if(Main.prop.get("nicknamechannel") != null) {
			if(e.getGuild().getTextChannelsByName("nickname-request", true).isEmpty()) {
				String nicknamechannelid = e.getGuild().createTextChannel("nickname-request").complete().getId();
				e.getGuild().getTextChannelById(nicknamechannelid).sendMessage("This is the nickname-request channel, all you have to do is send a message and that will change your nickname, there is a 5 minute cooldown between nicknames. **Inapropriate nicknames isn't allowed**");
				Methods.writeProp("nicknamechannel", nicknamechannelid);
			} else {
				e.getChannel().sendMessage("It has already been set up!").queue();		
				}
		} else {
			if(e.getGuild().getTextChannelsByName("nickname-request", true).isEmpty()) {
				String nicknamechannelid = e.getGuild().createTextChannel("nickname-request").complete().getId();
				e.getGuild().getTextChannelById(nicknamechannelid).sendMessage("This is the nickname-request channel, all you have to do is send a message and that will change your nickname, there is a 5 minute cooldown between nicknames. **Inapropriate nicknames isn't allowed**");
				Methods.writeProp("nicknamechannel", nicknamechannelid);	
				} else {
					Methods.writeProp("nicknamechannel", e.getGuild().getTextChannelsByName("nickname-request", true).get(0).getId());
				}
			}
		}
	
	if(Main.prop.get("nicknamechannel") != null && e.getChannel().getId().equalsIgnoreCase(Main.prop.getProperty("nicknamechannel"))) {
		e.getMessage().delete().queue();
		String nickname = e.getMessage().toString().trim();
		e.getMember().getUser().openPrivateChannel().queue(channel -> {
			EmbedBuilder eb2 = new EmbedBuilder();
			eb2.setTitle("Nickname Change", null);
			eb2.setColor(Color.red);
			eb2.setDescription("You've changed your nickname from " + e.getMember().getNickname() + " to " + nickname);
			channel.sendMessageEmbeds(eb2.build()).queue();
		});
	}
	
	}
}
