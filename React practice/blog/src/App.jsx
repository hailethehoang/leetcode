import { useState,useRef, useEffect } from 'react'
import './App.css'
import CountPage from './pages/CountPage'

const App = () => {
  const [isShown, setIsShown] = useState(false)
  const count = useRef(0)

  const handleToggle = () => {
    if (!isShown) {
      count.current += 1;
    }

    setIsShown(prev => !prev);
  };

  return (
   <> 
    <button onClick={handleToggle}>
      Toggle
    </button>
    <CountPage count={count.current} />
   
   </>
  )
}

export default App
