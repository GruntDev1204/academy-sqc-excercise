import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './Components/Header'
import Body from './Components/Body'
import Buoihoc1 from './Components/body_components/buoi1/Buoihoc1';
import Buoihoc2 from './Components/body_components/Buoihoc2';

function App() {
  return (
    <div className='container'>

      <Router>
        <Header/>
        <Routes>
          <Route path="/" element={<Body />} >
           <Route path="buoi-hoc-1" element={<Buoihoc1 />} />
           <Route path="buoi-hoc-2" element={<Buoihoc2 />} />

          </Route>
        </Routes>
      </Router>
    </div>


  )
}

export default App
