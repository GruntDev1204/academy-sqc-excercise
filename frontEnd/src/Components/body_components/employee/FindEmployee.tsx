import { useEffect, useState } from "react"
import { toast, ToastContainer } from "react-toastify"
import { FetchEmployee, FetchDepartment, FindEmployee } from "../../../type/dataType"
import { formatDate, formatNumber } from "../../../service/format"
import { DepartmentService } from "../../../service/impl/DepartmentsService"
import { EmployeeService } from "../../../service/impl/EmployeesService"

const FindEmployees: React.FC = () => {
    const dS = new DepartmentService()
    const eS = new EmployeeService()
    const [formData, setFormData] = useState<FindEmployee>({
        funName: "",
        birthDayFrom: "",
        birthDayTo: "",
        gender: "",
        salaryValue: "",
        phoneNumber: "",
        departmentId: 0,
    })

    const [dataEmployees, setDataEmployees] = useState<FetchEmployee[]>([])

    const [dataDepartments, setDepartments] = useState<FetchDepartment[]>([])

    useEffect(() => {
        apiDepartments()
    }, [])

    const handleReset = () => {
        setFormData({
            funName: "",
            birthDayFrom: "",
            birthDayTo: "",
            gender: "",
            salaryValue: "",
            phoneNumber: "",
            departmentId: 0,
        })
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target
        setFormData((prev) => ({ ...prev, [name]: value }))
    }


    const handleFind = async () => {
        const validParams = Object.fromEntries(
            Object.entries(formData).filter(([key, value]) =>
                value !== "" && value !== null && value !== 0
            )
        )

        const dataSelect = await eS.fetchData(-1 ,validParams )
        setDataEmployees(dataSelect.data)
        toast.success(dataSelect.message)
        if(dataSelect === null ) toast.error("có lỗi khi tải dữ liệu")
    }

    const apiDepartments = async () => {
        const data = await dS.fetchData(-1)
        setDepartments(data.data)
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
                    <option value="under_10m">Dưới 10 triệu</option>
                    <option value="10_to_15m">10-15 triệu</option>
                    <option value="15_to_20m"> 15-20 triệu</option>
                    <option value="above_20m">Trên 20 triệu</option>
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
                        <span>Department : {employee.department ? employee.department.name : "Loading..." }</span>


                    </div>
                ))}
            </div>

            <ToastContainer />

        </div>
    )
}

export default FindEmployees
