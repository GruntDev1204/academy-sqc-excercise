import { useEffect, useState } from "react"
import { ToastContainer } from "react-toastify"
import { FetchEmployee, FetchDepartment, FindEmployee } from "../../../type/DataType"
import { formatDate, formatNumber } from "../../../service/Format"
import { DepartmentService } from "../../../service/impl/DepartmentsService"
import EmployeeService from "../../../service/impl/EmployeesService"

const FindEmployees: React.FC = () => {
    const dS = new DepartmentService()
    const eS = new EmployeeService()
    const dfFormData: FindEmployee = {
        fullName: "",
        birthDayFrom: "",
        birthDayTo: "",
        gender: "",
        salaryValue: "",
        phoneNumber: "",
        departmentId: 0,
    }
    const [formData, setFormData] = useState<FindEmployee>(dfFormData)

    const [dataEmployees, setDataEmployees] = useState<FetchEmployee[] | null>(null)

    const [dataDepartments, setDepartments] = useState<FetchDepartment[]>([])

    useEffect(() => {
        apiDepartments()
        handleFind()
    }, [])

    const handleReset = () => {
        setFormData(dfFormData)
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

        const dataSelect = await eS.fetchData(-1, validParams)
        if (dataSelect && dataSelect.data) setDataEmployees(dataSelect.data)
        else setDataEmployees(null)
    }

    const apiDepartments = async () => {
        const data = await dS.fetchData(-1)
        const totalItems = data.pageDetail.total_items
        const getTotal = await dS.fetchData(-1, null, totalItems)
        setDepartments(getTotal.data)
    }

    return (
        <div className="bai-tap">
            {dataEmployees === null ? (
                <div className="user-request column">
                    <p className="center">
                        <i className="fa-solid fa-magnifying-glass"></i> Tìm kiếm  nhân viên
                    </p>
                    <h3 className="center">Loading...</h3>
                </div>)
                :
                (<div className="user-request column">
                    <p className="center">
                        <i className="fa-solid fa-magnifying-glass"></i> Tìm kiếm  nhân viên
                    </p>
                    <h3> full name</h3>
                    <input
                        type="text"
                        name="fullName"
                        placeholder="Please type full name"
                        value={formData.fullName}
                        onChange={handleInputChange}
                    />

                    <h3> Ngày sinh từ</h3>
                    <input
                        type="date"
                        name="birthDayFrom"
                        placeholder="Birth Day From"
                        value={formData.birthDayFrom}
                        onChange={handleInputChange}
                    />

                    <h3> Ngày sinh đến</h3>
                    <input
                        type="date"
                        name="birthDayTo"
                        placeholder="Birth Day To"
                        value={formData.birthDayTo}
                        onChange={handleInputChange}
                    />
                    <h3> giới tính </h3>
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
                    <input
                        type="text"
                        name="phoneNumber"
                        placeholder="Phone Number"
                        value={formData.phoneNumber}
                        onChange={handleInputChange}
                    />


                    <h3>Bộ phận làm việc </h3>
                    <select
                        name="departmentId"
                        value={formData.departmentId}
                        onChange={handleInputChange}
                    >
                        <option value="">Chọn bộ phận</option>
                        {dataDepartments && dataDepartments.map((d) => (
                            <option key={d.id} value={d.id}>
                                {d.name}
                            </option>
                        ))}
                    </select>

                    <button className="btn-submit" onClick={handleReset}>
                        Reset  <i className="fa-solid fa-rotate-right"></i>
                    </button>
                    <button className="btn-submit" onClick={handleFind}>
                        Find   <i className="fa-solid fa-magnifying-glass"></i>
                    </button>
                </div>
                )}

            <div className="modal">
                {dataEmployees && (dataEmployees.length > 0 ? (
                    dataEmployees.map((employee) => (
                        <div key={employee.id} className="modal-user">
                            <div className="avatar-area">
                                <img src={employee.avatar} className="avatar" alt="Avatar" />
                            </div>
                            <span>ID: {employee.id}</span>
                            <span>Name: {employee.fullName}</span>
                            <span>Date of Birth: {formatDate(employee.birthDay)}</span>
                            <span>Gender: {employee.gender}</span>
                            <span>Salary: {formatNumber(employee.salary)}</span>
                            <span>Phone Number: {employee.phoneNumber}</span>
                            <span>Department: {employee.department ? employee.department.name : "Loading..."}</span>
                        </div>
                    ))
                ) : (
                    <h4 className="center">Không có khứa nào...</h4>
                ))}
            </div>

            <ToastContainer />
        </div>
    )
}

export default FindEmployees
