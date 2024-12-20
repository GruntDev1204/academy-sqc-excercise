import React from "react"
import { toast } from "react-toastify"

interface Employee {
    id: number
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

interface UpdateEmployeeModalProps {
    isOpen: boolean;
    employee: Employee;
    departments: Department[];
    onClose: () => void;
    onSave: (updatedEmployee: Employee) => void;
    onChange: (name: string, value: string | number) => void
}


const UpdateEmployeeModal: React.FC<UpdateEmployeeModalProps> = ({
    isOpen,
    employee,
    departments,
    onClose,
    onSave,
    onChange,
}) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        onChange(name, name === "salary" || name === "departmentId" ? Number(value) : value)
    }

    const handleSave = () => {
        if (!employee.fullName || !employee.birthDay || !employee.salary || !employee.gender) {
            toast.error("Vui lòng điền đầy đủ thông tin!")
            return
        }
        onSave(employee)
        onClose()
    }

    if (!isOpen) return null

    return (
        <div className="independence-modal">
            <div className="user-request column">
                <span>Cập nhật thông tin mới cho nhân viên có ID: {employee.id}</span>
                <input
                    type="text"
                    name="fullName"
                    value={employee.fullName}
                    onChange={handleChange}
                    placeholder="Please type full name"
                />
                <input
                    type="date"
                    name="birthDay"
                    value={employee.birthDay}
                    onChange={handleChange}
                />
                <input
                    type="number"
                    name="salary"
                    value={employee.salary}
                    onChange={handleChange}
                    placeholder="Please type salary"
                />
                <input
                    type="text"
                    name="phoneNumber"
                    value={employee.phoneNumber}
                    onChange={handleChange}
                    placeholder="Please type the phone"
                />
                <select
                    name="departmentId"
                    value={employee.departmentId}
                    onChange={handleChange}
                >
                    <option value="">Chọn bộ phận</option>
                    {departments.map((d) => (
                        <option key={d.id} value={d.id}>
                            {d.name}
                        </option>
                    ))}
                </select>
                <select
                    name="gender"
                    value={employee.gender}
                    onChange={handleChange}
                >
                    <option value="">Chọn giới tính</option>
                    <option value="Male">Nam</option>
                    <option value="Female">Nữ</option>
                </select>
                <div className="area-action">
                    <button className="btn-submit" onClick={handleSave}>
                        Save <i className="fa-solid fa-check"></i>
                    </button>
                    <button className="btn-submit cancel" onClick={onClose}>
                        Cancel <i className="fa-solid fa-x"></i>
                    </button>
                </div>
            </div>
        </div>
    )
}

export default UpdateEmployeeModal
