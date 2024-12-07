java.sql.*;

class Voter {
	    public int voterId;
	    public String name;
	    public String cnic;
	    public String gender;
	    public String address;
	    public boolean hasVoted;

	    public Voter(int voterId, String name, String cnic, String gender, String address) {
	        this.voterId = voterId;
	        this.name = name;
	        this.cnic = cnic;
	        this.gender = gender;
	        this.address = address;
	        this.hasVoted = false;
	    }

	    // Getters for the additional attributes

	    public String getName() {
	        return name;
	    }

	    public String getCnic() {
	        return cnic;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public String getAddress() {
	        return address;
	    }
    public void castVote(Candidate candidate) {
        if (!hasVoted) {
            candidate.incrementVotes();
            hasVoted = true;
            System.out.println("Vote cast by voter " + voterId + " for " + candidate.getName() + " (" + candidate.getPost() + ")");
        } else {
            System.out.println("Voter " + voterId + " has already cast their vote.");
        }
    }

    public boolean hasVoted(Connection connection) {
        String query = "SELECT HasVoted FROM Voters WHERE VoterId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, voterId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean hasVoted = resultSet.getBoolean("HasVoted");
                resultSet.close();
                return hasVoted;
            } else {
                resultSet.close();
                System.out.println("Voter record not found in the database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking voter's voting status: " + e.getMessage());
            return false;
        }
    }

    public int getVoterId() {
        return voterId;
    }
}
