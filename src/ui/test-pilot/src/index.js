import React from 'react';
import ReactDOM from 'react-dom';
import getRoutes from './Routes';
import './index.css';

ReactDOM.render(
  <div className="App">
    <div className="App-header">
      <h2>Test Pilot</h2>
    </div>
    { getRoutes() }
  </div>,
  document.getElementById('root')
);
