# FinTech AI Advisor - Frontend

A professional fintech dashboard built with React, Vite, and React Router. The frontend provides secure authentication with JWT tokens and integrates seamlessly with the Spring Boot backend.

## Features

✅ **Authentication**
- User registration with password confirmation
- Secure login with JWT tokens
- Automatic token attachment to API requests
- 401 error handling with auto-redirect to login

✅ **Dashboard**
- Total income tracking
- Total expense tracking
- Savings calculation
- Financial health score

✅ **Transactions**
- View all transactions
- Transaction details (date, description, category, type, amount)
- Responsive table layout

✅ **Budget Management**
- View all budgets by category
- Automatic budget status loading
- Spent vs. limit tracking
- Remaining amount calculation

✅ **AI Insights**
- Budget overspending alerts
- Savings rate analysis
- Category spending analysis

✅ **User Profile**
- View authenticated user information
- Name, email, and ID display

## Prerequisites

- Node.js 16+ and npm
- Backend running on http://localhost:8080
- Git

## Setup & Installation

### 1. Install Dependencies

```bash
cd frontend
npm install
```

### 2. Environment Configuration

Copy the example env file:
```bash
cp .env.example .env
```

Edit `.env` if your backend is on a different URL:
```
VITE_API_BASE_URL=http://localhost:8080
```

### 3. Start Development Server

```bash
npm run dev
```

The frontend will run on `http://localhost:5173`

## Build for Production

```bash
npm run build
npm run preview
```

## Project Structure

```
frontend/src/
├── pages/              # Page components
│   ├── Login.jsx       # Login form with validation
│   ├── Register.jsx    # Registration with password confirmation
│   ├── Dashboard.jsx   # Financial overview
│   ├── Transactions.jsx # Transaction list
│   ├── Budgets.jsx     # Budget tracking
│   ├── Insights.jsx    # AI-powered insights
│   └── Profile.jsx     # User profile
├── components/         # Reusable components
│   ├── Sidebar.jsx     # Navigation sidebar
│   ├── Topbar.jsx      # Top navigation with user info
│   ├── Navbar.jsx      # Alternative navbar
│   ├── StatCard.jsx    # Dashboard stat cards
│   └── RecentTransactions.jsx  # Transaction list
├── hooks/              # Custom React hooks
│   └── useAuth.js      # User authentication state
├── api.js              # Axios instance with JWT handling
├── App.jsx             # Main app with routing
├── App.css             # Global and component styles
└── main.jsx            # React entry point
```

## Testing Checklist

### 1. Registration
```bash
# Navigate to http://localhost:5173/register
# Fill in form:
Name: Swasthik
Email: swasthik123@example.com
Password: 1234556
Confirm Password: 1234556
# Expected: Redirected to dashboard, logged in
```

### 2. Login
```bash
# Navigate to http://localhost:5173/login
# Fill in:
Email: swasthik123@example.com
Password: 1234556
# Expected: Redirected to dashboard, JWT token stored in localStorage
```

### 3. Protected Routes
```bash
# Try accessing http://localhost:5173/ without logging in
# Expected: Redirected to /login
```

### 4. Dashboard
- [ ] Total Income displays correctly
- [ ] Total Expense displays correctly
- [ ] Savings formula (Income - Expense) is accurate
- [ ] Health Score shows correct value (0-100)

### 5. Transactions
- [ ] Transactions load from backend
- [ ] Table displays: date, description, category, type, amount
- [ ] Amounts formatted with ₹ symbol

### 6. Budgets
- [ ] Budgets load automatically
- [ ] Budget status loads with logged-in user ID (not hardcoded)
- [ ] Shows: category, limit, spent, remaining

### 7. Insights
- [ ] AI insights load for logged-in user
- [ ] Shows budget alerts, savings rate, category analysis

### 8. Profile
- [ ] Displays logged-in user's name, email, and ID
- [ ] Data fetched from GET /api/users/me

### 9. Logout
- [ ] Click logout button in sidebar
- [ ] Token cleared from localStorage
- [ ] Redirected to /login

### 10. Error Handling
- [ ] 401 errors redirect to login
- [ ] Network errors show friendly messages
- [ ] Loading states display while fetching

## API Endpoints Used

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | `/api/auth/register` | User registration |
| POST | `/api/auth/login` | User login |
| GET | `/api/users/me` | Get current user info |
| GET | `/api/dashboard/total-income` | Total income |
| GET | `/api/dashboard/total-expense` | Total expenses |
| GET | `/api/dashboard/savings` | Savings amount |
| GET | `/api/health-score` | Financial health score |
| GET | `/api/transactions` | List all transactions |
| GET | `/api/budgets` | List all budgets |
| GET | `/api/budgets/status/{userId}` | Budget status for user |
| GET | `/api/insights/{userId}` | AI insights for user |

## Authentication Flow

1. **Registration**
   - User fills form with name, email, password
   - POST request to `/api/auth/register`
   - Backend returns JWT token
   - Token stored in `localStorage` as `token`
   - User redirected to dashboard

2. **API Requests**
   - `api.js` interceptor reads token from localStorage
   - Attaches `Authorization: Bearer <token>` header
   - All protected endpoints automatically authenticated

3. **Token Expiration**
   - If backend returns 401
   - Response interceptor clears token
   - User redirected to `/login`

## Common Issues & Solutions

### Issue: Login fails with "Network error"
**Solution**: Ensure backend is running on `http://localhost:8080`
```bash
# In backend folder
./mvnw spring-boot:run
```

### Issue: "Token not in localStorage"
**Solution**: Check if registration/login completed successfully
- Open browser DevTools (F12)
- Application > Local Storage > http://localhost:5173
- Verify `token` key exists

### Issue: "401 Unauthorized on protected endpoints"
**Solution**: 
- Verify JWT token is valid (not expired)
- Check Authorization header is included (DevTools > Network tab)
- Ensure backend `/api/users/me` endpoint works

### Issue: Budget Status shows null values
**Solution**: Ensure logged-in user has budgets created
- Check backend: `GET /api/budgets` returns data
- Verify user ID is correct from `GET /api/users/me`

### Issue: "CORS error"
**Solution**: Add CORS configuration to backend
```bash
# Already configured in SecurityConfig.java for development
# If needed, add to pom.xml and SecurityConfig
```

## Performance Tips

- Budgets and Insights pages auto-load on user data
- Dashboard uses Promise.all() for parallel API calls
- Axios interceptor prevents redundant token lookups
- Loading states prevent multiple simultaneous requests

## Security Practices

✅ JWT tokens stored in localStorage (accessible to frontend)
✅ Authorization header automatically attached
✅ Password confirmation on registration
✅ 401 handling clears token
✅ Sensitive data (password) excluded from responses with `@JsonIgnore`

## Next Steps / Future Enhancements

- [ ] Add spending charts using Recharts
- [ ] Implement transaction add/edit forms
- [ ] Add budget creation/edit UI
- [ ] Implement date range filtering
- [ ] Add dark mode
- [ ] Local logout confirmation
- [ ] User profile edit form
- [ ] Export transactions to CSV
- [ ] Push notifications for budget alerts

## Support

For issues with authentication, API integration, or styling:
1. Check browser console for errors (F12)
2. Check backend logs for API errors
3. Verify environment variables in `.env`
4. Check network requests in DevTools Network tab

