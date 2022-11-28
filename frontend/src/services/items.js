import axios from 'axios'

// The API endpoint where items are located, Gateway URL and not Microservice URL
const itemsUrl = `http://localhost:8081/item`

// Gets all items which belong to a user
const getUserItems = async (user) => {
  // Get items of the given user, using query parameter, ?authorId={user.authorId}
  const response = await axios.get(`${itemsUrl}?authorId=${user.authorId}`)
  
  // The .data field would now contain the items of the users
  return response.data
}

// Updates the item object in the database with the one that is specified here
const updateItem = async (updateItemJSON) => {
  const response = await axios.put(`${itemsUrl}/${updateItemJSON.item.itemId}`, updateItemJSON)
  return response.data
}

// Creates a new item object in the database
const createItem = async (item) => {
  const response = await axios.post(`${itemsUrl}`, item)
  return response.data
}

const exportObject = { getUserItems, updateItem, createItem }

export default exportObject