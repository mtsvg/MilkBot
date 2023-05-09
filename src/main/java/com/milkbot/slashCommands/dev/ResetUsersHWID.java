package com.milkbot.slashCommands.dev;

import com.milkbot.MilkBot;
import com.milkbot.slashCommands.Command;
import com.milkbot.data.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class ResetUsersHWID extends Command {

    public ResetUsersHWID(MilkBot bot) {
        super(bot);

        this.name = "reset-a-users-hwid";
        this.description = "Clears a hardware lock";
        this.devOnly = true;
        this.args.add(new OptionData(OptionType.USER, "user", "User you want to modify", true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Database db = bot.getDatabase();


        if (db.resetHWID(event.getOption("user").getAsUser())) {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s HWID has been reset.").queue();
        } else {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s HWID has not been reset.").queue();
        }
    }
}
