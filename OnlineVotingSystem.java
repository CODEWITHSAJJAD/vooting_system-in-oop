import java.sql.*;
import java.util.Scanner;

public class OnlineVotingSystem {
    public static void main(String[] args) {
        Admin admin = new Admin();
        Candidate[] candidates = new Candidate[4];

        // Establishing database connection
        String url = "jdbc:sqlserver://SUQOON\\SUQOON;databaseName=onlinevotingsystem;user=sa;password=123456;encrypt=false;";
        String username = "sa";
        String password = "123456";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established.");

            // Retrieve candidate information from the database
            retrieveCandidatesFromDatabase(connection, candidates);

            // Start voting
            admin.startVoting();

            // Simulate voting
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the online voting system!");

            while (admin.isVotingOpen()) {
                System.out.println("Enter your voter ID or 'end' to finish voting:");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("end")) {
                    break;
                }

                int voterId;
                try {
                    voterId = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid voter ID.");
                    continue;
                }

                System.out.println("Enter your name:");
                String name = scanner.nextLine();

                System.out.println("Enter your CNIC:");
                String cnic = scanner.nextLine();

                System.out.println("Enter your gender:");
                String gender = scanner.nextLine();

                System.out.println("Enter your address:");
                String address = scanner.nextLine();

                Voter voter = new Voter(voterId, name, cnic, gender, address);

                if (voter.hasVoted(connection)) {
				    System.out.println("You have already cast your vote.");
				    continue;
				}

                System.out.println("Enter the candidate name:");
                String candidateName = scanner.nextLine();

                System.out.println("Enter the candidate post:");
                String candidatePost = scanner.nextLine();

                Candidate selectedCandidate = findCandidate(candidates, candidateName, candidatePost);
                if (selectedCandidate != null) {
                    voter.castVote(selectedCandidate);
                    saveVoterInDatabase(connection, voter);
                    updateVotesInDatabase(connection, selectedCandidate);
                } else {
                    System.out.println("Candidate " + candidateName + " (" + candidatePost + ") not found.");
                }
            }

            // Stop voting
            admin.stopVoting();

            // Declare winner
            admin.declareWinner(candidates);

            scanner.close();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.out.println("Failed to close database connection: " + e.getMessage());
                }
            }
        }
    }

    public static void retrieveCandidatesFromDatabase(Connection connection, Candidate[] candidates) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Candidates";
        ResultSet resultSet = statement.executeQuery(query);

        int index = 0;
        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            String post = resultSet.getString("Post");
            String party = resultSet.getString("Party");
            int votes=resultSet.getInt("votes");
            candidates[index] = new Candidate(name, post, party,votes);
            index++;
        }

        resultSet.close();
        statement.close();
    }

    public static Candidate findCandidate(Candidate[] candidates, String candidateName, String candidatePost) {
        for (Candidate candidate : candidates) {
            if (candidate != null && candidate.getName().equalsIgnoreCase(candidateName) && candidate.getPost().equalsIgnoreCase(candidatePost)) {
                return candidate;
            }
        }
        return null;
    }

    public static void saveVoterInDatabase(Connection connection, Voter voter) throws SQLException {
        String insertQuery = "INSERT INTO Voters (VoterId, Name, CNIC, Gender, Address, HasVoted) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setInt(1, voter.voterId);
        preparedStatement.setString(2, voter.name);
        preparedStatement.setString(3, voter.cnic);
        preparedStatement.setString(4, voter.gender);
        preparedStatement.setString(5, voter.address);
        preparedStatement.setBoolean(6, true); // Assuming the voter has voted
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    public static void updateVotesInDatabase(Connection connection, Candidate candidate) throws SQLException {
        String updateQuery = "UPDATE Candidates SET Votes = ? WHERE Name = ? AND Post = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setInt(1, candidate.getVotes());
        preparedStatement.setString(2, candidate.getName());
        preparedStatement.setString(3, candidate.getPost());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
