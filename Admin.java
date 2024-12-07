class Admin {
    private boolean votingOpen;

    public Admin() {
        votingOpen = false;
    }

    public void startVoting() {
        votingOpen = true;
        System.out.println("Voting started.");
    }

    public void stopVoting() {
        votingOpen = false;
        System.out.println("Voting stopped.");
    }

    public boolean isVotingOpen() {
        return votingOpen;
    }

    public void declareWinner(Candidate[] candidates) {
        System.out.println("The results are as follows:");
        Candidate winner = null;
        int maxVotes = 0;

        for (Candidate candidate : candidates) {
            System.out.println(candidate.getName() + " (" + candidate.getPost() + ", " + candidate.getParty() + "): " + candidate.getVotes() + " votes");
            if (candidate.getVotes() > maxVotes) {
                maxVotes = candidate.getVotes();
                winner = candidate;
            }
        }

        if (winner != null) {
            System.out.println("The winner is " + winner.getName() + " (" + winner.getPost() + ", " + winner.getParty() + ") with " + winner.getVotes() + " votes.");
        } else {
            System.out.println("No winner found.");
        }
    }
}
