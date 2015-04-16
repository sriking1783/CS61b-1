import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.io.File;

/** Thought of this idea after looking at the BSTMap.java file we created in hw6. We use this
  * class to keep track of the connections between each Commit, so that we don't lose
  * the fundamental structure when perform methods such as rebase. */
public class CommitBody implements Serializable {

    /** Used to keep track of previous commit messages */
    private CommitBody past;
    /** Message that represents each commit. */
    private String message;
    /** Keeps track of the commitID for each commit (different for each body, so it should not
      * ever change for each individual commit). */
    private int commitID;
    /** Keeps track of the current time when the commit was made */
    private Date commitTime;
    /** Used to store all of the files that have been added (not inherited) in this commit. */
    private ArrayList<File> filesInCommit = new ArrayList<File>();
    /** Stores the names of all files in this CommitBody. */
    private ArrayList<String> listFilesInCommit = new ArrayList<String>();
    /** Keeps track of all files that were inherited from the previous commits as well as the
      * most recent commit where there was a change in the file (inherited from past pointer). */
    private HashMap<String, ArrayList<Integer>> inheritedFiles
            = new HashMap<String, ArrayList<Integer>>();
    /** Keeps all of the inherited files of the previous version and also adds in all newly added
      * objects. Used to define the inherited files of the next commit. Last element = latest
      * commit. */
    private HashMap<String, ArrayList<Integer>> inheritedPlusAdded
            = new HashMap<String, ArrayList<Integer>>();

    /** Temporarily stores any mappings that are removed from inheritedPlusAdded; incase we need to
      * re-add the object, simply copy the mapping from here to the other map. */
    private HashMap<String, ArrayList<Integer>> temp = new HashMap<String, ArrayList<Integer>>();
    /** Keeps track of all of the history of the branch */
    private ArrayList<Integer> historyNodes = new ArrayList<Integer>();

    /********** CONSTRUCTORS **********/

    /** Most commits will be of this form (unless revert is called) */
    public CommitBody(String msg, CommitBody prev) {
        message = msg;
        past = prev;
        commitTime = new Date();
        if (past != null && past.inheritedPlusAdded != null) {
            this.inheritedFiles = new HashMap<String, ArrayList<Integer>>(past.inheritedPlusAdded);
            inheritedPlusAdded = new HashMap<String, ArrayList<Integer>>(this.inheritedFiles);
            historyNodes = new ArrayList<Integer>(past.historyNodes);
        }
    }

    public CommitBody(String msg) {
        message = msg;
        past = null;
        commitTime = new Date();
        if (past != null && past.inheritedPlusAdded != null) {
            this.inheritedFiles = new HashMap<String, ArrayList<Integer>>(past.inheritedPlusAdded);
            inheritedPlusAdded = new HashMap<String, ArrayList<Integer>>(this.inheritedFiles);
            historyNodes = new ArrayList<Integer>(past.historyNodes);
        }
    }

    /********** METHODS **********/

    /** Adds the current node to history */
    public void addHistory() {
        historyNodes.add(commitID);
    }

    /** Returns the history of the node. */
    public ArrayList<Integer> getHistory(){
        return historyNodes;
    }

    /** Used to get all inherited files (primarily from past commit) */
    public HashMap<String, ArrayList<Integer>> getInherits() {
        return inheritedFiles;
    }

    /** Remove the file from what is inherited by the next commit. */
    public void removeFromInherits(String filename) {
        temp.put(filename, inheritedPlusAdded.get(filename));
        inheritedPlusAdded.remove(filename);
    }

    /** Adds the file to what is inherited by the next commit. */
    public void reInherit(String filename) {
        inheritedPlusAdded.put(filename, temp.get(filename));
        temp.remove(filename);
    }

    public HashMap<String, ArrayList<Integer>> getInheritedPlusAdd() {
        return inheritedPlusAdded;
    }

    /** Used to get all added files (from past commit) */
    public ArrayList<File> getAddedFiles() {
        return filesInCommit;
    }

    /** Used to get all added files (from past commit) */
    public ArrayList<String> getStringAddedFiles() {
        return listFilesInCommit;
    }

    /** Returns the message (so other classes can access it) */
    public String message() {
        return message;
    }

    /** Returns the past CommitBody object (useful for log). */
    public CommitBody getPast() {
        return past;
    }

    /** Establishes the commitID value as idNumber. Only call this method once per commit. */
    public void updateCommitID(int idNumber) {
        commitID = idNumber;
        addHistory();
    }

    /** Returns the ID number for this commit. */
    public int getCommitID() {
        return commitID;
    }

    /** Returns the time when the commit was made. */
    public Date getDate() {
        return commitTime;
    }

    /** Adds the file to the current CommitBody. */
    public void addToCommit(File file) {
        filesInCommit.add(file);
        listFilesInCommit.add(file.getPath());
        if (!inheritedPlusAdded.containsKey(file.getPath())) {
            ArrayList<Integer> idList = new ArrayList<Integer>();
            inheritedPlusAdded.put(file.getPath(), idList);
        }
        inheritedPlusAdded.get(file.getPath()).add(commitID);
    }
}
