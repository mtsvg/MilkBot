package com.milkbot.slashCommands.fun;

import com.milkbot.MilkBot;
import com.milkbot.slashCommands.Category;
import com.milkbot.util.embeds.EmbedColor;
import com.milkbot.slashCommands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONObject;

/**
 * This command goes through an API and gives me a random image based off of a reaction. These are all anime images
 */
public class EmoteCommand extends Command {

    // All actions available for this command
    private final String[] actions = {"angry", "blush", "bleh", "celebrate", "clap", "confused", "cry", "dance", "evillaugh", "facepalm", "happy", "laugh", "pout", "punch", "shrug", "shy", "sigh", "slowclap", "scared", "sleep", "yawn"};

    public EmoteCommand(MilkBot bot) {
        super(bot);
        this.name = "emote";
        this.description = "Get a moving emote";
        this.category = Category.FUN;

        OptionData data = new OptionData(OptionType.STRING, "action", "The action you want to perform", true);

        // Adding all the actions above as options for the user to pick from.
        data.addChoice(actions[0], "mad");
        for (int i = 1; i < actions.length; i++) {
            data.addChoice(actions[i], actions[i]);
        }

        this.args.add(data);

    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        JSONObject data = getApiData("https://api.otakugifs.xyz/gif?reaction=" + event.getOption("action").getAsString()); // Getting the data from the URL (link)

        // Building the embed
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.DEFAULT_COLOR);
        embed.setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl());
        embed.setImage(data.getString("url"));
        event.replyEmbeds(embed.build()).queue();
    }
}
