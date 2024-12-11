import { useEffect, useState } from "react"
import { toast } from "react-toastify"
import type { FetchTypeMKP, ITypeMKP } from "../../../type/DataType"
import { TypeMarketPlaceService } from "../../../service/impl/TypeMarketPlaceService"

const TypeMKP: React.FC = () => {
    const dfType: ITypeMKP = {
        name: ""
    }
    const [type, setType] = useState<FetchTypeMKP[] | null>(null)

    const [selectedType, setSelectedType] = useState<FetchTypeMKP | null>(null)
    const [openDetail, setOpenDetail] = useState<boolean>(false)
    const [isUpdate, setIsUpdate] = useState<boolean>(false)

    const [updateType, setUpdateType] = useState<ITypeMKP>(dfType)
    const [newType, setNewType] = useState<ITypeMKP>(dfType)

    const newObjectDeparment = (data: any = null): TypeMarketPlaceService => {
        if (data === null) return new TypeMarketPlaceService()
        else return new TypeMarketPlaceService(data)
    }
    const typeService = newObjectDeparment(null)

    useEffect(() => {
        fetchType()
    }, [])

    const fetchType = async () => {
        const dataD = await typeService.fetchData(-1)
        if (dataD && dataD.data) {
            setType(dataD.data)
        } else {
            setType(null)
        }
    }


    const createType = async () => {

        if (!newType.name) {
            toast.error("vui lòng ko bỏ trống tên bộ phận!")
            return
        }
        const typeService = newObjectDeparment(newType)
        await typeService.createData(
            (message: string) => {
                alert(message)
                fetchType()
                setNewType(dfType)
            },
            (message: string) => toast.error(message)
        )

    }

    const deleteType = async (id: number) => {
        if (window.confirm("Do you want to delete this department? ID = " + id)) {
            const dl = await typeService.deleteData(id)
            alert(dl)
            fetchType()
        }
    }

    const viewDetailType = async (id: number, openDetail: boolean = true) => {
        if (openDetail) {
            setOpenDetail(true)
        }

        const detail = await typeService.fetchData(id)
        setSelectedType(detail.data)
        return detail.data
    }

    const openUpdateType = async (id: number) => {
        setIsUpdate(true)
        const dataType = await viewDetailType(id, false);
        setUpdateType(dataType)
    }

    const uppdateType = async () => {
        if (!selectedType) return

        const update = newObjectDeparment(updateType)
        const data = await update.updateData(selectedType.id)
        await fetchType()
        setUpdateType(dfType)
        setIsUpdate(false)
        alert(data)

    }

    return (
        <div className="bai-tap">
            {/* Modal chi tiết bộ phận */}
            {openDetail && selectedType && (
                <div className="independence-modal" key={selectedType.id}>
                    <span>ID: {selectedType.id}</span>
                    <span>Name: {selectedType.name}</span>


                    <div className="area-action">
                        <button className="btn-submit delete" onClick={() => {
                            setOpenDetail(false)
                            setSelectedType(null)
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
                        <span>Cập nhật thông tin mới cho kiểu mặt bằng </span>
                        <input
                            type="text"
                            name="name"
                            value={updateType.name}
                            onChange={(e) => setUpdateType({ ...updateType, name: e.target.value })}
                            placeholder="Please type full name"
                        />
                        <div className="area-action">
                            <button className="btn-submit" onClick={() => uppdateType()}>
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
                <i className="fa-solid fa-house"></i> Type Market Place Manager
            </p>

            {type === null ? (<div className="user-request column"> <h3 className="center">Loading...</h3></div>) : (
                <div className="user-request column">
                    <input
                        type="text"
                        name="name"
                        placeholder="Edit department name"
                        value={newType.name}
                        onChange={(e) => setNewType({ ...newType, name: e.target.value })}
                    />

                    <div className="flex">
                        <button className="btn-submit" onClick={() => createType()}>Create</button>
                    </div>

                </div>
            )}
            {type && (
                <p className="center">
                    <i className="fa-solid fa-building-user"></i> Type market place Data
                </p>
            )}
            <div className="modal">
                {type && type.length > 0 ? (
                    <table className="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name type</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {type.map((d) => (
                                <tr key={d.id}>
                                    <td>{d.id}</td>
                                    <td>{d.name}</td>
                                    <td>
                                        <button
                                            className="btn-submit"
                                            onClick={() => viewDetailType(d.id)}
                                        >
                                            View <i className="fa-solid fa-eye"></i>
                                        </button>
                                        <button
                                            className="btn-submit"
                                            onClick={() => openUpdateType(d.id)}
                                        >
                                            Edit <i className="fa-solid fa-pencil"></i>
                                        </button>
                                        <button
                                            className="btn-submit"
                                            onClick={() => deleteType(d.id)}
                                        >
                                            Delete <i className="fa-solid fa-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No type market place available.</p>
                )}
            </div>

        </div>
    )

}

export default TypeMKP

