package net.purplemushroom.neverend.client.render.screen.menu.splash;

import net.minecraft.client.Minecraft;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SplashProvider {
    private static final int SPECIAL_SPLASHES = 7;

    public static final String[] SPLASHES = {
            "Purple is the new black!",
            "Nevermine in the End!",
            "A friendly perdition!",
            "Subjectively good!",
            "Woo, Discord!",
            "Woo, GitHub!",
            "Woo, CurseForge!",
            "Woo, Modrinth!",
            "Eww, 9Minecraft!",
            "Also try The Aether!",
            "Also try The Betweenlands!",
            "Also try Eternalcraft!",
            "Also try Advent of Ascension!",
            "Family friendly!",
            "Remember the cows!",
            "It never ends!",
            "Powered by TimeCore!",
            "Powered by NeoForge!",
            "The effect affects the picture!",
            "Do you want to have a bad time?",
            "I... am Steve.",
            "The Ancients have awakened!",
            "No soup for you!",
            "Mun or bust!",
            "Faster than light!",
            "To infinity, and beyond!",
            "FATALITY",
            "Finish him!",
            "Absolutely NOT dragon free!",
            "Modded!",
            "Modified!",
            "Hot!",
            "Open source!",
            "Made with love!",
            "Here's Johnny!",
            "Part of a complete breakfast!",
            "Mamma mia!",
            "It's a bird! It's a plane!",
            "Level up!",
            "A sense of pride and accomplishment!",
            "Alex has joined the party!",
            "Have you seen the §kHerobrine§r?",
            ">:(!",
            "Can't touch this!",
            "Low risk of death or serious injury!",
            "Better with friends!",
            "You've got mail!",
            "Say hello to my little friend!",
            "Timeless!",
            "You just lost The Game!",
            "It's a trap!",
            "Nothing personnel, kid!",
            "No swearing on Christian servers!",
            "Human-generated!",
            "You love to see it!",
            "Wazzup!",
            "Down the witches' road!",
            "You should have gone for the head!",
            "Weirdmageddon!",
            "I'M A' FIRIN' MAH LAZER!",
            "Noncontroversial!",
            "Welcome to WHITE SPACE.",
            "We've been trying to reach you about your car's extended warranty!",
            "This! Is! MINECRAFT!!!",
            "Which of the Pickwick triplets did it?",
            "Modern!",
            "Cowabunga!",
            "Be sure to like and subscribe!",
            "Extraterrestrial!",
            "Surreal!",
            "Uncanny!",
            "Supernatural!",
            "Interdimensional!",
            "I tawt I taw a puddy-tat!",
            "Lots of rifts!",
            "50 Shades of Purple!",
            "By using this software, you rescind your right to life.",
            "Ask again later.",
            "Totally radical!",
            "Stay in school, kids!",
            "I hate you, you hate me, we're an unhappy family!",
            "Git gud!",
            "Skill issue!",
            "Porsche with no brakes!",
            "Does whatever a spider can!",
            "Trololo!",
            "Cannot make bricks without clay!",
            "Banger!",
            "Minecraft is love, Minecraft is life!",
            "You are The Boogeyman!",
            "Infinite cake!",
            "Breaking Block!",
            "Get the banana!",
            "You have failed this city!",
            "§1A blue house with a blue window!",
            "YouTube is where the poop is!",
            "Starships were meant to fly!",
            "A rift? Localized entirely within your kitchen?",
            "This is my message to my master!",
            "Death by snu snu!",
            "Hello World!",
            "Hey! Listen!",
            "Not educational!",
            "Welcome!",
            "Hi!",
            "Hey!",
            "Heya!",
            "Hello!",
            "Made by nerds, for nerds!",
            "The (unofficial) End Update!",
            "Over 100 custom splashes!",
            "This statement is false!",
            "Sample Text",
            "Writing these is fun!",
            "Quoth the raven, \"Wacka wacka wacka wah.\"",
            "Bazinga!",
            "Epic!",
            "Only on PC!",
            "America, heck yeah!",
            "Didn't see you come in!",
            "UwU",
            "How are you feeling today?",
            "How does that make you feel?",
            "Self-conscious Endermen!",
            "Scopophobia!",
            "Waiting for something to happen?",
            "Dead memes!",
            "What mouth?",
            "Flying pop-tart rainbow cat of awesomeness!",
            "Order of the Stone!",
            "Thou shalt not commit perjury!",
            "The police investigate crime and the district attorneys prosecute the offenders!",
            "Objection!",
            "010000100110100101101110011000010111001001111001!",
            "Behold the man who is a bean!",
            "That '20s mod!",
            "Get rekt scrub!",
            "Kids, ask your parents where babies come from!",
            "Now with blackjack and hookers!",
            "Only '90s kids will remember!",
            "Peace and love!",
            "Trendy!"
    };

    public static final String[] SPOOKY_SPLASHES = {
            "Spooky!",
            "Boo!",
            "The nightmare before Christmas!",
            "Haunted!",
            "Beware!",
            "Spooky scary skeletons!",
            "Ghosts and ghouls!",
            "Contains ghasts!"
    };

    public static final String[] HOLIDAY_SPLASHES = {
            "Merry Christmas!",
            "Happy Holidays!",
            "Happy Hanukkah!",
            "Joyous Kwanzaa!",
            "Yuletide!",
            "Festive!",
            "Christmas is the time to say \'I love you\'!",
            "It's the most wonderful time of the year!",
            "Grandma got run over!",
            "Santa Claus is coming to town!",
            "A home invader is coming down the chimney!"
    };

    public static NeverendSplash getRandomSplash() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DATE) >= 15) {
            return new FestiveSplash(HOLIDAY_SPLASHES[new Random().nextInt(HOLIDAY_SPLASHES.length)]);
        }
        if (calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DATE) >= 15) {
            return new SpookySplash(SPOOKY_SPLASHES[new Random().nextInt(SPOOKY_SPLASHES.length)]);
        }
        if (calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DATE) == 1) {
            return new FoolsSplash();
        }
        int pick = new Random().nextInt(SPLASHES.length + SPECIAL_SPLASHES);
        if (pick < SPECIAL_SPLASHES) {
            return getSpecialSplash(pick);

        }
        return new NeverendSplash(SPLASHES[pick - SPECIAL_SPLASHES]);
    }

    private static NeverendSplash getSpecialSplash(int id) {
        switch (id) {
            case 0: // nostalgic
                return new NeverendSplash(isNostalgic() ? "Nostalgic!" : "Not nostalgic yet!");
            case 1: // greeting
                String greeting;
                Calendar time = Calendar.getInstance();
                time.setTime(new Date());
                int hour = time.get(Calendar.HOUR_OF_DAY);
                if (hour >= 5 && hour < 12) {
                    greeting = "morning";
                } else if (hour >= 12 && hour < 18) {
                    greeting = "afternoon";
                } else {
                    greeting = "evening";
                }
                return new NeverendSplash("Good " + greeting + "!");
            case 2: // double trouble
                return new DoubleSplash("Double trouble!");
            case 3: // you're a wizard
                return new NeverendSplash("You're a wizard, " + Minecraft.getInstance().getUser().getName() + "!");
            case 4: // THE END IS NEVER THE END
                return new EndIsNeverSplash("THE END IS NEVER ".repeat(20));
            case 5:
                return new CountdownSplash("Liftoff!");
            case 6: // this text is color
                switch (new Random().nextInt(10 + 6)) {
                    case 0:
                        return new NeverendSplash("§0This splash is black!");
                    case 1:
                        return new NeverendSplash("§1This splash is dark blue!");
                    case 2:
                        return new NeverendSplash("§2This splash is dark green!");
                    case 3:
                        return new NeverendSplash("§3This splash is dark aqua!");
                    case 4:
                        return new NeverendSplash("§4This splash is dark red!");
                    case 5:
                        return new NeverendSplash("§5This splash is dark purple!");
                    case 6:
                        return new NeverendSplash("§6This splash is golden!");
                    case 7:
                        return new NeverendSplash("§7This splash is grey!");
                    case 8:
                        return new NeverendSplash("§8This splash is dark grey!");
                    case 9:
                        return new NeverendSplash("§9This splash is blue!");
                    case 10:
                        return new NeverendSplash("§aThis splash is green!");
                    case 11:
                        return new NeverendSplash("§bThis splash is aqua!");
                    case 12:
                        return new NeverendSplash("§cThis splash is red!");
                    case 13:
                        return new NeverendSplash("§dThis splash is light purple!");
                    case 14:
                        return new NeverendSplash("§eThis splash is yellow!");
                    case 15:
                        return new NeverendSplash("§fThis splash is white!");
                }
        }
        throw new IllegalArgumentException(id + " is not a valid special splash ID!");
    }

    private static boolean isNostalgic() {
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.set(2024, Calendar.OCTOBER, 29); // TODO: update release date!
        releaseDate.add(Calendar.YEAR, 3);

        Calendar time = Calendar.getInstance();
        time.setTime(new Date());
        return time.after(releaseDate);
    }
}
