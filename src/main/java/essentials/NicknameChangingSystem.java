package essentials;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NicknameChangingSystem extends ListenerAdapter {
	
	Main main = new Main();

	String[] profanitylist = {"fuck","bitch","shit","sex","nigga","ass","slut","nigger"};
	
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
		if(e.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
			e.getMember().getUser().openPrivateChannel().queue(channel -> {
				EmbedBuilder eb2 = new EmbedBuilder();
				eb2.setTitle("Nickname Change Error!", null);
				eb2.setColor(Color.red);
				eb2.setDescription("Unable to change nickname from " + e.getMember().getNickname() + " to " + nickname + "due to not being able to modify you, perhaps you have a superior role than the bot?");
				channel.sendMessageEmbeds(eb2.build()).queue();
			});
		} else {
			boolean inappropiatenickame = false;
			for(int x=0; x<profanitylist.length - 1; x++) {
				if(nickname.contains(profanitylist[x])) {
					inappropiatenickame = true;
				}
			}
			if(inappropiatenickame == true) {
				e.getMember().getUser().openPrivateChannel().queue(channel -> {
					EmbedBuilder eb2 = new EmbedBuilder();
					eb2.setTitle("Nickname Change Error!", null);
					eb2.setColor(Color.red);
					eb2.setDescription("Unable to change your nickname from " + e.getMember().getNickname() + " to " + nickname + "due to containing a form of profanity. Please consider using a different name.");
					channel.sendMessageEmbeds(eb2.build()).queue();
				});
			} else {
				if(nickname.length() >= 32)  {
					e.getMember().getUser().openPrivateChannel().queue(channel -> {
						EmbedBuilder eb2 = new EmbedBuilder();
						eb2.setTitle("Nickname Change Error!", null);
						eb2.setColor(Color.red);
						eb2.setDescription("Unable to change your nickname from " + e.getMember().getNickname() + " to " + nickname + "due to it being more than 32 characters, please decrease it.");
						channel.sendMessageEmbeds(eb2.build()).queue();
					});
				} else {
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
	}
	
	}
}
