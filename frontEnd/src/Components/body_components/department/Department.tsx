import axios from "axios"
import { useEffect, useState } from "react"
import { toast } from "react-toastify"
import { DepartmentInterface, FetchDepartment } from "../../../type/dataType"




const Department: React.FC = () => {
    const [department, setDepartment] = useState<FetchDepartment[]>([])
    const [seletedDepartment, setSelectedDepartment] = useState<FetchDepartment | null>(null)
    const [openDetail, setOpenDetail] = useState<boolean>(false)
    const [isUpdate, setIsUpdate] = useState<boolean>(false)

    const [updateDepartment, setUpdateDepartment] = useState<DepartmentInterface>({
        name: ""
    })


    const [newDepartment, setNewDepartment] = useState<DepartmentInterface>({
        name: ""
    })

    useEffect(() => {
        fetchDepartments()
    }, [])

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

    const createDepartments = () => {
        if (!newDepartment.name) {
            toast.error("thiếu thông tin!")
            return
        }
        axios
            .post("http://localhost:8080/departments", newDepartment)
            .then((response) => {

                toast.success(response.data.message)

                fetchDepartments()

                setNewDepartment({
                    name: ""
                })

                console.log(response.data.data)
            })
            .catch((error) => console.error("Error fetching employees:", error))
    }


    const deleteDepartment = (id: number) => {

        if (window.confirm("Do you want to delete this department? ID = " + id)) {
            axios
                .delete("http://localhost:8080/departments/" + id)
                .then((response) => {

                    toast.success(response.data.message)

                    fetchDepartments()

                    console.log(response.data.data)
                })
                .catch((error) => console.error("Error fetching employees:", error))
        }

    }

    const getDepartmentById = (id: number) => {
        setOpenDetail(true)
        axios
            .get("http://localhost:8080/departments/" + id)
            .then((response) => {

                setSelectedDepartment(response.data.data)
                console.log(response.data.message)
            })
            .catch((error) => console.error("Error fetching employees:", error))
    }


    const openUpdateDepartment = (id: number) => {
        setIsUpdate(true)
        axios
            .get("http://localhost:8080/departments/" + id)
            .then((response) => {
                setSelectedDepartment(response.data.data)
                setUpdateDepartment(response.data.data)
                console.log(response.data.message)
            })
            .catch((error) => console.error("Error fetching employees:", error))
    }


    const updateDepartments = () => {
        if (!seletedDepartment) return
        axios
            .put(`http://localhost:8080/departments/${seletedDepartment.id}`, updateDepartment)
            .then((res) => {
                console.log(res.data.data)
                console.log(res.data.message)
                toast.success("Cập nhật bộ phận thành công!")
                fetchDepartments()

                // Reset lại dữ liệu
                setUpdateDepartment({
                    name: ""
                })
                setIsUpdate(false)
            })
            .catch((error) => {
                toast.error("Lỗi khi cập nhật bộ phận!")
                console.error("Error updating employee:", error)
            })
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
                            onChange={(e) => setUpdateDepartment({ ...newDepartment, name: e.target.value })}
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
            <p className="center">
                <i className="fa-solid fa-building-user"></i> Departments Data
            </p>

            <div className="modal">

                {department.map((d) => (
                    <div className="modal-user" key={d.id} >
                        <span>ID: {d.id}</span>
                        <span>Name Department : {d.name} </span>
                        <span>View :   <button className="btn-submit" onClick={() => getDepartmentById(d.id)} >View <i className="fa-solid fa-eye"></i> </button> </span>
                        <span>Edit : <button className="btn-submit" onClick={() => openUpdateDepartment(d.id)} >Edit <i className="fa-solid fa-pencil"></i> </button>  </span>
                        <span>Delete : <button className="btn-submit" onClick={() => deleteDepartment(d.id)}>Delete <i className="fa-solid fa-trash"></i> </button>  </span>

                    </div>

                ))}

            </div>

        </div>
    )

}

export default Department