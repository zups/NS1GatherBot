package org.ns1.gatherbot.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ns1.gatherbot.emoji.LifeformEmojis;

@UtilityClass
public class Utils {

    public static Optional<String> readFieldFromJson(String filepath, String fieldName) {
        JSONParser parser = new JSONParser();
        Optional<String> field = Optional.empty();

        try (Reader is = new FileReader(filepath)) {
            Object obj = parser.parse(is);
            JSONObject jsonObject = (JSONObject) obj;
            field = Optional.of((String) jsonObject.get(fieldName));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return field;
    }

    public static Optional<JSONObject> readJson(String filepath) {
        JSONParser parser = new JSONParser();
        Optional<JSONObject> jsonObject = Optional.empty();

        try (Reader is = new FileReader(filepath)) {
            Object obj = parser.parse(is);
            jsonObject = Optional.of((JSONObject) obj);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static void removeEmotesFromMessage(Message message, String emoteName) {
        message.getReactions().forEach(react -> {
            if (react.getReactionEmote().getName().equals(emoteName))
                react.getUsers().forEach(userReact ->
                        react.removeReaction(userReact).queue()
                );
        });
    }

    public static void removeEmoteFromMessageByUser(Message message, User user, String emoteName) {
        message.getReactions().forEach(react -> {
            if (react.getReactionEmote().getName().equals(emoteName))
                react.removeReaction(user).queue();
        });
    }

    public static MessageEmbed coolEmbed(JDA jda) {
        LifeformEmojis lifeformEmojis = LifeformEmojis.getEmojis();
        EmbedBuilder embed = new EmbedBuilder();
        String tyhja = "\u200B";

        embed.setTitle("Gather #1");

        Emoji orangeSmall = EmojiManager.getForAlias(":small_orange_diamond:");
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");

        Emoji orangeLarge = EmojiManager.getForAlias(":large_orange_diamond:");
        Emoji blueLarge = EmojiManager.getForAlias(":large_blue_diamond:");

        Emoji star = EmojiManager.getForAlias(":eight_pointed_black_star:");

        String fade = lifeformEmojis.getEmote("fade").get().getAsMention();
        String skulk = lifeformEmojis.getEmote("skulk").get().getAsMention();
        String gorge = lifeformEmojis.getEmote("gorge").get().getAsMention();
        String onos = lifeformEmojis.getEmote("onos").get().getAsMention();
        String lerk = lifeformEmojis.getEmote("lerk").get().getAsMention();
        String commander = lifeformEmojis.getEmote("commander").get().getAsMention();

        // Emoji commander = ReactionEmoji.of("commander", 428946828487295008L);

        StringBuilder kissamies = new StringBuilder();
        kissamies.append(blueSmall.getUnicode() + "Tane" + fade + "\n");
        kissamies.append(blueSmall.getUnicode() + "zups" + skulk + fade + "\n");
        kissamies.append(blueSmall.getUnicode() + "zh" + commander + skulk + "\n");
        kissamies.append(blueSmall.getUnicode() + "EisTee" + gorge + "\n");
        kissamies.append(blueSmall.getUnicode() + "kingyo" + lerk + "\n");
        kissamies.append(blueSmall.getUnicode() + "mulkmulkmulk" + lerk + onos);


        StringBuilder koiramies = new StringBuilder();
        koiramies.append(orangeSmall.getUnicode() + "Jiriki" + commander + "\n");
        koiramies.append(orangeSmall.getUnicode() + "ADHD" + fade + skulk + "\n");
        koiramies.append(orangeSmall.getUnicode() + "vartija" + skulk + fade + "\n");
        koiramies.append(orangeSmall.getUnicode() + "skipjack" + lerk + "\n");
        koiramies.append(orangeSmall.getUnicode() + "larks tongue in aspic" + skulk + gorge + "\n");
        koiramies.append(orangeSmall.getUnicode() + "x-man" + commander + gorge);

        embed.addField(blueLarge.getUnicode() + "Marines", kissamies.toString(), true);
        embed.addField(orangeLarge.getUnicode() + "Aliens", koiramies.toString(), true);

        String stars = star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode();

        embed.addField(stars, stars, false);

        embed.addField("First map:", "ns_origin", true);
        embed.addField("Second map(if wanted):", "ns_veil", true);
        embed.setImage("https://i.imgur.com/PF8E8o9.gif");

        return embed.build();
    }

}
