import React from "react"
import { toast } from "react-toastify"
import { IMarketPlace, FetchTypeMKP } from "../../../type/DataType"

interface UpdateMKP {
    isOpen: boolean
    marketPlace: IMarketPlace
    type: FetchTypeMKP[] | null
    onClose: () => void
    onSave: (updateMKP: IMarketPlace) => void
    onChange: (name: string, value: string | number) => void
}

const UpdateMKPModal: React.FC<UpdateMKP> = ({
    isOpen,
    marketPlace,
    type,
    onClose,
    onSave,
    onChange,
}) => {

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target as HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
        const updatedValue: any = name === "typeMarketPlaceId" ? (value === "0" ? null : Number(value)) : value
        onChange(name, updatedValue)
    }

    const handleSave = () => {
        if (!marketPlace.name || !marketPlace.address || !marketPlace.area || !marketPlace.rentPrice || !marketPlace.rentDate) {
            toast.error("Vui lòng điền đầy đủ thông tin!")
            return
        }
        onSave(marketPlace)
        onClose()
    }

    if (!isOpen) return null

    return (
        <div className="independence-modal">
            <div className="user-request column">
                <span>Cập nhật thông tin mới mặt bằng</span>

                <h3> Tên mặt bằng </h3>
                <input
                    type="text"
                    name="name"
                    value={marketPlace.name}
                    onChange={handleChange}
                    placeholder="Please type full name"
                />

                <h3> Địa chỉ </h3>
                <input
                    type="text"
                    name="address"
                    value={marketPlace.address}
                    onChange={handleChange}
                />

                <h3> Ngày cho thuê </h3>
                <input
                    type="date"
                    name="rentDate"
                    value={marketPlace.rentDate}
                    onChange={handleChange}
                    placeholder="Please type salary"
                />

                <h3> Diện tích </h3>
                <input
                    type="number"
                    name="area"
                    value={marketPlace.area}
                    onChange={handleChange}
                    placeholder="Please type the phone"
                />

                <h3> Giá thuê </h3>
                <input
                    type="number"
                    name="rentPrice"
                    value={marketPlace.rentPrice}
                    onChange={handleChange}
                    placeholder="Please type the rent price "
                />

                <h3>Mô tả chi tiết (ko bắt buộc)</h3>
                <textarea className="text-form" name="description" value={marketPlace.description} rows={5} onChange={handleChange}>
                </textarea>

                <select
                    name="typeMarketPlaceId"
                    value={marketPlace.typeMarketPlaceId}
                    onChange={handleChange}
                >
                    {type === null ? (
                        <option value={0}>Loading...</option>
                    ) : (
                        <>
                            <option value={0}>Chọn loại mặt bằng</option>
                            {type.map((d) => (
                                <option key={d.id} value={d.id}>
                                    {d.name}
                                </option>
                            ))}
                        </>
                    )}
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

export default UpdateMKPModal
