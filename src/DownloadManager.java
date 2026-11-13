import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
public class DownloadManager {
    private List<Downloadable> files;
    private List<Downloadable> downloaded;
    private DownloadListener listener;
    private Semaphore semaphore;
    private List<Thread> threads;
    public DownloadManager() {
        this.files = new ArrayList<>();
        this.downloaded = Collections.synchronizedList(new ArrayList<>());
        this.threads = new ArrayList<>();
        this.semaphore = new Semaphore(3);
    }
    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }
    public void addFile(Downloadable file) {
        files.add(file);
    }
    public void startDownloads() {
        for (Downloadable file : files) {
            Thread thread = new Thread(() -> {
                try {
                    semaphore.acquire();
                    file.download();
                    downloaded.add(file);
                    if (listener != null) {
                        listener.onDownloadComplete(file);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " прерван при загрузке " + file.getName());
                    semaphore.release();
                }
            });
            thread.start();
            threads.add(thread);
        }
    }
    public void interruptOne() {
        if (!threads.isEmpty()) {
            threads.get(0).interrupt();
            System.out.println("Прервана загрузка- " + threads.get(0).getName());
        }
    }
    public void waitForAll() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }
    public void printResults() {
        System.out.println("З агруженные файлы");
        int totalSize = 0;
        for (Downloadable file : downloaded) {
            System.out.println(file.getName() + " — " + file.getSize() + " MB");
            totalSize += file.getSize();
        }
        System.out.println("Общий размер- " + totalSize + " MB");
    }
}