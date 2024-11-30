import { useEffect, useState } from "react"
import { ToastContainer } from "react-toastify"
import { FindMkp, fetchMarketPlace, FetchTypeMKP } from "../../../type/DataType"
import { formatDate, formatNumber } from "../../../service/Format"
import { TypeMarketPlaceService } from "../../../service/impl/TypeMarketPlaceService"
import MarketPlaceService from "../../../service/impl/MarketPlaceService"

const FindMKP: React.FC = () => {
    const typeS = new TypeMarketPlaceService()
    const mkpS = new MarketPlaceService()
    const dfFormData: FindMkp = {
        name: "",
        address: "",
        typeId: 0,
        rentPrice: "",
        area: 0,
        minStartDate: "",
        maxStartDate: "",
    }
    const [formData, setFormData] = useState<FindMkp>(dfFormData)

    const [dataMKP, setDataMKP] = useState<fetchMarketPlace[] | null>(null)

    const [typeData, setTypeData] = useState<FetchTypeMKP[]>([])

    useEffect(() => {
        fetchType()
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

        const dataSelect = await mkpS.fetchData(-1, validParams)
        if (dataSelect && dataSelect.data) setDataMKP(dataSelect.data)
        else setDataMKP(null)
    }

    const fetchType = async () => {
        const data = await typeS.fetchData(-1)
        setTypeData(data.data)
    }

    return (
        <div className="bai-tap">
            {dataMKP === null ? (
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
                    <h3>name</h3>
                    <input
                        type="text"
                        name="name"
                        placeholder="Please type full name"
                        value={formData.name}
                        onChange={handleInputChange}
                    />

                    <h3> tìm theo địa chỉ </h3>
                    <input
                        type="text"
                        name="address"
                        placeholder="Address"
                        value={formData.address}
                        onChange={handleInputChange}
                    />

                    <h3>Mức giá thuê </h3>
                    <select name="rentPrice" value={formData.rentPrice} onChange={handleInputChange}>
                        <option value="">Chọn mức lương</option>
                        <option value="under_2m">Dưới 2 triệu</option>
                        <option value="2_to_5m">2-5 triệu</option>
                        <option value="5_to_10m"> 5-10 triệu</option>
                        <option value="above_10m">Trên 10 triệu</option>
                    </select>

                    <h3> Diện tích </h3>
                    <input
                        type="number"
                        name="area"
                        placeholder="area"
                        value={formData.area}
                        onChange={handleInputChange}
                    />

                    <h3>Cho thuê từ</h3>
                    <input
                        type="text"
                        name="minStartDate"
                        placeholder="min rent date"
                        value={formData.minStartDate}
                        onChange={handleInputChange}
                    />

                    <h3>Cho thuê đến</h3>
                    <input
                        type="text"
                        name="maxStartDate"
                        placeholder="max rent date"
                        value={formData.maxStartDate}
                        onChange={handleInputChange}
                    />

                    <h3>Kiểu mặt bằng</h3>
                    <select
                        name="typeId"
                        value={formData.typeId}
                        onChange={handleInputChange}
                    >
                        <option value="">Chọn kiểu mặt bằng</option>
                        {typeData && typeData.map((d) => (
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
                {dataMKP && (dataMKP.length > 0 ? (
                    dataMKP.map((data) => (
                        <div key={data.id} className="modal-user">
                            <div className="avatar-area">
                                <img src={data.avatar} className="avatar-square" alt="Avatar" />
                            </div>
                            <span>ID: {data.id}</span>
                            <span>Name: {data.name}</span>
                            <span>rent date: {formatDate(data.rentDate)}</span>
                            <span>address: {data.address}</span>
                            <span>rent price: {formatNumber(data.rentPrice)}</span>
                            <span>description: {data.description}</span>
                            <span>typeMarketPlace: {data.typeMarketPlace ? data.typeMarketPlace.name : "Loading..."}</span>
                        </div>
                    ))
                ) : (
                    <h4 className="center">Không có cái nào...</h4>
                ))}
            </div>

            <ToastContainer />
        </div>
    )
}

export default FindMKP
