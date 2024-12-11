//employee
export interface IEmployee {
    fullName: string
    birthDay: string
    gender: string
    salary: number
    phoneNumber: string
    departmentId: number
    avatar: string
}
export interface FetchEmployee {
    id: number,
    fullName: string
    birthDay: string
    gender: string
    salary: number
    phoneNumber: string
    avatar: string
    department: FetchDepartment
}
export interface FindEmployee {
    fullName: string
    birthDayFrom: string
    birthDayTo: string
    gender: string
    salaryValue: string
    phoneNumber: string
    departmentId: number
}

//deparment
export interface FetchDepartment {
    id: number
    name: string
}
export interface IDepartment {
    name: string
}

//type marketplace
export interface ITypeMKP {
    name: string
}
export interface FetchTypeMKP {
    id: number
    name: string
}

// marketplace
export interface IMarketPlace {
    name: string
    address: string
    area: number
    rentPrice: number
    rentDate: string
    description: string
    avatar: string
    typeMarketPlaceId: number
}
export interface fetchMarketPlace {
    id: number
    name: string
    address: string
    area: number
    rentPrice: number
    rentDate: string
    description: string
    avatar: string
    typeMarketPlace: FetchTypeMKP
}
export interface FindMkp {
    name: string
    address: string
    typeId: number
    rentPrice: string
    area: number
    minStartDate: string
    maxStartDate: string
}
