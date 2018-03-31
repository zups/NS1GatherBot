package org.ns1.gatherbot.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ns1.gatherbot.datastructure.Lifeforms;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

public class Utils {

    public static Optional<String> readConfig(String filepath) {
        JSONParser parser = new JSONParser();
        Optional<String> token = Optional.empty();

        try (Reader is = new FileReader(filepath)) {
            Object obj = parser.parse(is);

            JSONObject jsonObject = (JSONObject) obj;

            token = Optional.of((String) jsonObject.get("token"));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return token;
    }

//    channel.sendMessage("moro", Utils.coolEmbed(message.getGuild()));
    public static MessageEmbed coolEmbed(JDA jda) {
        Lifeforms lifeforms = new Lifeforms(jda);
        EmbedBuilder embed = new EmbedBuilder();
        String tyhja = "\u200B";

        embed.setTitle("Gather #1");

        Emoji orangeSmall = EmojiManager.getForAlias(":small_orange_diamond:");
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");

        Emoji orangeLarge = EmojiManager.getForAlias(":large_orange_diamond:");
        Emoji blueLarge = EmojiManager.getForAlias(":large_blue_diamond:");

        Emoji star = EmojiManager.getForAlias(":eight_pointed_black_star:");

        Emote fade = lifeforms.getEmote("fade").get();
        Emote skulk = lifeforms.getEmote("skulk").get();
        Emote gorge = lifeforms.getEmote("gorge").get();
        Emote onos = lifeforms.getEmote("onos").get();
        Emote lerk = lifeforms.getEmote("lerk").get();
        Emote commander = lifeforms.getEmote("commander").get();

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

        embed.addField(blueLarge.getUnicode() + "Marines",kissamies.toString(), true);
        embed.addField(orangeLarge.getUnicode() + "Aliens", koiramies.toString(), true);

        String stars = star.getUnicode() + star.getUnicode() +star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode();

        embed.addField(stars,stars, false);

        embed.addField("First map:", "ns_origin", true);
        embed.addField("Second map(if wanted):", "ns_veil", true);
        embed.setImage("https://i.imgur.com/PF8E8o9.gif");

        return embed.build();
    }

}
