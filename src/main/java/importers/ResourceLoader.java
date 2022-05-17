package importers;

public abstract class ResourceLoader {
    private String path;

    public ResourceLoader(String path){
        this.path = path;
        loadData();
    }

    public abstract void loadData();

    public abstract void readData();

    public abstract void writeData();
}
