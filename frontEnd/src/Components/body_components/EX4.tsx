import axios from "axios"
import { useEffect, useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"

const taskDescription: string = `Tạo một API RESTful trong Spring Boot với đường dẫn
/employees để quản lý thông tin nhân viên. API sẽ hỗ trợ các thao tác cơ bản: 
lấy danh sách nhân viên, tìm kiếm theo ID, thêm mới, cập nhật, và xóa nhân viên.
Tạo API RESTful với các phương thức GET, POST, PUT, DELETE trong Spring Boot.
Sử dụng @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, và @DeleteMapping.
Xử lý các yêu cầu liên quan đến thông tin nhân viên.`

// Định nghĩa kiểu cho Employee
interface Employee {
    id: number,
    fullName: string
    birthDay: string
    gender: string
    salary: number
}

const EX4: React.FC = () => {
    const [action, setAction] = useState<boolean>(false)
    const [update, setUpdate] = useState<boolean>(false)
    const [employees, setEmployees] = useState<Employee[]>([])
    const [newEmployee, setNewEmployee] = useState<Employee>({
        id: 0,
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
    })
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null)
    const [updateEmployee, setUpdateEmployee] = useState<Employee>({
        id: 0,
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
    })

    // Lấy danh sách nhân viên
    const fetchEmployees = () => {
        axios
            .get("http://localhost:8080/employees")
            .then((response) => {
                setEmployees(response.data)
                console.log(response.data)
            })
            .catch((error) => console.error("Error fetching employees:", error))
    }

    useEffect(() => {
        fetchEmployees()
    }, [])

    // Hàm xử lý thay đổi dữ liệu đầu vào
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        if (update) {
            setUpdateEmployee((prev) => ({
                ...prev,
                [name]: value,
            }))
        } else {
            setNewEmployee((prev) => ({
                ...prev,
                [name]: value,
            }))
        }
    }

    // Tạo nhân viên mới
    const handleCreateEmployee = () => {
        if (!newEmployee.fullName || !newEmployee.birthDay || !newEmployee.salary || !newEmployee.gender) {
            toast.error("Vui lòng điền đầy đủ thông tin!")
            return
        }

        axios
            .post("http://localhost:8080/employees", newEmployee)
            .then((response) => {
                toast.success("Tạo nhân viên thành công!")
                setEmployees([...employees, response.data])
                setNewEmployee({ id: 0, fullName: "", birthDay: "", gender: "", salary: 0 })
            })
            .catch((error) => {
                toast.error("Lỗi khi tạo nhân viên!")
                console.error("Error creating employee:", error)
            })
    }

    // Hiển thị thông tin chi tiết nhân viên
    const handleOpenForm = (id: number) => {
        setAction(true)
        axios
            .get(`http://localhost:8080/employees/${id}`)
            .then((response) => setSelectedEmployee(response.data))
            .catch((error) => {
                toast.error("Lỗi khi hiển thị nhân viên!")
                console.error("Error fetching employee details:", error)
            })
    }

    // Xóa nhân viên
    const handleDeleteEmployee = (id: number) => {
        axios
            .delete(`http://localhost:8080/employees/${id}`)
            .then((response) => {
                toast.success(response.data.message)
                setEmployees(employees.filter((employee) => employee.id !== id)) // Cập nhật lại danh sách nhân viên
            })
            .catch((error) => {
                toast.error("Lỗi khi xóa nhân viên!")
                console.error("Error deleting employee:", error)
            })
    }

    // Mở form cập nhật nhân viên
    const handleOpenFormUpdate = (id: number) => {
        setUpdate(true)
        axios
            .get(`http://localhost:8080/employees/${id}`)
            .then((response) => {
                setSelectedEmployee(response.data)
                setUpdateEmployee(response.data)
            })
            .catch((error) => {
                toast.error("Lỗi khi hiển thị nhân viên!")
                console.error("Error fetching employee details:", error)
            })
    }

    // Cập nhật nhân viên
    const handleUpdateEmployee = () => {
        if (!selectedEmployee) return

        axios
            .put(`http://localhost:8080/employees/${selectedEmployee.id}`, updateEmployee)
            .then((res) => {
                console.log(res.data)
                toast.success("Cập nhật nhân viên thành công!")
                fetchEmployees()
                setUpdateEmployee({ id: 0, fullName: "", birthDay: "", gender: "", salary: 0 })
                setUpdate(false)
            })
            .catch((error) => {
                toast.error("Lỗi khi cập nhật nhân viên!")
                console.error("Error updating employee:", error)
            })
    }

    //format tiền sang VNĐ
    const formatNumber = (number: number): string => {
        return new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND",
        }).format(number)
    }


    const formatDate = (datetime: string): string => {
        const input = datetime;
        const dateObj = new Date(input);
        const year = dateObj.getFullYear();
        const month = (dateObj.getMonth() + 1).toString().padStart(2, '0');
        const date = dateObj.getDate().toString().padStart(2, '0');
        const result = `${date}/${month}/${year}`;
        return result;
    }

    return (
        <div className="bai-tap">

            {/* Modal cập nhật */}
            {update && (
                <div className="independence-modal">
                    <div className="user-request column">
                        <span>Điền thông tin mới cho nhân viên có ID : {updateEmployee.id}</span>
                        <input
                            type="text"
                            name="fullName"
                            value={updateEmployee.fullName}
                            onChange={handleInputChange}
                            placeholder="Please type full name"
                        />
                        <input
                            type="date"
                            name="birthDay"
                            value={updateEmployee.birthDay}
                            onChange={handleInputChange}
                            placeholder="Please type birthday"
                        />
                        <input
                            type="number"
                            name="salary"
                            value={updateEmployee.salary}
                            onChange={handleInputChange}
                            placeholder="Please type salary"
                        />
                        <select name="gender" value={updateEmployee.gender} onChange={handleInputChange}>
                            <option value="">Chọn giới tính</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                        </select>

                        <div className="area-action">
                            <button className="btn-submit" onClick={handleUpdateEmployee}>
                                Update <i className="fa-solid fa-check"></i>
                            </button>

                            <button className="btn-submit cancel" onClick={() => setUpdate(false)}>
                                Cancel <i className="fa-solid fa-x"></i>
                            </button>
                        </div>

                    </div>
                </div>
            )}

            {/* Modal chi tiết nhân viên */}
            {action && selectedEmployee && (
                <div className="independence-modal" key={selectedEmployee.id}>
                    <span>ID: {selectedEmployee.id}</span>
                    <span>Name: {selectedEmployee.fullName}</span>
                    <span>Date of Birth: {formatDate(selectedEmployee.birthDay)}</span>
                    <span>Gender: {selectedEmployee.gender}</span>
                    <span>Salary: {formatNumber(selectedEmployee.salary)}</span>
                    <div className="area-action">
                        <button className="btn-submit delete" onClick={() => {
                            setAction(false)
                            setSelectedEmployee(null)
                        }}>
                            Close <i className="fa-solid fa-x"></i>
                        </button>
                    </div>
                </div>
            )}

            <span>Đề bài tập 4: {taskDescription}</span>
            <span>User request:</span>

            {/* Form thêm mới nhân viên */}
            <div className="user-request column">
                <span>Điền thông tin cho nhân viên:</span>
                <input
                    type="text"
                    name="fullName"
                    value={newEmployee.fullName}
                    onChange={handleInputChange}
                    placeholder="Please type full name"
                />
                <input
                    type="date"
                    name="birthDay"
                    value={newEmployee.birthDay}
                    onChange={handleInputChange}
                    placeholder="Please type birthday"
                />
                <input
                    type="number"
                    name="salary"
                    value={newEmployee.salary}
                    onChange={handleInputChange}
                    placeholder="Please type salary"
                />
                <select name="gender" value={newEmployee.gender} onChange={handleInputChange}>
                    <option value="">Chọn giới tính</option>
                    <option value="Nam">Nam</option>
                    <option value="Nữ">Nữ</option>
                </select>
                <button className="btn-submit" onClick={handleCreateEmployee}>Create</button>
            </div>

            <p className="center">
                <i className="fas fa-users"></i> Quản lý nhân viên
            </p>

            {/* Hiển thị danh sách nhân viên */}
            <div className="modal">
                {employees.map((employee) => (
                    <div key={employee.id} className="modal-user">
                        <span>ID: {employee.id}</span>
                        <span>Name: {employee.fullName}</span>
                        <span>Date of Birth: {formatDate(employee.birthDay)}</span>
                        <span>Gender: {employee.gender}</span>
                        <span>Salary: {formatNumber(employee.salary)}</span>
                        <div className="area-action">
                            <button className="btn-icon edit" onClick={() => handleOpenFormUpdate(employee.id)}>
                                Edit <i className="fa-solid fa-pencil"></i>
                            </button>
                            <button className="btn-icon detail" onClick={() => handleOpenForm(employee.id)}>
                                View <i className="fa-solid fa-eye"></i>
                            </button>
                            <button className="btn-icon delete" onClick={() => handleDeleteEmployee(employee.id)}>
                                Delete <i className="fa-solid fa-trash"></i>
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            <ToastContainer />
        </div>
    )
}

export default EX4
