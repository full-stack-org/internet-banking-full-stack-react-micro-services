import "bootstrap/dist/css/bootstrap.min.css";
import "react-datepicker/dist/react-datepicker.css";
import "./components/common/css/common.css";
import Header from "./components/common/Header";
import Footer from "./components/common/Footer";
import Routing from "./components/router/Routing";

function App() {
 
  return ( 
    <div>
      <Header />
      <Routing />
      <Footer />
    </div>
  );
}

export default App;
