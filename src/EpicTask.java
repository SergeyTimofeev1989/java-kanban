import java.util.ArrayList;

public class EpicTask extends Task {
    protected ArrayList<Integer> subtaskIds = new ArrayList<>();

    public EpicTask(String name, String description) {
        super(name, description);
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}