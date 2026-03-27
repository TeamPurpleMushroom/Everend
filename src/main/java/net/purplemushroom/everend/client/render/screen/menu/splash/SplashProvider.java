package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;

public class SplashProvider {
    public static final String[] SPLASHES = {
            "Purple is the new black!",
            "\"Neverend\" was taken!",
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
            "Also try Hexxit!",
            "Also try Born in Chaos!",
            "Also try Oh The Biomes We've Gone!",
            "Also try Botania!",
            "Also try Gamer's Glee!",
            "Family friendly!",
            "Remember the cows!",
            "Will it ever end?!",
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
            "Wazzaaaap!",
            "Deja vu!",
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
            "Yo!",
            "Made by nerds, for nerds!",
            "The (unofficial) End Update!",
            "Over 200 custom splashes!",
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
            "Trendy!",
            "Requires a very particular set of skills!",
            "Let the bodies hit the floor!",
            "Batteries not included!",
            "Free shipping!",
            "No refunds!",
            "TODO: get milk, destroy humanity",
            "No animals were harmed in the making of this mod!",
            "Monday Mornin' Sobbin'!",
            "Undead cat inside a box!",
            "Creeper no creeping!",
            "Non-biodegradable!",
            "It exists!",
            "90% of gamblers quit right before they hit it big!",
            "Bank error in your favor!",
            "Uno!",
            "Crop circles are just alien emojis!",
            "Sensual!",
            "Wahoo!",
            "1-Up!",
            "Tower of pimps!",
            "Let's play!",
            "A series of unfortunate events!",
            "In a galaxy far, far away!",
            "You've won two free iPod Nanos!",
            "Cheaper than eggs!",
            "Slow and tranquil!",
            "Confusion and delay!",
            "Why so uptight, Mr. Cox?",
            "Look behind you.",
            "'The Owl House' is a fun show!",
            "Deja vu!",
            "One man's \"cringe\" is another man's \"cool\"!",
            "Ores spawn!",
            "De gustibus non est disputandum!",
            "Don't murder people!",
            "Hic textus latine scriptus est, ut debet esse valde profundus!",
            "This message is almost certainly qualified!",
            "Custom shaders!",
            "Thanks, Mr. Perlin!",
            "Pokemon go to the polls!",
            "World's only antivirus with data recovery software!",
            "I see love!",
            "Skibidi!",
            "On the floor!",
            "Diablo El Pollo!",
            "Be protected from mayhem!",
            "Colorful mushrooms!",
            "You rule!",
            "Take back the night!",
            "Some assembly required!",
            "Diary of a blocky man!",
            "Illuminati confirmed!",
            "§mJumpscare Mansion§r House of Jumpscares!",
            "Elytras give you wiiings!",
            "May I take your order?",
            "Cities & Dragons!",
            "Dumbledore speaks calmly!",
            "I'm the least evil person I know!",
            "Hallelujah!",
            "Suspicious looking eyes!",
            "Budget of $0!",
            "OP enchantments!",
            "Teal skies!",
            "SFW!",
            "Based on a true story!",
            "Fun for all ages!",
            "Very odd!",
            "The hottest thing north of Havana!",
            "Spaghetti clouds!",
            "Mono hearts!",
            "Master of its domain!",
            "Call your friend Kevin!",
            "Black, slender men!",
            "Bullet hell-ish!",
            "Your personal healthcare companion!",
            "(Mostly) Stress-free!",
            "What's so funny about money?",
            "Astrological!",
            "Floating islands!",
            "Dun dun duuuun!",
            "Now you know your ABCs!",
            "\"Ouch\", cried I!",
            "Loser says what!",
            "This text was written by a stranger on the internet!",
            "I'm the box demon!",
            "Hello, procrastinators!",
            "My precious!",
            "x = x + 1 is a valid statement!",
            "PEMDAS!",
            "24 = 4!",
            "Si, la mi, sol. Si, la mi, re.",
            "Fat controller!",
            "Wasted!",
            "Peak!",
            "I was crazy once!",
            "Subscribe to Drapie!",
            "Piggy demon!",
            "As seen on YouTube!",
            "An offer you can't refuse!",
            "A very aladeen experience!",
            "That's a fender bender!",
            "You owe your life to dental hygiene!",
            "Here be dragons!",
            "BEWARE of CAT!",
            "Dr. iChun!",
            "This world uses experimental settings that could stop working at any time!",
            "Freeware!",
            "Alex's Cockroaches!",
            "Speak of the devil!",
            "Inspired by HEE!",
            "Better than shulkers!",
            "Don't throw popcorn in movie theaters!",
            "I can see my house from here!",
            "Evil Incorporated!",
            "Analysis paralysis!",
            "A terrible, awful, no good, very bad day!",
            "Rock me, Amadeus!",
            "State of the art!",
            "Not Shane!",
            "Southern hospitality!",
            "Love thy neighbor!",
            "Geronimo!",
            "There is nowhere to run, chat!",
            "YOUR TAKING TOO LONG",
            "The hierarchy of power is about to change.",
            "1A! 1A! 1A!",
            "Java, not JavaScript!",
            "Nearest neighbor!",
            "That bump is shaped like a deer!",
            "Shaww!",
            "To be, or not to be!",
            "If at first you don't succeed, redefine success!",
            "Post-processing!",
            "Not necessarily just a problem, but an opportunity as well!",
            "Keep the door open three inches!"
    };

    public static final String[] SPOOKY_SPLASHES = {
            "Spooky!",
            "Boo!",
            "The nightmare before Christmas!",
            "Haunted!",
            "Beware!",
            "Spooky scary skeletons!",
            "Ghosts and ghouls!",
            "Contains ghasts!",
            "There is a skeleton inside of you!"
    };

    public static final String[] HOLIDAY_SPLASHES = {
            "Merry Christmas!",
            "Happy Holidays!",
            "Happy Hanukkah!",
            "Joyous Kwanzaa!",
            "Yuletide!",
            "Festive!",
            "Christmas is the time to say 'I love you'!",
            "It's the most wonderful time of the year!",
            "Grandma got run over!",
            "Santa Claus is coming to town!",
            "A home invader is coming down the chimney!"
    };

    public static final Supplier<?>[] SPECIAL_SPLASHES = { // I don't think I'm allowed to specify the generic here. Sad :(
            // nostalgic
            () -> new EverendSplash(isNostalgic() ? "Nostalgic!" : "Not nostalgic yet!"),

            // greeting
            () -> {
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
                return new EverendSplash("Good " + greeting + "!");
            },

            // double trouble
            () -> new DoubleSplash("Double trouble!"),

            // you're a wizard
            () -> new EverendSplash("You're a wizard, " + Minecraft.getInstance().getUser().getName() + "!"),

            // THE END IS NEVER THE END IS NEVER
            () -> new EndIsNeverSplash("THE END IS NEVER ".repeat(20)),

            // countdown
            () -> new CountdownSplash("Liftoff!"),

            // this text is color
            () -> switch (new Random().nextInt(10)) {
                case 0 -> new SolidColorSplash("§0This splash is black!");
                case 1 -> new SolidColorSplash("§1This splash is blue!");
                case 2 -> new SolidColorSplash("§2This splash is green!");
                case 3 -> new SolidColorSplash("§4This splash is red!");
                case 4 -> new SolidColorSplash("§5This splash is purple!");
                case 5 -> new SolidColorSplash("§6This splash is golden!");
                case 6 -> new SolidColorSplash("§7This splash is grey!");
                case 7 -> new SolidColorSplash("§dThis splash is pink!");
                case 8 -> new SolidColorSplash("§eThis splash is yellow!");
                case 9 -> new SolidColorSplash("§fThis splash is white!");
                default -> throw new IllegalArgumentException("Could not pick a valid color for color splash!");
            },

            // this message will self-destruct
            () -> new VanishingSplash("This message will self-destruct in five seconds!"),

            // mod list
            () -> {
                int extraModCount = ModList.get().size() - 6;
                if (extraModCount == 0) { // everend + timecore
                    return new EverendSplash("Consider downloading some quality of life mods!");
                } else if (extraModCount < 30) {
                    return new EverendSplash("A humble mod count!");
                } else {
                    return new EverendSplash("Look mom, I'm in a modpack!");
                }
            },

            // gossip
            () -> new EverendSplash("That " + Minecraft.getInstance().getUser().getName() + " is such a- oh, didn't see you there!"),

            // rainbow
            () -> new RainbowSplash("COLORS!!!!!!!!!"),

            () -> {
                String[] versions = new String[] {
                        "1.0.0",
                        "1.0.1",

                        "1.1",

                        "1.2.1",
                        "1.2.2",
                        "1.2.3",
                        "1.2.4",
                        "1.2.5",

                        "1.3.1",
                        "1.3.2",

                        "1.4.2",
                        "1.4.4",
                        "1.4.5",
                        "1.4.6",
                        "1.4.7",

                        "1.5",
                        "1.5.1",
                        "1.5.2",

                        "1.6.1",
                        "1.6.2",

                        "1.7.2",
                        "1.7.4",
                        "1.7.5",
                        "1.7.6",
                        "1.7.7",
                        "1.7.8",
                        "1.7.9",
                        "1.7.10",

                        "1.8",
                        "1.8.1",
                        "1.8.2",
                        "1.8.3",
                        "1.8.4",
                        "1.8.5",
                        "1.8.6",
                        "1.8.7",
                        "1.8.8",
                        "1.8.9",

                        "1.9",
                        "1.9.1",
                        "1.9.2",
                        "1.9.3",
                        "1.9.4",

                        "1.10",
                        "1.10.1",
                        "1.10.2",

                        "1.11",
                        "1.11.1",
                        "1.11.2",

                        "1.12",
                        "1.12.1",
                        "1.12.2",

                        "1.13",
                        "1.13.1",
                        "1.13.2",

                        "1.14",
                        "1.14.1",
                        "1.14.2",
                        "1.14.3",
                        "1.14.4",

                        "1.15",
                        "1.15.1",
                        "1.15.2",

                        "1.16",
                        "1.16.1",
                        "1.16.2",
                        "1.16.3",
                        "1.16.4",
                        "1.16.5",

                        "1.17",
                        "1.17.1",

                        "1.18",
                        "1.18.1",
                        "1.18.2",

                        "1.19",
                        "1.19.1",
                        "1.19.2",
                        "1.19.3",
                        "1.19.4",

                        "1.20",
                        "1.20.1",
                        "1.20.2",
                        "1.20.3",
                        "1.20.4",
                        "1.20.5",
                        "1.20.6",

                        "1.21",
                        "1.21.1",
                        "1.21.2",
                        "1.21.3",
                        "1.21.4",
                        "1.21.5",
                        "1.21.6",
                        "1.21.7",
                        "1.21.8",
                        "1.21.9",
                        "1.21.10",
                        "1.21.11"
                };

                String selectedVersion;
                do {
                    selectedVersion = versions[new Random().nextInt(versions.length)];
                } while (selectedVersion.equals(SharedConstants.getCurrentVersion().getName()));

                return new EverendSplash(selectedVersion + " please!");
            }
    };

    private static final Calendar RELEASE_DATE = Calendar.getInstance();
    static {
        RELEASE_DATE.set(2024, Calendar.OCTOBER, 29); // TODO: update release date!
        RELEASE_DATE.add(Calendar.YEAR, 3);
    }
    private static final String DEBUG_SPLASH = null;

    private static boolean isNostalgic() {
        Calendar time = Calendar.getInstance();
        time.setTime(new Date());
        return time.after(RELEASE_DATE);
    }

    public static EverendSplash getRandomSplash() {
        if (DEBUG_SPLASH != null) {
            return new EverendSplash(DEBUG_SPLASH);
        }

        Random rand = new Random();
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DATE) >= 15) {
            return new FestiveSplash(HOLIDAY_SPLASHES[new Random().nextInt(HOLIDAY_SPLASHES.length)]);
        }
        if (calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DATE) >= 15) {
            return new SpookySplash(SPOOKY_SPLASHES[new Random().nextInt(SPOOKY_SPLASHES.length)]);
        }
        if (calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DATE) == 1) {
            if (rand.nextInt(30) == 0) return new ClickMeSplash("Click me!");
            return new FoolsSplash();
        }
        if (calendar.get(Calendar.MONTH) == RELEASE_DATE.get(Calendar.MONTH) && calendar.get(Calendar.DATE) == RELEASE_DATE.get(Calendar.DATE)) {
            return new EverendSplash("Happy Birthday, Everend!");
        }
        if (rand.nextInt(1000) == 0) return new ClickMeSplash("Click me!");

        int pick = rand.nextInt(SPLASHES.length + SPECIAL_SPLASHES.length);
        pick = SPECIAL_SPLASHES.length - 1;
        if (pick < SPECIAL_SPLASHES.length) {
            return (EverendSplash) SPECIAL_SPLASHES[pick].get();
        }

        return new EverendSplash(SPLASHES[pick - SPECIAL_SPLASHES.length]);
    }
}
