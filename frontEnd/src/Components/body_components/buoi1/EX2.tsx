import axios from "axios"
import { useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"



const taskDescription: string = "Xây dựng một API GET trong Spring Boot với đường dẫn /calculator. API này sẽ nhận ba tham số: firstNumber, secondNumber, và operator từ URL. API này sẽ thực hiện phép toán dựa trên các tham số và trả về kết quả."

const EX2: React.FC = () => {

    const [firstNumber, setFirstNumber] = useState<string>("")
    const [secondNumber, setSecondNumber] = useState<string>("")
    const [operation, setOperation] = useState<string>("")

    const [result, setResult] = useState<string>("")



    const [apiRes, setApiRes] = useState<string>("")
    const [apiStatus, setApiStatus] = useState<string>("")

    const payLoad = {
        number1: firstNumber,
        number2: secondNumber,
        operator: operation,
    }


    const calculatorAPI = async (): Promise<void> => {

        try {

            const res = await axios.post(`http://localhost:8080/caculator`, payLoad)

            // Hiển thị thông báo thành công với kết quả API trả về
            toast.success("gọi Api từ Spring boot thành công")
            // toast.success(res.data.message)

            setApiStatus(res.data.status)
            setApiRes(res.data.message)
            setResult(res.data.result)




        } catch (error: any) {
            if (error.response) {
                // Khi nhận được phản hồi lỗi từ server (mã lỗi 4xx, 5xx)
                toast.error(error.response.data.message || "Có lỗi xảy ra khi gọi API.");
            } else if (error.request) {
                // Khi không nhận được phản hồi từ server
                toast.error("Không nhận được phản hồi từ server.");
            } else {
                // Các lỗi khác liên quan đến cấu hình của request
                toast.error("Có lỗi trong quá trình gọi API.");
            }
        }



    }



    return (
        <div className="bai-tap">
            <span>Đề bài tập 2:  {taskDescription}</span>

            <span>User request : </span>

            <div className="user-request column">

                <span>Số thứ 1 : </span>
                <input
                    type="number"
                    value={firstNumber}
                    onChange={(e) => setFirstNumber(e.target.value)}
                    placeholder="please  type your first number"
                />

                <span>Số thứ 2 : </span>
                <input
                    type="number"
                    value={secondNumber}
                    onChange={(e) => setSecondNumber(e.target.value)}
                    placeholder="please  type your second number"
                />

                <span>Chọn phép tính: </span>
                <select
                    value={operation}
                    onChange={(e) => setOperation(e.target.value)} // Cập nhật phép tính khi người dùng chọn
                >
                    <option value="">Chọn phép tính</option>
                    <option value="+">+</option>
                    <option value="-">-</option>
                    <option value="*">*</option>
                    <option value="/">/</option>
                </select>

                <button className="btn-submit" onClick={calculatorAPI}>Caculate</button>
            </div>


            <span>Api response : message: {apiRes}, status: {apiStatus}</span>

            <span>Kết quả phép toán : {result} </span>



            <ToastContainer />
        </div>
    )
}

export default EX2