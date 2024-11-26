import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Header from './Components/Header'
import Body from './Components/Body'
import Employee from './Components/body_components/employee/Employee'
import Department from './Components/body_components/department/Department'

function App() {
  return (
    <div className='container'>
      <Router>
        <Header/>
        <Routes>
          <Route path="/" element={<Body />} >
           <Route path="employees" element={<Employee />} />
           <Route path="departments" element={<Department />} />

          </Route>
        </Routes>
      </Router>
    </div>


  )
}

export default App
