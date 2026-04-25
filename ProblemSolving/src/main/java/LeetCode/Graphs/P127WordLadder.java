package LeetCode.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * P127. Word Ladder - Hard
 * 
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words 
 * beginWord -> s1 -> s2 -> ... -> sk such that:
 * - Every adjacent pair of words differs by a single letter.
 * - Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 * - sk == endWord
 * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words 
 * in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
 * 
 * Approach - BFS - 1Direction, 2Direction, StringBuilder, Char Replacement, Map
 * 
 * All the words in the wordList are unique.
 */
public class P127WordLadder {

	static class Pair<String, Integer> {
		String word;
		Integer level;

		Pair(String word, Integer level) {
			this.word = word;
			this.level = level;
		}
	}

	public static void main(String[] args) {

//		String beginWord = "hit";
//		String endWord = "cog";
//		List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

		String beginWord = "sand";
		String endWord = "acne";
		List<String> wordList = Arrays.asList("slit", "bunk", "wars", "ping", "viva", "wynn", "wows", "irks", "gang",
				"pool", "mock", "fort", "heel", "send", "ship", "cols", "alec", "foal", "nabs", "gaze", "giza", "mays",
				"dogs", "karo", "cums", "jedFSi", "webb", "lend", "mire", "jose", "catt", "grow", "toss", "magi",
				"leis", "bead", "kara", "hoof", "than", "ires", "baas", "vein", "kari", "riga", "oars", "gags", "thug",
				"yawn", "wive", "view", "germ", "flab", "july", "tuck", "rory", "bean", "feed", "rhee", "jeez", "gobs",
				"lath", "desk", "yoko", "cute", "zeus", "thus", "dims", "link", "dirt", "mara", "disc", "limy", "lewd",
				"maud", "duly", "elsa", "hart", "rays", "rues", "camp", "lack", "okra", "tome", "math", "plug", "monk",
				"orly", "friz", "hogs", "yoda", "poop", "tick", "plod", "cloy", "pees", "imps", "lead", "pope", "mall",
				"frey", "been", "plea", "poll", "male", "teak", "soho", "glob", "bell", "mary", "hail", "scan", "yips",
				"like", "mull", "kory", "odor", "byte", "kaye", "word", "honk", "asks", "slid", "hopi", "toke", "gore",
				"flew", "tins", "mown", "oise", "hall", "vega", "sing", "fool", "boat", "bobs", "lain", "soft", "hard",
				"rots", "sees", "apex", "chan", "told", "woos", "unit", "scow", "gilt", "beef", "jars", "tyre", "imus",
				"neon", "soap", "dabs", "rein", "ovid", "hose", "husk", "loll", "asia", "cope", "tail", "hazy", "clad",
				"lash", "sags", "moll", "eddy", "fuel", "lift", "flog", "land", "sigh", "saks", "sail", "hook", "visa",
				"tier", "maws", "roeg", "gila", "eyes", "noah", "hypo", "tore", "eggs", "rove", "chap", "room", "wait",
				"lurk", "race", "host", "dada", "lola", "gabs", "sobs", "joel", "keck", "axed", "mead", "gust", "laid",
				"ends", "oort", "nose", "peer", "kept", "abet", "iran", "mick", "dead", "hags", "tens", "gown", "sick",
				"odis", "miro", "bill", "fawn", "sumo", "kilt", "huge", "ores", "oran", "flag", "tost", "seth", "sift",
				"poet", "reds", "pips", "cape", "togo", "wale", "limn", "toll", "ploy", "inns", "snag", "hoes", "jerk",
				"flux", "fido", "zane", "arab", "gamy", "raze", "lank", "hurt", "rail", "hind", "hoot", "dogy", "away",
				"pest", "hoed", "pose", "lose", "pole", "alva", "dino", "kind", "clan", "dips", "soup", "veto", "edna",
				"damp", "gush", "amen", "wits", "pubs", "fuzz", "cash", "pine", "trod", "gunk", "nude", "lost", "rite",
				"cory", "walt", "mica", "cart", "avow", "wind", "book", "leon", "life", "bang", "draw", "leek", "skis",
				"dram", "ripe", "mine", "urea", "tiff", "over", "gale", "weir", "defy", "norm", "tull", "whiz", "gill",
				"ward", "crag", "when", "mill", "firs", "sans", "flue", "reid", "ekes", "jain", "mutt", "hems", "laps",
				"piss", "pall", "rowe", "prey", "cull", "knew", "size", "wets", "hurl", "wont", "suva", "girt", "prys",
				"prow", "warn", "naps", "gong", "thru", "livy", "boar", "sade", "amok", "vice", "slat", "emir", "jade",
				"karl", "loyd", "cerf", "bess", "loss", "rums", "lats", "bode", "subs", "muss", "maim", "kits", "thin",
				"york", "punt", "gays", "alpo", "aids", "drag", "eras", "mats", "pyre", "clot", "step", "oath", "lout",
				"wary", "carp", "hums", "tang", "pout", "whip", "fled", "omar", "such", "kano", "jake", "stan", "loop",
				"fuss", "mini", "byrd", "exit", "fizz", "lire", "emil", "prop", "noes", "awed", "gift", "soli", "sale",
				"gage", "orin", "slur", "limp", "saar", "arks", "mast", "gnat", "port", "into", "geed", "pave", "awls",
				"cent", "cunt", "full", "dint", "hank", "mate", "coin", "tars", "scud", "veer", "coax", "bops", "uris",
				"loom", "shod", "crib", "lids", "drys", "fish", "edit", "dick", "erna", "else", "hahs", "alga", "moho",
				"wire", "fora", "tums", "ruth", "bets", "duns", "mold", "mush", "swop", "ruby", "bolt", "nave", "kite",
				"ahem", "brad", "tern", "nips", "whew", "bait", "ooze", "gino", "yuck", "drum", "shoe", "lobe", "dusk",
				"cult", "paws", "anew", "dado", "nook", "half", "lams", "rich", "cato", "java", "kemp", "vain", "fees",
				"sham", "auks", "gish", "fire", "elam", "salt", "sour", "loth", "whit", "yogi", "shes", "scam", "yous",
				"lucy", "inez", "geld", "whig", "thee", "kelp", "loaf", "harm", "tomb", "ever", "airs", "page", "laud",
				"stun", "paid", "goop", "cobs", "judy", "grab", "doha", "crew", "item", "fogs", "tong", "blip", "vest",
				"bran", "wend", "bawl", "feel", "jets", "mixt", "tell", "dire", "devi", "milo", "deng", "yews", "weak",
				"mark", "doug", "fare", "rigs", "poke", "hies", "sian", "suez", "quip", "kens", "lass", "zips", "elva",
				"brat", "cosy", "teri", "hull", "spun", "russ", "pupa", "weed", "pulp", "main", "grim", "hone", "cord",
				"barf", "olav", "gaps", "rote", "wilt", "lars", "roll", "balm", "jana", "give", "eire", "faun", "suck",
				"kegs", "nita", "weer", "tush", "spry", "loge", "nays", "heir", "dope", "roar", "peep", "nags", "ates",
				"bane", "seas", "sign", "fred", "they", "lien", "kiev", "fops", "said", "lawn", "lind", "miff", "mass",
				"trig", "sins", "furl", "ruin", "sent", "cray", "maya", "clog", "puns", "silk", "axis", "grog", "jots",
				"dyer", "mope", "rand", "vend", "keen", "chou", "dose", "rain", "eats", "sped", "maui", "evan", "time",
				"todd", "skit", "lief", "sops", "outs", "moot", "faze", "biro", "gook", "fill", "oval", "skew", "veil",
				"born", "slob", "hyde", "twin", "eloy", "beat", "ergs", "sure", "kobe", "eggo", "hens", "jive", "flax",
				"mons", "dunk", "yest", "begs", "dial", "lodz", "burp", "pile", "much", "dock", "rene", "sago", "racy",
				"have", "yalu", "glow", "move", "peps", "hods", "kins", "salk", "hand", "cons", "dare", "myra", "sega",
				"type", "mari", "pelt", "hula", "gulf", "jugs", "flay", "fest", "spat", "toms", "zeno", "taps", "deny",
				"swag", "afro", "baud", "jabs", "smut", "egos", "lara", "toes", "song", "fray", "luis", "brut", "olen",
				"mere", "ruff", "slum", "glad", "buds", "silt", "rued", "gelt", "hive", "teem", "ides", "sink", "ands",
				"wisp", "omen", "lyre", "yuks", "curb", "loam", "darn", "liar", "pugs", "pane", "carl", "sang", "scar",
				"zeds", "claw", "berg", "hits", "mile", "lite", "khan", "erik", "slug", "loon", "dena", "ruse", "talk",
				"tusk", "gaol", "tads", "beds", "sock", "howe", "gave", "snob", "ahab", "part", "meir", "jell", "stir",
				"tels", "spit", "hash", "omit", "jinx", "lyra", "puck", "laue", "beep", "eros", "owed", "cede", "brew",
				"slue", "mitt", "jest", "lynx", "wads", "gena", "dank", "volt", "gray", "pony", "veld", "bask", "fens",
				"argo", "work", "taxi", "afar", "boon", "lube", "pass", "lazy", "mist", "blot", "mach", "poky", "rams",
				"sits", "rend", "dome", "pray", "duck", "hers", "lure", "keep", "gory", "chat", "runt", "jams", "lays",
				"posy", "bats", "hoff", "rock", "keri", "raul", "yves", "lama", "ramp", "vote", "jody", "pock", "gist",
				"sass", "iago", "coos", "rank", "lowe", "vows", "koch", "taco", "jinn", "juno", "rape", "band", "aces",
				"goal", "huck", "lila", "tuft", "swan", "blab", "leda", "gems", "hide", "tack", "porn", "scum", "frat",
				"plum", "duds", "shad", "arms", "pare", "chin", "gain", "knee", "foot", "line", "dove", "vera", "jays",
				"fund", "reno", "skid", "boys", "corn", "gwyn", "sash", "weld", "ruiz", "dior", "jess", "leaf", "pars",
				"cote", "zing", "scat", "nice", "dart", "only", "owls", "hike", "trey", "whys", "ding", "klan", "ross",
				"barb", "ants", "lean", "dopy", "hock", "tour", "grip", "aldo", "whim", "prom", "rear", "dins", "duff",
				"dell", "loch", "lava", "sung", "yank", "thar", "curl", "venn", "blow", "pomp", "heat", "trap", "dali",
				"nets", "seen", "gash", "twig", "dads", "emmy", "rhea", "navy", "haws", "mite", "bows", "alas", "ives",
				"play", "soon", "doll", "chum", "ajar", "foam", "call", "puke", "kris", "wily", "came", "ales", "reef",
				"raid", "diet", "prod", "prut", "loot", "soar", "coed", "celt", "seam", "dray", "lump", "jags", "nods",
				"sole", "kink", "peso", "howl", "cost", "tsar", "uric", "sore", "woes", "sewn", "sake", "cask", "caps",
				"burl", "tame", "bulk", "neva", "from", "meet", "webs", "spar", "fuck", "buoy", "wept", "west", "dual",
				"pica", "sold", "seed", "gads", "riff", "neck", "deed", "rudy", "drop", "vale", "flit", "romp", "peak",
				"jape", "jews", "fain", "dens", "hugo", "elba", "mink", "town", "clam", "feud", "fern", "dung", "newt",
				"mime", "deem", "inti", "gigs", "sosa", "lope", "lard", "cara", "smug", "lego", "flex", "doth", "paar",
				"moon", "wren", "tale", "kant", "eels", "muck", "toga", "zens", "lops", "duet", "coil", "gall", "teal",
				"glib", "muir", "ails", "boer", "them", "rake", "conn", "neat", "frog", "trip", "coma", "must", "mono",
				"lira", "craw", "sled", "wear", "toby", "reel", "hips", "nate", "pump", "mont", "died", "moss", "lair",
				"jibe", "oils", "pied", "hobs", "cads", "haze", "muse", "cogs", "figs", "cues", "roes", "whet", "boru",
				"cozy", "amos", "tans", "news", "hake", "cots", "boas", "tutu", "wavy", "pipe", "typo", "albs", "boom",
				"dyke", "wail", "woke", "ware", "rita", "fail", "slab", "owes", "jane", "rack", "hell", "lags", "mend",
				"mask", "hume", "wane", "acne", "team", "holy", "runs", "exes", "dole", "trim", "zola", "trek", "puma",
				"wacs", "veep", "yaps", "sums", "lush", "tubs", "most", "witt", "bong", "rule", "hear", "awry", "sots",
				"nils", "bash", "gasp", "inch", "pens", "fies", "juts", "pate", "vine", "zulu", "this", "bare", "veal",
				"josh", "reek", "ours", "cowl", "club", "farm", "teat", "coat", "dish", "fore", "weft", "exam", "vlad",
				"floe", "beak", "lane", "ella", "warp", "goth", "ming", "pits", "rent", "tito", "wish", "amps", "says",
				"hawk", "ways", "punk", "nark", "cagy", "east", "paul", "bose", "solo", "teed", "text", "hews", "snip",
				"lips", "emit", "orgy", "icon", "tuna", "soul", "kurd", "clod", "calk", "aunt", "bake", "copy", "acid",
				"duse", "kiln", "spec", "fans", "bani", "irma", "pads", "batu", "logo", "pack", "oder", "atop", "funk",
				"gide", "bede", "bibs", "taut", "guns", "dana", "puff", "lyme", "flat", "lake", "june", "sets", "gull",
				"hops", "earn", "clip", "fell", "kama", "seal", "diaz", "cite", "chew", "cuba", "bury", "yard", "bank",
				"byes", "apia", "cree", "nosh", "judo", "walk", "tape", "taro", "boot", "cods", "lade", "cong", "deft",
				"slim", "jeri", "rile", "park", "aeon", "fact", "slow", "goff", "cane", "earp", "tart", "does", "acts",
				"hope", "cant", "buts", "shin", "dude", "ergo", "mode", "gene", "lept", "chen", "beta", "eden", "pang",
				"saab", "fang", "whir", "cove", "perk", "fads", "rugs", "herb", "putt", "nous", "vane", "corm", "stay",
				"bids", "vela", "roof", "isms", "sics", "gone", "swum", "wiry", "cram", "rink", "pert", "heap", "sikh",
				"dais", "cell", "peel", "nuke", "buss", "rasp", "none", "slut", "bent", "dams", "serb", "dork", "bays",
				"kale", "cora", "wake", "welt", "rind", "trot", "sloe", "pity", "rout", "eves", "fats", "furs", "pogo",
				"beth", "hued", "edam", "iamb", "glee", "lute", "keel", "airy", "easy", "tire", "rube", "bogy", "sine",
				"chop", "rood", "elbe", "mike", "garb", "jill", "gaul", "chit", "dons", "bars", "ride", "beck", "toad",
				"make", "head", "suds", "pike", "snot", "swat", "peed", "same", "gaza", "lent", "gait", "gael", "elks",
				"hang", "nerf", "rosy", "shut", "glop", "pain", "dion", "deaf", "hero", "doer", "wost", "wage", "wash",
				"pats", "narc", "ions", "dice", "quay", "vied", "eons", "case", "pour", "urns", "reva", "rags", "aden",
				"bone", "rang", "aura", "iraq", "toot", "rome", "hals", "megs", "pond", "john", "yeps", "pawl", "warm",
				"bird", "tint", "jowl", "gibe", "come", "hold", "pail", "wipe", "bike", "rips", "eery", "kent", "hims",
				"inks", "fink", "mott", "ices", "macy", "serf", "keys", "tarp", "cops", "sods", "feet", "tear", "benz",
				"buys", "colo", "boil", "sews", "enos", "watt", "pull", "brag", "cork", "save", "mint", "feat", "jamb",
				"rubs", "roxy", "toys", "nosy", "yowl", "tamp", "lobs", "foul", "doom", "sown", "pigs", "hemp", "fame",
				"boor", "cube", "tops", "loco", "lads", "eyre", "alta", "aged", "flop", "pram", "lesa", "sawn", "plow",
				"aral", "load", "lied", "pled", "boob", "bert", "rows", "zits", "rick", "hint", "dido", "fist", "marc",
				"wuss", "node", "smog", "nora", "shim", "glut", "bale", "perl", "what", "tort", "meek", "brie", "bind",
				"cake", "psst", "dour", "jove", "tree", "chip", "stud", "thou", "mobs", "sows", "opts", "diva", "perm",
				"wise", "cuds", "sols", "alan", "mild", "pure", "gail", "wins", "offs", "nile", "yelp", "minn", "tors",
				"tran", "homy", "sadr", "erse", "nero", "scab", "finn", "mich", "turd", "then", "poem", "noun", "oxus",
				"brow", "door", "saws", "eben", "wart", "wand", "rosa", "left", "lina", "cabs", "rapt", "olin", "suet",
				"kalb", "mans", "dawn", "riel", "temp", "chug", "peal", "drew", "null", "hath", "many", "took", "fond",
				"gate", "sate", "leak", "zany", "vans", "mart", "hess", "home", "long", "dirk", "bile", "lace", "moog",
				"axes", "zone", "fork", "duct", "rico", "rife", "deep", "tiny", "hugh", "bilk", "waft", "swig", "pans",
				"with", "kern", "busy", "film", "lulu", "king", "lord", "veda", "tray", "legs", "soot", "ells", "wasp",
				"hunt", "earl", "ouch", "diem", "yell", "pegs", "blvd", "polk", "soda", "zorn", "liza", "slop", "week",
				"kill", "rusk", "eric", "sump", "haul", "rims", "crop", "blob", "face", "bins", "read", "care", "pele",
				"ritz", "beau", "golf", "drip", "dike", "stab", "jibs", "hove", "junk", "hoax", "tats", "fief", "quad",
				"peat", "ream", "hats", "root", "flak", "grit", "clap", "pugh", "bosh", "lock", "mute", "crow", "iced",
				"lisa", "bela", "fems", "oxes", "vies", "gybe", "huff", "bull", "cuss", "sunk", "pups", "fobs", "turf",
				"sect", "atom", "debt", "sane", "writ", "anon", "mayo", "aria", "seer", "thor", "brim", "gawk", "jack",
				"jazz", "menu", "yolk", "surf", "libs", "lets", "bans", "toil", "open", "aced", "poor", "mess", "wham",
				"fran", "gina", "dote", "love", "mood", "pale", "reps", "ines", "shot", "alar", "twit", "site", "dill",
				"yoga", "sear", "vamp", "abel", "lieu", "cuff", "orbs", "rose", "tank", "gape", "guam", "adar", "vole",
				"your", "dean", "dear", "hebe", "crab", "hump", "mole", "vase", "rode", "dash", "sera", "balk", "lela",
				"inca", "gaea", "bush", "loud", "pies", "aide", "blew", "mien", "side", "kerr", "ring", "tess", "prep",
				"rant", "lugs", "hobo", "joke", "odds", "yule", "aida", "true", "pone", "lode", "nona", "weep", "coda",
				"elmo", "skim", "wink", "bras", "pier", "bung", "pets", "tabs", "ryan", "jock", "body", "sofa", "joey",
				"zion", "mace", "kick", "vile", "leno", "bali", "fart", "that", "redo", "ills", "jogs", "pent", "drub",
				"slaw", "tide", "lena", "seep", "gyps", "wave", "amid", "fear", "ties", "flan", "wimp", "kali", "shun",
				"crap", "sage", "rune", "logs", "cain", "digs", "abut", "obit", "paps", "rids", "fair", "hack", "huns",
				"road", "caws", "curt", "jute", "fisk", "fowl", "duty", "holt", "miss", "rude", "vito", "baal", "ural",
				"mann", "mind", "belt", "clem", "last", "musk", "roam", "abed", "days", "bore", "fuze", "fall", "pict",
				"dump", "dies", "fiat", "vent", "pork", "eyed", "docs", "rive", "spas", "rope", "ariz", "tout", "game",
				"jump", "blur", "anti", "lisp", "turn", "sand", "food", "moos", "hoop", "saul", "arch", "fury", "rise",
				"diss", "hubs", "burs", "grid", "ilks", "suns", "flea", "soil", "lung", "want", "nola", "fins", "thud",
				"kidd", "juan", "heps", "nape", "rash", "burt", "bump", "tots", "brit", "mums", "bole", "shah", "tees",
				"skip", "limb", "umps", "ache", "arcs", "raft", "halo", "luce", "bahs", "leta", "conk", "duos", "siva",
				"went", "peek", "sulk", "reap", "free", "dubs", "lang", "toto", "hasp", "ball", "rats", "nair", "myst",
				"wang", "snug", "nash", "laos", "ante", "opal", "tina", "pore", "bite", "haas", "myth", "yugo", "foci",
				"dent", "bade", "pear", "mods", "auto", "shop", "etch", "lyly", "curs", "aron", "slew", "tyro", "sack",
				"wade", "clio", "gyro", "butt", "icky", "char", "itch", "halt", "gals", "yang", "tend", "pact", "bees",
				"suit", "puny", "hows", "nina", "brno", "oops", "lick", "sons", "kilo", "bust", "nome", "mona", "dull",
				"join", "hour", "papa", "stag", "bern", "wove", "lull", "slip", "laze", "roil", "alto", "bath", "buck",
				"alma", "anus", "evil", "dumb", "oreo", "rare", "near", "cure", "isis", "hill", "kyle", "pace", "comb",
				"nits", "flip", "clop", "mort", "thea", "wall", "kiel", "judd", "coop", "dave", "very", "amie", "blah",
				"flub", "talc", "bold", "fogy", "idea", "prof", "horn", "shoo", "aped", "pins", "helm", "wees", "beer",
				"womb", "clue", "alba", "aloe", "fine", "bard", "limo", "shaw", "pint", "swim", "dust", "indy", "hale",
				"cats", "troy", "wens", "luke", "vern", "deli", "both", "brig", "daub", "sara", "sued", "bier", "noel",
				"olga", "dupe", "look", "pisa", "knox", "murk", "dame", "matt", "gold", "jame", "toge", "luck", "peck",
				"tass", "calf", "pill", "wore", "wadi", "thur", "parr", "maul", "tzar", "ones", "lees", "dark", "fake",
				"bast", "zoom", "here", "moro", "wine", "bums", "cows", "jean", "palm", "fume", "plop", "help", "tuba",
				"leap", "cans", "back", "avid", "lice", "lust", "polo", "dory", "stew", "kate", "rama", "coke", "bled",
				"mugs", "ajax", "arts", "drug", "pena", "cody", "hole", "sean", "deck", "guts", "kong", "bate", "pitt",
				"como", "lyle", "siam", "rook", "baby", "jigs", "bret", "bark", "lori", "reba", "sups", "made", "buzz",
				"gnaw", "alps", "clay", "post", "viol", "dina", "card", "lana", "doff", "yups", "tons", "live", "kids",
				"pair", "yawl", "name", "oven", "sirs", "gyms", "prig", "down", "leos", "noon", "nibs", "cook", "safe",
				"cobb", "raja", "awes", "sari", "nerd", "fold", "lots", "pete", "deal", "bias", "zeal", "girl", "rage",
				"cool", "gout", "whey", "soak", "thaw", "bear", "wing", "nagy", "well", "oink", "sven", "kurt", "etna",
				"held", "wood", "high", "feta", "twee", "ford", "cave", "knot", "tory", "ibis", "yaks", "vets", "foxy",
				"sank", "cone", "pius", "tall", "seem", "wool", "flap", "gird", "lore", "coot", "mewl", "sere", "real",
				"puts", "sell", "nuts", "foil", "lilt", "saga", "heft", "dyed", "goat", "spew", "daze", "frye", "adds",
				"glen", "tojo", "pixy", "gobi", "stop", "tile", "hiss", "shed", "hahn", "baku", "ahas", "sill", "swap",
				"also", "carr", "manx", "lime", "debs", "moat", "eked", "bola", "pods", "coon", "lacy", "tube", "minx",
				"buff", "pres", "clew", "gaff", "flee", "burn", "whom", "cola", "fret", "purl", "wick", "wigs", "donn",
				"guys", "toni", "oxen", "wite", "vial", "spam", "huts", "vats", "lima", "core", "eula", "thad", "peon",
				"erie", "oats", "boyd", "cued", "olaf", "tams", "secs", "urey", "wile", "penn", "bred", "rill", "vary",
				"sues", "mail", "feds", "aves", "code", "beam", "reed", "neil", "hark", "pols", "gris", "gods", "mesa",
				"test", "coup", "heed", "dora", "hied", "tune", "doze", "pews", "oaks", "bloc", "tips", "maid", "goof",
				"four", "woof", "silo", "bray", "zest", "kiss", "yong", "file", "hilt", "iris", "tuns", "lily", "ears",
				"pant", "jury", "taft", "data", "gild", "pick", "kook", "colt", "bohr", "anal", "asps", "babe", "bach",
				"mash", "biko", "bowl", "huey", "jilt", "goes", "guff", "bend", "nike", "tami", "gosh", "tike", "gees",
				"urge", "path", "bony", "jude", "lynn", "lois", "teas", "dunn", "elul", "bonn", "moms", "bugs", "slay",
				"yeah", "loan", "hulk", "lows", "damn", "nell", "jung", "avis", "mane", "waco", "loin", "knob", "tyke",
				"anna", "hire", "luau", "tidy", "nuns", "pots", "quid", "exec", "hans", "hera", "hush", "shag", "scot",
				"moan", "wald", "ursa", "lorn", "hunk", "loft", "yore", "alum", "mows", "slog", "emma", "spud", "rice",
				"worn", "erma", "need", "bags", "lark", "kirk", "pooh", "dyes", "area", "dime", "luvs", "foch", "refs",
				"cast", "alit", "tugs", "even", "role", "toed", "caph", "nigh", "sony", "bide", "robs", "folk", "daft",
				"past", "blue", "flaw", "sana", "fits", "barr", "riot", "dots", "lamp", "cock", "fibs", "harp", "tent",
				"hate", "mali", "togs", "gear", "tues", "bass", "pros", "numb", "emus", "hare", "fate", "wife", "mean",
				"pink", "dune", "ares", "dine", "oily", "tony", "czar", "spay", "push", "glum", "till", "moth", "glue",
				"dive", "scad", "pops", "woks", "andy", "leah", "cusp", "hair", "alex", "vibe", "bulb", "boll", "firm",
				"joys", "tara", "cole", "levy", "owen", "chow", "rump", "jail", "lapp", "beet", "slap", "kith", "more",
				"maps", "bond", "hick", "opus", "rust", "wist", "shat", "phil", "snow", "lott", "lora", "cary", "mote",
				"rift", "oust", "klee", "goad", "pith", "heep", "lupe", "ivan", "mimi", "bald", "fuse", "cuts", "lens",
				"leer", "eyry", "know", "razz", "tare", "pals", "geek", "greg", "teen", "clef", "wags", "weal", "each",
				"haft", "nova", "waif", "rate", "katy", "yale", "dale", "leas", "axum", "quiz", "pawn", "fend", "capt",
				"laws", "city", "chad", "coal", "nail", "zaps", "sort", "loci", "less", "spur", "note", "foes", "fags",
				"gulp", "snap", "bogs", "wrap", "dane", "melt", "ease", "felt", "shea", "calm", "star", "swam", "aery",
				"year", "plan", "odin", "curd", "mira", "mops", "shit", "davy", "apes", "inky", "hues", "lome", "bits",
				"vila", "show", "best", "mice", "gins", "next", "roan", "ymir", "mars", "oman", "wild", "heal", "plus",
				"erin", "rave", "robe", "fast", "hutu", "aver", "jodi", "alms", "yams", "zero", "revs", "wean", "chic",
				"self", "jeep", "jobs", "waxy", "duel", "seek", "spot", "raps", "pimp", "adan", "slam", "tool", "morn",
				"futz", "ewes", "errs", "knit", "rung", "kans", "muff", "huhs", "tows", "lest", "meal", "azov", "gnus",
				"agar", "sips", "sway", "otis", "tone", "tate", "epic", "trio", "tics", "fade", "lear", "owns", "robt",
				"weds", "five", "lyon", "terr", "arno", "mama", "grey", "disk", "sept", "sire", "bart", "saps", "whoa",
				"turk", "stow", "pyle", "joni", "zinc", "negs", "task", "leif", "ribs", "malt", "nine", "bunt", "grin",
				"dona", "nope", "hams", "some", "molt", "smit", "sacs", "joan", "slav", "lady", "base", "heck", "list",
				"take", "herd", "will", "nubs", "burg", "hugs", "peru", "coif", "zoos", "nick", "idol", "levi", "grub",
				"roth", "adam", "elma", "tags", "tote", "yaws", "cali", "mete", "lula", "cubs", "prim", "luna", "jolt",
				"span", "pita", "dodo", "puss", "deer", "term", "dolt", "goon", "gary", "yarn", "aims", "just", "rena",
				"tine", "cyst", "meld", "loki", "wong", "were", "hung", "maze", "arid", "cars", "wolf", "marx", "faye",
				"eave", "raga", "flow", "neal", "lone", "anne", "cage", "tied", "tilt", "soto", "opel", "date", "buns",
				"dorm", "kane", "akin", "ewer", "drab", "thai", "jeer", "grad", "berm", "rods", "saki", "grus", "vast",
				"late", "lint", "mule", "risk", "labs", "snit", "gala", "find", "spin", "ired", "slot", "oafs", "lies",
				"mews", "wino", "milk", "bout", "onus", "tram", "jaws", "peas", "cleo", "seat", "gums", "cold", "vang",
				"dewy", "hood", "rush", "mack", "yuan", "odes", "boos", "jami", "mare", "plot", "swab", "borg", "hays",
				"form", "mesh", "mani", "fife", "good", "gram", "lion", "myna", "moor", "skin", "posh", "burr", "rime",
				"done", "ruts", "pays", "stem", "ting", "arty", "slag", "iron", "ayes", "stub", "oral", "gets", "chid",
				"yens", "snub", "ages", "wide", "bail", "verb", "lamb", "bomb", "army", "yoke", "gels", "tits", "bork",
				"mils", "nary", "barn", "hype", "odom", "avon", "hewn", "rios", "cams", "tact", "boss", "oleo", "duke",
				"eris", "gwen", "elms", "deon", "sims", "quit", "nest", "font", "dues", "yeas", "zeta", "bevy", "gent",
				"torn", "cups", "worm", "baum", "axon", "purr", "vise", "grew", "govs", "meat", "chef", "rest", "lame");

		long start = System.currentTimeMillis();
		int levelCharReplaceBiBFSQueue = wordLadderCharReplaceBiBFSQueue(beginWord, endWord, wordList);
		long end = System.currentTimeMillis();
		System.out.println("Char Replace Bidirectional BFS Queue: The level of the word ladder is "
				+ levelCharReplaceBiBFSQueue + " Time taken: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelTransformMapBiBFSQueue = wordLadderTransformMapBiBFSQueue(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Transform Map Bidirectional BFS Queue: The level of the word ladder is "
				+ levelTransformMapBiBFSQueue + " Time taken: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelTransformMapBiBFSSet = wordLadderTransformMapBiBFSSet(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Transform Map Bidirectional BFS Set: The level of the word ladder is "
				+ levelTransformMapBiBFSSet + " Time taken: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelTransformMapBiBFSQueueLayer = wordLadderTransformMapBiBFSQueueLayer(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Transform Map Bidirectional BFS Queue Layer: The level of the word ladder is "
				+ levelTransformMapBiBFSQueueLayer + " Time taken: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelTransformMap = wordLadderTransformMap(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Transform Map: The level of the word ladder is " + levelTransformMap + " Time taken: "
				+ (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelCharArr = wordLadderCharArr(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Char Array: The level of the word ladder is " + levelCharArr + " Time taken: "
				+ (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelToCharArr = wordLadderLevelToCharArr(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Level To Char Array: The level of the word ladder is " + levelToCharArr + " Time taken: "
				+ (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelString = wordLadderIntCharArr(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Int Char Array: The level of the word ladder is " + levelString + " Time taken: "
				+ (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelPairStringBuilder = wordLadderPairStringBuilder(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Pair StringBuilder: The level of the word ladder is " + levelPairStringBuilder
				+ " Time taken: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		int levelPairString = wordLadderPairString(beginWord, endWord, wordList);
		end = System.currentTimeMillis();
		System.out.println("Pair String: The level of the word ladder is " + levelPairString + " Time taken: "
				+ (end - start) + " ms");

	}

	// Bidirectional BFS: Char replacement with 2 queues
	// We use char replacement as keeping an allCombinations hashmap is costly in
	// memory and preprocessing. Instead of precomputing patterns like h*t -> [hot,
	// hit]. We generate neighbors on the fly by changing each character: hit ->
	// ait, bit, ..., zit | hit -> hat, hbt, ..., hzt | hit -> hia, hib, ... hiz
	// We check if the generated word exists in the set created from wordList.
	// This is faster than HashMap as map had preprocessing which takes O(N*L^2).
	// Memory taken is huge as it stores all * patterns. In char replacement, there
	// is no preprocessing. Each step is O(26*L), total: O(N*L*26) -> much cleaner
	// and faster in practice. One may remove the nextWord from set but it may not
	// be optimal. It can avoid revisiting the set entirely.
	// Bidirectional BFS optimizes over BFS. It may seem that per step, the work
	// looks similar(generate neigbors, check visited, push into the queue).
	// Bidirectional BFS is faster, here the steps are not cheaper, it's due to
	// exploring far fewer levels of the search tree. Model the search as a tree.
	// Average branching factor = b (each word yields ~b valid next words)
	// Shortest path length = d. In Single-direction BFS, one expand level by level
	// from the start: Total nodes visited: 1+b+b^2+...+b^d ~ (b^(d+1) - 1)/(b-1)
	// ~ theta(b^d). Here the work is dominated by the last level = b^d.
	// Bidirectional BFS: We run 2 BFS waves: One from the start and One from the
	// end. They They meet roughly in the middle, after d/2 level each.
	// Each side explores: 1+b+b^2+...+b^(d/2) ~ (b^(d/2+1) - 1)/(b-1) ~
	// theta(b^(d/2)). Total work: theta(b^(d/2)) + theta(b^(d/2)) =
	// theta(2*b^(d/2)). Comparing the 2: taking the ratio b^d/2*b^(d/2) = b^(d/2)/2
	// So the speedup factor is roughly b^(d/2)/2. Why this is good, as the
	// exponential growth is key: depth d = 10, branching factor b = 10. Single BFS:
	// 10^10 = 10000000000. Bi-BFS: 2*10^5 = 200000. This is 50000x reduction.
	// Hence, even though it seems similar at the micro level in BI-BFS like same
	// neighbor generation/checks. But the optimization is at macro-level. One
	// avoids exploring deep exponential layers. One can cut depth from d -> d/2.
	// For word ladder: each word generates upto 26*L candidates. Effective
	// branching factor b is smaller due to dictionary filtering, but still > 1.
	// So, single BFS -> explores many invalid layers before reaching end word.
	// Bidirectional BFS -> meets early, prunes half the depth. Bidirectional BFS
	// doesn't reduce cost per node. It reduces the number of nodes explored
	// exponentially. Work drops from b^d to 2*b^(d/2). It's a optimization for
	// shortest-path problems on implicit graphs like word ladder.
	private static int wordLadderCharReplaceBiBFSQueue(String beginWord, String endWord, List<String> wordList) {
		if (!wordList.contains(beginWord)) {
			return 0;
		}
		Set<String> wordSet = new HashSet<>(wordList);

		int L = beginWord.length();

		Queue<String> beginQueue = new LinkedList<>();
		beginQueue.offer(beginWord);

		Queue<String> endQueue = new LinkedList<>();
		endQueue.offer(endWord);

		Set<String> beginVisited = new HashSet<>();
		beginVisited.add(beginWord);

		Set<String> endVisited = new HashSet<>();
		endVisited.add(endWord);

		int level = 1;
		while (!beginQueue.isEmpty() && !endQueue.isEmpty()) {
			// Expand smaller side
			if (beginQueue.size() > endQueue.size()) {
				Queue<String> tempQueue = beginQueue;
				beginQueue = endQueue;
				endQueue = tempQueue;

				Set<String> tempVisited = beginVisited;
				beginVisited = endVisited;
				endVisited = tempVisited;
			}

			int size = beginQueue.size();

			while (size-- > 0) {
				char[] word = beginQueue.poll().toCharArray();

				for (int i = 0; i < L; i++) {
					char old = word[i];

					for (char c = 'a'; c <= 'z'; c++) {
						word[i] = c;
						String nextWord = new String(word);

						// intersection
						if (endVisited.contains(nextWord)) {
							return level + 1;
						}
						if (!beginVisited.contains(nextWord) && wordSet.contains(nextWord)) {
							beginQueue.offer(nextWord);
							beginVisited.add(nextWord);
						}
					}

					word[i] = old; // restore
				}
			}
			level++;
		}

		return 0;
	}

	// Bidirectional BFS: Queues
	// We use 2 Queues, 2 visited sets(startVisited, endVisited).
	// endVisited is used for collision detection. 2 queues are used for
	// bidirectional BFS. Direction switching is the key optimization. 2 BFS waves:
	// one from beginWord and another from endWord. When they meet -> shortest path
	// found. Expanding the smaller queue avoids unnecessary work.
	private static int wordLadderTransformMapBiBFSQueue(String beginWord, String endWord, List<String> wordList) {
		if (!wordList.contains(endWord)) {
			return 0;
		}

		int len = beginWord.length();
		Map<String, List<String>> allCombinations = new HashMap<>();

		for (String word : wordList) {
			for (int i = 0; i < len; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
				String intermediate = sb.toString();

				allCombinations.computeIfAbsent(intermediate, k -> new ArrayList<>()).add(word);
			}
		}

		// Queues for both ends
		Queue<String> beginQueue = new LinkedList<>();
		beginQueue.offer(beginWord);

		Queue<String> endQueue = new LinkedList<>();
		endQueue.offer(endWord);

		// Visited sets (word -> level)
		Set<String> beginVisited = new HashSet<>();
		beginVisited.add(beginWord);

		Set<String> endVisited = new HashSet<>();
		endVisited.add(endWord);

		int level = 1;

		while (!beginQueue.isEmpty() && !endQueue.isEmpty()) {

			// Always expand smaller queue
			if (beginQueue.size() > endQueue.size()) {
				Queue<String> tempQueue = beginQueue;
				beginQueue = endQueue;
				endQueue = tempQueue;

				Set<String> tempVisited = beginVisited;
				beginVisited = endVisited;
				endVisited = tempVisited;
			}
			int size = beginQueue.size();

			while (size-- > 0) {
				String word = beginQueue.poll();

				for (int i = 0; i < len; i++) {
					StringBuilder sb = new StringBuilder();
					sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
					String intermediate = sb.toString();

					List<String> combinations = allCombinations.getOrDefault(intermediate, new ArrayList<>());

					for (String nextWord : combinations) {

						// intersection found
						if (endVisited.contains(nextWord)) {
							return level + 1;
						}
						if (!beginVisited.contains(nextWord)) {
							beginQueue.add(nextWord);
							beginVisited.add(nextWord);
						}
					}
				}
			}
			level++;
		}

		return 0;
	}

	// Bidirectional BFS: Sets
	// We maintain 2 queues: one from start, one from end and also 2 visited sets.
	// We always expand the smalle frontier (optimization). We stop when both sides
	// meet. Instead of 1 BFS wave, we do 2 waves, begin -> -> <- <- end. This cuts
	// search space drastically from O(N) to O(n/2) depth. Visited is shared and
	// prevents cycles across both directions. We return level + 1 when intersection
	// happens. No need for explicit queues - Set works better for bidirectional
	// BFS.
	private static int wordLadderTransformMapBiBFSSet(String beginWord, String endWord, List<String> wordList) {
		if (!wordList.contains(beginWord)) {
			return 0;
		}
		int len = beginWord.length();
		Map<String, List<String>> comboMap = new HashMap<>();

		for (String word : wordList) {
			for (int i = 0; i < len; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
				String intermediate = sb.toString();
				comboMap.computeIfAbsent(intermediate, k -> new ArrayList<>()).add(word);
			}
		}

		Set<String> beginSet = new HashSet<>();
		beginSet.add(beginWord);

		Set<String> endSet = new HashSet<>();
		endSet.add(endWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int level = 1;

		while (!beginSet.isEmpty() && !endSet.isEmpty()) {
			// Always expand smaller set
			if (beginSet.size() > endSet.size()) {
				Set<String> temp = beginSet;
				beginSet = endSet;
				endSet = temp;
			}

			Set<String> nextLevelSet = new HashSet<>();

			for (String word : beginSet) {
				for (int i = 0; i < len; i++) {
					StringBuilder sb = new StringBuilder();
					sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
					String intermediate = sb.toString();
					List<String> combinations = comboMap.getOrDefault(intermediate, new ArrayList<>());

					for (String nextWord : combinations) {
						// If connection is found
						if (endSet.contains(nextWord)) {
							return level + 1;
						}

						if (!visited.contains(nextWord)) {
							nextLevelSet.add(nextWord);
							visited.add(nextWord);
						}
					}
				}
			}
			level++;
			beginSet = nextLevelSet;
		}
		return 0;
	}

	static int ladderLen = 0;

	// Bidirectional BFS: 2 Queues with 1 BFS layer.
	// visitWordNode(...) handles one BFS layer. Uses visited -> current direction.
	// othersVisited -> opposite direction. When a word appears in both -> path
	// found. if (otherVisited.contains(nextWord)) { -> Merges both search trees and
	// gives total path length.
	// The graph formed from the nodes in the allCombinations map might be too big.
	// The search space considered by the BFS algo depends upon the branching factor
	// of the nodes at each level(each word can form ~26 valid next words). If the
	// branching factor remains the same for all the nodes, the search space
	// increases exponentially along with the number of levels(d). Consider a simple
	// example of a binary treee. With each passing level in a complete binary tree,
	// the number of nodes increase in powers of 2. We can considerably cut down the
	// search space of the standard BFS algo if we launch 2 simultaneous BFS as it'd
	// lead to level being reduced to level/2 for each BFS. We start 1 BFS from
	// beginWord and one from the endWord. We progress one node at a time from both
	// sides and at any point in time if we find a common node in both the searches,
	// we stop the search. The 2 paraller searches meet at some point if they are
	// connected. It's bidirectional BFS and it considerably cuts down on
	// the search space and hence reduces the time and space complexity. Algo:
	// This algo is similar to standard BFS but we now do BFS starting 2 nodes. This
	// changes the termination condition of our search. Now we've 2 visited
	// sets to keep track of nodes visited from the search starting at the
	// respective ends. If we ever find a node/word which is in the visited set of
	// the parallel search we terminate our search, since we found the meet point of
	// this bidirectional search. It's like we met in the middle(depth = d/2)
	// instead of going all the way through(d). Terminations condition for
	// bidirectional search is finding a word which is already been seen by the
	// parallel search. The shortest transformations sequence is the sum of levels
	// of the meet point from both the ends(l1+l2). Thus, for every visited node we
	// save its level as value in the visited set.
	// Time complexity - O(M^2*N), where M is the length of words and N is the total
	// number of words in the input word list. Just like 1 directional,
	// bidirectional BFS also takes O(M^2*N) time for finding out all the
	// transformations. But the search time reduces to half, since the 2 parallel
	// searches meet somewhere in the middle.
	// Space complexity - O(M^2*N), to store all M transformations for each of the N
	// words in the allCombinations map, same as 1 directional BFS. But
	// bidirectional BFS reduces the search space. It narrows down because of
	// meeting in the middle.
	private static int wordLadderTransformMapBiBFSQueueLayer(String beginWord, String endWord, List<String> wordList) {
		if (!wordList.contains(endWord)) {
			return 0;
		}
		int len = beginWord.length();
		// Map holds combinations of words that can be formed, from any given word. By
		// changing 1 letter at a time.
		Map<String, List<String>> comboMap = new HashMap<>();

		// Preprocessing
		for (String word : wordList) {
			for (int i = 0; i < len; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
				String intermediate = sb.toString();

				comboMap.computeIfAbsent(intermediate, k -> new ArrayList<>()).add(word);
			}
		}

		// Queues for both ends
		Queue<String> beginQueue = new LinkedList<>();
		beginQueue.offer(beginWord);

		Queue<String> endQueue = new LinkedList<>();
		endQueue.offer(endWord);

		// Visited sets(word -> level)
		Set<String> beginVisited = new HashSet<>();
		beginVisited.add(beginWord);

		Set<String> endVisited = new HashSet<>();
		endVisited.add(endWord);

		ladderLen = 1;

		while (!beginQueue.isEmpty() && !endQueue.isEmpty()) {
			// Expand from begin side
			int ans = visitWordNode(beginQueue, beginVisited, endVisited, len, comboMap);
			if (ans > -1) {
				return ans;
			}

			// Expand from end side
			ans = visitWordNode(endQueue, endVisited, beginVisited, len, comboMap);
			if (ans > -1) {
				return ans;
			}
		}
		return 0;
	}

	private static int visitWordNode(Queue<String> queue, Set<String> visited, Set<String> otherVisited, int len,
			Map<String, List<String>> comboMap) {
		int size = queue.size();

		while (size-- > 0) {
			String word = queue.poll();

			for (int i = 0; i < len; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
				String intermediate = sb.toString();

				List<String> combinations = comboMap.getOrDefault(intermediate, new ArrayList<>());

				for (String nextWord : combinations) {
					// Intersection found
					// Merges both search trees and gives total path length
					if (otherVisited.contains(nextWord)) {
						return ladderLen + 1; // ladderLen + othersVisited.get(nextWord) if javafx.util.Pair is used.
					}
					if (!visited.contains(nextWord)) {
						queue.offer(nextWord);
						visited.add(nextWord);
					}
				}
			}
		}

		ladderLen++;
		return -1;
	}

	// BFS: Combinations map
	// We're given a beginWord and an endWord. Let these 2 represent start node and
	// end node of the graph. We've to reach from start to the end node using some
	// intermediate nodes/words. These intermediate nodes are determined by the
	// wordList given to us. The only condition for every step we take on this
	// ladder of words is the current word should change by just 1 letter. We will
	// be working with an undirected and unweighted graph with words as nodes and
	// edges between words which differ by just 1 letter. We find the shortest path
	// from a start node to a destination node, if there exists one. Hence, it can
	// be solved using BFS approach. One of the most important step is to find the
	// adjacent nodes i.e. words which differ by 1 letter. To efficiently find the
	// neighboring nodes for any given word we do some pre-processing on the words
	// of the given wordList. This involves replacing the letter of a word by a
	// non-alphabet(*). This pre-processing helps to form generic states to
	// represent a 1 letter change. Example - hot -> *ot, h*t, ho*, Dog-> D*g <-Dig
	// Both Dog and Dig map to the same intermedite or generic state D*g. The
	// preprocessing helps to find the generic 1 letter away nodes for any word of
	// the wordList and hence making it easy to get adjacent nodes. Else, for every
	// word we'll have to iterate over the entire wordList and find words that
	// differ by 1 letter which takes lot of time. This preprocessing step helps to
	// build the adjacency list before beginning the BFS algorithm. Example - While
	// doing BFS if we've to find the adjacent nodes for Dug we can 1st find all teh
	// generic states for Dug -> *ug, D*g, Du*. The 2nd transformation D*g could
	// then be mapped to Dog or Dig, since all of them stare the generic sttate.
	// Having common generic transformation means 2 words are connected(1 letter).
	// Intuition: We start from beginWord and search the endWord via BFS. Algo:
	// Do the pre-processing on the given wordList and find all the possible generic
	// states. Save these intermediate states in a dictionary/map - allCombinations
	// with key as the intermediate word and value as the list of words which have
	// the same intermediate word. Push a Pair containing the beginWord and 1(level)
	// in a queue. level = 1 represents the level number of a node. We've to return
	// the level of the endNode as it represents the shortest distance from
	// beginWord. To prevent cycles we use a visited map/set with string word and
	// boolean present. While the queue has elements, get the front element of the
	// queue - currentWord. Find all the generic transformations of currentWord and
	// find out if any of these transformations is also a transformation of other
	// words in the wordList. This is done via allCombinations map. The list of
	// words we get from this map are all the words which have a common intermediate
	// state with the currentWord. These set of words will be the adjacent
	// nodes/words to currentWord and added to queue (word, level+1). Here, level is
	// level for currentWord. Eventually if one reaches the desired endWord, its
	// level represents the shortest transformation sequeunce length. Termination
	// condition for standard BFS is finding the endWord.
	// Time complexity: O(M^2*N), were M is the length of each word and N is the
	// total number of words in wordList. For each word in the word list, we iterate
	// over its length to find all its intermediate words. Hence, total number of
	// iterations to create allCombinations map is M*N. Also, to form each
	// intermediate word we take O(M) time as the substring operation used to create
	// the new string.
	// BFS in worst case may go to each of the N words. For each word, we need to
	// examine M possible intermediate words/combinations. We use substring
	// operation to find each of the combination. Thus, M combinations take O(M^2)
	// time. As a result, the time complexity of BFS traversal is O(M^2*N).
	// Space complexity - O(M^2*N), each word in the wordList would have M
	// intermediate combinations. To create the allCombinations map, we save an
	// intermediate word as the key and its corresponding original words as the
	// value. Note, for each of the M intermediate words, we save the original word
	// of length M. It means for every word we would need a space of M^2 to save all
	// teh transformations corresponding to it. This allCombinations map takes
	// O(M^2*N). Visited set takes O(M*N) as max. Queue for BFS in worst case would
	// need to store all O(N) words which takes O(M*N) space. We may reduce the
	// space complexity of this by storing the indices of each word instead of word.
	private static int wordLadderTransformMap(String beginWord, String endWord, List<String> wordList) {
		if (!wordList.contains(endWord)) {
			return 0;
		}

		// Map holds combination of words that can be formed, from any given word. By
		// changing 1 letter at a time.
		Map<String, List<String>> combinationsMap = new HashMap<>();
		int len = beginWord.length(); // All words are of same length.

		for (String word : wordList) { // wordList.forEach(word -> { can also be used
			for (int i = 0; i < len; i++) {
				StringBuilder sb = new StringBuilder();
				sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
				String intermediate = sb.toString();
				// Key is the generic / intermediate word
				// Value is the list of words which have the same intermediate generic word.
				combinationsMap.computeIfAbsent(intermediate, k -> new ArrayList<>()).add(word);

//				List<String> transformations = combinationsMap.getOrDefault(intermediate, new ArrayList<>());
//				transformations.add(word);
//				combinationsMap.put(intermediate, transformations);
			}
		}

		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);

		// Visited ensures we don't repeat processing same word.
		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int level = 1;

		while (!queue.isEmpty()) {
			int size = queue.size();

			while (size-- > 0) {
				String word = queue.poll();
				for (int i = 0; i < len; i++) {
					StringBuilder sb = new StringBuilder();
					sb.append(word.substring(0, i)).append('*').append(word.substring(i + 1));
					String intermediate = sb.toString();
					List<String> transformations = combinationsMap.getOrDefault(intermediate, new ArrayList<>());

					for (String nextWord : transformations) {
						if (endWord.equals(nextWord)) {
							return level + 1;
						}
						if (!visited.contains(nextWord)) {
							queue.offer(nextWord);
							visited.add(nextWord);
						}
					}
				}
			}
			level++;
		}
		return 0;
	}

	private static int wordLadderCharArr(String beginWord, String endWord, List<String> wordList) {
		Set<String> wordSet = new HashSet<>(wordList);
		if (!wordSet.contains(endWord)) {
			return 0;
		}

		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int level = 1;
		while (!queue.isEmpty()) {
			int size = queue.size();

			while (size-- > 0) {
				String curr = queue.poll();
				char[] word = curr.toCharArray();

				for (int i = 0; i < word.length; i++) {
					char old = word[i];
					for (char c = 'a'; c <= 'z'; c++) {
						word[i] = c;

						String newWord = new String(word);

						if (wordSet.contains(newWord) && !visited.contains(newWord)) {
							if (endWord.equals(newWord)) {
								return level + 1;
							}
							queue.offer(newWord);
							visited.add(newWord);
						}
					}
					word[i] = old;
				}
			}
			level++;
		}
		return 0;
	}

	private static int wordLadderLevelToCharArr(String beginWord, String endWord, List<String> wordList) {
		Set<String> wordSet = new HashSet<>(wordList);
		if (!wordSet.contains(endWord)) {
			return 0;
		}

		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int level = 1;
		while (!queue.isEmpty()) {
			int size = queue.size();

			while (size-- > 0) {
				String curr = queue.poll();

				// Can put this char[] inside the 2 for loop as it'd prevent one from keeping
				// track of the old char replacement, but it's slower as new char[] is always
				// formed instead of reusing.
				char[] word = curr.toCharArray();

				for (int i = 0; i < word.length; i++) {
					char old = word[i];
					for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
						word[i] = c;
						String newWord = new String(word);
						if (wordSet.contains(newWord) && !visited.contains(newWord)) {
							if (endWord.equals(newWord)) {
								return level + 1;
							}
							queue.offer(newWord);
							visited.add(newWord);
						}
					}
					word[i] = old;
				}
			}
			level++;
		}
		return 0;
	}

	private static int wordLadderIntCharArr(String beginWord, String endWord, List<String> wordList) {
		Set<String> set = new HashSet<>(wordList);
		if (!set.contains(endWord)) {
			return 0;
		}
		Queue<String> queue = new LinkedList<>();
		queue.add(beginWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);
		int level = 1;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				String word = queue.poll();
				// Below is not necessary
//				if (word.equals(endWord)) {
//					return level;
//				}
				for (int j = 0; j < word.length(); j++) {
					for (int k = 'a'; k <= 'z'; k++) {
						char[] charArr = word.toCharArray();
						charArr[j] = (char) k;
						String newWord = new String(charArr);
						if (set.contains(newWord) && !visited.contains(newWord)) {
							if (newWord.equals(endWord)) {
								return level + 1;
							}
							queue.add(newWord);
							visited.add(newWord);
						}
					}
				}
			}
			level++;

		}
		return 0;
	}

	private static int wordLadderPairStringBuilder(String beginWord, String endWord, List<String> wordList) {
		Set<String> wordSet = new HashSet<>(wordList);// Reduces the time taken in contains operation
		if (!wordSet.contains(endWord)) {
			return 0;
		}

		Queue<Pair<String, Integer>> queue = new LinkedList<>();
		queue.add(new Pair<String, Integer>(beginWord, 1));

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		while (!queue.isEmpty()) {
			Pair<String, Integer> node = queue.poll();
			String word = node.word;
			int level = node.level;

			for (int i = 0; i < word.length(); i++) {
				for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
					StringBuilder newWord = new StringBuilder(word.substring(0, i)).append(c)
							.append(word.substring(i + 1));
					String currentWord = newWord.toString();
					// !currentWord.equals(word) is not needed as visited will prohibit same word
					// reentry
					if (!currentWord.equals(word) && wordSet.contains(currentWord) && !visited.contains(currentWord)) {
						// can take this check outside, before the for loops but it'll be less efficient
						if (currentWord.equals(endWord)) {
							return level + 1;
						}
						queue.add(new Pair<String, Integer>(currentWord, level + 1));
						visited.add(currentWord);
					}
				}
			}
		}
		return 0;
	}

	private static int wordLadderPairString(String beginWord, String endWord, List<String> wordList) {
		Set<String> set = new HashSet<>(wordList);// Reduces the time taken in contains operation
		Queue<Pair<String, Integer>> queue = new LinkedList<>();

		queue.add(new Pair<String, Integer>(beginWord, 1));
		Set<String> visited = new HashSet<>();
		visited.add(beginWord);
		while (!queue.isEmpty()) {
			Pair<String, Integer> node = queue.poll();
			String word = node.word;
			int level = node.level;

			for (int i = 0; i < beginWord.length(); i++) {
				// String c : "abcdefghijklmnopqrstuvwxyz".split("") is slower
				for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
					String currentWord = word.substring(0, i) + c + word.substring(i + 1); // Adding string is slow
					// !currentWord.equals(word) is not needed as visited will prohibit same word
					// reentry
					if (!currentWord.equals(word) && set.contains(currentWord) && !visited.contains(currentWord)) {
						if (currentWord.equals(endWord)) {
							return level + 1;
						}
						queue.add(new Pair<String, Integer>(currentWord, level + 1));
						visited.add(currentWord);
					}
				}
			}
		}
		return 0;
	}

}
