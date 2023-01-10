import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class EmployeesMaximumSalaries {

    private static final String GET_DEPARTMENTS = "FROM Department";

    private static final String DEPARTMENT_MAX_SALARY_FORMAT = "%s %.2f%n";

    private static final BigDecimal BELOW_SALARY = BigDecimal.valueOf(30000);
    private static final BigDecimal ABOVE_SALARY = BigDecimal.valueOf(70000);

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        final List<Department> departmentList = entityManager.createQuery(GET_DEPARTMENTS, Department.class)
                .getResultList();

        for (Department department : departmentList) {
            final BigDecimal maxSalary = department.getEmployees().stream()
                    .map(Employee::getSalary)
                    .max(BigDecimal::compareTo)
                    .orElse(null);

            if (maxSalary != null &&
                    (maxSalary.compareTo(BELOW_SALARY) < 0 || maxSalary.compareTo(ABOVE_SALARY) > 0)) {
                System.out.printf(DEPARTMENT_MAX_SALARY_FORMAT, department.getName(), maxSalary);
            }
        }

        entityManager.close();
    }
}
