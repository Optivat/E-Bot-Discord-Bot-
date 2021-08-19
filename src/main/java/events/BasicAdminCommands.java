package events;

import essentials.Main;
import essentials.Methods;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BasicAdminCommands extends ListenerAdapter {
	Main main = new Main();

	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

		//This is so cool!
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(args[0].equalsIgnoreCase(main.prefix + "setmodrole") && (e.getMember().isOwner() || e.getAuthor().getId().equalsIgnoreCase("263066454004465665"))) {
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
		}
		if(args[0].equalsIgnoreCase(main.prefix + "setadminrole") && (e.getMember().isOwner() || e.getAuthor().getId().equalsIgnoreCase("263066454004465665"))) {
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
		}
	}
}
