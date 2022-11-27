import axios from 'axios'

// The API endpoint where courses are located
const courseUrl = `http://localhost:8081/course`

// Gets all items which belong to a user
const getCourses = async () => {
  try {
    const response = await axios.get(`${courseUrl}`)
  
    // The .data field would now contain the items of the users
    return response.data
  }
  catch (exception) {
    return []
  }
}

const exportObject = { getCourses }

export default exportObject