public class Main {
    public static void main(String[] args) throws InterruptedException {
        DownloadManager manager = new DownloadManager();
        manager.setListener(file -> {
            System.out.println("Listener: файл " + file.getName() + " загружен");
        });
        manager.addFile(new ImageFile("photo.jpg", 5));
        manager.addFile(new VideoFile("movie.mp4", 50));
        manager.addFile(new DocumentFile("report.pdf", 2));
        manager.addFile(new ImageFile("banner.png", 3));
        manager.addFile(new VideoFile("clip.avi", 30));
        manager.startDownloads();
        Thread.sleep(1500);
        manager.interruptOne();
        manager.waitForAll();
        manager.printResults();
    }
}