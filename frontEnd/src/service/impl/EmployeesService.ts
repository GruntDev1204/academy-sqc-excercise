import axios from "axios"
import { toast } from "react-toastify"
import { IEmployee } from "../../type/DataType"

export default class EmployeeService {
    private employee: IEmployee = {
        fullName: "",
        birthDay: "",
        gender: "",
        salary: 0,
        phoneNumber: "",
        departmentId: 0,
        avatar: ""
    }

    private api: string = "http://localhost:8080/employees"

    get(field: keyof typeof this.employee) {
        return this.employee[field]
    }

    set<K extends keyof typeof this.employee>(field: K, value: typeof this.employee[K]) {
        this.employee[field] = value
    }

    constructor(initData?: Partial<IEmployee>) {
        if (initData) {
            this.employee = { ...this.employee, ...initData }
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
        const department = (this.get('departmentId') === 0 || this.get('departmentId') === null)
            ? null
            : { id: this.get('departmentId') }
        const data = {
            fullName: this.get('fullName'),
            birthDay: this.get('birthDay'),
            gender: this.get('gender'),
            salary: this.get('salary'),
            phoneNumber: this.get('phoneNumber'),
            department: department,
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
                toast.error(data.message || "Lỗi khi tạo nhân viên!")

                if (data.data) {
                    Object.keys(data.data).forEach((field) => {
                        toast.error(`${data.data[field]}`)
                    })
                }

            } else {

                toast.error("Không thể kết nối đến máy chủ!")
            }

            console.error("Error creating employee:", error)

            return []
        }
    }

    public deleteData = async (id: number) => {
        const res = await axios.delete(this.api + `/${id}`)
        return res.data.message
    }

    public updateData = async (id: number) => {
        const department = (this.get('departmentId') === 0 || this.get('departmentId') === null)
            ? null
            : { id: this.get('departmentId') }
        const data = {
            fullName: this.get('fullName'),
            birthDay: this.get('birthDay'),
            gender: this.get('gender'),
            salary: this.get('salary'),
            phoneNumber: this.get('phoneNumber'),
            department: department,
            avatar: this.get('avatar')
        }
        const res = await axios.put(this.api + `/${id}`, data)
        return res.data

    }

}