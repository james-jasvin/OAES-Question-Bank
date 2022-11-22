import axios from 'axios'

// The API endpoint where bills are located
const itemsUrl = `http://localhost:8060/items`

// Gets all items which belong to a user
const getUserItems = async (user) => {
  // Get items of the given user, using query parameter, ?authorId={user.authorId}
  const response = await axios.get(`${itemsUrl}?authorId=${user.authorId}`)
  
  // The .data field would now contain the items of the users
  return response.data
}

// Pays the bill which is specified, after paying, the record of the bill is deleted
// So this translates to a delete request from axios to the bill API endpoint at the backend
const payBill = async (bill) => {
  const response = await axios.delete(`${itemsUrl}/${bill.billId}`)
  return response.data
}

const exportObject = { getUserItems, payBill }

export default exportObject