class Candidate {
    private String name;
    private String post;
    private String party;
    private int votes;

    public Candidate(String name, String post, String party,int votes) {
        this.name = name;
        this.post = post;
        this.party = party;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public String getParty() {
        return party;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        votes++;
    }
}

