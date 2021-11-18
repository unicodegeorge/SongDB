
//        .##.....##.##....##.####..######...#######..########..########
//        .##.....##.###...##..##..##....##.##.....##.##.....##.##......
//        .##.....##.####..##..##..##.......##.....##.##.....##.##......
//        .##.....##.##.##.##..##..##.......##.....##.##.....##.######..
//        .##.....##.##..####..##..##.......##.....##.##.....##.##......
//        .##.....##.##...###..##..##....##.##.....##.##.....##.##......
//        ..#######..##....##.####..######...#######..########..########


//        oP"Yb.  dP"Yb  oP"Yb.   .d                  oP"Yb.  dP"Yb  oP"Yb. oP"Yb.
//        "' dP' dP   Yb "' dP' .d88     ________     "' dP' dP   Yb "' dP' "' dP'
//          dP'  Yb   dP   dP'    88     """"""""       dP'  Yb   dP   dP'    dP'
//        .d8888  YbodP  .d8888   88                  .d8888  YbodP  .d8888 .d8888


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;


public class MusicLibrary {

    static ArrayList<Song> addedSongs = new ArrayList<>();

    public static void main(String[] args) {
        final Scanner inputReader = new Scanner(System.in);
        System.out.println("*** FREE SHITTY MUSIC LIBRARY ***");
        System.out.println("How many songs would you like to add ?");

        getSongsFromUser(Integer.parseInt(inputReader.nextLine()), inputReader);
        goToMenu(inputReader);
    }

    public static void addSong(Song song) {
        addedSongs.add(song);
    }


    public static void getSongsFromUser(final int amount, final Scanner inputReader) {

        for (int i = 0; i < amount; i++) {

            // Creating an empty song object.
            // I am updating the objects' values one by one after every users' input.

            Song newSong = new Song();

            System.out.printf("[ADDING SONG %d] Enter the title : ", i + 1);
            newSong.setTitle(inputReader.nextLine());

            System.out.printf("[ADDING SONG %d] Enter name of the author: ", i + 1);
            newSong.setAuthor(inputReader.nextLine());

            System.out.printf("[ADDING SONG %d] Add length in seconds: ", i + 1);
            newSong.setLengthOfSong(Integer.parseInt(inputReader.nextLine()));

            addedSongs.add(newSong);
            System.out.println("* Song added successfully *");
        }

        System.out.printf("You added %d songs!", amount);
    }

    public static void goToMenu(final Scanner inputReader) {

        System.out.println("Choose one of these operations");

        System.out.println("1) Average song length - by author");
        System.out.println("2) Number of songs - by author");
        System.out.println("3) List of songs - by keyword");

        // Switching the users' input

        switch (inputReader.nextLine()) {
            case "1":
                getAverageLengthByAuthor(inputReader);
                break;
            case "2":
                getNumberOfSongsByAuthor(inputReader);
                break;
            case "3":
                getListOfSongsByKeyword(inputReader);
                break;
            case "END":
                System.out.println("Exiting");
                break;
            default:
                System.out.println("You need to choose an operation 1 - 3");
                goToMenu(inputReader);
                break;
        }
    }

    public static void getAverageLengthByAuthor(final Scanner inputReader) {
        System.out.println("Enter the name of the author : ");
        final String author = inputReader.nextLine();

        // .stream().filter() is used to filter out songs with specified author
        // then mapping it out again and collect duration of these songs into a list

        final OptionalDouble average = addedSongs.stream().filter(song -> song.author.equals(author)).mapToInt(song -> song.lengthOfSong).average();

        //Basic condition - which is checking the amount of found songs. -> handles the possibility division by zero.
        if (average.isPresent()) {
            System.out.printf("Average length of songs made by %s is %.2f seconds.", author, average.getAsDouble());
        } else {
            System.out.printf("Could not find author named %s", author);
        }
    }

    public static void getNumberOfSongsByAuthor(Scanner inputReader) {
        System.out.println("Enter the name of the author : ");
        String author = inputReader.nextLine();

        // .stream().filter() is used to filter out songs with specified author
        // then mapping it out again and collecting whole songs this time ( unlike on row 88 - where I had to specify to collect only durations ).

        List<Song> songsByAuthor = addedSongs.stream().filter(song -> song.author.equals(author)).collect(Collectors.toList());

        if (songsByAuthor.size() > 0) {
            System.out.printf("%d songs found! \n", songsByAuthor.size());
            for (Song song : songsByAuthor) {
                System.out.printf("%s - %s [%d seconds] \n", song.getAuthor(), song.getTitle(), song.getLengthOfSong());
            }
        } else {
            System.out.println("Could not find any songs by " + author);
        }
    }

    public static void getListOfSongsByKeyword(Scanner inputReader) {
        System.out.println("Enter the keyword you want to search by : ");
        String keyword = inputReader.nextLine();

        // Filtering songs which contains keyword in the title or in the authors' name and then collecting it to a list via .collect method.

        List<Song> songsByKeyword = addedSongs.stream().filter(song -> song.title.contains(keyword) || song.author.contains(keyword)).collect(Collectors.toList());

        if (songsByKeyword.size() > 0) {
            System.out.printf("%d songs found! \n", songsByKeyword.size());

            //Cycling through found songs and printing them out

            for (Song song : songsByKeyword) {
                System.out.printf("%s - %s [%d seconds] \n", song.getAuthor(), song.getTitle(), song.getLengthOfSong());
            }
        } else {
            System.out.println("Could not find any titles containing " + keyword);
        }
    }
}

//      888888 88  88 888888     888888 88b 88 8888b.
//        88   88  88 88__       88__   88Yb88  8I  Yb
//        88   888888 88""       88""   88 Y88  8I  dY
//        88   88  88 888888     888888 88  Y8 8888Y"