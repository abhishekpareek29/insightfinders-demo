import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class SearchResult {
  public String search_term;
  public int wordCount;

  public void SearchResult(String search_term) {
    search_term = search_term;
    wordCount = 0;
  }

  public void incWordCount(int i) {
    wordCount = wordCount + i;
  }
}

public class SearchSession {
  public ArrayList<SearchResult> searchResult = new ArrayList<SearchResult>();
  public int searchCount;
  public String searchdir;

  // Constructor.
  public void SearchSession(int searchCount, String searchdir) {
    searchCount = 0;
    searchdir = "";
  }

  // Main file.
  public static void main(String []args) {
    // SearchSession this = new SearchSession();

    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter a valid relative path to search: ");

    SearchSession awesomesearch = new SearchSession();
    awesomesearch.searchdir = sc.nextLine();
    int wordCountsum = 0;
    String terms = "";

    // Seek user input indefinitely.
    while (true) {
      System.out.println("Please enter a search term: ");
      String search_term = sc.nextLine();

      // EXIT condition.
      if (search_term.equals("-1")) {
        // Display summary.
        System.out.println("Summary: ====================>");
        for (SearchResult j : awesomesearch.searchResult) {
          wordCountsum = wordCountsum + j.wordCount;
          terms =  "'" + j.search_term + "'" + " " + terms;
        }
        System.out.println("Number of searches: " + awesomesearch.searchCount);
        System.out.println("Number of words matched across ALL input files: " + wordCountsum);
        System.out.println("Searched path: " + awesomesearch.searchdir);
        System.out.println("Searched terms: " + terms);

        // Exit.
        System.exit(0);
      }

      // Record search results.
      awesomesearch.searchCount++;
      awesomesearch.searchfiles(search_term);


    }
  }

  // Read files.
  public void searchfiles(String search_term) {
    // Search directory.
    String folder_path;
    folder_path = this.searchdir;

    // Search instance.
    SearchResult search = new SearchResult();
    search.search_term = search_term;

    BufferedReader br = null;
    String msg = "";

    File folder = new File(folder_path);
    File[] files = folder.listFiles();

    if(files == null) {
      System.err.println("No files found.");
      System.exit(0);
    }

    // Search every file.
    for (File f : files) {
      try {
        br = new BufferedReader(new FileReader(folder_path + "/" + f.getName()));
        while((msg = br.readLine()) != null){
          int firstindex = 0;
          int lastindex = 0;

          if (msg.toLowerCase().indexOf(search_term.toLowerCase()) != -1 ) {
            firstindex = msg.toLowerCase().indexOf(search_term.toLowerCase());
            while (firstindex >= 0) {
              // Increment word counter.
              search.wordCount++;
              // Configure msg display.
              lastindex = search_term.length() + firstindex;
              msg = msg.substring(0,firstindex) + msg.substring(firstindex,lastindex).toUpperCase() + msg.substring(lastindex);
              firstindex = msg.toLowerCase().indexOf(search_term.toLowerCase(), firstindex + 1);
            }

            // Print search results.
            msg = "Result: " + msg;
            System.out.println(msg);
          }
        }
      } catch (FileNotFoundException e) {
        System.err.println("Unable to find the file.");
      } catch (IOException e) {
        System.err.println("Unable to read the file.");
      }
    }
    this.searchResult.add(search);
  }
}
