import java.io.IOException;
import org.pcj.*;

@RegisterStorage(SharedMemory.Shared.class)
public class SharedMemory implements StartPoint {

    @Storage(SharedMemory.class)
    enum Shared { a }
    long a;

    public static void main(String[] args) throws IOException {
        String nodesFile  = "nodes.txt";
        PCJ.deploy(SharedMemory.class, new NodesDescription("nodes.txt"));
    }

    public void main() throws Throwable {
        if(PCJ.myId()==0){
            PCJ.put(3, 0, Shared.a);
        }
        PCJ.barrier();
        if(PCJ.myId()==1){
            System.out.println((Long)PCJ.get(0,Shared.a));
        }
        PCJ.barrier();
        System.out.println(PCJ.myId()+ " end");
    }
}