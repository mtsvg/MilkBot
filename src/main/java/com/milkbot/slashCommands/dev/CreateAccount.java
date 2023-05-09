package com.milkbot.slashCommands.dev;

import com.milkbot.MilkBot;
import com.milkbot.data.Database;
import com.milkbot.util.keyGen.PasskeyGenerater;
import com.milkbot.slashCommands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CreateAccount extends Command {

    public CreateAccount(MilkBot bot) {
        super(bot);

        this.name = "create-user";
        this.description = "Creates a user within the database";
        this.devOnly = true;

        this.args.add(new OptionData(OptionType.USER, "user", "User you want to add", true));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Database db = bot.getDatabase();
        String key = PasskeyGenerater.generatePasskey();

        if (db.createAccount(event.getOption("user").getAsUser())) {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s data has been created.").queue();
        } else {
            event.getHook().sendMessage(event.getOption("user").getAsUser().getName() + "'s data already exists.").queue();
        }
    }
}
