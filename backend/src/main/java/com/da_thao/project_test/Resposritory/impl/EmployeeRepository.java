package com.da_thao.project_test.Resposritory.impl;

import com.da_thao.project_test.Exception.ApiException;
import com.da_thao.project_test.DTO.code_response.impl.ErrorCode;
import com.da_thao.project_test.Model.Employee;
import com.da_thao.project_test.Request_param.vaild_request.RequestInterface;
import com.da_thao.project_test.Request_param.filter_data.FilterDataInterface;
import com.da_thao.project_test.DTO.data_filter.FindEmployee;
import com.da_thao.project_test.Resposritory.InterfaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Primary
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EmployeeRepository implements InterfaceRepository<Employee, FindEmployee> {
    FilterDataInterface<Employee, FindEmployee> filterDataEmployees;
    RequestInterface<Employee> requestDataEmployees;

    List<Employee> employeeList = new ArrayList<>(
            Arrays.asList(
                    new Employee(1, 10000000, "Hồ Trung", "Male", LocalDate.of(2004, 5, 18),
                            "0763514492", 1,
                            "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fz5900740189484_9bf3aefe9a2c1c7c2cc129b4cdc680bd.jpg?alt=media&token=c85d74dd-b7b5-480f-9f4c-272f1a46259a"),
                    new Employee(5, 12000000, "Dạ Thảo", "Female", LocalDate.of(2005, 12, 4),
                            "0763514402", 2,
                            "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fz5900740189484_9bf3aefe9a2c1c7c2cc129b4cdc680bd.jpg?alt=media&token=c85d74dd-b7b5-480f-9f4c-272f1a46259a"),
                    new Employee(2, 1000000000, "Ko Pin Yi", "Male", LocalDate.of(1989, 5, 31),
                            "0763514497", 4,
                            "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fimages.jpg?alt=media&token=c7f6ccdb-6f26-46bc-89dd-0fa14e1ec136"),
                    new Employee(3, 2000000000, "Fedor Gorst", "Male", LocalDate.of(2000, 5, 31),
                            "0763514487", 4,
                            "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fimages%20(1).jpg?alt=media&token=842f5852-3a7b-4865-b28c-a26d20f7c0dd"),
                    new Employee(4, 1000000000, "Francisco Sánchez Ruíz", "Male", LocalDate.of(1991, 12, 29),
                            "0763514387", 4,
                            "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fbil-FranciscoSanchezRuiz-2-0502.jpg?alt=media&token=4d18d762-f31d-4433-b5a3-61804c978ccd"),
                    new Employee(6, 2000000000, "Trương Mỹ Lan", "Female", LocalDate.of(1956, 10, 13),
                            "0763514587", 6,
                            "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Favataa.png?alt=media&token=751b2c78-709f-4631-9a46-81cbca824691")
            )
    );

    //database
    private Integer autoRandomId() {
        Random random = new Random();
        return random.nextInt(9993) + 7; // random(7->9999)
    }


    //IMPL METHODS
    @Override
    public List<Employee> getAll(FindEmployee requestParam) {
        return filterDataEmployees.filterAll(this.employeeList, requestParam);
    }

    @Override
    public Employee findById(Integer id) {
        Employee e = filterDataEmployees.filterById(this.employeeList, id);
        if (e == null) throw new ApiException(ErrorCode.EMPLOYEES_NOT_FOUND);

        return e;
    }

    @Override
    public Employee create(Employee e) {
        if (requestDataEmployees.checkRequest(e)) {
            e.setAvatar(e.getAvatar().isEmpty()
                    ?
                    "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fdefault_avatar.png?alt=media&token=cc9681dc-be98-4ebe-93a5-39238be649a0"
                    : e.getAvatar());
            e.setId(this.autoRandomId());
            employeeList.add(e);

            return e;
        }
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        this.employeeList.remove(this.findById(id));
        return true;
    }

    @Override
    public Employee update(Integer id, Employee updatedEmployee) {
        Employee e = this.findById(id);

        if (requestDataEmployees.checkRequest(updatedEmployee)) {
            e.setFullName(updatedEmployee.getFullName());
            e.setBirthDay(updatedEmployee.getBirthDay());
            e.setGender(updatedEmployee.getGender());
            e.setSalary(updatedEmployee.getSalary());
            e.setPhoneNumber(updatedEmployee.getPhoneNumber());
            e.setDepartmentId(updatedEmployee.getDepartmentId());
            e.setAvatar(updatedEmployee.getAvatar());

            return e;
        }
        throw new ApiException(ErrorCode.EMPLOYEES_UPDATE_FAILED);
    }

}
