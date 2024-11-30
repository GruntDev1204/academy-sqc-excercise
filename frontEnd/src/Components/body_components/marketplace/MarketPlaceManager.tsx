import { useEffect, useState } from "react"
import { fetchMarketPlace, FetchTypeMKP, IMarketPlace } from "../../../type/DataType"
import MarketPlaceService from "../../../service/impl/MarketPlaceService"
import { TypeMarketPlaceService } from "../../../service/impl/TypeMarketPlaceService"
import { toast, ToastContainer } from "react-toastify"
import { uploadAvatar, uploadAvatarM } from "../../../config/upload"
import { formatDate, formatNumber } from "../../../service/Format"
import UpdateMKPModal from "./UpdateMKP"

const MarketPlaceManager: React.FC = () => {
    const dfMkp: IMarketPlace = {
        name: "",
        address: "",
        area: 0,
        rentPrice: 0,
        rentDate: "",
        description: "",
        avatar: "",
        typeMarketPlaceId: 0
    }

    const newObjectMkp = (data: any = null): MarketPlaceService => {
        if (data === null) {
            return new MarketPlaceService()
        } else {
            return new MarketPlaceService(data)
        }
    }

    const clearInput = (caseInput: number) => {
        switch (caseInput) {
            case 1:
                setUpdateMKP(dfMkp)
                break
            case 2:
                setNewMKP(dfMkp)
                break
            default:
                alert("Invalid  case of input!")
                break
        }

    }
    const MkpS = newObjectMkp(null)
    const TmkpS = new TypeMarketPlaceService()
    const [findId, setId] = useState<number>(0)

    //post
    const [newMKP, setNewMKP] = useState<IMarketPlace>(dfMkp)
    const [updateMKP, setUpdateMKP] = useState<IMarketPlace>(dfMkp)


    //upload avatar
    const [openAvatar, setOpenAvatar] = useState<boolean>(false)
    const [selectedFile, setSelectedFile] = useState<File | null>(null)

    //fetch
    const [typeMKPs, setTypeMKPs] = useState<FetchTypeMKP[] | null>(null)
    const [marketPlaces, setMarketPlaces] = useState<fetchMarketPlace[] | null>(null)
    const [selectedMKP, setSelectedMKP] = useState<fetchMarketPlace | null>(null)
    const [viewDetail, setViewDetail] = useState<boolean>(false)

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value, files } = e.target as HTMLInputElement

        if (name === 'avatar' && files && files.length > 0) {
            const file = files[0]
            setSelectedFile(file)
        }
        else if (name === "typeMarketPlaceId") {
            const typeMarketPlaceIdValue: any = value === "0" ? null : Number(value)
            setNewMKP((prev) => ({
                ...prev,
                typeMarketPlaceId: typeMarketPlaceIdValue,
            }))
        }
        else if (name === "name" || name === "rentDate" || name === "area" || name === "rentPrice" || name === "description" || name === "address") {
            setNewMKP((prev) => ({
                ...prev,
                [name]: value,
            }))
        }
    }

    const checkData = () => {
        return (!newMKP.name || !newMKP.address || !newMKP.area || !newMKP.rentPrice || !newMKP.rentDate)
    }

    useEffect(() => {
        fetchMkp()
        fetchTypeMkp()
    }, [])

    const fetchTypeMkp = async () => {
        const dataD = await TmkpS.fetchData(-1)
        setTypeMKPs(dataD.data)
    }

    const fetchMkp = async () => {
        const dataD = await MkpS.fetchData(-1)
        if (dataD && dataD.data) {
            setMarketPlaces(dataD.data)

        } else {
            setMarketPlaces(null)
        }
    }

    const viewDetailMKP = async (id: number, openDetail: boolean = true) => {
        if (openDetail) setViewDetail(true)

        const data = await MkpS.fetchData(id)
        setSelectedMKP(data.data)
        return data.data
    }

    const handleUploadAvatar = async () => {
        if (!selectedFile) {
            toast.error('Chưa chọn ảnh!')
            return
        }

        const uploadedAvatarUrl = await uploadAvatarM(selectedFile)
        if (uploadedAvatarUrl) {
            setNewMKP((prev) => ({
                ...prev,
                avatar: uploadedAvatarUrl,
            }))
            setSelectedFile(null)
        }
    }
    const handleCreateMarketPlace = async () => {
        if (checkData()) {
            toast.error("Vui lòng điền đầy đủ thông tin!")
            return
        }

        if (selectedFile) {
            const avatarUrl = await uploadAvatar(selectedFile)
            newMKP.avatar = avatarUrl
        }

        const createM = newObjectMkp(newMKP)

        await createM.createData(
            (message: string) => {
                alert(message)
                fetchMkp()
                clearInput(2)
                setOpenAvatar(false)
            },
            (message: string) => toast.error(message)
        )
    }

    const handleDeleteMarketPlace = async (id: number) => {
        if (window.confirm(" bạn muốn thủ tiêu cái mặt bằng này chứ ?")) {
            const dele = await MkpS.deleteData(id)
            alert(dele)
            fetchMkp()
        }
    }

    const [updateAvatar, setUpdateAvatar] = useState<boolean>(false)
    const handleOpenFormUpdateAvatar = async (id: number) => {
        setUpdateAvatar(true)

        const data = await viewDetailMKP(id, false)
        const mappedData = {
            ...data,
            typeMarketPlaceId: data.typeMarketPlace?.id || 0,
        }

        setUpdateMKP(mappedData)
    }
    const handleReuploadAvatar = async () => {
        if (!selectedFile) {
            toast.error('Chưa chọn ảnh!')
            return
        }

        try {
            const uploadedAvatarUrl = await uploadAvatar(selectedFile)
            if (uploadedAvatarUrl) {
                setUpdateMKP((prev) => {
                    const updateMP = { ...prev, avatar: uploadedAvatarUrl }
                    return updateMP
                })
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
    const handleUpdateAvatar = async () => {
        if (!selectedMKP) return

        try {
            const updateM = newObjectMkp(updateMKP)

            const data = await updateM.updateData(selectedMKP.id)
            await fetchMkp()
            clearInput(1)
            setUpdateAvatar(false)
            alert(data.message)
        } catch (error) {
            toast.error("Cập nhật avatar thất bại!")
        }
    }

    const [update, setUpdate] = useState<boolean>(false)
    const handleOpenFormUpdate = async (id: number) => {
        setUpdate(true)

        const data = await viewDetailMKP(id, false)
        const mappedData = {
            ...data,
            typeMarketPlaceId: data.typeMarketPlace?.id || 0,
        }
        setUpdateMKP(mappedData)
        setId(id)
    }
    const handleUpdateMKP = async (updatedData: IMarketPlace) => {
        if (!updatedData) return

        try {
            const updateE = newObjectMkp(updatedData)
            const data = await updateE.updateData(findId)
            await fetchMkp()

            clearInput(1)
            setUpdate(false)
            alert(data.message)
        } catch (error) {
            toast.error("Cập nhật thông tin thất bại!")
        }
    }

    return (
        <div className="bai-tap" >
            <p className="center">
                <i className="fas fa-house"></i> T-Marketplace
            </p>

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

            <UpdateMKPModal
                isOpen={update}
                marketPlace={updateMKP}
                type={typeMKPs}
                onClose={() => setUpdate(false)}
                onSave={(updatedMKP) => {
                    handleUpdateMKP(updatedMKP)
                }}

                onChange={(name, value) => {
                    setUpdateMKP((prev) => ({ ...prev, [name]: value }))
                }}
            />

            {viewDetail && selectedMKP && (
                <div className="independence-modal" key={selectedMKP.id}>
                    <div className="avatar-area" >  <img src={selectedMKP.avatar} className="avatar-square" /> </div>
                    <span>ID: {selectedMKP.id}</span>
                    <span>Name: {selectedMKP.name}</span>
                    <span>Area: {selectedMKP.area}</span>
                    <span>Rent Date : {formatDate(selectedMKP.rentDate)}</span>
                    <span>description: {selectedMKP.description}</span>
                    <span>rentPrice: {formatNumber(selectedMKP.rentPrice)}</span>
                    <span>
                        Type of MarketPlace: {selectedMKP.typeMarketPlace ? selectedMKP.typeMarketPlace.name : "Loading..."}
                    </span>


                    <div className="area-action">
                        <button className="btn-submit delete" onClick={() => {
                            setViewDetail(false)
                            setSelectedMKP(null)
                        }}>
                            Close <i className="fa-solid fa-x"></i>
                        </button>
                    </div>
                </div>
            )}

            {marketPlaces === null ?
                (<div className="user-request column">  <h3 className="center">Loading....</h3> </div>)
                :
                (
                    <div className="user-request column">
                        <span>Điền thông tin cho mặt bằng mới :</span>

                        <h3> Tên mặt bằng </h3>
                        <input
                            type="text"
                            name="name"
                            value={newMKP.name}
                            onChange={handleInputChange}
                            placeholder="Please type  name"
                        />

                        <h3> Địa chỉ </h3>
                        <input
                            type="text"
                            name="address"
                            value={newMKP.address}
                            onChange={handleInputChange}
                            placeholder="Please type  name"
                        />

                        <h3> Ngày cho thuê </h3>
                        <input
                            type="date"
                            name="rentDate"
                            value={newMKP.rentDate}
                            onChange={handleInputChange}
                            placeholder="Please type rentDate"
                        />

                        <h3> Diện tích </h3>
                        <input
                            type="number"
                            name="area"
                            value={newMKP.area}
                            onChange={handleInputChange}
                            placeholder="Please type salary"
                        />

                        <h3> Giá thuê </h3>
                        <input
                            type="number"
                            name="rentPrice"
                            value={newMKP.rentPrice}
                            onChange={handleInputChange}
                            placeholder="Please type the rent price "
                        />

                        <h3>Mô tả chi tiết (ko bắt buộc)</h3>
                        <textarea className="text-form" name="description" value={newMKP.description} rows={5} onChange={handleInputChange}>
                        </textarea>

                        <select
                            name="typeMarketPlaceId"
                            value={newMKP.typeMarketPlaceId}
                            onChange={handleInputChange}
                        >
                            <option value="0">Chọn loại mặt bằng</option>
                            {typeMKPs &&
                                typeMKPs.map((d) => (
                                    <option key={d.id} value={d.id}>
                                        {d.name}
                                    </option>
                                ))}
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
                            <button className="btn-large " onClick={() => setOpenAvatar((toggle) => !toggle)}>Upload a avatar for Marketplace <i className="fa-solid fa-camera-retro"></i> </button>
                            <button className="btn-submit" onClick={handleCreateMarketPlace}>Create</button>
                        </div>

                    </div>
                )
            }

            <div className="modal">
                {marketPlaces === null ? (
                    <></>
                ) : marketPlaces.length === 0 ? (
                    <p>No marketplace found...</p>
                ) : (
                    marketPlaces.map((m) => (
                        <div key={m.id} className="modal-user">
                            <div className="avatar-area">
                                <img src={m.avatar} className="avatar-square" alt="Avatar" />
                            </div>
                            <i
                                className="fas fa-pencil editt"
                                onClick={() => handleOpenFormUpdateAvatar(m.id)}
                            ></i>

                            <span>ID: {m.id}</span>
                            <span>Name: {m.name}</span>
                            <span>Address: {m.address}</span>
                            <span>Rent Date: {formatDate(m.rentDate)}</span>
                            <span>Area: {m.area}</span>
                            <span>Rent Price: {formatNumber(m.rentPrice)}</span>
                            <span>Description: {m.description ? m.description : "No description!"}</span>
                            <span>Type: {m.typeMarketPlace ? m.typeMarketPlace.name : "Loading..."}</span>

                            <div className="area-action">
                                <button
                                    className="btn-icon edit"
                                    onClick={() => handleOpenFormUpdate(m.id)}
                                >
                                    Edit <i className="fa-solid fa-pencil"></i>
                                </button>
                                <button
                                    className="btn-icon detail"
                                    onClick={() => viewDetailMKP(m.id)}
                                >
                                    View <i className="fa-solid fa-eye"></i>
                                </button>
                                <button
                                    className="btn-icon delete"
                                    onClick={() => handleDeleteMarketPlace(m.id)}
                                >
                                    Delete <i className="fa-solid fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    ))
                )}
            </div>

            <ToastContainer />
        </div>

    )
}

export default MarketPlaceManager