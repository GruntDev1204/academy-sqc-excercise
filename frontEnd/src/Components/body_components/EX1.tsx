import axios from "axios"
import { useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"


const taskDescription: string =
    `
  Xây dựng một API GET trong Spring Boot với đường dẫn /greeting. 
API này sẽ nhận tham số name từ URL (không bắt buộc) và trả về thông điệp chào mừng tương ứng.
Input: Tham số name (tuỳ chọn) được truyền qua URL. Nếu không có tham số, giá trị mặc định sẽ là rỗng.
Output:
- Nếu tham số name được cung cấp, API trả về chuỗi: "Hello + name!!!".
- Nếu không có tham số name, API trả về chuỗi: "Hello!!!".
`


const EX1: React.FC = () => {

   

    const [name, setName] = useState("")

    const [apiRes , setApiRes] = useState("")

    const [apiStatus , SetApiStatus] = useState("")


    // Hàm để gọi API và hiển thị thông báo toast
    const greetingFromApi = async (): Promise<void> => {
        try {
            // Gọi API greeting với tham số name nếu có
            // console.log(`http://localhost:8080/greeting/${name ? name : ''}`)

            const res = await axios.get(`http://localhost:8080/greeting/${name ? name : ''}`)

            // Hiển thị thông báo thành công với kết quả API trả về
            toast.success("gọi Api từ Spring boot thành công")
            setApiRes(res.data.message)
            SetApiStatus(res.data.status)

            
        } catch (error : any) {
            // Hiển thị thông báo lỗi nếu có lỗi xảy ra trong quá trình gọi API
            toast.error(error.response?.data?.message || "Có lỗi xảy ra khi gọi API");
        }

        setName("")
    }


    return (
        <div className="bai-tap">
            <span>Đề bài tập 1:  {taskDescription}</span>

            <span>User request :</span>

            <div className="user-request">
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="please try type your name"
                />

                <button className="btn-submit" onClick={greetingFromApi}>Check</button>
            </div>


            <span>Api response [message : {apiRes} , status : {apiStatus}  ]  </span>

            <ToastContainer />
        </div>
    )
}

export default EX1