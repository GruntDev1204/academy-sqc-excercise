import { useEffect, useState } from "react"
import { toast } from "react-toastify"
import type { IDepartment, FetchDepartment } from "../../../type/DataType"
import { DepartmentService } from "../../../service/impl/DepartmentsService"

const Department: React.FC = () => {
    const dafDepartMent: IDepartment = {
        name: ""
    }
    const [totalPages, setTotalPages] = useState<number>(0)
    const [selectedPage, setSelectedPage] = useState<number>(1)

    const [department, setDepartment] = useState<FetchDepartment[] | null>(null)

    const [seletedDepartment, setSelectedDepartment] = useState<FetchDepartment | null>(null)
    const [openDetail, setOpenDetail] = useState<boolean>(false)
    const [isUpdate, setIsUpdate] = useState<boolean>(false)

    const [updateDepartment, setUpdateDepartment] = useState<IDepartment>(dafDepartMent)
    const [newDepartment, setNewDepartment] = useState<IDepartment>(dafDepartMent)

    const newObjectDeparment = (data: any = null): DepartmentService => {
        if (data === null) return new DepartmentService()
        else return new DepartmentService(data)
    }
    const dS = newObjectDeparment(null)

    useEffect(() => {
        fetchDepartments()
    }, [selectedPage])

    const fetchDepartments = async () => {
        const dataD = await dS.fetchData(-1, selectedPage)
        if (dataD && dataD.data) {
            setDepartment(dataD.data)
            setTotalPages(dataD.pageDetail.total_pages)
        } else {
            setDepartment(null)
        }
    }

    const createDepartments = async () => {

        if (!newDepartment.name) {
            toast.error("vui lòng ko bỏ trống tên bộ phận!")
            return
        }
        const dS = newObjectDeparment(newDepartment)
        await dS.createData(
            (message: string) => {
                alert(message)
                fetchDepartments()
                setNewDepartment(dafDepartMent)
            },
            (message: string) => toast.error(message)
        )

    }

    const deleteDepartment = async (id: number) => {
        if (window.confirm("Do you want to delete this department? ID = " + id)) {
            const dl = await dS.deleteData(id)
            alert(dl)
            fetchDepartments()
        }
    }

    const getDepartmentById = async (id: number, openDetail: boolean = true) => {
        if (openDetail) {
            setOpenDetail(true)
        }

        const detail = await dS.fetchData(id)
        setSelectedDepartment(detail.data)
        return detail.data
    }

    const openUpdateDepartment = async (id: number) => {
        setIsUpdate(true)
        const department = await getDepartmentById(id, false)
        setUpdateDepartment(department)
    }

    const updateDepartments = async () => {
        if (!seletedDepartment) return

        const update = newObjectDeparment(updateDepartment)
        const data = await update.updateData(seletedDepartment.id)
        await fetchDepartments()
        setUpdateDepartment(dafDepartMent)
        setIsUpdate(false)
        alert(data)

    }

    return (
        <div className="bai-tap">
            {/* Modal chi tiết bộ phận */}
            {openDetail && seletedDepartment && (
                <div className="independence-modal" key={seletedDepartment.id}>
                    <span>ID: {seletedDepartment.id}</span>
                    <span>Name: {seletedDepartment.name}</span>


                    <div className="area-action">
                        <button className="btn-submit delete" onClick={() => {
                            setOpenDetail(false)
                            setSelectedDepartment(null)
                        }}>
                            Close <i className="fa-solid fa-x"></i>
                        </button>
                    </div>
                </div>
            )}

            {/* Modal cập nhật */}
            {isUpdate && (
                <div className="independence-modal">
                    <div className="user-request column">
                        <span>Cập nhật thông tin mới cho bộ phận  </span>
                        <input
                            type="text"
                            name="name"
                            value={updateDepartment.name}
                            onChange={(e) => setUpdateDepartment({ ...updateDepartment, name: e.target.value })}
                            placeholder="Please type full name"
                        />
                        <div className="area-action">
                            <button className="btn-submit" onClick={() => updateDepartments()}>
                                Update <i className="fa-solid fa-check"></i>
                            </button>

                            <button className="btn-submit cancel" onClick={() => setIsUpdate(false)}>
                                Cancel <i className="fa-solid fa-x"></i>
                            </button>
                        </div>

                    </div>
                </div>
            )}

            <p className="center">
                <i className="fa-solid fa-building-user"></i> Departments Manager
            </p>

            {department === null ? (<div className="user-request column"> <h3 className="center">Loading...</h3></div>) : (
                <div className="user-request column">
                    <input
                        type="text"
                        name="name"
                        placeholder="Edit department name"
                        value={newDepartment.name}
                        onChange={(e) => setNewDepartment({ ...newDepartment, name: e.target.value })}
                    />

                    <div className="flex">
                        <button className="btn-submit" onClick={() => createDepartments()}>Create</button>
                    </div>

                </div>
            )}

            {department && (
                <p className="center">
                    <i className="fa-solid fa-building-user"></i> Departments Data
                </p>
            )}
            <div className="modal">
                {department && department.length > 0 ? (
                    <table className="table">
                        <thead>
                            <tr>
                                <th> ID
                                    <select className="select-page" name="index" value={selectedPage} onChange={(e) => setSelectedPage(Number(e.target.value))}>
                                        <option value="">Chọn page</option>
                                        {Array.from({ length: totalPages }, (_, index) => (
                                            <option key={index + 1} value={index + 1}>
                                                {index + 1}
                                            </option>
                                        ))}
                                    </select>
                                </th>
                                <th>Name Department</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {department.map((d) => (
                                <tr key={d.id}>
                                    <td>{d.id}</td>
                                    <td>{d.name}</td>
                                    <td>
                                        <button
                                            className="btn-submit"
                                            onClick={() => getDepartmentById(d.id)}
                                        >
                                            View <i className="fa-solid fa-eye"></i>
                                        </button>
                                        <button
                                            className="btn-submit"
                                            onClick={() => openUpdateDepartment(d.id)}
                                        >
                                            Edit <i className="fa-solid fa-pencil"></i>
                                        </button>
                                        <button
                                            className="btn-submit"
                                            onClick={() => deleteDepartment(d.id)}
                                        >
                                            Delete <i className="fa-solid fa-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No departments available.</p>
                )}
            </div>

        </div>
    )

}

export default Department