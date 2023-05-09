package com.milkbot.slashCommands.dev;

import com.milkbot.MilkBot;
import com.milkbot.slashCommands.Command;
import com.milkbot.data.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class DeleteAccount extends Command {

    public DeleteAccount(MilkBot bot) {
        super(bot);

        this.name = "delete-account";
        this.description = "Delete a users account";
        this.devOnly = true;
        this.args.add(new OptionData(OptionType.USER, "user", "User you want to modify", true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Database db = bot.getDatabase();


        if (db.deleteAccount(event.getOption("user").getAsUser())) {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s account has been deleted.").queue();
        } else {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s account has already been deleted.").queue();
        }
    }
}
