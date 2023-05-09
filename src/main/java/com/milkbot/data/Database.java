package com.milkbot.data;

import com.milkbot.MilkBot;
import com.milkbot.util.keyGen.PasskeyGenerater;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * This class handles everything that is connected to databases.
 * THIS IS THE ONLY CLASS THAT HAS DIRECT ACCESS TO THE DATABASE
 *
 */
public class Database {

    private final MilkBot bot;
    private final String discordOwnerID = "405043391135612929"; //to receive confirmation messages
    private final String discordBotToken = "";

    /**
     * discord bot token
     */
    JDA jda = JDABuilder.createDefault(discordBotToken).build();

    private final MongoCollection<Document> accounts; // The collection of documents for users

    /**
     * Creates a connection to the database and the collections
     *
     * @param srv The connection string
     */
    public Database(String srv, MilkBot bot) {
        this.bot = bot;
        MongoClient mongoClient = new MongoClient(new MongoClientURI(srv));
        MongoDatabase database = mongoClient.getDatabase("milky");
        accounts = database.getCollection("accounts");

    }


    /**
     * creates a document in a mongodb database with the chosen fields
     * send a message to the discord owner upon completion
     * @param user the discord user gathered from a listener event
     * @return true if the account was created
     */
    public boolean createAccount(User user) {
        if (user.isBot()) return false;
        if (checkIfUserExists(user)) {
            return false;
        }

        String key = PasskeyGenerater.generatePasskey();

        accounts.insertOne(new Document(
                        "name",user.getName())
                .append("userID", user.getId())
                .append("passkey", key)
                .append("instance1",'0')
                .append("instance2",'0')
                .append("lastlogin",'0')
                .append("startdate",'0')
                .append("timezone",'0')
                .append("hwid",'0'));


        user.openPrivateChannel().queue(channel -> {
            // send the message
            channel.sendMessage("Hello! Thanks for subscribing! " +
                    "\n \nYour key is: " + key +
                    "\n \nTYTYTYTY :milk: :heart:").queue();
        });

        //message to discord server owner for confirmation
        jda.getUserById(discordOwnerID).openPrivateChannel().queue(channel -> {
            // send the message
            channel.sendMessage("Hello! " + user.getName() + " has created a key" +
                    "\n \nThe key is: " + key).queue();
        });

        return true;
    }


    /**
     * updates a field in the databse, in this case i update "hwid" to the string value of "0"
     * send a message to the discord owner upon completion
     * @param user the discord user gathered from a listener event
     * @return true if the account was created
     */
    public boolean resetHWID(User user) {
        if (user.isBot()) return false;
        if (checkIfUserExists(user)) {
            Document query = new Document("userID", user.getId());

            //UpdateOptions options = new UpdateOptions().arrayFilters(List.of(Filters.eq("ele.guildID", guildId)));

            Bson updates = Updates.set("hwid", "0");

            accounts.updateOne(query, updates);

            user.openPrivateChannel().queue(channel -> {
                // send the message
                channel.sendMessage("Hello! Your hardware id has been cleared! " +
                        "\n \n:milk: :heart:").queue();
            });

            //message to discord server owner for confirmation
            jda.getUserById(discordOwnerID).openPrivateChannel().queue(channel -> {
                // send the message
                channel.sendMessage("Hello! " + user.getName() + " has cleared their HWID").queue();
            });

            return true;
        }
        return false;
    }


    /**
     * updates a field in the databse, in this case i update "instance1" to the string value of "0"
     * and "instance2" to the string value of "0"
     * send a message to the discord owner upon completion
     * @param user the discord user gathered from a listener event
     * @return true if the field was updated
     */
    public boolean clearInstances(User user) {
        if (user.isBot()) return false;
        if (checkIfUserExists(user)) {
            Document query = new Document("userID", user.getId());

            //UpdateOptions options = new UpdateOptions().arrayFilters(List.of(Filters.eq("ele.guildID", guildId)));

            Bson updates = Updates.set("instance1", "0");
            Bson updates2 = Updates.set("instance2", "0");


            accounts.updateOne(query, updates);
            accounts.updateOne(query, updates2);


            //message to discord server owner for confirmation
            jda.getUserById(discordOwnerID).openPrivateChannel().queue(channel -> {
                // send the message
                channel.sendMessage("Hello! " + user.getName() + " has had their instances cleared").queue();
            });

            return true;
        }
       return false;
    }

    /**
     * deletes and enitre document from the databse
     * i use this to delete the account when discord removes the users premium role
     * send a message to the discord owner upon completion
     * @param user the discord user gathered from a listener event
     * @return true if the account is deleted
     */
    public boolean deleteAccount(User user) {
        if (user.isBot()) return false;
        if (checkIfUserExists(user)) {
            Document query = new Document("userID", user.getId());
            String name = user.getName();




            //accounts.findOneAndDelete(query);
            accounts.deleteMany(query);

            user.openPrivateChannel().queue(channel -> {
                // send the message
                channel.sendMessage("Hello! Your subscription has ended! " +
                        "\nThank you for milking" +
                        " \n:milk: :heart:").queue();
            });


            //message to discord server owner for confirmation
            jda.getUserById(discordOwnerID).openPrivateChannel().queue(channel -> {
                // send the message
                channel.sendMessage("Hello! " + name + "'s subscription has ended and their account has been deleted").queue();
            });
            return true;
        }


        return false;
    }




    public FindIterable<Document> getAllUsers() {
        return accounts.find();
    }



    public Document getUser(String userId) {
        return accounts.find(new Document("userID", userId)).first();
    }


    private boolean checkIfUserExists(User user) {
        FindIterable<Document> iterable = accounts.find(new Document("userID", user.getId()));
        return iterable.first() != null;
    }
}
