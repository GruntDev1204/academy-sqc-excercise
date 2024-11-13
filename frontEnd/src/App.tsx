import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './Components/Header'
import Buoihoc1 from './Components/body_components/Buoihoc1'
import Body from './Components/Body'

function App() {
  return (
    <div className='container'>

      <Router>
        <Header/>
        <Routes>
          <Route path="/" element={<Body />} >
           <Route path="buoi-hoc-1" element={<Buoihoc1 />} />
          </Route>
        </Routes>
      </Router>
    </div>


  )
}

export default App
