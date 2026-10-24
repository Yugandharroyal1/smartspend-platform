import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [query, setQuery] = useState('');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const search = async () => {
    if (!query.trim()) return;
    setLoading(true);
    setError('');
    try {
      const res = await axios.get('http://localhost:8080/api/products/compare', {
        params: { query: query.trim() },
      });
      setResult(res.data);
    } catch (e) {
      setResult(null);
      setError('Could not reach the backend: ' + e.message);
    }
    setLoading(false);
  };

  const prices = result && result.prices ? result.prices : [];

  return (
    <div className="app">
      <div className="header">
        <h1>SmartSpend</h1>
        <p>Find the cheapest price across Walmart, Target, Costco, Kroger and Publix</p>
      </div>

      <div className="search-box">
        <input
          type="text"
          placeholder="Search for a product (eggs, milk, bread...)"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && search()}
        />
        <button onClick={search}>{loading ? 'Searching...' : 'Compare'}</button>
      </div>

      {error && <div className="error">{error}</div>}

      {result && result.cheapestStore && (
        <div className="winner">
          Cheapest: <b>{result.cheapestStore}</b> at <b>${Number(result.cheapestPrice).toFixed(2)}</b>
        </div>
      )}

      {prices.length > 0 && (
        <table className="results">
          <thead>
            <tr><th>Store</th><th>Regular</th><th>Sale</th></tr>
          </thead>
          <tbody>
            {prices.map((p, i) => (
              <tr key={i} className={p.store && p.store.name === result.cheapestStore ? 'best' : ''}>
                <td>{p.store ? p.store.name : ''}</td>
                <td>${Number(p.regularPrice).toFixed(2)}</td>
                <td>{p.salePrice ? '$' + Number(p.salePrice).toFixed(2) : '-'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <div className="footer">Built by Yugandhar Ande | USF Computer Science</div>
    </div>
  );
}

export default App;
