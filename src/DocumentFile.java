public class DocumentFile implements Downloadable {
    private String name;
    private int size;
    public DocumentFile(String name, int size) {
        this.name = name;
        this.size = size;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public void download() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " загружает " + name);
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " завершил загрузку " + name);
    }
}