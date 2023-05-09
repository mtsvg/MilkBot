package com.milkbot.slashCommands.utility;

import com.milkbot.MilkBot;
import com.milkbot.data.Database;
import com.milkbot.slashCommands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ResetHWID extends Command {

    public ResetHWID(MilkBot bot) {
        super(bot);

        this.name = "reset-hwid";
        this.description = "Clears your hardware lock";
        this.devOnly = false;
        this.cooldown = 10800;
        //this.args.add(new OptionData(OptionType.USER, "user", "User you want to add", true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Database db = bot.getDatabase();


        if (db.resetHWID(event.getUser())) {
            event.getHook().sendMessage(event.getUser().getName() + "'s HWID has been reset.").queue();
        } else {
            event.getHook().sendMessage(event.getUser().getName() + "'s HWID has not been reset.").queue();
        }
    }
}
