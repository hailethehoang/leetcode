// 7. Bài luyện tập số 1
// Cho danh sách
// List<Integer> numbers = List.of(3, 8, 2, 10, 5, 8, 12, 3);
// Hãy dùng Stream API để:

// Lấy số chẵn.
// Loại bỏ số trùng.
// Nhân mỗi số với 2.
// Sắp xếp giảm dần.
// Lấy 3 số đầu tiên.
List<Integer> numbers = List.of(3, 8, 2, 10, 5, 8, 12, 3);
List<Integer> result = numbers.stream()
        // code của anh
        .filter( i -> i % 2 == 0)
        .distinct().
        .map(i-> i* 2)
        .sorted(Comparator.comparing().reversed())
        .limit(3)
        .toList();

// Bài 2 — khó hơn một chút:
// record Employee(String name, String department, int salary) {}
// List<Employee> employees = List.of(
//         new Employee("An", "IT", 2000),
//         new Employee("Binh", "HR", 1500),
//         new Employee("Cuong", "IT", 3000),
//         new Employee("Dung", "IT", 2500),
//         new Employee("Hoa", "HR", 2200)
// );
// Hãy lấy:
// Nhân viên phòng "IT"
// Sắp xếp lương giảm dần
// Chỉ lấy tên
// Kết quả: ["Cuong", "Dung", "An"]
// Khung code:
List<String> result = employees.stream()
        // code của anh
        .filter( u -> u.getDepartment().equals("IT"))
        .sorted(Comparator.comparing(u -> u.getSalary(), Comparator.reversedOrder()))
        .map(u->u.getName())
        .toList();
// FIX
List<String> result = employees.stream()
        .filter(employee -> employee.department().equals("IT"))
        .sorted(Comparator.comparing(
                Employee::salary,
                Comparator.reverseOrder()
        ))
        .map(Employee::name)
        .toList();


// Bài 3:
// Từ employees, hãy tạo:
// Map<String, List<String>>
// Nhóm tên nhân viên theo phòng ban. Kết quả mong muốn:
// {
//     "IT": ["An", "Cuong", "Dung"],
//     "HR": ["Binh", "Hoa"]
// }
// Khung:
Map<String, List<String>> result = employees.stream()
        .collect(/* code của anh */);


Không. Java Stream API không chỉ dùng với List.

1. Mọi Collection: gọi .stream() trực tiếp

ArrayList, LinkedList, HashSet, Queue, Deque… đều implement Collection.

List<String> arrayList = new ArrayList<>();
arrayList.stream();


LinkedList<String> linkedList = new LinkedList<>();
linkedList.stream();


Queue<String> queue = new LinkedList<>();
queue.stream();


Set<String> set = new HashSet<>();
set.stream();

Ví dụ:

Queue<String> queue = new LinkedList<>();
queue.add("Java");
queue.add("Go");
queue.add("Python");


List<String> result = queue.stream()
        .filter(s -> s.length() > 2)
        .map(String::toUpperCase)
        .toList();

Không cần convert sang List.

2. Object array: dùng Arrays.stream()
String[] languages = {"Java", "Go", "Python"};


List<String> result = Arrays.stream(languages)
        .filter(s -> s.length() > 2)
        .map(String::toUpperCase)
        .toList();


3. Primitive array có stream riêng

Java hỗ trợ trực tiếp:

int[] numbers = {3, 8, 2, 10};


IntStream stream = Arrays.stream(numbers);

Ví dụ:

int sum = Arrays.stream(numbers)
        .filter(n -> n % 2 == 0)
        .map(n -> n * 2)
        .sum();

Các kiểu tương ứng:

int[]    → IntStream
long[]   → LongStream
double[] → DoubleStream

Lợi ích là không cần boxing int thành Integer.

Chuyển int[] thành List<Integer>
List<Integer> list = Arrays.stream(numbers)
        .boxed()
        .toList();

boxed() chuyển:

IntStream → Stream<Integer>