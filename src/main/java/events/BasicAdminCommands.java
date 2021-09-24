package events;

import essentials.Main;
import essentials.Methods;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BasicAdminCommands extends ListenerAdapter {
	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(args[0].startsWith(main.prefix) && (e.getMember().isOwner() || e.getAuthor().getId().equalsIgnoreCase("263066454004465665"))) {
			switch(args[0].replace(main.prefix, " ").trim()) {
			case "setmodrole":
				if(args.length == 2) {
					if (e.getGuild().getRoleById(args[1]) != null) {
						e.getChannel().sendMessage("The role, **" + e.getGuild().getRoleById(args[1]).getName() + "**, is now the moderating role.").queue();
						Methods.writeProp("modroleid", args[1]);
					} else {
						e.getChannel().sendMessage("The moderator id is invalid, please search up online on how to get the role ID of a role if you are having issues.").queue();
					}
				} else {
					e.getChannel().sendMessage("Invalid arguments! !setmodrole (role id)").queue();
				}
				break;
			case "setadminrole":
				if(args.length == 2) {
					if (e.getGuild().getRoleById(args[1]) != null) {
						e.getChannel().sendMessage("The role, **" + e.getGuild().getRoleById(args[1]).getName() + "**, is now the administrating role.").queue();
						Methods.writeProp("adminroleid", args[1]);
					} else {
						e.getChannel().sendMessage("The administrator id is invalid, please search up online on how to get the role ID of a role if you are having issues.").queue();
					}
				} else {
					e.getChannel().sendMessage("Invalid arguments! !setadminrole (role id)").queue();
				}
				break;
			case "unmute":
				if(args.length == 2) {
					System.out.println(args[1]);
					if(!(e.getGuild().getMemberById(args[1]).equals(null))) {
						if(e.getGuild().getMemberById(args[1]).getVoiceState().isGuildMuted()) {
							e.getGuild().mute(e.getGuild().getMemberById(args[1]), false).queue();
							e.getChannel().sendMessage("You are now no longer server muted!").queue();
						} else {
							e.getChannel().sendMessage("You aren't server muted!").queue();s
						}
					} else {
						e.getChannel().sendMessage("Invalid member id! !unmuteme (member id)").queue();
					}
				} else {
					e.getChannel().sendMessage("Invalid arguments! !unmuteme (member id)").queue();
				}
				break;
			case "undeafen":
				if(args.length == 2) {
					if(e.getGuild().getMemberById(args[1]) != null) {
						if(e.getGuild().getMemberById(args[1]).getVoiceState().isGuildDeafened()) {
							e.getGuild().deafen(e.getGuild().getMemberById(args[1]), false).queue();
							e.getChannel().sendMessage("You are now no longer server defeaned!").queue();
						} else {
							e.getChannel().sendMessage("You aren't server defeaned").queue();
						}
					} else {
						e.getChannel().sendMessage("Invalid member id! !unmuteme (member id)").queue();
					}
				} else {
					e.getChannel().sendMessage("Invalid arguments! !unmuteme (member id)").queue();
				}
				break;
			case "sendmessage":
				if (args.length>=3) {
					System.out.println(args[1]);
					if(Methods.testIfLettersInString(args[1]) == false && e.getGuild().getTextChannelById(Long.parseLong(args[1])) != null) {
						TextChannel channel = e.getGuild().getTextChannelById(args[1]);
						String message = e.getMessage().getContentRaw().replace(args[0], " ").replace(args[1], " ").strip();
						channel.sendMessage(message).queue();
					} else {
						e.getChannel().sendMessage("The channel id is invalid.").queue();
					}
				}
				break;
			default:
				return;
				
			}
		}
	}
}
