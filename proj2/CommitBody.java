import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Date;
import java.io.File;

/** Thought of this idea after looking at the BSTMap.java file we created in hw6. We use this
  * class to keep track of the connections between each Commit, so that we don't lose
  * the fundamental structure when perform methods such as rebase. */
public class CommitBody implements Serializable {

    /** Used to keep track of previous commit messages */
    private CommitBody past;
    /** Used to keep track of next commit messages (in case head pointer is shifted back).
      * Reason why we use ArrayList, is because there can be more than one future. */
    private ArrayList<CommitBody> future;
    /** Message that represents each commit. */
    private String message;
    /** Keeps track of the commitID for each commit (different for each body, so it should not
      * ever change for each individual commit). */
    private int commitID;
    /** Keeps track of the current time when the commit was made */
    private Date commitTime;

    /** Used to store all of the files that have been added (not inherited) in this commit. */
    private HashMap<File, Integer> filesInCommit = new HashMap<File, Integer>();
    /** Keeps track of all files that were inherited from the previous commits as well as the
      * most recent commit where there was a change in the file (inherited from past pointer). */
    private HashMap<File, Integer> inheritedFiles = new HashMap<File, Integer>();

    /********** CONSTRUCTORS **********/

    public CommitBody(String msg, CommitBody prev, ArrayList<CommitBody> next) {
        message = msg;
        past = prev;
        future = next;
        commitTime = new Date();
        if (past != null && past.getInherits() != null) {
            inheritedFiles = new HashMap<File, Integer>(past.getInherits());
        }
        if (past != null && past.getAddedFiles() != null) {
            inheritedFiles.putAll(past.getAddedFiles());
        }
    }

    /** Most commits will be of this form (unless revert is called) */
    public CommitBody(String msg, CommitBody prev) {
        message = msg;
        past = prev;
        future = null;
        commitTime = new Date();
        if (past != null && past.getInherits() != null) {
            inheritedFiles = new HashMap<File, Integer>(past.getInherits());
        }
        if (past != null && past.getAddedFiles() != null) {
            inheritedFiles.putAll(past.getAddedFiles());
        }
    }

    public CommitBody(String msg) {
        message = msg;
        past = null;
        future = null;
        commitTime = new Date();
        if (past != null && past.inheritedFiles != null) {
            inheritedFiles = new HashMap<File, Integer>(past.getInherits());
        }
        if (past != null && past.filesInCommit != null) {
            inheritedFiles.putAll(past.getAddedFiles());
        }
    }

    /********** METHODS **********/

    /** Used to get all inherited files (primarily from past commit) */
    public HashMap<File, Integer> getInherits() {
        return inheritedFiles;
    }

    /** Used to get all added files (from past commit) */
    public HashMap<File, Integer> getAddedFiles() {
        return filesInCommit;
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
    }

    /** Returns the ID number for this commit. */
    public int getCommitID() {
        return commitID;
    }

    /** Returns the time when the commit was made. */
    public Date getDate(){
        return commitTime;
    }

    /** Adds the file to the current CommitBody. */
    public void addToCommit(File file) {
        filesInCommit.put(file, commitID);
    }
}