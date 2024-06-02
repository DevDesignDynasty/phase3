import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Main from './main';  // Assuming Main is your main component
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));

// Choose the component to render:
root.render(
  <React.StrictMode>
    <Main />  {/* Render the Main component */}
  </React.StrictMode>
);

reportWebVitals();

