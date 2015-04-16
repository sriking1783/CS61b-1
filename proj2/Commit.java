import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Collections;
import java.io.Serializable;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.io.File;

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
    /** Keeps track of all of the files that have been added (not inherited) to each commit. If
      * a new commit is made, add its commitID as the integer and the appropriate files. */
    private HashMap<Integer, ArrayList<File>> filesByCommit;
    /** Stores the latest versions of each file across all commits. Any time that a file that was
      * committed before added, remove the previous version and add the latest one. Also update its
      * commitID as the value, so we know what the latest commit version is. */
    private HashMap<String, Integer> latestVersions;
    /** Keeps track of all CommitBody objects, with their IDs as the keys. */
    private TreeMap<Integer, CommitBody> eachCommit;
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

    /********** CONSTRUCTOR **********/

    public Commit() {
        branches = new HashMap<String, CommitBody>();
        allBranches = new HashSet<String>();
        allCommits = new HashMap<String, ArrayList<CommitBody>>();
        commitIDCounter = 0;
        filesByCommit = new HashMap<Integer, ArrayList<File>>();
        latestVersions = new HashMap<String, Integer>();
        eachCommit = new TreeMap<Integer, CommitBody>(Collections.reverseOrder());
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

    /** Returns the commitID of the latest updated version of the file. */
    public int getLatestVersion(String filename) {
        if (latestVersions.containsKey(filename)) {
            return latestVersions.get(filename);
        }
        return -1;
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

    /** Returns the hashmap that goes from all commitIDs to their respective commits. */
    public TreeMap<Integer, CommitBody> getEachCommit() {
        return eachCommit;
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
        eachCommit.put(addedCommit.getCommitID(), addedCommit);
    }

    /** Does the core work of the commit method in Gitlet. When we decided to make a new commit
      * and instantiate a version of the CommitBody object to add to our commit tree, create a
      * File class version to save each file and add it to the list of files that belong in this
      * commit node.  */
    public void updateCommitFiles(CommitBody newCommit, File directory) {
        for (String name : addFiles) {
            File file = new File(name);
            if (file.exists()) {
                newCommit.addToCommit(file);
                if (latestVersions.containsKey(name)) {
                    latestVersions.remove(name);
                }
                latestVersions.put(name, newCommit.getCommitID());
                // Move files to Commit_#id file
                String corePath = ".gitlet/commit_" + newCommit.getCommitID() + "/";
                String thePathname = corePath + file.getName();
                String actualFile = makeApprDirs(corePath, name);
                File newFile = new File(actualFile);
                try {
                    Files.copy(file.toPath(), newFile.toPath());
                } catch (IOException m) {
                    m.printStackTrace();
                }
            }
        }
        filesByCommit.put(newCommit.getCommitID(), newCommit.getAddedFiles());
        addFiles = new HashSet<String>();
        removeFiles = new HashSet<String>();
    }

    private String makeApprDirs(String core, String pathname) {
        String[] splitName = pathname.split("/");
        String path = core;
        for (int counts = 0; counts < splitName.length - 1; counts += 1) {
            path = path + splitName[counts] + "/";
            File dir = new File(path);
            dir.mkdir();
        }
        return path + splitName[splitName.length - 1];
    }

    /** Decides if a file can be added, and adds it if appropriate */
    public void stageFile(String filename, CommitBody back) {
        if (removeFiles.contains(filename)) {
            removeFiles.remove(filename);
            back.reInherit(filename);
            return;
        }
        addFiles.add(filename);
    }

    /* Decides if a file needs to be removed, and removes it if appropriate */
    public void removeFile(String filename) {
        if (!addFiles.contains(filename)
            && !branches.get(currBranch).getInherits().containsKey(filename)) {
            System.out.println("No reason to remove the file.");
            return;
        }
        if (addFiles.contains(filename)) {
            addFiles.remove(filename);
        } else {
            branches.get(currBranch).removeFromInherits(filename);
        }
        removeFiles.add(filename);
    }

    /********** SERIALIZATION **********/

    /** Responsible for writing the state of the object for its particular class so that
      * readObject() can restore it. Call out.defaultWriteObject to save the Object's field. */
    public static void serialize(Commit myCommit) {
        if (myCommit == null) {
            return;
        }
        try {
            File myCommitFile = new File(".gitlet/myCommit.ser");
            FileOutputStream fileOut = new FileOutputStream(myCommitFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(myCommit);
        } catch (IOException e) {
            String msg = "IOException while saving myCommit.";
            System.out.println(msg);
        }
    }

    /** Responsible for reading from the stream and restoring the class fields. May call 
      * in.defaultReadObject to restore te object's non-static and non-transient fields.
      * Uses info in the steam to asign fields to the correspondingly named fields in the
      * current object. */ 
    public static Commit deserialize() {
        Commit myCommit = null;
        File myCommitFile = new File(".gitlet/myCommit.ser");
        if (myCommitFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(myCommitFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                myCommit = (Commit) objectIn.readObject();
            } catch (IOException e) {
                String msg = "IOException while loading myCommit.";
                System.out.println(msg);
            } catch (ClassNotFoundException e) {
                String msg = "ClassNotFoundException while loading myCommit.";
                System.out.println(msg);
            }
        }
        return myCommit;
    }
}
