package com.milkbot.slashCommands.dev;

import com.milkbot.MilkBot;
import com.milkbot.slashCommands.Command;
import com.milkbot.data.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class ClearInstances extends Command {

    public ClearInstances(MilkBot bot) {
        super(bot);

        this.name = "clear-instances";
        this.description = "Clears intances";
        this.devOnly = true;
        this.args.add(new OptionData(OptionType.USER, "user", "User you want to modify", true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Database db = bot.getDatabase();


        if (db.clearInstances(event.getOption("user").getAsUser())) {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s instances have been reset.").queue();
        } else {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s instances have not been reset.").queue();
        }
    }
}
