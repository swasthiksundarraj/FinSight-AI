function formatCurrency(amount) {
  const formatted = Math.abs(amount).toLocaleString('en-US', {
    style: 'currency',
    currency: 'USD',
  })
  return amount >= 0 ? `+${formatted}` : `-${formatted}`
}

function formatDate(dateStr) {
  return new Date(dateStr + 'T12:00:00').toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
  })
}

function RecentTransactions({ transactions }) {
  return (
    <section className="transactions" id="transactions">
      <div className="transactions__header">
        <div>
          <h2 className="transactions__title">Recent Transactions</h2>
          <p className="transactions__subtitle">Your latest account activity</p>
        </div>
        <button type="button" className="transactions__view-all">
          View all
        </button>
      </div>

      <div className="transactions__table-wrapper">
        <table className="transactions__table">
          <thead>
            <tr>
              <th scope="col">Date</th>
              <th scope="col">Description</th>
              <th scope="col">Category</th>
              <th scope="col" className="transactions__amount-col">
                Amount
              </th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((tx) => (
              <tr key={tx.id}>
                <td data-label="Date">{formatDate(tx.date)}</td>
                <td data-label="Description">{tx.description}</td>
                <td data-label="Category">
                  <span className={`transactions__badge transactions__badge--${tx.type}`}>
                    {tx.category}
                  </span>
                </td>
                <td
                  data-label="Amount"
                  className={`transactions__amount transactions__amount--${tx.type}`}
                >
                  {formatCurrency(tx.amount)}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  )
}

export default RecentTransactions
