import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TrajectoryParser {

    File inputFile;
    BufferedReader reader;

    public TrajectoryParser(String path) throws IOException {

        inputFile = new File(path);
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Cannot find File.");
        }

    }

    public List<Trajectory> get_traj_set() {
        ArrayList<Trajectory> trajectories = new ArrayList<>();
        String line;

        try {
            reader.readLine(); // ignore the first line of the input file

            // I do it by using a index of moving objects
            // initialize trajectory to 1
            int trajectoryIndex = 1;
            Trajectory currentTrajectory = new Trajectory(1);

            // read the file which is named data.csv line by line
            while((line=reader.readLine()) != null) {
                String[] tokens = line.split(", ");

                // one line to one STPoint
                int currentOid = Integer.parseInt(tokens[0]);
                double currentT = Double.parseDouble(tokens[1]);
                int currentX = Integer.parseInt(tokens[2]);
                int currentY = Integer.parseInt(tokens[3]);
                STPoint currentSTPoint = new STPoint(currentOid, currentT, currentX, currentY);

                // if another object is found, create new Trajectory and add to trajectories
                if(trajectoryIndex < currentOid) {
                    trajectories.add(currentTrajectory);
                    trajectoryIndex = currentOid;
                    currentTrajectory = new Trajectory(currentOid);
                    currentTrajectory.getPoints().add(currentSTPoint);
                } else { // if another point of the current object is found, just add the point
                    currentTrajectory.getPoints().add(currentSTPoint);
                }
            }
            trajectories.add(currentTrajectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trajectories;
    }
}