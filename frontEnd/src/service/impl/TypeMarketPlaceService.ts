import axios from "axios";
import { ITypeMKP } from "../../type/DataType";
import ServiceInterface from "../ServiceInterface";
import { toast } from "react-toastify";

export class TypeMarketPlaceService implements ServiceInterface {
    private typeMKP: ITypeMKP = {
        name: ""
    }

    private api: string = "http://localhost:8080/type-market-places"

    constructor(initData?: Partial<ITypeMKP>) {
        if (initData) {
            this.typeMKP = { ...this.typeMKP, ...initData }
        }
    }

    get(field: keyof typeof this.typeMKP) {
        return this.typeMKP[field]
    }
    set<K extends keyof typeof this.typeMKP>(field: K, value: typeof this.typeMKP[K]) {
        this.typeMKP[field] = value
    }

    public fetchData = async (id: number): Promise<any> => {
        const url = (id >= 0 && !isNaN(id)) ? `${this.api}/${id}` : this.api

        try {
            const res = await axios.get(url)
            return res.data
        } catch (error) {
            console.error("Error fetching type of marketplaces:", error)
            return []
        }

    }

    public createData = async (onSuccess: (message: string) => void, onError: (message: string) => void): Promise<any> => {

        const data = {
            name: this.get("name")
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
                toast.error(data.message || "Lỗi khi tạo bộ phận!")

                if (data.data) {
                    Object.keys(data.data).forEach((field) => {
                        toast.error(`${data.data[field]}`)
                    })
                }

            } else {

                toast.error("Không thể kết nối đến máy chủ!")
            }
            console.error("Error creating type of marketplaces:", error)
            return []
        }
    }

    public deleteData = async (id: number): Promise<any> => {
        const res = await axios.delete(this.api + `/${id}`)
        return res.data.message
    }

    public updateData = async (id: number): Promise<any> => {
        const data = {
            name: this.get('name'),
        }
        const res = await axios.put(this.api + `/${id}`, data)
        return res.data.message

    }
}