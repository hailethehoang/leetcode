Học theo thứ tự:

Thread
↓
Runnable / Callable
↓
ExecutorService
↓
Future
↓
CompletableFuture
↓
Virtual Thread

Sau đó:

synchronized
volatile
AtomicInteger
Lock
ConcurrentHashMap

// RUNNABLE
// 1. Define the task
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task is running in: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        // 2. Create an instance of the task
        MyTask task = new MyTask();
        
        // 3. Pass the task to a Thread and start it
        Thread thread = new Thread(task);
        thread.start(); 
    }
}

2. Lambda Expression (Recommended for Clean Code)Because Runnable is a functional interface, you can completely bypass creating a separate class by using a lambda expression.javapublic class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Lambda thread is running!");
        });
        
        thread.start();
    }
}

Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Anonymous inner class thread is running!");
    }
});
thread.start();


// CALLABLE
Callable<T>

Dùng khi task cần trả kết quả hoặc có thể ném exception.

Callable<Integer> calculate = () -> {
    return 10 + 20;
};

Signature:

T call() throws Exception

Ví dụ: gọi pricing service, tính report, query nhiều nguồn dữ liệu.

Callable<User> getUser = () -> userRepository.findById(1L)
        .orElseThrow();
        
        
Callable<T>

Dùng khi task cần trả kết quả hoặc có thể ném exception.

Callable<Integer> calculate = () -> {
    return 10 + 20;
};

Signature:

T call() throws Exception

Ví dụ: gọi pricing service, tính report, query nhiều nguồn dữ liệu.

Callable<User> getUser = () -> userRepository.findById(1L)
        .orElseThrow();


💻 Code ExampleBecause you cannot pass a Callable directly into a Thread constructor, it is typically executed using an ExecutorService. The service immediately returns a Future object, which acts as a placeholder for the pending result.javaimport java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args) {
        // Create an ExecutorService pool with 1 thread
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Define a Callable task using a lambda expression
        Callable<Integer> task = () -> {
            System.out.println("Processing heavy calculation...");
            Thread.sleep(2000); // Simulate network/DB latency
            return 42; // Return the computational result
        };

        try {
            // Submit the task to the pool
            System.out.println("Submitting task...");
            Future<Integer> future = executor.submit(task);

            // Do other work here while the thread executes...
            System.out.println("Waiting for the result...");
            
            // get() blocks the main thread until the task completes
            Integer result = future.get(); 
            System.out.println("Result received: " + result);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Always shut down the executor pool when finished
            executor.shutdown();
        }
    }
}


4. ExecutorService: quản lý thread đúng cách

ExecutorService là thread pool: giữ sẵn một nhóm thread và tái sử dụng chúng.

ExecutorService executor = Executors.newFixedThreadPool(4);

executor.submit(() -> {
    System.out.println("Process task");
});

executor.shutdown();

Ý nghĩa: tối đa 4 task chạy đồng thời. Các task dư sẽ chờ trong queue.

100 tasks
   ↓
Task Queue
   ↓
4 worker threads

Ví dụ xử lý ảnh, gửi email, gọi API bên ngoài.

ExecutorService executor = Executors.newFixedThreadPool(10);

for (String email : emails) {
    executor.submit(() -> emailService.send(email));
}

Nhưng lưu ý: newFixedThreadPool() không phải lúc nào cũng tốt vì queue mặc định có thể rất lớn. Production thường cấu hình ThreadPoolExecutor rõ ràng:

ExecutorService executor = new ThreadPoolExecutor(
        4,                      // core pool size
        8,                      // max pool size
        60, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(100),
        new ThreadPoolExecutor.CallerRunsPolicy()
);

Senior mindset: phải biết:

pool bao nhiêu thread?
queue có giới hạn không?
task bị reject thì sao?
task CPU-bound hay I/O-bound?
shutdown graceful như thế nào?


6. CompletableFuture

CompletableFuture cho phép tạo pipeline bất đồng bộ.

Ví dụ gọi User Service, sau đó lấy order của user:

CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(() -> userService.getUser(1L));

CompletableFuture<List<Order>> ordersFuture =
        userFuture.thenApply(user -> orderService.getOrders(user.id()));

List<Order> orders = ordersFuture.join();

thenApply: biến đổi kết quả.

CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> "quang")
                .thenApply(String::toUpperCase);

System.out.println(future.join()); // QUANG

thenCompose: khi bước tiếp theo cũng trả về CompletableFuture.

CompletableFuture<List<Order>> future =
        CompletableFuture.supplyAsync(() -> userService.getUser(1L))
                .thenCompose(user ->
                        CompletableFuture.supplyAsync(
                                () -> orderService.getOrders(user.id())
                        )
                );

Quy tắc:

thenApply: T → R
thenCompose: T → CompletableFuture<R>
Chạy song song và gộp kết quả
CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(() -> userService.getUser(1L));

CompletableFuture<List<Order>> orderFuture =
        CompletableFuture.supplyAsync(() -> orderService.getOrders(1L));

CompletableFuture<UserDashboard> dashboardFuture =
        userFuture.thenCombine(orderFuture,
                (user, orders) -> new UserDashboard(user, orders));

Hai API call chạy song song thay vì tuần tự.

Xử lý lỗi
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> paymentService.charge())
                .exceptionally(ex -> {
                    log.error("Payment failed", ex);
                    return "FAILED";
                });

Hoặc:

.handle((result, ex) -> {
    if (ex != null) {
        return "FALLBACK";
    }
    return result;
});

Cẩn thận: supplyAsync() mặc định dùng ForkJoinPool.commonPool(). Với production I/O task, nên truyền executor riêng:

CompletableFuture.supplyAsync(
        () -> externalApi.call(),
        ioExecutor
);
7. Virtual Threads — Java 21+

Virtual thread là thread nhẹ do JVM quản lý, rất phù hợp cho workload I/O blocking như HTTP call, DB call, file I/O.

try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<String> future = executor.submit(() -> {
        Thread.sleep(1000);
        return "Done";
    });

    System.out.println(future.get());
}

Khác biệt trực giác:

Platform thread:
Task blocking → OS thread bị giữ lại

Virtual thread:
Task blocking I/O → JVM có thể nhả carrier thread chạy task khác

Nó cho phép code synchronous, dễ đọc nhưng vẫn scale concurrency cao:

try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    List<Callable<String>> tasks = urls.stream()
            .map(url -> (Callable<String>) () -> httpClient.get(url))
            .toList();

    List<Future<String>> results = executor.invokeAll(tasks);
}

Virtual thread không phải “nhanh hơn mọi trường hợp”.


ConcurrentHashMap

HashMap không thread-safe.

Map<String, Integer> counts = new HashMap<>();

Nhiều thread put() đồng thời có thể gây race condition và state không đúng.

Dùng:

ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();

counts.merge("java", 1, Integer::sum);

Hoặc:

counts.compute("java", (key, oldValue) ->
        oldValue == null ? 1 : oldValue + 1
);

Hoặc khi counter có contention cao:

ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<>();

counts.computeIfAbsent("java", key -> new LongAdder())
        .increment();



// syncho
synchronized

synchronized dùng intrinsic lock (monitor lock) của object.

class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

Một thời điểm chỉ một thread được vào increment() trên cùng instance Counter.

Tương đương:

public void increment() {
    synchronized (this) {
        count++;
    }
}

Cần nhớ:

Bảo đảm atomicity cho đoạn code trong lock.
Bảo đảm visibility khi unlock/lock.
Có blocking: thread không lấy được lock phải chờ.

Không nên lock quá rộng:

public synchronized void process() {
    databaseCall();       // không nên giữ lock khi I/O lâu
    externalApiCall();    // lock lâu gây nghẽn
    count++;
}

Tốt hơn:

public void process() {
    externalApiCall();

    synchronized (this) {
        count++;
    }
}

Visibility và volatile

Visibility là việc một thread có “nhìn thấy” giá trị mới nhất do thread khác ghi hay không.

Ví dụ dễ bị treo:

class TaskRunner {
    private boolean running = true;

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            // work
        }
    }
}

Do CPU cache/compiler optimization, thread chạy run() có thể không thấy running = false.

Sửa:

private volatile boolean running = true;

volatile bảo đảm thread khác nhìn thấy value mới nhất.

class TaskRunner {
    private volatile boolean running = true;

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            // work
        }
    }
}