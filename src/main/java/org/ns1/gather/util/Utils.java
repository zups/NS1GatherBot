package org.ns1.gather.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiLoader;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.EmbedBuilder;

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


    public static EmbedObject coolEmbed(IGuild guild) {
        EmbedBuilder embed = new EmbedBuilder();
        String tyhja = "\u200B";


        embed.withTitle("Gather #1");

        Emoji orangeSmall = EmojiManager.getForAlias(":small_orange_diamond:");
        Emoji blueSmall = EmojiManager.getForAlias(":small_blue_diamond:");

        Emoji orangeLarge = EmojiManager.getForAlias(":large_orange_diamond:");
        Emoji blueLarge = EmojiManager.getForAlias(":large_blue_diamond:");

        Emoji star = EmojiManager.getForAlias(":eight_pointed_black_star:");

        IEmoji fade = guild.getEmojiByName("fade");
        IEmoji skulk = guild.getEmojiByName("skulk");
        IEmoji gorge = guild.getEmojiByName("gorge");
        IEmoji onos = guild.getEmojiByName("onos");
        IEmoji lerk = guild.getEmojiByName("lerk");
        IEmoji commander = guild.getEmojiByName("commander");

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

        embed.appendField(blueLarge.getUnicode() + "Marines",kissamies.toString(), true);
        embed.appendField(orangeLarge.getUnicode() + "Aliens", koiramies.toString(), true);

        String stars = star.getUnicode() + star.getUnicode() +star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode();

        embed.appendField(stars,stars, false);

        embed.appendField("First map:", "ns_origin", true);
        embed.appendField("Second map(if wanted):", "ns_veil", true);
        embed.withImage("https://i.imgur.com/PF8E8o9.gif");

        return embed.build();
    }

}
