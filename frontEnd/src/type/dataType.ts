export interface Employee {
    fullName: string
    birthDay: string
    gender: string
    salary: number
    phoneNumber: string
    departmentId : number
    avatar: string
}

export interface FetchEmployee {
    id: number,
    fullName: string
    birthDay: string
    gender: string
    salary: number
    phoneNumber: string
    department: { id: number, name: string }
    avatar: string
}

export interface DepartmentInterface {
    name: string
}

export interface FetchDepartment {
    id: number
    name: string
}

export interface FindEmployee {
    funName: string
    birthDayFrom: string
    birthDayTo: string
    gender: string
    salaryValue: string
    phoneNumber: string
    departmentId: number
}
