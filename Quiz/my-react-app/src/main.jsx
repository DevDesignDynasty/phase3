// Main.jsx

// Import necessary modules from React
import React, { useEffect, useState } from 'react';

// Import the main component of the application and the global styles
import App from './App.js';
import './index.css';
import axios from 'axios';

// Define the main functional component of the application
const Main = () => {
  // Define state to hold the quiz data fetched from the backend
  const [quizData, setQuizData] = useState(null);
  const [token, setToken] = useState(null);

  // Use the useEffect hook to fetch data from the backend when the component mounts
  useEffect(() => {
    //extract the token from the URL
    const getTokenFromUrl = () => {
      const url = window.location.href;
      const token = url.split('/').pop();
      return token;
    };

    // Define an asynchronous function to fetch quiz data from the backend
    

    const fetchData = async () => {
      try {
        const token = getTokenFromUrl();
        setToken(token);
        console.log(token);
    
        // Send a GET request to the backend API endpoint to fetch quiz data using Axios
        //makethe request to the backend wthout cors policy
        const response = await axios.get(`https://backendgame.sytes.net/get/${token}`);
        //const response = await fetch(`http://localhost:8081/quiz/${token}`, {method: 'GET', mode: 'cors'});
    
        // Access the response data
        const data = response.data;
    
        // Update the quizData state with the fetched quiz questions
        setQuizData(data.questions);
      } catch (error) {
        // Handle errors that occur during the fetch operation
        console.error('Error fetching quiz data:', error);
      }
    };
    

    // Call the fetchData function to initiate the data fetching process
    fetchData();
  }, []); // Empty dependency array ensures the effect runs only once when the component mounts

  // Render the main application component
  return (
    <>
      {quizData ? <App questions={quizData} token={token} /> : <p>You Have Already Submitted Quiz. Go and enjoy the game!!!</p>}
    </>
  );
};

export default Main;



