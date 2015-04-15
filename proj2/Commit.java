import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Date;
import java.io.File;
import java.io.*;

public class Commit implements Serializable {

    /** Determines if a de-serialized file is compatible with this class. */
    private static final long serialVersionUID = 123546987L;
    /** Keeps track of which commit message is the most recent one for each branch. Normally
      * stays at the front of the commit structure, but will be pushed back when the commits
      * are 'reverted.' */
    private HashMap<String, CommitBody> branches;
    /** Using a given message, we can find every CommitBody that has that same message. Used to
      * help keep track of all of the commits that exist. */
    private HashMap<String, ArrayList<CommitBody>> allCommits;

    /******** INCORPORATE (IF NECESSARY) ********/

    /** Keeps track of all of the files that have been added (not inherited) to each commit. If
      * a new commit is made, add its commitID as the integer and the appropriate files. */
    private HashMap<Integer, ArrayList<File>> filesByCommit;
    /** Keeps track of all the files that have ever been inherited or committed. */
    private ArrayList<File> allInheritedFiles;
    /** Stores the latest versions of each file across all commits. Any time that a file that was
      * committed before added, remove the previous version and add the latest one. Also update its
      * commitID as the value, so we know what the latest commit version is. */
    private HashMap<String, Integer> latestVersions;

    /******** INCORPORATE (IF NECESSARY) ********/

    /** Keeps track of the current branch that we are checked out into. Whenever checkout
      * is called, change this variable and the value will change for all "initializations"
      * of Gitlet. Useful for making methods in the Commit class much easier. */
    private String currBranch;
    /** Keeps track of all of the branches that are added to branches */
    private HashSet<String> allBranches;
    /** Keeps track of all files that are staged to be committed. */
    private HashSet<String> addFiles;
    /** Keeps track of all files mark for removal. */
    private HashSet<String> removeFiles;
    /** Keeps track of the commitID sequence. Each time a new CommitBody object is made, update
      * this value. Reason for making it in this class is because the value will be different
      * for each CommitBody object. */
    private int commitIDCounter;

    public Commit() {
        branches = new HashMap<String, CommitBody>();
        allBranches = new HashSet<String>();
        allCommits = new HashMap<String, ArrayList<CommitBody>>();
        commitIDCounter = 0;
        filesByCommit = new HashMap<Integer, ArrayList<File>>();
        allInheritedFiles = new ArrayList<File>();
        latestVersions = new HashMap<String, Integer>();
    }

    /********** METHODS **********/

    /** Returns the current value for commitIDCounter and adds one to the variable */
    public int getLatestCommitID() {
        int temp = commitIDCounter;
        commitIDCounter += 1;
        return temp;
    }

    /** Returns the commitIDCounter without updating it's value. */
    public int getID() {
        return commitIDCounter;
    }

    /** Returns the CommitBody that the head pointer points to. */
    public CommitBody getBody(String head) {
        return this.branches.get(head);
    }

    /** Returns the current branch being used */
    public String getDefBranch() {
        return currBranch;
    }

    /** Returns the hashmap that goes from all messages to all commits with that message. */
    public HashMap<String, ArrayList<CommitBody>> getAllCommits() {
        return allCommits;
    }

    /** Returns the set of added files. */
    public HashSet<String> getAddFiles() {
        return addFiles;
    }

    /** Returns the set of to-be removed files. */
    public HashSet<String> getRemoveFiles() {
        return removeFiles;
    }

    /** Returns the set of all created branches */
    public HashSet<String> getAllBranches() {
        return allBranches;
    }

    /** Sets addFiles and removeFiles to initial values. Made it a separate method becayse I was
      * unsure of whether or not I would need it later */
    public void initializeAddRemove(CommitBody first) {
        addFiles = new HashSet<String>();
        removeFiles = new HashSet<String>();
    }

    /** Adds a CommitBody object to the HashMap branches. (i.e. Creates a new branch.) */
    public void addBranch(String branchName, CommitBody addedCommit) {
        branches.put(branchName, addedCommit);
        allBranches.add(branchName);
    }

    /** Removes a CommitBody object to the HashMap branches. (i.e. Deletes the pointer.) */
    public void remBranch(String branchName) {
        branches.remove(branchName);
        allBranches.remove(branchName);
    }

    /** Sets the current branch to a certain branch (HINT: Useful for checkout) */
    public void setDefBranch(String branchName) {
        currBranch = branchName;
    }

    /** Sets the current branch to the newest commit object */
    public void setBranchToCommit(CommitBody newest) {
        branches.put(currBranch, newest);
    }

    /** Adds a commit to the list of all commits */
    public void addCommit(String commitMsg, CommitBody addedCommit) {
        if (!allCommits.containsKey(commitMsg)) {
            ArrayList<CommitBody> commonMessage = new ArrayList<CommitBody>();
            allCommits.put(commitMsg, commonMessage);
        }
        allCommits.get(commitMsg).add(addedCommit);
    }

    /** Does the core work of the commit method in Gitlet. When we decided to make a new commit
      * and instantiate a version of the CommitBody object to add to our commit tree, create a
      * File class version to save each file and add it to the list of files that belong in this
      * commit node.  */
    public void updateCommitFiles(CommitBody newCommit) {
        ArrayList<File> files = new ArrayList<File>();
        for (String name : addFiles) {
            File file = new File(name);
            if (file.exists() && file.isFile()) {
                /***** FIX THIS!!!!!!!! *****/
                /***** FIX THIS!!!!!!!! *****/
                /***** FIX THIS!!!!!!!! *****/
                /***** FIX THIS!!!!!!!! *****/
                /***** FIX THIS!!!!!!!! *****/
                /***** FIX THIS!!!!!!!! *****/
                newCommit.addToCommit(file);
                files.add(file);
                if (latestVersions.containsKey(name)) {
                    latestVersions.remove(name);
                }
                latestVersions.put(name, newCommit.getCommitID());
            }
        }
        filesByCommit.put(newCommit.getCommitID(), files);
    }

    /** Decides if a file can be added, and adds it if appropriate */
    public void stageFile(String filename) {
        if (removeFiles.contains(filename)) {
            removeFiles.remove(filename);
            return;
        }
        addFiles.add(filename);
    }

    /* Decides if a file needs to be removed, and removes it if appropriate */
    public void removeFile(String filename) {
        if (!addFiles.contains(filename)) {
            System.out.println("No reason to remove the file.");
            return;
        }
        if (addFiles.contains(filename)) {
            //FIll tHIS INN!!!!!!!
        }
    }

    /********** SERIALIZATION **********/

    /** Responsible for writing the state of the object for its particular class so that
	  * readObject() can restore it. Call out.defaultWriteObject to save the Object's field. */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /** Responsible for reading from the stream and restoring the class fields. May call 
	  * in.defaultReadObject to restore te object's non-static and non-transient fields.
	  * Uses info in the steam to asign fields to the correspondingly named fields in the
	  * current object. */ 
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    // * Responsible for initializin the state of the object for a particular class if
    //   * serializaion stream does not list the given class as a superclass of a desterialized
    //   * object. 
    // private void readObjectNoData() throws ObjectOutputStream {

    // }

    /** READD THIS IF NECESSARY
    public static void main(String[] args) {
        ArrayList<String> a1 = new ArrayList<String>();
        a1.add("Hello");
        a1.add("Hi");
        a1.add("Howdy");
        try {
            FileOutputStream outFiles = new FileOutputStream("/.gitlet/commits.ser");
            ObjectOutputStream out = new ObjectOutputStream(outFiles);
            out.writeObject(a1);
            out.close();
            outFiles.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> a2 = new ArrayList<String>();
        try {
            FileInputStream f2 = new FileInputStream("myfile.ser");
            ObjectInputStream o2 = new ObjectInputStream(f2);
            a2 = (ArrayList) o2.readObject();
            o2.close();
            System.out.println("One: " + a2.get(0) + ", Two: " + a2.get(1) + ", Three: " + a2.get(2));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }
    */
}