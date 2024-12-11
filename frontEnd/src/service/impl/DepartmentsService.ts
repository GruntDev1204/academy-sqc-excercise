import axios from "axios"
import { toast } from "react-toastify"
import ServiceInterface from "../ServiceInterface"
import { IDepartment } from "../../type/DataType"


export class DepartmentService implements ServiceInterface {

    private department: IDepartment = {
        name: ""
    }
    get(field: keyof typeof this.department) {
        return this.department[field]
    }

    set<K extends keyof typeof this.department>(field: K, value: typeof this.department[K]) {
        this.department[field] = value
    }
    private api: string = "http://localhost:8080/departments"

    constructor(initData?: Partial<IDepartment>) {
        if (initData) {
            this.department = { ...this.department, ...initData }
        }
    }

    public fetchData = async (id: number , index : number | null = null , size : number | null = null ): Promise<any> => {
        const url = `${this.api}${(id >= 0 && !isNaN(id)) ? `/${id}` : ''}${index !== null ? `?index=${index}` : ''}${size !== null ? `?size=${size}` : ''}`

        try {
            const res = await axios.get(url)
            return res.data
        } catch (error) {
            console.error("Error fetching departments:", error)
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

            console.error("Error creating department:", error)

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