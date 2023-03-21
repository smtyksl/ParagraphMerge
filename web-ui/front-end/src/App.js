import { useState } from "react";
import axios from "axios";
import { v4 as uuidv4 } from 'uuid';
import "./styles.css";

function App() {
  const [text, setText] = useState('');
  const [elapsedTime, setelapsedTime] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [lastSavedIds, setLastSavedIds] = useState(null);
  const generateUUID = () => {
    var id = uuidv4()
    // console.log("id===>>> ", id)
    return id;
  }
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

  };
  const handleChange = (event) => {
    setText(event.target.value);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleMerge = () => {
    axios
      .get("http://localhost:8080/merge/lastSavedIds", lastSavedIds)
      .then((response) => {
        console.log(response.data);
        setLastSavedIds(response.data)

        // Handle response data
      })
      .catch((error) => {
        console.error(error);
        // Handle error
      });
    axios.post("http://localhost:8080/merge/mergeTexts", lastSavedIds)
      .then((response) => {
        console.log(response.data);
        setText(response.data);
      })
      .catch((error) => {
        console.error("mergeTexts" + error);
      });

    axios
      .get("http://localhost:8080/merge/getMergedText")
      .then((response) => {
        console.log("mergeedd ==>>> ", response.data);
        setText(response.data)

        // Handle response data
      })
      .catch((error) => {
        console.error(error);
        console.log("mergeedd ==>>> ");
        // Handle error
      });
      axios
      .get("http://localhost:8080/merge/getElapsedTime")
      .then((response) => {
        console.log("mergeedd ==>>> ", response.data);
        setelapsedTime(response.data)

        // Handle response data
      })
      .catch((error) => {
        console.error(error);
        console.log("mergeedd ==>>> ");
        // Handle error
      });
    setIsModalOpen(true);

  };

  const handleSave = () => {
    const jsonArray = [];
    for (let i = 0; i < inputs.length; i++) {
      const jsonObject = {
        id: generateUUID(),
        title: `title`,
        content: inputs[i]
      };
      jsonArray.push(jsonObject);
    }

    axios
      .post("http://localhost:8080/merge/saveTexts", jsonArray)
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
      <div className="actions">
        <button onClick={handleMerge}>Merge</button>
        <button onClick={handleSave}>Save</button>
      </div>
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
      <div>
        {isModalOpen && (
          <div className="modal">
            <div className="modal-overlay" onClick={closeModal} />
            <div className="modal-content">
              <h2>Merging Operation Results</h2>
              <p>{text}</p>
              <h2>AlgorithM Elapsed Time</h2>
              <p>{elapsedTime}</p>
              <button onClick={closeModal}>Close</button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
