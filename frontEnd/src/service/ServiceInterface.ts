export default interface ServiceInterface {
    fetchData(id: number): Promise<any>
    createData(onSuccess: (message: string) => void, onError: (message: string) => void): Promise<any>
    deleteData(id: number): Promise<any>
    updateData(id: number): Promise<any>
}