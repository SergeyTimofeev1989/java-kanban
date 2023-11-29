public class SubTask extends Task {
    protected int epicId;

    public SubTask(String name, String description, String status) {
        super(name, description);
        this.status = status;
    }
}
