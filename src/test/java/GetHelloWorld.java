import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jun
 * @date 2020-11-01 20:53
 * @description
 *
 * 创建10000个文件中有1000行，找到其中包含hello+world的这行，统计这行和文件名
 */
public class GetHelloWorld {

    public static void main(String[] args) throws Exception {
//        createFile();
        getHelloWorldNumber();
    }

    public static void getHelloWorldNumber() throws Exception {
        String filepath = System.getProperty("user.dir");
        File file = new File(filepath + "/data");
        if (!file.exists() || file.isFile()) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        long start = System.currentTimeMillis();
        System.out.println(start);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10000));
        File[] subFile = file.listFiles();
        List<Future<Map<String, Long>>> arrayList = new ArrayList<>();
        for (File f : subFile) {
            Future<Map<String, Long>> future = threadPoolExecutor.submit(new MyCallableProducer(f));
            arrayList.add(future);
            countDownLatch.countDown();

        }
        countDownLatch.await();
        File outputFile = new File(file + "/out");
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true));
        for (Future<Map<String, Long>> m : arrayList) {
            Map<String, Long> map = m.get();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
                bw.flush();
            }
        }
        bw.close();
        System.out.println(System.currentTimeMillis() - start + "ms");
        threadPoolExecutor.shutdown();
    }

    public static long readContents(File file) {
        StringBuilder res = new StringBuilder();
        BufferedReader br = null;
        int length = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                if (br.readLine().indexOf("hello+world") > 0) {
                    length++;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return length;
    }


    /**
     * 创建文件
     *
     * @throws InterruptedException
     */
    private static void createFile() throws InterruptedException {
        String filepath = System.getProperty("user.dir");
        File file = new File(filepath + "/data");
        if (file.exists()) {
            return;
        }
        file.mkdirs();
        System.out.println(file);
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        Thread thread = new Thread(() -> {
            Random random = new Random();
            try {
                createFolder(file + "/" + atomicInteger.incrementAndGet() + ".txt", random.toString());
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long start = System.currentTimeMillis();
        System.out.println(start);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10000));
        for (int i = 0; i < 10000; i++) {
            threadPoolExecutor.submit(thread);
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - start + "ms");
        threadPoolExecutor.shutdown();
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @param s
     * @throws IOException
     */
    public static void createFolder(String path, String s) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < 10000; i++) {
            bufferedWriter.newLine();
            if (i % 4 == 1) {
                bufferedWriter.write(s);
            } else {
                bufferedWriter.write("hello+world" + s);
            }
        }
        bufferedWriter.close();
    }

    // 文件读线程
    static class MyCallableProducer implements Callable<Map<String, Long>> {
        private File file;

        public MyCallableProducer(File file) {
            this.file = file;
        }


        @Override
        public Map<String, Long> call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 线程开始读取文件 ：" + file.getName() + " ,时间为 " + startTime);
            Map<String, Long> stringLongMap = readContents(file);
            System.out.println(Thread.currentThread().getName() + " 线程读取文件 ：" + file.getName() + " 完毕" + " ,时间为 " + (System.currentTimeMillis() - startTime) + "ms");
            return stringLongMap;
        }

        public static Map<String, Long> readContents(File file) {
            StringBuilder res = new StringBuilder();
            BufferedReader br = null;
            Map<String, Long> map = new HashMap<>();
            long length = 0;

            try {
                br = new BufferedReader(new FileReader(file));
                while (br.ready()) {
                    if (br.readLine().contains("hello+world")) {
                        length++;
                        map.put(file.getName(), length);
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return map;
        }
    }

    public static void saveFiles(String contents, String output) {
        File outputFile = new File(output);
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true));
            bw.write(contents);

            bw.flush();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("保存成功！！！");
    }
}
