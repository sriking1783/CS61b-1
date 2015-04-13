import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

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

    /********** CONSTRUCTORS **********/

    public CommitBody(CommitBody prev, ArrayList<CommitBody> next) {
        past = prev;
        future = next;
        commitTime = new Date();
    }

    /** Most commits will be of this form (unless revert is called) */
    public CommitBody(CommitBody prev) {
        past = prev;
        future = null;
        commitTime = new Date();
    }

    public CommitBody(String msg) {
        message = msg;
        past = null;
        future = null;
        commitTime = new Date();
    }

    /** Only really useful for the initial commit */
    public CommitBody() {
        past = null;
        future = null;
        commitTime = new Date();
    }

    /********** METHODS **********/

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

}