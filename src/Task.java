public abstract class Task {
    protected int id;
    protected String name;
    protected String description;
    protected String status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
