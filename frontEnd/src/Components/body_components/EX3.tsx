import axios from "axios"
import { useState } from "react"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"



const taskDescription: string =
    `"Xây dựng một API GET trong Spring Boot với đường dẫn /dictionary. API này sẽ nhận tham số word từ URL và tra cứu từ trong một từ điển tiếng Anh-Việt có sẵn.
Input: Tham số word (tuỳ chọn) được truyền qua URL. Tham số sẽ được xử lý để loại bỏ khoảng trắng dư thừa và chuyển thành chữ thường trước khi tra cứu.
Output:
Nếu từ có trong từ điển, API sẽ trả về bản dịch tiếng Việt với mã trạng thái 200 OK.
Nếu từ không có trong từ điển, API sẽ trả về thông báo "Không tìm thấy từ này trong từ điển." với mã trạng thái 404 Not Found."`

const EX3: React.FC = () => {

    const [word, setWord] = useState<string>("")
    
    const [result, setResult] = useState<string>("")



    const [apiStatus, setApiStatus] = useState<string>("")

   

    const dictionaryAPI = async (): Promise<void> => {

        if(word === "" ){
            toast.error("ko được bỏ trống từ cần điền")
            return
        }


        try {

            const res = await axios.get(`http://localhost:8080/dictionary/` + word)


            // Hiển thị thông báo thành công với kết quả API trả về
            toast.success("gọi Api từ Spring boot thành công")

            setApiStatus(res.data.status)
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

        setWord("")

    }



    return (
        <div className="bai-tap">
            <span>Đề bài tập 3:  {taskDescription}</span>

            <span>User request : </span>

            <div className="user-request column">

                <span>Nhập từ điển : </span>
                <input
                    type="text"
                    value={word}
                    onChange={(e) => setWord(e.target.value)}
                    placeholder="please  type some word"
                />

                <button className="btn-submit" onClick={dictionaryAPI}>Translate</button>
            </div>


            <span>Api response : status: {apiStatus}</span>

            <span>Kết quả biên dịch : {result} </span>



            <ToastContainer />
        </div>
    )
}

export default EX3