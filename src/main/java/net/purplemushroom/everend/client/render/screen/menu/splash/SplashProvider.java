package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.Minecraft;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SplashProvider {
    private static final int SPECIAL_SPLASHES = 8;

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
            "Trendy!",
            "Requires a very particular set of skills!",
            "Let the bodies hit the floor!",
            "Batteries not included!",
            "Free shipping!",
            "No refunds!",
            "TODO: get milk, destroy humanity",
            "No animals were harmed in the making of this mod!",
            "Monday Night Sobbin'!",
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
            "...and then one with the crowbar!",
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
            "Lowkey!",
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
            "It's dust in the wind!"
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

    private static final String DEBUG_SPLASH = null;

    public static EverendSplash getRandomSplash() {
        if (DEBUG_SPLASH != null) {
            return new EverendSplash(DEBUG_SPLASH);
        }

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
        return new EverendSplash(SPLASHES[pick - SPECIAL_SPLASHES]);
    }

    private static EverendSplash getSpecialSplash(int id) {
        switch (id) {
            case 0: // nostalgic
                return new EverendSplash(isNostalgic() ? "Nostalgic!" : "Not nostalgic yet!");
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
                return new EverendSplash("Good " + greeting + "!");
            case 2: // double trouble
                return new DoubleSplash("Double trouble!");
            case 3: // you're a wizard
                return new EverendSplash("You're a wizard, " + Minecraft.getInstance().getUser().getName() + "!");
            case 4: // THE END IS NEVER THE END
                return new EndIsNeverSplash("THE END IS NEVER ".repeat(20));
            case 5:
                return new CountdownSplash("Liftoff!");
            case 6: // this text is color
                return switch (new Random().nextInt(10)) {
                    case 0 -> new EverendSplash("§0This splash is black!");
                    case 1 -> new EverendSplash("§1This splash is blue!");
                    case 2 -> new EverendSplash("§2This splash is green!");
                    case 3 -> new EverendSplash("§4This splash is red!");
                    case 4 -> new EverendSplash("§5This splash is purple!");
                    case 5 -> new EverendSplash("§6This splash is golden!");
                    case 6 -> new EverendSplash("§7This splash is grey!");
                    case 7 -> new EverendSplash("§dThis splash is pink!");
                    case 8 -> new EverendSplash("§eThis splash is yellow!");
                    case 9 -> new EverendSplash("§fThis splash is white!");
                    default -> throw new IllegalArgumentException("Could not pick a valid color for color splash!");
                };
            case 7: // this message will self-destruct
                return new VanishingSplash("This message will self-destruct in five seconds!");
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
