package com.milkbot.listeners;

import com.milkbot.data.Database;
import com.milkbot.MilkBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


/**
 * This is a bot Event Listener. Anytime something happens in a server, it is registered as an event for the bot.
 * All events related to the bot should be coded here.
 *
 * @author Freyr
 */
public class BotListener extends ListenerAdapter {
    Database database;


    private final MilkBot bot; // This is used to access the database

    /**
     * This is a bot Event Listener. Anytime something happens in a server, it is registered as an event for the bot.
     * All events related to the bot should be coded here.
     *
     * @param bot Getting the bot here so that we have access to the database classes.
     */
    public BotListener(MilkBot bot) {
        this.bot = bot;
    }



    /**
     * This event fires everytime the bot is invited to a new guild
     *
     * @param event Has all the details about the event
     */
    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }


    /**
     * Fires when a reaction is made to a message
     * @param event deatils
     */
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {


        if(!event.getChannel().getId().equals("1095644803405971516")){
            return;
        }

        User user = event.getUser();

        bot.getDatabase().createAccount(user);

    }


    /**
     * This event fires everytime a message is sent in a guild
     *
     * @param event Has all the details about the event
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //bot.getDatabase().createUserData(event.getAuthor());
    }


}
