import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CMC {
    /**
     *
     * @param o : trajectories
     * @param m : : a minimum number of a convoy's members
     * @param k : a minimum number of sequential time
     * @param e : threshold bound of a cluster
     * @return : a set of convoys
     * @throws Exception
     */
    public static List<Convoy> cm_clustering(List<Trajectory> o, int m, int k, double e) throws Exception {
        ArrayList<Convoy> V = new ArrayList<>();
        ArrayList<Convoy> vResult = new ArrayList<>();

        for(int t = 1; t <=25; t++) {
            ArrayList<Convoy> vNext = new ArrayList<>();
            ArrayList<STPoint> oT = new ArrayList<>();

            // get points of current time
            for(Trajectory currentT : o) {
                if(currentT.getPointAt(t)!= null)
                    oT.add(currentT.getPointAt(t));
            }

            // pruning : if a number of objects on current time is less than m,
            // skip this iteration
            if(oT.size() < m) {
                continue;
            }

            // DBSCAN : make clusters of current time
            Cluster[] clusters = DBSCAN.dbscan_to_cluster(oT, e, m);
            ArrayList<Cluster> C = new ArrayList<Cluster>(Arrays.asList(clusters)); // for convenience

            for(Convoy v : V) {
                v.setAssigned(false);
                Iterator<Cluster> it_c = C.iterator();
                while(it_c.hasNext()) {
                    Cluster c = it_c.next();
                    int numberOfIntersection = v.getNumberOfIntersection(c);
                    if(numberOfIntersection >= m) {
                        v.setAssigned(true);
                        v.doIntersection(c);
                        v.setEndTime(t); // this method include setLifeTime();
                        vNext.add(v);
                        it_c.remove(); // current cluster has been assigned.
                    }
                }
                if(v.isAssigned() == false && v.getLifetime() >= k) {
                    vResult.add(v);
                }
            }

            // do not check whether the cluster is assgined or not
            // in previous step, I remove assgined cluster already
            for(Cluster c : C) {
                Convoy unassignedConvoy = new Convoy(c);
                unassignedConvoy.setStartTime(t);
                unassignedConvoy.setEndTime(t);
                vNext.add(unassignedConvoy);
            }
            V = vNext;
        }



        return vResult;
    }

}

