import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Convoy extends Obj_List{

    boolean assigned;
    double startTime, endTime;
    int lifetime;
    public Convoy( boolean assigned, int lifetime) {
        this.assigned = assigned;
        this.lifetime = lifetime;
    }

    public Convoy() {
        this(false, 1);
    }


    public Convoy(Cluster c) {
        this();
        super.addAll(c);

    }

    public boolean isAssigned() {return assigned;}
    public void setAssigned(boolean assigned) {this.assigned = assigned;}
    public double getStartTime() { return startTime; }
    public void setStartTime(double startTime) {this.startTime = startTime; }
    public double getEndTime() { return endTime; }
    public void setEndTime(double endTime) {
        this.endTime = endTime;
        this.setLifetime((int)(endTime - startTime) + 1);
    }
    public int getLifetime() { return lifetime; }
    public void setLifetime(int lifetime) { this.lifetime = lifetime; }
    public int getNumberOfIntersection(Cluster c) {
        int numberOfIntersection = 0;
        // do object by object of this convoy
        for(int convoyObject : this) {
            // If there is a duplicated object in cluster, add 1.
            if(c.contains(convoyObject)) {
                numberOfIntersection++;
            }
        }
        return numberOfIntersection;
    }
    public void doIntersection(Cluster c) {
        // do object by object of this convoy
        Iterator<Integer> it_convoyObject = this.iterator();
        while(it_convoyObject.hasNext()) {
            int convoyObject = it_convoyObject.next();
            // If there is a duplicated object in cluster, do nothing
            if(c.contains(convoyObject)) {
                continue;
            } else { // If there is no duplicated object in cluster, remove it
                it_convoyObject.remove();
            }
        }
    }

    @Override
    public String toString() {

        String res = "Start_time: " + getStartTime() + ",\tEnd_Time: " + getEndTime() +"\t(" + getLifetime()+")\nobj_list: ";

        Collections.sort(this);

        res+= this.get(0);

        for (int i = 1; i < this.size(); i++) {
            Integer oid = this.get(i);
            res += ", "+ oid;
        }

        res+='\n';

        return res;
    }
}
