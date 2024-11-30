import axios from "axios";
import { IMarketPlace } from "../../type/DataType";
import { toast } from "react-toastify";

export default class MarketPlaceService {
    private mkpls: IMarketPlace = {
        name: "",
        address: "",
        area: 0,
        rentPrice: 0,
        rentDate: "",
        description: "",
        avatar: "",
        typeMarketPlaceId: 0
    }

    private api: string = "http://localhost:8080/market-places"

    get(field: keyof typeof this.mkpls) {
        return this.mkpls[field]
    }

    set<K extends keyof typeof this.mkpls>(field: K, value: typeof this.mkpls[K]) {
        this.mkpls[field] = value
    }

    constructor(initData?: Partial<IMarketPlace>) {
        if (initData) {
            this.mkpls = { ...this.mkpls, ...initData }
        }
    }

    public fetchData = async (id: number, params: Record<string, any> = {}): Promise<any> => {
        const url = id >= 0 && !isNaN(id) ? `${this.api}/${id}` : this.api

        try {
            const res = await axios.get(url, { params })
            return res.data
        } catch (error) {
            console.error("Error fetching employees:", error)
            return []
        }
    }

    public createData = async (onSuccess: (message: string) => void, onError: (message: string) => void): Promise<any> => {
        const typeMkpls = (this.get('typeMarketPlaceId') === 0 || this.get('typeMarketPlaceId') === null)
            ? null
            : { id: this.get('typeMarketPlaceId') }
        const data = {
            name: this.get('name'),
            address: this.get('address'),
            area: this.get('area'),
            rentPrice: this.get('rentPrice'),
            rentDate: this.get('rentDate'),
            description: this.get('description'),
            typeMarketPlace: typeMkpls,
            avatar: this.get('avatar')
        }

        try {
            const res = await axios.post(this.api, data)
            if (res.data && res.data.message) {
                onSuccess(res.data.message)
            } else {
                onError("Không có thông báo từ server")
            }
        } catch (error: unknown) {
            if (axios.isAxiosError(error) && error.response) {
                const { data } = error.response
                toast.error(data.message || "Lỗi khi tạo mặt bằng!")

                if (data.data) {
                    Object.keys(data.data).forEach((field) => {
                        toast.error(`${data.data[field]}`)
                    })
                }

            } else {

                toast.error("Không thể kết nối đến máy chủ!")
            }

            console.error("Error creating marketplace:", error)

            return []
        }
    }

    public deleteData = async (id: number) => {
        const res = await axios.delete(this.api + `/${id}`)
        return res.data.message
    }

    public updateData = async (id: number) => {
        const typeMkpls = (this.get('typeMarketPlaceId') === 0 || this.get('typeMarketPlaceId') === null)
            ? null
            : { id: this.get('typeMarketPlaceId') }
        const data = {
            name: this.get('name'),
            address: this.get('address'),
            area: this.get('area'),
            rentPrice: this.get('rentPrice'),
            rentDate: this.get('rentDate'),
            description: this.get('description'),
            typeMarketPlace: typeMkpls,
            avatar: this.get('avatar')
        }
        const res = await axios.put(this.api + `/${id}`, data)
        return res.data
    }

}