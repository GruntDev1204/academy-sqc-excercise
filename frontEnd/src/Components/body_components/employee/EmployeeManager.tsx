import { useEffect, useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
import UpdateEmployeeModal from "./ModalForm"
import { EmployeeService } from "../../../service/impl/EmployeesService"
import { Employee, FetchEmployee, FetchDepartment } from "../../../type/dataType"
import { uploadAvatar } from "../../../config/upload"
import { formatDate, formatNumber } from "../../../service/format"
import { DepartmentService } from "../../../service/impl/DepartmentsService"

const EX4: React.FC = () => {
    const employeeService = new EmployeeService()
    const departmentService = new DepartmentService()

    const [findId, setId] = useState<number>(0)

    const [action, setAction] = useState<boolean>(false)
    const [openAvatar, setOpenAvatar] = useState<boolean>(false)

    const [updateAvatar, setUpdateAvatar] = useState<boolean>(false)


    const [update, setUpdate] = useState<boolean>(false)
    const [employees, setEmployees] = useState<FetchEmployee[]>([])
    const [department, setDepartment] = useState<FetchDepartment[]>([])
    const [selectedEmployee, setSelectedEmployee] = useState<FetchEmployee | null>(null)

    const [selectedFile, setSelectedFile] = useState<File | null>(null)

    const [newEmployee, setNewEmployee] = useState<Employee>({
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
        phoneNumber: "",
        departmentId: 0,
        avatar: "",

    })
    const [updateEmployee, setUpdateEmployee] = useState<Employee>({
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
        phoneNumber: "",
        departmentId: 0,
        avatar: "",
    })

    useEffect(() => {
        fetchEmployees()
        fetchDepartments()
    }, [])

    //lấy danh sách bộ phận
    const fetchDepartments = async () => {
        const dataD = await departmentService.fetchData(-1)
        setDepartment(dataD.data)
    }
    // Lấy danh sách nhân viên
    const fetchEmployees = async () => {
        const dataE = await employeeService.fetchData(-1)
        setEmployees(dataE.data)
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
                    return updatedEmployee
                })

                alert("Cập nhật avatar thành công!")
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


    const viewDetailEmployee = async (id: number) => {
        setAction(true)

        const data = await employeeService.fetchData(id)
        setSelectedEmployee(data.data)
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value, files } = e.target as HTMLInputElement

        if (name === 'avatar' && files && files.length > 0) {
            const file = files[0]
            setSelectedFile(file)
        } else {
            if (name === "departmentId") {
                const departmentIdValue: any = value === "0" ? null : Number(value)
                if (update) {
                    setUpdateEmployee((prev) => ({
                        ...prev,
                        departmentId: departmentIdValue,
                    }))
                } else {
                    setNewEmployee((prev) => ({
                        ...prev,
                        departmentId: departmentIdValue,
                    }))
                }
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
    }

    const checkData = () => {
        return (!newEmployee.fullName || !newEmployee.birthDay || !newEmployee.salary || !newEmployee.gender || !newEmployee.phoneNumber)
    }

    // Tạo nhân viên mới
    const handleCreateEmployee = async () => {
        if (checkData()) {
            toast.error("Vui lòng điền đầy đủ thông tin!")
            return
        }
        if (selectedFile) {
            const avatarUrl = await uploadAvatar(selectedFile)
            newEmployee.avatar = avatarUrl
        }

        const createE = new EmployeeService(newEmployee)

        await createE.createData(
            (message: string) => {
                alert(message)
                fetchEmployees()
                setNewEmployee({
                    fullName: "", birthDay: "", gender: "", salary: 0, phoneNumber: "",
                    departmentId: 0,
                    avatar: "",
                })
                setOpenAvatar(false)
            },
            (message: string) => toast.error(message)
        )

    }

    //xóa nhân viên
    const handleDeleteEmployee = async (id: number) => {
        if (window.confirm("Do you want to delete this user? ID = " + id)) {
            const deleted = await employeeService.deleteData(id)
            alert(deleted)
            setEmployees(employees.filter((employee) => employee.id !== id))
        }
    }

    const handleOpenFormUpdateAvatar = async (id: number) => {
        setUpdateAvatar(true)

        const data = await employeeService.fetchData(id)
        const getData = data.data
        const mappedData = {
            ...getData,
            departmentId: getData.department?.id || 0,
        }
        
        setSelectedEmployee(getData)
        setUpdateEmployee(mappedData)
        console.log(JSON.stringify(getData))

        console.log(JSON.stringify(mappedData))

    }

    const handleUpdateAvatar = async () => {
        if (!selectedEmployee) return

        try {
            const updateA = new EmployeeService(updateEmployee)

            const data = await updateA.updateData(selectedEmployee.id)
            await fetchEmployees()

            setUpdateEmployee({
                fullName: "",
                birthDay: "",
                gender: "",
                salary: 0,
                phoneNumber: "",
                departmentId: 0,
                avatar: "",
            })

            setUpdateAvatar(false)
            alert(data.message)
        } catch (error) {
            toast.error("Cập nhật avatar thất bại!")
        }
    }

    const handleOpenFormUpdate = async (id: number) => {
        setUpdate(true)

        const data = await employeeService.fetchData(id)
        const getData = data.data
        const mappedData = {
            ...getData,
            departmentId: getData.department?.id || 0,
        }

        setSelectedEmployee(getData)
        setUpdateEmployee(mappedData)
        setId(id)
    }

    const handleUpdateEmployee = async (updatedEmployee: Employee) => {
        if (!updatedEmployee) return

        try {
            const updateE = new EmployeeService(updatedEmployee)

            const data = await updateE.updateData(findId)
            await fetchEmployees()

            setUpdateEmployee({
                fullName: "",
                birthDay: "",
                gender: "",
                salary: 0,
                phoneNumber: "",
                departmentId: 0,
                avatar: "",
            })
            alert(data.message)
            setUpdate(false)
        } catch (error) {
            toast.error("Cập nhật thông tin thất bại!")
        }
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
                    <span>
                        Department: {selectedEmployee.department ? selectedEmployee.department.name : "Loading..."}
                    </span>


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

            <p className="center">
                <i className="fas fa-users"></i> Quản lý nhân viên
            </p>

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
                    <option value="0">Chọn bộ phận</option>
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
                        <span>Department: {employee.department ? employee.department.name : "Loading..."}</span>


                        <div className="area-action">
                            <button className="btn-icon edit" onClick={() => handleOpenFormUpdate(employee.id)}>
                                Edit <i className="fa-solid fa-pencil"></i>
                            </button>
                            <button className="btn-icon detail" onClick={() => viewDetailEmployee(employee.id)}>
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
