public interface Downloadable {
    String getName();
    int getSize();
    void download() throws InterruptedException;
}