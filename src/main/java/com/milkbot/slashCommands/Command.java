package com.milkbot.slashCommands;

import com.milkbot.MilkBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a basic command layout. It allows us to create commands very easily. The command manager then can use
 * this data to add commands into Discord!
 *
 * @author Freyr
 */
public abstract class Command {

    public MilkBot bot; // This gives us access to the config file which is in the main class
    public String name; // Name of the command
    public String description; // Description of the command
    public Category category; // The category it should be in within the help command
    public boolean devOnly = false; // Will restrict a command so that it can only be run by the developer
    public List<OptionData> args; // Any options the command needs goes here
    public List<SubcommandData> subCommands; // Any subcommands that the command has
    public Permission permission; // Permissions for the user
    public Permission botPermission; // Permissions the bot needs
    public int cooldown = 0;
    public int uses = 1;

    /**
     * Command Constructor.
     * Initializes args as an empty arraylist
     */
    public Command(MilkBot bot) {
        this.bot = bot;
        this.args = new ArrayList<>();
        this.subCommands = new ArrayList<>();
    }

    /**
     * This method will contain the code that we want the bot to execute when that command is called.
     *
     * @param event Has all the information about the event.
     */
    public abstract void execute(SlashCommandInteractionEvent event);

    /**
     * This command uses an API URL to grab data and return it for use
     *
     * @param apiURL The api url you want to use
     * @return All the data given through that URL
     */
    public JSONObject getApiData(String apiURL) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiURL)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This command uses an API URL to grab data and return it for use
     *
     * @param apiURL The api url you want to use
     * @param authentication The authentication key (Bearer + [key])
     * @return All the data given through that URL
     */
    public JSONObject getApiData(String apiURL, String authentication) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiURL)).header("Authorization", "Bearer " + authentication).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
