import axios from 'axios';

// Remove the hardcoded baseURL so that requests use relative paths (e.g., '/api/auth/login').
// This allows the Vite development server proxy to intercept and forward the requests,
// bypassing CORS issues.
const api = axios.create();

// Add a request interceptor to include JWT token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Add a response interceptor to handle 401 (unauthorized) errors
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      // Clear token and redirect to login
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;