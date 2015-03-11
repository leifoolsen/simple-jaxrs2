package com.github.leifoolsen.simplejaxrs2.repository;

import com.github.leifoolsen.simplejaxrs2.domain.Book;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BookRepository {
    public static final String ALMA_BOOKS    = "18468";
    public static final String DAMM          = "82040";
    public static final String TURNER        = "11181";
    public static final String HISTORY_PRESS = "07524";
    public static final String PAX           = "82530";
    public static final String GYLDENDAL     = "82054";
    public static final String VINTAGE       = "17847";
    public static final String VINTAGE_UK    = "00995";
    public static final String CAPPELEN      = "82021";
    public static final String CAPPELEN_DAMM = "82022";
    public static final String PICADOR       = "14472";
    public static final String WEIDENFELD    = "02978";
    public static final String DOUBLEDAY     = "08575";
    public static final String PENGUIN       = "02419";

    private static final  ConcurrentMap<String, String> publishers = new ConcurrentHashMap<String, String>() {{
        put(ALMA_BOOKS   , "Alma Books");
        put(DAMM         , "Damm");
        put(TURNER       , "Turner Publishing Company");
        put(HISTORY_PRESS, "The History Press");
        put(PAX          , "Pax");
        put(GYLDENDAL    , "Gyldendal");
        put(VINTAGE      , "Vintage Books");
        put(VINTAGE_UK   , "Vintage Books UK");
        put(CAPPELEN     , "Cappelen");
        put(CAPPELEN_DAMM, "Cappelen Damm");
        put(PICADOR      , "Picador");
        put(WEIDENFELD   , "Weidenfeld & Nicolson (Orion Publishing Co)");
        put(DOUBLEDAY    , "Doubleday (Transworld Publishers Ltd)");
        put(PENGUIN      , "Penguin Books Ltd");
    }};

    private static final ConcurrentMap<String, Book> bookRepository = new ConcurrentHashMap<String, Book>() {{
        put("9781846883668",
            Book.with("9781846883668")
                .title("Travelling to Infinity: The True Story")
                .author("Hawking, Jane")
                .published(new GregorianCalendar(2014, 12, 18).getTime())
                .summary("Soon to be a major motion picture starring Eddie Redmayne as Hawking and Felicity " +
                        "Jones as his wife Jane. It chronicles their relationship, from his early development " +
                        "of ALS to his success in physics.")
                .build()
        );
        put("9788204094261",
            Book.with("9788204094261")
                .title("Superstjernen Stephen Hawking: biografi")
                .author("Gribbin, John; White, Michael")
                .published(new GregorianCalendar(2014, 1, 1).getTime())
                .translator("Larsen, Anne Kirsti Solheim; Larsen, Finn B.")
                .summary("Boka forteller om livet til vitenskapsmannen Stephen Hawking. Boka gir ett innblikk i både " +
                        "privatlivet til Hawking, og i hans karriere som forsker innenfor teoretisk fysikk og " +
                        "astrofysikk. Den setter vitenskapen inn i en menneskelig sammenheng, og viser hvordan " +
                        "vitenskap og liv er uløselig knytta sammen for Hawking. Har register.")
                .build() // Damm, Bokmål
        );
        put("9781118175392",
            Book.with("9781118175392")
                .title("Alone in the Universe: Why Our Planet Is Unique")
                .author("Gribbin, John")
                .published(new GregorianCalendar(2011, 1, 1).getTime())
                .summary("The acclaimed author of In Search of Schrodinger's Cat searches for life on other " +
                        "planetsAre we alone in the universe? Surely amidst the immensity of the cosmos there must " +
                        "be other intelligent life out there. Don't be so sure, says John Gribbin, one of today's " +
                        "best popular science writers. In this fascinating and intriguing new book, Gribbin argues " +
                        "that the very existence of intelligent life anywhere in the cosmos is, from an " +
                        "astrophysicist's point of view, a miracle. So why is there life on Earth and (seemingly) " +
                        "nowhere else? What happened to make this planet special? Taking us back some 600 million " +
                        "years, Gribbin lets you experience the series of unique cosmic events that were " +
                        "responsible for our unique form of life within the Milky Way Galaxy.Written by one of " +
                        "our foremost popular science writers, author of the bestselling In Search of Schrodinger's " +
                        "Cat Offers a bold answer to the eternal question, \"Are we alone in the universe?\"" +
                        "Explores how the impact of a quote; with Venus 600 million years ago created our moon, " +
                        "and along with it, the perfect conditions for life on EarthFrom one of our most talented " +
                        "science writers, this book is a daring, fascinating exploration into the dawning of the " +
                        "universe, cosmic collisions and their consequences, and the uniqueness of life on Earth.")
                .build() // Turner Publishing Company, Engelsk
        );
        put("9780752495620",
            Book.with("9780752495620")
                .title("Guide to Middle Earth: Tolkien and The Lord of the Rings")
                .author("Duriez, Colin")
                .published(new GregorianCalendar(2013, 1, 1).getTime())
                .summary("An illuminating guide to Middle-earth and the man who created it.")
                .build() // The History Press, Engelsk
        );
        put("9788253019727",
            Book.with("9788253019727")
                .title("Vredens druer")
                .author("Steinbeck, John")
                .published(new GregorianCalendar(1998, 1, 1).getTime())
                .translator("Omre, Arthur")
                .summary("I 1939 (på norsk i 1940) kom denne romanen som slo fast at Steinbeck var en av mestrene " +
                        "i moderne amerikansk prosadiktning. Romanen står i dag som et minnesmerke over " +
                        "depresjonens elendighet. Handlingen er hentet fra 1930-årenes USA. Hovedpersonene er " +
                        "familien Joad, en av mange farmerfamilier som må flykte fra Oklahoma, etter at banker og " +
                        "storspekulanter har tatt jorda fra dem. Lokket av løfter fra farmere i California begir de " +
                        "seg vestover mot det forjettede land, der ny elendighet venter dem.")
                .build() // Pax, Norsk
        );
        put("9788205478428",
            Book.with("9788205478428")
                .title("Gravrøys")
                .author("Theorin, Johan")
                .published(new GregorianCalendar(2014, 1, 1).getTime())
                .translator("Bolstad, Kari")
                .summary("I Johan Theorins fjerde bok fra Öland forenes fortid og nåtid i en særdeles " +
                        "velskrevet og ubehagelig krim. Det er midtsommer på Öland. Tusenvis av turister er " +
                        "kommet til den kalkhvite øya, der sommersolen steker, men ferieparadiset skjuler " +
                        "mørke hemmeligheter. En av de besøkende har kommet tilbake for å kreve betaling for " +
                        "gammel gjeld. Han etterlater seg død og skrekk i sommernatten. Ingen vet hvem han " +
                        "er, eller hva han vil. Men det er én mann som begynner å ane uråd. Gerlof Davidsson, " +
                        "en av øyas eldste innbyggere, begynner å forstå hvem den ukjente er, og hvorfor han " +
                        "søker hevn. Han har nemlig møtt mannen før, i ungdomstiden, da de begge sto på " +
                        "kirkegården og plutselig hørte banking fra en kiste ? I Johan Theorins fjerde bok " +
                        "fra Öland, Gravrøys, forenes fortid og nåtid i en særdeles velskrevet og " +
                        "ubehagelig krim.")
                .build() // Gyldendal, Norsk
        );
        put("9788205478336",
            Book.with("9788205478336")
                .title("Kuppet: på innsiden av Norges mektigste mafiafamilie")
                .author("Aass, Hans Petter; Widerøe, Rolf J.")
                .published(new GregorianCalendar(2015, 1, 1).getTime())
                .summary("En ny, knallsterk dokumentar fra forfatterne av bestselgerne Dødsranet og Krigshelten. " +
                        "Tema: Nordea-kuppet, norgeshistoriens frekkeste bedrageri. 20. juli 2010 tropper en 49 " +
                        "år gammel hjelpepleier opp i Nordeas filialer på Tveita i Oslo. Utkledd som " +
                        "millionærarving og med falskt bankkort ber hun en velvillig saksbehandler om å " +
                        "registrere en fullmakt på en ung mann i hennes følge. Snart er 62 millioner kroner " +
                        "overført til flere konti i Dubai. Pengene er aldri kommet til rette. Hovedmannen går " +
                        "fortsatt fri. Forfatterne går tett på den norsk-pakistanske familien bak bedrageriet, " +
                        "av politiet utpekt som den mektigste mafiafamilien i Norge. I kretsen rundt dem dukker " +
                        "det opp flere sentrale personer fra Norges tyngste kriminelle miljøer ? fra «B-gjengen», " +
                        "kriminelle MC-bander, ransmiljøet og torpedomiljøet. Det er nådeløse oppgjør, " +
                        "luksusliv i Dubai og pengestrømmer på ville veier. I Oslos underverden foregår det " +
                        "ting du aldri ville ha trodd.")
                .build() // Gyldendal, Norsk
        );
        put("9788205418820",
            Book.with("9788205418820")
                .title("Gyldendals store fugleguide: Europas og middelhavsområdets fugler i felt")
                .author("Svensson, Lars")
                .published(new GregorianCalendar(2011, 1, 1).getTime())
                .translator("Sandvik, Jostein; Syvertsen, Per Ole")
                .summary("Verdens beste fuglebok i ny feltutgave! Gyldendals store fugleguide er markedets mest " +
                        "omfattende felthåndbok og anses som et normgivende standardverk for fuglehåndbøker. " +
                        "Fugleguiden gjennomgikk en omfattende revidering i 2010. Omfanget ble økt med 48 sider. " +
                        "Ca.20 nye bildesider kom til, mens andre fikk vesentlige tillegg. Arter, slekter og " +
                        "familier er nå ordnet ut fra de siste vitenskapelige erfaringene. I boken blir ca. 900 " +
                        "arter behandlet, derav 800 utførlig. De grundige tekstene er helt oppdatert og i mange " +
                        "tilfeller helt nyskrevne. Her beskrives hver arts størrelse,biotopvalg, kjennetegn og " +
                        "variasjoner. Symbolene angir hvor vanlige eller sjeldne artene er. De oversiktlige " +
                        "kartene viser utbredelsen. For alle som er interessert i fugler, er denne boken like " +
                        "uunnværlig som kikkerten. Redaktør for den norske utgaven er Viggo Ree. Boken er nå " +
                        "innbundet i en feltmessig softcoverutgave.")
                .build()
        );
        put("9781784700089",
            Book.with("9781784700089")
                .title("Alan Turing: The Enigma")
                .author("Hodges, Andrew")
                .published(new GregorianCalendar(2014, 11, 13).getTime())
                .summary("This is the official book that inspired the film The Imitation Game, which stars " +
                        "Benedict Cumberbatch and Keira Knightley, and which has received eight Oscar " +
                        "nominations, including: Best film; Best Actor in a Leading Role; Best Supporting " +
                        "Actress; Best Adapted Screenplay; and Alan Turing was the mathematician whose " +
                        "cipher-cracking transformed the Second World War. Taken on by British Intelligence " +
                        "in 1938, as a shy young Cambridge don, he combined brilliant logic with a flair for " +
                        "engineering. In 1940 his machines were breaking the Enigma-enciphered messages of " +
                        "Nazi Germany's air force. He then headed the penetration of the super-secure " +
                        "U-boat communications. But his vision went far beyond this achievement. Before the " +
                        "war he had invented the concept of the universal machine, and in 1945 he turned t" +
                        "his into the first design for a digital computer. Turing's far-sighted plans for " +
                        "the digital era forged ahead into a vision for Artificial Intelligence. However, " +
                        "in 1952 his homosexuality rendered him a criminal and he was subjected to humiliating " +
                        "treatment. In 1954, aged 41, Alan Turing took his own life.")
                .build() // Vintage
        );
        put("9780099554486",
            Book.with("9780099554486")
                .title("The Lives of Others")
                .author("Mukherjee, Neel")
                .published(new GregorianCalendar(2015, 1, 8).getTime())
                .summary("This is book shortlisted for the Man Booker Prize 2014. It was shortlisted for the " +
                        "Costa Novel Award 2014. Calcutta, 1967. Unnoticed by his family, Supratik has become " +
                        "dangerously involved in extremist political activism. Compelled by an idealistic " +
                        "desire to change his life and the world around him, all he leaves behind before " +
                        "disappearing is a note. At home, his family slowly begins to unravel. Poisonous " +
                        "rivalries grow, the once-thriving family business implodes and destructive secrets " +
                        "are unearthed. And all around them the sands are shifting as society fractures, " +
                        "for this is a moment of turbulence, of inevitable and unstoppable change.")
                .build()
        );
        put("9788202148683",
            Book.with("9788202148683")
                .title("Fisken")
                .author("Loe, Erlend")
                .published(new GregorianCalendar(1994, 1, 1).getTime())
                .summary("Kurt er truckfører. Hver dag kjører han truck nede på kaia, og han løfter kasser " +
                        "som veier over 1000 kilo. Kurt har dessuten bart, og en søt kone og tre rare barn. " +
                        "En dag finner Kurt noe på kaia. Han finner noe han aldri har sett før. " +
                        "Noe virkelig kjempestort.")
                .build()
        );
        put("9781447268970",
            Book.with("9781447268970")
                .title("Station Eleven")
                .author("St. John Mandel, Emily")
                .published(new GregorianCalendar(2015, 1, 1).getTime())
                .summary("DAY ONE The Georgia Flu explodes over the surface of the earth like a neutron bomb. " +
                        "News reports put the mortality rate at over 99%. WEEK TWO Civilization has crumbled. " +
                        "YEAR TWENTY A band of actors and musicians called the Travelling Symphony move through " +
                        "their territories performing concerts and Shakespeare to the settlements that have " +
                        "grown up there. Twenty years after the pandemic, life feels relatively safe. But now " +
                        "a new danger looms, and he threatens the hopeful world every survivor has tried to " +
                        "rebuild. STATION ELEVEN Moving backwards and forwards in time, from the glittering " +
                        "years just before the collapse to the strange and altered world that exists twenty " +
                        "years after, Station Eleven charts the unexpected twists of fate that connect six " +
                        "people: famous actor Arthur Leander; Jeevan - warned about the flu just in time; " +
                        "Arthur's first wife Miranda; Arthur's oldest friend Clark; Kirsten, a young actress " +
                        "with the Travelling Symphony; and the mysterious and self-proclaimed 'prophet'. " +
                        "Thrilling, unique and deeply moving, this is a beautiful novel that asks questions " +
                        "about art and fame and about the relationships that sustain us through anything - " +
                        "even the end of the world.")
                    .build()
        );
        put("9781447279402",
            Book.with("9781447279402")
                .title("The Guest Cat")
                .author("Hiraide, Takashi")
                .published(new GregorianCalendar(2014, 9, 25).getTime())
                .translator("Selland, Eric")
                .summary("THE SUNDAY TIMES AND NEW YORK TIMES BESTSELLER. A couple in their thirties live in a " +
                        "small rented cottage in a quiet part of Tokyo. They work at home as freelance writers. " +
                        "They no longer have very much to say to one another. One day a cat invites itself " +
                        "into their small kitchen. She is a beautiful creature. She leaves, but the next day " +
                        "comes again, and then again and again. New, small joys accompany the cat; the days " +
                        "have more light and colour. Life suddenly seems to have more promise for the husband " +
                        "and wife; they go walking together, talk and share stories of the cat and its little " +
                        "ways, play in the nearby Garden. But then something happens that will change " +
                        "everything again. The Guest Cat is an exceptionally moving and beautiful novel " +
                        "about the nature of life and the way it feels to live it. Written by Japanese poet " +
                        "and novelist Takashi Hiraide, the book won Japan's Kiyama Shohei Literary Award, and " +
                        "was a bestseller in France and America.")
                .build()
        );
        put("9780241971987",
            Book.with("9780241971987")
                .title("Little Failure")
                .author("Shteyngart, Gary")
                .published(new GregorianCalendar(2014, 10, 29).getTime())
                .summary("Gary Shteyngart's parents dreamed that he would become a lawyer, or at least an " +
                        "accountant, something their distracted son was simply not cut out to do. Fusing " +
                        "English and Russian, his mother created the term Failurchka - 'Little Failure' - " +
                        "which she applied to her son. With love. Mostly. A candid and poignant story of a " +
                        "Soviet family's trials and tribulations, and of their escape in 1979 to the " +
                        "consumerist promised land of the USA, Little Failure is also an exceptionally " +
                        "funny account of the author's transformation from asthmatic toddler in Leningrad " +
                        "to 40 - something Manhattanite with a receding hairline and a memoir to write.")
                .build()
        );

        /*
        put("",
                Book.with("")
                        .title("")
                        .author("")
                        .published(new GregorianCalendar(2008, 1, 1).getTime())
                        .translator("")
                        .summary("")
                        .build()
        );
        */
    }};

    private BookRepository() {}

    public static void addBook(final Book book) {
        bookRepository.putIfAbsent(book.getIsbn(), book);
    }

    public static Book findBook(final String isbn) {
        return bookRepository.get(isbn);
    }

    public static boolean removeBook(final String isbn) {
        return bookRepository.remove(isbn) != null ? true : false;
    }

    public static void updateBook(final Book book) {
        removeBook(book.getIsbn());
        addBook(book);
    }

    public static String getPublisherName(final String isbn) {
        return publishers.get(isbn.substring(3, 8));
    }

    public static List<Book> getBooksByPublisher(final String publisherName) {
        List<Book> result = Lists.newArrayList();

        for(Book b : bookRepository.values()) {
            final String pName = getPublisherName(b.getIsbn());
            if(pName.startsWith(publisherName)) {
                result.add(b);
            }
        }
        return result;
    }

    public static List<Book> getBooksByTitle(final String title) {
        List<Book> result = Lists.newArrayList();

        for(Book b : bookRepository.values()) {
            if(title.equals(b.getTitle())) {
                result.add(b);
            }
        }
        return result;
    }

    public static List<Book> getAllBooks(Integer offset, Integer limit) {
        List<Book> result = Lists.newArrayList();

        int o = MoreObjects.firstNonNull(offset, 0);
        int l = MoreObjects.firstNonNull(limit, bookRepository.size());
        int i = 0;
        int n = 0;
        for(Book b : bookRepository.values()) {
            if(i++ >= o) {
                if (n++ < l) {
                    result.add(b);
                }
            }
        }
        return result;
    }

    public static int countBooks() {
        return bookRepository.size();
    }
}
