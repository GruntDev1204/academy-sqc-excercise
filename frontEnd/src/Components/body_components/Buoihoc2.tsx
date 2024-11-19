import axios from "axios"
import { useEffect, useState } from "react"
import { toast, ToastContainer } from "react-toastify"

const Buoihoc2: React.FC = () => {
    interface FindEmployee {
        funName: string
        birthDayFrom: string
        birthDayTo: string
        gender: string
        salaryValue: number
        phoneNumber: string
        departmentId: number
    }

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

    const [formData, setFormData] = useState<FindEmployee>({
        funName: "",
        birthDayFrom: "",
        birthDayTo: "",
        gender: "",
        salaryValue: 0,
        phoneNumber: "",
        departmentId: 0,
    })

    const [dataEmployees, setDataEmployees] = useState<Employee[]>([])

    const [dataDepartments, setDepartments] = useState<Department[]>([])


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        setFormData((prev) => ({ ...prev, [name]: value }))
    }

    const handleReset = () => {
        setFormData({
            funName: "",
            birthDayFrom: "",
            birthDayTo: "",
            gender: "",
            salaryValue: 0,
            phoneNumber: "",
            departmentId: 0,
        })
    }

    const handleFind = () => {
        console.log("Tìm kiếm với dữ liệu:", formData)

        const validParams = Object.fromEntries(
            Object.entries(formData).filter(([key, value]) =>
                value !== "" && value !== null && value !== 0
            )
        )

        axios.get("http://localhost:8080/employees", {
            params: validParams,
        })
            .then((res) => {
                console.log("Kết quả trả về:", res.data);
                toast.success(res.data.message);
                setDataEmployees(res.data.data);
            })
            .catch((err) => {
                console.error("Lỗi:", err);
                toast.error("Có lỗi xảy ra trong quá trình tìm kiếm.");
            })

    }

    const apiDepartments = () => {
        axios.get("http://localhost:8080/departments")
            .then((res) => {
                console.log(res.data.data)
                setDepartments(res.data.data)
            })
    }

    const getDepartmentName = (departmentId: number): string => {
        const getDepName = dataDepartments.find(dep => dep.id === departmentId)
        return getDepName ? getDepName.name : "Null"
    }



    useEffect(() => {
        apiDepartments()
    }, [])

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
            <div className="user-request column">
                <p className="center">
                    <i className="fa-solid fa-magnifying-glass"></i> Tìm kiếm  nhân viên
                </p>
                <h3> full name</h3>
                {/* Họ tên */}
                <input
                    type="text"
                    name="funName"
                    placeholder="Please type full name"
                    value={formData.funName}
                    onChange={handleInputChange}
                />


                <h3> Ngày sinh từ</h3>

                {/* Ngày sinh từ */}
                <input
                    type="date"
                    name="birthDayFrom"
                    placeholder="Birth Day From"
                    value={formData.birthDayFrom}
                    onChange={handleInputChange}
                />


                <h3> Ngày sinh đến</h3>
                {/* Ngày sinh đến */}
                <input
                    type="date"
                    name="birthDayTo"
                    placeholder="Birth Day To"
                    value={formData.birthDayTo}
                    onChange={handleInputChange}
                />
                <h3> giới tính </h3>
                {/* Giới tính */}
                <select name="gender" value={formData.gender} onChange={handleInputChange}>
                    <option value="">Chọn giới tính</option>
                    <option value="Male">Nam</option>
                    <option value="Female">Nữ</option>
                </select>

                <h3>Mức Lương</h3>
                <select name="salaryValue" value={formData.salaryValue} onChange={handleInputChange}>
                    <option value="">Chọn mức lương</option>
                    <option value={1}>Dưới 10 triệu</option>
                    <option value={2}>10-15 triệu</option>
                    <option value={3}> 15-30 triệu</option>
                    <option value={4}>Trên 30 triệu</option>

                </select>


                <h3>Số phone </h3>

                {/* Số điện thoại */}
                <input
                    type="text"
                    name="phoneNumber"
                    placeholder="Phone Number"
                    value={formData.phoneNumber}
                    onChange={handleInputChange}
                />


                <h3>Bộ phận làm việc </h3>

                {/* Bộ phận */}
                <select
                    name="departmentId"
                    value={formData.departmentId}
                    onChange={handleInputChange}
                >
                    <option value="">Chọn bộ phận</option>
                    {dataDepartments.map((d) => (
                        <option key={d.id} value={d.id}>
                            {d.name}
                        </option>
                    ))}
                </select>

                {/* Buttons */}
                <button className="btn-submit" onClick={handleReset}>
                    Reset  <i className="fa-solid fa-rotate-right"></i>
                </button>
                <button className="btn-submit" onClick={handleFind}>

                    Find   <i className="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>

            <div className="modal">
                {dataEmployees.map((employee) => (
                    <div key={employee.id} className="modal-user">
                        <div className="avatar-area" >  <img src={employee.avatar} className="avatar" /> </div>


                        <span>ID: {employee.id}</span>
                        <span>Name: {employee.fullName}</span>
                        <span>Date of Birth: {formatDate(employee.birthDay)}</span>
                        <span>Gender: {employee.gender}</span>
                        <span>Salary: {formatNumber(employee.salary)}</span>
                        <span>Phone Number: {employee.phoneNumber}</span>
                        <span>Department : {getDepartmentName(employee.departmentId)}</span>


                    </div>
                ))}
            </div>

            <ToastContainer />

        </div>
    )
}

export default Buoihoc2
