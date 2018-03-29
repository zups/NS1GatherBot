package org.ns1.gather.util;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
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




        //embed.appendField(tyhja, heart.getUnicode(), false);

        embed.appendField(blueLarge.getUnicode() + "Marines",
                blueSmall.getUnicode() + "Tane" + "\n" +
                        blueSmall.getUnicode() + "zups\nkissa\nmarsupilami\njuha\nzuha", true);
        embed.appendField(orangeLarge.getUnicode() + "Aliens", orangeSmall.getUnicode() + "Tane" + "\n" + orangeSmall.getUnicode() + "zups\nkissa\nmarsupilami\njuha\nzuha", true);

        embed.appendField(tyhja, star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode() + star.getUnicode(), false);

        embed.appendField("First map:", "ns_origin", true);
        embed.appendField("Second map:", "ns_veil", true);
        embed.withImage("https://i.imgur.com/SDSJlAh.gif");

        return embed.build();
    }

}
