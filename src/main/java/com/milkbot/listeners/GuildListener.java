package com.milkbot.listeners;

import com.milkbot.MilkBot;
import com.milkbot.data.Database;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * This is an event listener. All events that are generic to the guild happen here.
 * <p>
 * Events are how Discord interacts with bots. Anything that happens in Discord is sent
 * to the bot as an event which we can use to do cool things like this.
 *
 * @author Freyr
 */
public class GuildListener extends ListenerAdapter {

    private final MilkBot bot; // This allows us to get access to the database

    /**
     * This is an event listener. All events that are generic to the guild happen here.
     * <p>
     * Events are how Discord interacts with bots. Anything that happens in Discord is sent
     * to the bot as an event which we can use to do cool things like this.
     *
     * @param bot We send in an instance of the bot so that we have access to the database
     * @author Freyr
     */
    public GuildListener(MilkBot bot) {
        this.bot = bot;
    }

    /**
     * This event fires everytime a user enters a server
     *
     * @param event Has all the details about the event
     */
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Database db = bot.getDatabase(); // Grabbing the database

       // db.createUserData(event.getUser()); // Creating the data for that user when they join
       // db.updateServerStatus(event.getUser().getId(), event.getGuild().getId(), true);
    }

    /**
     * This event fires everytime a member leaves/kicked/banned from a server
     *
     * @param event has all the details about the event
     */
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

    }


    /**
     * remove accounts that are no longer enthusiasts
     * @param event
     */
    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        for(Role role : event.getRoles()){
            if(role.getId().equals("1097253047643676743")
                    || role.getId().equals("1099921132192288849")){
                Database db = bot.getDatabase(); // Grabbing the database
                db.deleteAccount(event.getUser());
            }


        }
    }




    /**
     * This method fires everytime a message is sent
     *
     * @param event Has all the information about the bot
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

    }
}
