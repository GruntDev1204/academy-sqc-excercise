//format tiền sang VNĐ
export const formatNumber = (number: number): string => {
    return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
    }).format(number)
}

//formate date time
export const formatDate = (datetime: string): string => {
    const input = datetime
    const dateObj = new Date(input)
    const year = dateObj.getFullYear()
    const month = (dateObj.getMonth() + 1).toString().padStart(2, '0')
    const date = dateObj.getDate().toString().padStart(2, '0')
    const result = `${date}/${month}/${year}`
    return result
}

export const formatDateTime = (datetime: string): string => {
    const input = datetime  
    const dateObj = new Date(input) 
    const year = dateObj.getFullYear()
    const month = (dateObj.getMonth() + 1).toString().padStart(2, '0')
    const date = dateObj.getDate().toString().padStart(2, '0')
    const hours = dateObj.getHours().toString().padStart(2, '0')
    return `${date}/${month}/${year} ${hours}:00`
}

