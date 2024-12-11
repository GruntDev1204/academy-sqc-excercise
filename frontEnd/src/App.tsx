import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Header from './components/Header'
import Body from './components/Body'
import Employee from './components/body_components/employee/Employee'
import Department from './components/body_components/department/Department'
import MarketPlace from './components/body_components/marketplace/MarketPlace'
import TypeMKP from './components/body_components/type_marketplace/TypeMKP'

function App() {
  return (
    <div className='container'>
      <Router>
        <Header />
        <Routes>
          <Route path="/" element={<Body />} >
            <Route path="employees" element={<Employee />} />
            <Route path="departments" element={<Department />} />
            <Route path="market-places" element={<MarketPlace />} />
            <Route path="type-market-places" element={<TypeMKP />} />
          </Route>
        </Routes>
      </Router>
    </div>
  )
}

export default App
