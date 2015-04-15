import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Date;
import java.io.File;

public class Gitlet {

    /** After deserialization, returns the latest commit object. (Can make this variable static
      * because it does not make a difference in the long run, due to deserialization process.) */
    private static Commit commitTree;

    public static void main(String[] args) {
        // Deserialize, then mark the commit as the private var

        // initialize();
        // branch("hello");
        // removeBranch("hello");

        String initArg = args[0];
        switch (initArg) {
            case "init":
                initialize();
                break;
            case "add":
                String fileName = args[1];
                // add(fileName);
                break;
            case "commit":
                String message = args[1];
                // commit(message);
                break;
            case "rm":
                String filename = args[1];
                // commit(filename);
                break;
            case "log":
                log();
                break;
            case "global-log":
                globalLog();
                break;
            case "find":
                String commit = args[1];
                find(commit);
                break;
            case "status":
                status();
                break;
            case "checkout":
                // if (args.length == 3) {
                //     checkout(args[2]);
                // } else {
                //     checkout(Integer.parseInt(args[2]), args[3]);
                // }
                break;
            case "branch":
                String branchName = args[1];
                branch(branchName);
                break;
            case "rm-branch":
                String branchname = args[1];
                removeBranch(branchname);
                break;
            case "reset":
                int commitID = Integer.parseInt(args[1]);
                // reset(commitID);
                break;
            case "merge":
                String nameBranch = args[1];
                // merge(nameBranch);
                break;
            case "rebase":
                String nameOfBranch = args[1];
                // rebase(nameOfBranch);
                break;
            case "i-rebase":
                String branchsName = args[1];
                // interactiveRebase(branchsName);
                break;
        }
        /** After main command has been run, serialize the commitTree object before closing */
    }

    /** Creates a new gitlet version control system in the current directory. This
      * system will automatically start with one commit: a commit that contains no
      * files and has the commit message 'initial commit.'
      * 
      * Runtime: Should be constant relative to any significant measure. */
    public static void initialize() {
        if (new File(".gitlet").exists()) {
            String msg = "A gitlet version control system already exists in the current directory.";
            System.out.println(msg);
            return;
        }
        // Initializes the gitlet version control system.
        // 1. Make the .gitlet directory
        File gitlet = new File(".gitlet");
        gitlet.mkdir();
        File newestFiles = new File(".gitlet/latestVersion");
        newestFiles.mkdir();
        File pastFiles = new File(".gitlet/pastVersions");
        pastFiles.mkdir();
        // 2. Blank commit with message 'initial commit'
        commitTree = new Commit();
        commitTree.setDefBranch("master");
        String initCommitMessage = "initial commit";
        CommitBody init = new CommitBody(initCommitMessage);
        init.updateCommitID(commitTree.getLatestCommitID());
        commitTree.addBranch(commitTree.getDefBranch(), init);
        commitTree.addCommit(initCommitMessage, init);
        commitTree.initializeAddRemove(init);
    }

    /** Indicated that you want the file to be included in the upcoming commit as
      * having been changed (also called 'staging' a file). If the file has been 
      * marked for removal, instead just unmark it.
      * 
      * Runtime: In the worst case, should run in linear time relative to the size
      * of the file being added. */
    public static void add(String filename) {
        File addF = new File(filename);
        if (!addF.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        if (/* file has not been modified since last commit */ true) {
            System.out.println("File has not been modified since the last commit.");
            return;
        }
        commitTree.stageFile(filename);
    }

    /** Saves a snapshot of certain files that can be viewed or restored later. Two
      * sources for commits: files newly added to the commit and files inherited
      * from the previous commit.
      * 
      * Runtime: Should be constant with respect to any measure of number of commits. */
    public static void commit(String message) {
        if (/* no files have been staged or marked for removal */ true) {
            System.out.println("No changes added to the commit.");
            return;
        }
        if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        // Set the previous of the new commit node
        CommitBody now = new CommitBody(message, commitTree.getBody(commitTree.getDefBranch()));
        now.updateCommitID(commitTree.getLatestCommitID());
        commitTree.updateCommitFiles(now);
        commitTree.addCommit(message, now);
        // Figure out where to add the commit to the commit node

        // Update the head pointer (branches) to newest value
        commitTree.setBranchToCommit(now);
        commitTree.addCommit(message, now);
        // ADD FILES TO ACTUAl .GITLET FOLDER
        
    }

    /** Mark the file for removal. This means that it will not be inherited as
      * an old file in the next commit. If the file had been staged, then unstage it.
      * 
      * Runtime: Should run in constant time relative to any significant measure. */
    public static void remove(String filename) {
        commitTree.removeFile(filename);
    }

    /** Starting at the current head pointer, display the information for each commit
      * backwards along the commit tree until the initial commit (called commit's 
      * history). For every node, it should display the commit id, the time the commit
      * was made, and the commit mesage, with each message seperated with a "====" and
      * an empty line.
      * 
      * Example:
      *
      *     ====
      *     Commit 2.
      *     2015-03-14 11:59:26
      *     A commit message.
      *
      *     ====
      *     Commit 1.
      *     2015-03-14 11:49:29
      *     Another commit message.
      *
      *     ====
      *     Commit 0.
      *     2015-03-14 11:39:26
      *     initial commit
      * 
      * Runtime: Should be linear with respect to the number of nodes in head's history. */
    public static void log() {
        CommitBody head = commitTree.getBody(commitTree.getDefBranch());
        while (head != null) {
            System.out.println("====");
            System.out.println("Commit " + head.getCommitID() + ".");
            SimpleDateFormat dateTime = new SimpleDateFormat("YYYY-MM-dd KK:mm:ss");
            String timeOfCommit = dateTime.format(head.getDate());
            System.out.println(timeOfCommit);
            System.out.println(head.message());
            if (head.getCommitID() != 0) {
               System.out.println();
            }
            head = head.getPast();
        }
    }

    /** Like log, except it displays information about al the commits ever made. The
      * order of the commits does NOT matter.
      * 
      * Runtime: Linear with respect to the number of commits ever made */
    public static void globalLog() {
        ArrayList<CommitBody> completed = new ArrayList<CommitBody>();
        for (String branchName : commitTree.getAllBranches()) {
            CommitBody head = commitTree.getBody(commitTree.getDefBranch());
            while (head != null && !completed.contains(head)) {
                System.out.println("====");
                System.out.println("Commit " + head.getCommitID() + ".");
                SimpleDateFormat dateTime = new SimpleDateFormat("YYYY-MM-dd KK:mm:ss");
                String timeOfCommit = dateTime.format(head.getDate());
                System.out.println(timeOfCommit);
                System.out.println(head.message());
                if (head.getCommitID() != 0) {
                   System.out.println();
                }
                head = head.getPast();
            }
        }
    }

    /** Prints out the id of the commit that has the given commit message. If there are
      * multiple such commits, it prints the ids out on separate lines.
      * 
      * Runtine: Should be linear relative to the number of commits that have the given
      * message. */
    public static void find(String message) {
        HashMap<String, ArrayList<CommitBody>> theCommits = commitTree.getAllCommits();
        if (!theCommits.containsKey(message)) {
            System.out.println("Found no commit with that message.");
            return;
        }
        for (CommitBody cb : theCommits.get(message)) {
            System.out.println(cb.getCommitID());
        }
    }

    /** Displays what branches currently exist, and marks the current branch with a "*".
      * Also displays what files have been stages or marked for removal (in this format)
      * 
      * Example:
      * 
      *     === Branches ===
      *     *master
      *     other-branch
      * 
      *     === Staged Files ===
      *     wug.txt
      *     some_folder/wugs.txt
      * 
      *     === Files Marked for Removal ===
      *     goodbye.txt
      * 
      * Runtime: Make sure this is linear relative to the number of files that have been
      * staged or marked for removal and the number of branches that exist. */
    public static void status() {
        System.out.println("=== Branches ===");
        for (String branchName : commitTree.getAllBranches()) {
            if (branchName == commitTree.getDefBranch()) {
                System.out.print("*");
            }
            System.out.println(branchName);
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (String filename : commitTree.getAddFiles()) {
            System.out.println(filename);
        }
        System.out.println();
        System.out.println("=== Files Marked for Removal ===");
        for (String filename : commitTree.getRemoveFiles()) {
            System.out.println(filename);
        }
    }

    /** Restores the given file in the working directory to its state at the commit at
      * the head of the current branch. 
      * 
      * Runtime: Should be linear to the size of the file being checked out. */
    public static void checkout(String filename) {
        if (/*allBranches.contains(filename)*/ true) {
            checkoutBranch(filename);
            return;
        }
        if (/** file does not exist in the previous commit */ true) {
            String m = "File does not exist in the most recent commit, or no such branch exists.";
            System.out.println(m);
            return;
        }
        /** DO THIS - Restore given file in the working directory to its state at the commit at
          * the head of the current branch */
    }

    /** Restores the given file in the working directory to its state at the given
      * commit. 
      * 
      * Runtime: Should be linear to the size of the file being checked out. */
    public static void checkout(int commitID, String filename) {
        if (commitID >= commitTree.getID()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        if (/** the file does not exist in the given commit */ true) {
            System.out.println("File does not exist in that commit.");
            return;
        }
    }

    /** Restores all files in the working directory to their versions in the commit
      * at the head of the given branch. Considers the given branch to now be the 
      * current branch. 
      * 
      * Runtime: Should be linear with respect to the total size of the files in the
      * commit's snapshot. Should be constant with respect to any measure involving
      * number of commits. Should be constant with respect to the number of branches. */
    public static void checkoutBranch(String branchName) {
        if (/** no branch with that name exists */ 1 == 2) {
            String m = "File does not exist in the most recent commit, or no such branch exists.";
            System.out.println(m);
        } else if (/** that branch is the current branch */ true) {
            System.out.println("No need to checkout the current branch.");
        }
        /** DO THIS - Restore all files in the working directory to their states at the commit at
          * the head of the given branch. Make given branch the current branch. */
    }

    /** Creates a new branch with the given name. (A branch is nothing more than
      * the name of the head pointer in the commit graph). Before you ever call branch, 
      * your code should be running with a default branch called 'master.'
      * Note: Does NOT immediately switch to the newly created branch.
      * 
      * Runtime: Should be constant relative to any significant measure. */
    public static void branch(String branchName) {
        if (commitTree.getAllBranches().contains(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        CommitBody pointer = commitTree.getBody(commitTree.getDefBranch());
        commitTree.addBranch(branchName, pointer);
    }

    /** Deletes the branch with the given name. This only means to delete the head
      * pointer associated with the branch; it does not eman to delete all the commits
      * that were created under the branch, or anything like that.
      * 
      * Runtime: Should be constant relative to any significant measure. */
    public static void removeBranch(String branchName) {
        if (!commitTree.getAllBranches().contains(branchName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        } else if (commitTree.getDefBranch().equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        commitTree.remBranch(branchName);
    }

    /** Restores all files to their versions in the commit with the given id. Also
      * moves the current branch's head to that commit node.
      * 
      * Runtime: Should be linear with respect to the total size of files in the given
      * commit's snapshot. Should be constant with respect to any measure involving
      * number of commits. */
    public static void reset(int commitID) {
        if (/** no commits with the given id exist */ true) {
            System.out.println("No commit with that id exists.");
        }
    }

    /** Merges files from the head of the given brnch into the head of the current
      * branch. This method is a bit complicated.
      * 
      * Runtime: Should be linear in terms of the lenths of the history of each branch.
      * Should also be linear in terms of the total size of new files added to commits
      * in each branch. */
    public static void merge(String branchName) {
        if (/** branch with the given name does not exist */ 1 == 5) {
            System.out.println("A branch with that name does not exist.");
        } else if (/** attempting to merge a branch with itself */ true) {
            System.out.println("Cannot merge a branch with itself.");
        }
    }

    /** Conceptually, finds the split point of the current branch and the given branch,
      * then snaps off the current branch at this point and reattaches the current
      * branch to the head of the given branch.
      * Special case: If the current branch is in the history of the given branch, then
      * just more the current branch to point to the same commit that the given branch
      * points to. No commits are replayed in this case.
      * 
      * Runtime: Should be linear relative to the history of the current branch and the
      * given branch. Should also be linear in terms of the number of files added to
      * both branches. Should also be linear relative to the total size fo files added
      * to the given branch. Also, rebase should not need to make any additional backup
      * copies of files. */
    public static void rebase(String branchName) {
        if (/** branch with given name does not exist */ 1 == 6) {
            System.out.println("A branch with that name does not exist.");
        } else if (/* given branch is same as the current branch name */ 2 == 4) {
            System.out.println("Cannot rebase a branch onto itself.");
        } else if (/* input branch's head is in teh history of the current branch's head */ true) {
            System.out.println("Already up-to-date.");
        }
    }

    /** Same as rebase, but with a twist. For each node it replays, it allows the user
      * to change the commit message or skip replaying the commit. This means the
      * command needs to pause and prompt the user for text input before continuing. It
      * should also print out info about the commit like log does. Finally, follow:
      * 
      * Example:
      * 
      *     Currently replaying:
      *     ....
      *     Would you like to (c)ontinue, (s)kip the commit, or change the commit's
      *     (m)essage?
      * 
      * It acts accordingly and then ends out.
      * 
      * Runtime: Should be no different than rebase along any significant measure. */
    public static void interactiveRebase(String branchName) {
        if (/** branch with given name does not exist */ 1 == 6) {
            System.out.println("A branch with that name does not exist.");
        } else if (/* given branch is same as the current branch name */ 2 == 4) {
            System.out.println("Cannot rebase a branch onto itself.");
        } else if (/* input branch's head is in teh history of the current branch's head */ true) {
            System.out.println("Already up-to-date.");
        }
    }
}
