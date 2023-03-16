import { useState } from "react";
import axios from "axios";
import "./styles.css";

function App() {
  const [inputs, setInputs] = useState(["", ""]);
  const [data, setData] = useState("");

  const handleInput = (e, index) => {
    const newInputs = [...inputs];
    newInputs[index] = e.target.value;
    setInputs(newInputs);
  };

  const handleAddInput = () => {
    setInputs([...inputs, ""]);
  };

  const handleRemoveInput = (indexToRemove) => {
    setInputs(inputs.filter((input, index) => index !== indexToRemove));
  };

  const handleInsert = () => {
    axios
      .post("https://example.com/api/inputs", inputs)
      .then((response) => {
        console.log(response.data);
        // Handle response data
      })
      .catch((error) => {
        console.error(error);
        // Handle error
      });
  };

  const handleMerge = () => {
    axios
      .get("https://example.com/api/merged")
      .then((response) => {
        console.log(response.data);
        // Handle response data
      })
      .catch((error) => {
        console.error(error);
        // Handle error
      });
  };

  const handleSave = () => {
    axios
      .post("https://example.com/api/data", data)
      .then((response) => {
        console.log(response.data);
        // Handle response data
      })
      .catch((error) => {
        console.error(error);
        // Handle error
      });
  };

  return (
    <div className="container">
      <h1 className="title">Pragraph Merge</h1>
      <div className="inputs">
        {inputs.map((input, index) => (
          <div key={index} className="input-group">
            <input
              type="text"
              placeholder="Enter input"
              value={input}
              onChange={(e) => handleInput(e, index)}
            />
            <button onClick={() => handleRemoveInput(index)}>Remove</button>
          </div>
        ))}
        <button onClick={handleAddInput}>Add Input</button>
      </div>
      <div className="actions">
        <button onClick={handleMerge}>Merge</button>
        <button onClick={handleSave}>Save</button>
        <button onClick={handleInsert}>Insert</button>
      </div>
    </div>
  );
}

export default App;
