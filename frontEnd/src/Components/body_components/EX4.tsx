import axios from "axios"
import { getDownloadURL, ref, uploadBytes } from "firebase/storage"
import { useEffect, useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
import { storage } from "../../config/firebase"
import UpdateEmployeeModal from "./buoi1/ModalForm"

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
    phoneNumber: string
    departmentId: number
    avatar: string
}

interface Department {
    id: number
    name: string
}

const EX4: React.FC = () => {
    const [action, setAction] = useState<boolean>(false)
    const [openAvatar, setOpenAvatar] = useState<boolean>(false)

    const [updateAvatar, setUpdateAvatar] = useState<boolean>(false)


    const [update, setUpdate] = useState<boolean>(false)
    const [employees, setEmployees] = useState<Employee[]>([])
    const [department, setDepartment] = useState<Department[]>([])

    const [selectedFile, setSelectedFile] = useState<File | null>(null)


    const [newEmployee, setNewEmployee] = useState<Employee>({
        id: 0,
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
        phoneNumber: "",
        departmentId: 0,
        avatar: "",

    })
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null)
    const [updateEmployee, setUpdateEmployee] = useState<Employee>({
        id: 0,
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
        phoneNumber: "",
        departmentId: 0,
        avatar: "",
    })

    // Upload avatar lên Firebase
    const uploadAvatar = async (file: File): Promise<string> => {
        try {
            const storageRef = ref(storage, `sqc-academy/${file.name}`)
            console.log('Storage reference created:', storageRef)

            const uploadResult = await uploadBytes(storageRef, file)
            console.log('Upload result:', uploadResult)

            const downloadURL = await getDownloadURL(storageRef)
            console.log('Download URL:', downloadURL)

            toast.success("tải ảnh lên thành công! ")

            return downloadURL
        } catch (error) {
            toast.error("Lỗi khi tải ảnh lên Firebase!")
            console.error(error)
            return ""
        }
    }

    const handleUploadAvatar = async () => {
        if (!selectedFile) {
            toast.error('Chưa chọn ảnh!')
            return
        }


        const uploadedAvatarUrl = await uploadAvatar(selectedFile)
        if (uploadedAvatarUrl) {
            setNewEmployee((prev) => ({
                ...prev,
                avatar: uploadedAvatarUrl,
            }))
            console.log(newEmployee)
            setSelectedFile(null)
        }
    }


    // for update avatar    
    const handleReuploadAvatar = async () => {
        if (!selectedFile) {
            toast.error('Chưa chọn ảnh!')
            return
        }

        try {
            const uploadedAvatarUrl = await uploadAvatar(selectedFile)

            if (uploadedAvatarUrl) {

                setUpdateEmployee((prev) => {
                    const updatedEmployee = { ...prev, avatar: uploadedAvatarUrl }
                    console.log("Updated Employee:", updatedEmployee)
                    return updatedEmployee
                })

                toast.success("Cập nhật avatar thành công!")
            } else {
                toast.error("Không thể cập nhật avatar!")
            }
        } catch (error) {
            toast.error("Có lỗi xảy ra khi cập nhật avatar!")
            console.error("Error reuploading avatar:", error)
        } finally {
            setSelectedFile(null)
        }
    }

    // Lấy danh sách nhân viên
    const fetchEmployees = () => {
        axios
            .get("http://localhost:8080/employees")
            .then((response) => {
                setEmployees(response.data.data)
                console.log(response.data.data)
            })
            .catch((error) => console.error("Error fetching employees:", error))
    }

    //lấy danh sách bộ phận
    const fetchDepartments = () => {
        axios
            .get("http://localhost:8080/departments")
            .then((response) => {
                setDepartment(response.data.data)
                console.log(response.data.data)
            })
            .catch((error) => console.error("Error fetching employees:", error))
    }

    const getDepartmentName = (departmentId: number): string => {
        const getDepName = department.find(dep => dep.id === departmentId)
        return getDepName ? getDepName.name : "Null"
    }


    useEffect(() => {
        fetchEmployees()
        fetchDepartments()
    }, [])

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value, files } = e.target as HTMLInputElement

        if (name === 'avatar' && files && files.length > 0) {
            const file = files[0] // Lấy file được chọn
            setSelectedFile(file) // Lưu file vào trạng thái
        } else {
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
    }



    // Tạo nhân viên mới
    const handleCreateEmployee = async () => {
        if (!newEmployee.fullName || !newEmployee.birthDay || !newEmployee.salary || !newEmployee.gender || !newEmployee.departmentId || !newEmployee.phoneNumber) {
            toast.error("Vui lòng điền đầy đủ thông tin!")
            return
        }
        if (selectedFile) {
            const avatarUrl = await uploadAvatar(selectedFile)
            newEmployee.avatar = avatarUrl
        }
        axios
            .post("http://localhost:8080/employees", newEmployee)
            .then((response) => {
                toast.success(response.data.message)
                console.log(response.data.data)
                fetchEmployees()
                setNewEmployee({
                    id: 0, fullName: "", birthDay: "", gender: "", salary: 0, phoneNumber: "",
                    departmentId: 0,
                    avatar: "",
                })
                setOpenAvatar(false)
            })
            .catch((error) => {
                toast.error("Lỗi khi tạo nhân viên! - ")
                console.error("Error creating employee:", error)
            })
    }

    // Hiển thị thông tin chi tiết nhân viên
    const handleOpenForm = (id: number) => {
        setAction(true)
        axios
            .get(`http://localhost:8080/employees/${id}`)
            .then((response) => {
                setSelectedEmployee(response.data.data)
                console.log(response.data.message)
            })
            .catch((error) => {
                toast.error("Lỗi khi hiển thị nhân viên!")
                console.error("Error fetching employee details:", error)
            })
    }

    // Xóa nhân viên
    const handleDeleteEmployee = (id: number) => {
        if (window.confirm("Do you want to delete this user? ID = " + id)) {
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

    }

    //mở form update avatar
    const handleOpenFormUpdateAvatar = (id: number) => {
        setUpdateAvatar(true)
        axios
            .get(`http://localhost:8080/employees/${id}`)
            .then((response) => {
                setSelectedEmployee(response.data.data)
                setUpdateEmployee(response.data.data)
                console.log(response.data.data)
            })
            .catch((error) => {
                toast.error("Lỗi khi hiển thị nhân viên!")
                console.error("Error fetching employee details:", error)
            })
    }


    // Mở form cập nhật nhân viên
    const handleOpenFormUpdate = (id: number) => {
        setUpdate(true)
        axios
            .get(`http://localhost:8080/employees/${id}`)
            .then((response) => {
                setSelectedEmployee(response.data.data)
                setUpdateEmployee(response.data.data)
            })
            .catch((error) => {
                toast.error("Lỗi khi hiển thị nhân viên!")
                console.error("Error fetching employee details:", error)
            })
    }

    // Cập nhật nhân viên
    const handleUpdateAvatar = () => {
        if (!selectedEmployee) return
        axios
            .put(`http://localhost:8080/employees/${selectedEmployee.id}`, updateEmployee)
            .then((res) => {
                console.log(res.data.data)
                console.log(res.data.message)
                toast.success("Cập nhật nhân viên thành công!")
                fetchEmployees()

                // Reset lại dữ liệu
                setUpdateEmployee({
                    id: 0, fullName: "", birthDay: "", gender: "", salary: 0, phoneNumber: "",
                    departmentId: 0, avatar: "",
                })
                setUpdate(false)
                setUpdateAvatar(false)
            })
            .catch((error) => {
                toast.error("Lỗi khi cập nhật nhân viên!")
                console.error("Error updating employee:", error)
            })
    }


    const handleUpdateEmployee = (updatedEmployee: Employee) => {
        if (!updatedEmployee) return

        axios
            .put(`http://localhost:8080/employees/${updatedEmployee.id}`, updatedEmployee)
            .then((res) => {
                console.log(res.data.data)
                console.log(res.data.message)
                toast.success("Cập nhật nhân viên thành công!")
                fetchEmployees()

                // Reset lại dữ liệu
                setUpdateEmployee({
                    id: 0, fullName: "", birthDay: "", gender: "", salary: 0, phoneNumber: "",
                    departmentId: 0, avatar: "",
                })
                setUpdate(false)
                setUpdateAvatar(false)
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
        const input = datetime
        const dateObj = new Date(input)
        const year = dateObj.getFullYear()
        const month = (dateObj.getMonth() + 1).toString().padStart(2, '0')
        const date = dateObj.getDate().toString().padStart(2, '0')
        const result = `${date}/${month}/${year}`
        return result
    }

    return (
        <div className="bai-tap">

            {/* form dành cho việc update avatar */}
            {
                updateAvatar &&
                (
                    <div className="independence-modal">
                        <div className="user-request column">
                            <div className="file-area">
                                <input
                                    type="file"
                                    name="avatar"
                                    onChange={handleInputChange}
                                />
                                <button onClick={handleReuploadAvatar}>Upload a file <i className="fa-solid fa-floppy-disk"></i></button>
                            </div>
                            <div className="area-action">
                                <button className="btn-submit" onClick={handleUpdateAvatar}>
                                    Update <i className="fa-solid fa-check"></i>
                                </button>

                                <button className="btn-submit cancel" onClick={() => setUpdateAvatar(false)}>
                                    Cancel <i className="fa-solid fa-x"></i>
                                </button>
                            </div>

                        </div>
                    </div>
                )
            }


            <UpdateEmployeeModal
                isOpen={update}
                employee={updateEmployee}
                departments={department}
                onClose={() => setUpdate(false)}
                onSave={(updatedEmployee) => {
                    handleUpdateEmployee(updatedEmployee)
                }}
                onChange={(name, value) => {
                    setUpdateEmployee((prev) => ({ ...prev, [name]: value }))
                }}
            />

            {/* Modal chi tiết nhân viên */}
            {action && selectedEmployee && (
                <div className="independence-modal" key={selectedEmployee.id}>
                    <div className="avatar-area" >  <img src={selectedEmployee.avatar} className="avatar" /> </div>
                    <span>ID: {selectedEmployee.id}</span>
                    <span>Name: {selectedEmployee.fullName}</span>
                    <span>Date of Birth: {formatDate(selectedEmployee.birthDay)}</span>
                    <span>Gender: {selectedEmployee.gender}</span>
                    <span>Salary: {formatNumber(selectedEmployee.salary)}</span>
                    <span>Phone Number: {selectedEmployee.phoneNumber}</span>
                    <span>Department: {getDepartmentName(selectedEmployee.departmentId)}</span>

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
                <input
                    type="text"
                    name="phoneNumber"
                    value={newEmployee.phoneNumber}
                    onChange={handleInputChange}
                    placeholder="Please type the phone "
                />




                {/* Render danh sách bộ phận */}
                <select
                    name="departmentId"
                    value={newEmployee.departmentId}
                    onChange={handleInputChange}
                >
                    <option value="">Chọn bộ phận</option>
                    {department.map((d) => (
                        <option key={d.id} value={d.id}>
                            {d.name}
                        </option>
                    ))}
                </select>
                <select name="gender" value={newEmployee.gender} onChange={handleInputChange}>
                    <option value="">Chọn giới tính</option>
                    <option value="Male">Nam</option>
                    <option value="Female">Nữ</option>
                </select>




                {openAvatar && (

                    <div className="file-area">
                        <input
                            type="file"
                            name="avatar"
                            onChange={handleInputChange}
                        />
                        <button onClick={handleUploadAvatar}>Upload a file <i className="fa-solid fa-floppy-disk"></i></button>
                    </div>
                )}

                <div className="flex">
                    <button className="btn-large " onClick={() => setOpenAvatar((toggle) => !toggle)}>Upload a avatar for employees <i className="fa-solid fa-camera-retro"></i> </button>

                    <button className="btn-submit" onClick={handleCreateEmployee}>Create</button>
                </div>



            </div>

            <p className="center">
                <i className="fas fa-users"></i> Quản lý nhân viên
            </p>

            {/* Hiển thị danh sách nhân viên */}
            <div className="modal">
                {employees.map((employee) => (
                    <div key={employee.id} className="modal-user">
                        <div className="avatar-area" >  <img src={employee.avatar} className="avatar" /> </div>
                        <i className="fas fa-pencil editt" onClick={() => handleOpenFormUpdateAvatar(employee.id)}></i>


                        <span>ID: {employee.id}</span>
                        <span>Name: {employee.fullName}</span>
                        <span>Date of Birth: {formatDate(employee.birthDay)}</span>
                        <span>Gender: {employee.gender}</span>
                        <span>Salary: {formatNumber(employee.salary)}</span>
                        <span>Phone Number: {employee.phoneNumber}</span>
                        <span>Department : {getDepartmentName(employee.departmentId)}</span>

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
