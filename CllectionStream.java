import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

record Employee(String name, String department, int salary) {}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee("Minh", "IT", 35),
            new Employee("Lan", "HR", 25),
            new Employee("Hùng", "IT", 50),
            new Employee("Mai", "HR", 32),
            new Employee("Nam", "Finance", 40)
        );

        // 1. Nhân viên có lương từ 30 triệu
        List<Employee> highSalaryEmployees = employees.stream()
            .filter(employee -> employee.salary() >= 30)
            .toList();

        // 2. Sắp xếp giảm dần theo lương
        List<Employee> sortedEmployees = employees.stream()
            .sorted(
                Comparator.comparingInt(Employee::salary).reversed()
            )
            .toList();

        // 3. Chuyển thành danh sách tên
        List<String> employeeNames = employees.stream()
            .map(Employee::name)
            .toList();

        // 4. Nhóm nhân viên theo department
        Map<String, List<Employee>> employeesByDepartment =
            employees.stream()
                .collect(Collectors.groupingBy(Employee::department));

        // 5. Tính tổng lương của mỗi department
        Map<String, Integer> totalSalaryByDepartment =
            employees.stream()
                .collect(
                    Collectors.groupingBy(
                        Employee::department,
                        Collectors.summingInt(Employee::salary)
                    )
                );

        System.out.println(highSalaryEmployees);
        System.out.println(sortedEmployees);
        System.out.println(employeeNames);
        System.out.println(employeesByDepartment);
        System.out.println(totalSalaryByDepartment);
    }
}


Comparator<Employee> bySalaryThenName =
    Comparator.comparing(Employee::salary)
        .reversed()
        .thenComparing(Employee::name);

List<Employee> sorted = employees.stream()
    .sorted(bySalaryThenName)
    .toList();