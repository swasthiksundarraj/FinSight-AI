# FinTech AI Advisor - Complete End-to-End Testing Guide

**Last Updated**: June 13, 2026
**Status**: ✅ Ready for Testing

---

## Prerequisites

- Backend running: `./mvnw spring-boot:run` (http://localhost:8080)
- Frontend running: `npm run dev` (http://localhost:5173)
- Test user credentials ready (see below)

---

## Test User Accounts

### Account 1: Fresh Registration
```
Name: Swasthik Sundar
Email: swasthik@fintech.com
Password: SecurePass123!
```

### Account 2: Pre-existing (if already registered)
```
Email: swasthik123@example.com
Password: 1234556
```

---

## Test Scenario 1: Complete User Onboarding

### Step 1A: Test Registration (Happy Path)

1. Open http://localhost:5173/register
2. Fill form:
   - Name: `Swasthik Sundar`
   - Email: `swasthik@fintech.com`
   - Password: `SecurePass123!`
   - Confirm Password: `SecurePass123!`
3. Click "Create account"

**Expected Results:**
- ✅ No client-side validation errors
- ✅ POST request to `/api/auth/register` sent
- ✅ HTTP 201 response with JWT token
- ✅ Token stored in localStorage (check DevTools > Application > Local Storage)
- ✅ Redirect to dashboard (`/`)
- ✅ Sidebar visible
- ✅ Topbar shows "Swasthik Sundar"

**Verification Commands (Browser Console):**
```javascript
localStorage.getItem('token')  // Should return long JWT string
```

### Step 1B: Test Registration (Validation Edge Cases)

Test 1: Missing name
- Email: `test@test.com`
- Password: `Pass123`
- Confirm: `Pass123`
- **Expected:** "Name is required" error shown

Test 2: Invalid email
- Name: `Test`
- Email: `invalid-email`
- Password: `Pass123`
- Confirm: `Pass123`
- **Expected:** Server returns validation error

Test 3: Password too short
- Name: `Test`
- Email: `test@test.com`
- Password: `Pass`
- Confirm: `Pass`
- **Expected:** "Password must be at least 6 characters" error

Test 4: Passwords don't match
- Name: `Test`
- Email: `test@test.com`
- Password: `Pass123`
- Confirm: `Different1`
- **Expected:** "Passwords do not match" error shown

Test 5: Duplicate email
- Use same email as Account 1
- **Expected:** Error: "User already exists with email..."

---

## Test Scenario 2: Login Flow

### Step 2A: Fresh Login
1. Logout first: Click logout button in sidebar
2. Navigate to http://localhost:5173/login
3. Enter:
   - Email: `swasthik@fintech.com`
   - Password: `SecurePass123!`
4. Click "Sign in"

**Expected Results:**
- ✅ POST to `/api/auth/login`
- ✅ HTTP 200 response with JWT token
- ✅ Token stored in localStorage
- ✅ Redirect to dashboard
- ✅ Topbar shows "Swasthik Sundar"

### Step 2B: Login with Invalid Credentials
1. Navigate to http://localhost:5173/login
2. Try:
   - Email: `swasthik@fintech.com`
   - Password: `wrongpassword`
3. Click "Sign in"

**Expected Results:**
- ✅ HTTP 401 error from backend
- ✅ Error message shown: "Invalid email or password"
- ✅ Remain on login page
- ✅ Token NOT stored

---

## Test Scenario 3: Protected Routes

### Step 3A: Unauthorized Access
1. Logout and clear localStorage: 
   ```javascript
   localStorage.removeItem('token')
   ```
2. Try to access http://localhost:5173/dashboard
3. Try to access http://localhost:5173/transactions

**Expected Results:**
- ✅ Redirected to `/login`
- ✅ Cannot access protected pages without token

### Step 3B: Token Expiration Handling
*Note: This requires backend token expiration config*
1. Get JWT token and wait for expiration (or manually set short expiration)
2. Try to navigate to protected route
3. Try to trigger API call

**Expected Results:**
- ✅ Backend returns 401
- ✅ Frontend response interceptor catches it
- ✅ Token cleared from localStorage
- ✅ Redirected to `/login`

---

## Test Scenario 4: Dashboard Functionality

### Step 4A: Load Dashboard Data
1. Login with valid credentials
2. Navigate to / (Dashboard)
3. Wait for data to load

**Expected Results:**
- ✅ Shows "Loading your dashboard..." briefly
- ✅ Card 1: Total Income with ₹ symbol
- ✅ Card 2: Total Expense with ₹ symbol
- ✅ Card 3: Savings (Income - Expense)
- ✅ Card 4: Health Score (0-100)
- ✅ All values formatted correctly

**Manual Verification:**
Open DevTools Network tab:
- ✅ GET `/api/dashboard/total-income` → 200
- ✅ GET `/api/dashboard/total-expense` → 200
- ✅ GET `/api/dashboard/savings` → 200
- ✅ GET `/api/health-score` → 200

**Example Response Check:**
```javascript
// In Console after API responses
fetch('http://localhost:8080/api/dashboard/total-income', {
  headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
}).then(r => r.json()).then(console.log)
// Should show: {totalIncome: number}
```

---

## Test Scenario 5: Transactions Page

### Step 5A: Load and Display Transactions
1. Navigate to Sidebar > Transactions
2. Wait for table to load

**Expected Results:**
- ✅ Shows "Loading transactions..." briefly
- ✅ Table displays transactions
- ✅ Columns: Date, Description, Category, Type, Amount
- ✅ Amounts formatted with ₹ symbol

### Step 5B: No Transactions State
*If no transactions exist:*
- ✅ Shows message: "No transactions available"
- ✅ No table displayed

### Step 5C: Error Handling
*Simulate network error (use DevTools to throttle):*
- ✅ Shows error message: "Failed to load transactions"
- ✅ User can retry

---

## Test Scenario 6: Budgets Management

### Step 6A: Load Budgets and Status
1. Navigate to Sidebar > Budgets
2. Wait for data to load

**Expected Results:**
- ✅ Budgets list shows (if any exist)
- ✅ Budget Status auto-loads (NOT manual button!)
- ✅ Shows: category, limit, spent, remaining
- ✅ **Uses real userId (not hardcoded 1)**

**Verify userId is correct:**
```javascript
// In Console
const userId = fetch('http://localhost:8080/api/users/me', {
  headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
}).then(r => r.json()).then(u => console.log('User ID:', u.id))
```

### Step 6B: Budget Status Details
For each budget, verify:
- ✅ Category name displays correctly
- ✅ Limit shows correct value with ₹
- ✅ Spent shows correct sum (from transactions)
- ✅ Remaining = Limit - Spent

**Example Math Check:**
- Budget: Food, Limit: ₹5000
- Transactions in Food: ₹2000, ₹1500
- Spent: ₹3500
- Remaining: ₹1500 ✓

---

## Test Scenario 7: AI Insights

### Step 7A: Load Insights
1. Navigate to Sidebar > Insights
2. Wait for insights to load

**Expected Results:**
- ✅ Shows "Loading insights..." briefly
- ✅ Displays list of insights (if any)
- ✅ **Uses real userId (fetched from `/api/users/me`)**

### Step 7B: Insights Content
Expected insight types:
- ✅ Budget overspending alerts (e.g., "Your food spending exceeds budget by ₹800")
- ✅ Savings rate analysis (e.g., "Your savings rate is above 40%")
- ✅ Category spending (e.g., "Food accounts for 35% of your total expenses")

### Step 7C: No Insights State
*If user has no transactions/budgets:*
- ✅ Shows message: "No insights available yet"

---

## Test Scenario 8: User Profile

### Step 8A: Load Profile
1. Navigate to Sidebar > Profile
2. Wait for data to load

**Expected Results:**
- ✅ Shows "Loading..." briefly
- ✅ Displays user name
- ✅ Displays user email
- ✅ Displays user ID
- ✅ **Data fetched from `/api/users/me` (not hardcoded)**

**Verify Data Source:**
Open DevTools Network tab:
- ✅ GET `/api/users/me` called
- ✅ Response shows current user's ID

### Step 8B: Profile Data Accuracy
```javascript
// Verify profile matches login
// Name should match what was registered
// Email should match what was logged in with
// ID should be consistent across requests
```

---

## Test Scenario 9: Navigation & Logout

### Step 9A: Sidebar Navigation
1. Click each link in sidebar:
   - Dashboard → ✅ Load dashboard
   - Transactions → ✅ Load transactions
   - Budgets → ✅ Load budgets
   - Insights → ✅ Load insights
   - Profile → ✅ Load profile

**Expected:** No 404 errors, all pages load correctly

### Step 9B: Active Route Highlighting
1. Click a sidebar link
2. Check if link is highlighted/active style applied

**Expected:** ✅ Active link shows different styling

### Step 9C: Logout Functionality
1. Click "Logout" button in sidebar
2. Observe what happens

**Expected Results:**
- ✅ Token cleared from localStorage
- ✅ Redirected to `/login`
- ✅ Sidebar hidden on login page
- ✅ Can't access dashboard without re-logging

---

## Test Scenario 10: Cross-Browser Testing

Test in multiple browsers to ensure compatibility:

### Browser: Chrome
- [ ] All pages load
- [ ] All API calls work
- [ ] DevTools shows no errors
- [ ] Local storage accessible

### Browser: Firefox
- [ ] Same as Chrome

### Browser: Safari
- [ ] Same as Chrome

---

## Test Scenario 11: Responsive Design

### Desktop (1400px+)
- [ ] Sidebar visible on left
- [ ] Content takes remaining space
- [ ] Tables fully visible
- [ ] All controls accessible

### Tablet (768px - 1024px)
- [ ] Sidebar collapses or hides
- [ ] Content adapts
- [ ] Touch-friendly buttons
- [ ] Tables may scroll horizontally

### Mobile (320px - 640px)
- [ ] Sidebar hidden/collapsed
- [ ] Content full width
- [ ] Forms stack vertically
- [ ] Touch-friendly padding

**Test with DevTools:**
1. Open DevTools (F12)
2. Click device toolbar icon
3. Test different viewport sizes

---

## Test Scenario 12: Error Scenarios

### Network Error Handling
1. Go to DevTools > Network tab
2. Throttle to "Offline"
3. Try to load dashboard

**Expected:**
- ✅ Shows error message (timeout or "Failed to load")
- ✅ No unhandled JavaScript errors

### 401 Unauthorized Handling
1. Manually delete token: `localStorage.removeItem('token')`
2. Try to call protected API
3. **Expected:** Auto-redirect to login

### Invalid API Responses
1. Response interceptor logs errors to console
2. User sees friendly error messages
3. No exception errors in console

---

## API Call Verification Checklist

### Check Network Tab (DevTools > Network)

#### Auth APIs
- [ ] POST `/api/auth/register` → 201 (201 is success for creation)
- [ ] POST `/api/auth/login` → 200
- [ ] Response bodies include: `{ token: "...", message: "..." }`

#### User Endpoints
- [ ] GET `/api/users/me` → 200
- [ ] Response: `{ id: number, name: string, email: string }`

#### Dashboard APIs
- [ ] GET `/api/dashboard/total-income` → 200
- [ ] GET `/api/dashboard/total-expense` → 200
- [ ] GET `/api/dashboard/savings` → 200
- [ ] GET `/api/health-score` → 200

#### Protected APIs
- [ ] GET `/api/transactions` → 200
- [ ] GET `/api/budgets` → 200
- [ ] GET `/api/budgets/status/{userId}` → 200
- [ ] GET `/api/insights/{userId}` → 200

#### All Requests Should Have
- [ ] Authorization header: `Authorization: Bearer <token>`
- [ ] Status: 200 or 201 (never 4xx or 5xx if user valid)

---

## Browser Console Checks

Run these in Browser Console to verify setup:

```javascript
// 1. Check token is stored
console.log('Token:', localStorage.getItem('token').substring(0, 20) + '...')

// 2. Check user info endpoint
fetch('http://localhost:8080/api/users/me', {
  headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
}).then(r => r.json()).then(u => console.log('User:', u))

// 3. Check dashboard endpoint
fetch('http://localhost:8080/api/dashboard/total-income', {
  headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
}).then(r => r.json()).then(d => console.log('Income:', d))

// 4. Check budgets endpoint
fetch('http://localhost:8080/api/budgets', {
  headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
}).then(r => r.json()).then(b => console.log('Budgets:', b))
```

---

## Final Verification Checklist

- [ ] Backend compiles without errors
- [ ] Backend starts without exceptions
- [ ] Frontend npm install successful
- [ ] Frontend dev server starts
- [ ] Registration works end-to-end
- [ ] Login works with valid credentials
- [ ] Login rejects invalid credentials
- [ ] Protected routes require authentication
- [ ] Dashboard loads all stats correctly
- [ ] Transactions display properly
- [ ] Budgets show budget status
- [ ] Budgets use real userId (not hardcoded)
- [ ] Insights display AI-generated insights
- [ ] Insights use real userId (not hardcoded)
- [ ] Profile shows real user data
- [ ] Logout clears token properly
- [ ] Topbar shows logged-in user name
- [ ] All API calls have Authorization header
- [ ] 401 errors redirect to login
- [ ] Validation errors display on forms
- [ ] Client-side form validation works
- [ ] No console errors in DevTools
- [ ] Mobile responsive layout works

---

## Success Indicators

### ✅ Everything Working When:
1. User can complete full registration → login → dashboard flow
2. All pages load without 403/404 errors
3. User data fetched from backend (not demo/hardcoded)
4. API calls visible in Network tab with JWT header
5. No unhandled JavaScript errors in console
6. Error messages clear and helpful

### 🚀 Ready for Production When:
1. All tests pass consistently
2. No memory leaks or performance issues
3. Response times acceptable (< 2s)
4. UI responsive on all screen sizes
5. No console warnings/errors
6. Database has test data

---

## Troubleshooting Quick Reference

| Issue | Check | Solution |
|-------|-------|----------|
| Cannot connect backend | Is port 8080 open? | `lsof -i :8080` or `netstat -an` |
| Cannot connect frontend | Is port 5173 open? | `lsof -i :5173` |
| Login fails | Token in localStorage? | Check DevTools Application tab |
| 404 on protected route | Is token valid? | Login again and check token |
| CORS error | Backend CORS config? | Already configured in SecurityConfig |
| Budget status null | User has budgets? | Create budgets first |
| Profile shows demo user | `/api/users/me` works? | Login again, check network calls |

---

## Recommended Test Order

1. **First 5 min**: Register → Login → View Dashboard
2. **Next 5 min**: Check all sidebar pages load
3. **Next 5 min**: Verify Network tab shows JWT headers
4. **Next 5 min**: Test error scenarios (invalid login, logout, etc.)
5. **Next 5 min**: Check mobile responsiveness
6. **Final**: Full end-to-end walkthrough

---

**Total Estimated Testing Time: 30-45 minutes**

**Status**: 🎯 Ready to Test!

