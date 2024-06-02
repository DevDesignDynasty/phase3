const express = require('express');
const app = express();
const path = require('path');
const cors = require('cors');

app.use(cors({
    origin: '*'
}));

const reactAppDirectory = path.join(__dirname, '/my-react-app/build');

// Serve static files from the specified directory
app.use(express.static(reactAppDirectory));

//Serve the index.html file for any other requests
app.get('*', (req, res) => {
  res.sendFile(path.join(reactAppDirectory, 'index.html'));
});

// Start the server on port 8080
app.listen(5000, () => {
  console.log('Server started on http://localhost:5000');
});